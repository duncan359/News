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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    TimberLogger logger;
    int id=0;
    Presenter mPresenter;
    ArrayList<GetReplyResponse> ReplyList;
    public int currentId=0;
    public ReplyFragment() {
        // Required empty public constructor
    }

    public static ReplyFragment newInstance() {
        Bundle args = new Bundle();

        ReplyFragment fragment = new ReplyFragment();
        fragment.setArguments(args);
        return fragment;
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

        // initializeInjector();
        //TODO initialize the presenter here
        //
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
//                GetCommentResponse result =  (GetCommentResponse) adapter.getItem(position);
//                ((BaseActivity) getActivity()).getEventBus().post(new EventGetResult(result,"Success"));
                //Do something
                //Ex: display msg with product id get from view.getTag
               //GetStoryResponse x =  (GetStoryResponse)adapter.getItem(position);
              //  Toast.makeText(getActivity().getApplicationContext(), "Clicked product id =" + adapter.getItem(position), Toast.LENGTH_SHORT).show();
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

    private void updateItem(GetCommentResponse response)
    {
        if(response!=null) {
            ((Read) getActivity().getApplication()).clearCommentNoList();
            id = response.getId();

            arraylist = new ArrayList<Integer>();
            if (response.getKids() != null) {
                List<Integer> kidlist = response.getKids();
                if (kidlist.size() > 0) {
                    for (int i = 0; i < kidlist.size(); i++) {
                        ((Read) getActivity().getApplication()).addReplyNoList(kidlist.get(i));
                        if (i < 20) {
                            arraylist.add(kidlist.get(i));
                            Log.v("Duncan", "" + arraylist.get(i));
                        }

                    }
                    mHandler.sendEmptyMessage(0);
                    mPresenter.GetReply(arraylist);

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
                mPresenter.GetComment(integers);
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
            Log.v("Duncan","Size "+list.size());
            if(currentId+i<list.size()) {
                arraylist.add(list.get(currentId+i));
                Log.v("Duncan","Wei"+currentId+" ");
            }

        }
//        if(currentId+arraylist.size()>list.size())
//        {
//            isLoading = true;
//        }
        if(arraylist.size()>0) {
            mPresenter.GetReply(arraylist);
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
                    adapter.addListItemToAdapter((ArrayList<GetReplyResponse>)msg.obj);
                    //Remove loading view after update listview
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

    }

    @Override
    public void onGetStorylistingFailure(String errorMessgae) {

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
    public void onGetCommentlistingFailure(String errorMessgae) {

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
