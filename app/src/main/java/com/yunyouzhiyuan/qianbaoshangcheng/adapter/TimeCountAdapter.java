package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.app.Activity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/23.
 */

public class TimeCountAdapter extends MyAdapter<String> {
    private ArrayMap<Integer, Boolean> map;

    public TimeCountAdapter(Activity context, List<String> data) {
        super(context, data);
        this.map = new ArrayMap<>();
        for (int i = 0; i < getData().size(); i++) {
            map.put(i, false);
        }
    }

    public void setMap(List<String> data) {
        this.map.clear();
        for (int i = 0; i < getData().size(); i++) {
            map.put(i, false);
        }
        notifyDataSetChanged();
    }

    public String getAttrValue() {
        for (ArrayMap.Entry<Integer, Boolean> entity : map.entrySet()) {
            LogUtils.d("Entry:key=" + entity.getKey() + "value=" + entity.getValue());
            if (entity.getValue()) {
                return getData().get(entity.getKey());
            }
        }
        return null;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CheckedTextView textView = (CheckedTextView) getLayoutInflater().inflate(R.layout.flowlayout_new_riqi, parent, false);
        textView.setText(getData().get(position));
        textView.setChecked(map.get(position));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ArrayMap.Entry<Integer, Boolean> entity : map.entrySet()) {
                    entity.setValue(false);
                }
                map.put(position, true);
                notifyDataSetChanged();
            }
        });
        return textView;
    }
}
