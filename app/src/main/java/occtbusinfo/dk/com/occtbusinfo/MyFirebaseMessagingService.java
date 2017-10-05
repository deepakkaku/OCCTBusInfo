package occtbusinfo.dk.com.occtbusinfo; /**
 * Created by Deepak Kaku on 02-09-2016.
 */
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    // [START receive_message]
       @Override
       public void onMessageReceived(RemoteMessage remoteMessage) {
           Log.d(TAG, "From: " + remoteMessage.getFrom());
           if (remoteMessage.getData().size() > 0) {
               Log.d(TAG, "Message data payload: " + remoteMessage.getData());
           }

           if (remoteMessage.getNotification() != null) {
               Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
           }
       }
    }
