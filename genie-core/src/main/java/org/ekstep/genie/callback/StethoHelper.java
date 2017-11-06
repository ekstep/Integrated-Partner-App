package org.ekstep.genie.callback;

import android.content.Context;

import okhttp3.OkHttpClient;


/**
 * https://github.com/facebook/stetho/issues/16
 * <p>
 * StethoHelper interface, AKA "NetworkEventReporterImpl"
 * <p>
 * Created on 10/6/2016.
 *
 * @author anil
 */

public interface StethoHelper {
    void init(Context context);

    void configureInterceptor(OkHttpClient httpClient);
}
