package com.arronyee.form.view.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.arronyee.form.R;
import com.arronyee.form.utils.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeying on 2018/9/14.
 */

public class MultiSelectPop<T> extends PopupWindow {

    private Listener mListener;
    private List<Integer> selectedPoses = new ArrayList<>();
    private MyBaseAdapter<T> myBaseAdapter;

    public interface Listener<T> {

        String getItemTextShow(T element);

        void onFinish(List<Integer> selectedPoses);
    }


    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public void init(final Activity activity, String titleStr, List<T> data, List<Integer> poses) {
        View contentView = LayoutInflater.from(activity).inflate(R.layout.pop_multi_select, null);
        if (poses == null) {
            selectedPoses = new ArrayList<>();
        } else {
            selectedPoses = poses;
        }
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        setContentView(contentView);
        setHeight(height / 4 * 3);
        setWidth(width / 3 * 2);
        View close = contentView.findViewById(R.id.event_close);
        TextView title = (TextView) contentView.findViewById(R.id.title);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        title.setText(titleStr);
        contentView.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onFinish(selectedPoses);
                }
                dismiss();
            }
        });
        contentView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ListView listView = (ListView) contentView.findViewById(R.id.grid_listView);
        myBaseAdapter= new MyBaseAdapter<T>(activity, data, R.layout.item_pop_multi_select) {
            @Override
            public void bindData(ViewHolder viewHolder, T element, int i) {
                TextView tv = viewHolder.getView(R.id.text);
                if (mListener != null) {
                    tv.setText(mListener.getItemTextShow(element));
                }
                CheckBox checkBox = viewHolder.getView(R.id.check_box);
                checkBox.setChecked(selectedPoses.contains(i));
            }
        };
        listView.setAdapter(myBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedPoses.contains(position)) {
                    selectedPoses.remove((Integer) position);
                } else {
                    selectedPoses.add(position);
                }
                myBaseAdapter.notifyDataSetChanged();
            }
        });
    }

    public void show(final View rootView, Listener<T> listener) {
        final Activity activity = (Activity) rootView.getContext();
        this.mListener = listener;
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                AnimationUtils.darkBackgroundColor(activity.getWindow(), 1.0f);
            }
        });
        AnimationUtils.darkBackgroundColor(activity.getWindow(), 0.5f);
        showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }

    public void refresh(List<T> data){
        myBaseAdapter.refreshData(data);
    }

}
