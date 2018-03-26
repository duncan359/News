package com.duncan.read.News.domain;

/**
 * Created by Duncan Lim on 21/3/2018.
 */

public interface UseCase {

    void GetTopNewsListing(String Url);
    void GetStoryListing(String Url);
    void GetCommentListing(String Url);
    void GetReplyListing(String Url);
}
