package com.duncan.read.domain;



import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetReplyResponse;
import com.duncan.read.domain.data.GetStoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Duncan Lim on 21/3/2018.
 */

public interface ConnectAPI {

    @GET
     Call<List<Integer>> GetTopNews(@Url String url);

    @GET
     Call<GetStoryResponse> GetStory(@Url String url);

    @GET
     Call<GetCommentResponse> GetCommend(@Url String url);

    @GET
     Call<GetReplyResponse> GetReply(@Url String url);
}
