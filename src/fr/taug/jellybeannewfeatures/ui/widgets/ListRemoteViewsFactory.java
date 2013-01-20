package fr.taug.jellybeannewfeatures.ui.widgets;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import fr.taug.jellybeannewfeatures.R;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
	private String[] mWidgetItems;
	private Context mContext;

	public ListRemoteViewsFactory(Context context, Intent intent) {
		mContext = context;
	}

	public void onCreate() {
		mWidgetItems = mContext.getResources().getStringArray(R.array.long_list);
	}

	@Override
	public int getCount() {
		return mWidgetItems.length;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public RemoteViews getViewAt(int position) {
		RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
		rv.setTextViewText(R.id.widget_item, mWidgetItems[position]);

		return rv;
	}

	@Override
	public int getViewTypeCount() {
		return mWidgetItems.length;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public void onDataSetChanged() {

	}

	@Override
	public void onDestroy() {

	}

}