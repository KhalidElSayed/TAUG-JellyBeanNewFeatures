package fr.taug.jellybeannewfeatures.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import fr.taug.jellybeannewfeatures.R;

public class DayDreamPreferenceFragmentTwitter extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.daydream_preferences_twitter);

	}

}
