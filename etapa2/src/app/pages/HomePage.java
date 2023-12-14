package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;

@Getter
public class HomePage implements Page {
    private static final int LIMIT = 5;
    private final ArrayList<String> likedSongs;
    private final ArrayList<String> followedPlaylists;

    public HomePage(ArrayList<Song> likedSongs, ArrayList<Playlist> followedPlaylists) {

        this.likedSongs = getTop5Songs(likedSongs);
        this.followedPlaylists = getTop5Playlists(followedPlaylists);

    }

    private ArrayList<String> getTop5Songs(ArrayList<Song> likedSongs) {
        ArrayList<Song> sortedSongs = new ArrayList<>(likedSongs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        ArrayList<String> topSongs = new ArrayList<>();
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

    private ArrayList<String> getTop5Playlists(ArrayList<Playlist> followedPlaylists) {
        ArrayList<Playlist> sortedPlaylists = new ArrayList<>(followedPlaylists);
        sortedPlaylists.sort(Comparator.comparingInt(playlist ->
                {
                    Integer likes = 0;
                    for (Song song : playlist.getSongs()) {
                        likes += song.getLikes();
                    }
                    return likes;
                }));
        ArrayList<String> topPlaylists = new ArrayList<>();
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

    @Override
    public String printCurrentPage() {
        return "Liked songs:\n\t"
                + likedSongs
                + "\n\nFollowed playlists:\n\t"
                + followedPlaylists;
    }
}
