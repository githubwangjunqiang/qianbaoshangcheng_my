package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.XiaofeiyanzhengAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.VerifiCation;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.XiaoFeiModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.xlistview.XListView;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.MD5Utils;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class XiaofeiYanzhengActivity extends BaseActivity {
    @ViewInject(value = R.id.yanzheng_ivsaoyisao)
    private ImageView ivSao;
    @ViewInject(value = R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.top_ivback)
    private ImageView ivback;
    @ViewInject(value = R.id.activity_xiaofeiyanzheng_et)
    private EditText etsousuo;
    @ViewInject(value = R.id.activity_xiaofeiyanzheng_listview)
    private XListView listView;
    private XiaofeiyanzhengAdapter adapter;
    private XiaoFeiModel model;
    private Call call;
    private int page = 0;
    private List<VerifiCation.DataBean> list;
    private final int toScanning = 202;
    private final int toSearch = 203;
    private LoadingDialog loadingDialog;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("model", model);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaofei_yanzheng);
        if (savedInstanceState != null) {
            model = (XiaoFeiModel) savedInstanceState.getSerializable("model");
        }
        x.view().inject(this);
        init();
        setListener();
        setadapter();
        listView.startRefresh();
        getData(true);
    }

    /**
     * 获取订单数据
     */
    private void getData(final boolean isRefresh) {
        if (isRefresh) {
            page = 0;
        }
        call = model.verify_consum_list(SpService.getSP().getStorId(), page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                if (isRefresh) {
                    list.clear();
                }
                list.addAll((List<VerifiCation.DataBean>) object);
                setadapter();
                page++;
                hideLayout(isRefresh);
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                if (isRefresh) {
                    list.clear();
                    setadapter();
                }
                hideLayout(isRefresh);
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
                if (isRefresh) {
                    list.clear();
                    setadapter();
                }
                hideLayout(isRefresh);
            }
        });
    }

    /**
     * 关闭刷新 和加载
     *
     * @param isRefresh
     */
    private void hideLayout(boolean isRefresh) {
        if (isRefresh) {
            listView.stopRefresh(true);
        } else {
            listView.stopLoadMore();
        }
    }

    /**
     * 适配器
     */
    private void setadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new XiaofeiyanzhengAdapter(this, list, new XiaofeiyanzhengAdapter.Callback() {
                @Override
                public void onScanning(int position) {
                    toScanning(position);
                }
            });
            listView.setAdapter(adapter);
        }
    }

    /**
     * 打开扫描验证界面
     *
     * @param position
     */
    private void toScanning(int position) {
        Intent intent = new Intent(this, CaptureActivity.class);
        intent.putExtra("position", position);
        startActivityForResult(intent, toScanning);
    }

    /**
     * 打开扫描搜索界面
     */
    private void toSearchData() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, toSearch);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (toScanning == requestCode && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            String result = extras.getString("result");
            String plusPassword = extras.getString("strings");
            String order = extras.getString("order");
            int position = extras.getInt("position", -1);
            if (!TextUtils.isEmpty(result) && position != -1 && !TextUtils.isEmpty(plusPassword)) {
                verification(plusPassword, position);
            } else {
                Too.oo("空的二维码");
            }
        }
        if (toSearch == requestCode && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            String result = extras.getString("result");
            String plusPassword = extras.getString("strings");
            String order = extras.getString("order");
            if (!TextUtils.isEmpty(result) && !TextUtils.isEmpty(plusPassword)
                    && !TextUtils.isEmpty(order)) {
                search(order);
            } else {
                Too.oo("空的二维码");
            }
        }
    }

    /**
     * 验证
     *
     * @param result
     * @param position
     */
    private void verification(String result, int position) {
        loadingDialog.show();
        Date date = new Date();
        long time = date.getTime();
        StringBuffer buffer = new StringBuffer();
        List<String> bufferlist = new ArrayList<>();
        bufferlist.add(time + "");
        bufferlist.add(SpService.getSP().getStorId());
        bufferlist.add("verify_consum_order");
        Collections.sort(bufferlist);
        for (int i = 0; i < bufferlist.size(); i++) {
            buffer.append(bufferlist.get(i));
        }
        String token = MD5Utils.md5Code(buffer.toString());
        call = model.verify_consum_order(SpService.getSP().getStorId(), list.get(position).getOrder_id()
                , list.get(position).getMaster_order_sn(), result, list.get(position).getMobile(),
                time + "", token, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object object) {
                        success(object, true);
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Object object) {
                        success(object, false);
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onFailure(String string) {
                        success(string, false);
                        loadingDialog.dismiss();
                    }
                });
    }

    /**
     * 验证成功
     *
     * @param object
     */
    private void success(Object object, final boolean isSuccess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("黔宝商城提醒您");
        builder.setMessage(object.toString());
        builder.setCancelable(true);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isSuccess) {
                    getData(true);
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    /**
     * 监听i
     */
    private void setListener() {
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                getData(true);
            }

            @Override
            public void onLoadMore() {
                getData(false);
            }
        });

        etsousuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        search(null);
                        break;
                }
                return false;
            }
        });
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (call != null && !call.isCanceled()) {
                        call.cancel();
                    }
                }
                return false;
            }
        });
        ivSao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchData();
            }
        });
    }

    /**
     * 搜索数据
     */
    private void search(String string) {
        String text = null;
        if (TextUtils.isEmpty(string)) {
            text = etsousuo.getText().toString().trim();
            hintKbTwo();
        } else {
            text = string;
        }
        if (TextUtils.isEmpty(text)) {
            Too.oo("您没有输入文字");
            return;
        }
        loadingDialog.show();
        call = model.search_verify_consum(SpService.getSP().getStorId(), text, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                list.clear();
                list.addAll((List<VerifiCation.DataBean>) object);
                setadapter();
                loadingDialog.dismiss();
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

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 初始化
     */
    private void init() {
        list = new ArrayList<>();
        model = new XiaoFeiModel();
        tvtitle.setText(R.string.xiaofeiyanzheng);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * 点击搜素按钮
     *
     * @param view
     */
    public void tosearch(View view) {
        search(null);
    }
}
