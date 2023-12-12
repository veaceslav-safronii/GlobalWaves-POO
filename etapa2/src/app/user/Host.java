package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.extras.Announcement;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Host extends User {
    List<Podcast> podcasts;
    List<Announcement> announcements;
    public Host(String username, int age, String city) {
        super(username, age, city, "host");
        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();
    }

    public String addPodcast(String name, List<EpisodeInput> episodeInputList) {
        if (podcasts.stream().anyMatch(podcast -> podcast.getName().equals(name))) {
            return getUsername() + " has another podcast with the same name.";
        }
        List<Episode> episodes = episodeInputList.stream()
                .map(episodeInput -> new Episode(episodeInput.getName(),
                        episodeInput.getDuration(), episodeInput.getDescription()))
                .collect(Collectors.toList());

        if (episodes.stream().map(Episode::getName).distinct().count() != episodes.size()) {
            return getUsername() + " has the same episode in this podcast.";
        }

        podcasts.add(new Podcast(name, getUsername(), episodes));
        Admin.addPodcasts(podcasts);
        return getUsername() + " has added new podcast successfully.";
    }

    public String addAnnouncement(String name, String description) {
        if (announcements.stream().anyMatch(announcement -> announcement.getName().equals(name))) {
            return getUsername() + " has already added an announcement with this name.";
        }

        announcements.add(new Announcement(name, description));
        return getUsername() + " has successfully added new announcement.";
    }
}
