package fr.taug.jellybeannewfeatures.ui.activities;

import java.util.List;

import android.preference.PreferenceActivity;
import fr.taug.jellybeannewfeatures.R;

public class TAUGDayDreamConfigActivity extends PreferenceActivity {

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.daydream_preference_header, target);
	}
}
