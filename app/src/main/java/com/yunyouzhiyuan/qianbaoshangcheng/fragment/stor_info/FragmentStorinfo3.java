package com.yunyouzhiyuan.qianbaoshangcheng.fragment.stor_info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.StorShenqinginfoActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.ShenqingInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.StorShenqingModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.MyAnimtion;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.Text_Size;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class FragmentStorinfo3 extends Fragment implements View.OnClickListener {
    private View view;
    @ViewInject(value = R.id.fragment_stor_info_three_tvbuttom_ok)
    private TextView tvbtnok;
    @ViewInject(value = R.id.fragment_stor_info_three_tvbankname)
    private TextView tvbankname;
    @ViewInject(value = R.id.fragment_stor_info_three_etname)
    private EditText etname;
    @ViewInject(value = R.id.fragment_stor_info_three_etshenfenzheng)
    private EditText etnumber;
    private StorShenqingModel model;
    private boolean looding = true;//获取银行卡是否返回
    private String bankname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fratgment_stor_info_three, null);
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
        etname.setText(shenqingInfo.getData().getBank_name());
        etnumber.setText(shenqingInfo.getData().getBank_num());
        bankname = shenqingInfo.getData().getBank_uname();
        String s1 = getResources().getString(R.string.fragment_stor_info_three_et5);
        String text = s1 + bankname;
        Text_Size.setSize(getActivity(), tvbankname, text, 0, s1.length(), "#646464", 12, s1.length(), text.length(), "#dd646464", 10);

    }

    private void init() {
        model = new StorShenqingModel();
    }

    /**
     * 监听器
     */
    private void setlistener() {
        tvbtnok.setOnClickListener(this);
        etnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 6 && looding) {
                    looding = false;
                    getbankname(s.toString().trim());
                }
            }
        });
    }

    /**
     * 获取银行卡名称
     */
    private void getbankname(String s) {
        model.getBankname(s, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                bankname = (String) object;
                String s1 = getResources().getString(R.string.fragment_stor_info_three_et5);
                String text = s1 + object.toString();
                Text_Size.setSize(getActivity(), tvbankname, text, 0, s1.length(), "#646464", 12, s1.length(), text.length(), "#dd646464", 10);
                looding = true;
            }

            @Override
            public void onError(Object object) {
                bankname = null;
                looding = true;
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_stor_info_three_tvbuttom_ok://点击下一步
                toNext();
                break;
        }
    }

    /**
     * 点击下一步
     */
    private void toNext() {
        String number = etnumber.getText().toString().trim();
        String name = etname.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            MyAnimtion.getAnimator_DX(etname).start();
            Too.oo(getResources().getString(R.string.qingsshuruuser));
            return;
        }
        if (TextUtils.isEmpty(number)) {
            MyAnimtion.getAnimator_DX(etnumber).start();
            Too.oo(getResources().getString(R.string.qingshuruyinhangka));
            return;
        }
        if (TextUtils.isEmpty(bankname)) {
            MyAnimtion.getAnimator_DX(etnumber).start();
            Too.oo(getResources().getString(R.string.qingshuruyinhangka));
            return;
        }

        StorShenqinginfoActivity.instanse.setThreeParams(bankname, number, name);
        StorShenqinginfoActivity.instanse.setView(3);
    }
}
