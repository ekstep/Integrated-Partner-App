package org.ekstep.genie.util.geniesdk;

import org.ekstep.genieservices.commons.bean.MasterData;
import org.ekstep.genieservices.commons.bean.MasterDataValues;

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

}
