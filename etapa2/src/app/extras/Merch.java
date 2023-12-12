package app.extras;

import lombok.Getter;

@Getter
public class Merch {
    private final String name;
    private final String description;
    private final Integer price;

    public Merch(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
