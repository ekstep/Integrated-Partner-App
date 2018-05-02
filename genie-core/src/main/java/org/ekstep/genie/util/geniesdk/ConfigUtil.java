package org.ekstep.genie.util.geniesdk;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.MasterData;
import org.ekstep.genieservices.commons.bean.MasterDataValues;
import org.ekstep.genieservices.commons.bean.enums.MasterDataType;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swayangjit on 20/2/17.
 */

public class ConfigUtil {

    private static final String TAG = ConfigUtil.class.getSimpleName();

    public static List<String> getMasterDataLabelList(MasterData masterData) {
        List<String> labelList = new ArrayList<>();
        if (masterData != null) {
            for (MasterDataValues masterDataValue : masterData.getValues()) {
                labelList.add(masterDataValue.getLabel());
            }
        }
        return labelList;
    }

    public static String getRelevantMasterData(List<String> labelList, String value) {
        for (String label : labelList) {
            if (label.equalsIgnoreCase(value)) {
                return label;
            }
        }
        return null;
    }


    public static String[] getFilterConfig(String filter) {
        GenieResponse<MasterData> response = CoreApplication.getGenieSdkInstance().getConfigService().getMasterData(MasterDataType.CONFIG);
        if (response != null && response.getStatus()) {
            MasterData masterData = response.getResult();
            if (masterData != null && !CollectionUtil.isNullOrEmpty(masterData.getValues())) {
                for (MasterDataValues masterDataValues : masterData.getValues()) {
                    if (filter.equals(masterDataValues.getLabel())) {
                        String value = masterDataValues.getValue();
                        if (!StringUtil.isNullOrEmpty(value)) {
                            return value.split(",");
                        }
                    }
                }
            }
        }

        return null;
    }

}
