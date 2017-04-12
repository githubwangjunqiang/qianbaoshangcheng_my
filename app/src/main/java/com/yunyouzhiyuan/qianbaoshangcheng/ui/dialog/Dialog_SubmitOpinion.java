package com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.MyModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import okhttp3.Call;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class Dialog_SubmitOpinion extends BaseDiaLog {
    private TextView tvok, tvover;
    private EditText etContent;
    private MyModel model;
    private LinearLayout llTop;
    private LoadingDialog loadingDialog;
    private Call call;


    @Override
    public void dismiss() {
        loadingDialog = null;
        hintKbTwo();
        super.dismiss();
    }

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    public Dialog_SubmitOpinion(Context context) {
        super(context, R.style.Dialog);
        this.model = new MyModel();
        loadingDialog = new LoadingDialog(getContext());
        setContentView(R.layout.dialog_submitopinion);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogWindowAnim);
    }

    @Override
    public View setView() {
        return llTop;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        tvok = (TextView) findViewById(R.id.dialog_submit_opinion_btnok);
        tvover = (TextView) findViewById(R.id.dialog_submit_opinion_btnover);
        etContent = (EditText) findViewById(R.id.dialog_submit_opinion_etcontent);
        llTop = (LinearLayout) findViewById(R.id.dialog_submitopinion_layout);
        setlistener();
    }

    private void setlistener() {
        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = etContent.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    Too.oo("您未填写任何数据");
                    return;
                }
                submit(trim);
            }
        });
        tvover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        loadingDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (call != null && !call.isCanceled()) {
                    call.cancel();
                }
                return false;
            }
        });
    }

    /**
     * 29、商家发布商品时添加的菜系
     */
    private void submit(final String string) {
        loadingDialog.show();

        call = model.feed_back(SpService.getSP().getStorId(), string, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                Too.oo(object);
                loadingDialog.dismiss();
                dismiss();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
                loadingDialog.dismiss();
            }
        });
    }
}
