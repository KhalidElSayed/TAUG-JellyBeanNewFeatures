package fr.taug.jellybeannewfeatures.ui.daydream;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.taug.jellybeannewfeatures.R;
import fr.taug.jellybeannewfeatures.ui.daydream.DownloadImage.IOnDownloadImageListener;

public class TweetAdapter extends ArrayAdapter<Tweet> {

	private Context mContext;
	private IOnTweetAdapterListener mListener;

	public TweetAdapter(Context context, List<Tweet> tweets, IOnTweetAdapterListener listener) {
		super(context, android.R.id.text1, tweets);
		mContext = context;
		mListener = listener;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final LocalHolder holder;
		if (convertView == null) {
			convertView = View.inflate(getContext(), R.layout.tweet, null);
			holder = new LocalHolder();
			holder.userName = (TextView) convertView.findViewById(R.id.user_name);
			holder.user = (TextView) convertView.findViewById(R.id.user);
			holder.message = (TextView) convertView.findViewById(R.id.message);
			holder.dateTweet = (TextView) convertView.findViewById(R.id.date);
			holder.tweetContainer = (LinearLayout) convertView.findViewById(R.id.tweet_container);
			holder.profileImage = (ImageView) convertView.findViewById(R.id.profile_image);
			convertView.setTag(holder);
		} else {
			holder = (LocalHolder) convertView.getTag();
		}

		final Tweet tweet = getItem(position);

		holder.user.setText("@" + tweet.getUser());
		holder.userName.setText(tweet.getUserName());
		holder.message.setText(tweet.getMessage());
		// Linkify.addLinks(holder.message, Linkify.ALL);
		//
		// TransformFilter profileTransformFilter = new TransformFilter() {
		// @Override
		// public String transformUrl(Matcher match, String url) {
		// return url.substring(1); // remove the $ sign
		// }
		// };
		//
		// Pattern patternProfile = Pattern.compile("\\@[a-zA-Z0-9]+");
		// Linkify.addLinks(holder.message, patternProfile,
		// "http://twitter.com/", null, profileTransformFilter);
		//
		// Pattern patternHTag = Pattern.compile("\\#[a-zA-Z0-9]+");
		// Linkify.addLinks(holder.message, patternHTag,
		// "https://twitter.com/search/realtime?q=%23");

		holder.dateTweet.setText(DateFormat.format("dd/MM/yyyy hh:mm:ss", tweet.getDate()));
		if (tweet.getProfileImageB() != null) {
			holder.profileImage.setImageBitmap(tweet.getProfileImageB());
		} else {
			holder.profileImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.gdg));
			tweet.loadImage(new IOnDownloadImageListener() {

				@Override
				public void onOk(Bitmap image) {
					tweet.setProfileImageB(image);
					holder.profileImage.setImageBitmap(image);

				}

				@Override
				public void onError() {

				}
			});
		}

		if (tweet.isNew()) {
			holder.tweetContainer.setBackgroundResource(R.drawable.tweet_new);
		} else {
			holder.tweetContainer.setBackgroundResource(R.drawable.tweet_not_new);
		}
		holder.tweetContainer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweet.getTweetUrl()));
				browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(browserIntent);
				mListener.onOpenLink();
			}
		});
		holder.profileImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweet.getProfileUrl()));
				browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(browserIntent);
				mListener.onOpenLink();
			}
		});

		return convertView;
	}

	public void setNew(int position, boolean isNew) {
		Tweet tweet = getItem(position);
		tweet.setNew(isNew);
	}

	public void setAllTweetsToOld() {
		for (int i = 0; i < getCount(); i++) {
			getItem(i).setNew(false);
		}
		mListener.onMarkAllAsRead();
	}

	static class LocalHolder {
		public TextView message;
		public TextView user;
		public TextView userName;
		public LinearLayout tweetContainer;
		public TextView dateTweet;
		public ImageView profileImage;
	}

	public boolean addTweet(Tweet tweet) {
		if (getPosition(tweet) < 0) {
			insert(tweet, 0);
			if (getCount() > 100) {
				remove(getItem(100));
			}
			return true;
		}
		return false;
	}

	public int getNumberNewTweets() {
		int nb = 0;
		for (int i = 0; i < getCount(); i++) {
			if (getItem(i).isNew())
				nb++;
		}
		return nb;

	}

	public interface IOnTweetAdapterListener {
		public abstract void onOpenLink();

		public abstract void onMarkAllAsRead();

	}
}
