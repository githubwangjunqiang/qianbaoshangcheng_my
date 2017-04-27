package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.SpannerAdapterShop;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.ShopLeixing;
import com.yunyouzhiyuan.qianbaoshangcheng.model.FabuModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.SquareImageView;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.DiaLogaddImage;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.MyAnimtion;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.PhotoTask;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SDisTrue;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodFabuActivity extends BaseActivity {
    @Bind(R.id.top_tvtitle)
    TextView topTvtitle;
    @Bind(R.id.top_tvtitle_ri)
    TextView topTvtitleRi;
    @Bind(R.id.food_etname)
    EditText foodEtname;
    @Bind(R.id.food_etinfo)
    EditText foodEtinfo;
    @Bind(R.id.food_sp0)
    Spinner foodSp0;
    @Bind(R.id.food_sp1)
    Spinner foodSp1;
    @Bind(R.id.food_sp2)
    Spinner foodSp2;
    @Bind(R.id.food_sp3)
    EditText etBiaoqian;
    @Bind(R.id.food_etprice)
    EditText foodEtprice;
    @Bind(R.id.food_etoldprice)
    EditText foodEtoldprice;
    @Bind(R.id.food_etyongjin)
    EditText foodEtyongjin;
    @Bind(R.id.food_etnumber)
    EditText foodEtnumber;
    @Bind(R.id.food_ivimage)
    SquareImageView foodIvimage;
    @Bind(R.id.food_btnok)
    Button foodBtnok;
    @Bind(R.id.fabushop_layout)
    PullRefreshLayout refreshLayout;
    private SpannerAdapterShop spannerAdapterShop, spannerAdapterShop1, spannerAdapterShop2;
    private DiaLogaddImage dialogaddimage;//上传照片对话框
    private File file = SDisTrue.hasSdcard() ? new File(Environment.getExternalStorageDirectory(), "qianbaoshangcheng_image.jpg") : null;
    private ByteArrayOutputStream baos;
    private ByteArrayInputStream is;
    private String paths1;//图片地址
    private FabuModel model;
    private List<ShopLeixing.DataBean> list = new ArrayList<>();
    private List<ShopLeixing.DataBean> list1 = new ArrayList<>();
    private List<ShopLeixing.DataBean> list2 = new ArrayList<>();
    private String cat_id1, cat_id2, cat_id3;
    private PhotoTask data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoping_fabu);
        ButterKnife.bind(this);
        init();
        setListener();
        getData(0, "0");
    }

    /**
     * 获取分类
     */
    private void getData(final int position, String id) {
        loodingShow();
        model.getScategory(id, SpService.getSP().getStorId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                switch (position) {
                    case 0:
                        list.clear();
                        list.addAll((List<ShopLeixing.DataBean>) object);
                        setSp0();
                        break;
                    case 1:
                        list1.clear();
                        list1.addAll((List<ShopLeixing.DataBean>) object);
                        setSp1();
                        break;
                    case 2:
                        list2.clear();
                        list2.addAll((List<ShopLeixing.DataBean>) object);
                        setSp2();
                        break;
                }
                refreshLayout.setRefreshing(false);
                dismissLooding();
            }

            @Override
            public void onError(Object object) {
                switch (position) {
                    case 0:
                        list.clear();
                        list1.clear();
                        list2.clear();
                        setSp0();
                        setSp1();
                        setSp2();
                        cat_id1 = null;
                        cat_id2 = null;
                        cat_id3 = null;
                        break;
                    case 1:
                        list1.clear();
                        setSp1();
                        list2.clear();
                        setSp2();
                        cat_id2 = null;
                        cat_id3 = null;
                        break;
                    case 2:
                        list2.clear();
                        setSp2();
                        cat_id3 = null;
                        break;
                }
                Too.oo(object);
                refreshLayout.setRefreshing(false);
                dismissLooding();
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }

    /**
     * 监听器
     */
    private void setListener() {
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(0, "0");
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        topTvtitle.setText(R.string.fabu);
        dialogaddimage = new DiaLogaddImage(this, new DiaLogaddImage.CallBack() {
            @Override
            public void callBack(int type) {
                if (type == 0) {     //照相
                    takePhotos();
                } else {             //相册选择
                    gallery();
                }
            }
        });
        model = new FabuModel();
        list.add(new ShopLeixing.DataBean("请选择分类"));
        list1.add(new ShopLeixing.DataBean("请选择分类"));
        list2.add(new ShopLeixing.DataBean("请选择分类"));
        setSp0();
        setSp1();
        setSp2();
    }

    /**
     * 设置第一个商品分类
     */
    private void setSp0() {
        spannerAdapterShop = new SpannerAdapterShop(this, list);
        foodSp0.setAdapter(spannerAdapterShop);
        foodSp0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).getId() != null) {
                    cat_id1 = list.get(position).getId();
                    cat_id2 = null;
                    cat_id3 = null;
                    getData(1, cat_id1);
                    LogUtils.d("foodSp0=" + cat_id1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Too.oo("onNothingSelected");
            }
        });
    }

    /**
     * 设置第二个商品分类
     */
    private void setSp1() {
        spannerAdapterShop1 = new SpannerAdapterShop(this, list1);
        foodSp1.setAdapter(spannerAdapterShop1);
        foodSp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (list1.get(position).getId() != null) {
                    cat_id2 = list1.get(position).getId();
                    cat_id3 = null;
                    getData(2, cat_id2);
                    LogUtils.d("foodSp1=" + cat_id2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Too.oo("onNothingSelected");
            }
        });

    }

    /**
     * 设置第三个商品分类
     */
    private void setSp2() {
        spannerAdapterShop2 = new SpannerAdapterShop(this, list2);
        foodSp2.setAdapter(spannerAdapterShop2);
        foodSp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (list2.get(position).getId() != null) {
                    cat_id3 = list2.get(position).getId();
                    LogUtils.d("foodSp2=" + cat_id3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Too.oo("onNothingSelected");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {//照相选择返回
            if (file.length() != 0) {
                toOutImage(Uri.fromFile(file));
            } else {
                Too.oo(R.string.xuanzezhaopian);
            }
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {  //系统相册返回
            if (data != null) {
                Uri uri = data.getData();
                toOutImage(uri);
            } else {
                Too.oo(R.string.tukushibai);
            }
        }
    }

    /**
     * 上传图片
     */
    private void toOutImage(final Uri uri) {
        data = new PhotoTask(MyApplication.getContext(), HttpUrl.ADDIMAGE, new PhotoTask.PhotoCallback() {
            @Override
            public void onPreExecute() {
                loodingShow();
                x.image().bind(foodIvimage, uri.getPath());
            }

            @Override
            public void onSuccess(String s) {
                if (GetJsonRetcode.getRetcode(s) == 2000) {
                    paths1 = (GetJsonRetcode.getname("data", s));
                } else {
                    Too.oo(GetJsonRetcode.getmsg(s));
                }
                dismissLooding();
            }

            @Override
            public void onError() {
                dismissLooding();
                foodIvimage.setImageResource(R.drawable.t2);
            }
        });
        data.execute(uri);
    }

    /**
     * 调用摄像头拍照
     */
    private void takePhotos() {
        // 判断存储卡是否可以用，可用进行存储
        if (file != null) {
            file.delete();
            file = new File(Environment.getExternalStorageDirectory(), "qianbaoshangcheng_image.jpg");
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(file));
            startActivityForResult(intent, 2);
            overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
        } else {
            Too.oo("请检查内存卡是否存在");
        }
    }

    /**
     * 调用相册
     */
    public void gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 3);
        overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
    }

    @Override
    protected void onDestroy() {
        if (file != null) {
            file.delete();
        }
        if (data != null && !data.isCancelled()) {
            data.cancel(true);
        }
        data = null;
        super.onDestroy();
    }

    @OnClick({R.id.top_ivback, R.id.food_ivimage, R.id.food_btnok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_ivback://返回
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
                break;
            case R.id.food_ivimage://添加照片
                if (dialogaddimage != null) {
                    dialogaddimage.show();
                }
                break;
            case R.id.food_btnok://提交
                tofabu();
                break;
        }
    }

    /**
     * 发布商品
     */
    private void tofabu() {
        String goodname = foodEtname.getText().toString().trim();
        String goodcontent = foodEtinfo.getText().toString().trim();
        String biaoqian = etBiaoqian.getText().toString().trim();
        String price = foodEtprice.getText().toString().trim();
        String makerprice = foodEtoldprice.getText().toString().trim();
        String crprice = foodEtyongjin.getText().toString().trim();
        String count = foodEtnumber.getText().toString().trim();


        if (TextUtils.isEmpty(cat_id1)) {
            Too.oo("请选择分类");
            MyAnimtion.getAnimator_DX(foodSp0).start();
            return;
        }
        if (TextUtils.isEmpty(cat_id2)) {
            Too.oo("请选择分类");
            MyAnimtion.getAnimator_DX(foodSp1).start();
            return;
        }
        if (TextUtils.isEmpty(cat_id3)) {
            Too.oo("请选择分类");
            MyAnimtion.getAnimator_DX(foodSp2).start();
            return;
        }
        if (TextUtils.isEmpty(goodname)) {
            Too.oo("请填写名字");
            MyAnimtion.getAnimator_DX(foodEtname).start();
            return;
        }
        if (TextUtils.isEmpty(goodcontent)) {
            Too.oo("请填写详情");
            MyAnimtion.getAnimator_DX(foodEtinfo).start();
            return;
        }
        if (TextUtils.isEmpty(biaoqian)) {
            Too.oo("请填写标签");
            MyAnimtion.getAnimator_DX(etBiaoqian).start();
            return;
        }
        if (TextUtils.isEmpty(price)) {
            Too.oo("请选择价格");
            MyAnimtion.getAnimator_DX(foodEtprice).start();
            return;
        }
        if (TextUtils.isEmpty(makerprice)) {
            Too.oo("请填写市场价");
            MyAnimtion.getAnimator_DX(foodEtoldprice).start();
            return;
        }
        if (TextUtils.isEmpty(crprice)) {
            Too.oo("请填写佣金");
            MyAnimtion.getAnimator_DX(foodEtyongjin).start();
            return;
        }
        if (TextUtils.isEmpty(count)) {
            Too.oo("请填写数量");
            MyAnimtion.getAnimator_DX(foodEtnumber).start();
            return;
        }
        if (TextUtils.isEmpty(paths1)) {
            Too.oo("请选择图片");
            MyAnimtion.getAnimator_DX(foodIvimage).start();
            return;
        }
        loodingShow();
        model.toFabuShop(SpService.getSP().getStorId(), goodname, goodcontent, cat_id1, cat_id2,
                cat_id3, biaoqian, price, makerprice, crprice, count, paths1, null,
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object object) {
                        Too.oo(object);
                        dismissLooding();
                        if (SpService.getSP().getScid().equals("1")) {//如果是美食
                            Intent intent = new Intent(FoodFabuActivity.this, HuoDongTuanGouActivity.class);
                            intent.putExtra("istuangou", true);
                            startActivity(intent);
                        }
                        finish();
                    }

                    @Override
                    public void onError(Object object) {
                        Too.oo(object);
                        dismissLooding();
                    }

                    @Override
                    public void onFailure(String string) {
                        Too.oo(string);
                        dismissLooding();
                    }
                });

    }
}
