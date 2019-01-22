
package hr.fer.dm.mongodb.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class MongoDAO {

    private DBCollection col;
    private DBCollection singers;
    private DBCollection concerts;
    private DBCollection places;
    private DBCollection similarSingers;
    private DBCollection songs;



    public MongoDAO(MongoClient mongo) {

        this.singers = mongo.getDB("concertBook").getCollection("singers");
        this.concerts = mongo.getDB("concertBook").getCollection("concerts");
        this.places = mongo.getDB("concertBook").getCollection("places");
        this.similarSingers = mongo.getDB("concertBook").getCollection(
                "similarSingers");


    }


    public void saveSingers(String json) {
        List<DBObject> doc = (List<DBObject>) JSON.parse(json);
        this.singers.insert(doc);

    }

    public void saveConcerts(String json) {
        List<DBObject> doc = (List<DBObject>) JSON.parse(json);
        this.concerts.insert(doc);

    }

    public void savePlaces(String json) {
        List<DBObject> doc = (List<DBObject>) JSON.parse(json);
        this.places.insert(doc);

    }

    public void saveSimilarSingers(String json) {
        List<DBObject> doc = (List<DBObject>) JSON.parse(json);
        this.similarSingers.insert(doc);
    }

    public void saveSongs(String json){
        List<DBObject> doc = (List<DBObject>) JSON.parse(json);
        this.songs.insert(doc);
    }

    public String getSingers() {
        DBCursor cursor = singers.find();
        StringBuilder build = new StringBuilder();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            build.append(doc.toString() + ",");
        }

        int size = build.toString().length();

        if (size == 0)
            return "";

        return "[" + build.toString().substring(0, size - 1) + "]";
    }

    public String getSingerConcerts(String singer) {
        BasicDBObject whereQuery = new BasicDBObject();
        StringBuilder build = new StringBuilder();
        whereQuery.put("singerId", singer);
        DBCursor cursor = concerts.find(whereQuery);

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            build.append(doc.toString() + ",");
        }
        int size = build.toString().length();

        if (size == 0)
            return "";

        return "[" + build.toString().substring(0, size - 1) + "]";
    }

    public String getAllSimilarSingers() {
        DBCursor cursor = similarSingers.find();
        StringBuilder build = new StringBuilder();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            build.append(doc.toString() + ",");
        }

        int size = build.toString().length();

        if (size == 0)
            return "";

        return "[" + build.toString().substring(0, size - 1) + "]";
    }

    public String getSimilarSingers(String name) {
        BasicDBObject whereQuery = new BasicDBObject();
        StringBuilder build = new StringBuilder();
        whereQuery.put("primarySinger", name);
        DBCursor cursor = similarSingers.find(whereQuery);

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            build.append(doc.toString() + ",");
        }
        int size = build.toString().length();

        if (size == 0)
            return "";

        return "[" + build.toString().substring(0, size - 1) + "]";
    }

    public String getAllSongs(){
        DBCursor cursor = songs.find();
        StringBuilder build = new StringBuilder();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            build.append(doc.toString() + ",");
        }

        int size = build.toString().length();

        if (size == 0)
            return "";

        return "[" + build.toString().substring(0, size - 1) + "]";
    }

    public String getSongs(String name){
        BasicDBObject whereQuery = new BasicDBObject();
        StringBuilder build = new StringBuilder();
        whereQuery.put("singer", name);
        DBCursor cursor = songs.find(whereQuery);

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            build.append(doc.toString() + ",");
        }
        int size = build.toString().length();

        if (size == 0)
            return "";

        return "[" + build.toString().substring(0, size - 1) + "]";
    }

    public String getDateConcerts(String date) {
        BasicDBObject whereQuery = new BasicDBObject();
        StringBuilder build = new StringBuilder();
        whereQuery.put("date", date);
        DBCursor cursor = concerts.find(whereQuery);

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            build.append(doc.toString() + ",");
        }
        int size = build.toString().length();

        if (size == 0)
            return "";

        return "[" + build.toString().substring(0, size - 1) + "]";
    }

    public String getConcerts() {
        DBCursor cursor = concerts.find();
        StringBuilder build = new StringBuilder();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            build.append(doc.toString() + ",");
        }

        int size = build.toString().length();

        if (size == 0)
            return "";

        return "[" + build.toString().substring(0, size - 1) + "]";
    }

    public String getConcertPlaces(String concert) {
        BasicDBObject whereQuery = new BasicDBObject();
        StringBuilder build = new StringBuilder();
        whereQuery.put("concertId", concert);
        DBCursor cursor = places.find(whereQuery);

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            build.append(doc.toString() + ",");
        }
        int size = build.toString().length();

        if (size == 0)
            return "";

        return "[" + build.toString().substring(0, size - 1) + "]";
    }

    public String getPlaces() {
        DBCursor cursor = places.find();
        StringBuilder build = new StringBuilder();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            build.append(doc.toString() + ",");
        }

        int size = build.toString().length();

        if (size == 0)
            return "";

        return "[" + build.toString().substring(0, size - 1) + "]";
    }




    public void deleteAllCollections() {
        this.singers.drop();
        this.concerts.drop();
        this.places.drop();
        this.similarSingers.drop();
    }


}

