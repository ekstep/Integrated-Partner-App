package org.ekstep.genie.custom;

import org.ekstep.genie.BuildConfig;
import org.ekstep.geniecanvas.MainActivity;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by swayangjit on 31/7/17.
 */

public class Assertion {

    public static void assertContentPlayerIntent() {
        intended(hasComponent(MainActivity.class.getName()));
        hasExtras(allOf(hasEntry(equalTo("origin"), equalTo("Genie")),
                hasEntry(equalTo("mode"), equalTo("play")),
                hasEntry(equalTo("appQualifier"), equalTo(BuildConfig.QUALIFIER)),
                hasEntry(equalTo("contentExtras"), notNullValue()),
                hasEntry(equalTo("languageInfo"), notNullValue()),
                hasEntry(equalTo("appQualifier"), notNullValue())
        ));
    }
}
