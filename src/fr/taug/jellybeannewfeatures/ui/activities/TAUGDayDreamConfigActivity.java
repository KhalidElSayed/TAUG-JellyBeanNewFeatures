package fr.taug.jellybeannewfeatures.ui.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import fr.taug.jellybeannewfeatures.ui.fragments.DayDreamPreferenceFragment;

public class TAUGDayDreamConfigActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new DayDreamPreferenceFragment())
				.commit();
	}
}
