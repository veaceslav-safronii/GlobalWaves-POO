package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.utils.Enums;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Album extends AudioCollection {
    private Integer releaseYear;
    private String description;
    private ArrayList<Song> songs;

    public Album(String name, String owner, Integer releaseYear, String description, ArrayList<Song> songs) {
        super(name, owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }

    /**
     * Contains song boolean.
     *
     * @param song the song
     * @return the boolean
     */
    public boolean containsSong(final Song song) {
        return songs.contains(song);
    }

    @Override
    public int getNumberOfTracks() {
        return getSongs().size();
    }

    @Override
    public AudioFile getTrackByIndex(int index) {
        return songs.get(index);
    }
}
