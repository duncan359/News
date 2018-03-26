package com.duncan.read.News.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duncan.read.R;
import com.duncan.read.domain.data.GetStoryResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Duncan Lim on 22/3/2018.
 */

public class StoryListAdapter extends BaseAdapter {
    private Context mContext;
    private List<GetStoryResponse> mStoryList;

    public StoryListAdapter(Context mContext, List<GetStoryResponse> mStoryList) {
        this.mContext = mContext;
        this.mStoryList = mStoryList;
    }

    public void addListItemToAdapter(List<GetStoryResponse> list) {
        //Add list to current array list of data
        mStoryList.addAll(list);
        //Notify UI
        this.notifyDataSetChanged();
    }

    public void resetListItemToAdapter(List<GetStoryResponse> list) {
        //Add list to current array list of data
        mStoryList.clear();
        mStoryList.addAll(list);
        //Notify UI
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mStoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(mContext, R.layout.item_product_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.title.setText(String.valueOf(position+1+": "+mStoryList.get(position).getTitle()));
        holder.name.setText(String.valueOf(mStoryList.get(position).getUrl()));
        Calendar calendar = Calendar.getInstance();
        long time = mStoryList.get(position).getTime()*1000;
        calendar.setTimeInMillis(time);
        //Date d = new Date(mStoryList.get(position).getTime());
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy,HH:mm");
       // f.setTimeZone(tz);
        String s = f.format(calendar.getTime());
        holder.time.setText(mContext.getResources().getString(R.string.txt_post,s,String.valueOf(mStoryList.get(position).getBy())));
        holder.num.setText(String.valueOf(mStoryList.get(position).getDescendants()));
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_title)
        TextView title;
        @Bind(R.id.item_name)
        TextView name;
        @Bind(R.id.item_time)
        TextView time;
        @Bind(R.id.item_num)
        TextView num;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }




}
