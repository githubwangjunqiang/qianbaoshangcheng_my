package com.yunyouzhiyuan.qianbaoshangcheng.fragment.stor_info;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.StorShenqinginfoActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.SpannerAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.LeiXing;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.ShenqingInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.StorShenqingModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.DiaLogaddImage;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.Dialog_date_se_3;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetBitmap;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.MyAnimtion;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.PhotoUploadTask;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SDisTrue;

import org.xutils.common.task.AbsTask;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class FragmentStorinfo1 extends Fragment implements View.OnClickListener {
    private View view;
    @ViewInject(value = R.id.fragment_stor_info_one_tvbuttom_ok)
    private TextView tvbrnok;
    @ViewInject(value = R.id.fragment_stor_info_one_ivstor_waijing)//点击外景图片
    private ImageView ivWaijing;
    @ViewInject(value = R.id.fragment_stor_info_one_ivstor_shinei)//点击内景图片
    private ImageView ivneijing;
    @ViewInject(value = R.id.fragment_stor_info_one_ivstor_log)//点击店铺logo图片
    private ImageView ivlogo;
    @ViewInject(value = R.id.fragment_stor_info_one_tvstor_adress)//店铺区域
    private TextView tvbtnadress;
    @ViewInject(value = R.id.fragment_stor_info_one_tvstor_leibie)//店铺类型
    private Spinner spinner;
    @ViewInject(value = R.id.fragment_stor_info_one_etstor)//店铺名称
    private EditText etStorname;
    @ViewInject(value = R.id.fragment_stor_info_one_etsto_ren)//店铺负责人
    private EditText etStorUser;
    @ViewInject(value = R.id.fragment_stor_info_one_etsto_phone)//店铺负责人联系电话
    private EditText etStorphone;
    @ViewInject(value = R.id.fragment_stor_info_one_etsto_adress)//店铺详细地址
    private EditText etStorAdressinfo;
    @ViewInject(value = R.id.fragment_stor_info_one_etsto_info)//店铺简介
    private EditText etStorinfo;


    private List<String> strings = new ArrayList<>();
    private Dialog_date_se_3 diaLogCity;//三级联动 对话框
    private StorShenqingModel model;
    private String city;//用户选择的地址区域

    private String province_id;//省id
    private String city_id;//市id
    private String district;//区县id

    private String leixing;//用户选择的店铺类型
    private DiaLogaddImage dialogaddimage;//上传照片对话框
    private File file = SDisTrue.hasSdcard() ? new File(Environment.getExternalStorageDirectory(), "qianbaoshangcheng_image.jpg") : null;
    private ByteArrayOutputStream baos;
    private ByteArrayInputStream is;
    private String paths1;//外景图片地址
    private String paths2;//内景图片地址
    private String paths3;//logo图片地址
    private SpannerAdapter adapter;


    /**
     * 确认 下一步
     */
    private void toOkNext() {
        if (TextUtils.isEmpty(leixing)) {
            MyAnimtion.getAnimator_DX(spinner).start();
            Too.oo(getResources().getString(R.string.qingxuanzedianpuleixing));
            return;
        }
        if (TextUtils.isEmpty(city)) {
            MyAnimtion.getAnimator_DX(tvbtnadress).start();
            Too.oo(getResources().getString(R.string.qingxuanzedianpuquyu));
            return;
        }
        if (TextUtils.isEmpty(paths1)) {
            MyAnimtion.getAnimator_DX(ivWaijing).start();
            Too.oo(getResources().getString(R.string.qingxuanzedianpuwaijing));
            return;
        }
        if (TextUtils.isEmpty(paths2)) {
            MyAnimtion.getAnimator_DX(ivneijing).start();
            Too.oo(getResources().getString(R.string.qingxuanzedianpuneijing));
            return;
        }
        if (TextUtils.isEmpty(paths3)) {
            MyAnimtion.getAnimator_DX(ivlogo).start();
            Too.oo(getResources().getString(R.string.qingxuanzedianpulog));
            return;
        }
        String name = etStorname.getText().toString().trim();
        String user = etStorUser.getText().toString().trim();
        String phone = etStorphone.getText().toString().trim();
        String adressinfo = etStorAdressinfo.getText().toString().trim();
        String storinfo = etStorinfo.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            MyAnimtion.getAnimator_DX(etStorname).start();
            Too.oo(getResources().getString(R.string.qingtianxiexingming));
            return;
        }
        if (TextUtils.isEmpty(user)) {
            MyAnimtion.getAnimator_DX(etStorUser).start();
            Too.oo(getResources().getString(R.string.qingtianuse));
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            MyAnimtion.getAnimator_DX(etStorphone).start();
            Too.oo(getResources().getString(R.string.qingtiaphone));
            return;
        }
        if (TextUtils.isEmpty(adressinfo)) {
            MyAnimtion.getAnimator_DX(etStorAdressinfo).start();
            Too.oo(getResources().getString(R.string.qingtianxiexiangxidizhi));
            return;
        }
        if (TextUtils.isEmpty(storinfo)) {
            MyAnimtion.getAnimator_DX(etStorinfo).start();
            Too.oo(getResources().getString(R.string.qingtianxiejianji));
            return;
        }
        if (province_id != null && city_id != null && district != null) {
            StorShenqinginfoActivity.instanse.setOneParams(name, user, phone, city, adressinfo, leixing,
                    storinfo, paths1, paths2, paths3, province_id, city_id, district);
            StorShenqinginfoActivity.instanse.setView(1);
        } else {
            Too.oo("省市区id为null");
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 152) {
                String s = (String) msg.obj;
                if (GetJsonRetcode.getRetcode(s) == 2000) {
                    if (addimage == 1) {
                        paths1 = (GetJsonRetcode.getname("data", s));
                    } else if (addimage == 2) {
                        paths2 = (GetJsonRetcode.getname("data", s));
                    } else if (addimage == 3) {
                        paths3 = (GetJsonRetcode.getname("data", s));
                    }
                    setimage();
                } else {
                    Too.oo(GetJsonRetcode.getmsg(s));
                }
                loadingDialog.dismiss();
            }
        }
    };

    /**
     * 写入图片
     */
    private void setimage() {
        if (addimage == 1) {//加载外景图片
            x.image().bind(ivWaijing, HttpUrl.IMAGE + paths1, new ImageOptions.Builder().setFadeIn(true)
                    .setLoadingDrawableId(R.drawable.t2).setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
        }
        if (addimage == 2) {//加载内景图片
            x.image().bind(ivneijing, HttpUrl.IMAGE + paths2, new ImageOptions.Builder()
                    .setFadeIn(true).setLoadingDrawableId(R.drawable.t2).setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
        }
        if (addimage == 3) {//加载内景图片
            x.image().bind(ivlogo, HttpUrl.IMAGE + paths3, new ImageOptions.Builder().
                    setFadeIn(true).setLoadingDrawableId(R.drawable.t2).setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
        }
    }

    private int addimage;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fratgment_stor_info_one, null);
        x.view().inject(this, view);
        init();
        setListener();
        return view;
    }

    /**
     * 监听器
     */
    private void setListener() {
        tvbrnok.setOnClickListener(this);
        tvbtnadress.setOnClickListener(this);
        ivWaijing.setOnClickListener(this);
        ivneijing.setOnClickListener(this);
        ivlogo.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == -1) {//照相选择返回
            if (file.length() != 0) {
                toOutImage(Uri.fromFile(file));

            } else {
                Too.oo(R.string.xuanzezhaopian);
            }
        }
        if (requestCode == 3 && resultCode == -1) {  //系统相册返回
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
        loadingDialog.show();
        x.task().start(new AbsTask<Bitmap>() {
            @Override
            protected Bitmap doBackground() throws Throwable {
                try {
                    Bitmap bitmap = GetBitmap.getBitmapFormUri(getActivity(), uri);
                    // 字节数组输出流
                    baos = new ByteArrayOutputStream();
                    // 将Bitmap对象转化为字节数组输出流
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    // 获得字节流
                    is = new ByteArrayInputStream(baos.toByteArray());
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onSuccess(Bitmap result) {
                PhotoUploadTask put = new PhotoUploadTask(
                        HttpUrl.ADDIMAGE, is,
                        getActivity(), handler);
                put.start();
            }

            @Override
            protected void onError(Throwable ex, boolean isCallbackError) {
                Too.oo("上传图片失败");
                loadingDialog.dismiss();
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {

        loadingDialog = new LoadingDialog(getActivity());
        diaLogCity = new Dialog_date_se_3(getActivity(), new Dialog_date_se_3.CallBack() {
            @Override
            public void callBack(String name, String p, String c, String dis) {
                city = name;
                province_id = p;
                city_id = c;
                district = dis;

                tvbtnadress.setText("您的店铺地址是：" + city);
            }
        });
        dialogaddimage = new DiaLogaddImage(getActivity(), new DiaLogaddImage.CallBack() {
            @Override
            public void callBack(int type) {
                if (type == 0) {     //照相
                    takePhotos();
                } else {             //相册选择
                    gallery();
                }
            }
        });
        model = new StorShenqingModel();

        ShenqingInfo shenqingInfo = StorShenqinginfoActivity.instanse.getShenqingInfo();
        if (null != shenqingInfo) {
            setView(shenqingInfo);
        } else {
            getStorLeixing(null);
        }
    }

    /**
     * 修改信息  写入之前信息
     *
     * @param shenqingInfo
     */
    private void setView(ShenqingInfo shenqingInfo) {
        province_id = shenqingInfo.getData().getProvince_id();
        city_id = shenqingInfo.getData().getCity_id();
        district = shenqingInfo.getData().getDistrict();
        etStorname.setText(shenqingInfo.getData().getStroname());
        etStorUser.setText(shenqingInfo.getData().getConnecter());
        etStorphone.setText(shenqingInfo.getData().getCphone());
        city = shenqingInfo.getData().getAddr();
        tvbtnadress.setText("您的店铺地址是：" + city);
        etStorAdressinfo.setText(shenqingInfo.getData().getXx_addr());
        getStorLeixing(shenqingInfo.getData().getStype());
        etStorinfo.setText(shenqingInfo.getData().getIntroducetext());
        paths1 = shenqingInfo.getData().getOutpicture();
        x.image().bind(ivWaijing, HttpUrl.IMAGE + paths1,
                new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2)
                        .setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
        paths2 = shenqingInfo.getData().getInsidepicture();
        x.image().bind(ivneijing, HttpUrl.IMAGE + paths2,
                new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2)
                        .setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
        paths3 = shenqingInfo.getData().getSlogo();
        x.image().bind(ivlogo, HttpUrl.IMAGE + paths3,
                new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2)
                        .setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());

    }

    /**
     * 获取店铺类型选项
     *
     * @param stype
     */
    private void getStorLeixing(final String stype) {
        loadingDialog.show();
        model.getStorLeiXing(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                loadingDialog.dismiss();
                toSetSpanner((List<LeiXing.DataBean>) object, stype);
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }

    /**
     * 设置Spanner  店铺类型的Spanner
     *
     * @param object
     * @param stype
     */
    private void toSetSpanner(List<LeiXing.DataBean> object, String stype) {
        final List<LeiXing.DataBean> li = object;
        strings.clear();
        strings.add(getResources().getString(R.string.xuanzedizhi));
        int index = -1;
        if (null != stype) {
            for (int i = 0; i < li.size(); i++) {
                if (TextUtils.equals(stype, li.get(i).getTitle())) {
                    index = (i + 1);
                }
                strings.add(li.get(i).getTitle());
            }
            if (index == -1) {
                Too.oo("因为服务器类型升级，请您记得重新选择店铺类型");
            }
        } else {
            for (int i = 0; i < li.size(); i++) {
                strings.add(li.get(i).getTitle());
            }
        }

        adapter = new SpannerAdapter(getActivity(), strings);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (0 != position) {
                    leixing = li.get(position-1).getId();
                } else {
                    leixing = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner.setSelection(index == -1 ? 0 : index);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_stor_info_one_tvbuttom_ok://点击下一步
                toOkNext();
                break;
            case R.id.fragment_stor_info_one_tvstor_adress://点击店铺区域
                diaLogCity.show();
                province_id = null;
                city_id = null;
                district = null;
                city = null;
                tvbtnadress.setText("您的店铺地址是：" + city);
                break;
            case R.id.fragment_stor_info_one_ivstor_waijing://点击外景图片
                dialogaddimage.show();
                addimage = 1;
                break;
            case R.id.fragment_stor_info_one_ivstor_shinei://点击内景图片
                dialogaddimage.show();
                addimage = 2;
                break;
            case R.id.fragment_stor_info_one_ivstor_log://点击logo图片
                dialogaddimage.show();
                addimage = 3;
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
            file = new File(Environment.getExternalStorageDirectory(), "qianbaoshangcheng_image.jpg");
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(file));
            startActivityForResult(intent, 2);
            getActivity().overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
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
        getActivity().overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (file != null) {
            file.delete();
        }
        if (handler != null) {
            handler = null;
        }
    }
}
