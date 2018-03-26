package com.duncan.read.domain.presentation.logger;

import timber.log.Timber;

/**
 * Timber Logger implementation
 * <p/>
 * Created by Duncan on 21/3/2018.
 */
public class TimberLogger implements Logger {


    public TimberLogger() {
        //empty constructor
    }

    @Override
    public void logD(String tag, String message) {
        Timber.tag(tag);
        Timber.d(message);
    }

    @Override
    public void logE(String tag, String message, Throwable throwable) {

        Timber.tag(tag);
        Timber.e(throwable, message);

    }


}
