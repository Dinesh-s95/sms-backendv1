package com.valtech.poc.sms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Floors")
public class Floors {
	@Id
	@Column(name = "fId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int fId;
	private String fName;
	private int fSeats;

	public int getfId() {
		return fId;
	}

	public void setfId(int fId) {
		this.fId = fId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public int getfSeats() {
		return fSeats;
	}

	public void setfSeats(int fSeats) {
		this.fSeats = fSeats;
	}

	public Floors() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Floors(int fId, String fName, int fSeats) {
		super();
		this.fId = fId;
		this.fName = fName;
		this.fSeats = fSeats;
	}

	public Floors(String fName, int fSeats) {
		super();
		this.fName = fName;
		this.fSeats = fSeats;
	}

	@Override
	public String toString() {
		return "Floors [fId=" + fId + ", fName=" + fName + ", fSeats=" + fSeats + "]";
	}

}
