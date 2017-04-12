package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.MingxiAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.BankInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Mingxi;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.withdrawModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.swlayout.SwipyRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.swlayout.SwipyRefreshLayoutDirection;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TongjiActivity extends BaseActivity {

    @Bind(R.id.tongji_editText)
    EditText etDetails;
    @Bind(R.id.top_tvtitle)
    TextView topTvtitle;
    @Bind(R.id.tongji_tvprice)
    TextView tongjiTvprice;
    @Bind(R.id.tongji_btntixian)
    TextView tongjiBtntixian;
    @Bind(R.id.tongji_tvbank)
    TextView tvBankNumber;
    @Bind(R.id.tongji_listview)
    ListView listView;
    @Bind(R.id.tongji_layout)
    SwipyRefreshLayout tongjiLayout;
    @Bind(R.id.tongji_tvusername)
    TextView tvUsername;
    @Bind(R.id.tongji_tvbankname)
    TextView tvbankName;
    private withdrawModel model;
    private BankInfo info;
    private int page = 0;
    private List<Mingxi.DataBean> beanList;
    private MingxiAdapter adapter;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongji);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            page = savedInstanceState.getInt("page", 0);
        }
        init();
        setListener();
        tongjiLayout.setRefreshing(true);
        getView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("page", page);
    }

    /**
     * 获取网络数据
     */
    private void getView() {
        getData();
    }

    /**
     * 监听器
     */
    private void setListener() {
        tongjiLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    getView();
                } else {
                    loadDetails(false);
                }
            }
        });
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    clearCalls();
                }
                return false;
            }
        });
    }

    /**
     * 获取余额和银行卡信息
     */
    private void getData() {
        if (model == null) {
            tongjiLayout.setRefreshing(false);
            return;
        }
        setCalls(model.getData(SpService.getSP().getStorId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                if (isFinishing()) {
                    return;
                }
                info = (BankInfo) object;
                setYueView();
                loadDetails(true);
            }

            @Override
            public void onError(Object object) {
                if (isFinishing()) {
                    return;
                }
                Too.oo(object);
                tongjiLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String string) {
                if (isFinishing()) {
                    return;
                }
                Too.oo(string);
                tongjiLayout.setRefreshing(false);
            }
        }));
    }

    /**
     * 获取明细
     */
    private void loadDetails(boolean isTop) {
        if (model == null) {
            tongjiLayout.setRefreshing(false);
            return;
        }
        if (isTop) {
            page = 0;
        }
        setCalls(model.getDetails(SpService.getSP().getStorId(), page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                if (isFinishing()) {
                    return;
                }
                if (page == 0) {
                    beanList.clear();
                }
                beanList.addAll((List<Mingxi.DataBean>) object);
                setAdapter();
                tongjiLayout.setRefreshing(false);
                page++;
            }

            @Override
            public void onError(Object object) {
                if (isFinishing()) {
                    return;
                }
                if (page == 0) {
                    beanList.clear();
                    setAdapter();
                }
                Too.oo(object);
                tongjiLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String string) {
                if (isFinishing()) {
                    return;
                }
                if (page == 0) {
                    beanList.clear();
                    setAdapter();
                }

                Too.oo(string);
                tongjiLayout.setRefreshing(false);
            }
        }));
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new MingxiAdapter(this, beanList);
            listView.setAdapter(adapter);
        }
    }

    /**
     * 写入余额和银行卡信息
     */
    private void setYueView() {
        if (info == null) {
            return;
        }
        tongjiTvprice.setText("￥" + info.getData().getStore_money());
        tvbankName.setText("\t" + info.getData().getAccount_name());
        tvUsername.setText("\t" + info.getData().getBank_name());
        tvBankNumber.setText("\t" + info.getData().getAccount_bank());
    }

    /**
     * 初始化
     */
    private void init() {
        beanList = new ArrayList<>();
        model = new withdrawModel();
        topTvtitle.setText(R.string.jinetongji);
        tongjiLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        tongjiLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.white));
        tongjiLayout.setProgressBackgroundColor(R.color.app_color);
        loadingDialog = new LoadingDialog(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.top_ivback, R.id.tongji_btntixian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_ivback:
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
                break;
            case R.id.tongji_btntixian://提现
                Details();
                hintKbTwo();
                break;
        }
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
     * 提现
     */
    private void Details() {

        if (info == null) {
            return;
        }
        String trim = etDetails.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            return;
        }
        tongjiLayout.setRefreshing(true);
        loadingDialog.show();
        setCalls(model.loadDetails(SpService.getSP().getStorId(), trim, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                if (isFinishing()) {
                    return;
                }
                Too.oo(object);
                getView();
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object object) {
                if (isFinishing()) {
                    return;
                }
                Too.oo(object);
                tongjiLayout.setRefreshing(false);
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(String string) {
                if (isFinishing()) {
                    return;
                }
                tongjiLayout.setRefreshing(false);
                Too.oo(string);
                loadingDialog.dismiss();
            }
        }));
    }

}
