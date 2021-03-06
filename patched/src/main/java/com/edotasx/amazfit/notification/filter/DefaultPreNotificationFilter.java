package com.edotasx.amazfit.notification.filter;

import android.content.Context;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.edotasx.amazfit.Constants;
import com.huami.watch.notification.data.StatusBarNotificationData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edoardotassinari on 21/02/18.
 */

public class DefaultPreNotificationFilter implements PreNotificationFilter {

    private Map<String, Long> notificationsLetGo;
    private Context context;

    public DefaultPreNotificationFilter(Context context) {
        notificationsLetGo = new HashMap<>();
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public boolean filter(StatusBarNotification statusBarNotification) {
        String completeKey = StatusBarNotificationData.getUniqueKey(statusBarNotification);

        if (notificationsLetGo.containsKey(completeKey)) {
            Log.d(Constants.TAG_NOTIFICATION, "rejected by pre-filter: " + statusBarNotification.getPackageName() + " - key: " + completeKey);
            return true;
        } else {
            Log.d(Constants.TAG_NOTIFICATION, "accepted by pre-filter: " + statusBarNotification.getPackageName());

            notificationsLetGo.put(completeKey, System.currentTimeMillis());
            return false;
        }
    }
}
