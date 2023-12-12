package app.extras;


import lombok.Getter;

@Getter
public class Event {
    private final String name;
    private final String description;
    private final String date;

    public Event(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }
}
