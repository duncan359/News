package com.duncan.read.News.presenter;

import junit.framework.TestCase;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Duncan Lim on 23/3/2018.
 */
public class GetNewsPresenterTest extends TestCase {
    @Mock
    private com.duncan.read.News.domain.UseCase UseCase;
    @Mock
    private org.greenrobot.eventbus.EventBus EventBus;
    private GetNewsPresenter GetNewsPresenter;
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        GetNewsPresenter = new GetNewsPresenter(UseCase,EventBus);
    }

    public void tearDown() throws Exception {
        GetNewsPresenter =null;
    }


    public void testGetTopNews() throws Exception {
        GetNewsPresenter.GetTopNews(anyString());
        verify(UseCase).GetTopNewsListing(anyString());
    }

    public void testGetStory() throws Exception {
        List<Integer>mockedList = mock(List.class);
        GetNewsPresenter.GetStory(mockedList);
        for (int i = 0; i < mockedList.size(); i++)
            verify(UseCase).GetStoryListing(anyString());
    }

    public void testGetComment() throws Exception {
        List<Integer>mockedList = mock(List.class);
        GetNewsPresenter.GetComment(mockedList);
        for (int i = 0; i < mockedList.size(); i++)
            verify(UseCase).GetCommentListing(anyString());
    }

    public void testGetReply() throws Exception {
        List<Integer>mockedList = mock(List.class);
        GetNewsPresenter.GetReply(mockedList);
        for (int i = 0; i < mockedList.size(); i++)
            verify(UseCase).GetReplyListing(anyString());
    }


}