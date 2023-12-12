package app.extras;

import lombok.Getter;

@Getter
public class Announcement {
    private final String name;
    private final String description;

    public Announcement(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
