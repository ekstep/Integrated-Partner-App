package org.ekstep.ipa;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.util.RawUtil;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.ipa.db.PartnerDB;
import org.ekstep.ipa.model.PartnerConfig;

/**
 * @author vinayagasundar
 */

public class PartnerApp extends CoreApplication {

    private static PartnerApp mPartnerApp;

    private PartnerConfig mPartnerConfig;


    public static PartnerApp getPartnerApp() {
        return mPartnerApp;
    }


    @Override
    protected void appInit() {
        super.appInit();

        mPartnerApp = this;
        initPartner();
    }


    @Override
    protected String getConfigPackageName() {
        return "org.ekstep.ipa";
    }

    /**
     * Get the Partner Config.
     * @return instance of the Partner Config
     */
    public PartnerConfig getPartnerConfig() {
        return mPartnerConfig;
    }


    /**
     * Initialize the Partner Apps features in this method
     */
    private void initPartner() {
        PartnerDB.initDataBase(this);


        String partnerConfigFileString = RawUtil.readRawFile(this, R.raw.partner_config);

        if (partnerConfigFileString != null) {
            mPartnerConfig = GsonUtil.fromJson(partnerConfigFileString, PartnerConfig.class);
        }
    }
}
