package com.yunyouzhiyuan.qianbaoshangcheng.fragment.stor_info;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.StorShenqinginfoActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.ShenqingInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.DiaLogaddImage;
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
import java.util.Calendar;

/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class FragmentStorinfo2 extends Fragment implements View.OnClickListener {
    private View view;
    @ViewInject(value = R.id.fragment_stor_info_tow_tvbuttom_ok)
    private TextView tvbtnok;
    @ViewInject(value = R.id.fragment_stor_shenqing_tow_etname)//姓名
    private EditText etname;
    @ViewInject(value = R.id.fragment_stor_info_tow_etshenfenzheng)//身份证号码
    private EditText etnumber;
    @ViewInject(value = R.id.fragment_stor_info_tow_ivstor_shenfen)//身份证图像
    private ImageView ivshenfenzheng;
    @ViewInject(value = R.id.fragment_stor_info_tow_etzhizhao)//店铺执照名称
    private EditText etzhichao;
    @ViewInject(value = R.id.fragment_stor_info_tow_etzhihaoma)//店铺执照号码
    private EditText etzhizhaonumber;
    @ViewInject(value = R.id.fragment_stor_info_tow_etzhiadress)//店铺执照所在地
    private EditText etzhizhaoadress;
    @ViewInject(value = R.id.fragment_stor_info_one_tvstor_leibie)//店铺执照有效期
    private TextView tvtime;
    @ViewInject(value = R.id.fragment_stor_info_tow_ivstor_zhizhao)//店铺执照图片
    private ImageView ivzhizhao;
    @ViewInject(value = R.id.fragment_stor_info_tow_etxukezheng)//许可证名字
    private EditText etxukename;
    @ViewInject(value = R.id.fragment_stor_info_tow_etxukenumber)//许可证号码
    private EditText etxukenumber;
    @ViewInject(value = R.id.fragment_stor_info_tow_etxukeadress)//许可证所在地
    private EditText etxukeadress;
    @ViewInject(value = R.id.fragment_stor_info_one_tvxuketime)//许可证有效期
    private TextView tvxuketime;
    @ViewInject(value = R.id.fragment_stor_info_tow_ivstor_xukezheng)//许可证图片
    private ImageView ivxukezheng;
    private String paths1;//身份证图片地址
    private String paths2;//执照图片地址
    private String paths3;//许可证图片地址
    private String timeyouxiaoqi;//执照有效期
    private DiaLogaddImage dialogaddimage;//上传照片对话框
    private File file = SDisTrue.hasSdcard() ? new File(Environment.getExternalStorageDirectory(), "qianbaoshangcheng_image.jpg") : null;
    private ByteArrayOutputStream baos;
    private ByteArrayInputStream is;
    private int addimage;
    private String xukeyouxiaoqi;

    /**
     * \点击下一步
     */
    private void toNext() {
        if (TextUtils.isEmpty(xukeyouxiaoqi)) {
            MyAnimtion.getAnimator_DX(tvxuketime).start();
            Too.oo(getResources().getString(R.string.qingshuruxukezhengyouxiaoqi));
            return;
        }
        if (TextUtils.isEmpty(timeyouxiaoqi)) {
            MyAnimtion.getAnimator_DX(tvtime).start();
            Too.oo(getResources().getString(R.string.qingxuanzeyouxiaoqi));
            return;
        }
        if (TextUtils.isEmpty(paths1)) {
            MyAnimtion.getAnimator_DX(ivshenfenzheng).start();
            Too.oo(getResources().getString(R.string.qingxxuanzeshenfenzheng));
            return;
        }
        if (TextUtils.isEmpty(paths2)) {
            MyAnimtion.getAnimator_DX(ivzhizhao).start();
            Too.oo(getResources().getString(R.string.qingxxuanzhizhao));
            return;
        }
        if (TextUtils.isEmpty(paths3)) {
            MyAnimtion.getAnimator_DX(ivxukezheng).start();
            Too.oo(getResources().getString(R.string.qingxxuanxuke));
            return;
        }
        String name = etname.getText().toString().trim();
        String shenfennumber = etnumber.getText().toString().trim();
        String zhizhaoname = etzhichao.getText().toString().trim();
        String zhizhaonumber = etzhizhaonumber.getText().toString().trim();
        String zhizhaoadress = etzhizhaoadress.getText().toString().trim();
        String xukename = etxukename.getText().toString().trim();
        String xukenumber = etxukenumber.getText().toString().trim();
        String xukeadress = etxukeadress.getText().toString().trim();

        if (TextUtils.isEmpty(xukeadress)) {
            MyAnimtion.getAnimator_DX(etxukeadress).start();
            Too.oo(getResources().getString(R.string.qingshuruzhizhaoxukeadress));
            return;
        }
        if (TextUtils.isEmpty(xukenumber)) {
            MyAnimtion.getAnimator_DX(etxukenumber).start();
            Too.oo(getResources().getString(R.string.qingshuruzhizhaoxukehaoma));
            return;
        }
        if (TextUtils.isEmpty(xukename)) {
            MyAnimtion.getAnimator_DX(etxukename).start();
            Too.oo(getResources().getString(R.string.qingshuruzhizhaoxukename));
            return;
        }
        if (TextUtils.isEmpty(zhizhaoadress)) {
            MyAnimtion.getAnimator_DX(etzhizhaoadress).start();
            Too.oo(getResources().getString(R.string.qingshuruzhizhaoadres));
            return;
        }
        if (TextUtils.isEmpty(zhizhaonumber)) {
            MyAnimtion.getAnimator_DX(etzhizhaonumber).start();
            Too.oo(getResources().getString(R.string.qingshuruzhizhaohaoma));
            return;
        }
        if (TextUtils.isEmpty(zhizhaoname)) {
            MyAnimtion.getAnimator_DX(etzhichao).start();
            Too.oo(getResources().getString(R.string.qingshuruzhizhaomingcheng));
            return;
        }
        if (TextUtils.isEmpty(shenfennumber)) {
            MyAnimtion.getAnimator_DX(etnumber).start();
            Too.oo(getResources().getString(R.string.qingshurushenfenzhenghaoma));
            return;
        }
        if (TextUtils.isEmpty(name)) {
            MyAnimtion.getAnimator_DX(etname).start();
            Too.oo(getResources().getString(R.string.qingshurushenfenmingcheng));
            return;
        }




        StorShenqinginfoActivity.instanse.setTowParams(paths3,xukeyouxiaoqi,xukeadress,xukenumber,xukename,paths2,timeyouxiaoqi,zhizhaoadress,zhizhaonumber,zhizhaoname,paths1,shenfennumber,name);
        StorShenqinginfoActivity.instanse.setView(2);
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
            x.image().bind(ivshenfenzheng, HttpUrl.IMAGE + paths1, new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2).setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
        }
        if (addimage == 2) {//加载内景图片
            x.image().bind(ivzhizhao, HttpUrl.IMAGE + paths2, new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2).setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
        }
        if (addimage == 3) {//加载内景图片
            x.image().bind(ivxukezheng, HttpUrl.IMAGE + paths3, new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2).setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
        }
    }
