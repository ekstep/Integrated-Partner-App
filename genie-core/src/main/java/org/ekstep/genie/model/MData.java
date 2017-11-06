package org.ekstep.genie.model;

import org.ekstep.genie.util.preference.PreferenceUtil;

/**
 * Created on 8/1/2016.
 *
 * @author anil
 */
public class MData {

    private String type;    // "fcm"
    private String id;      // registration ID

    public MData() {
        this.type = "fcm";
        this.id = PreferenceUtil.getFcmToken();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
