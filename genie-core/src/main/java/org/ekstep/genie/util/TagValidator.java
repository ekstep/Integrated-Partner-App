package org.ekstep.genie.util;


import org.ekstep.genieservices.commons.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created on 3/31/2016.
 *
 * @author swayangjit_gwl
 */
public class TagValidator {

    private static final String TAG = TagValidator.class.getSimpleName();

    public static boolean checkStartEndDate(String sDate, String eDate) {
        if (!StringUtil.isNullOrEmpty(sDate) && !StringUtil.isNullOrEmpty(eDate)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            try {
                Date startDate = sdf.parse(sDate);
                Date endDate = sdf.parse(eDate);
                return !endDate.after(startDate);
            } catch (ParseException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean checkStartEndDateSplash(String sDate, String eDate) {
        if (!StringUtil.isNullOrEmpty(sDate) && !StringUtil.isNullOrEmpty(eDate)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
            try {
                Date startDate = sdf.parse(sDate);
                Date endDate = sdf.parse(eDate);
                return !endDate.after(startDate);
            } catch (ParseException e) {
                return false;
            }

        } else {
            return false;
        }
    }


    public static String changeDateFormat(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
            Date tempDate = simpleDateFormat.parse(date);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            String lDate = outputDateFormat.format(tempDate);

            LogUtil.i(TAG, "Output date is = " + lDate);

            return lDate;
        } catch (ParseException e) {
            // Do nothing.
        }

        return null;
    }

}
