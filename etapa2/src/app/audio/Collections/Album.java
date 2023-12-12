package app.audio.Collections;

import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Album {
    private String name;
    private Integer releaseYear;
    private String description;
    private List<SongInput> songs;

    public Album(String name, Integer releaseYear, String description, List<SongInput> songs) {
        this.name = name;
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
    public boolean containsSong(final SongInput song) {
        return songs.contains(song);
    }

}
