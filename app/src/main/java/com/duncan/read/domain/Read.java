package com.duncan.read.domain;

import android.app.Application;

import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetReplyResponse;
import com.duncan.read.domain.data.GetStoryResponse;

import java.util.ArrayList;

/**
 * Created by Duncan Lim on 22/3/2018.
 */

public class Read extends Application {
    public ArrayList<GetReplyResponse> ReplyList = new ArrayList<GetReplyResponse>();
    public ArrayList<GetCommentResponse> CommentList = new ArrayList<GetCommentResponse>();
    public ArrayList<GetStoryResponse> StoryList = new ArrayList<GetStoryResponse>();
    public ArrayList<Integer> StoryNoList = new ArrayList<Integer>();
    public ArrayList<Integer> ReplyNoList = new ArrayList<Integer>();
    public ArrayList<Integer> CommentNoList = new ArrayList<Integer>();

    public void addStoryList(GetStoryResponse add) {
        StoryList.add(add);
    }

    public ArrayList<GetStoryResponse> getStoryList() {
        return StoryList;
    }

    public void addStoryNoList(Integer add) {
        StoryNoList.add(add);
    }

    public ArrayList<Integer> getStoryNoList() {
        return StoryNoList;
    }

    public void clearStoryNoList()
    {
        StoryNoList.clear();
    }


    public ArrayList<GetCommentResponse> getCommentList() {
        return CommentList;
    }

    public void addCommentNoList(Integer add) {
        CommentNoList.add(add);
    }

    public ArrayList<Integer> getCommentNoList() {
        return CommentNoList;
    }

    public void clearCommentNoList()
    {
        CommentNoList.clear();
    }


    public ArrayList<GetReplyResponse> getReplyList() {
        return ReplyList;
    }


    public void addReplyNoList(Integer add) {
        ReplyNoList.add(add);
    }

    public ArrayList<Integer> getReplyNoList() {
        return ReplyNoList;
    }

}
