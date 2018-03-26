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
//    @Headers("Content-Type: application/raw")
//    @POST("Login")
//    Observable<SenderLoginResponse1> SenderLogin(@Body SenderLoginRequest1 request);

    @GET
    public Call<List<Integer>> GetTopNews(@Url String url);

    @GET
    public Call<GetStoryResponse> GetStory(@Url String url);

    @GET
    public Call<GetCommentResponse> GetCommend(@Url String url);

    @GET
    public Call<GetReplyResponse> GetReply(@Url String url);
}
