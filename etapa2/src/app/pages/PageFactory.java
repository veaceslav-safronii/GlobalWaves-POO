package app.pages;

import app.Admin;
import app.user.Artist;
import app.user.Host;
import app.user.NormalUser;

public final class PageFactory {

    private PageFactory() {

    }

    /**
     * Creates an instance of a specific type of page
     * @param user the user
     * @return page instance
     */
    public static Page getPage(final NormalUser user) {
        return switch (user.getCurrentPageType()) {
            case "Home" -> new HomePage(user.getLikedSongs(), user.getFollowedPlaylists());
            case "LikedContent" ->
                    new LikedContentPage(user.getLikedSongs(),
                            user.getFollowedPlaylists());
            case "Artist" ->
                    new ArtistPage((Artist) Admin.getUser(user.getCurrentPage().getName()));
            case "Host" ->
                    new HostPage((Host) Admin.getUser(user.getCurrentPage().getName()));
            default ->
                    throw new IllegalStateException("Unexpected value: "
                            + user.getCurrentPageType());
        };
    }
}
