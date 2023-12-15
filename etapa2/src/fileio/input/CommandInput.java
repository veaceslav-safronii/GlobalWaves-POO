package fileio.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class CommandInput {
    private String command;
    private String username;
    private Integer timestamp;
    private String type; // song / playlist / podcast
    private FiltersInput filters; // pentru search
    private Integer itemNumber; // pentru select
    private Integer repeatMode; // pentru repeat
    private Integer playlistId; // pentru add/remove song
    private String playlistName; // pentru create playlist
    private Integer seed; // pentru shuffle
    private Integer age; // pentru add user
    private String city; // pentru add user
    private String name;
    private Integer releaseYear;
    private String description;
    private List<SongInput> songs;
    private String date; // event
    private Integer price; // merch
    private List<EpisodeInput> episodes; // podcast
    private String nextPage; // changePage

    public CommandInput() {
    }

    @Override
    public String toString() {
        return "CommandInput{"
                + "command='" + command + '\''
                + ", username='" + username + '\''
                + ", timestamp=" + timestamp
                + ", type='" + type + '\''
                + ", filters=" + filters
                + ", itemNumber=" + itemNumber
                + ", repeatMode=" + repeatMode
                + ", playlistId=" + playlistId
                + ", playlistName='" + playlistName + '\''
                + ", seed=" + seed
                + ", age=" + age
                + ", city='" + city + '\''
                + ", name='" + name + '\''
                + ", releaseYear=" + releaseYear
                + ", description='" + description + '\''
                + ", songs=" + songs
                + ", date='" + date + '\''
                + ", price=" + price
                + ", episodes=" + episodes
                + ", nextPage='" + nextPage + '\''
                + '}';
    }
}
