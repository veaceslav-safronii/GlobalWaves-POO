package app.pages;

import app.audio.Collections.Album;
import app.extras.Event;
import app.extras.Merch;
import app.user.Artist;

import javax.print.DocFlavor;
import java.util.List;

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
        StringBuilder str = new StringBuilder("Albums:\n\t[");

        for(Album album : albums) {
            str.append(album.getName()).append(", ");
        }
        str.append("]\n\nMerch:\n\t[");

        for(Merch merch : merches) {
            str.append(merch.getName()).append(" - ").append(merch.getPrice()).append(":\n\t")
                    .append(merch.getDescription()).append(", ");
        }
        str.append("]\n\nEvent:\n\t[");

        for(Event event : events) {
            str.append(event.getName()).append(" - ").append(event.getDate())
                    .append(":\n\t").append(event.getDescription()).append(", ");
        }
        str.append("]");
        return str.toString();
    }
}
