package com.memecabin.holder;

import java.util.ArrayList;
import java.util.List;

import com.memecabin.model.Record;

public class CriticMessageFromDan {

	String status = "";
	String message = "";
	List<Record> result = new ArrayList<Record>();

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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

	/**
	 * @return the record
	 */
	public List<Record> getRecord() {
		return result;
	}

	/**
	 * @param record
	 *            the record to set
	 */
	public void setRecord(List<Record> record) {
		this.result = record;
	}

}
