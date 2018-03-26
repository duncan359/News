package com.duncan.read.News.view;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duncan.read.R;
import com.duncan.read.TextViewFixTouchConsume;
import com.duncan.read.domain.data.GetCommentResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Duncan Lim on 22/3/2018.
 */

public class CommentsListAdapter extends BaseAdapter {
    private Context mContext;
    private List<GetCommentResponse> mStoryList;

    public CommentsListAdapter(Context mContext, List<GetCommentResponse> mStoryList) {
        mStoryList.clear();
        notifyDataSetChanged();
        this.mContext = mContext;
        this.mStoryList = mStoryList;
    }

    public void addListItemToAdapter(List<GetCommentResponse> list) {
        //Add list to current array list of data
        mStoryList.addAll(list);
        //Notify UI
        this.notifyDataSetChanged();
    }

    public void resetListItemToAdapter(List<GetCommentResponse> list) {
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
            convertView = View.inflate(mContext, R.layout.item_comments_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        if(mStoryList.get(position).getText()!=null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                holder.title.setText(Html.fromHtml(mStoryList.get(position).getText(), Html.FROM_HTML_MODE_COMPACT));
                holder.title.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                holder.title.setText(Html.fromHtml(mStoryList.get(position).getText()));
                holder.title.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
        Calendar calendar = Calendar.getInstance();
        long time = mStoryList.get(position).getTime()*1000;
        calendar.setTimeInMillis(time);
        //Date d = new Date(mStoryList.get(position).getTime());
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy,HH:mm");
       // f.setTimeZone(tz);
        String s = f.format(calendar.getTime());
        holder.time.setText(mContext.getResources().getString(R.string.txt_comments,s,String.valueOf(mStoryList.get(position).getBy())));
        if(mStoryList.get(position).getKids()!=null) {
            holder.num.setText(String.valueOf(mStoryList.get(position).getKids().size()));
        }
        else
        {
            holder.num.setText("0");
        }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_title)
        TextView title;
        @Bind(R.id.item_time)
        TextView time;
        @Bind(R.id.item_num)
        TextView num;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }




}
