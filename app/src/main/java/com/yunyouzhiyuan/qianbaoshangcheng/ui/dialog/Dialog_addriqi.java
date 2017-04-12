package com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.KTVModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class Dialog_addriqi extends Dialog {
    @Bind(R.id.dialog_addriqi_c0)
    RadioButton dialogAddriqiC0;
    @Bind(R.id.dialog_addriqi_c1)
    RadioButton dialogAddriqiC1;
    @Bind(R.id.dialog_addriqi_c2)
    RadioButton dialogAddriqiC2;
    @Bind(R.id.dialog_addriqi_c3)
    RadioButton dialogAddriqiC3;
    @Bind(R.id.dialog_addriqi_c4)
    RadioButton dialogAddriqiC4;
    @Bind(R.id.dialog_addriqi_c5)
    RadioButton dialogAddriqiC5;
    @Bind(R.id.dialog_addriqi_c6)
    RadioButton dialogAddriqiC6;
    @Bind(R.id.dialog_addriqi_rg)
    RadioGroup dialogAddriqiRg;
    private Callback callback;
    private KTVModel model;
    private String spec_id = "";
    private LoadingDialog loadingDialog;
    private List<Call> list = new ArrayList<>();
    private List<RadioButton> listBotton = new ArrayList<>();

    @Override
    public void dismiss() {
        loadingDialog = null;
        spec_id = null;
        model = null;
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null && !list.get(i).isCanceled()) {
                    list.get(i).cancel();
                }
            }
        }
        list.clear();
        list = null;
        ButterKnife.unbind(this);
        super.dismiss();
    }


    @OnClick({R.id.dialog_addriqi_tvok, R.id.dialog_addriqi_tvover})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_addriqi_tvok://提交
                addSpecItem();
                break;
            case R.id.dialog_addriqi_tvover://取消
                dismiss();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addriqi);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).isCanceled()) {
                        list.get(i).cancel();
                    }
                }
                return false;
            }
        });
        listBotton.add(dialogAddriqiC0);
        listBotton.add(dialogAddriqiC1);
        listBotton.add(dialogAddriqiC2);
        listBotton.add(dialogAddriqiC3);
        listBotton.add(dialogAddriqiC4);
        listBotton.add(dialogAddriqiC5);
        listBotton.add(dialogAddriqiC6);
    }

    public interface Callback {
        void onClick(String name, String id);
    }

    public Dialog_addriqi(Context context, Callback callback, KTVModel model, String spec_id) {
        super(context, R.style.Dialog_addtime);
        this.callback = callback;
        this.model = model == null ? new KTVModel() : model;
        this.spec_id = spec_id;
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


    /**
     * 29、商家发布商品时添加的规格
     */
    private void addSpecItem() {
        loadingDialog.show();
        addSpitne();
    }


    /**
     * 填加标签
     */
    private void addSpitne() {
        String name = null;
        for (int i = 0; i < listBotton.size(); i++) {
            if (listBotton.get(i).isChecked()) {
                name = "" + i;
                break;
            }
        }
        if (TextUtils.isEmpty(name)) {
            Too.oo("请选择");
            return;
        }
        loadingDialog.show();
        final String finalName = name;
        Call call = model.addSpecItem(SpService.getSP().getStorId(), spec_id, name, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                Too.oo("成功");
                if (callback != null) {
                    callback.onClick(finalName, (String) object);
                }
                loadingDialog.dismiss();
                dismiss();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                loadingDialog.dismiss();
                dismiss();
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
                loadingDialog.dismiss();
                dismiss();

            }
        });
        list.add(call);

    }
}
