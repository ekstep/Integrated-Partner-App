package org.ekstep.genie.base;

import android.content.Context;

public interface BasePresenter {

    void bindView(BaseView view, Context context);

    void unbindView();

}
