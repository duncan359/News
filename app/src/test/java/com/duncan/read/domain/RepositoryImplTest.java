package com.duncan.read.domain;

import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetReplyResponse;
import com.duncan.read.domain.data.GetStoryResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Created by Duncan Lim on 23/3/2018.
 */
public class RepositoryImplTest {

    private ConnectAPI apiService;

    @Before
    public void setUp() throws Exception {
        apiService = Generator.create(ConnectAPI.class);
    }

    @Test
    public void TestTopStoreis() {
        List<Integer> list = null;
        try {
            list = apiService.GetTopNews("topstories.json").execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(list.size(), is(not(0)));
        System.out.print(list.size());
    }

    @Test
    public void TestStoreis() {
        GetStoryResponse list = null;
        try {
            list = apiService.GetStory("item/16652626.json").execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(list.getId(), is(not(0)));
    }

    @Test
    public void TestComments() {
        GetCommentResponse list = null;
        try {
            list = apiService.GetCommend("item/2921983.json").execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(list.getId(), is(not(0)));
    }

    @Test
    public void TestReplyStoreis() {
        GetReplyResponse list = null;
        try {
            list = apiService.GetReply("item/2922097.json").execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(list.getId(), is(not(0)));
    }



    @After
    public void tearDown() throws Exception {
    }

}