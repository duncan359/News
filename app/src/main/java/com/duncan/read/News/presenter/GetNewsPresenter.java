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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Duncan Lim on 21/3/2018.
 */

public class GetNewsPresenter extends BasePresenter {

    private UseCase Usecase;
    private EventBus eventBus;
    private NewsListingView listingView;
    private int count;
    private int replycount;
    private int commentcount;
    List<GetStoryResponse> resultlist = new ArrayList<GetStoryResponse>();
    List<GetCommentResponse> commentlist = new ArrayList<GetCommentResponse>();
    List<GetReplyResponse> replylist = new ArrayList<GetReplyResponse>();
    List<Integer> arraylist=new ArrayList<Integer>();
    public GetNewsPresenter(UseCase useCase, EventBus eventBus)
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

    public void GetStory(@NonNull final List<Integer> urllist)
    {
        this.arraylist=urllist;
        resultlist.clear();
        count = urllist.size();
        for(int i=0;i<urllist.size();i++)
        {
        Usecase.GetStoryListing("item/"+urllist.get(i)+".json");
        }
    }

    public void GetComment(@NonNull final List<Integer> urllist)
    {
        this.arraylist=urllist;
        commentlist.clear();
        commentcount = urllist.size();
        for(int i=0;i<urllist.size();i++)
        {
            Usecase.GetCommentListing("item/"+urllist.get(i)+".json");
        }
    }

    public void GetReply(@NonNull final List<Integer> urllist)
    {
        this.arraylist=urllist;
        replylist.clear();
        replycount = urllist.size();
        for(int i=0;i<urllist.size();i++)
        {
            Usecase.GetReplyListing("item/"+urllist.get(i)+".json");
        }
    }
    @Override
    public void onPause() {
        this.listingView = null;
        eventBus.unregister(this);
    }

    @Override
    public void onResume(BaseView view) {
        this.listingView = (NewsListingView) view;
        eventBus.register(this);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onActivateResult(UseCaseImpl.EventTopNewListing event) {
        if (event.exception != null) {
        } else {
            listingView.onGetNewslistingSuccess(event.reponse);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStoryresult(UseCaseImpl.EventStoryListing event) {
        count--;
        resultlist.add(event.reponse);
        if (event.exception == null) {
            if(count==0) {
                for(int index=0;index<resultlist.size();index++)
                {
                    if(resultlist.get(index)!=null)
                    resultlist.add(arraylist.indexOf(resultlist.get(index).getId()),resultlist.remove(index));
                }

                listingView.onGetStorylistingSuccess(resultlist);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCommentsesult(UseCaseImpl.EventCommentListing event) {
        commentcount--;
        commentlist.add(event.reponse);
        if (event.exception == null) {
            if(commentcount==0) {
                for(int index=0;index<commentlist.size();index++)
                {
                    if(commentlist.get(index)!=null)
                    commentlist.add(arraylist.indexOf(commentlist.get(index).getId()),commentlist.remove(index));
                }
                listingView.onGetCommentlistingSuccess(commentlist);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReplyesult(UseCaseImpl.EventReplyListing event) {
        replycount--;
        replylist.add(event.reponse);
        if (event.exception == null) {

            if(replycount==0) {
                for(int index=0;index<replylist.size();index++)
                {
                    if(replylist.get(index)!=null)
                    replylist.add(arraylist.indexOf(replylist.get(index).getId()),replylist.remove(index));
                }
                listingView.onGetReplylistingSuccess(replylist);
            }
        }
    }
}
