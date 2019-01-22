package hr.fer.dm.lastfm;

import hr.fer.dm.mongodb.model.SimilarSinger;
import hr.fer.dm.mongodb.model.Song;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Track;

public class SongApi {

    public static List<Song> loadSongs(String name){
        Collection<Track> tracks=Artist.getTopTracks(name, "8af5bde596cf8d1af17ad71e008d4404");

        List<Song> songs=new ArrayList<Song>();
        int i=0;

        for(Track t:tracks){
            Song s=new Song();
            s.setSinger(name);
            s.setSong(t.getName());
            s.setAlbum(t.getAlbum());
            s.setUrl(t.getUrl());

            songs.add(s);


        }

        return songs;
    }



    public static String convertToJSON(final List<Song> concerts)  {
        final OutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(out, concerts);
        } catch (JsonGenerationException e) {}
        catch (JsonMappingException e) {}
        catch (IOException e) {}

        final byte[] data = ((ByteArrayOutputStream) out).toByteArray();

        return new String(data);
    }


    public static List<Song> convertToObject(String json){
        ObjectMapper mapper = new ObjectMapper();
        List<Song> list=null;
        try {
            list = mapper.readValue(json,
                    TypeFactory.defaultInstance().constructCollectionType(List.class,
                            Song.class));
        } catch (JsonParseException e) {}
        catch (JsonMappingException e) {}
        catch (IOException e) {}

        return list;
    }
}
