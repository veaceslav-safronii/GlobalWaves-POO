package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class Album extends AudioCollection {
    private Integer releaseYear;
    private String description;
    private ArrayList<Song> songs;

    public Album(final String name, final String owner, final Integer releaseYear,
                 final String description, final ArrayList<Song> songs) {
        super(name, owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }

    /**
     * Gets number of songs
     * @return number of songs
     */
    @Override
    public int getNumberOfTracks() {
        return getSongs().size();
    }

    /**
     * Gets the song by index
     * @param index the index
     * @return the song
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    /**
     * Gets the number of likes
     * @return album's number of likes
     */
    public Integer getNumberOfLikes() {
        Integer nrLikes = 0;
        for (Song song : songs) {
            nrLikes += song.getLikes();
        }
        return nrLikes;
    }
}
