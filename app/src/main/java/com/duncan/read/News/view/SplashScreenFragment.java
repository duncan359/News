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

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashScreenFragment extends BaseFragment implements NewsListingView {
    Presenter mPresenter;
    EventBus eventBus;
    TimberLogger logger;




    public SplashScreenFragment() {
        // Required empty public constructor
    }

    public static SplashScreenFragment newInstance() {

        Bundle args = new Bundle();
        SplashScreenFragment fragment = new SplashScreenFragment();
        fragment.setArguments(args);
        return fragment;
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

        // initializeInjector();
        //TODO initialize the presenter here
        //mPresenter = new LoginPresenter(new LoginUsecaseImpl(new RepositoryImpl()), logger, eventBus);

        initializePresenter();
        mPresenter.setView(this);
    }

    private void initializePresenter() {

        logger = new TimberLogger();
        eventBus = EventBus.getDefault();
        mPresenter = new Presenter(new UseCaseImpl(new RepositoryImpl(),eventBus), eventBus);
        mPresenter.setView(this);
        mPresenter.GetTopNews("topstories.json");
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
    public void showProgress() {

        logger.logD("ActivateFragment", "showProgress");
    }

    @Override
    public void hideProgress() {
        logger.logD("ActivateFragment", "hideProgress");
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
        ArrayList<GetStoryResponse> lstResult = new ArrayList<GetStoryResponse>();
        lstResult.clear();
        for(int i =0;i<resultList.size();i++)
        {
            ((Read) getActivity().getApplication()).addStoryList(resultList.get(i));
        }
        ((BaseActivity) getActivity()).getEventBus().post(new EventGetResult("Success"));
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


    public static class EventGetResult {
        public final String reponse;

        public EventGetResult(String reponse) {

            this.reponse = reponse;

        }
    }


    /**
     * Event indicating forgotPassword has been pressed
     */
}
