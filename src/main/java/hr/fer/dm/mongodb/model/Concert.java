package hr.fer.dm.mongodb.model;

import java.util.ArrayList;
import java.util.List;

public class Concert {
	
	public Oid _id;
	public String id;
	public double latitude;
	public double longitude;
	public String name;
	public String adress;
	public String date;
	public String city;
	public String country;
	public String singerId;
	
	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}


	public String getSingerId() {
		return singerId;
	}


	public void setSingerId(String singerId) {
		this.singerId = singerId;
	}


	public Oid get_id() {
		return _id;
	}


	public void set_id(Oid _id) {
		this._id = _id;
	}
	
	
	
	

}
