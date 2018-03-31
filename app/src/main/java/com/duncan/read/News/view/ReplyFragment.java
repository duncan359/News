package com.duncan.read.News.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class ReplyFragment extends BaseFragment implements NewsListingView{
    @Bind(R.id.listview_product)
    ListView lvProduct;

    @Bind(R.id.ll_home)
    SwipeRefreshLayout mSwipeRefreshLayout;


    List<Integer> arraylist;
    EventBus eventBus;
    public Handler mHandler;
    public View ftView;
    public boolean isLoading = false;
    private ReplyListAdapter adapter;
    int id=0;
    GetNewsPresenter mGetNewsPresenter;
    ArrayList<GetReplyResponse> ReplyList;
    public int currentId=0;
    public ReplyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reply, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializePresenter();
        mHandler = new MyHandler();
        Intent i = getActivity().getIntent();
        GetCommentResponse dene = (GetCommentResponse)i.getSerializableExtra("Class");
        ReplyList = ((Read) getActivity().getApplication()).getReplyList();
        adapter = new ReplyListAdapter(getActivity().getApplicationContext(), ReplyList);
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
            }
        });
        lvProduct.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        lvProduct.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lvProduct.requestFocus();
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
        eventBus = EventBus.getDefault();
        mGetNewsPresenter = new GetNewsPresenter(new UseCaseImpl(new RepositoryImpl(),eventBus), eventBus);
        mGetNewsPresenter.setView(this);
    }

    private void updateItem(GetCommentResponse response)
    {
        if(response!=null) {
            ((Read) getActivity().getApplication()).clearCommentNoList();
            id = response.getId();
            arraylist = new ArrayList<>();
            if (response.getKids() != null) {
                List<Integer> kidlist = response.getKids();
                if (kidlist.size() > 0) {
                    for (int i = 0; i < kidlist.size(); i++) {
                        ((Read) getActivity().getApplication()).addReplyNoList(kidlist.get(i));
                        if (i < 20) {
                            arraylist.add(kidlist.get(i));
                        }
                    }
                    mHandler.sendEmptyMessage(0);
                    mGetNewsPresenter.GetReply(arraylist);

                } else {
                    mHandler.sendEmptyMessage(0);
                }

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
                mGetNewsPresenter.GetComment(integers);
            }
            },0);
        }
    public void update()
    {
        List<Integer> arraylist = new ArrayList<Integer>();
        List<Integer> list=((Read) getActivity().getApplication()).getReplyNoList();
        arraylist.clear();
        for(int i =0;i<10;i++)
        {
            if(currentId+i<list.size()) {
                arraylist.add(list.get(currentId+i));
            }

        }
        if(arraylist.size()>0) {
            mGetNewsPresenter.GetReply(arraylist);
            mHandler.sendEmptyMessage(0);
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
                    adapter.addListItemToAdapter((ArrayList<GetReplyResponse>)msg.obj);
                    mSwipeRefreshLayout.setRefreshing(false);
                    lvProduct.removeFooterView(ftView);
                    if(((Read) getActivity().getApplication()).getReplyNoList().size()>currentId)
                    isLoading=false;
                    break;
                case 2:
                    adapter.resetListItemToAdapter((ArrayList<GetReplyResponse>)msg.obj);
                    lvProduct.removeFooterView(ftView);
                    mSwipeRefreshLayout.setRefreshing(false);
                    if(((Read) getActivity().getApplication()).getReplyNoList().size()>currentId)
                    isLoading=false;
                    break;
                case 3:
                    lvProduct.removeFooterView(ftView);
                    mSwipeRefreshLayout.setRefreshing(false);
                    adapter.resetListItemToAdapter(ReplyList);
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
    }

    @Override
    public void onGetStorylistingSuccess(List<GetStoryResponse> resultList) {

    }


    @Override
    public void onGetCommentlistingSuccess(List<GetCommentResponse> resultList) {
        if(resultList.size()>0)
        {
            currentId=0;
            updateItem(resultList.get(0));
        }

    }


    @Override
    public void onGetReplylistingSuccess(List<GetReplyResponse> resultList) {
        Message msg;
        ArrayList<GetReplyResponse> lstResult = new ArrayList<GetReplyResponse>();
        lstResult.clear();
        if(resultList.size()>0) {
            for (int i = 0; i < resultList.size(); i++) {
                lstResult.add(resultList.get(i));
                currentId++;
            }
            if (currentId <= 20) {
                msg = mHandler.obtainMessage(2, lstResult);
            } else {
                msg = mHandler.obtainMessage(1, lstResult);
            }
            mHandler.sendMessage(msg);
        }else {
            mHandler.sendEmptyMessage(3);
        }
    }


}
