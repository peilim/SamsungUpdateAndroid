package com.memecabin.db;

import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.memecabin.model.DashText;
import com.memecabin.model.ImageInfo;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "memecabindb";

	// Contacts table name
	private static final String TABLE_DASHBOARD = "dashboardtable";
	private static final String TABLE_MEMEITEMS = "memeitemtable";

	// Contacts Table Columns names
	private static final String _ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_DASHTEXT = "dashtext";
	private static final String KEY_IMAGEID = "dashimageid";
	private static final String KEY_STATUS = "status";

	private static final String M_ID = "id";
	private static final String M_IMAGEID = "imageid";

	private static final String M_IMAGECATEGORY = "imageCategory";
	private static final String M_IMAGEURL = "imageUrl";
	private static final String M_IMAGEUPLOADBY = "imageUploadBy";
	public static final String M_IMAGEUPLOADDATE = "imageUploadDate";
	public static final String M_IMAGELIKE = "imageLike";
	public static final String M_IMAGEVIEW = "imageView";
	public static final String M_ISLIKE = "isLiked";
	public static final String M_ISFAVOURITE = "isFavourite";
	public static final String M_likeCount = "likecount";
	public static final String M_delete = "m_delete";
	public static final String M_ISREAD = "m_isread";
	public static final String M_ISONLINE = "m_isonline";

	final static String CREATE_MEMEITEM_TABLE = "CREATE TABLE "
			+ DatabaseHandler.TABLE_MEMEITEMS + "(" + DatabaseHandler.M_ID
			+ " INTEGER PRIMARY KEY, " + DatabaseHandler.M_IMAGEID + " TEXT, "
			+ DatabaseHandler.M_IMAGECATEGORY + " TEXT, "
			+ DatabaseHandler.M_IMAGEURL + " TEXT, "
			+ DatabaseHandler.M_IMAGEUPLOADBY + " TEXT, "
			+ DatabaseHandler.M_IMAGEUPLOADDATE + " TEXT, "
			+ DatabaseHandler.M_IMAGELIKE + " TEXT, "
			+ DatabaseHandler.M_IMAGEVIEW + " TEXT, "
			+ DatabaseHandler.M_ISLIKE + " TEXT, "
			+ DatabaseHandler.M_ISFAVOURITE + " TEXT, "
			+ DatabaseHandler.M_likeCount + " TEXT, "
			+ DatabaseHandler.M_delete + " TEXT, " + DatabaseHandler.M_ISREAD
			+ " TEXT, " + DatabaseHandler.M_ISONLINE + " TEXT "

			+ " )";

	final static String CREATE_DASHBOARD_TABLE = "CREATE TABLE "
			+ DatabaseHandler.TABLE_DASHBOARD + "(" + DatabaseHandler._ID
			+ " INTEGER PRIMARY KEY," + DatabaseHandler.KEY_NAME + " TEXT,"
			+ DatabaseHandler.KEY_DASHTEXT + " TEXT,"
			+ DatabaseHandler.KEY_IMAGEID + " INTEGER,"
			+ DatabaseHandler.KEY_STATUS + " TEXT "

			+ " )";

	private final Context con;

	public DatabaseHandler(Context context) {
		super(context, DatabaseHandler.DATABASE_NAME, null,
				DatabaseHandler.DATABASE_VERSION);
		con = context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_DASHBOARD_TABLE);
		db.execSQL(CREATE_MEMEITEM_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS "
				+ DatabaseHandler.CREATE_DASHBOARD_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DatabaseHandler.CREATE_MEMEITEM_TABLE);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public void addOneEntry(final String name, final String dashtext,
			final String imageid, final String status) {
		final SQLiteDatabase db = getWritableDatabase();

		final ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_NAME, name);
		values.put(DatabaseHandler.KEY_DASHTEXT, dashtext);
		values.put(DatabaseHandler.KEY_IMAGEID, imageid);
		values.put(DatabaseHandler.KEY_STATUS, status);

		// Inserting Row
		db.insert(DatabaseHandler.TABLE_DASHBOARD, null, values);
		db.close(); // Closing database connection
	}

	public int getFavoriteSize() {

		int sizeFav = 0;
		final SQLiteDatabase db = getWritableDatabase();

		final String selectQuery = "SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where  "
				+ DatabaseHandler.M_ISFAVOURITE + "='1'";

		final Cursor cursor = db.rawQuery(selectQuery, null);

		sizeFav = cursor.getCount();

		db.close(); // Closing database connection

		return sizeFav;
	}

	public void removeFavorite(String imgid, String cattype) {

		final SQLiteDatabase db = getWritableDatabase();

		final String selectQuery1 = " Update "
				+ DatabaseHandler.TABLE_MEMEITEMS + " set "
				+ DatabaseHandler.M_ISFAVOURITE + "='0'  where "
				+ DatabaseHandler.M_IMAGEID + "='" + imgid + "' AND "
				+ DatabaseHandler.M_IMAGECATEGORY + "='" + cattype + "'";

		Log.e("update ", ">>" + selectQuery1);

		// db.rawQuery(selectQuery1, null);
		db.execSQL(selectQuery1);

		db.close(); // Closing database connection
	}

	public void setAllON(String status) {

		final SQLiteDatabase db = getWritableDatabase();

		final String selectQuery1 = "Update " + DatabaseHandler.TABLE_MEMEITEMS
				+ " set " + DatabaseHandler.M_delete + "='" + status + "'";

		// Log.e("update ", ">>" + selectQuery1);

		// db.rawQuery(selectQuery1, null);
		db.execSQL(selectQuery1);

		db.close(); // Closing database connection
	}

	public void addFavoritesImg(ImageInfo img) {

		final SQLiteDatabase db = getWritableDatabase();

		final String selectQuery = "SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where "
				+ DatabaseHandler.M_IMAGEID + "='" + img.getImageID()
				+ "' AND " + DatabaseHandler.M_IMAGECATEGORY + "='"
				+ img.getImageCategory() + "'";

		final Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.getCount() > 0) {

			final String selectQuery1 = " Update "
					+ DatabaseHandler.TABLE_MEMEITEMS + " set "
					+ DatabaseHandler.M_ISFAVOURITE + "='1' , "
					+ DatabaseHandler.M_likeCount + "='" + img.getLikeCount()
					+ "'   where " + DatabaseHandler.M_IMAGEID + "='"
					+ img.getImageID() + "' AND "
					+ DatabaseHandler.M_IMAGECATEGORY + "='"
					+ img.getImageCategory() + "'";

			Log.e("update ", ">>" + selectQuery1);

			// db.rawQuery(selectQuery1, null);
			db.execSQL(selectQuery1);

		} else {
			final ContentValues values = new ContentValues();
			values.put(DatabaseHandler.M_IMAGEID, img.getImageID());
			values.put(DatabaseHandler.M_IMAGECATEGORY, img.getImageCategory());
			values.put(DatabaseHandler.M_IMAGEURL, img.getImageUrl());
			values.put(DatabaseHandler.M_ISFAVOURITE, "1");
			values.put(DatabaseHandler.M_likeCount, img.getLikeCount());

			// Inserting Row
			db.insert(DatabaseHandler.TABLE_MEMEITEMS, null, values);
		}

		db.close(); // Closing database connection
	}

	public void removeFavoritesImg(ImageInfo img) {

		final SQLiteDatabase db = getWritableDatabase();

		final String selectQuery = "SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where  "
				+ DatabaseHandler.M_IMAGEID + "='" + img.getImageID()
				+ "' AND " + DatabaseHandler.M_IMAGECATEGORY + "='"
				+ img.getImageCategory() + "'";

		final Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.getCount() > 0) {

			final String selectQuery1 = " Update "
					+ DatabaseHandler.TABLE_MEMEITEMS + " set   "
					+ DatabaseHandler.M_ISFAVOURITE + "='0'  where "
					+ DatabaseHandler.M_IMAGEID + "='" + img.getImageID()
					+ "' AND " + DatabaseHandler.M_IMAGECATEGORY + "='"
					+ img.getImageCategory() + "'";

			Log.e("update ", ">>" + selectQuery1);

			// db.rawQuery(selectQuery1, null);
			db.execSQL(selectQuery1);

		} else {
			final ContentValues values = new ContentValues();
			values.put(DatabaseHandler.M_IMAGEID, img.getImageID());
			values.put(DatabaseHandler.M_IMAGECATEGORY, img.getImageCategory());
			values.put(DatabaseHandler.M_IMAGEURL, img.getImageUrl());
			values.put(DatabaseHandler.M_ISFAVOURITE, "0");
			// values.put(DatabaseHandler.M_likeCount, img.getLikeCount());

			// Inserting Row
			db.insert(DatabaseHandler.TABLE_MEMEITEMS, null, values);
		}

		db.close(); // Closing database connection
	}

	public Vector<ImageInfo> getOfflineFavoriteImages() {
		final Vector<ImageInfo> offLineFavList = new Vector<ImageInfo>();

		final String selectQuery = "SELECT    " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where "
				+ DatabaseHandler.M_ISFAVOURITE + "='" + "1" + "'" + " AND "
				+ DatabaseHandler.M_ISONLINE + "=" + "'0'" + " OR "
				+ DatabaseHandler.M_ISONLINE + " IS NULL " + "OR "
				+ DatabaseHandler.M_ISONLINE + "=" + "''";

		final SQLiteDatabase db = getWritableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				final ImageInfo image = new ImageInfo();

				image.setImageID(cursor.getString(1));
				image.setImageCategory(cursor.getString(2));
				image.setImageUrl(cursor.getString(3));
				image.setLikeCount(cursor.getString(10));
				image.setFavorite(cursor.getString(9));
				image.setDeleteflag(cursor.getString(11));

				offLineFavList.addElement(image);

			} while (cursor.moveToNext());
		}

		db.close(); // Closing database connection

		return offLineFavList;
	}

	public Vector<ImageInfo> getOfflineUnFavoriteImages() {
		final Vector<ImageInfo> offLineUnfavList = new Vector<ImageInfo>();

		final String selectQuery = "SELECT    " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where "
				+ DatabaseHandler.M_ISFAVOURITE + "='" + "0" + "'" + " AND "
				+ DatabaseHandler.M_ISONLINE + "=" + "'0'" + " OR "
				+ DatabaseHandler.M_ISONLINE + " IS NULL " + " OR "
				+ DatabaseHandler.M_ISONLINE + "=" + "''";

		final SQLiteDatabase db = getWritableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				final ImageInfo image = new ImageInfo();

				image.setImageID(cursor.getString(1));
				image.setImageCategory(cursor.getString(2));
				image.setImageUrl(cursor.getString(3));
				image.setLikeCount(cursor.getString(10));
				image.setFavorite(cursor.getString(9));
				image.setDeleteflag(cursor.getString(11));

				offLineUnfavList.addElement(image);

			} while (cursor.moveToNext());
		}

		db.close(); // Closing database connection

		return offLineUnfavList;
	}

	// Need to finish
	public void setOnlineFav(ImageInfo imageInfo) {
		final SQLiteDatabase db = getWritableDatabase();

		final String selectQuery = "SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where  "
				+ DatabaseHandler.M_IMAGEID + "='" + imageInfo.getImageID()
				+ "' AND " + DatabaseHandler.M_IMAGECATEGORY + "='"
				+ imageInfo.getImageCategory() + "'";

		final Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.getCount() > 0) {

			final String selectQuery1 = " Update "
					+ DatabaseHandler.TABLE_MEMEITEMS + " set   "
					+ DatabaseHandler.M_ISFAVOURITE + "='1' " + "AND "
					+ DatabaseHandler.M_ISONLINE + "='1'" + "  where "
					+ DatabaseHandler.M_IMAGEID + "='" + imageInfo.getImageID()
					+ "' AND " + DatabaseHandler.M_IMAGECATEGORY + "='"
					+ imageInfo.getImageCategory() + "'";

			Log.e("update ", ">>" + selectQuery1);

			// db.rawQuery(selectQuery1, null);
			db.execSQL(selectQuery1);

		} else {
			final ContentValues values = new ContentValues();
			values.put(DatabaseHandler.M_IMAGEID, imageInfo.getImageID());
			values.put(DatabaseHandler.M_IMAGECATEGORY,
					imageInfo.getImageCategory());
			values.put(DatabaseHandler.M_IMAGEURL, imageInfo.getImageUrl());
			values.put(DatabaseHandler.M_ISFAVOURITE, "1");
			values.put(DatabaseHandler.M_ISONLINE, "1");
			// values.put(DatabaseHandler.M_likeCount, img.getLikeCount());

			// Inserting Row
			db.insert(DatabaseHandler.TABLE_MEMEITEMS, null, values);
		}

		db.close(); // Closing database connection
	}

	// Need to finish
	public void setOnlineUnFav(ImageInfo imageInfo) {
		final SQLiteDatabase db = getWritableDatabase();

		final String selectQuery = "SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where  "
				+ DatabaseHandler.M_IMAGEID + "='" + imageInfo.getImageID()
				+ "' AND " + DatabaseHandler.M_IMAGECATEGORY + "='"
				+ imageInfo.getImageCategory() + "'";

		final Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.getCount() > 0) {

			final String selectQuery1 = " Update "
					+ DatabaseHandler.TABLE_MEMEITEMS + " set   "
					+ DatabaseHandler.M_ISFAVOURITE + "='0' " + " AND "
					+ DatabaseHandler.M_ISONLINE + "='1'" + " where "
					+ DatabaseHandler.M_IMAGEID + "='" + imageInfo.getImageID()
					+ "' AND " + DatabaseHandler.M_IMAGECATEGORY + "='"
					+ imageInfo.getImageCategory() + "'";

			Log.e("update ", ">>" + selectQuery1);

			// db.rawQuery(selectQuery1, null);
			db.execSQL(selectQuery1);

		} else {
			final ContentValues values = new ContentValues();
			values.put(DatabaseHandler.M_IMAGEID, imageInfo.getImageID());
			values.put(DatabaseHandler.M_IMAGECATEGORY,
					imageInfo.getImageCategory());
			values.put(DatabaseHandler.M_IMAGEURL, imageInfo.getImageUrl());
			values.put(DatabaseHandler.M_ISFAVOURITE, "0");
			values.put(DatabaseHandler.M_ISONLINE, "1");
			// values.put(DatabaseHandler.M_likeCount, img.getLikeCount());

			// Inserting Row
			db.insert(DatabaseHandler.TABLE_MEMEITEMS, null, values);
		}

		db.close(); // Closing database connection
	}

	public Vector<ImageInfo> getAllFavouriteImage() {

		final Vector<ImageInfo> favList = new Vector<ImageInfo>();
		favList.removeAllElements();

		// Select All Query
		final String selectQuery = "SELECT    " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where "
				+ DatabaseHandler.M_ISFAVOURITE + "='" + "1" + "'";

		final SQLiteDatabase db = getWritableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				final ImageInfo image = new ImageInfo();

				image.setImageID(cursor.getString(1));
				image.setImageCategory(cursor.getString(2));
				image.setImageUrl(cursor.getString(3));
				image.setLikeCount(cursor.getString(10));
				image.setFavorite(cursor.getString(9));
				image.setDeleteflag(cursor.getString(11));

				favList.addElement(image);

			} while (cursor.moveToNext());
		}

		db.close(); // Closing database connection
		return favList;
	}

	public Vector<ImageInfo> getAllReadImage(final String catType) {

		final Vector<ImageInfo> favList = new Vector<ImageInfo>();
		favList.removeAllElements();

		// Select All Query
		final String selectQuery = " SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where "
				+ DatabaseHandler.M_ISREAD + "='" + "1" + "' AND "
				+ DatabaseHandler.M_IMAGECATEGORY + "='" + catType + "'";

		final SQLiteDatabase db = getWritableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				final ImageInfo image = new ImageInfo();

				image.setImageID(cursor.getString(1));
				image.setImageCategory(cursor.getString(2));
				image.setImageUrl(cursor.getString(3));
				image.setLikeCount(cursor.getString(10));
				image.setFavorite(cursor.getString(9));
				image.setDeleteflag(cursor.getString(11));

				favList.addElement(image);

			} while (cursor.moveToNext());
		}

		db.close(); // Closing database connection
		return favList;
	}

	public Vector<DashText> getAllDashData() {

		final Vector<DashText> myliblist = new Vector<DashText>();
		myliblist.removeAllElements();

		// Select All Query
		final String selectQuery = " SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_DASHBOARD + " where "
				+ DatabaseHandler.KEY_STATUS + "='" + "on" + "'";

		final SQLiteDatabase db = getWritableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				// Log.d("1 : 2 : 3 :",""+cursor.getString(0)+":"+cursor.getString(1)+":"+cursor.getString(2));

				final DashText mybook = new DashText();

				mybook.setId(cursor.getString(0));
				mybook.setName(cursor.getString(1));
				mybook.setDashtext(cursor.getString(2));
				mybook.setImageid(cursor.getInt(3));
				mybook.setStatus(cursor.getString(4));
				myliblist.addElement(mybook);

			} while (cursor.moveToNext());
		}
		// Log.d("after My list size ", ": " + myliblist.size());
		db.close(); // Closing database connection
		return myliblist;
	}

	public void updateDashStatus(final String name, final String status) {

		final SQLiteDatabase db = getWritableDatabase();
		final String query1 = " UPDATE " + " "
				+ DatabaseHandler.TABLE_DASHBOARD + " set "
				+ DatabaseHandler.KEY_STATUS + "='" + status + "'" + " where "
				+ DatabaseHandler.KEY_NAME + "='" + name + "'";

		db.execSQL(query1);
		// db.delete(DatabaseHandler.TABLE_MEMEITEMS,
		// DatabaseHandler.KEY_ID + " = ?", new String[] { String
		// .valueOf(mylib.getId()) });
		db.close();
	}

	public boolean isFavorite(final String bid, final String catType) {

		final Vector<ImageInfo> favStatuslist = new Vector<ImageInfo>();
		favStatuslist.removeAllElements();
		// Select All Queryb
		final String selectQuery = " SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where  "
				+ DatabaseHandler.M_IMAGEID + "='" + bid + "' AND "
				+ DatabaseHandler.M_ISFAVOURITE + "='" + "1" + "' AND "
				+ DatabaseHandler.M_IMAGECATEGORY + "='" + catType + "'";

		final SQLiteDatabase db = getReadableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);
		boolean fav = false;

		// looping through all rows and adding to list
		if (cursor.getCount() > 0) {
			fav = true;
		} else {
			fav = false;
		}
		db.close();
		return fav;

	}

	public boolean isLikeImage(final String imageID, final String catergory) {

		final Vector<ImageInfo> imageList = new Vector<ImageInfo>();
		imageList.removeAllElements();
		// Select All Queryb
		final String selectQuery = " SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where  "
				+ DatabaseHandler.M_IMAGEID + "='" + imageID + "' AND "
				+ DatabaseHandler.M_ISLIKE + "='" + "1" + "' AND "
				+ DatabaseHandler.M_IMAGECATEGORY + "='" + catergory + "' ";

		final SQLiteDatabase db = getReadableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);
		boolean islike = false;

		// looping through all rows and adding to list
		if (cursor.getCount() > 0) {
			islike = true;
		} else {
			islike = false;
		}
		db.close();
		return islike;

	}

	public void removeLike(ImageInfo img) {
		final SQLiteDatabase db = getWritableDatabase();

		final String selectQuery1 = " Update "
				+ DatabaseHandler.TABLE_MEMEITEMS + " set "
				+ DatabaseHandler.M_ISLIKE + "='0' where "
				+ DatabaseHandler.M_IMAGEID + "='" + img.getImageID()
				+ "' AND " + DatabaseHandler.M_IMAGECATEGORY + "='"
				+ img.getImageCategory() + "'";

		Log.e("update ", ">>" + selectQuery1);

		// db.rawQuery(selectQuery1, null);
		db.execSQL(selectQuery1);

		db.close(); // Closing database connection
	}

	public void addLike(ImageInfo img) {
		final SQLiteDatabase db = getWritableDatabase();

		final String selectQuery = " SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where  "
				+ DatabaseHandler.M_IMAGEID + "='" + img.getImageID()
				+ "' AND " + DatabaseHandler.M_IMAGECATEGORY + "='"
				+ img.getImageCategory() + "'";

		final Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.getCount() > 0) {

			final String selectQuery1 = " Update "
					+ DatabaseHandler.TABLE_MEMEITEMS + " set "
					+ DatabaseHandler.M_ISLIKE + "='1' , "
					+ DatabaseHandler.M_likeCount + "='" + img.getLikeCount()
					+ "' where " + DatabaseHandler.M_IMAGEID + "='"
					+ img.getImageID() + "' AND "
					+ DatabaseHandler.M_IMAGECATEGORY + "='"
					+ img.getImageCategory() + "'";

			Log.e("update ", ">>" + selectQuery1);

			// db.rawQuery(selectQuery1, null);
			db.execSQL(selectQuery1);

		} else {
			final ContentValues values = new ContentValues();
			values.put(DatabaseHandler.M_IMAGEID, img.getImageID());
			values.put(DatabaseHandler.M_IMAGECATEGORY, img.getImageCategory());
			values.put(DatabaseHandler.M_IMAGEURL, img.getImageUrl());
			values.put(DatabaseHandler.M_ISLIKE, "1");
			values.put(DatabaseHandler.M_likeCount, img.getLikeCount());

			// Inserting Row
			db.insert(DatabaseHandler.TABLE_MEMEITEMS, null, values);
		}

		db.close(); // Closing database connection
	}

	public void deleteAllDash() {

		final SQLiteDatabase db = getWritableDatabase();
		final String query1 = "DELETE " + " FROM "
				+ DatabaseHandler.TABLE_DASHBOARD + " where "
				+ DatabaseHandler._ID + ">-1";

		db.execSQL(query1);
		// db.delete(DatabaseHandler.TABLE_MEMEITEMS,
		// DatabaseHandler.KEY_ID + " = ?", new String[] { String
		// .valueOf(mylib.getId()) });
		db.close();
	}

	public void deleteAllMeme() {

		final SQLiteDatabase db = getWritableDatabase();
		final String query1 = "DELETE " + " FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where "
				+ DatabaseHandler.M_ID + ">-1";

		db.execSQL(query1);
		// db.delete(DatabaseHandler.TABLE_MEMEITEMS,
		// DatabaseHandler.KEY_ID + " = ?", new String[] { String
		// .valueOf(mylib.getId()) });
		db.close();
	}

	public void addRead(ImageInfo img) {
		final SQLiteDatabase db = getWritableDatabase();

		final String selectQuery = " SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where  "
				+ DatabaseHandler.M_IMAGEID + "='" + img.getImageID()
				+ "' AND " + DatabaseHandler.M_IMAGECATEGORY + "='"
				+ img.getImageCategory() + "'";

		final Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.getCount() > 0) {

			final String selectQuery1 = " Update "
					+ DatabaseHandler.TABLE_MEMEITEMS + " set "
					+ DatabaseHandler.M_ISREAD + "='1' " + " where "
					+ DatabaseHandler.M_IMAGEID + "='" + img.getImageID()
					+ "' AND " + DatabaseHandler.M_IMAGECATEGORY + "='"
					+ img.getImageCategory() + "'";

			Log.e("update ", ">>" + selectQuery1);

			// db.rawQuery(selectQuery1, null);
			db.execSQL(selectQuery1);

		} else {

			final String selectQuery1 = " INSERT INTO "
					+ DatabaseHandler.TABLE_MEMEITEMS + "  ("
					+ DatabaseHandler.M_IMAGEID + ","
					+ DatabaseHandler.M_IMAGEURL + ","
					+ DatabaseHandler.M_IMAGECATEGORY + ","
					+ DatabaseHandler.M_ISREAD + "" +

					")" + " VALUES (" + "'" + img.getImageID() + "'," + "'"
					+ img.getImageUrl() + "'," + "'" + img.getImageCategory()
					+ "'," + "'1')";

			Log.e("insert ", ">>" + selectQuery1);

			// db.rawQuery(selectQuery1, null);
			db.execSQL(selectQuery1);

			// final ContentValues values = new ContentValues();
			// values.put(DatabaseHandler.M_IMAGEID, img.getImageID());
			// values.put(DatabaseHandler.M_IMAGEURL, img.getImageUrl());
			// values.put(DatabaseHandler.M_IMAGECATEGORY,
			// img.getImageCategory());
			// values.put(DatabaseHandler.M_ISREAD, "1");
			// db.insert(DatabaseHandler.TABLE_MEMEITEMS, null, values);
		}

		db.close(); // Closing database connection
	}

	public boolean isRead(final String imageID, final String catType) {

		final Vector<ImageInfo> imageList = new Vector<ImageInfo>();
		imageList.removeAllElements();
		// Select All Queryb
		final String selectQuery = " SELECT " + " * FROM "
				+ DatabaseHandler.TABLE_MEMEITEMS + " where  "
				+ DatabaseHandler.M_IMAGEID + "='" + imageID + "' AND "
				+ DatabaseHandler.M_ISREAD + "='" + "1" + "' AND "
				+ DatabaseHandler.M_IMAGECATEGORY + "='" + catType + "'";

		final SQLiteDatabase db = getReadableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);
		boolean isRead = false;
		// looping through all rows and adding to list
		if (cursor.getCount() > 0) {
			isRead = true;

		} else {
			isRead = false;
		}
		db.close();
		return isRead;

	}

	// New read information catetory wise
	//
	// public String readCatetorywise(final String imageID,final String
	// category) {
	//
	// final Vector<ImageInfo> imageList = new Vector<ImageInfo>();
	// imageList.removeAllElements();
	// int total;
	// // Select All Queryb
	// final String selectQuery = " SELECT " + " * FROM "
	// + DatabaseHandler.TABLE_MEMEITEMS + " where  "
	// + DatabaseHandler.M_IMAGEID + "='" + imageID + "' AND "
	// + DatabaseHandler.M_IMAGECATEGORY + "='" + category + "' AND "
	// + DatabaseHandler.M_ISREAD + "='" + "1" + "'";
	//
	// final SQLiteDatabase db = getReadableDatabase();
	// final Cursor cursor = db.rawQuery(selectQuery, null);
	//
	//
	// total = cursor.getCount();
	// String totalCatewotrywise = String.valueOf(total);
	//
	//
	// db.close();
	// return totalCatewotrywise;
	// }
	//

	// end read information

}
