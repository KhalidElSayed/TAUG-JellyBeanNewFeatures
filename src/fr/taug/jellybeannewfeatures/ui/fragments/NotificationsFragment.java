package fr.taug.jellybeannewfeatures.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import fr.taug.jellybeannewfeatures.R;
import fr.taug.jellybeannewfeatures.ui.notifications.Notifications;

public class NotificationsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ScrollView view = (ScrollView) inflater.inflate(R.layout.notifications_fragment, null);

		// Mapping the events

		Button simpleNotificationButton = (Button) view.findViewById(R.id.simple_notification);
		simpleNotificationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Notifications.generateSimpleNotification(getActivity(), false);

			}
		});
		Button secondStackNotificationButton = (Button) view.findViewById(R.id.simple_notification_second_stack);
		secondStackNotificationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Notifications.generateSimpleNotification(getActivity(), true);

			}
		});
		Button inboxNotificationButton = (Button) view.findViewById(R.id.inbox_notification);
		inboxNotificationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Notifications.generateInboxNotification(getActivity());

			}
		});
		Button bigPictureNotificationButton = (Button) view.findViewById(R.id.big_picture_notification);
		bigPictureNotificationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Notifications.generateBigPictureNotification(getActivity());

			}
		});
		Button bigTextNotificationButton = (Button) view.findViewById(R.id.big_text_notification);
		bigTextNotificationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Notifications.generateBigTextNotification(getActivity());

			}
		});
		Button customNotificationButton = (Button) view.findViewById(R.id.custom_notification);
		customNotificationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Notifications.generateCustomNotification(getActivity());

			}
		});

		Button clearAllNotificationButton = (Button) view.findViewById(R.id.clear_all);
		clearAllNotificationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Notifications.clearAllNotifications(getActivity());

			}
		});
		Button clearFirstStackNotificationButton = (Button) view.findViewById(R.id.clear_first);
		clearFirstStackNotificationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Notifications.clearFirstStackNotifications(getActivity());

			}
		});
		Button clearSecondStackNotificationButton = (Button) view.findViewById(R.id.clear_second);
		clearSecondStackNotificationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Notifications.clearSecondStackNotifications(getActivity());

			}
		});

		return view;
	}
}
