package com.valtech.poc.sms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ShiftTimings")
public class ShiftTimings {
	@Id
	@Column(name = "stId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int stId;
	private String stStart;
	private String stEnd;

	public int getStId() {
		return stId;
	}

	public void setStId(int stId) {
		this.stId = stId;
	}

	public String getStStart() {
		return stStart;
	}

	public void setStStart(String stStart) {
		this.stStart = stStart;
	}

	public String getStEnd() {
		return stEnd;
	}

	public void setStEnd(String stEnd) {
		this.stEnd = stEnd;
	}

	@Override
	public String toString() {
		return "ShiftTimings [stId=" + stId + ", stStart=" + stStart + ", stEnd=" + stEnd + "]";
	}

	public ShiftTimings() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShiftTimings(int stId, String stStart, String stEnd) {
		super();
		this.stId = stId;
		this.stStart = stStart;
		this.stEnd = stEnd;
	}

	public ShiftTimings(String stStart, String stEnd) {
		super();
		this.stStart = stStart;
		this.stEnd = stEnd;
	}

}
