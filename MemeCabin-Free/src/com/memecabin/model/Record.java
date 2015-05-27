package com.memecabin.model;

public class Record {

	// "id":"1","message":"A few of you are seeing a bazillion ads popup. Sorry! We're fixing it! If you really wanna be awesome, disable the ads! It will help SDL more than anything! Wanna watch a video about why?","IsPublish":"on","url":"http:\/\/www.danoah.com\/well-this-is-fun-lets-all-take-a-break-for-a-couple-days","title":"Yikes!
	// If you're seeing TONS of ads, it's a bug!","counter":"1"

	String id = "";
	String IsPublish = "";
	String url = "";
	String title = "";
	String counter = "";
	String message = "";
	String top_button_text = "";
	String middle_button_text = "";
	String botton_button_text = "";

	public String getTop_button_text() {
		return top_button_text;
	}

	public void setTop_button_text(String top_button_text) {
		this.top_button_text = top_button_text;
	}

	public String getMiddle_button_text() {
		return middle_button_text;
	}

	public void setMiddle_button_text(String middle_button_text) {
		this.middle_button_text = middle_button_text;
	}

	public String getBotton_button_text() {
		return botton_button_text;
	}

	public void setBotton_button_text(String botton_button_text) {
		this.botton_button_text = botton_button_text;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the isPublish
	 */
	public String getIsPublish() {
		return IsPublish;
	}

	/**
	 * @param isPublish
	 *            the isPublish to set
	 */
	public void setIsPublish(String isPublish) {
		IsPublish = isPublish;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the counter
	 */
	public String getCounter() {
		return counter;
	}

	/**
	 * @param counter
	 *            the counter to set
	 */
	public void setCounter(String counter) {
		this.counter = counter;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
