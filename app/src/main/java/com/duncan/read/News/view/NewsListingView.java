package com.duncan.read.News.view;

import com.duncan.read.BaseView;
import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetReplyResponse;
import com.duncan.read.domain.data.GetStoryResponse;

import java.util.List;

/**
 * Created by Duncan Lim on 22/3/2018.
 */

public interface NewsListingView extends BaseView {

    void onGetNewslistingSuccess(List<Integer> resultList);

    void onGetStorylistingSuccess(List<GetStoryResponse> resultList);

    void onGetCommentlistingSuccess(List<GetCommentResponse> resultList);

    void onGetReplylistingSuccess(List<GetReplyResponse> resultList);

}
