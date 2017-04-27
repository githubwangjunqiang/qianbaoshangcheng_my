package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.ShopingGuanliAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.StorList;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.StorGuanliModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.xlistview.XListView;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class ShoppingGuanliActivity extends BaseActivity {
    @ViewInject(value = R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.activity_shoppimng_guanli_tv)
    private TextView tvsousuo;
    @ViewInject(value = R.id.top_ivback)
    private ImageView ivback;
    @ViewInject(value = R.id.activity_shoppimng_guanli_et)
    private EditText etsousuo;
    @ViewInject(value = R.id.activity_shoppimng_guanli_listview)
    private XListView listView;
    private ShopingGuanliAdapter adapter;
    private List<StorList.DataBean> list = new ArrayList<>();
    private StorGuanliModel model;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_guanli);
        x.view().inject(this);
        init();
        setListener();
        setadapter();
        listView.startRefresh();
    }

    /**
     * 适配器
     */
    private void setadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new ShopingGuanliAdapter(this, list, new ShopingGuanliAdapter.CallBack() {
                @Override
                public void onClick(int postion) {
                    toxiaji(postion);
                }
            });
            listView.setAdapter(adapter);
        }
    }

    /**
     * 商品上下架
     *
     * @param position
     */
    private void toxiaji(final int position) {
        loodingShow();
        model.tounStorStas(list.get(position).getGoods_id(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                String s = list.get(position).getIs_on_sale();
                list.get(position).setIs_on_sale(TextUtils.equals("1", s) ? "0" : "1");
                setadapter();
                Too.oo(object);
                dismissLooding();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                dismissLooding();
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
            }
        });
    }

    /**
     * 监听i
     */
    private void setListener() {
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                getdate(true);
            }

            @Override
            public void onLoadMore() {
                getdate(false);
            }
        });
        etsousuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean isOK = true;
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        tosousuo();
                        hintKbTwo();
                        if (tvsousuo.getVisibility() == View.VISIBLE) {
                            showOrhideSousuo(false);
                        }
                        break;
                    default:
                        isOK = false;
                        break;
                }
                return isOK;
            }
        });
        etsousuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (tvsousuo.getVisibility() == View.GONE) {
                        showOrhideSousuo(true);
                    }
                } else {
                    if (tvsousuo.getVisibility() == View.VISIBLE) {
                        showOrhideSousuo(false);
                    }
                }
            }
        });
        tvsousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tosousuo();
                hintKbTwo();
                showOrhideSousuo(false);
            }
        });
    }

    /**
     * 显示或者隐藏 搜索按钮
     */
    private void showOrhideSousuo(boolean show) {
        TranslateAnimation animation = null;
        if (show) {
            tvsousuo.setVisibility(View.VISIBLE);
            animation = new TranslateAnimation(tvsousuo.getWidth() * 2, 0, 0, 0);
            animation.setDuration(500);
            tvsousuo.setAnimation(animation);
            animation.start();
        } else {
            animation = new TranslateAnimation(0, tvsousuo.getWidth() * 2, 0, 0);
            animation.setDuration(500);
            tvsousuo.setAnimation(animation);
            animation.start();
            tvsousuo.setVisibility(View.GONE);
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
     * 搜岁哦
     *
     * @return
     */
    private void tosousuo() {
        String text = etsousuo.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            Too.oo("您没有输入文字");
            return;
        }
        loodingShow();
        model.sousuo(text, MyApplication.getUser().getSid(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                list.clear();
                list.addAll((List<StorList.DataBean>) object);
                setadapter();
                dismissLooding();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                dismissLooding();
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }

    /**
     * 获取信息
     */
    private void getdate(final boolean istop) {
        if (istop) {
            page = 0;
        }
        model.getdata(SpService.getSP().getStorId(), page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                page++;
                if (istop) {
                    list.clear();
                }
                list.addAll((List<StorList.DataBean>) object);
                setadapter();
                listView.stopRefresh(true);
                listView.stopLoadMore();
            }

            @Override
            public void onError(Object object) {
                if (istop) {
                    list.clear();
                    setadapter();
                }
                listView.stopRefresh(false);
                listView.stopLoadMore();
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        tvtitle.setText(R.string.shangpinguanli);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
            }
        });
        model = new StorGuanliModel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
        }
        return super.onKeyDown(keyCode, event);

    }
}
