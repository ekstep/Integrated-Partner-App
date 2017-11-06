package org.ekstep.genie.base;

import android.support.v4.util.SimpleArrayMap;

public class PresenterManager {

    private static PresenterManager instance;

    private SimpleArrayMap<String, BasePresenter> presenterCache;

    private PresenterManager() {
        presenterCache = new SimpleArrayMap<>();
    }

    public static PresenterManager getInstance() {
        if (instance == null) {
            instance = new PresenterManager();
        }
        return instance;
    }

    public BasePresenter getPresenter(String tag, IPresenterFactory presenterFactory) {
        BasePresenter basePresenter = presenterCache.get(tag);
        if (basePresenter == null && presenterFactory != null) {
            basePresenter = presenterFactory.create();
            presenterCache.put(tag, basePresenter);
        }
        return basePresenter;
    }

    public void removePresenter(String tag) {
        presenterCache.remove(tag);
    }


}