package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.audio.LibraryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LikedContentPage implements Page {
    private final ArrayList<Song> likedSongs;
    private final ArrayList<Playlist> followedPlaylists;

    public LikedContentPage(ArrayList<Song> likedSongs, ArrayList<Playlist> followedPlaylists) {
        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
    }

    @Override
    public String printCurrentPage() {
        return "Liked songs:\n\t["
                + likedSongs.stream().map(song ->
                        song.getName() + " - "
                        + song.getArtist())
                .collect(Collectors.joining(", "))
                + "]\n\nFollowed playlists:\n\t["
                + followedPlaylists.stream().map(playlist ->
                        playlist.getName() + " - " + playlist.getOwner())
                .collect(Collectors.joining(", "))
                + "]";
    }
}
