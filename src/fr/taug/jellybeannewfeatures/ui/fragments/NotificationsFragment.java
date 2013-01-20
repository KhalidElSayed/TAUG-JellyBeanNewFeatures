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
				Notifications.generateSimpleNotification(getActivity());

			}
		});

		return view;
	}
}
