package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yunyouzhiyuan.qianbaoshangcheng.ui.ActivityCollector;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class BaseActivity extends AppCompatActivity {
    protected LoadingDialog loadingDialog;
    private List<Call> calls = new ArrayList<>();

    public void setCalls(Call calls) {
        this.calls.add(calls);
    }

    public void clearCalls() {
        if (calls != null && !calls.isEmpty()) {
            for (int i = 0; i < calls.size(); i++) {
                if (calls.get(i) != null && !calls.get(i).isCanceled()) {
                    calls.get(i).cancel();
                }
            }
        }
        calls.clear();
        calls = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    protected void loodingShow() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    protected void dismissLooding() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }
}
