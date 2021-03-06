package com.medikeen.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.medikeen.patient.HistoryDetailActivity;
import com.medikeen.patient.R;
import com.medikeen.patient.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Varun on 3/3/2016.
 * <p/>
 * This class listens for GCM message and then sends appropriate user notification
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        sendNotification(message);

        //if (from.startsWith("/topics/")) {
        // message received from some topic.
        //} else {
        // normal downstream message.
        //}

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */

        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {

        String number, status, recepientName, orderStatusMessage;
        double cost;

        try {

            JSONObject jsonObject = new JSONObject(message);

            number = jsonObject.getString("orderNumber");
            status = jsonObject.getString("orderStatus");
            recepientName = jsonObject.getString("recepientName");
            orderStatusMessage = jsonObject.getString("orderStatusMessage");
            cost = jsonObject.getDouble("cost");

            Intent intent = new Intent(this, HistoryDetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("ORDER NUMBER", number);
            intent.putExtra("ORDER STATUS", orderStatusMessage);
            intent.putExtra("RECIPIENT NAME", recepientName);
            intent.putExtra("COST", cost);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_menu_gallery)
                    .setContentTitle("MediKeen")
                    .setContentText("Order no. " + number + ": " + orderStatusMessage)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

