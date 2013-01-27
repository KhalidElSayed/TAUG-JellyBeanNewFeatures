package fr.taug.jellybeannewfeatures.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import fr.taug.jellybeannewfeatures.R;

public class DayDreamPreferenceFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.daydream_preferences);

		new Thread(new Runnable() {
			public void run() {
			}
		}).start();
	}

}
