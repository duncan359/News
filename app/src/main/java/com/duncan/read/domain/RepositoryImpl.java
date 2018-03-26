package com.duncan.read.domain;

import android.support.annotation.NonNull;


import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetReplyResponse;
import com.duncan.read.domain.data.GetStoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Duncan Lim on 21/3/2018.
 */

public class RepositoryImpl implements Repository {
    public ConnectAPI client = Generator.create(ConnectAPI.class);


    @Override
    public void TopNews(String URL, @NonNull final GetTopNewsCallBack callBack) {
        final Call<List<Integer>> call = client.GetTopNews(URL);
        call.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                    if(callBack!=null)
                    {
                        callBack.onSuccess(response.body());
                    }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                if (callBack != null) {
                    callBack.onFailure(new Exception("Error"));
                }
            }

            });
    }

    @Override
    public void GetStory(String URL, @NonNull final GetStoryCallBack callBack) {
        final Call<GetStoryResponse> call = client.GetStory(URL);
        call.enqueue(new Callback<GetStoryResponse>() {
            @Override
            public void onResponse(Call<GetStoryResponse> call, Response<GetStoryResponse> response) {
                if(callBack!=null)
                {
                    callBack.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetStoryResponse> call, Throwable t) {
                if (callBack != null) {
                    callBack.onFailure(new Exception("Error"));
                }
            }

        });
    }

    @Override
    public void GetComment(String URL, @NonNull final GetCommentCallBack callBack) {
        final Call<GetCommentResponse> call = client.GetCommend(URL);
        call.enqueue(new Callback<GetCommentResponse>() {
            @Override
            public void onResponse(Call<GetCommentResponse> call, Response<GetCommentResponse> response) {
                if(callBack!=null)
                {
                    callBack.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetCommentResponse> call, Throwable t) {
                if (callBack != null) {
                    callBack.onFailure(new Exception("Error"));
                }
            }

        });
    }

    @Override
    public void GetReply(String URL, @NonNull final GetReplyCallBack callBack) {
        final Call<GetReplyResponse> call = client.GetReply(URL);
        call.enqueue(new Callback<GetReplyResponse>() {
            @Override
            public void onResponse(Call<GetReplyResponse> call, Response<GetReplyResponse> response) {
                if(callBack!=null)
                {
                    callBack.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetReplyResponse> call, Throwable t) {
                if (callBack != null) {
                    callBack.onFailure(new Exception("Error"));
                }
            }

        });
    }
}
