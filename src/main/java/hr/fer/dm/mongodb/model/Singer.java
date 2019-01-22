package hr.fer.dm.mongodb.model;

import java.util.ArrayList;
import java.util.List;

public class Singer {

	public Oid _id;
	public String name;
	public String bio;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Oid get_id() {
		return _id;
	}

	public void set_id(Oid _id) {
		this._id = _id;
	}

	

	
	
	
}
