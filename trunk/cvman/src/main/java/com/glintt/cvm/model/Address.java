package com.glintt.cvm.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "address")
public class Address extends Location implements CommunicationChannel {
	private static final long serialVersionUID = -180716234511568602L;

	private String streetName;
	private String buildingName;
	private String buildingNumber;
	private String floor;
	private String postOfficeBox;

	@Override
	public String getChannelCode() {
		return "Address";
	}

	@XmlElement
	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	@XmlElement
	public String getBuildingName() {
		return this.buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	@XmlElement
	public String getBuildingNumber() {
		return this.buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	@XmlElement
	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	@XmlElement
	public String getPostOfficeBox() {
		return this.postOfficeBox;
	}

	public void setPostOfficeBox(String postOfficeBox) {
		this.postOfficeBox = postOfficeBox;
	}

}