package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.extras.Event;
import app.extras.Merch;
import app.player.Player;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Album getAlbum(String name) {
        return albums.stream().filter(album -> album.getName()
                .equals(name)).findAny().orElse(null);
    }

    public String addAlbum(String name, Integer releaseYear, String description,
                           List<SongInput> songInputList) {
        if (getAlbum(name) != null) {
            return getUsername() + " has another album with the same name.";
        }

        ArrayList<Song> songs = songInputList.stream()
                .map(songInput -> new Song(songInput.getName(),
                        songInput.getDuration(),
                        songInput.getAlbum(), songInput.getTags(),
                        songInput.getLyrics(), songInput.getGenre(),
                        songInput.getReleaseYear(), songInput.getArtist()))
                .collect(Collectors.toCollection(ArrayList::new));

        if (songs.stream().map(Song::getName).distinct().count() != songs.size()) {
            return getUsername() + " has the same song at least twice in this album.";
        }

        Album album = new Album(name, getUsername(), releaseYear, description, songs);
        albums.add(album);
        Admin.addSongs(songs);
        Admin.addAlbum(album);
        return getUsername() + " has added new album successfully.";
    }

    public String removeAlbum(String name) {
        Album album = getAlbum(name);
        if (album == null) {
            return getUsername() + " doesn't have an album with the given name.";
        }

        List<String> songs = album.getSongs().stream()
                .map(LibraryEntry::getName).toList();

        List<Player> players = Admin.getUsers().stream()
                .filter(user -> user.getType()
                        .equals("normal"))
                .map(user -> (NormalUser) user)
                .map(NormalUser::getPlayer).toList();

       List<String> audiofiles = players.stream()
               .map(Player::getCurrentAudioFile)
               .filter(Objects::nonNull)
               .map(LibraryEntry::getName).toList();

       List<String> audiocollections = players.stream()
               .map(Player::getCurrentAudioCollection)
               .filter(Objects::nonNull)
               .map(LibraryEntry::getName).toList();

       if (songs.stream().anyMatch(audiofiles::contains)
               || audiocollections.contains(name) || Admin.getPlaylists().stream()
               .anyMatch(playlist -> album.getSongs()
                       .stream().anyMatch(song -> playlist.getSongs().contains(song))))
           return getUsername() + " can't delete this album.";

       Admin.removeAlbum(album);
       albums.remove(album);

       return getUsername() + " deleted the album successfully.";
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

    public Event getEvent(String name) {
        return events.stream().filter(event -> event.getName()
                .equals(name)).findAny().orElse(null);
    }

    public String addEvent(String name, String description, String date) {
        Event event = getEvent(name);
        if (event != null) {
            return getUsername() + " has another event with the same name.";
        }

        if (!isValidFormat(date)) {
            return "Event for " + getUsername() + " does not have a valid date.";
        }

        events.add(new Event(name, description, date));
        return getUsername() + " has added new event successfully.";
    }

    public String removeEvent(String name) {
        Event event = getEvent(name);
        if (event == null) {
            return getUsername() + " doesn't have an event with the given name.";
        }

        events.remove(event);
        return getUsername() + " deleted the event successfully.";
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
