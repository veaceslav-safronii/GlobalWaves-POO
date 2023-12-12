package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Files.Song;
import app.extras.Event;
import app.extras.Merch;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Artist extends User {
    private List<Album> albums;
    private List<Merch> merches;
    private List<Event> events;
    public Artist(String username, int age, String city) {
        super(username, age, city, "artist");
        albums = new ArrayList<>();
        merches = new ArrayList<>();
        events = new ArrayList<>();
    }

    public String addAlbum(String name, Integer releaseYear, String description, List<SongInput> songInputList) {
        if (albums.stream().anyMatch(album -> album.getName().equals(name))) {
            return getUsername() + " has another album with the same name.";
        }
        List<Song> songs = songInputList.stream().map(songInput -> new Song(songInput.getName(),
                songInput.getDuration(),
                songInput.getAlbum(), songInput.getTags(),
                songInput.getLyrics(), songInput.getGenre(), songInput.getReleaseYear(),
                songInput.getArtist())).collect(Collectors.toList());

        if (songs.stream().map(Song::getName).distinct().count() != songs.size()) {
            return getUsername() + " has the same song at least twice in this album.";
        }

        albums.add(new Album(name, releaseYear, description, songs));
        Admin.addSongs(songs);
        return getUsername() + " has added new album successfully.";
    }

    public String addMerch(String name, String description, Integer price) {
        if (merches.stream().anyMatch(merch -> merch.getName().equals(name))) {
            return getUsername() + " has merchandise with the same name.";
        }

        if (price < 0) {
            return "Price for merchandise can not be negative.";
        }
        merches.add(new Merch(name, description, price));
        return getUsername() + " has added new merchandise successfully.";
    }

    public String addEvent(String name, String description, String date) {
        if (events.stream().anyMatch(event -> event.getName().equals(name))) {
            return getUsername() + " has another event with the same name.";
        }

        if (!isValidFormat(date)) {
            return "Event for " + getUsername() + " does not have a valid date.";
        }
        events.add(new Event(name, description, date));
        return getUsername() + " has added new event successfully.";
    }

    private boolean isValidFormat(String date) {
        String format = "dd-MM-yyyy";
        try {
            LocalDate ld = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
            return ld.getYear() >= 1900 && ld.getYear() <= 2023;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
