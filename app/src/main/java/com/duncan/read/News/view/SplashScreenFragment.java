package com.duncan.read.News.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class SplashScreenFragment extends BaseFragment implements NewsListingView {
    GetNewsPresenter mGetNewsPresenter;
    EventBus eventBus;




    public SplashScreenFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializePresenter();
        mGetNewsPresenter.setView(this);
    }

    private void initializePresenter() {
        
        eventBus = EventBus.getDefault();
        mGetNewsPresenter = new GetNewsPresenter(new UseCaseImpl(new RepositoryImpl(),eventBus), eventBus);
        mGetNewsPresenter.setView(this);
        mGetNewsPresenter.GetTopNews("topstories.json");
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
        ArrayList<GetStoryResponse> lstResult = new ArrayList<GetStoryResponse>();
        lstResult.clear();
        for(int i =0;i<resultList.size();i++)
        {
            ((Read) getActivity().getApplication()).addStoryList(resultList.get(i));
        }
        ((BaseActivity) getActivity()).getEventBus().post(new EventGetResult("Success"));
    }


    @Override
    public void onGetCommentlistingSuccess(List<GetCommentResponse> resultList) {

    }


    @Override
    public void onGetReplylistingSuccess(List<GetReplyResponse> resultList) {

    }


    public static class EventGetResult {
        public final String reponse;

        public EventGetResult(String reponse) {

            this.reponse = reponse;

        }
    }

}
