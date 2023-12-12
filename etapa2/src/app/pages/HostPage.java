package app.pages;

import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.extras.Announcement;
import app.user.Host;

import java.util.List;

public class HostPage implements Page {
    private final List<Podcast> podcasts;
    private final List<Announcement> announcements;

    public HostPage(Host host) {
        this.podcasts = host.getPodcasts();
        this.announcements = host.getAnnouncements();
    }

    @Override
    public String printCurrentPage() {
        StringBuilder str = new StringBuilder("Podcasts:\n\t[");

        for (Podcast podcast : podcasts) {
            str.append(podcast.getName()).append(":\n\t[");
            for (Episode episode : podcast.getEpisodes()) {
                str.append(episode.getName()).append(" - ").append(episode.getDescription())
                        .append(", ");
            }
            str.append("], ");
        }
        str.append("]\n\nAnnouncements:\n\t[");

        for (Announcement announcement : announcements) {
            str.append(announcement.getName()).append("\n\t")
                    .append(announcement.getDescription()).append("\n, ");
        }
        str.append("]");
        return str.toString();
    }
}
