package hr.fer.dm.mongodb.model;

public class SimilarSinger {

	public Oid _id;
	public String primarySinger;
	public String foreignSinger;
	
	
	
	public SimilarSinger(){
		
	}



	public String getPrimarySinger() {
		return primarySinger;
	}



	public void setPrimarySinger(String primarySinger) {
		this.primarySinger = primarySinger;
	}



	public String getForeignSinger() {
		return foreignSinger;
	}



	public void setForeignSinger(String foreignSinger) {
		this.foreignSinger = foreignSinger;
	}



	public Oid get_id() {
		return _id;
	}



	public void set_id(Oid _id) {
		this._id = _id;
	}
}
