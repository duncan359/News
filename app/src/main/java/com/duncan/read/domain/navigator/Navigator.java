package com.duncan.read.domain.navigator;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.duncan.read.News.view.CommentsActivity;
import com.duncan.read.News.view.MainActivity;
import com.duncan.read.News.view.ReplyActivity;
import com.duncan.read.domain.data.GetCommentResponse;
import com.duncan.read.domain.data.GetStoryResponse;


/**
 * Navigator contains the methods to start activities
 * Created by Duncan on 23/3/2018.
 */
public class Navigator {


    /**
     * Starts Inbox Message Activity
     *
     * @param context
     */

    /**
     * Starts Terms Activity
     *
     * @param context
     */

    public void startMainPage(@Nullable Context context) {

        if (context != null) {

            Intent intent = MainActivity.getCallingIntent(context);
            context.startActivity(intent);

        }
    }

    public void startComments(@Nullable Context context, GetStoryResponse storyResponse) {

        if (context != null) {

            Intent intent = CommentsActivity.getCallingIntent(context);
            intent.putExtra("Class",storyResponse);
            context.startActivity(intent);

        }
    }

    public void startReply(@Nullable Context context, GetCommentResponse storyResponse) {

        if (context != null) {

            Intent intent = ReplyActivity.getCallingIntent(context);
            intent.putExtra("Class",storyResponse);
            context.startActivity(intent);

        }
    }

}
