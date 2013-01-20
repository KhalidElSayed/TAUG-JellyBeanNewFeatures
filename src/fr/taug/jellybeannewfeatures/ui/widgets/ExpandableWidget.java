package fr.taug.jellybeannewfeatures.ui.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import fr.taug.jellybeannewfeatures.R;

public class ExpandableWidget extends TaugAppWidgetProvider {

	@Override
	public RemoteViews buildUpdate(Context context, int appWidgetId, boolean useBigLayout) {
		RemoteViews rview = new RemoteViews(context.getPackageName(), R.layout.expandable_widget);

		Intent intent = new Intent(context, ListWidgetService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
		rview.setRemoteAdapter(R.id.list, intent);

		return rview;
	}

}
