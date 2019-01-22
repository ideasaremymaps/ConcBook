package hr.fer.dm.mongodb.model;

public class Song {

    public Oid _id;
    public String singer;
    public String song;
    public String album;
    public String url;

    public Oid get_id() {
        return _id;
    }
    public void set_id(Oid _id) {
        this._id = _id;
    }
    public String getSinger() {
        return singer;
    }
    public void setSinger(String singer) {
        this.singer = singer;
    }
    public String getSong() {
        return song;
    }
    public void setSong(String song) {
        this.song = song;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }


}