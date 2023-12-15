package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.extras.Event;
import app.extras.Merch;
import app.player.Player;
import fileio.input.SongInput;
import lombok.Getter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class Artist extends User {
    private static final int MINYEAR = 1900;
    private static final int MAXYEAR = 2023;
    private List<Album> albums;
    private List<Merch> merches;
    private List<Event> events;
    public Artist(final String username, final int age, final String city) {
        super(username, age, city, "artist");
        albums = new ArrayList<>();
        merches = new ArrayList<>();
        events = new ArrayList<>();
    }

    /**
     * Gets an album by name
     * @param name album's name
     * @return album
     */
    public Album getAlbum(final String name) {
        return albums.stream().filter(album -> album.getName()
                .equals(name)).findAny().orElse(null);
    }

    /**
     * Adds an album
     * @param name album's name
     * @param releaseYear release year
     * @param description description
     * @param songInputList song input list
     * @return message
     */
    public String addAlbum(final String name, final Integer releaseYear,
                           final String description, final List<SongInput> songInputList) {
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

    /**
     * Removes an album
     * @param name album's name
     * @return message
     */
    public String removeAlbum(final String name) {
        Album album = getAlbum(name);
        if (album == null) {
            return getUsername() + " doesn't have an album with the given name.";
        }

        List<Song> songs = album.getSongs();

        List<Player> players = Admin.getUsers().stream()
                .filter(user -> user.getType()
                        .equals("normal"))
                .map(user -> (NormalUser) user)
                .map(NormalUser::getPlayer).toList();

       List<AudioFile> audiofiles = players.stream()
               .map(Player::getCurrentAudioFile)
               .filter(Objects::nonNull).toList();

       List<AudioCollection> audiocollections = players.stream()
               .map(Player::getCurrentAudioCollection)
               .filter(Objects::nonNull).toList();

       if (songs.stream().anyMatch(audiofiles::contains)
           || audiocollections.contains(album) || Admin.getPlaylists().stream()
           .anyMatch(playlist -> album.getSongs()
               .stream().anyMatch(song -> playlist.getSongs().contains(song)))) {
           return getUsername() + " can't delete this album.";
       }

       Admin.removeAlbum(album);
       albums.remove(album);

       return getUsername() + " deleted the album successfully.";
    }

    /**
     * Adds a merch
     * @param name name
     * @param description description
     * @param price price
     * @return message
     */
    public String addMerch(final String name, final String description, final Integer price) {
        if (merches.stream().anyMatch(merch -> merch.getName().equals(name))) {
            return getUsername() + " has merchandise with the same name.";
        }

        if (price < 0) {
            return "Price for merchandise can not be negative.";
        }

        merches.add(new Merch(name, description, price));
        return getUsername() + " has added new merchandise successfully.";
    }

    /**
     * Gets an event by name
     * @param name name
     * @return event
     */
    public Event getEvent(final String name) {
        return events.stream().filter(event -> event.getName()
                .equals(name)).findAny().orElse(null);
    }

    /**
     * Adds an event
     * @param name name
     * @param description description
     * @param date date
     * @return message
     */
    public String addEvent(final String name, final String description, final String date) {
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

    /**
     * Verifies event's date format validity
     * @param date date
     * @return boolean
     */
    private boolean isValidFormat(final String date) {
        String format = "dd-MM-yyyy";
        try {
            LocalDate ld = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
            return ld.getYear() >= MINYEAR && ld.getYear() <= MAXYEAR;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Removes an event
     * @param name name
     * @return message
     */
    public String removeEvent(final String name) {
        Event event = getEvent(name);
        if (event == null) {
            return getUsername() + " doesn't have an event with the given name.";
        }

        events.remove(event);
        return getUsername() + " deleted the event successfully.";
    }

    /**
     * Gets the number of likes of the artist
     * @return number of likes
     */
    public Integer getNumberOfLikes() {
        Integer nrLikes = 0;
        for (Album album : albums) {
            nrLikes += album.getNumberOfLikes();
        }
        return nrLikes;
    }
}
