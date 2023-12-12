package app.pages;

import app.Admin;
import app.user.Artist;
import app.user.Host;
import app.user.NormalUser;

public class PageFactory {
    public static Page getPage(NormalUser user) {
        return switch (user.getCurrentPageType()) {
            case "homepage" -> new HomePage(user.getLikedSongs(), user.getFollowedPlaylists());
            case "likedcontentpage" -> new LikedContentPage(user.getLikedSongs(),
                    user.getFollowedPlaylists());
            case "artistpage" -> new ArtistPage((Artist) Admin.getUser(user.getSearchBar()
                    .getLastSelected().getName()));
            case "hostpage" -> new HostPage((Host) Admin.getUser(user.getSearchBar()
                    .getLastSelected().getName()));
            default -> throw new IllegalStateException("Unexpected value: " + user.getCurrentPageType());
        };
    }
}
