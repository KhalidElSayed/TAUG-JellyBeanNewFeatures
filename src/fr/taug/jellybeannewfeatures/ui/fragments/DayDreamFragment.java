package fr.taug.jellybeannewfeatures.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import fr.taug.jellybeannewfeatures.R;

public class DayDreamFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ScrollView view = (ScrollView) inflater.inflate(R.layout.daydream_fragment, null);
		Button displaySettingsButton = (Button) view.findViewById(R.id.display_settings);
		displaySettingsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().startActivityForResult(new Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS), 0);

			}
		});
		return view;
	}
}
