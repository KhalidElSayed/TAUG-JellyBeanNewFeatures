package fr.taug.jellybeannewfeatures.ui.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import fr.taug.jellybeannewfeatures.R;
import fr.taug.jellybeannewfeatures.ui.activities.MainActivity;

public class Notifications {

	private static final int NOTIFICATION_ID = 1337;
	public static final String TAB = "TabToGo";

	public static void generateSimpleNotification(Context context) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.gdg)
				.setContentTitle("My notification").setContentText("Hello World!")
				.setContentIntent(getPendingIntent(context));

		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

	private static PendingIntent getPendingIntent(Context context) {
		Intent i = new Intent(context, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra(TAB, 1);
		return PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
	}
}
