package com.duncan.read.News.domain;

import com.duncan.read.domain.Repository;
import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetReplyResponse;
import com.duncan.read.domain.data.GetStoryResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Duncan Lim on 21/3/2018.
 */

public class UseCaseImpl implements UseCase{
    private final Repository repository;
    private final EventBus eventBus;

    public UseCaseImpl(Repository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;

    }

    @Override
    public void GetTopNewsListing(String Url) {
        repository.TopNews(Url, new Repository.GetTopNewsCallBack() {
            @Override
            public void onSuccess(List<Integer> response) {
                eventBus.post(new EventTopNewListing(response, null));
            }

            @Override
            public void onFailure(Exception e) {
                eventBus.post(new EventTopNewListing(null, e));
            }
        });
    }

    @Override
    public void GetStoryListing(String Url) {
        repository.GetStory(Url, new Repository.GetStoryCallBack() {
            @Override
            public void onSuccess(GetStoryResponse response) {
                eventBus.post(new EventStoryListing(response, null));
            }

            @Override
            public void onFailure(Exception e) {
                eventBus.post(new EventStoryListing(null, e));
            }
        });
    }

    @Override
    public void GetCommentListing(String Url) {
        repository.GetComment(Url, new Repository.GetCommentCallBack() {
            @Override
            public void onSuccess(GetCommentResponse response) {
                eventBus.post(new EventCommentListing(response, null));
            }

            @Override
            public void onFailure(Exception e) {
                eventBus.post(new EventCommentListing(null, e));
            }
        });
    }

    @Override
    public void GetReplyListing(String Url) {
        repository.GetReply(Url, new Repository.GetReplyCallBack() {
            @Override
            public void onSuccess(GetReplyResponse response) {
                eventBus.post(new EventReplyListing(response, null));
            }

            @Override
            public void onFailure(Exception e) {
                eventBus.post(new EventReplyListing(null, e));
            }
        });
    }


    public static class EventTopNewListing {
        public final Exception exception;
        public final List<Integer> reponse;

        public EventTopNewListing(List<Integer> reponse, Exception exception) {
            this.exception = exception;
            this.reponse = reponse;

        }
    }

    public static class EventStoryListing {
        public final Exception exception;
        public final GetStoryResponse reponse;

        public EventStoryListing(GetStoryResponse reponse, Exception exception) {
            this.exception = exception;
            this.reponse = reponse;

        }
    }

    public static class EventCommentListing {
        public final Exception exception;
        public final GetCommentResponse reponse;

        public EventCommentListing(GetCommentResponse reponse, Exception exception) {
            this.exception = exception;
            this.reponse = reponse;

        }
    }
    public static class EventReplyListing {
        public final Exception exception;
        public final GetReplyResponse reponse;

        public EventReplyListing(GetReplyResponse reponse, Exception exception) {
            this.exception = exception;
            this.reponse = reponse;

        }
    }
}
