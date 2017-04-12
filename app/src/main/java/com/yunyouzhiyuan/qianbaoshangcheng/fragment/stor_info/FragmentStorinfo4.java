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

/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class FragmentStorinfo4 extends Fragment implements View.OnClickListener {
    private View view;
    @ViewInject(value = R.id.fragment_stor_info_four_tvbuttom_ok)
    private TextView tvbtnok;
    @ViewInject(value = R.id.fragment_stor_info_four_ivshopping)
    private ImageView ivimage;
    @ViewInject(value = R.id.fragment_stor_info_four_etshopping)
    private EditText etshopping;
    @ViewInject(value = R.id.fragment_stor_info_four_etxiaofei)
    private EditText etrenjunxiaofei;

    private DiaLogaddImage dialogaddimage;//上传照片对话框
    private File file = SDisTrue.hasSdcard() ?
            new File(Environment.getExternalStorageDirectory(), "qianbaoshangcheng_image.jpg") : null;
    private ByteArrayOutputStream baos;
    private ByteArrayInputStream is;
    private String paths1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 152) {
                String s = (String) msg.obj;
                if (GetJsonRetcode.getRetcode(s) == 2000) {
                    paths1 = (GetJsonRetcode.getname("data", s));
                    setimage();
                } else {
                    Too.oo(GetJsonRetcode.getmsg(s));
                }
                loadingDialog.dismiss();
            }
        }
    };
    private LoadingDialog loadingDialog;

    /**
     * 写入图片
     */
    private void setimage() {
        x.image().bind(ivimage, HttpUrl.IMAGE + paths1, new ImageOptions.Builder().
                setFadeIn(true).setLoadingDrawableId(R.drawable.t2).
                setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
    }

    /**
     * 点击下一步
     */
    private void toNext() {
        String shoppingpeice = etshopping.getText().toString().trim();
        String renjunjiage = etrenjunxiaofei.getText().toString().trim();
        if (TextUtils.isEmpty(shoppingpeice)) {
            MyAnimtion.getAnimator_DX(etshopping).start();
            Too.oo(getResources().getString(R.string.qingshurushangpinjiage));
            return;
        }
        if (TextUtils.isEmpty(renjunjiage)) {
            MyAnimtion.getAnimator_DX(etrenjunxiaofei).start();
            Too.oo(getResources().getString(R.string.qingshururenjunjiage));
            return;
        }
        if (TextUtils.isEmpty(paths1)) {
            MyAnimtion.getAnimator_DX(ivimage).start();
            Too.oo(getResources().getString(R.string.qingshangchuantuxiang));
            return;
        }

        StorShenqinginfoActivity.instanse.setfourParams(paths1, shoppingpeice, renjunjiage);
        StorShenqinginfoActivity.instanse.toZhece();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fratgment_stor_info_four, null);
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
     * 修改信息  写入之前信息
     *
     * @param shenqingInfo
     */
    private void setView(ShenqingInfo shenqingInfo) {
        etshopping.setText(shenqingInfo.getData().getGprice());
        etrenjunxiaofei.setText(shenqingInfo.getData().getAvgprice());
        paths1 = shenqingInfo.getData().getGpicture();
        x.image().bind(ivimage, HttpUrl.IMAGE + paths1, new ImageOptions.Builder().
                setFadeIn(true).setLoadingDrawableId(R.drawable.t2).
                setFailureDrawableId(R.drawable.t2).setSize(0, 0).build());
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

    /**
     * 监听器
     */
    private void setlistener() {
        tvbtnok.setOnClickListener(this);
        ivimage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_stor_info_four_tvbuttom_ok://点击提交
                toNext();
                break;
            case R.id.fragment_stor_info_four_ivshopping://点击添加图片
                dialogaddimage.show();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == -1) {//照相选择返回
            if (file.length() != 0) {
                toOutImage(Uri.fromFile(file));
            } else {
                Too.oo(R.string.xuanzezhaopian);
            }
        }
        if (requestCode == 8 && resultCode == -1) {  //系统相册返回
            if (data != null) {
                Uri uri = data.getData();
                toOutImage(uri);
            } else {
                Too.oo(R.string.tukushibai);
            }
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
            startActivityForResult(intent, 7);
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
        startActivityForResult(intent, 8);
        getActivity().overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
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
