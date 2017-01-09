package com.cxmax.library;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

/**
 * @Author CaiXi on  2017/1/10 00:06.
 * @Github: https://github.com/cxMax
 * @Description
 */

public class PermissonUtils {

    private Activity activity;

    private PermissonUtils(Activity activity) {
        this.activity = activity;
    }

    public PermissonUtils with(Activity activity){
        return new PermissonUtils(activity);
    }


    public boolean checkAccessibleEnabled(){
        AccessibilityManager manager = (AccessibilityManager) activity.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> runningServices = manager.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo info : runningServices) {
            if (info.getId().equals(activity.getPackageName() + "/.MonitorService")) {
                return true;
            }
        }
        return false;
    }

    public boolean checkNotificationEnabled(){
        ContentResolver contentResolver = getContentResolver();
        String enabledListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");

        if (!TextUtils.isEmpty(enabledListeners)) {
            return enabledListeners.contains(getPackageName() + "/" + getPackageName() + ".NotificationService");
        } else {
            return false;
        }
    }

}
