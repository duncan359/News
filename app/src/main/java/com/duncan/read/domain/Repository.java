package com.duncan.read.domain;

import android.support.annotation.NonNull;


import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetReplyResponse;
import com.duncan.read.domain.data.GetStoryResponse;

import java.util.List;

/**
 * Created by Duncan Lim on 31/7/2017.
 */

public interface Repository {
    public void TopNews(String URL, @NonNull GetTopNewsCallBack callBack);

    interface GetTopNewsCallBack {
        void onSuccess(List<Integer> response);

        void onFailure(Exception e);
    }

    public void GetStory(String URL, @NonNull GetStoryCallBack callBack);

    interface GetStoryCallBack {
        void onSuccess(GetStoryResponse response);

        void onFailure(Exception e);
    }

    public void GetComment(String URL, @NonNull GetCommentCallBack callBack);

    interface GetCommentCallBack {
        void onSuccess(GetCommentResponse response);

        void onFailure(Exception e);
    }

    public void GetReply(String URL, @NonNull GetReplyCallBack callBack);

    interface GetReplyCallBack {
        void onSuccess(GetReplyResponse response);

        void onFailure(Exception e);
    }

}
