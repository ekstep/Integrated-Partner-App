package org.ekstep.genie.custom.matcher;

import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.ekstep.genie.model.DisplayResolveInfo;
import org.ekstep.genie.ui.share.ShareResolverAdapter;
import org.ekstep.genie.util.Session;
import org.ekstep.genieservices.commons.bean.enums.JWTokenType;
import org.ekstep.genieservices.commons.utils.Base64Util;
import org.ekstep.genieservices.commons.utils.CryptoUtil;

import java.util.List;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertTrue;

/**
 * Created on 21/7/17.
 * shriharsh
 */

public class RecyclerViewItemAssertion implements ViewAssertion {
    String versionHistory;

    public RecyclerViewItemAssertion(String versionHistory) {
        this.versionHistory = versionHistory;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        ShareResolverAdapter shareResolverAdapter = (ShareResolverAdapter) adapter;

        List<DisplayResolveInfo> resolveInfoList = shareResolverAdapter.getResolveInfoList();

        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            Intent intent = resolveInfoList.get(0).getIntent();
            String intentData = intent.getStringExtra(Intent.EXTRA_TEXT);
            assertTrue(intentData.contains(versionHistory));
            assertHmac(intentData);
        }
    }

    private void assertHmac(String intentData) {
        String configStringParts[] = intentData.split(Pattern.quote("||"));
        StringBuilder stringBuilder = new StringBuilder();
        String stringToBeValidated;
        for (int i = 0; i < configStringParts.length - 1; i++) {
            stringBuilder.append(configStringParts[i]);
            stringBuilder.append("||");
        }

        stringToBeValidated = stringBuilder.toString();
        stringToBeValidated = stringToBeValidated.substring(0, stringToBeValidated.length() - 2);

        String hmac = Base64Util.encodeToString(CryptoUtil.generateHMAC(stringToBeValidated.trim(), Session.getInstance().getUniqueDeviceId().getBytes(), JWTokenType.HS256.getAlgorithmName()), 11);
        String hmacParts[] = configStringParts[configStringParts.length-1].split(":");
        assertTrue(hmac.equalsIgnoreCase(hmacParts[1]));
    }

}