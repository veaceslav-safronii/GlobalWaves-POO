package app.audio.Collections;

import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AlbumOutput {
    private final String name;
    private final List<String> songs;

    public AlbumOutput(Album album) {
        this.name = album.getName();
        this.songs = new ArrayList<>();
        for (SongInput song : album.getSongs()) {
            songs.add(song.getName());
        }
    }
}
