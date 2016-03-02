package client;

import java.util.Date;

public class Campaign {
	public Date startDate;
	private String cpm;
	private int id;
	private String name;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public String getCpm() {
		return cpm;
	}
	public void setCpm(String cpm) {
		this.cpm = cpm;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
