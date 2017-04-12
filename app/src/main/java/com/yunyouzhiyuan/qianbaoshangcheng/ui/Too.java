package com.yunyouzhiyuan.qianbaoshangcheng.ui;

import android.widget.Toast;

import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;


/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class Too {
    private static Toast toast;
//    private static TextView tv;

    public static void oo(Object str) {
        String s = "网络返回异常";
        if (str instanceof String) {
            s = String.valueOf(str);
        }
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(MyApplication.getContext(),s,Toast.LENGTH_SHORT);
//        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.toast, null);
//        tv = (TextView) view.findViewById(R.id.toast_tv);
//        tv.setText(s + "");
//        toast.setView(view);
//        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


}
