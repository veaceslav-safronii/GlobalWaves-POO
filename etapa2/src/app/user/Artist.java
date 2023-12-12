package app.user;

import app.audio.Collections.Album;
import app.audio.Files.Song;
import app.extras.Event;
import app.extras.Merch;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
public class Artist extends User {
    private List<Album> albums;
    private List<Merch> merches;
    private List<Event> events;
    public Artist(String username, int age, String city) {
        super(username, age, city, "artist");
        albums = new ArrayList<>();
    }

    public String addAlbum (String name, Integer releaseYear, String description, List<SongInput> songs) {
        if (!super.getType().equals("artist")) {
            return super.getUsername() + " is not an artist.";
        }

        if (albums.stream().anyMatch(album -> album.getName().equals(name))) {
            return getUsername() + " has another album with the same name.";
        }

        if (songs.stream().map(SongInput::getName).distinct().count() != songs.size()) {
            return getUsername() + " has the same song at least twice in this album.";
        }

        albums.add(new Album(name, releaseYear, description, songs));
        return getUsername() + " has added new album successfully.";
    }
}
