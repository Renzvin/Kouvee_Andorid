package com.app.p3l.Endpoints;

import android.app.Notification;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);
            String url = "http://renzvin.com/kouvee/api/FcmNotification/sendnotification";
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Notification notification = new NotificationCompat.Builder(getApplicationContext())
                                    .setContentTitle(remoteMessage.getData().get("title"))
                                    .setContentText(remoteMessage.getData().get("body"))
                                    .setSmallIcon(R.drawable.paw)
                                    .build();
                            NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                            manager.notify(123, notification);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            queue.add(getRequest);
    }
}