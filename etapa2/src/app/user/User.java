package app.user;

import lombok.Getter;

@Getter
public abstract class User {
    private final String username;
    private final int age;
    private final String city;
    private final String type;
    public User(final String username, final int age, final String city, final String type) {
        this.username = username;
        this.age = age;
        this.city = city;
        this.type = type;
    }
}
