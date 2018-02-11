# 效果图先行

![](./img/show.gif)

# 地址:

[github 直达](https://github.com/zzz40500/android-shapeLoadingView)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Shape%20Loading%20View-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1897)

# Usage

## 配置

在`/build.gralde`中添加如下内容：

``` gradle
repositories {
    maven {
        url "https://jitpack.io"
    }
}
```

在`/app/build.gradle`中添加如下内容：

``` gradle
dependencies {
    compile 'com.github.zzz40500:android-shapeLoadingView:1.0.3.2'
}
```

## 参数

| 参数 | 说明 |
| ---- | ----------------------- |
| loadingText | 加载信息。 |
| loadingTextAppearance | 字体样式。 |
| delay | 暂停时间，单位为ms，默认为80ms。 |
| textSize | 加载信息字体大小，默认为16sp。 |
| textColor | 加载信息字体颜色。 |
| distance | 动画弹跳的距离，单位为dp，默认为54dp。 |
| acceleration | 动画弹跳的加速度，默认为1.2f。 |
| duration | 动画持续时间，单位为ms，默认为383ms。 |

## 使用

使用LoadingView教程如下：

``` xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.mingle.ViewDemoActivity">

    <com.mingle.widget.LoadingView
        android:id="@+id/loadView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        app:distance="200dp"
        app:textSize="24sp"
        app:delay="3000"
        app:acceleration="0.8"
        app:duration="1000"
        app:textColor="@color/triangle"
        app:loadingText="加载中..." />

</RelativeLayout>
```

使用ShapeLoadingDialog教程如下：

``` java
public class DialogDemoActivity extends AppCompatActivity {

    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_demo);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(this)
                .loadText("加载中...")
                .delayMS(2333)
                .distanceDP(66)
                .loadTextSizeSP(18)
                .acceleration(2.3f)
                .loadTextColorID(getResources().getColor(R.color.colorText))
                .build();
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shapeLoadingDialog.show();
            }
        });
    }
}
```
