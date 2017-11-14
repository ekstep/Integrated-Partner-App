package org.ekstep.genie.hooks;

import android.app.Activity;
import android.content.Intent;

/**
 * @author vinayagasundar
 */

public interface IStartUp {

    void init(Activity activity);

    void handleNotification(Intent intent);

    void handleLocalDeepLink(Intent intent);

    void handleServerDeepLink(Intent intent);

    String[] requiredPermissions();

    void nextScreen(Activity activity);
}
