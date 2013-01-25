package fr.taug.jellybeannewfeatures.ui.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import fr.taug.jellybeannewfeatures.R;

public class AboutActivity extends Activity {

	private static final String ACTION_LAUNCH = "fr.taug.jellybeannewfeatures.ABOUTACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setTitle(getString(R.string.menu_about));
		actionBar.setDisplayHomeAsUpEnabled(true);
		ImageView taugGPlus = (ImageView) findViewById(R.id.taug_gplus);
		ImageView taugTwitter = (ImageView) findViewById(R.id.taug_twitter);
		ImageView taugFacebook = (ImageView) findViewById(R.id.taug_facebook);
		ImageView taugBlog = (ImageView) findViewById(R.id.taug_blog);

		ImageView npGPlus = (ImageView) findViewById(R.id.np_gplus);
		ImageView npTwitter = (ImageView) findViewById(R.id.np_twitter);

		taugGPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("https://plus.google.com/117732835400777918591"));
				startActivity(browserIntent);

			}
		});
		taugTwitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ToulouseAUG"));
				startActivity(browserIntent);

			}
		});
		taugFacebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("https://www.facebook.com/ToulouseAndroidUserGroup"));
				startActivity(browserIntent);

			}
		});
		taugBlog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://toulouseaug.blogspot.fr/"));
				startActivity(browserIntent);

			}
		});
		npGPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("https://plus.google.com/113671876130843889747"));
				startActivity(browserIntent);

			}
		});
		npTwitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/pomepuyn"));
				startActivity(browserIntent);

			}
		});

	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	public static void launchActivity(Context context) {
		Intent intent = new Intent(ACTION_LAUNCH);
		intent.setPackage(context.getPackageName());
		context.startActivity(intent);
	}
}
