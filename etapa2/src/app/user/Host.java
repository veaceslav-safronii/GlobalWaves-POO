package app.user;

import app.audio.Collections.Podcast;
import app.extras.Announcement;
import lombok.Getter;

import java.util.List;

@Getter
public class Host extends User {
    List<Podcast> podcasts;
    List <Announcement> announcements;
    public Host(String username, int age, String city) {
        super(username, age, city, "host");
    }
}
