package app.pages;

import app.audio.Collections.Album;
import app.extras.Event;
import app.extras.Merch;
import app.user.Artist;

import javax.print.DocFlavor;
import java.util.List;
import java.util.stream.Collectors;

public class ArtistPage  implements Page {
    private final List<Album> albums;
    private final List<Merch> merches;
    private final List<Event> events;

    public ArtistPage(Artist artist) {
        this.albums = artist.getAlbums();
        this.merches = artist.getMerches();
        this.events = artist.getEvents();
    }


    @Override
    public String printCurrentPage() {
        return "Albums:\n\t["
                + albums.stream().map(Album::getName)
                .collect(Collectors.joining(", "))
                + "]\n\nMerch:\n\t["
                + merches.stream().map(merch ->
                        merch.getName() + " - "
                        + merch.getPrice() + ":\n\t"
                        + merch.getDescription())
                .collect(Collectors.joining(", "))
                + "]\n\nEvents:\n\t["
                + events.stream().map(event -> event.getName() + " - "
                        + event.getDate() + ":\n\t"
                        + event.getDescription())
                .collect(Collectors.joining(", "))
                + "]";
    }
}
