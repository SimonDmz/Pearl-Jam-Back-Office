package fr.insee.pearljam.api.configuration.auth.user;

public class User {

    private final String stamp;
    private final String name;

    public String getStamp() {
        return this.stamp;
    }

    public String getName() {
        return this.name;
    }

    public User(String stamp, String name) {
        this.stamp = stamp;
        this.name = name;
    }

    public User() {
        this("default", "Guest");
    }
}
