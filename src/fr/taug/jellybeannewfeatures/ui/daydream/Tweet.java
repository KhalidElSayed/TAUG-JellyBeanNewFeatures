package fr.taug.jellybeannewfeatures.ui.daydream;

import java.util.Date;

import android.graphics.Bitmap;
import fr.taug.jellybeannewfeatures.ui.daydream.DownloadImage.IOnDownloadImageListener;

public class Tweet {

	private int mId;
	private String mIdStr;
	private String mProfileImage;
	private Bitmap mProfileImageB = null;
	private String mMessage;
	private String mUserName;
	private String mUser;
	private Date mDate;
	private boolean isNew = true;

	public void loadImage(IOnDownloadImageListener listener) {
		new DownloadImage(listener).execute(getProfileImage());

	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getUser() {
		return mUser;
	}

	public void setUser(String mUser) {
		this.mUser = mUser;
	}

	public String getUserName() {
		return mUserName;
	}

	public void setUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String mMessage) {
		this.mMessage = mMessage;
	}

	public String getProfileImage() {
		return mProfileImage;
	}

	public void setProfileImage(String mProfileImage) {
		this.mProfileImage = mProfileImage;
	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date mDate) {
		this.mDate = mDate;
	}

	@Override
	public boolean equals(Object o) {
		return mId == ((Tweet) o).getId();
	}

	public Bitmap getProfileImageB() {
		return mProfileImageB;
	}

	public void setProfileImageB(Bitmap mProfileImageB) {
		this.mProfileImageB = mProfileImageB;
	}

	public String getTweetUrl() {
		return "https://twitter.com/" + getUser() + "/status/" + getIdStr();

	}

	public String getIdStr() {
		return mIdStr;
	}

	public void setIdStr(String mIdStr) {
		this.mIdStr = mIdStr;
	}

	public String getProfileUrl() {
		return "https://twitter.com/" + getUser();
	}

}
