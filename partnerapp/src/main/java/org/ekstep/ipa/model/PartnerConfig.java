
package org.ekstep.ipa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartnerConfig {

    @SerializedName("partner")
    @Expose
    private Partner partner;

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

}
