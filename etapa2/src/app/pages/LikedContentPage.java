package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;

import java.util.ArrayList;
import java.util.List;

public class LikedContentPage implements Page {
    private final ArrayList<Song> likedSongs;
    private final ArrayList<Playlist> followedPlaylists;

    public LikedContentPage(ArrayList<Song> likedSongs, ArrayList<Playlist> followedPlaylists) {
        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
    }

    @Override
    public String printCurrentPage() {
        StringBuilder str1 = new StringBuilder("Liked Songs:\n\t[");
        StringBuilder str2 = new StringBuilder("Followed Playlists:\n\t[");
        for (Song song : likedSongs) {
            str1.append(song.getName()).append(" - ").append(song.getArtist()).append(", ");
        }
        str1.append("]\n\n");

        for (Playlist playlist : followedPlaylists) {
            str2.append(playlist.getName()).append(" - ").append(playlist.getOwner()).append(", ");
        }
        str2.append("]");
        return str1.append(str2).toString();
    }
}
