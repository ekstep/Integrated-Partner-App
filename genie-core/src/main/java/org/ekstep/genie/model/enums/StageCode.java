package org.ekstep.genie.model.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by swayangjit on 5/7/17.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({StageCode.ADDCHILD, StageCode.SEARCH, StageCode.SYNC})
public @interface StageCode {

    int ADDCHILD = 0;
    int SEARCH = 1;
    int SYNC = 2;

}
