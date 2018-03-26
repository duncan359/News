package com.duncan.read.News.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

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
    TimberLogger logger;
    Presenter mPresenter;
    ArrayList<GetStoryResponse> StoryList;
    public int currentId=20;
    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
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

        // initializeInjector();
        //TODO initialize the presenter here
        //
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
                //Do something
                //Ex: display msg with product id get from view.getTag
               GetStoryResponse result =  (GetStoryResponse)adapter.getItem(position);
                ((BaseActivity) getActivity()).getEventBus().post(new EventGetResult(result,"Success"));
              //  Toast.makeText(getActivity().getApplicationContext(), "Clicked product id =" + adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });
        lvProduct.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //Check when scroll to last item in listview, in this tut, init data in listview = 10 item
                if(view.getLastVisiblePosition() == totalItemCount-1 && lvProduct.getCount() >=20&& isLoading == false) {
                    isLoading = true;
//                    Thread thread = new ThreadGetMoreData();
////////                    //Start thread
//                    thread.start();
                    Log.v("Duncan",""+currentId);
                    update();
                }

            }
        });
    }

    private void initializePresenter() {

        logger = new TimberLogger();
        eventBus = EventBus.getDefault();
        mPresenter = new Presenter(new UseCaseImpl(new RepositoryImpl(),eventBus), eventBus);
        mPresenter.setView(this);
    }


    private void refreshContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLoading = true;
                currentId=0;
                mPresenter.GetTopNews("topstories.json");
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
            Log.v("Duncan","Size "+list.size());
            if(currentId+i<list.size()) {
                arraylist.add(list.get(currentId+i));
                Log.v("Duncan","Wei"+currentId+" ");
            }

        }
        if(arraylist.size()>0) {
            mHandler.sendEmptyMessage(0);
            mPresenter.GetStory(arraylist);
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
                    adapter.addListItemToAdapter((ArrayList<GetStoryResponse>)msg.obj);
                    //Remove loading view after update listview
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
    public class ThreadGetMoreData extends Thread {
        @Override
        public void run() {
            List<Integer> arraylist = new ArrayList<Integer>();
            List<Integer> list=((Read) getActivity().getApplication()).getStoryNoList();
            arraylist.clear();
            for(int i =0;i<10;i++)
            {
                Log.v("Duncan","Size "+list.size());
                if(currentId+i<list.size()) {
                    arraylist.add(list.get(currentId+i));
                    Log.v("Duncan","Wei"+currentId+" ");
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
        List<Integer> arraylist = new ArrayList<Integer>();
        ((Read) getActivity().getApplication()).clearStoryNoList();
        for(int i =0;i<resultList.size();i++)
        {
            ((Read) getActivity().getApplication()).addStoryNoList(resultList.get(i));
            if(i<20)
            {
                arraylist.add(resultList.get(i));
                Log.v("Duncan",""+arraylist.get(i));
            }

        }
       
        mPresenter.GetStory(arraylist);
    }

    @Override
    public void onGetNewslistingFailure(String errorMessgae) {

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
    public void onGetStorylistingFailure(String errorMessgae) {

    }

    @Override
    public void onGetCommentlistingSuccess(List<GetCommentResponse> resultList) {

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
        public final GetStoryResponse result;

        public EventGetResult(GetStoryResponse result,String reponse) {
            this.result=result;
            this.reponse = reponse;

        }
    }
}
