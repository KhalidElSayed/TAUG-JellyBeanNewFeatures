package fr.taug.jellybeannewfeatures.ui.daydream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.dreams.DreamService;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import fr.taug.jellybeannewfeatures.R;
import fr.taug.jellybeannewfeatures.ui.daydream.TweetAdapter.IOnTweetAdapterListener;
import fr.taug.jellybeannewfeatures.ui.daydream.TwitterApi.IOnGetTweetsListener;

@TargetApi(17)
public class TAUGDayDream extends DreamService {

	private static Handler mHandler = new Handler();
	private static int mNbNewTweets;
	private static TextView newTweetsTV;
	private static ProgressBar progressBar;
	private static int lastTweetId = 0;
	private static final String PATTERN_12_HOURS = DateFormat.HOUR + ":" + DateFormat.MINUTE + DateFormat.MINUTE;
	private static final String PATTERN_24_HOURS = DateFormat.HOUR_OF_DAY + ":" + DateFormat.MINUTE + DateFormat.MINUTE;

	private static TwitterApi.IOnGetTweetsListener tweetsListener = new IOnGetTweetsListener() {

		@Override
		public void onError(String reason) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					progressBar.setVisibility(View.INVISIBLE);
					refreshButton.setVisibility(View.VISIBLE);
				}
			});

		}

		@Override
		public void onResult(final List<Tweet> list) {

			mHandler.post(new Runnable() {

				@Override
				public void run() {
					int idx = 0;
					int pos = 0;
					if (mKeepPosition && !mFirstKeepPosition) {
						idx = listView.getFirstVisiblePosition();
						View vfirst = listView.getChildAt(0);
						pos = 0;
						if (vfirst != null)
							pos = vfirst.getTop();

					}
					int nbNewTweets = 0;
					lastTweetId = list.get(0).getId();
					if (!mKeepUnread)
						tweetAdapter.setAllTweetsToOld();
					for (int i = list.size() - 1; i > -1; i--) {
						if (tweetAdapter.addTweet(list.get(i)))
							nbNewTweets++;
					}
					progressBar.setVisibility(View.INVISIBLE);
					refreshButton.setVisibility(View.VISIBLE);
					mNbNewTweets = tweetAdapter.getNumberNewTweets();
					newTweetsTV.setText(mNbNewTweets + " new tweets");
					newTweetsTV.setVisibility(View.VISIBLE);
					tweetAdapter.notifyDataSetChanged();
					if (mKeepPosition && !mFirstKeepPosition) {
						// Restore the position
						listView.setSelectionFromTop(idx + nbNewTweets, pos);
					}
					mFirstKeepPosition = false;
				}
			});
		}
	};
	private static ListView listView;
	private static TweetAdapter tweetAdapter;
	private static ImageView refreshButton;
	private Handler mRefreshHandler = new Handler();
	private Runnable mRefreshTask = new Runnable() {
		public void run() {
			retrieveTweets();
		}
	};
	private String mTwitterSearch;
	private int mRefreshTime;
	private boolean mNightMode;
	private boolean mShowBattery;
	private TextView battery;
	private BroadcastReceiver batteryReceiver;
	private BroadcastReceiver hourReceiver;
	private boolean mShowHour;
	private TextView hour;
	private static boolean mKeepPosition;
	private static boolean mFirstKeepPosition = true;
	private static boolean mKeepUnread;

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();

		setFullscreen(true);
		setInteractive(true);

		loadPref();

		setScreenBright(!mNightMode);

		setContentView(R.layout.taug_dream);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
		newTweetsTV = (TextView) findViewById(R.id.nb_new_tweets);
		listView = (ListView) findViewById(R.id.tweet_list_view);
		refreshButton = (ImageView) findViewById(R.id.refresh);

		if (mShowBattery) {

			battery = (TextView) findViewById(R.id.battery);

			battery.setVisibility(View.VISIBLE);

			batteryReceiver = new BroadcastReceiver() {

				@Override
				public void onReceive(Context context, Intent intent) {
					int batteryLevel = getBatteryLevel(context);
					battery.setText(batteryLevel + "%");

					if (batteryLevel < 5) {
						battery.setCompoundDrawablesRelativeWithIntrinsicBounds(
								context.getResources().getDrawable(R.drawable.battery_0), null, null, null);
					} else if (batteryLevel < 15) {
						battery.setCompoundDrawablesRelativeWithIntrinsicBounds(
								context.getResources().getDrawable(R.drawable.battery_10), null, null, null);
					} else if (batteryLevel < 25) {
						battery.setCompoundDrawablesRelativeWithIntrinsicBounds(
								context.getResources().getDrawable(R.drawable.battery_20), null, null, null);
					} else if (batteryLevel < 35) {
						battery.setCompoundDrawablesRelativeWithIntrinsicBounds(
								context.getResources().getDrawable(R.drawable.battery_30), null, null, null);
					} else if (batteryLevel < 45) {
						battery.setCompoundDrawablesRelativeWithIntrinsicBounds(
								context.getResources().getDrawable(R.drawable.battery_40), null, null, null);
					} else if (batteryLevel < 55) {
						battery.setCompoundDrawablesRelativeWithIntrinsicBounds(
								context.getResources().getDrawable(R.drawable.battery_50), null, null, null);
					} else if (batteryLevel < 65) {
						battery.setCompoundDrawablesRelativeWithIntrinsicBounds(
								context.getResources().getDrawable(R.drawable.battery_60), null, null, null);
					} else if (batteryLevel < 75) {
						battery.setCompoundDrawablesRelativeWithIntrinsicBounds(
								context.getResources().getDrawable(R.drawable.battery_70), null, null, null);
					} else if (batteryLevel < 85) {
						battery.setCompoundDrawablesRelativeWithIntrinsicBounds(
								context.getResources().getDrawable(R.drawable.battery_80), null, null, null);
					} else if (batteryLevel < 95) {
						battery.setCompoundDrawablesRelativeWithIntrinsicBounds(
								context.getResources().getDrawable(R.drawable.battery_90), null, null, null);
					} else {
						battery.setCompoundDrawablesRelativeWithIntrinsicBounds(
								context.getResources().getDrawable(R.drawable.battery_100), null, null, null);
					}

				}
			};
			registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		}
		if (mShowHour) {

			hour = (TextView) findViewById(R.id.hour);

			hour.setVisibility(View.VISIBLE);

			setHourText(this);

			hourReceiver = new BroadcastReceiver() {

				@Override
				public void onReceive(Context context, Intent intent) {
					setHourText(context);

				}

			};
			registerReceiver(hourReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
		}

		if (mKeepUnread) {
			listView.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					Log.i("Scroll", "State" + scrollState);
					if (OnScrollListener.SCROLL_STATE_IDLE == scrollState) {
						tweetAdapter.setAllTweetsToOld();
						tweetAdapter.notifyDataSetChanged();
					}

				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				}
			});
		}

		refreshButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				retrieveTweets();

			}
		});

		tweetAdapter = new TweetAdapter(this, new ArrayList<Tweet>(), new IOnTweetAdapterListener() {

			@Override
			public void onOpenLink() {
				finish();

			}

			@Override
			public void onMarkAllAsRead() {
				mNbNewTweets = 0;
				newTweetsTV.setText(mNbNewTweets + " new tweets");

			}
		});
		listView.setAdapter(tweetAdapter);

		retrieveTweets();
	}

	private void setHourText(Context context) {
		String patern = PATTERN_12_HOURS;
		if (DateFormat.is24HourFormat(context)) {
			patern = PATTERN_24_HOURS;
		}
		hour.setText(DateFormat.format(patern, Calendar.getInstance(Locale.getDefault())));
	}

	@Override
	public void onDestroy() {
		mRefreshHandler.removeCallbacks(mRefreshTask);
		unregisterReceiver(batteryReceiver);
		unregisterReceiver(hourReceiver);
		super.onDestroy();
	}

	private void retrieveTweets() {
		progressBar.setVisibility(View.VISIBLE);
		refreshButton.setVisibility(View.INVISIBLE);
		new Thread(new Runnable() {
			public void run() {
				String endOfQuery = "";
				if (lastTweetId != 0)
					endOfQuery = "&since_id=" + lastTweetId;
				TwitterApi.retrieveTweets(TAUGDayDream.this, mTwitterSearch + endOfQuery, tweetsListener);
			}
		}).start();
		launchTimer();

	}

	private void launchTimer() {
		mRefreshHandler.removeCallbacks(mRefreshTask);
		mRefreshHandler.postDelayed(mRefreshTask, mRefreshTime * 1000);

	}

	private void loadPref() {
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		mTwitterSearch = mySharedPreferences.getString("daydream_twitter_search", "#taug");

		mRefreshTime = mySharedPreferences.getInt("daydream_refresh_time", 30);
		mKeepUnread = mySharedPreferences.getBoolean("keep_unread", true);
		mKeepPosition = mySharedPreferences.getBoolean("keep_position", true);
		mNightMode = mySharedPreferences.getBoolean("night_mode", true);
		mShowBattery = mySharedPreferences.getBoolean("show_battery", true);
		mShowHour = mySharedPreferences.getBoolean("show_hour", true);

	}

	/**
	 * Give the battery level
	 * 
	 * @param context
	 * @return
	 */
	public int getBatteryLevel(Context context) {

		// get battery level
		Intent batteryIntent;
		try {
			batteryIntent = context.getApplicationContext().registerReceiver(null,
					new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			batteryIntent.setPackage(context.getPackageName());

			int level = -1;
			if (batteryIntent != null) {
				int rawlevel = batteryIntent.getIntExtra("level", -1);
				int scale = batteryIntent.getIntExtra("scale", -1);
				if (rawlevel >= 0 && scale > 0) {
					level = (rawlevel * 100) / scale;

				}
			}
			return level;
		} catch (Exception ex) {
			// can't register service
			return 0;
		}

	}

}
