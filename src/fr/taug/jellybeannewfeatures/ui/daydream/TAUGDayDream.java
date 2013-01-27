package fr.taug.jellybeannewfeatures.ui.daydream;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.dreams.DreamService;
import android.view.View;
import android.view.View.OnClickListener;
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
	private static int nbNewTweets;
	private static TextView newTweetsTV;
	private static ProgressBar progressBar;
	private static int lastTweetId = 0;

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
					nbNewTweets = 0;
					lastTweetId = list.get(0).getId();
					tweetAdapter.setAllTweetsToOld();
					for (int i = list.size() - 1; i > -1; i--) {
						if (tweetAdapter.addTweet(list.get(i)))
							nbNewTweets++;
					}
					progressBar.setVisibility(View.INVISIBLE);
					refreshButton.setVisibility(View.VISIBLE);
					newTweetsTV.setText(nbNewTweets + " new tweets");
					newTweetsTV.setVisibility(View.VISIBLE);
					tweetAdapter.notifyDataSetChanged();
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

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();

		setFullscreen(true);
		setInteractive(true);

		loadPref();

		setContentView(R.layout.taug_dream);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
		newTweetsTV = (TextView) findViewById(R.id.nb_new_tweets);
		listView = (ListView) findViewById(R.id.tweet_list_view);
		refreshButton = (ImageView) findViewById(R.id.refresh);

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
		});
		listView.setAdapter(tweetAdapter);

		retrieveTweets();
	}

	@Override
	public void onDestroy() {
		mRefreshHandler.removeCallbacks(mRefreshTask);
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
		mRefreshHandler.postDelayed(mRefreshTask, 30000);

	}

	private void loadPref() {
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		mTwitterSearch = mySharedPreferences.getString("daydream_twitter_search", "#taug");

		String my_edittext_preference = mySharedPreferences.getString("edittext_preference", "");

	}

}
