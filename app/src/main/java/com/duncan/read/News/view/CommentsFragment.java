package com.duncan.read.News.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.duncan.read.BaseActivity;
import com.duncan.read.BaseFragment;
import com.duncan.read.News.domain.UseCaseImpl;
import com.duncan.read.News.presenter.Presenter;
import com.duncan.read.R;
import com.duncan.read.domain.Read;
import com.duncan.read.domain.RepositoryImpl;
import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetReplyResponse;
import com.duncan.read.domain.data.GetStoryResponse;
import com.duncan.read.domain.presentation.logger.TimberLogger;
import com.fasterxml.jackson.core.sym.Name;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Duncan Lim on 22/3/2018.
 */

public class CommentsFragment extends BaseFragment implements NewsListingView{
    @Bind(R.id.listview_product)
    ListView lvProduct;

    @Bind(R.id.ll_home)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.item_title)
    TextView Title;

    @Bind(R.id.item_name)
    TextView Item;

    @Bind(R.id.item_time)
    TextView Time;

    @Bind(R.id.item_num)
    TextView Num;
    List<Integer> arraylist;
    EventBus eventBus;
    public Handler mHandler;
    public View ftView;
    public boolean isLoading = false;
    private CommentsListAdapter adapter;
    TimberLogger logger;
    int id=0;
    Presenter mPresenter;
    ArrayList<GetCommentResponse> CommentList;
    public int currentId=0;
    public CommentsFragment() {
        // Required empty public constructor
    }

    public static CommentsFragment newInstance() {
        Bundle args = new Bundle();

        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_comments, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // initializeInjector();
        //TODO initialize the presenter here
        //
        initializePresenter();
        mHandler = new MyHandler();
        Intent i = getActivity().getIntent();
        GetStoryResponse dene = (GetStoryResponse)i.getSerializableExtra("Class");


        CommentList = ((Read) getActivity().getApplication()).getCommentList();
        adapter = new CommentsListAdapter(getActivity().getApplicationContext(), CommentList);
        LayoutInflater li = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view, null);
        lvProduct.setAdapter(adapter);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                     @Override
                                                     public void onRefresh() {
                                                         refreshContent();
                                                     }
                                                 });
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetCommentResponse result =  (GetCommentResponse) adapter.getItem(position);
                if(result.getKids()!=null)
                ((BaseActivity) getActivity()).getEventBus().post(new EventGetResult(result,"Success"));
            }
        });
        lvProduct.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lvProduct.requestFocus();
                //Check when scroll to last item in listview, in this tut, init data in listview = 10 item
                if(view.getLastVisiblePosition() == totalItemCount-1 && lvProduct.getCount() >=20&& isLoading == false) {
                    isLoading = true;
                    update();
                }
                int topRowVerticalPosition =
                        (lvProduct == null || lvProduct.getChildCount() == 0) ?
                                0 : lvProduct.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);


            }
        });
        updateItem(dene);
    }

    private void initializePresenter() {

        logger = new TimberLogger();
        eventBus = EventBus.getDefault();
        mPresenter = new Presenter(new UseCaseImpl(new RepositoryImpl(),eventBus), eventBus);
        mPresenter.setView(this);
    }

    private void updateItem(GetStoryResponse response)
    {
        if(response!=null) {
            ((Read) getActivity().getApplication()).clearCommentNoList();
            id = response.getId();
            if (response.getTitle() != null)
                Title.setText(String.valueOf(response.getTitle()));
            if (response.getUrl() != null) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                    Item.setText(Html.fromHtml("<a href=" + response.getUrl() + ">" + response.getUrl() + "</a>", Html.FROM_HTML_MODE_COMPACT));
                    Item.setMovementMethod(LinkMovementMethod.getInstance());
                } else {
                    Item.setText(Html.fromHtml("<a href=" + response.getUrl() + ">" + response.getUrl() + "</a>"));
                    Item.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
            Calendar calendar = Calendar.getInstance();
            long time = response.getTime() * 1000;
            calendar.setTimeInMillis(time);
            //Date d = new Date(mStoryList.get(position).getTime());
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy,HH:mm");
            // f.setTimeZone(tz);
            String s = f.format(calendar.getTime());
            Time.setText(getResources().getString(R.string.txt_post, s, String.valueOf(response.getBy())));
            Num.setText(String.valueOf(response.getDescendants()));
            arraylist = new ArrayList<Integer>();
            if (response.getKids() != null) {
                List<Integer> kidlist = response.getKids();
                if (kidlist.size() > 0) {
                    for (int i = 0; i < kidlist.size(); i++) {
                        ((Read) getActivity().getApplication()).addCommentNoList(kidlist.get(i));
                        if (i < 20) {
                            arraylist.add(kidlist.get(i));
                            Log.v("Duncan", "" + arraylist.get(i));
                        }

                    }
                    mPresenter.GetComment(arraylist);
                }
            }
            if (arraylist.size() > 0) {
                mHandler.sendEmptyMessage(0);
            }else {
                mHandler.sendEmptyMessage(3);
            }
        }
    }
    private void refreshContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Integer> integers = new ArrayList<Integer>();
                integers.clear();
                integers.add(id);
                isLoading = true;
                currentId=0;
                mPresenter.GetStory(integers);
            }
            },0);
        }
    public void update()
    {
        List<Integer> arraylist = new ArrayList<Integer>();
        List<Integer> list=((Read) getActivity().getApplication()).getCommentNoList();
        arraylist.clear();
        for(int i =0;i<10;i++)
        {
            Log.v("Duncan","Size "+list.size());
            if(currentId+i<list.size()) {
                arraylist.add(list.get(currentId+i));
                Log.v("Duncan","Wei"+currentId+" ");
            }

        }
        if(arraylist.size()>0) {
            mPresenter.GetComment(arraylist);
            mHandler.sendEmptyMessage(0);
        }
    }
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //Add loading view during search processing
                    lvProduct.addFooterView(ftView);
                    break;
                case 1:
                    //Update data adapter and UI
                    adapter.addListItemToAdapter((ArrayList<GetCommentResponse>)msg.obj);
                    //Remove loading view after update listview
                    lvProduct.removeFooterView(ftView);
                    mSwipeRefreshLayout.setRefreshing(false);
                    if(((Read) getActivity().getApplication()).getCommentNoList().size()>currentId)
                    isLoading=false;
                    break;
                case 2:
                    adapter.resetListItemToAdapter((ArrayList<GetCommentResponse>)msg.obj);
                    mSwipeRefreshLayout.setRefreshing(false);
                    lvProduct.removeFooterView(ftView);
                    if(((Read) getActivity().getApplication()).getCommentNoList().size()>currentId)
                    isLoading=false;
                    break;
                case 3:
                    lvProduct.removeFooterView(ftView);
                    mSwipeRefreshLayout.setRefreshing(false);
                    adapter.resetListItemToAdapter(CommentList);
                default:
                    break;
            }
        }
    }
    public class ThreadGetMoreData extends Thread {
        @Override
        public void run() {
            List<Integer> arraylist = new ArrayList<Integer>();
            List<Integer> list=((Read) getActivity().getApplication()).getStoryNoList();
            arraylist.clear();
            for(int i =0;i<10;i++)
            {

                if(currentId+i<list.size()) {
                    arraylist.add(list.get(currentId+i));

                }

            }
            mPresenter.GetStory(arraylist);
        }
    }

    @Override
    protected void onResumePresenter() {
        mPresenter.onResume(this);
    }

    @Override
    protected void onPausePresenter() {
        mPresenter.onPause();
    }

    @Override
    public void onGetNewslistingSuccess(List<Integer> resultList) {
    }

    @Override
    public void onGetNewslistingFailure(String errorMessgae) {
    }

    @Override
    public void onGetStorylistingSuccess(List<GetStoryResponse> resultList) {
        if(resultList.size()>0)
        {
            currentId=0;
            updateItem(resultList.get(0));
        }
    }

    @Override
    public void onGetStorylistingFailure(String errorMessgae) {

    }

    @Override
    public void onGetCommentlistingSuccess(List<GetCommentResponse> resultList) {
        Message msg;
        ArrayList<GetCommentResponse> lstResult = new ArrayList<GetCommentResponse>();
        lstResult.clear();
        if(resultList.size()>0) {
        for(int i =0;i<resultList.size();i++)
        {
            lstResult.add(resultList.get(i));
            currentId++;
        }
        if(currentId<=20)
        {
            msg = mHandler.obtainMessage(2, lstResult);
        }
        else {
            msg = mHandler.obtainMessage(1, lstResult);
        }
        mHandler.sendMessage(msg);
        }else {
            mHandler.sendEmptyMessage(3);
        }
    }

    @Override
    public void onGetCommentlistingFailure(String errorMessgae) {

    }

    @Override
    public void onGetReplylistingSuccess(List<GetReplyResponse> resultList) {

    }

    @Override
    public void onGetReplylistingFailure(String errorMessgae) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    public static class EventGetResult {
        public final String reponse;
        public final GetCommentResponse result;

        public EventGetResult(GetCommentResponse result,String reponse) {
            this.result=result;
            this.reponse = reponse;

        }
    }


}
