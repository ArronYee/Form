//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.arronyee.form.view.widget;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewHolder {
    private SparseArray<View> views = new SparseArray();
    private View convertView;

    private ViewHolder(View convertView) {
        this.convertView = convertView;
        convertView.setTag(this);
    }

    public static ViewHolder getViewHolder(Context context, View convertView, int layoutId, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(layoutId, (ViewGroup)null);
            return new ViewHolder(convertView);
        } else {
            return (ViewHolder)convertView.getTag();
        }
    }

    public static ViewHolder getViewHolder(Context context, boolean isViewGroup, ViewGroup parent, View convertView, int layoutId, LayoutInflater inflater) {
        if (convertView == null) {
            if (isViewGroup) {
                convertView = inflater.inflate(layoutId, parent, false);
            } else {
                convertView = inflater.inflate(layoutId, (ViewGroup)null);
            }

            return new ViewHolder(convertView);
        } else {
            return (ViewHolder)convertView.getTag();
        }
    }

    public View getConvertView() {
        return this.convertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = (View)this.views.get(viewId);
        if (view == null) {
            view = this.convertView.findViewById(viewId);
            this.views.put(viewId, view);
        }

        return (T) view;
    }
}
