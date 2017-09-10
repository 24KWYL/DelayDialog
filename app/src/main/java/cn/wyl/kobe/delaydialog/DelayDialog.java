package cn.wyl.kobe.delaydialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * 自定义加载对话框，实现延时显示，短时间不显示
 * create by 24k
 */
public class DelayDialog extends Dialog{
    private static final int DELAY_HIDE = 500; // 不显示时间
    private static final int DELAY_SHOW = 1000; // 最短显示时间

    private static final int DIALOG_DISMISS = 1; // dissmiss对话框
    private static final int DIALOG_SHOW = 2; // 延时显示Dialog
    private static final int DIALOG_DISMISS_DELAY = 3; // 延时1000毫秒后,如果系统调用系统的dismiss方法，则消失dialog，不然不操作
    private Context mContext;
    private Handler myHandler;
    private Activity mParantActivity;
    private LinearLayout layoutProgressBar;
    private ProgressBar circleProgressBar;

    public DelayDialog(Context context) {
        super(context);
        mContext = context;
        mParantActivity = (Activity)context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        View root = LayoutInflater.from(context).inflate(R.layout.dialog_delay_progress, null, false);
        layoutProgressBar = (LinearLayout)root.findViewById(R.id.ll);
        circleProgressBar = (ProgressBar)root.findViewById(R.id.progressBar);
        setContentView(root);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        myHandler = new MyHandler();
    }

    @Override
    public void show() {
        if(!isShowing()){
            myHandler.sendEmptyMessageDelayed(DIALOG_SHOW, DELAY_HIDE);
        }
    }

    @Override
    public void dismiss() {
        myHandler.sendEmptyMessage(DIALOG_DISMISS);
    }

    public void dismissReally() {
        try {
            if(mParantActivity != null  && !mParantActivity.isFinishing()){
                myHandler.removeCallbacksAndMessages(null);
                super.dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DIALOG_DISMISS:
                    if(myHandler.hasMessages(DIALOG_SHOW)) {
                        dismissReally();
                    } else if(myHandler.hasMessages(DIALOG_DISMISS_DELAY)) {
                        myHandler.sendEmptyMessageDelayed(DIALOG_DISMISS, Integer.MAX_VALUE);
                    } else {
                        dismissReally();
                    }
                    break;
                case DIALOG_SHOW:
                    showReallyDialog();
                    myHandler.sendEmptyMessageDelayed(DIALOG_DISMISS_DELAY, DELAY_SHOW);
                    break;
                case DIALOG_DISMISS_DELAY:
                    if(myHandler.hasMessages(DIALOG_DISMISS)) {
                        dismissReally();
                    }
                    break;
            }
        }
    }

    private void showReallyDialog(){
        try {
            super.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
