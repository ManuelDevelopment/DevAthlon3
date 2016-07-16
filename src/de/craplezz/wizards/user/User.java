package de.craplezz.wizards.user;

import de.craplezz.wizards.Wizards;
import de.craplezz.wizards.kit.KitType;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Overload
 * @version 1.0
 */
public class User {

    private static final Map<Player, User> PLAYER_USER_MAP = new HashMap<>();

    private Player player;
    private Locale locale = Locale.forLanguageTag("de_DE");
    private KitType kitType;

    public User(Player player) {
        this.player = player;

        PLAYER_USER_MAP.put(player, this);
    }

    public void sendMessage(String languageKey, Object... args) {
        player.sendMessage(getMessage(languageKey, args));
    }

    public String getMessage(String languageKey, Object... args) {
        String message = Wizards.getLanguageManager().getMessage(locale, languageKey);

        // Replace prefix
        message = message.replace("%prefix%", Wizards.getLanguageManager().getMessage(locale, "prefix"));

        // Replace args
        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", String.valueOf(args[i]));
        }

        return message;
    }


    public KitType getKitType() {
        return kitType;
    }

    public void changeKit(KitType kitType) {
        this.kitType = kitType;

        kitType.getKit().apply(player);
    }

    public static User getUser(Player player) {
        return PLAYER_USER_MAP.get(player);
    }

    public static void removeUser(Player player) {
        PLAYER_USER_MAP.remove(player);
    }

    public static Collection<User> getUsers() {
        return PLAYER_USER_MAP.values();
    }

}
