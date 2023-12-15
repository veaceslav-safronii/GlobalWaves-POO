package app.pages;

import app.audio.Collections.Podcast;
import app.extras.Announcement;
import app.user.Host;

import java.util.List;
import java.util.stream.Collectors;

public class HostPage implements Page {
    private final List<Podcast> podcasts;
    private final List<Announcement> announcements;

    public HostPage(final Host host) {
        this.podcasts = host.getPodcasts();
        this.announcements = host.getAnnouncements();
    }

    /**
     * Prints host page
     * @return page content
     */
    @Override
    public String printCurrentPage() {
        return "Podcasts:\n\t["
                + podcasts.stream().map(podcast ->
                        podcast.getName() + ":\n\t["
                        + podcast.getEpisodes().stream().map(episode ->
                                episode.getName() + " - "
                                + episode.getDescription())
                        .collect(Collectors.joining(", "))
                        + "]\n")
                .collect(Collectors.joining(", "))
                + "]\n\nAnnouncements:\n\t["
                + announcements.stream().map(announcement ->
                        announcement.getName() + ":\n\t"
                        + announcement.getDescription())
                .collect(Collectors.joining(", "))
                + "\n]";
    }
}
