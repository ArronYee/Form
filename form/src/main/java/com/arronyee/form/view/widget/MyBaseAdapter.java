
package com.arronyee.form.view.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> searchList;
    protected List<T> tempList;
    protected List<T> list;
    protected int layoutId;
    protected boolean isViewGroup;
    protected LayoutInflater mInflater;
    private int count;

    public MyBaseAdapter(Context context, List<T> list, int layoutId) {
        this.isViewGroup = false;
        this.count = -1;
        this.mContext = context;
        this.tempList = this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }

    public MyBaseAdapter(Context context, List<T> list, int count, int layoutId) {
        this(context, list, layoutId);
        this.count = count;
    }

    public MyBaseAdapter(Context context, boolean isViewGroup, List<T> list, int layoutId) {
        this(context, list, layoutId);
        this.isViewGroup = isViewGroup;
    }

    public int getCount() {
        if (this.count == -1) {
            return this.list == null ? 0 : this.list.size();
        } else {
            return this.list.size() - this.count;
        }
    }

    public T getItem(int position) {
        return this.list.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(this.mContext, this.isViewGroup, parent, convertView, this.layoutId, this.mInflater);
        this.bindData(viewHolder, this.getItem(position), position);
        return viewHolder.getConvertView();
    }

    public abstract void bindData(ViewHolder var1, T var2, int var3);

    public void refreshData(List<T> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public synchronized List<T> getList() {
        return this.list;
    }

}

