package fr.taug.jellybeannewfeatures.ui.widgets;

import android.content.Context;
import android.widget.RemoteViews;
import fr.taug.jellybeannewfeatures.R;

public class TwoLayoutsWidget extends TaugAppWidgetProvider {

	@Override
	public RemoteViews buildUpdate(Context context, int appWidgetId, boolean useBigLayout) {
		RemoteViews rview;

		if (useBigLayout) {
			rview = new RemoteViews(context.getPackageName(), R.layout.second_layout_widget);
		} else {
			rview = new RemoteViews(context.getPackageName(), R.layout.first_layout_widget);
		}

		return rview;
	}

}
