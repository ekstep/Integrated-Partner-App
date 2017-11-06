package org.ekstep.genie.telemetry;

import android.text.TextUtils;

import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.CorrelationData;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 1/3/17.
 *
 * @author swayangjit
 */
public class TelemetryUtil {

    public static List<CorrelationData> computeCData(List<HierarchyInfo> contentInfoList) {
        List<CorrelationData> cdata = null;

        if (!PreferenceUtil.getCdataStatus()) {
            if (contentInfoList != null && contentInfoList.size() > 0) {
                cdata = new ArrayList<>();

                List<String> idList = new ArrayList<>();

                for (int i = 0; i < contentInfoList.size(); i++) {
                    idList.add(contentInfoList.get(i).getIdentifier());
                }

                cdata.add(new CorrelationData(TextUtils.join("/", idList), contentInfoList.get(0).getContentType()));
            } else {
                cdata = PreferenceUtil.getCoRelationList();
            }
        } else {
            PreferenceUtil.setCdataStatus(false);
        }

        return cdata;
    }

    public static boolean isFromCollectionOrTextBook(List<HierarchyInfo> contentInfoList) {
        if (contentInfoList != null && contentInfoList.size() > 0) {
            HierarchyInfo contentInfo = contentInfoList.get(0);

            return (contentInfo.getContentType().toLowerCase().equalsIgnoreCase(ContentType.COLLECTION)
                    || contentInfo.getContentType().toLowerCase().equalsIgnoreCase(ContentType.TEXTBOOK)
                    || contentInfo.getContentType().toLowerCase().equalsIgnoreCase(ContentType.TEXTBOOKUNIT));
        } else {
            return false;
        }
    }



}
