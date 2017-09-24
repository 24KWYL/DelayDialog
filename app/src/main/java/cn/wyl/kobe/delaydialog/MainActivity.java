package cn.wyl.kobe.delaydialog;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new DelayDialog(this);
        findViewById(R.id.tv1).setOnClickListener(this);
        findViewById(R.id.tv2).setOnClickListener(this);
        findViewById(R.id.tv3).setOnClickListener(this);
        findViewById(R.id.tv4).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                dialog.show();
                start(400);
                break;
            case R.id.tv2:
                dialog.show();
                start(1000);
                break;
            case R.id.tv3:
                dialog.show();
                start(2000);
                break;
            case R.id.tv4:
                dialog.show();
                start(10000);
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void start(final long time) {
        findViewById(R.id.tv1).postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },time);
    }
}