private LoadingDialog loadingDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fratgment_stor_info_tow, null);
        x.view().inject(this, view);
        init();
        setlistener();

        ShenqingInfo shenqingInfo = StorShenqinginfoActivity.instanse.getShenqingInfo();
        if (null != shenqingInfo) {
            setView(shenqingInfo);
        }
        return view;
    }

    /**
     * 修改界面 写入信息
     * @param shenqingInfo
     */
    private void setView(ShenqingInfo shenqingInfo) {
        etname.setText(shenqingInfo.getData().getIdname());
        etnumber.setText(shenqingInfo.getData().getIdcard());
        paths1 = shenqingInfo.getData().getHand_card();
        x.image().bind(ivshenfenzheng, HttpUrl.IMAGE + paths1, new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2).setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
        etzhichao.setText(shenqingInfo.getData().getLic_name());
        etzhizhaonumber.setText(shenqingInfo.getData().getLic_code());
        etzhizhaoadress.setText(shenqingInfo.getData().getLic_addr());
        timeyouxiaoqi = shenqingInfo.getData().getLic_endtime();
        tvtime.setText(getResources().getString(R.string.fragment_stor_info_tow_et13));
        tvtime.append(":   " + timeyouxiaoqi);
        paths2 = shenqingInfo.getData().getLic_picture();
        x.image().bind(ivzhizhao, HttpUrl.IMAGE + paths2,
                new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2)
                        .setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
        etxukename.setText(shenqingInfo.getData().getPer_name());
        etxukenumber.setText(shenqingInfo.getData().getPer_code());
        etxukeadress.setText(shenqingInfo.getData().getPer_addr());
        xukeyouxiaoqi = shenqingInfo.getData().getPer_endtime();
        tvxuketime.setText(getResources().getString(R.string.fragment_stor_info_tow_et55));
        tvxuketime.append(":   " + xukeyouxiaoqi);
        paths3 = shenqingInfo.getData().getPer_picture();
        x.image().bind(ivxukezheng, HttpUrl.IMAGE + paths3,
                new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2)
                        .setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
    }

    /**
     * 初始化
     */
    private void init() {
        loadingDialog = new LoadingDialog(getActivity());
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4 && resultCode == -1) {//照相选择返回
            if (file.length() != 0) {
                toOutImage(Uri.fromFile(file));
            } else {
                Too.oo(R.string.xuanzezhaopian);
            }
        }
        if (requestCode == 5 && resultCode == -1) {  //系统相册返回
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
     * 监听器
     */
    private void setlistener() {
        tvbtnok.setOnClickListener(this);
        ivshenfenzheng.setOnClickListener(this);
        ivxukezheng.setOnClickListener(this);
        ivzhizhao.setOnClickListener(this);
        tvtime.setOnClickListener(this);
        tvxuketime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_stor_info_tow_tvbuttom_ok://点击下一步
                toNext();
                break;
            case R.id.fragment_stor_info_tow_ivstor_shenfen://点击添加身份证图片
                addimage = 1;
                dialogaddimage.show();
                break;
            case R.id.fragment_stor_info_tow_ivstor_zhizhao://点击添加店铺执照图片
                addimage = 2;
                dialogaddimage.show();
                break;
            case R.id.fragment_stor_info_tow_ivstor_xukezheng://点击添加许可证图片
                addimage = 3;
                dialogaddimage.show();
                break;
            case R.id.fragment_stor_info_one_tvstor_leibie://点击选择有效期限
                toYouXiaoqi(1);
                break;
            case R.id.fragment_stor_info_one_tvxuketime://点击选择许可证有效期限
                toYouXiaoqi(2);
                break;
        }
    }

    /**
     * 选择有效期时间对话框
     */
    private void toYouXiaoqi(final int xuke) {
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int moth = instance.get(Calendar.MONTH);
        int day = instance.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(xuke == 1){
                    timeyouxiaoqi = year + "-" + (month + 1) + "-" + dayOfMonth;
                    tvtime.setText(getResources().getString(R.string.fragment_stor_info_tow_et13));
                    tvtime.append(":   " + timeyouxiaoqi);
                }else{
                    xukeyouxiaoqi = year + "-" + (month + 1) + "-" + dayOfMonth;
                    tvxuketime.setText(getResources().getString(R.string.fragment_stor_info_tow_et55));
                    tvxuketime.append(":   " + xukeyouxiaoqi);
                }

            }
        }, year, moth, day);
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.show();
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
            startActivityForResult(intent, 4);
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
        startActivityForResult(intent, 5);
        getActivity().overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (file != null) {
            file.delete();
        }
        if(handler!=null){
            handler = null;
        }
    }
}
