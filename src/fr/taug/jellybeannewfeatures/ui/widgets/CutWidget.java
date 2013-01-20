package fr.taug.jellybeannewfeatures.ui.widgets;

import android.content.Context;
import android.widget.RemoteViews;
import fr.taug.jellybeannewfeatures.R;

public class CutWidget extends TaugAppWidgetProvider {

	@Override
	public RemoteViews buildUpdate(Context context, int appWidgetId, boolean useBigLayout) {
		RemoteViews rview = new RemoteViews(context.getPackageName(), R.layout.cut_widget);

		return rview;
	}

}
