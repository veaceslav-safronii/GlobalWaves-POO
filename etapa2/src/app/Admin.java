package app;

import app.audio.Collections.*;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.player.Player;
import app.user.Artist;
import app.user.Host;
import app.user.NormalUser;
import app.user.User;
import app.utils.Enums;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * The type Admin.
 */
public final class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static List<Album> albums = new ArrayList<>();
    private static int timestamp = 0;
    private static final int LIMIT = 5;

    private Admin() {
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new NormalUser(userInput.getUsername(),
                    userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }


    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                                         episodeInput.getDuration(),
                                         episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            if (user.getType().equals("normal")) {
                playlists.addAll(((NormalUser) user).getPlaylists());
            }
        }
        return playlists;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            if (user.getType().equals("normal")
                    && ((NormalUser) user).getStatus() == Enums.UserStatus.ONLINE) {
                ((NormalUser) user).simulateTime(elapsed);
            }
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Gets online users
     *
     * @return online users
     */
    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getType().equals("normal")
                    && ((NormalUser) user).getStatus() == Enums.UserStatus.ONLINE) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    /**
     * Adds a user in app
     *
     * @param username new user's name
     * @param age new user's age
     * @param city new user's city
     * @param type new user's type
     * @return String
     */
    public static String addUser(String username, Integer age, String city, String type) {
        if (getUser(username) != null) {
            return "The username " + username + " is already taken.";
        }
        switch (type) {
            case "user" -> users.add(new NormalUser(username, age, city));
            case "artist" -> users.add(new Artist(username, age, city));
            case "host" -> users.add(new Host(username, age, city));
            default -> {
                return "Invalid type.";
            }
        }
        return "The username " + username + " has been added successfully.";
    }

    public static String deleteUser(String username) {
        User user = getUser(username);
        if (user == null) {
            return "The username " + username + " doesn't exist.";
        }

        boolean canBeDeleted = true;

        switch (user.getType()) {
            case "normal":
                NormalUser normalUser = (NormalUser) user;

                for (User user1 : users) {
                    if (user1.getType().equals("normal")) {
                        for (Playlist playlist : normalUser.getPlaylists()) {
                            if (playlist.equals(normalUser.getPlayer()
                                    .getCurrentAudioCollection())) {
                                canBeDeleted = false;
                                break;
                            }
                        }
                    }
                }

                if (canBeDeleted) {
                    users.stream().filter(user1 -> user1.getType().equals("normal"))
                            .forEach(user1 -> ((NormalUser) user1).getFollowedPlaylists()
                                    .removeAll(((NormalUser) user).getPlaylists()));
                    normalUser.getLikedSongs().forEach(Song::dislike);
                    normalUser.getFollowedPlaylists().forEach(Playlist::decreaseFollowers);
                    Admin.users.remove(user);
                }
                break;
            case "artist":
                Artist artist = (Artist) user;

                for (User user1 : users) {
                    if (user1.getType().equals("normal")) {
                        NormalUser normalUser1 = (NormalUser) user1;

                        for (Album album : ((Artist) user).getAlbums()) {
                            if (album.equals(normalUser1.getPlayer()
                                    .getCurrentAudioCollection())
                                || album.getSongs().stream()
                                    .anyMatch(song ->
                                            song.equals(normalUser1.getPlayer()
                                                    .getCurrentAudioFile()))) {
                                canBeDeleted = false;
                                break;
                            }
                        }
                        if (normalUser1.getCurrentPageType().equals("Artist")
                                && normalUser1.getCurrentPage()
                                .getName().equals(username)) {
                            canBeDeleted = false;
                            break;
                        }
                    }
                }
                if (canBeDeleted) {
                    for (User user1 : users) {
                        if (user1.getType().equals("normal")) {
                            for (Album album : artist.getAlbums()) {
                                ArrayList<Song> songs = album.getSongs();
                                ((NormalUser) user1).getLikedSongs().removeAll(songs);
                            }
                        }
                    }
                    artist.getAlbums().forEach(Admin::removeAlbum);
                    users.remove(user);
                }
                break;
            case "host":
                Host host = (Host) user;
                for (User user1 : users) {
                    if (user1.getType().equals("normal")) {
                        NormalUser normalUser1 = (NormalUser) user1;
                        for (Podcast podcast : host.getPodcasts()) {
                            if (podcast.equals(normalUser1.getPlayer()
                                    .getCurrentAudioCollection())) {
                                canBeDeleted = false;
                                break;
                            }
                        }
                        if (normalUser1.getCurrentPageType().equals("Host")) {
                            if (normalUser1.getCurrentPage()
                                    .getName().equals(username)) {
                                canBeDeleted = false;
                            }
                        }
                    }
                }
                if (canBeDeleted) {
                    Admin.podcasts.removeAll(host.getPodcasts());
                    Admin.users.remove(user);
                }
                break;
            default:
                break;
        }
        if (!canBeDeleted) {
            return username + " can't be deleted.";
        } else {
            return username + " was successfully deleted.";
        }
    }

    /**
     * Adds songs from a new album
     *
     * @param songList input songs
     */
    public static void addSongs(List<Song> songList) {
        songs.addAll(songList);
    }

    /**
     * Adds the new album
     * @param album the new album
     */
    public static void addAlbum(Album album) {
        albums.add(album);
    }

    /**
     * Removes the album
     * @param album the album to be removed
     */
    public static void removeAlbum(Album album) {
        songs.removeAll(album.getSongs());
        albums.remove(album);
    }

    /**
     * Shows albums of an artist
     *
     * @param artist the artist
     * @return the albums
     */
    public static List<AlbumOutput> showAlbums(Artist artist) {
        List<Album> albumList = artist.getAlbums();
        List<AlbumOutput> albumOutputs = new ArrayList<>();
        for (Album album : albumList) {
            albumOutputs.add(new AlbumOutput(album));
        }
        return albumOutputs;
    }

    /**
     * Adds podcast into the app
     *
     * @param podcast podcast to be added
     */
    public static void addPodcast(Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * Gets the albums
     *
     * @return albums
     */
    public static List<Album> getAlbums() {
        return new ArrayList<>(albums);
    }

    /**
     * Gets the users
     *
     * @return users
     */
    public static List<User> getUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Removes a podcast from the app
     *
     * @param podcast podcast to be removed
     */
    public static void removePodcast(Podcast podcast) {
        podcasts.remove(podcast);
    }

    public static List<PodcastOutput> showPodcasts(Host host) {
        List<Podcast> podcastList = host.getPodcasts();
        List<PodcastOutput> podcastOutputs = new ArrayList<>();
        for (Podcast podcast : podcastList) {
            podcastOutputs.add(new PodcastOutput(podcast));
        }
        return podcastOutputs;
    }

    public static List<String> getAllUsers() {
        List<String> usersList = new ArrayList<>();
        users.stream().filter(user -> user.getType()
                .equals("normal")).forEach(user -> usersList.add(user.getUsername()));
        users.stream().filter(user -> user.getType()
                .equals("artist")).forEach(user -> usersList.add(user.getUsername()));
        users.stream().filter(user -> user.getType()
                .equals("host")).forEach(user -> usersList.add(user.getUsername()));
        return usersList;
    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        albums = new ArrayList<>();
        timestamp = 0;
    }
}
