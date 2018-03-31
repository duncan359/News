package com.duncan.read.News.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.duncan.read.BaseActivity;
import com.duncan.read.BaseFragment;
import com.duncan.read.News.domain.UseCaseImpl;
import com.duncan.read.News.presenter.GetNewsPresenter;
import com.duncan.read.R;
import com.duncan.read.domain.Read;
import com.duncan.read.domain.RepositoryImpl;
import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetReplyResponse;
import com.duncan.read.domain.data.GetStoryResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Duncan Lim on 22/3/2018.
 */

public class MainFragment extends BaseFragment implements NewsListingView{
    @Bind(R.id.listview_product)
    ListView lvProduct;

    @Bind(R.id.ll_home)
    SwipeRefreshLayout mSwipeRefreshLayout;

    EventBus eventBus;
    public Handler mHandler;
    public View ftView;
    public boolean isLoading = false;
    private StoryListAdapter adapter;
    GetNewsPresenter mGetNewsPresenter;
    ArrayList<GetStoryResponse> StoryList;
    public int currentId=20;
    public MainFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializePresenter();
        mHandler = new MyHandler();
        StoryList = ((Read) getActivity().getApplication()).getStoryList();
        adapter = new StoryListAdapter(getActivity().getApplicationContext(), StoryList);
        LayoutInflater li = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view, null);
        lvProduct.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                     @Override
                                                     public void onRefresh() {
                                                         refreshContent();
                                                     }
                                                 });
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               GetStoryResponse result =  (GetStoryResponse)adapter.getItem(position);
               if(result!=null)
                ((BaseActivity) getActivity()).getEventBus().post(new EventGetResult(result,"Success"));
            }
        });
        lvProduct.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view.getLastVisiblePosition() == totalItemCount-1 && lvProduct.getCount() >=20&& isLoading == false) {
                    isLoading = true;
                    update();
                }
            }
        });
    }

    private void initializePresenter() {
        eventBus = EventBus.getDefault();
        mGetNewsPresenter = new GetNewsPresenter(new UseCaseImpl(new RepositoryImpl(),eventBus), eventBus);
        mGetNewsPresenter.setView(this);
    }


    private void refreshContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLoading = true;
                currentId=0;
                mGetNewsPresenter.GetTopNews("topstories.json");
            }
            },0);
        }
    public void update()
    {
        List<Integer> arraylist = new ArrayList<Integer>();
        List<Integer> list=((Read) getActivity().getApplication()).getStoryNoList();
        arraylist.clear();
        for(int i =0;i<10;i++)
        {
            if(currentId+i<list.size()) {
                arraylist.add(list.get(currentId+i));
            }
        }
        if(arraylist.size()>0) {
            mHandler.sendEmptyMessage(0);
            mGetNewsPresenter.GetStory(arraylist);
        }
    }
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    lvProduct.addFooterView(ftView);
                    break;
                case 1:
                    adapter.addListItemToAdapter((ArrayList<GetStoryResponse>)msg.obj);
                    lvProduct.removeFooterView(ftView);
                    isLoading=false;
                    break;
                case 2:
                    adapter.resetListItemToAdapter((ArrayList<GetStoryResponse>)msg.obj);
                    mSwipeRefreshLayout.setRefreshing(false);
                    lvProduct.removeFooterView(ftView);
                    isLoading=false;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onResumePresenter() {
        mGetNewsPresenter.onResume(this);
    }

    @Override
    protected void onPausePresenter() {
        mGetNewsPresenter.onPause();
    }

    @Override
    public void onGetNewslistingSuccess(List<Integer> resultList) {
        List<Integer> arraylist = new ArrayList<Integer>();
        ((Read) getActivity().getApplication()).clearStoryNoList();
        for(int i =0;i<resultList.size();i++)
        {
            ((Read) getActivity().getApplication()).addStoryNoList(resultList.get(i));
            if(i<20)
            {
                arraylist.add(resultList.get(i));
            }
        }
        mGetNewsPresenter.GetStory(arraylist);
    }



    @Override
    public void onGetStorylistingSuccess(List<GetStoryResponse> resultList) {
        Message msg;
        ArrayList<GetStoryResponse> lstResult = new ArrayList<GetStoryResponse>();
        lstResult.clear();
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
    }



    @Override
    public void onGetCommentlistingSuccess(List<GetCommentResponse> resultList) {

    }


    @Override
    public void onGetReplylistingSuccess(List<GetReplyResponse> resultList) {

    }




    public static class EventGetResult {
        public final String reponse;
        public final GetStoryResponse result;

        public EventGetResult(GetStoryResponse result,String reponse) {
            this.result=result;
            this.reponse = reponse;

        }
    }
}
