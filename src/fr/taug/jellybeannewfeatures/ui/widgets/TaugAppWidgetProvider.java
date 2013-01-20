package fr.taug.jellybeannewfeatures.ui.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

public abstract class TaugAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int i = 0; i < appWidgetIds.length; i++) {
			RemoteViews views = buildUpdate(context, appWidgetIds[i], true);
			appWidgetManager.updateAppWidget(appWidgetIds[i], views);

		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId,
			Bundle newOptions) {

		// Value 182 has been determined by getting the lockscreen widget height
		// with the following log

		// Log.d("test",
		// "Dimensions: " + newOptions.getInt("appWidgetMinWidth") + " / "
		// + newOptions.getInt("appWidgetMinHeight"));
		boolean useBigLayout = newOptions.getInt("appWidgetMinHeight") > 182;
		RemoteViews views = buildUpdate(context, appWidgetId, useBigLayout);
		appWidgetManager.updateAppWidget(appWidgetId, views);
		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
	}

	/**
	 * Create Widget RemoteViews
	 * 
	 * @param context
	 * @param appWidgetId
	 * @param point2
	 * @return
	 */
	public abstract RemoteViews buildUpdate(Context context, int appWidgetId, boolean useBigLayout);
}
