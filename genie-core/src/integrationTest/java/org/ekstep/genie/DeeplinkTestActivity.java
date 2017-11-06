package org.ekstep.genie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;


/**
 * Created by swayangjit_gwl on 8/17/2016.
 */
public class DeeplinkTestActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String DEEPLINK_CONTENT_DETAILS = "http://www.ekstep.in/c/org.ekstep.money.worksheet";
    private static final String DEEPLINK_EXPLORE_CONTENT = "http://www.ekstep.in/landing/exploreContent";
    private static final String DEEPLINK_TAG1 = "http://www.ekstep.in/settag?k=&s=20160325&e=20160403&d=Pratham%20Reading%20Week";
    private static final String DEEPLINK_TAG2 = "http://www.ekstep.in/settag?k=PRAT@!!!&s=20160325&e=20160403&d=Pratham%20Reading%20Week";
    private static final String DEEPLINK_TAG3 = "http://www.ekstep.in/settag?k=PRAT879123&s=20160325&e=20160324&d=Pratham%20Reading%20Week";
    private static final String DEEPLINK_TAG4 = "http://www.ekstep.in/settag?k=PRAT879123&s=20160325&e=20160403&d=Pratham%20Reading%20Week";
    private static final String DEEPLINK_DETAILS = "ekstep://c/org.ekstep.money.worksheet";
    private static final String DEEPLINK_WITH_HIERARCHYINFO = "ekstep://c/do_30014516&contentExtras={\"type\":\"collection\",\"id\":\"do_2121925679111454721253/do_30022230\"}";
    private static final String DEEPLINK_FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/GenieTestDump/Multiplication2.ecar";
    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deeplink_test);
        findViewById(R.id.btn_deeplink1).setOnClickListener(this);
        findViewById(R.id.btn_deeplink2).setOnClickListener(this);
        findViewById(R.id.btn_deeplink3).setOnClickListener(this);
        findViewById(R.id.btn_deeplink4).setOnClickListener(this);
        findViewById(R.id.btn_deeplink5).setOnClickListener(this);
        findViewById(R.id.btn_deeplink6).setOnClickListener(this);
        findViewById(R.id.btn_deeplink7).setOnClickListener(this);
        findViewById(R.id.btn_deeplink8).setOnClickListener(this);
        findViewById(R.id.btn_deeplink9).setOnClickListener(this);
        findViewById(R.id.btn_deeplink10).setOnClickListener(this);


    }

    private Intent createDeepLinkIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage(BuildConfig.APPLICATION_ID);
        intent.setData(Uri.parse(url));
        return intent;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_deeplink1) {
            startActivity(createDeepLinkIntent(DEEPLINK_CONTENT_DETAILS));

        } else if (i == R.id.btn_deeplink2) {
        } else if (i == R.id.btn_deeplink3) {
            startActivity(createDeepLinkIntent(DEEPLINK_EXPLORE_CONTENT));

        } else if (i == R.id.btn_deeplink4) {
            startActivity(createDeepLinkIntent(DEEPLINK_TAG1));

        } else if (i == R.id.btn_deeplink5) {
            startActivity(createDeepLinkIntent(DEEPLINK_TAG2));

        } else if (i == R.id.btn_deeplink6) {
            startActivity(createDeepLinkIntent(DEEPLINK_TAG3));

        } else if (i == R.id.btn_deeplink7) {
            startActivity(createDeepLinkIntent(DEEPLINK_TAG4));

        } else if (i == R.id.btn_deeplink8) {
            startActivity(createDeepLinkIntent(DEEPLINK_WITH_HIERARCHYINFO));

        } else if (i == R.id.btn_deeplink9) {
            startActivity(createDeepLinkIntent(DEEPLINK_DETAILS));

        } else if (i == R.id.btn_deeplink10) {
            File file = new File(DEEPLINK_FILE_PATH);
            Intent intent = new Intent();
            intent.setPackage(BuildConfig.APPLICATION_ID);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.fromFile(file));
            startActivity(intent);

        }
    }
}
