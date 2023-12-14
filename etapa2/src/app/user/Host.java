package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.extras.Announcement;
import app.player.Player;
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

    public Podcast getPodcast(String name) {
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return podcast;
            }
        }
        return null;
    }

    public String addPodcast(String name, List<EpisodeInput> episodeInputList) {
        Podcast podcast = getPodcast(name);
        if (podcast != null) {
            return getUsername() + " has another podcast with the same name.";
        }
        List<Episode> episodes = episodeInputList.stream()
                .map(episodeInput -> new Episode(episodeInput.getName(),
                        episodeInput.getDuration(), episodeInput.getDescription()))
                .collect(Collectors.toList());

        if (episodes.stream().map(Episode::getName).distinct().count() != episodes.size()) {
            return getUsername() + " has the same episode in this podcast.";
        }
        podcast = new Podcast(name, getUsername(), episodes);
        podcasts.add(podcast);
        Admin.addPodcast(podcast);
        return getUsername() + " has added new podcast successfully.";
    }

    public String removePodcast(String name) {
        Podcast podcast = getPodcast(name);
        if (podcast == null) {
            return getUsername() + " doesn't have a podcast with the given name.";
        }

        List<Episode> episodes = new ArrayList<>(podcast.getEpisodes());
        List<Player> players = new ArrayList<>();

        for (User user : Admin.getUsers()) {
            if (user.getType().equals("normal")) {
                players.add(((NormalUser) user).getPlayer());
            }
        }
        List<AudioFile> audioFiles = new ArrayList<>();
        List<AudioCollection> audioCollections = new ArrayList<>();

        for (Player player : players) {
            audioFiles.add(player.getCurrentAudioFile());
            audioCollections.add(player.getCurrentAudioCollection());
        }

        if (episodes.stream().anyMatch(audioFiles::contains)
                || audioCollections.contains(podcast)) {
            return getUsername() + " can't delete this podcast.";
        }

        Admin.removePodcast(podcast);
        podcasts.remove(podcast);
        return getUsername() + " deleted the podcast successfully.";
    }

    public Announcement getAnnouncement(String name) {
        return announcements.stream().filter(announcement -> announcement
                .getName().equals(name)).findAny().orElse(null);
    }

    public String addAnnouncement(String name, String description) {
        Announcement announcement = getAnnouncement(name);
        if (announcement != null) {
            return getUsername() + " has already added an announcement with this name.";
        }

        announcements.add(new Announcement(name, description));
        return getUsername() + " has successfully added new announcement.";
    }

    public String removeAnnouncement(String name) {
        Announcement announcement = getAnnouncement(name);
        if (announcement == null) {
            return getUsername() + " has no announcement with the given name.";
        }

        announcements.remove(announcement);
        return getUsername() + " has successfully deleted the announcement.";
    }
}
