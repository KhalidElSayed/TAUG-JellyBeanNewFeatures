package fr.taug.jellybeannewfeatures.ui.daydream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class TwitterApi {

	private static final String TWITTER_SEARCH_URL = "http://search.twitter.com/search.json?rpp=100&result_type=recent&q=";
	private static final String TAG = "TwitterAPI";

	public static void retrieveTweets(Context context, String hashtag, IOnGetTweetsListener listener) {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(TWITTER_SEARCH_URL + hashtag);
		HttpResponse response;
		try {
			response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String finalResponse = "";
			String line = "";
			while ((line = rd.readLine()) != null) {
				finalResponse += line;
			}

			parseTweetListResult(context, finalResponse, listener);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void parseTweetListResult(Context context, String jsonResult, IOnGetTweetsListener listener) {

		List<Tweet> tweets = new ArrayList<Tweet>();

		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonResult);
			String results = jsonObject.getString("results");

			JSONArray array = new JSONArray(results);
			for (int i = 0; i < array.length(); i++) {
				try {
					JSONObject row = array.getJSONObject(i);
					Tweet aTweet = new Tweet();
					aTweet.setProfileImage(row.getString("profile_image_url"));
					aTweet.setMessage(row.getString("text"));
					aTweet.setUser(row.getString("from_user"));
					aTweet.setUserName(row.getString("from_user_name"));
					aTweet.setIdStr(row.getString("id_str"));

					try {
						aTweet.setDate(getTwitterDate(row.getString("created_at")));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					aTweet.setId(row.getInt("id"));

					tweets.add(aTweet);
				} catch (Exception e) {
					Log.e(TAG, e.getClass().toString() + ": " + e.getMessage(), e);
				}
			}

			listener.onResult(tweets);

		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			listener.onError("JSON error");
		}

	}

	public static Date getTwitterDate(String date) throws ParseException {

		final String TWITTER = "EEE, dd MMM yyy HH:mm:ss ZZZZZ";
		SimpleDateFormat sf = new SimpleDateFormat(TWITTER, Locale.getDefault());
		// Thu Dec 3 18:26:07 +0000 2010
		// Thu, 06 Oct 2011 19:36:17 +0000
		sf.setLenient(true);
		return sf.parse(date);
	}

	public interface IOnGetTweetsListener {
		public abstract void onResult(List<Tweet> list);

		public abstract void onError(String reason);

	}
}
