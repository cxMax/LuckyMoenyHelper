package com.cxmax.luckymoenyhelper;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import com.cxmax.luckymoenyhelper.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author CaiXi on  2017/1/10 01:14.
 * @Github: https://github.com/cxMax
 * @Description 通过监听notification来判断红包是否来了
 */

public class NotificationService extends NotificationListenerService {
    private static final String TAG = NotificationService.class.getSimpleName();

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        if (notification != null){
            Bundle args = notification.extras;
            List<String> result = resolveArgs(args);
            if (Util.isEmpty(result)) {
                throw new NullPointerException("something wrong in this method named resolveArgs()");
            }
            for (String str : result) {
                if (!TextUtils.isEmpty(str) && str.contains("[微信红包]")) {
                    final PendingIntent intent = notification.contentIntent;
                    try {
                        intent.send();
                    } catch (PendingIntent.CanceledException e) {
                        Log.e(TAG, "something wrong in this method named  onNotificationPosted()");
                    }
                    break;
                }
            }
        }
    }

    private List<String> resolveArgs(Bundle args) {
        List<String> result = new ArrayList<String>();
        if (args == null) {
            Log.e(TAG, "You have received null red packet!");
            return result;
        }
        String title = args.getString("android.title");
        if (!TextUtils.isEmpty(title)){
            result.add(title);
        }
        String detailText = args.getString("android.text");
        if (!TextUtils.isEmpty(detailText)){
            result.add(detailText);
        }
        return result;
    }

}
