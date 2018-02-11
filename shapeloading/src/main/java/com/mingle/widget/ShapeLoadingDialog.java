package com.mingle.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.mingle.shapeloading.R;

/**
 * @author zzz40500
 * @date 15/6/15
 * @describe TODO
 */
public class ShapeLoadingDialog extends Dialog {

    private LoadingView mLoadingView;

    private Builder mBuilder;

    private ShapeLoadingDialog(Builder builder) {
        super(builder.mContext, R.style.custom_dialog);
        mBuilder = builder;
        setCancelable(mBuilder.mCancelable);
        setCanceledOnTouchOutside(mBuilder.mCanceledOnTouchOutside);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);

        mLoadingView = findViewById(R.id.loadView);

        // 设置属性
        mLoadingView.setDelayMS(mBuilder.mDelay);
        mLoadingView.setDurationMS(mBuilder.mDuration);
        mLoadingView.setLoadingText(mBuilder.mLoadText);
        mLoadingView.setJumpDistanceDP(mBuilder.mDistance);
        mLoadingView.setAcceleration(mBuilder.mAcceleration);
        mLoadingView.setLoadTextSizeSP(mBuilder.mLoadTextSize);
        mLoadingView.setLoadTextColorID(mBuilder.mLoadTextColor);

        setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mLoadingView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        mLoadingView.setVisibility(View.VISIBLE);
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    /**
     * 使用建造者模式来构建Dialog
     */
    public static class Builder {

        private Context mContext;
        /**
         * 暂停时间，默认暂停时间为80ms
         */
        private int mDelay = 80;
        /**
         * 加载信息
         */
        private CharSequence mLoadText;
        /**
         * 加载信息字体颜色
         */
        private int mLoadTextColor = 0xFF577FA1;
        /**
         * 加载信息字体大小,单位SP，默认为16sp
         */
        private int mLoadTextSize = 16;
        /**
         * 设置跳动的距离，默认最小的distance为33dp
         */
        private int mDistance = 0;
        /**
         * 设置弹跳的加速度
         */
        private float mAcceleration;
        /**
         * 设置动画的持续时间，最小为383ms
         */
        private int mDuration;

        private boolean mCancelable = true;

        private boolean mCanceledOnTouchOutside = true;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder delayMS(int delay) {
            mDelay = delay;
            return this;
        }

        public Builder loadText(CharSequence loadText) {
            mLoadText = loadText;
            return this;
        }

        public Builder loadText(int resId) {
            mLoadText = mContext.getString(resId);
            return this;
        }

        public Builder loadTextColorID(int colorId) {
            mLoadTextColor = colorId;
            return this;
        }

        public Builder loadTextSizeSP(int spSize) {
            mLoadTextSize = spSize;
            return this;
        }

        public Builder distanceDP(int distance) {
            mDistance = distance;
            return this;
        }

        public Builder acceleration(float acceleration) {
            mAcceleration = acceleration;
            return this;
        }

        public Builder duration(int duration) {
            mDuration = duration;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            mCancelable = cancelable;
            mCanceledOnTouchOutside = cancelable;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public ShapeLoadingDialog build() {
            return new ShapeLoadingDialog(this);
        }

        public ShapeLoadingDialog show() {
            ShapeLoadingDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }
}
