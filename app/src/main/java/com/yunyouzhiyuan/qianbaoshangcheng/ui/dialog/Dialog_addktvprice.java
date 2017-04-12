package com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class Dialog_addktvprice extends BaseDiaLog {
    private TextView tvok, tvover;
    private EditText etName;
    private Callback callback;
    private String titleHint;


    public interface Callback {
        void onClick(String name);

        void onClickDismiss();
    }

    @Override
    public void dismiss() {
        if (callback != null) {
            callback.onClickDismiss();
        }
        super.dismiss();
    }

    public Dialog_addktvprice(Context context, Callback callback, String titleHint) {
        super(context, R.style.dialogWindowAnim);
        this.callback = callback;
        this.titleHint = titleHint;
        setContentView(R.layout.dialog_addktvprice);
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
        etName.setHint(titleHint);
    }

    @Override
    public View setView() {

        return findViewById(R.id.lltop_dialog);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        tvok = (TextView) findViewById(R.id.dialog_addktvpricetvok);
        tvover = (TextView) findViewById(R.id.dialog_addktvprice_tvover);
        etName = (EditText) findViewById(R.id.dialog_addktvprice_etcontent);
        setlistener();
    }

    private void setlistener() {
        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = etName.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    Too.oo("您没输入任何内容");
                    return;
                }
                if (callback != null) {
                    callback.onClick(trim);
                    dismiss();
                }
            }
        });
        tvover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
