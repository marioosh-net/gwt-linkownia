package net.marioosh.gwt.shared.model.helper;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Criteria implements Serializable {

	private int start;
	private int count;
	
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	
	public int getStart() {
		return start;
	}
	
	
	public void setStart(int start) {
		this.start = start;
	}

}
