package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.StorInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.model.BianjiModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.DiaLogaddImage;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetBitmap;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.MyAnimtion;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.PhotoUploadTask;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SDisTrue;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.logo;

import org.xutils.common.task.AbsTask;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ZhuYeBianjiActivity extends BaseActivity {
    @ViewInject(value = R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.activity_binaji_zhuye_ok)
    private TextView tvbtnok;
    @ViewInject(value = R.id.top_ivback)
    private ImageView ivback;
    @ViewInject(value = R.id.fragment_home_tvimage)
    private TextView tvimage;
    @ViewInject(value = R.id.fragment_home_ivimage)
    private ImageView ivimage;
    @ViewInject(value = R.id.fragment_home_etname)
    private EditText etstorname;
    @ViewInject(value = R.id.fragment_home_etadress)
    private EditText etstoradress;
    @ViewInject(value = R.id.fragment_home_etusername)
    private EditText etusername;
    @ViewInject(value = R.id.fragment_home_etphone)
    private EditText etphone;
    @ViewInject(value = R.id.activity_zhuye_bianji_layout)
    private PullRefreshLayout layout;
    private BianjiModel model;
    private StorInfo info;
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
                    isbaocun = true;
                    x.image().bind(ivimage, HttpUrl.IMAGE + paths1, new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2).setFailureDrawableId(R.drawable.t2).setSize(0, 0).setCircular(true).build());
                } else {
                    Too.oo(GetJsonRetcode.getmsg(s));
                }
                dismissLooding();
            }
        }
    };
    private boolean isbaocun = false;//是否更改过信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ye_bianji);
        x.view().inject(this);
        init();
        setListener();
    }

    /**
     * 写入信息
     */
    private void setView() {
        LogUtils.e(info.getData().get(0).getStroName());
        x.image().bind(ivimage, HttpUrl.IMAGE + info.getData().get(0).getSlogo(), new ImageOptions.Builder().setFadeIn(true).setFailureDrawableId(R.drawable.t2).setLoadingDrawableId(R.drawable.t2).setSize(0, 0).setCircular(true).build());
        etstorname.setText(info.getData().get(0).getStroName());
        etstoradress.setText(info.getData().get(0).getXx_addr());
        etusername.setText(info.getData().get(0).getConnecter());
        etphone.setText(info.getData().get(0).getCphone());
        etstorname.addTextChangedListener(new Listtener());
        etphone.addTextChangedListener(new Listtener());
        etstoradress.addTextChangedListener(new Listtener());
        etusername.addTextChangedListener(new Listtener());
    }

    /**
     * 监听i
     */
    private void setListener() {
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdate();
            }
        });
        tvimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogaddimage.show();
            }
        });
        tvbtnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    /**
     * edittext文字改变监听器
     */
    private class Listtener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            logo.e(isbaocun + "");
            if (!isbaocun) {
                isbaocun = true;
            }
        }
    }

    /**
     * 保存信息
     */
    private void toOutInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.qinbaocuoinfoma);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setdata();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.over, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
            }
        });
        builder.show();
    }

    /**
     * 修改信息
     */
    private void setdata() {
        if (info == null) {
            Too.oo(getResources().getString(R.string.qinhuoquxinxishibaiqingshuaxinjiemian));
            return;
        }
        String storname = etstorname.getText().toString().trim();
        String slogo = paths1 == null ? info.getData().get(0).getSlogo() : paths1;
        String xx_addr = etstoradress.getText().toString().trim();
        String connecter = etusername.getText().toString().trim();
        String cphone = etphone.getText().toString().trim();
        if (TextUtils.isEmpty(cphone)) {
            MyAnimtion.getAnimator_DX(etphone).start();
            return;
        }
        if (TextUtils.isEmpty(connecter)) {
            MyAnimtion.getAnimator_DX(etusername).start();
            return;
        }
        if (TextUtils.isEmpty(xx_addr)) {
            MyAnimtion.getAnimator_DX(etstoradress).start();
            return;
        }
        if (TextUtils.isEmpty(slogo)) {
            MyAnimtion.getAnimator_DX(ivimage).start();
            return;
        }
        if (TextUtils.isEmpty(storname)) {
            MyAnimtion.getAnimator_DX(etstorname).start();
            return;
        }
        loodingShow();
        model.setdata(MyApplication.getUser().getSid(),storname, slogo, xx_addr, connecter, cphone, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                Too.oo(object);
                dismissLooding();
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                dismissLooding();
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }


    /**
     * 出事啊胡
     */
    private void init() {
        tvtitle.setText(R.string.gerenzhuyebianji);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        model = new BianjiModel();
        layout.setRefreshing(true);
        getdate();
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
     * 返回
     */
    private boolean goBack() {
        if (isbaocun) {
            toOutInfo();
            return true;
        } else {
            finish();
            overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
            return false;
        }

    }

    /**
     * 获取信息
     */
    private void getdate() {
        model.getdata(MyApplication.getUser().getSid(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                info = (StorInfo) object;
                setView();
                layout.setRefreshing(false);
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                layout.setRefreshing(false);
            }

            @Override
            public void onFailure(String string) {

            }
        });
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
                    Bitmap bitmap = GetBitmap.getBitmapFormUri(ZhuYeBianjiActivity.this, uri);
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
                        ZhuYeBianjiActivity.this, handler);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return goBack();
        }
        return super.onKeyDown(keyCode, event);
    }
}
