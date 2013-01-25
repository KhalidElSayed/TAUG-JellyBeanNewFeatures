package fr.taug.jellybeannewfeatures.ui.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import fr.taug.jellybeannewfeatures.R;
import fr.taug.jellybeannewfeatures.ui.activities.MainActivity;

public class Notifications {

	private static final int NOTIFICATION_FIRST_STACK_ID = 1337;
	private static final int NOTIFICATION_SECOND_STACK_ID = 0;
	public static final String TAB = "TabToGo";

	public static void generateSimpleNotification(Context context, boolean secondStack) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.gdg)
				.setContentTitle("My notification").setContentText("Hello World!")
				.setContentIntent(getPendingIntent(context));

		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		if (!secondStack) {
			mNotificationManager.notify(NOTIFICATION_FIRST_STACK_ID, mBuilder.build());
		} else {
			mNotificationManager.notify(NOTIFICATION_SECOND_STACK_ID, mBuilder.build());

		}
	}

	private static PendingIntent getPendingIntent(Context context) {
		Intent i = new Intent(context, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra(TAB, 1);
		return PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	public static void generateInboxNotification(Context context) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.gdg)
				.setContentTitle("Event tracker").setContentText("Events received");
		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
		String[] events = context.getResources().getStringArray(R.array.long_list);
		inboxStyle.setBigContentTitle("Event tracker details:");
		for (int i = 0; i < events.length; i++) {

			inboxStyle.addLine(events[i]);
		}
		mBuilder.setStyle(inboxStyle);
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(NOTIFICATION_FIRST_STACK_ID, mBuilder.build());
	}

	/**
	 * Cancel all the notifications
	 * 
	 * @param context
	 */
	public static void clearAllNotifications(Context context) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(NOTIFICATION_FIRST_STACK_ID);
		notificationManager.cancel(NOTIFICATION_SECOND_STACK_ID);
	}

	/**
	 * Cancel first stack notifications
	 * 
	 * @param context
	 */
	public static void clearFirstStackNotifications(Context context) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(NOTIFICATION_FIRST_STACK_ID);
	}

	/**
	 * Cancel second stack notifications
	 * 
	 * @param context
	 */
	public static void clearSecondStackNotifications(Context context) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(NOTIFICATION_SECOND_STACK_ID);
	}
}
