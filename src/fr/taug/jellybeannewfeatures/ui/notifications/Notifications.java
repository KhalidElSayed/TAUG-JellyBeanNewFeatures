package fr.taug.jellybeannewfeatures.ui.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.RemoteViews;
import fr.taug.jellybeannewfeatures.R;
import fr.taug.jellybeannewfeatures.ui.activities.MainActivity;

public class Notifications {

	private static final int NOTIFICATION_FIRST_STACK_ID = 1337;
	private static final int NOTIFICATION_SECOND_STACK_ID = 0;
	public static final String TAB = "TabToGo";

	/**
	 * Create the simple notification.
	 * 
	 * @param context
	 * @param secondStack
	 *            use the second notification stack ?
	 */
	public static void generateSimpleNotification(Context context, boolean secondStack) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.gdg)
				.setContentTitle(context.getString(R.string.simple_notification))
				.setContentText(context.getString(R.string.simple_notification_description))
				.setTicker(context.getString(R.string.simple_notification)).setContentIntent(getPendingIntent(context));

		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		if (!secondStack) {
			mNotificationManager.notify(NOTIFICATION_FIRST_STACK_ID, mBuilder.build());
		} else {
			mNotificationManager.notify(NOTIFICATION_SECOND_STACK_ID, mBuilder.build());

		}
	}

	/**
	 * Create the inbow style notification
	 * 
	 * @param context
	 */
	public static void generateInboxNotification(Context context) {
		// Get the lines
		String[] events = context.getResources().getStringArray(R.array.long_list);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.gdg))
				.setSmallIcon(R.drawable.gdg).setContentTitle(context.getString(R.string.inbox_notification))
				.setContentText(context.getString(R.string.events_received))
				.setContentTitle(context.getString(R.string.events_received))
				.setTicker(context.getString(R.string.inbox_notification))
				.setContentInfo(String.valueOf(events.length)).setContentIntent(getPendingIntent(context));

		// Notification is now an inbox one
		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
		inboxStyle.setBigContentTitle(context.getString(R.string.event_tracker_details));
		inboxStyle.setSummaryText(context.getResources().getString(R.string.app_name));

		// Add the lines
		int nbMessages = 0;
		ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 255, 255));
		for (int i = 0; i < events.length; i++) {

			// Color the first letter of the lines
			SpannableStringBuilder builder = new SpannableStringBuilder(events[i]);
			builder.setSpan(fcs, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			inboxStyle.addLine(builder);
			nbMessages++;
		}

		mBuilder.setNumber(nbMessages);

		mBuilder.setStyle(inboxStyle);
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(NOTIFICATION_FIRST_STACK_ID, mBuilder.build());
	}

	/**
	 * Create the big picture notification
	 * 
	 * @param context
	 */
	public static void generateBigPictureNotification(Context context) {
		Bitmap iconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.user);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
				.setLargeIcon(iconBitmap)
				.setSmallIcon(R.drawable.gdg)
				.setContentTitle(context.getString(R.string.big_picture_notification))
				.setContentText(context.getString(R.string.picture_received))
				.setTicker(context.getString(R.string.big_picture_notification))
				.addAction(R.drawable.ic_menu_share_holo_dark, context.getString(R.string.share),
						getSharePendingIntent(context));

		NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
		bigPictureStyle.setBigContentTitle(context.getString(R.string.picture_received));
		bigPictureStyle.bigPicture(iconBitmap);

		mBuilder.setStyle(bigPictureStyle);
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(NOTIFICATION_FIRST_STACK_ID, mBuilder.build());
	}

	/**
	 * Create the notification using remoteviews
	 * 
	 * @param context
	 */
	public static void generateCustomNotification(Context context) {

		// Remote view used for the folded part
		RemoteViews smallRView = getSmallRView(context);
		// Remote view used for the unfolded part
		RemoteViews bigRView = new RemoteViews(context.getPackageName(), R.layout.first_layout_widget);

		Bitmap iconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.user);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
				.setLargeIcon(iconBitmap)
				.setSmallIcon(R.drawable.gdg)
				.setContent(smallRView)
				.setTicker(context.getString(R.string.big_picture_notification))
				.addAction(R.drawable.ic_menu_share_holo_dark, context.getString(R.string.share),
						getSharePendingIntent(context));

		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = mBuilder.build();
		notification.bigContentView = bigRView;

		mNotificationManager.notify(NOTIFICATION_FIRST_STACK_ID, notification);
	}

	/**
	 * Create small RemovteView
	 * 
	 * @param context
	 * @return the remoteview
	 */
	private static RemoteViews getSmallRView(Context context) {
		RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.custom_notification_small);
		rv.setTextViewText(R.id.title, "Custom view title");
		rv.setTextViewText(R.id.text2, "Custom view description");
		rv.setImageViewBitmap(R.id.icon, BitmapFactory.decodeResource(context.getResources(), R.drawable.gdg));
		rv.setImageViewBitmap(R.id.icon2, BitmapFactory.decodeResource(context.getResources(), R.drawable.user));
		return rv;
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

	/**
	 * Generates an itent opening the application in the notification tab
	 * 
	 * @param context
	 * @return
	 */
	private static PendingIntent getPendingIntent(Context context) {
		Intent i = new Intent(context, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra(TAB, MainActivity.NOTIFICATION_TAB);
		return PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	private static PendingIntent getSharePendingIntent(Context context) {
		Intent i = new Intent(android.content.Intent.ACTION_SEND);
		i.setType("image/png");

		i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Look at this image!");
		i.putExtra(Intent.EXTRA_STREAM,
				Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.user));

		return PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
	}
}
