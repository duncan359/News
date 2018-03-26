package com.duncan.read.News.presenter;

import android.support.annotation.NonNull;

import com.duncan.read.BasePresenter;
import com.duncan.read.BaseView;
import com.duncan.read.News.domain.UseCase;
import com.duncan.read.News.domain.UseCaseImpl;
import com.duncan.read.News.view.NewsListingView;
import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetReplyResponse;
import com.duncan.read.domain.data.GetStoryResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duncan Lim on 21/3/2018.
 */

public class Presenter extends BasePresenter {

    private UseCase Usecase;
    private EventBus eventBus;
    private NewsListingView listingView;
    private int count;
    private int replycount;
    private int commentcount;
    List<GetStoryResponse> resultlist = new ArrayList<GetStoryResponse>();
    List<GetCommentResponse> commentlist = new ArrayList<GetCommentResponse>();
    List<GetReplyResponse> replylist = new ArrayList<GetReplyResponse>();
    public Presenter(UseCase useCase,  EventBus eventBus)
    {
        this.Usecase=useCase;
        this.eventBus=eventBus;
    }

    public void setView(NewsListingView loginView) {
        this.listingView = loginView;
    }

    public void GetTopNews(@NonNull final String Url)
    {
        Usecase.GetTopNewsListing(Url);
    }

    public void GetStory(@NonNull final List<Integer> arraylist)
    {
        resultlist.clear();
        count = arraylist.size();
        for(int i=0;i<arraylist.size();i++)
        {
        Usecase.GetStoryListing("item/"+arraylist.get(i)+".json");
        }
    }

    public void GetComment(@NonNull final List<Integer> arraylist)
    {
        commentlist.clear();
        commentcount = arraylist.size();
        for(int i=0;i<arraylist.size();i++)
        {
            Usecase.GetCommentListing("item/"+arraylist.get(i)+".json");
        }
    }

    public void GetReply(@NonNull final List<Integer> arraylist)
    {
        replylist.clear();
        replycount = arraylist.size();
        for(int i=0;i<arraylist.size();i++)
        {
            Usecase.GetReplyListing("item/"+arraylist.get(i)+".json");
        }
    }
    @Override
    public void onPause() {
        hideLoading();
        this.listingView = null;
        eventBus.unregister(this);
    }

    @Override
    public void onResume(BaseView view) {
        this.listingView = (NewsListingView) view;
        eventBus.register(this);
    }

    @Override
    protected void showLoading() {
        if (listingView != null) {
            listingView.showProgress();
        }
    }

    @Override
    protected void hideLoading() {
        if (listingView != null) {
            listingView.hideProgress();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onActivateResult(UseCaseImpl.EventTopNewListing event) {

        hideLoading();
        //TODO check the login result and update view
        if (event.exception != null) {
            //TODO get error message from ErrorMessage factory
            listingView.onGetNewslistingFailure("Fail");

        } else {
            listingView.onGetNewslistingSuccess(event.reponse);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStorysesult(UseCaseImpl.EventStoryListing event) {

        hideLoading();
        //TODO check the login result and update view
        count--;
        if (event.exception != null) {
            //TODO get error message from ErrorMessage factory
            listingView.onGetStorylistingFailure("Fail");

        } else {

            resultlist.add(event.reponse);

            if(count==0) {
                listingView.onGetStorylistingSuccess(resultlist);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCommentsesult(UseCaseImpl.EventCommentListing event) {

        hideLoading();
        //TODO check the login result and update view
        commentcount--;
        if (event.exception != null) {
            //TODO get error message from ErrorMessage factory
            listingView.onGetCommentlistingFailure("Fail");

        } else {

            commentlist.add(event.reponse);

            if(commentcount==0) {
                listingView.onGetCommentlistingSuccess(commentlist);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReplyesult(UseCaseImpl.EventReplyListing event) {

        hideLoading();
        //TODO check the login result and update view
        replycount--;
        if (event.exception != null) {
            //TODO get error message from ErrorMessage factory
            listingView.onGetReplylistingFailure("Fail");

        } else {

            replylist.add(event.reponse);
            if(replycount==0) {
                listingView.onGetReplylistingSuccess(replylist);
            }
        }
    }
}
