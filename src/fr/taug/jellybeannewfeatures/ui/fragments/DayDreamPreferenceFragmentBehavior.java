package fr.taug.jellybeannewfeatures.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import fr.taug.jellybeannewfeatures.R;

public class DayDreamPreferenceFragmentBehavior extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.daydream_preferences_behavior);

	}

}
