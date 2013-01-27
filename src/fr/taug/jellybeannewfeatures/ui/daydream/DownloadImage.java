package fr.taug.jellybeannewfeatures.ui.daydream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadImage extends AsyncTask<String, Integer, Bitmap> {

	private IOnDownloadImageListener mListener;

	public DownloadImage(IOnDownloadImageListener listener) {
		super();
		mListener = listener;
	}

	@Override
	protected Bitmap doInBackground(String... values) {
		// This is done in a background thread
		return downloadImage(values[0]);
	}

	/**
	 * Called after the image has been downloaded -> this calls a function on
	 * the main thread again
	 */
	protected void onPostExecute(Bitmap image) {
		mListener.onOk(image);
	}

	/**
	 * Actually download the Image from the _url
	 * 
	 * @param _url
	 * @return
	 */
	private Bitmap downloadImage(String _url) {
		// Prepare to download image
		URL url;
		BufferedOutputStream out;
		InputStream in;
		BufferedInputStream buf;

		// BufferedInputStream buf;
		try {
			url = new URL(_url);
			in = url.openStream();

			/*
			 * THIS IS NOT NEEDED
			 * 
			 * YOU TRY TO CREATE AN ACTUAL IMAGE HERE, BY WRITING TO A NEW FILE
			 * YOU ONLY NEED TO READ THE INPUTSTREAM AND CONVERT THAT TO A
			 * BITMAP out = new BufferedOutputStream(new
			 * FileOutputStream("testImage.jpg")); int i;
			 * 
			 * while ((i = in.read()) != -1) { out.write(i); } out.close();
			 * in.close();
			 */

			// Read the inputstream
			buf = new BufferedInputStream(in);

			// Convert the BufferedInputStream to a Bitmap
			Bitmap bMap = BitmapFactory.decodeStream(buf);
			if (in != null) {
				in.close();
			}
			if (buf != null) {
				buf.close();
			}

			return bMap;

		} catch (Exception e) {
			Log.e("Error reading file", e.toString());
		}

		return null;
	}

	public interface IOnDownloadImageListener {
		public abstract void onOk(Bitmap image);

		public abstract void onError();

	}

}