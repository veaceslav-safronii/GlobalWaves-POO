package app.extras;

import lombok.Getter;

@Getter
public class Merch {
    private final String name;
    private final String description;
    private final Integer price;

    public Merch(final String name, final String description, final Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
