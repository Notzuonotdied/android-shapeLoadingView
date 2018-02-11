package com.mingle.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingle.shapeloading.R;
import com.mingle.widget.util.DimenUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * @author wangyu
 * @date 18-2-10
 * @describe TODO
 */

public abstract class BaseLoadingView extends RelativeLayout {
    public static final String TAG = BaseLoadingView.class.getName();
    /**
     * 动画移动的距离
     */
    protected float mDistance;
    /**
     * 暂停的时间
     */
    protected int mDelay;
    /**
     * 加速度
     */
    protected float acceleration = 1.3f;
    /**
     * 动画的持续时间
     */
    protected int duration;
    protected Context context;
    protected TextView mLoadTextView;
    protected ImageView mIndicationIm;
    /**
     * 上抛动画
     */
    private AnimatorSet mUpAnimatorSet;
    /**
     * 下落动画
     */
    private AnimatorSet mDownAnimatorSet;
    private boolean mStopped = false;
    private ShapeLoadingView mShapeLoadingView;
    private Runnable mFreeFallRunnable = new Runnable() {
        @Override
        public void run() {
            ViewHelper.setRotation(mShapeLoadingView, 270f);
            ViewHelper.setTranslationY(mShapeLoadingView, 0f);
            ViewHelper.setScaleX(mIndicationIm, 0.2f);
            mStopped = false;
            freeFall();
        }
    };

    public BaseLoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public BaseLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BaseLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化，引入布局和自定义属性
     *
     * @param context context
     * @param attrs   属性
     */
    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        // 导入布局
        LayoutInflater.from(context).inflate(R.layout.load_view, this, true);

        // 加载动画
        mShapeLoadingView = findViewById(R.id.shapeLoadingView);
        //
        mIndicationIm = findViewById(R.id.indication);
        // 加载文字
        mLoadTextView = findViewById(R.id.promptTV);

