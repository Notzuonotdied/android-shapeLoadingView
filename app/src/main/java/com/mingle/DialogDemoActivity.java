package com.mingle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mingle.widget.ShapeLoadingDialog;

public class DialogDemoActivity extends AppCompatActivity {

    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_demo);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(this)
                .loadText("加载中...")
                .delayMS(2333)
                .distanceDP(28)
                .loadTextSizeSP(18)
                .acceleration(1.3f)
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
