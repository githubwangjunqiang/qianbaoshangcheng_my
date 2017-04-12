package com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.KTVModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class Dialog_addfangxing extends Dialog {
    private TextView tvok, tvover;
    private EditText etName;
    private Callback callback;
    private KTVModel model;
    private String spec_id = "";
    private LoadingDialog loadingDialog;


    public interface Callback {
        void onClick(String name, String id);

        void onClick();
    }

    @Override
    public void dismiss() {
        if (callback != null) {
            callback.onClick();
        }
        super.dismiss();
    }

    public Dialog_addfangxing(Context context, Callback callback, KTVModel model, String spec_id) {
        super(context, R.style.Dialog);
        this.callback = callback;
        this.model = model == null ? new KTVModel() : model;
        this.spec_id = spec_id;
        loadingDialog = new LoadingDialog(getContext());
        setContentView(R.layout.dialog_addfangxing);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        tvok = (TextView) findViewById(R.id.dialog_addfangxing_tvok);
        tvover = (TextView) findViewById(R.id.dialog_addfangxing_tvover);
        etName = (EditText) findViewById(R.id.dialog_addfangxing_etcontent);
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
                addSpecItem(trim);
            }
        });
        tvover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 29、商家发布商品时添加的规格
     */
    private void addSpecItem(final String string) {
        loadingDialog.show();
        model.addSpecItem(SpService.getSP().getStorId(), spec_id, string, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                if (callback != null) {
                    callback.onClick(string, (String) object);
                }
                loadingDialog.dismiss();
                dismiss();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
            }
        });
    }
}
