package app;

import app.audio.Collections.AlbumOutput;
import app.audio.Collections.PodcastOutput;
import app.user.Artist;
import app.user.Host;
import app.user.NormalUser;
import app.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.List;

public final class CommandRunner2 {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner2() {
    }

    /**
     * Gets top 5 artists.
     *
     * @param commandInput the command input
     * @return the top 5 artists
     */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        List<String> artists = Admin.getTop5Artists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(artists));

        return objectNode;
    }

    /**
     * Gets top 5 albums.
     *
     * @param commandInput the command input
     * @return the top 5 albums
     */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        List<String> albums = Admin.getTop5Albums();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    /**
     * Switches connection status
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user != null) {
            if (user.getType().equals("normal")) {
                message = ((NormalUser) user).switchConnectionStatus();
            } else {
                message = user.getUsername() + " is not a normal user.";
            }
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Gets online users
     *
     * @param commandInput the command input
     * @return online users
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<String> onlineUsers = Admin.getOnlineUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }

    /**
     * Adds a user to the app
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        String message = Admin.addUser(commandInput.getUsername(), commandInput.getAge(),
                commandInput.getCity(), commandInput.getType());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Deletes a user from the app
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
        String message = Admin.deleteUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds an album
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user != null) {
            if (user.getType().equals("artist")) {
                message = ((Artist) user).addAlbum(commandInput.getName(),
                        commandInput.getReleaseYear(), commandInput.getDescription(),
                        commandInput.getSongs());
            } else {
                message = user.getUsername() + " is not an artist.";
            }
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Removes an album
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user != null) {
            if (user.getType().equals("artist")) {
                message = ((Artist) user).removeAlbum(commandInput.getName());
            } else {
                message = user.getUsername() + " is not an artist.";
            }
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Shows an artist's albums
     *
     * @param commandInput the command input
     * @return the albums
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        Artist artist = (Artist) Admin.getUser(commandInput.getUsername());
        List<AlbumOutput> albums = Admin.showAlbums(artist);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    /**
     *  Shows a host's podcasts
     *
     * @param commandInput the command input
     * @return the podcasts
     */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        Host host = (Host) Admin.getUser(commandInput.getUsername());
        List<PodcastOutput> podcasts = Admin.showPodcasts(host);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(podcasts));

        return objectNode;
    }

    /**
     * Prints the current page of a user
     *
     * @param commandInput the command input
     * @return the current page
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        NormalUser user = (NormalUser) Admin.getUser(commandInput.getUsername());
        String message = user.printCurrentPage();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a merch of an artist
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user != null) {
            if (user.getType().equals("artist")) {
                message = ((Artist) user).addMerch(commandInput.getName(),
                        commandInput.getDescription(), commandInput.getPrice());
            } else {
                message = user.getUsername() + " is not an artist.";
            }
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds an event of the artist
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user != null) {
            if (user.getType().equals("artist")) {
                message = ((Artist) user).addEvent(commandInput.getName(),
                        commandInput.getDescription(), commandInput.getDate());
            } else {
                message = user.getUsername() + " is not an artist.";
            }
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Removes an event
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user != null) {
            if (user.getType().equals("artist")) {
                message = ((Artist) user).removeEvent(commandInput.getName());
            } else {
                message = user.getUsername() + " is not an artist.";
            }
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a podcast into the app
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user != null) {
            if (user.getType().equals("host")) {
                message = ((Host) user).addPodcast(commandInput.getName(),
                        commandInput.getEpisodes());
            } else {
                message = user.getUsername() + " is not a host.";
            }
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Removes a podcast from the app
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user != null) {
            if (user.getType().equals("host")) {
                message = ((Host) user).removePodcast(commandInput.getName());
            } else {
                message = user.getUsername() + " is not a host.";
            }
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds an announcement
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user != null) {
            if (user.getType().equals("host")) {
                message = ((Host) user).addAnnouncement(commandInput.getName(),
                        commandInput.getDescription());
            } else {
                message = user.getUsername() + " is not a host.";
            }
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Removes an announcement
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user != null) {
            if (user.getType().equals("host")) {
                message = ((Host) user).removeAnnouncement(commandInput.getName());
            } else {
                message = user.getUsername() + " is not a host.";
            }
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Gets all users from the app
     *
     * @param commandInput the command input
     * @return the users
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        List<String> users = Admin.getAllUsers();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(users));

        return objectNode;
    }

    /**
     * Changes the current user's page
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode changePage(final CommandInput commandInput) {
        NormalUser user = (NormalUser) Admin.getUser(commandInput.getUsername());
        String message = user.changePage(commandInput.getNextPage());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
}
