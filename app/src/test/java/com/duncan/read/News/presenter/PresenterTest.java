package com.duncan.read.News.presenter;

import com.duncan.read.News.domain.UseCase;
import com.duncan.read.News.domain.UseCaseImpl;
import com.duncan.read.News.view.NewsListingView;
import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetReplyResponse;
import com.duncan.read.domain.data.GetStoryResponse;

import junit.framework.TestCase;

import org.greenrobot.eventbus.EventBus;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Duncan Lim on 23/3/2018.
 */
public class PresenterTest extends TestCase {
    @Mock
    private com.duncan.read.News.domain.UseCase UseCase;
    @Mock
    private org.greenrobot.eventbus.EventBus EventBus;
    @Mock
    private com.duncan.read.News.view.NewsListingView NewsListingView;
    @InjectMocks
    private Presenter instance;
    // private Collection resultlist;
    private Iterator <Integer> resultlistIterator;
    private Collection<Integer> commentlist;
    private Iterator<Integer> commentlistIterator;
    private Collection<Integer> replylist;
    private Iterator<Integer> replylistIterator;
    private Presenter Presenter;
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        Presenter = new Presenter(UseCase,EventBus);
    }

    public void tearDown() throws Exception {
        Presenter=null;
    }


    public void testGetTopNews() throws Exception {
        Presenter.GetTopNews(anyString());
        verify(UseCase).GetTopNewsListing(anyString());
    }

    public void testGetStory() throws Exception {
        List<Integer>mockedList = mock(List.class);
        Presenter.GetStory(mockedList);
        for (int i = 0; i < mockedList.size(); i++)
            verify(UseCase).GetStoryListing(anyString());
    }

    public void testGetComment() throws Exception {
        List<Integer>mockedList = mock(List.class);
        Presenter.GetComment(mockedList);
        for (int i = 0; i < mockedList.size(); i++)
            verify(UseCase).GetCommentListing(anyString());
    }

    public void testGetReply() throws Exception {
        List<Integer>mockedList = mock(List.class);
        Presenter.GetReply(mockedList);
        for (int i = 0; i < mockedList.size(); i++)
            verify(UseCase).GetReplyListing(anyString());
    }


}