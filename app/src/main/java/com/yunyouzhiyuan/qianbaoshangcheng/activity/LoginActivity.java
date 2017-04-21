package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.User;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.LoginModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.MyAnimtion;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity {
    @ViewInject(value = R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.top_ivback)
    private ImageView ivback;
    @ViewInject(value = R.id.activity_login_etuser)
    private EditText etuser;
    @ViewInject(value = R.id.activity_login_etpassword)
    private EditText etpassword;
    @ViewInject(value = R.id.activity_login_tvbtnok)
    private TextView tvbtnok;
    @ViewInject(value = R.id.activity_login_tvwangjimima)
    private TextView tvWangjimima;
    @ViewInject(value = R.id.activity_login_tvzhuce)
    private TextView tvzhuce;
    public static LoginActivity instanse;
    private LoginModel model;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);
        instanse = this;
        init();
        initLocation();
    }

    /**
     * 初始化
     */
    private void init() {
        model = new LoginModel();
        tvtitle.setText("登陆");
        ivback.setVisibility(View.GONE);
        etuser.setText(SpService.getSP().getphone());
        etpassword.setText(SpService.getSP().getpas());
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
    }

    /**
     * 监听器
     */
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_tvbtnok://点击登陆
                toLogin();
                break;
            case R.id.activity_login_tvwangjimima://点击忘记密码
                Intent intent1 = new Intent(LoginActivity.this, WangPasswordActivity.class);
                startActivityForResult(intent1, 1);
                overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
                break;
            case R.id.activity_login_tvzhuce://点击注册
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
                break;
        }
    }

    /**
     * 登陆
     */
    private void toLogin() {
        final String phone = etuser.getText().toString().trim();
        final String pas = etpassword.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length() < 1) {
            MyAnimtion.getAnimator_DX(etuser).start();
            Too.oo("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(pas) || pas.length() < 1) {
            MyAnimtion.getAnimator_DX(etpassword).start();
            Too.oo("请输入密码");
            return;
        }
        loodingShow();

        LogUtils.d("登陆参数getRegistrationID=" + JPushInterface.getRegistrationID(MyApplication.getContext()));
        LogUtils.d("登陆参数getUdid=" + JPushInterface.getUdid(MyApplication.getContext()));


        model.login(phone, pas, JPushInterface.getRegistrationID(MyApplication.getContext()),
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object object) {
                        User user = (User) object;
                        Too.oo(getResources().getString(R.string.dengluchenggong));
                        SpService.getSP().clearUser();
                        SpService.getSP().saveUid(user.getSid(), phone, pas);
                        MyApplication.setUser(user);
                        if (TextUtils.equals("0", user.getFlag())) {
                            Intent intent1 = new Intent(LoginActivity.this, StorShenqingActivity.class);
                            startActivity(intent1);
                            overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);

                        } else {
                            startActivity(new Intent(LoginActivity.this, ShenqingjiluActivity.class));
                            overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
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
                        Too.oo(string);
                        dismissLooding();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            if (null == data) {
                return;
            }
            etuser.setText(data.getStringExtra("phone"));
            etpassword.setText(data.getStringExtra("pas"));
        }
    }

    /**
     * 定位监听器
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取定位结果
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间
            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息
            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数
                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
                mLocationClient.stop();
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
                mLocationClient.stop();
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
                mLocationClient.stop();
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                mLocationClient.requestLocation();
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
                Too.oo("网络不通导致定位失败，请检查网络是否通畅");
                mLocationClient.requestLocation();
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                Too.oo("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                mLocationClient.requestLocation();
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息
            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            LogUtils.d("定位结果" + sb.toString());

            SpService.getSP().saveLocation(location.getLatitude() + "", location.getLongitude() + "");
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * 定位参数
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setEnableSimulateGps(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mLocationClient.unRegisterLocationListener(myListener);
        myListener = null;
        mLocationClient = null;
        super.onDestroy();
    }
}
