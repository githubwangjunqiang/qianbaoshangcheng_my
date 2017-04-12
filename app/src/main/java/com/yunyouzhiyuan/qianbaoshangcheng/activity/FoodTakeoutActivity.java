package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.FlowAdapterFood;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.SpannerAdapterShop;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.FoodCuisine;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.ShopLeixing;
import com.yunyouzhiyuan.qianbaoshangcheng.model.FabuModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.SquareImageView;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.DiaLogaddImage;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.Dialog_addCuisine;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetBitmap;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.MyAnimtion;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.PhotoUploadTask;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SDisTrue;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.xutils.common.task.AbsTask;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodTakeoutActivity extends BaseActivity {

    @Bind(R.id.pullrefreshLayout)
    PullRefreshLayout pullRefreshLayout;
    @Bind(R.id.top_tvtitle)
    TextView topTvtitle;
    @Bind(R.id.Takeaway_etname)
    EditText TakeawayEtname;
    @Bind(R.id.Takeaway_etinfo)
    EditText TakeawayEtinfo;
    @Bind(R.id.Takeaway_sp0)
    Spinner TakeawaySp0;
    @Bind(R.id.Takeaway_sp1)
    Spinner TakeawaySp1;
    @Bind(R.id.Takeaway_sp2)
    Spinner TakeawaySp2;
    @Bind(R.id.Takeaway_etbiaoqian)
    EditText TakeawayEtbiaoqian;
    @Bind(R.id.Takeaway_etprice)
    EditText TakeawayEtprice;
    @Bind(R.id.Takeaway_etoldprice)
    EditText TakeawayEtoldprice;
    @Bind(R.id.Takeaway_etyongjin)
    EditText TakeawayEtyongjin;
    @Bind(R.id.Takeaway_etnumber)
    EditText TakeawayEtnumber;
    @Bind(R.id.food_ivimage)
    SquareImageView foodIvimage;
    @Bind(R.id.flowlayout_btnaddcaixi)
    Button flowlayoutBtnaddcaixi;
    @Bind(R.id.flowlayout_btnediting)
    Button flowlayoutBtnediting;
    @Bind(R.id.flowlayout_btndelete)
    Button flowlayoutBtndelete;
    @Bind(R.id.flowlayout_food)
    TagFlowLayout flowlayoutFood;
    private SpannerAdapterShop spannerAdapterShop, spannerAdapterShop1, spannerAdapterShop2;
    private DiaLogaddImage dialogaddimage;//上传照片对话框
    private File file = SDisTrue.hasSdcard() ? new File(Environment.getExternalStorageDirectory(), "qianbaoshangcheng_image.jpg") : null;
    private ByteArrayOutputStream baos;
    private ByteArrayInputStream is;
    private String paths1;//图片地址
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 152) {
                String s = (String) msg.obj;
                if (GetJsonRetcode.getRetcode(s) == 2000) {
                    paths1 = (GetJsonRetcode.getname("data", s));
                } else {
                    Too.oo(GetJsonRetcode.getmsg(s));
                }
                dismissLooding();
            }
        }
    };
    private FabuModel model;
    private List<ShopLeixing.DataBean> list = new ArrayList<>();
    private List<ShopLeixing.DataBean> list1 = new ArrayList<>();
    private List<ShopLeixing.DataBean> list2 = new ArrayList<>();
    private String cat_id1, cat_id2, cat_id3;
    private FlowAdapterFood flowAdapter;
    private List<FoodCuisine.DataBean> listCuisine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_takeout);
        ButterKnife.bind(this);
        init();
        setListener();
        pullRefreshLayout.setRefreshing(true);
        loadData();

    }

    private void loadData() {
        count = 2;
        loadStoreGoodsClass();
        getData(0, "0");
    }

    private int count = 0;

    private void hideLayout() {
        count--;
        if (count < 1) {
            count = 0;
            pullRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pullRefreshLayout.setRefreshing(false);
                }
            }, 1000);

        }
    }

    /**
     * 获取本店菜系
     */
    private void loadStoreGoodsClass() {
        model.storeGoodsClass(SpService.getSP().getStorId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                listCuisine.clear();
                listCuisine.addAll((List<FoodCuisine.DataBean>) object);
                setFlowAdapterCuisine();
                hideLayout();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                hideLayout();
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
                hideLayout();
            }
        });
    }

    /**
     * 获取分类
     */
    private void getData(final int position, String id) {
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
                hideLayout();
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
                hideLayout();
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
                hideLayout();
            }
        });
    }

    /**
     * 监听器
     */
    private void setListener() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        listCuisine = new ArrayList<>();
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);
        flowlayoutBtnaddcaixi.setSelected(true);
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
        TakeawaySp0.setAdapter(spannerAdapterShop);
        TakeawaySp0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        TakeawaySp1.setAdapter(spannerAdapterShop1);
        TakeawaySp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        TakeawaySp2.setAdapter(spannerAdapterShop2);
        TakeawaySp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        loodingShow();
        x.task().start(new AbsTask<Bitmap>() {
            @Override
            protected Bitmap doBackground() throws Throwable {
                try {
                    Bitmap bitmap = GetBitmap.getBitmapFormUri(FoodTakeoutActivity.this, uri);
                    update(0, bitmap);
                    // 字节数组输出流
                    baos = new ByteArrayOutputStream();
                    // 将Bitmap对象转化为字节数组输出流
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    // 获得字节流
                    is = new ByteArrayInputStream(baos.toByteArray());
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onUpdate(int flag, Object... args) {
                super.onUpdate(flag, args);
                if (flag == 0 && args != null && args.length > 0 && (args[0] instanceof Bitmap)) {
                    Bitmap args1 = (Bitmap) args[0];
                    foodIvimage.setImageBitmap(args1);
                }
            }

            @Override
            protected void onSuccess(Bitmap result) {
                PhotoUploadTask put = new PhotoUploadTask(
                        HttpUrl.ADDIMAGE, is,
                        FoodTakeoutActivity.this, handler);
                put.start();
            }

            @Override
            protected void onError(Throwable ex, boolean isCallbackError) {
                Too.oo(getResources().getString(R.string.shangchuantupianshibai));
                dismissLooding();
            }
        });


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
        if (handler != null) {
            handler = null;
        }
        super.onDestroy();
    }

    /**
     * 发布商品
     */
    private void tofabu() {
        String goodname = TakeawayEtname.getText().toString().trim();
        String goodcontent = TakeawayEtinfo.getText().toString().trim();
        String biaoqian = TakeawayEtbiaoqian.getText().toString().trim();
        String price = TakeawayEtprice.getText().toString().trim();
        String makerprice = TakeawayEtoldprice.getText().toString().trim();
        String crprice = TakeawayEtyongjin.getText().toString().trim();
        String count = TakeawayEtnumber.getText().toString().trim();


        if (TextUtils.isEmpty(cat_id1)) {
            MyAnimtion.getAnimator_DX(TakeawaySp0).start();
            Too.oo("请选择分类");
            return;
        }
        if (TextUtils.isEmpty(cat_id2)) {
            Too.oo("请选择分类");
            MyAnimtion.getAnimator_DX(TakeawaySp1).start();
            return;
        }
        if (TextUtils.isEmpty(cat_id3)) {
            Too.oo("请选择分类");
            MyAnimtion.getAnimator_DX(TakeawaySp2).start();
            return;
        }
        if (TextUtils.isEmpty(goodname)) {
            Too.oo("请填写名字");
            MyAnimtion.getAnimator_DX(TakeawayEtname).start();
            return;
        }
        if (TextUtils.isEmpty(goodcontent)) {
            Too.oo("请填写详情");
            MyAnimtion.getAnimator_DX(TakeawayEtinfo).start();
            return;
        }
        if (TextUtils.isEmpty(biaoqian)) {
            Too.oo("请填写标签");
            MyAnimtion.getAnimator_DX(TakeawayEtbiaoqian).start();
            return;
        }
        if (TextUtils.isEmpty(price)) {
            Too.oo("请填写价格");
            MyAnimtion.getAnimator_DX(TakeawayEtprice).start();
            return;
        }
        if (TextUtils.isEmpty(makerprice)) {
            Too.oo("请填写市场价");
            MyAnimtion.getAnimator_DX(TakeawayEtoldprice).start();
            return;
        }
        if (TextUtils.isEmpty(crprice)) {
            Too.oo("请填写佣金");
            MyAnimtion.getAnimator_DX(TakeawayEtyongjin).start();
            return;
        }
        if (TextUtils.isEmpty(count)) {
            Too.oo("请填写库存");
            MyAnimtion.getAnimator_DX(TakeawayEtnumber).start();
            return;
        }
        if (TextUtils.isEmpty(paths1)) {
            Too.oo("请选择图片");
            MyAnimtion.getAnimator_DX(foodIvimage).start();
            return;
        }
        Set<Integer> selectedList = flowlayoutFood.getSelectedList();
        if (selectedList == null || selectedList.isEmpty()) {
            Too.oo("请选择本店菜系分类");
            return;
        }
        String catid2 = null;
        if (selectedList.iterator().hasNext()) {
            catid2 = listCuisine.get(selectedList.iterator().next()).getCat_id();
        }
        if (TextUtils.isEmpty(catid2)) {
            Too.oo("请选择本店菜系分类");
            return;
        }


        loodingShow();
        model.toFabuShop(SpService.getSP().getStorId(), goodname, goodcontent, cat_id1, cat_id2,
                cat_id3, biaoqian, price, makerprice, crprice, count, paths1, catid2,
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object object) {
                        Too.oo(object);
                        dismissLooding();
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

    @OnClick({R.id.top_ivback, R.id.food_ivimage, R.id.Takeaway_btnok, R.id.flowlayout_btnaddcaixi,
            R.id.flowlayout_btnediting, R.id.flowlayout_btndelete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_ivback://返回
                finish();
                break;
            case R.id.food_ivimage://添加照片
                if (dialogaddimage != null) {
                    dialogaddimage.show();
                }
                break;
            case R.id.Takeaway_btnok://点击提交
                tofabu();
                break;
            case R.id.flowlayout_btnaddcaixi://添加菜系
                showDialogCuisine();
                break;
            case R.id.flowlayout_btnediting://编辑菜系
                if (view.isSelected()) {
                    editingCuisine();
                }
                break;
            case R.id.flowlayout_btndelete://删除菜系
                if (view.isSelected()) {
                    deleteCuisine();
                }
                break;
        }
    }

    /**
     * 删除选中菜系
     */
    private void deleteCuisine() {
        Set<Integer> selectedList = flowlayoutFood.getSelectedList();
        if (selectedList != null && !selectedList.isEmpty()) {
            int next = -1;
            String stringName = null;
            String cat_id = null;

            Iterator<Integer> iterator = selectedList.iterator();
            if (iterator.hasNext()) {
                next = iterator.next();
                stringName = listCuisine.get(next).getCat_name();
                cat_id = listCuisine.get(next).getCat_id();
            }
            if (next == -1) {
                Too.oo("您没选择属性");
                return;
            }

            final int finalNext = next;
            loodingShow();
            model.goods_class_delete(SpService.getSP().getStorId(), stringName, cat_id, new IModel.AsyncCallBack() {
                @Override
                public void onSucceed(Object object) {
                    listCuisine.remove(finalNext);
                    setFlowAdapterCuisine();
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
                    dismissLooding();
                }
            });
        }
    }

    /**
     * 编辑菜系
     */

    private void editingCuisine() {
        Set<Integer> selectedList = flowlayoutFood.getSelectedList();
        if (selectedList != null && !selectedList.isEmpty()) {
            int next = -1;
            String stringName = null;
            String cat_id = null;

            Iterator<Integer> iterator = selectedList.iterator();
            if (iterator.hasNext()) {
                next = iterator.next();
                stringName = listCuisine.get(next).getCat_name();
                cat_id = listCuisine.get(next).getCat_id();
            }
            if (next == -1) {
                Too.oo("您没选择属性");
                return;
            }

            final int finalNext = next;
            new Dialog_addCuisine(this, new Dialog_addCuisine.Callback() {
                @Override
                public void onClick(String name, String id) {
                    FoodCuisine.DataBean data = new FoodCuisine.DataBean();
                    data.setCat_id(id);
                    data.setCat_name(name);
                    listCuisine.set(finalNext, data);
                    setFlowAdapterCuisine();
                }

                @Override
                public void onDismiss() {
                }
            }, model, stringName, cat_id).show();
        }


    }

    /**
     * 显示添加菜系对话框
     */
    private void showDialogCuisine() {
        new Dialog_addCuisine(this, new Dialog_addCuisine.Callback() {
            @Override
            public void onClick(String name, String id) {
                FoodCuisine.DataBean data = new FoodCuisine.DataBean();
                data.setCat_id(id);
                data.setCat_name(name);
                listCuisine.add(data);
                setFlowAdapterCuisine();
            }

            @Override
            public void onDismiss() {
            }
        }, model, null, null).show();
    }


    /**
     * 设置菜系
     */
    private void setFlowAdapterCuisine() {
        if (flowAdapter != null) {
            flowAdapter.notifyDataChanged();
        } else {
            flowAdapter = new FlowAdapterFood(listCuisine, this);
            flowlayoutFood.setAdapter(flowAdapter);
            flowlayoutFood.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    if (selectPosSet != null && !selectPosSet.isEmpty()) {
                        if (selectPosSet.size() < 2) {
                            flowlayoutBtndelete.setSelected(true);
                            flowlayoutBtnediting.setSelected(true);
                        } else {
                            flowlayoutBtndelete.setSelected(false);
                            flowlayoutBtnediting.setSelected(false);
                        }
                    } else {
                        flowlayoutBtndelete.setSelected(false);
                        flowlayoutBtnediting.setSelected(false);
                    }

                }
            });
        }
    }

}
