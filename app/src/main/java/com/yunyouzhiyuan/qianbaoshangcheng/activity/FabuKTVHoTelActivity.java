package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.FlowAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.RiqiAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.SpannerAdapterShop;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.TimeCountAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.KTVList;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.KTVPrice;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.KTVTimeCount;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.ShopLeixing;
import com.yunyouzhiyuan.qianbaoshangcheng.model.FabuModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.KTVModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.SquareImageView;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.DiaLogaddImage;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.Dialog_addTime;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.Dialog_addfangxing;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.Dialog_addriqi;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.BitmapUtils_My;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.PhotoTask;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SDisTrue;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class FabuKTVHoTelActivity extends BaseActivity {

    @Bind(R.id.top_tvtitle)
    TextView topTvtitle;
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
    EditText foodSp3;
    @Bind(R.id.food_etprice)
    EditText foodEtprice;
    @Bind(R.id.ktv_timecount_gv)
    GridView gvTimeCount;
    @Bind(R.id.food_etoldprice)
    EditText foodEtoldprice;
    @Bind(R.id.food_etyongjin)
    EditText foodEtyongjin;
    @Bind(R.id.food_etnumber)
    EditText foodEtnumber;
    @Bind(R.id.food_ivimage)
    SquareImageView foodIvimage;
    @Bind(R.id.ktvxuanze_adapter0)
    TagFlowLayout tagFlowLayout0;
    @Bind(R.id.ktvxuanze_adapter1)
    TagFlowLayout tagFlowLayout1;
    @Bind(R.id.ktvxuanze_adapter2)
    GridView tagFlowLayout2;
    @Bind(R.id.ktv_tvfangxing)
    TextView ktvTvfangxing;
    @Bind(R.id.ktv_tvtime)
    TextView ktvTvtime;
    @Bind(R.id.food_btnok)
    Button foodBtnok;
    @Bind(R.id.ktv_tvdeletefagnxing)
    TextView ktvTvdeletefagnxing;
    @Bind(R.id.ktv_tvdeletetime)
    TextView ktvTvdeletetime;
    @Bind(R.id.food_scrollview)
    ScrollView scrollView;
    private FlowAdapter flowAdapter0, flowAdapter1;
    private RiqiAdapter adapter;
    private List<ShopLeixing.DataBean> list = new ArrayList<>();
    private List<ShopLeixing.DataBean> list1 = new ArrayList<>();
    private List<ShopLeixing.DataBean> list2 = new ArrayList<>();
    private String cat_id1, cat_id2, cat_id3;
    private KTVModel modelktv;
    private FabuModel model;
    private SpannerAdapterShop spannerAdapterShop, spannerAdapterShop1, spannerAdapterShop2;
    private DiaLogaddImage dialogaddimage;//上传照片对话框
    private File file = SDisTrue.hasSdcard() ? new File(Environment.getExternalStorageDirectory(),
            "qianbaoshangcheng_image.jpeg") : null;
    private Uri imageUri;
    private AsyncTask<Uri, Void, String> execute;
    private List<KTVList.DataBean> beanList = new ArrayList<>();
    private Call call;
    private TimeCountAdapter timeCountAdapter;
    private List<KTVList.DataBean.SpecItemListBean> listTimeCount = new ArrayList<>();
    private KTVTimeCount.DataBean dataTimeCopunt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabu_ktvho_tel);
        ButterKnife.bind(this);
        init();
        setView();
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (call != null && !call.isCanceled()) {
                        call.cancel();
                        call = null;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 设置适配器
     */
    private void setView() {
        getData(0, "0");
        getTimeCount();
    }

    /**
     * 获取团购一次的时间段
     */
    private void getTimeCount() {
        modelktv.getTimeCount(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                dataTimeCopunt = (KTVTimeCount.DataBean) object;
                setTimeCountAdapter(dataTimeCopunt.getAttr_values());
            }

            @Override
            public void onError(Object object) {

            }

            @Override
            public void onFailure(String string) {

            }
        });
    }

    /**
     * 设置团购一次的时间显示adapter
     */
    private void setTimeCountAdapter(List<String> list) {
        if (timeCountAdapter != null) {
            timeCountAdapter.notifyDataSetChanged();
        } else {
            timeCountAdapter = new TimeCountAdapter(this, list);
            gvTimeCount.setAdapter(timeCountAdapter);
        }
    }

    /**
     * 商家发布商品时属性列表
     */
    private void getShuxinglist() {
        if (TextUtils.isEmpty(cat_id3)) {
            Too.oo("请选择商品三级分类");
            return;
        }
        loodingShow();
        modelktv.getSpecList(cat_id3, SpService.getSP().getStorId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                beanList.clear();
                beanList.addAll((List<KTVList.DataBean>) object);
                if (beanList.size() >= 3) {
                    setFlowAdapterXing();
                    setFlowAdapterTime();
                    setFlowAdapterRiqi();
                } else {
                    Too.oo("后台返回错误");
                }
                dismissLooding();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                dismissLooding();
            }

            @Override
            public void onFailure(String string) {
                Too.oo("断网了");
            }
        });
    }

    /**
     * 设置日期
     */
    private void setFlowAdapterRiqi() {
        if (adapter != null) {
            adapter = null;
        }
        adapter = new RiqiAdapter(this, beanList.get(2).getSpecItemList(), null);
        tagFlowLayout2.setAdapter(adapter);
    }

    /**
     * 设置时间
     */
    private void setFlowAdapterTime() {
        if (flowAdapter1 != null) {
            flowAdapter1.notifyDataChanged();
        } else {
            flowAdapter1 = new FlowAdapter(beanList.get(1).getSpecItemList(), this);
            tagFlowLayout1.setAdapter(flowAdapter1);
            tagFlowLayout1.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    if (selectPosSet != null && !selectPosSet.isEmpty() && selectPosSet.size() < 2) {
                        ktvTvdeletetime.setSelected(true);
                    } else {
                        ktvTvdeletetime.setSelected(false);
                    }
                }
            });
        }
    }

    /**
     * 设置房型
     */
    private void setFlowAdapterXing() {
        if (flowAdapter0 != null) {
            flowAdapter0.notifyDataChanged();
        } else {
            flowAdapter0 = new FlowAdapter(beanList.get(0).getSpecItemList(), this);
            tagFlowLayout0.setAdapter(flowAdapter0);
            tagFlowLayout0.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    if (selectPosSet != null && !selectPosSet.isEmpty() && selectPosSet.size() < 2) {
                        ktvTvdeletefagnxing.setSelected(true);
                    } else {
                        ktvTvdeletefagnxing.setSelected(false);
                    }
                    Iterator<Integer> iterator = selectPosSet.iterator();
                    while (iterator.hasNext()) {
                        LogUtils.d("选中房型下标=" + iterator.next());
                    }
                }
            });
        }
    }

    /**
     * 初始化
     */
    private void init() {
        topTvtitle.setText("发布商品");
        model = new FabuModel();
        modelktv = new KTVModel();

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

    @OnClick({R.id.ktv_tvaddriqi, R.id.top_ivback, R.id.food_btnok,
            R.id.ktv_tvfangxing, R.id.ktv_tvtime, R.id.food_ivimage
            , R.id.ktv_tvdeletetime, R.id.ktv_tvdeletefagnxing})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ktv_tvdeletefagnxing://删除房型属性
                if (view.isSelected()) {
                    deleteItme(0);
                }
                break;
            case R.id.ktv_tvdeletetime://删除时间属性
                if (view.isSelected()) {
                    deleteItme(1);
                }
                break;
            case R.id.top_ivback://返回
                finish();
                break;
            case R.id.food_btnok://确定
                toSubmit();
                break;
            case R.id.ktv_tvfangxing://添加房型
                showAddDialog(0);
                break;
            case R.id.ktv_tvtime://添加时间
                showAddDialog(1);
                break;
            case R.id.ktv_tvaddriqi://添加日期
                showAddDialog(2);
                break;
            case R.id.food_ivimage://添加图片
                dialogaddimage.show();
                break;
        }
    }


    /**
     * 删除选中规格
     */
    private void deleteItme(int position) {
        loadingDialog.show();
        switch (position) {
            case 0://房型
                Set<Integer> selectedList = tagFlowLayout0.getSelectedList();
                if (selectedList == null || selectedList.isEmpty()) {
                    Too.oo("您还没选中属性");
                    loadingDialog.dismiss();
                    return;
                }
                if (selectedList.size() > 1) {
                    Too.oo("一次只能删除一个呢");
                }
                Iterator<Integer> iterator = selectedList.iterator();
                int next = -1;
                if (iterator.hasNext()) {
                    next = iterator.next();
                }
                if (next == -1) {
                    loadingDialog.dismiss();
                    return;
                }
                toDeleteTime(position, next, beanList.get(position).getSpecItemList().get(next).getItem_id());
                break;
            case 1://时间段
                Set<Integer> times = tagFlowLayout1.getSelectedList();
                if (times == null || times.isEmpty()) {
                    Too.oo("您还没选中属性");
                    loadingDialog.dismiss();
                    return;
                }
                Iterator<Integer> timesiterator = times.iterator();
                int nextTime = -1;
                if (timesiterator.hasNext()) {
                    nextTime = timesiterator.next();
                }
                if (nextTime == -1) {
                    loadingDialog.dismiss();
                    return;
                }
                toDeleteTime(position, nextTime, beanList.get(position).getSpecItemList().get(nextTime).getItem_id());
                break;
            default:
                break;
        }

    }

    /**
     * 联网删除itme规格
     *
     * @param finalNext
     */
    private void toDeleteTime(final int position, final int finalNext, String id) {
        modelktv.delSpecItem(SpService.getSP().getStorId(), beanList.get(position).getId(), id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                if (beanList.get(position).getSpecItemList() != null &&
                        beanList.get(position).getSpecItemList().size() >= finalNext) {
                    beanList.get(position).getSpecItemList().remove(finalNext);
                }
                switch (position) {
                    case 0://房型
                        setFlowAdapterXing();
                        break;
                    case 1://时间段
                        setFlowAdapterTime();
                        break;
                    case 2://日期
                        setFlowAdapterRiqi();
                        break;
                    default:
                        break;
                }
                Too.oo(object);
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

    /**
     * 显示添加规格参数对话框
     *
     * @param index
     */
    private void showAddDialog(final int index) {
        if (beanList.size() < (index + 1) || TextUtils.isEmpty(beanList.get(index).getId())) {
            Too.oo("商品分类还没获取到或者您没选择，暂时不能添加");
            return;
        }
        switch (index) {
            case 0://添加房型
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                new Dialog_addfangxing(this, new Dialog_addfangxing.Callback() {
                    @Override
                    public void onClick(String name, String id) {
                        if (!beanList.isEmpty()) {
                            beanList.get(index).getSpecItemList().add(new KTVList.DataBean.SpecItemListBean(id, name));
                            setFlowAdapterXing();
                        }
                    }

                    @Override
                    public void onClick() {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    }
                }, modelktv, beanList.get(index).getId()).show();
                break;
            case 1://添加时间
                new Dialog_addTime(this, new Dialog_addTime.Callback() {
                    @Override
                    public void onClick(String name, String id) {
                        Too.oo(name);
                        beanList.get(index).getSpecItemList().
                                add(new KTVList.DataBean.SpecItemListBean(id, name));
                        setFlowAdapterTime();
                    }
                }, modelktv, beanList.get(index).getId()).show();
                break;
            case 2://添加日期
                new Dialog_addriqi(this, new Dialog_addriqi.Callback() {

                    @Override
                    public void onClick(String name, String id) {
                        beanList.get(index).getSpecItemList()
                                .add(new KTVList.DataBean.SpecItemListBean(id, name));
                        setFlowAdapterRiqi();
                    }
                }, modelktv, beanList.get(index).getId()).show();
                break;
        }

    }

    /**
     * 调用摄像头拍照
     */
    private void takePhotos() {
        // 判断存储卡是否可以用，可用进行存储
        if (file != null) {
            file.delete();
            file = new File(Environment.getExternalStorageDirectory(), "qianbaoshangcheng_image.jpeg");
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(file));
            startActivityForResult(intent, 2);
            overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
        } else {
            Too.oo("请检查内存卡是否存在/或者请您收取本应用权限");
        }
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
                dismissLooding();
            }

            @Override
            public void onFailure(String string) {

            }
        });
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
                    if (TextUtils.equals("49", cat_id3)) {//预定
                        tagFlowLayout0.setMaxSelectCount(-1);
                        tagFlowLayout1.setMaxSelectCount(-1);
                    } else {//团购
                        tagFlowLayout0.setMaxSelectCount(1);
                        tagFlowLayout1.setMaxSelectCount(1);
                    }
                    getShuxinglist();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Too.oo("onNothingSelected");
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (file != null) {
            file.delete();
        }
        if (execute != null && !execute.isCancelled()) {
            execute.cancel(true);
            execute = null;
        }
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {//照相选择返回
            if (file.length() != 0) {
                x.image().bind(foodIvimage, file.getPath(), new ImageOptions.Builder().setUseMemCache(false).build());
                LogUtils.d("战象路径=" + file.getPath());
                imageUri = Uri.fromFile(file);
            } else {
                Too.oo(R.string.xuanzezhaopian);
            }
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {  //系统相册返回
            if (data != null) {
                imageUri = data.getData();
                try {
                    Bitmap bitmapFormUri = BitmapUtils_My.getBitmapFormUri(this, imageUri);
                    foodIvimage.setImageBitmap(bitmapFormUri);
                } catch (IOException e) {
                    e.printStackTrace();
                    Too.oo(getString(R.string.tukushibai));
                }
            } else {
                Too.oo(getString(R.string.tukushibai));
            }
        }
    }

    /**
     * 提交 所有请求
     */
    private void toSubmit() {
        if (beanList.isEmpty() || beanList.size() < 3) {
            Too.oo("获取数据分类失败");
            return;
        }
        if (TextUtils.isEmpty(cat_id1) || TextUtils.isEmpty(cat_id2) || TextUtils.isEmpty(cat_id3)) {
            Too.oo("请您选择商品分类");
            return;
        }

        if (imageUri == null) {
            Too.oo("请上传图片");
            float x = foodIvimage.getY();
            scrollView.smoothScrollTo(0, (int) x);
            return;
        }

        final String name = foodEtname.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Too.oo("请填写标题");
            scrollView.smoothScrollTo(0, (int) foodEtname.getY());
            return;
        }
        final String info = foodEtinfo.getText().toString().trim();
        if (TextUtils.isEmpty(info)) {
            Too.oo("请填写商品详情");
            scrollView.smoothScrollTo(0, (int) foodEtinfo.getY());
            return;
        }

        final String biaoqian = foodSp3.getText().toString().trim();
        if (TextUtils.isEmpty(biaoqian)) {
            Too.oo("请填写商品标签");
            scrollView.smoothScrollTo(0, (int) foodSp3.getY());
            return;
        }
        final String price = foodEtprice.getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            Too.oo("请填写商品价格");
            scrollView.smoothScrollTo(0, (int) foodEtprice.getY());
            return;
        }
        final String olPprice = foodEtoldprice.getText().toString().trim();
        if (TextUtils.isEmpty(olPprice)) {
            Too.oo("请填写商品市场价格");
            scrollView.smoothScrollTo(0, (int) foodEtoldprice.getY());
            return;
        }
        final String yongjin = foodEtyongjin.getText().toString().trim();
        if (TextUtils.isEmpty(yongjin)) {
            Too.oo("请填写商品佣金价格");
            scrollView.smoothScrollTo(0, (int) foodEtyongjin.getY());
            return;
        }
        final String number = foodEtnumber.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Too.oo("请填写商品库存");
            scrollView.smoothScrollTo(0, (int) foodEtnumber.getY());
            return;
        }
        Set<Integer> selectedList = tagFlowLayout0.getSelectedList();
        if (selectedList == null || selectedList.isEmpty()) {
            Too.oo("请选择房型属性");
            return;
        }
        Map<String, String> stringMap = new ArrayMap<>();
        Iterator<Integer> iterator = selectedList.iterator();
        int index;
        if (beanList.size() > 0 && beanList.get(0).getSpecItemList().size() > 0) {
            while (iterator.hasNext()) {
                index = iterator.next();
                stringMap.put(beanList.get(0).getSpecItemList().get(index).getItem_id(),
                        beanList.get(0).getSpecItemList().get(index).getItem());
            }
        }
        final String wrap = new Gson().toJson(stringMap);
        if (TextUtils.isEmpty(wrap)) {
            Too.oo("请选择房型属性");
            return;
        }
        Set<Integer> selectedtime = tagFlowLayout1.getSelectedList();
        if (selectedtime == null || selectedtime.isEmpty()) {
            Too.oo("请选择时间属性");
            return;
        }
        Map<String, String> stringMap1 = new ArrayMap<>();
        Iterator<Integer> timeitor = selectedtime.iterator();
        if (beanList.size() >= 1 && beanList.get(1).getSpecItemList().size() > 0) {
            while (timeitor.hasNext()) {
                index = timeitor.next();
                stringMap1.put(beanList.get(1).getSpecItemList().get(index).getItem_id(),
                        beanList.get(1).getSpecItemList().get(index).getItem());
            }
        }
        final String tim = new Gson().toJson(stringMap1);
        if (TextUtils.isEmpty(tim)) {
            Too.oo("请选择时间属性");
            return;
        }


        Map<String, String> stringMap2 = new ArrayMap<>();
        List<KTVList.DataBean.SpecItemListBean> specItemList = beanList.get(2).getSpecItemList();
        for (int i = 0; i < specItemList.size(); i++) {
            if (specItemList.get(i).isChecked()) {
                stringMap2.put(specItemList.get(i).getItem_id(), specItemList.get(i).getItem());
            }
        }

        final String builder = new Gson().toJson(stringMap2);

        if (builder.length() < 1) {
            Too.oo("请选择日期");
            return;
        }
        if (timeCountAdapter == null ) {
            Too.oo("请选择购买时间限制");
            return;
        }
        final String attr_value = timeCountAdapter.getAttrValue();
        if (TextUtils.isEmpty(attr_value)) {
            Too.oo("请选择购买时间限制");
            return;
        }
        LogUtils.d("发布ktv参数：attr_value=" + attr_value);


        loodingShow();
        execute = new PhotoTask(this, HttpUrl.ADDIMAGE, new PhotoTask.PhotoCallback() {
            @Override
            public void onError() {
                super.onError();
                dismissLooding();
            }

            @Override
            public void onSuccess(String string) {
                String urlImage = GetJsonRetcode.getname("data", string);


                call = modelktv.addSpecialGoods(SpService.getSP().getStorId(), name, info, cat_id1, cat_id2,
                        cat_id3, biaoqian, price, olPprice, yongjin, number, urlImage,
                        builder, tim, wrap, attr_value, new IModel.AsyncCallBack() {
                            @Override
                            public void onSucceed(Object object) {
                                KTVPrice object1 = (KTVPrice) object;
                                Too.oo("发布成功");
                                dismissLooding();
                                KTVPriceActivity.startPriceActivity(FabuKTVHoTelActivity.this,
                                        object1.getData(), number, price, object1.getMsg());
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
        }).execute(imageUri);

    }

}
