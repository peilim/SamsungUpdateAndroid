package com.aacom.memecabin.utils;

import java.util.Arrays;
import java.util.List;

import android.graphics.Bitmap;
import android.net.Uri;

import com.memecabin.holder.CriticMessageFromDan;

public class AppConstant {

	// ===== other dummy site=====
	public static Uri outputFileUri = null;
	public static String password = null;
	public static final String CLASSIFIED = "classifieds";
	public static final int CAPTURE_IMAGE = 1;
	public static final int SELECT_IMAGE = 2;
	public static final String facebookURL = "http://www.facebook.com";
	public static final String instagramURL = "http://www.facebook.com";

	public static final String mopubAdsUnitId = "7d1b1c0ee76a463e9f2bceb82efae15f";
	public static final String mopubAdsUnitIdFullScreen = "9c9b3bdeabc741df9e2c9a8a2a7d5033";
	// public static final String instagramURL = "http://www.facebook.com";

	public static int totalEveryoneMeme = 0;
	public static int totalMotivationalMeme = 0;

	public static int totalRacyMeme = 0;

	public static int totalSpiffyMeme = 0;

	public static Bitmap fbImage = null;
	public static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");

	public static CriticMessageFromDan criticMessage = null;

	// public static String imageUrl = "";

	// ========for Memeapps==============

	// public static boolean twitterFlag = false;

	// public static boolean danmessageshow = false;
	// public static boolean showrateapps = false;

	public static boolean favView = false;
	public static boolean clickflage = false;
	public static boolean clicksigledad = false;
	public static boolean clickrateapp = false;
	public static String twitterImage = "";

	public static boolean trendingRefresh = false;
	public static boolean refreshFavourite = false;
	public static boolean removefaveritesimage = false;

	public static String memeEveryone = "on";
	public static String motivationIns = "on";
	public static String racyMeme = "on";
	public static String tempPath = "";

	public static String homeScr = "show";
	public static String racyLock = "lock";
	public static String image = "";
	public static String spiffy = "";

	public static String userID = "";
	public static boolean flag = false;
	public static boolean readFlagmeme = false;
	public static boolean readallmemeshare = false;
	public static boolean shareviewclose = false;
	public static boolean readtrenshare = false;
	public static boolean trensview = false;
	public static boolean readmotishare = false;
	public static boolean motishareview = false;
	public static boolean readfavoriteshare = false;
	public static boolean favoriteshareview = false;
	public static boolean readracyshare = false;
	public static boolean racyshareview = false;
	public static boolean readFlagmoti = false;
	public static boolean readFlagRacy = false;
	public static boolean passcodeflage = false;
	// public static boolean passcodesecond = false;
	public static boolean checkverfypasscode = false;
	// public static boolean onpageChangeview= true;

	// ===========Status==================
	public static String status = "";
	public static String fbStatus = "";
	public static String messageregistration = "";
	public static boolean likeflage = false;
	public static boolean favflage = false;
	public static boolean flageTend = false;
	// public static boolean flagForgetpass = false;
	public static int position = 0;
	// public static int favsize = 0;
	// public static boolean flageAgain = false;
	public static boolean flageAgainRacy = false;
	public static boolean flageAgainMeme = false;
	public static boolean flageAgainMoti = false;

	// public static String racyflage = "";
	public static int firstimeflage = 1;
	public static boolean prefBckFlage = false;

	// public static String imageBaseUrl = "http://54.69.158.41/api/";
	public static String imageBaseUrl = "";

	public static String thumbsMedium = "";
	public static String thumbSmall = "";

	public static String imageid = "";
	public static String imagecategory;
	public static String totalLike = "";
	public static String racyImageUrl = "";
	public static String treadingMemesName = "";
	public static boolean fromcamera = false;
	public static String showpass = "";
	public static int showpassCount = 0;
	public static String matchpasscode = "";
	public static String veryfypasscode = "";
	public static String appId = "";
	public static final int maxLoad = 2;

	public static int loginScreenIndex = 0;
	public static int homeScreenIndex = 1;
	// public static boolean fromSplashscreen = false;
	public static String fromscreen = "main";
	public static String loadTreding = "";
	public static String fburl = "https://www.facebook.com/memecabin";
	public static String singledadurl = "http://www.facebook.com/singledadlaughing";
	public static String instaramurl = "http://www.instagram.com/danoah";
	public static String urlmessage = "";

	public static boolean upcomingCallOnce = true;

	// admob unite ID

	public static String MY_FullPage_AD_UNIT_ID = "ca-app-pub-6246253320597551/8542627026";
	public static String MY_Banner_AD_UNIT_ID = "ca-app-pub-6246253320597551/7065893823";
	public static String myFavorite = "0";
	public static int currentAllMemeBadge = 0;
	public static int currentMotiBadge = 0;
	public static int currentRacyBadge = 0;
	public static int currentSpiffBadge = 0;
	public static int minScreenWidth = 0;

	public static int minScreenHeight = 0;
	public static boolean isSamsung = false;

	// SKUs for our products: the premium upgrade (non-consumable) and gas
	// (consumable)
	public static final String SKU_PREMIUM = "lifetime";

	// SKU for our subscription (infinite gas)
	public static final String SKU_YEARLY = "yearly";

	// public static final String FACEBOOK_APPID = "662404677134011";// from
	// Vuehome

	// public static final String FACEBOOK_APPID = "841310412569994";// from DAN

	// public static final String[] FACEBOOK_PERMISSION = { "read_stream",
	// "user_photos", "email", "photo_upload", "publish_actions" };

	public static final String FACEBOOK_APPID = "801343876547394";// from DAN
																	// 841310412569994

	public static final String[] FACEBOOK_PERMISSIONWITHEMAIL = {
			"publish_stream", "photo_upload", "email" };

	public static final long TWENTY_FOUR_HOUR_IN_MILLISECOND = 86400000;

	public static final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtgiwrtEzurreeYC9MqmicwPy1psSLJjMeYiJbfzYXSsBxZrx6jYaiuE0TbaxjoFJZscAZsKF49JJnJV8hEeQsNM2VQgQ1v1dBBJ7RvqEkOJYySVNM5jJf/j7MEz+OswjtHig7if0Ng1s9Ye80DXoAYjsjShGpQbscNrW/NBXC9JPum07TgaQi4dKyxvX2t0fsUY9doqxOFGRkAEGXM6pWb4ukpO7CEukOQkJtAHXsqULhKYgIZQEytrvMO26uOURyMsf5gfOTIqbLkHxV6KImxLHlfh99SuQIG/FfeWRqOoCvbgXEm7Se7RWKKMHHdlhi31fcimFYDkoyOsjOBUzQIDAQAB";

	public static final int PAGINATION_ITEM_COUNT = 32;

	public static String addname_banner = "MemeCabin Android Banner";
	public static String meme_banner_add = "ca-app-pub-9152009225005926/2645517992";
	public static String addname_int = "MemeCabin Android Intersitial";
	public static String meme_int_add = "ca-app-pub-9152009225005926/6517314396";

}
