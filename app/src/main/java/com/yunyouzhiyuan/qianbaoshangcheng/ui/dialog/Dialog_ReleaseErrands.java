package com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.model.DingDanModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.StringReplaceUtil;

import okhttp3.Call;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class Dialog_ReleaseErrands extends BaseDiaLog {
    private TextView tvok, tvover, tvPhone, tvPrice;
    private EditText etContent;
    private Callback callback;
    private DingDanModel model;
    private LinearLayout llTop;
    private LoadingDialog loadingDialog;
    private Call call;
    private TextView tvtitle;
    private String userPhone, price;


    public interface Callback {
        void onSuccess();
    }

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

    private String order_id;

    public Dialog_ReleaseErrands(Context context, Callback callback, DingDanModel model, String stringPhone, String price, String order_id) {
        super(context, R.style.Dialog);
        this.callback = callback;
        this.model = model == null ? new DingDanModel() : model;
        this.userPhone = stringPhone;
        this.price = price;
        this.order_id = order_id;
        loadingDialog = new LoadingDialog(getContext());
        setContentView(R.layout.dialog_releaseerrands);
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
        tvPhone = (TextView) findViewById(R.id.release_tvphone);
        tvPrice = (TextView) findViewById(R.id.release_tvzongjia);
        etContent = (EditText) findViewById(R.id.dialog_addfangxing_etcontent);
        llTop = (LinearLayout) findViewById(R.id.dialog_caixitop);


        tvtitle.setText("发布跑腿");
        tvPhone.setText("顾客电话:"+StringReplaceUtil.idphoneReplaceWithStar(userPhone));
        tvPrice.setText("订单价格：￥：" + price);
        setlistener();
    }

    private void setlistener() {
        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSpecItem();

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
    private void addSpecItem() {
        loadingDialog.show();

        call = model.send_express_order(order_id, SpService.getSP().getStorId(), etContent.getText().toString().trim(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                Too.oo(object);
                if (callback != null) {
                    callback.onSuccess();
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
