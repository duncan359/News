package com.duncan.read.domain.presentation.logger;

/**
 * Logger wrapper for logs
 * <p/>
 * Created by Duncan on 21/3/2018.
 */
public interface Logger {

    void logD(String tag, String message);

    void logE(String tag, String message, Throwable throwable);
}
