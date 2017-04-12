package com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.model.FabuModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import okhttp3.Call;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class Dialog_addCuisine extends BaseDiaLog {
    private TextView tvok, tvover;
    private EditText etName;
    private Callback callback;
    private FabuModel model;
    private LinearLayout llTop;
    private LoadingDialog loadingDialog;
    private Call call;
    private TextView tvtitle;
    private String stringName, cat_id;


    public interface Callback {
        void onClick(String name, String id);

        void onDismiss();
    }

    @Override
    public void dismiss() {
        if (callback != null) {
            callback.onDismiss();
        }
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


    public Dialog_addCuisine(Context context, Callback callback, FabuModel model, String stringName, String cat_id) {
        super(context, R.style.Dialog);
        this.callback = callback;
        this.model = model == null ? new FabuModel() : model;
        this.stringName = stringName;
        this.cat_id = cat_id;
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
        window.setWindowAnimations(R.style.dialogWindowAnim);
    }

    @Override
    public View setView() {
        return llTop;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        tvok = (TextView) findViewById(R.id.dialog_addfangxing_tvok);
        tvtitle = (TextView) findViewById(R.id.tvtitle_dialog_cuisine);
        tvover = (TextView) findViewById(R.id.dialog_addfangxing_tvover);
        etName = (EditText) findViewById(R.id.dialog_addfangxing_etcontent);
        llTop = (LinearLayout) findViewById(R.id.dialog_caixitop);


        tvtitle.setText("添加本店菜系");
        etName.setHint("请输入您要添加的菜系名称");
        if (!TextUtils.isEmpty(cat_id)) {
            etName.setText(stringName);
            etName.setSelection(etName.length());

            tvtitle.setText("编辑本店菜系");
            etName.setHint("请输入您要更改的菜系名称");
        }
        setlistener();
    }

    private void setlistener() {
        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = etName.getText().toString().trim();
                if (!TextUtils.isEmpty(cat_id)) {//是修改
                    if (TextUtils.equals(stringName, trim)) {
                        Too.oo("您没改变任何内容");
                        return;
                    } else {
                        editingSpecItem(trim);
                    }
                } else {
                    if (TextUtils.isEmpty(trim)) {
                        Too.oo("您没输入任何内容");
                        return;
                    } else {
                        addSpecItem(trim);
                    }
                }


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
    private void addSpecItem(final String string) {
        loadingDialog.show();

        call = model.goods_class_save(SpService.getSP().getStorId(), string, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                Too.oo("添加成功");
                if (callback != null) {
                    callback.onClick(string, (String) object);
                }
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

    /**
     * 29、商家发布商品时编辑的菜系
     */
    private void editingSpecItem(final String string) {
        loadingDialog.show();

        call = model.goods_class_editing(SpService.getSP().getStorId(), string, cat_id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                Too.oo("编辑成功");
                if (callback != null) {
                    callback.onClick(string, (String) object);
                }
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
