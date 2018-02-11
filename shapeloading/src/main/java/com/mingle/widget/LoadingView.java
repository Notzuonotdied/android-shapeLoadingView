package com.mingle.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.mingle.widget.util.DimenUtil;


/**
 * @author zzz40500
 * @date 15/4/6
 */
public class LoadingView extends BaseLoadingView {

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 设置加载信息
     *
     * @param loadingText 加载信息
     */
    @Override
    public void setLoadText(String loadingText) {
        if (TextUtils.isEmpty(loadingText)) {
            mLoadTextView.setVisibility(GONE);
        } else {
            mLoadTextView.setVisibility(VISIBLE);
        }
        this.mLoadTextView.setText(loadingText);
    }

    /**
     * 设置加载信息字体颜色
     *
     * @param textColor 字体的颜色
     */
    public void setLoadTextColorID(int textColor) {
        this.mLoadTextView.setTextColor(textColor);
    }

    /**
     * 设置跳动距离
     *
     * @param distance 跳动的距离
     */
    @Override
    public void setJumpDistanceDP(int distance) {
        // 默认最小的Distance为33dp
        if (distance <= 33) {
            mDistance = DimenUtil.dp2px(context, 33f);
        } else {
            mDistance = DimenUtil.dp2px(context, distance);
        }
        // 如果设置的distance大于默认高度的时候，取小
        mDistance = Math.min(DimenUtil.defaultHeight(context), mDistance);
        // 设置阴影的Margin，使得下方阴影始终在Shape下方
        setViewMargin(mIndicationIm, false, (int) mDistance);
    }

    /**
     * 获取暂停的时间，单位毫秒
     */
    public int getDelayMS() {
        return mDelay;
    }

    /**
     * 设置暂停时间，单位毫秒
     */
    public void setDelayMS(int delay) {
        mDelay = delay;
    }

    /**
     * 获取加载信息内容
     */
    public String getLoadingText() {
        return mLoadTextView.getText().toString();
    }

    /**
     * 设置加载信息
     *
     * @param loadingText 加载信息
     */
    public void setLoadingText(CharSequence loadingText) {
        if (TextUtils.isEmpty(loadingText)) {
            mLoadTextView.setVisibility(GONE);
        } else {
            mLoadTextView.setVisibility(VISIBLE);
        }
        mLoadTextView.setText(loadingText);
    }

    /**
     * 设置加载信息字体的大小
     *
     * @param loadTextSize 字体大小，单位为sp
     */
    public void setLoadTextSizeSP(int loadTextSize) {
        this.mLoadTextView.setTextSize(DimenUtil.sp2dp(context, loadTextSize));
    }

    /**
     * 设置弹跳的加速度
     *
     * @param acceleration 加速度，默认是1.2f
     */
    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration == 0 ? 1.2f : acceleration;
    }

    /**
     * 设置动画的持续时间
     *
     * @param duration 动画持续时间，默认是383ms，单位毫秒
     */
    public void setDurationMS(int duration) {
        this.duration = duration == 0 ? 383 : duration;
    }
}