        ViewHelper.setScaleX(mIndicationIm, 0.2f);

        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseLoadingView);

        // 加载文字
        String loadText = typedArray.getString(R.styleable.BaseLoadingView_loadingText);

        // 加载文字的样式
        int textAppearance = typedArray.getResourceId(R.styleable.BaseLoadingView_loadingText, -1);

        // 延迟
        mDelay = typedArray.getInteger(R.styleable.BaseLoadingView_delay, 80);

        // 加载文字的大小
        float textSize = typedArray.getDimension(R.styleable.BaseLoadingView_textSize,
                DimenUtil.sp2px(context, 16));
        mLoadTextView.setTextSize(DimenUtil.px2dp(context, textSize));

        // 加载文字颜色
        int color = typedArray.getColor(R.styleable.BaseLoadingView_textColor, getResources().getColor(R.color.colorText));
        mLoadTextView.setTextColor(color);

        // 设置LoadingView的跳动距离，默认是54dp
        mDistance = typedArray.getDimension(R.styleable.BaseLoadingView_distance,
                DimenUtil.dp2px(context, 54f));
        setJumpDistanceDP((int) DimenUtil.px2dp(context, mDistance));

        // 获取弹跳的加速度
        acceleration = typedArray.getFloat(R.styleable.BaseLoadingView_acceleration, 1.2f);

        // 设置动画的时间
        duration = typedArray.getInt(R.styleable.BaseLoadingView_duration, 383);

        typedArray.recycle();

        if (textAppearance != -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mLoadTextView.setTextAppearance(textAppearance);
            } else {
                mLoadTextView.setTextAppearance(getContext(), textAppearance);
            }
        }
        setLoadText(loadText);

        // 父布局宽高都设置为wrap_content
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        this.setLayoutParams(params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getVisibility() == VISIBLE) {
            startLoading(mDelay);
        }
    }

    /**
     * 启动动画（开始加载）
     *
     * @param delay 暂停时间
     */
    private void startLoading(long delay) {
        if (mDownAnimatorSet != null && mDownAnimatorSet.isRunning()) {
            return;
        }
        this.removeCallbacks(mFreeFallRunnable);
        if (delay > 0) {
            this.postDelayed(mFreeFallRunnable, delay);
        } else {
            this.post(mFreeFallRunnable);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoading();
    }

    /**
     * 停止动画
     */
    private void stopLoading() {
        mStopped = true;
        if (mUpAnimatorSet != null) {
            if (mUpAnimatorSet.isRunning()) {
                mUpAnimatorSet.cancel();
            }
            mUpAnimatorSet.removeAllListeners();
            for (Animator animator : mUpAnimatorSet.getChildAnimations()) {
                animator.removeAllListeners();
            }
            mUpAnimatorSet = null;
        }
        if (mDownAnimatorSet != null) {
            if (mDownAnimatorSet.isRunning()) {
                mDownAnimatorSet.cancel();
            }
            mDownAnimatorSet.removeAllListeners();
            for (Animator animator : mDownAnimatorSet.getChildAnimations()) {
                animator.removeAllListeners();
            }
            mDownAnimatorSet = null;
        }
        this.removeCallbacks(mFreeFallRunnable);
    }

    @Override
    public void setVisibility(int visibility) {
        this.setVisibility(visibility, mDelay);
    }

    /**
     * 是否可见
     *
     * @param visibility GONE，VISIBLE，INVISIBLE
     * @param delay      暂停时间
     */
    public void setVisibility(int visibility, int delay) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            startLoading(delay);
        } else {
            stopLoading();
        }
    }

    /**
     * 上抛
     */
    public void upThrow() {
        // 因为每个Shape旋转的角度不一样，所以要先把Set清空，重新设置。
        if (mUpAnimatorSet != null) {
            if (mUpAnimatorSet.isRunning()) {
                mUpAnimatorSet.cancel();
            }
            mUpAnimatorSet.removeAllListeners();
            for (Animator animator : mUpAnimatorSet.getChildAnimations()) {
                animator.removeAllListeners();
            }
            mUpAnimatorSet = null;
        }

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mShapeLoadingView,
                "translationY", mDistance, 0);
        ObjectAnimator scaleIndication = ObjectAnimator.ofFloat(mIndicationIm,
                "scaleX", 1f, 0.2f);
        ObjectAnimator objectAnimator1 = null;
        switch (mShapeLoadingView.getShape()) {
            case SHAPE_RECT:
                objectAnimator1 = ObjectAnimator.ofFloat(mShapeLoadingView,
                        "rotation", 0, -270);
                break;
            case SHAPE_CIRCLE:
                objectAnimator1 = ObjectAnimator.ofFloat(mShapeLoadingView,
                        "rotation", 0, 180);
                break;
            case SHAPE_TRIANGLE:
                objectAnimator1 = ObjectAnimator.ofFloat(mShapeLoadingView,
                        "rotation", 0, 270);
                break;
            default:
                break;
        }
        mUpAnimatorSet = new AnimatorSet();
        mUpAnimatorSet.playTogether(objectAnimator, objectAnimator1, scaleIndication);
        mUpAnimatorSet.setDuration(duration);
        mUpAnimatorSet.setInterpolator(new DecelerateInterpolator(acceleration));
        mUpAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!mStopped) {
                    freeFall();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mUpAnimatorSet.start();
    }

    /**
     * 下落
     */
    public void freeFall() {
        if (mDownAnimatorSet == null) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mShapeLoadingView,
                    "translationY", 0, mDistance);
            ObjectAnimator scaleIndication = ObjectAnimator.ofFloat(mIndicationIm,
                    "scaleX", 0.2f, 1f);
            mDownAnimatorSet = new AnimatorSet();
            mDownAnimatorSet.playTogether(objectAnimator, scaleIndication);
            mDownAnimatorSet.setDuration(duration);
            mDownAnimatorSet.setInterpolator(new AccelerateInterpolator(acceleration));
            mDownAnimatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!mStopped) {
                        mShapeLoadingView.changeShape();
                        upThrow();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        mDownAnimatorSet.start();
    }

    /**
     * 设置某个View的margin
     *
     * @param view 需要设置的view
     * @param isDp 需要设置的数值是否为DP
     * @param top  上边距
     */
    public void setViewMargin(View view, boolean isDp, int top) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams;

        // 获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            // 不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }

        // 根据DP与PX转换计算值
        if (isDp) {
            top = (int) DimenUtil.px2dp(context, top);
        }
        // 设置margin
        marginParams.setMargins(0, top, 0, 0);
        view.setLayoutParams(marginParams);
    }

    /**
     * 设置跳动距离
     *
     * @param distance 跳动的距离
     */
    public abstract void setJumpDistanceDP(int distance);

    /**
     * 设置加载信息字体的大小
     *
     * @param loadTextSize 字体大小，单位为sp
     */
    public abstract void setLoadText(String loadTextSize);
}
