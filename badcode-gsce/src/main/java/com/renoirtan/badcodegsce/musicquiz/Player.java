package com.renoirtan.badcodegsce.musicquiz;

import java.io.Reader;
import java.lang.Integer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.renoirtan.badcodegsce.authentification.Hasher;

/**
 * A class representing a player.
 */
public class Player {

    public static class PlayerImportBean {
        public static PlayerImportBean newUser(
            String username,
            String password
        ) throws Exception {
            int authId = Hasher.hashUsernameAndPassword(username, password);
            return new PlayerImportBean(username, authId);
        }

        private String username;
        private int authId;

        public PlayerImportBean() {
            this("Unknown", 0);
        }

        public PlayerImportBean(String username, int authId) {
            this.username = username;
            this.authId = authId;
        }

        @Override
        public String toString() {
            return String.format(
                "<PlayerImportBean username=\"%s\" authId=\"%d\"/>",
                this.getUsername(),
                this.getAuthId()
            );
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getAuthId() {
            return this.authId;
        }

        public void setAuthId(int authId) {
            this.authId = authId;
        }
    }

    public static class PlayerExportBean {
        private String username;
        private int authId;
        private int score;
        private int incorrect;

        public PlayerExportBean() {
            this("Unknown", 0, 0, 0);
        }

        public PlayerExportBean(
            String username,
            int authId,
            int score,
            int incorrect
        ) {
            this.username = username;
            this.authId = authId;
            this.score = score;
            this.incorrect = incorrect;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getAuthId() {
            return this.authId;
        }

        public void setAuthId(int authId) {
            this.authId = authId;
        }

        public int getScore() {
            return this.score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getIncorrect() {
            return this.incorrect;
        }

        public void setIncorrect(int incorrect) {
            this.incorrect = incorrect;
        }
    }

    /**
     * <https://stackoverflow.com/questions/5554217/google-gson-deserialize-listclass-object-generic-type#5554296>
     */
    private static final Type listOfImported =
        new TypeToken<ArrayList<PlayerImportBean>>() {}.getType();

    /**
     * Get a list of {@link PlayerImportBean}s from a json file.
     * 
     * @param reader An input file stream.
     * @return The list of Player beans.
     * @throws Exception If the Json deserialiser is unable to read the file
     * provided.
     */
    public static ArrayList<PlayerImportBean> importBeansFromJson(
        Reader reader
    ) throws Exception {
        return new Gson().fromJson(reader, listOfImported);
    }

    /**
     * Convert an iterator of player beans into a list of players.
     * 
     * @param beans The iterator of beans.
     * @return The list of players mapped from the iterator of beans.
     */
    public static ArrayList<Player> playersFromBeans(
        Iterator<PlayerImportBean> beans
    ) {
        ArrayList<Player> players = new ArrayList<>();
        beans.forEachRemaining(bean -> players.add(new Player(bean)));
        return players;
    }

    /**
     * Convert a json file into a list of players.
     * 
     * @param reader A input file stream containing the json object.
     * @return The list of players.
     * @throws Exception If the deserialiser is unable to read the json file.
     */
    public static ArrayList<Player> importPlayersFromJson(
        Reader reader
    ) throws Exception {
        return Player.playersFromBeans(
            Player.importBeansFromJson(reader).iterator()
        );
    }

    /**
     * The name of the player.
     */
    protected String username;

    /**
     * The ID of the player. This can be used as a unique identifier for a
     * player.
     */
    protected int authId;

    /**
     * The score of the player has accumulated.
     */
    protected int score;

    /**
     * How many guesses the player has gotten incorrect previously.
     */
    protected int previousIncorrect;

    /**
     * How many wrong guesses the player has currently made when guessing
     * the current song.
     */
    protected int currentIncorrect;

    /**
     * How many chances a player has before a wrong guess induces a game
     * termination event.
     */
    protected static int allowedChances = 2;

    /**
     * How many chances a player has before a wrong guess induces a game termination
     * event.
     * 
     * @return The maximum number of chances a player has.
     */
    public static int getAllowedChances() {
        return Player.allowedChances;
    }

    /**
     * Initialise the player's data.
     * 
     * @return The current player.
     */
    private Player init() {
        this.score = 0;
        this.previousIncorrect = 0;
        this.currentIncorrect = 0;
        return this;
    }

    /**
     * The default constructor for {@link Player}.
     * This will set the name of the player to "Unknown"
     * and their ID to 0.
     */
    public Player() {
        this("Unknown", 0);
    }

    /**
     * The main constructor {@link Player}.
     * 
     * @param username The username of the player.
     * @param authId The identity of the player.
     */
    public Player(String username, int authId) {
        this.username = username;
        this.authId = authId;
        this.init();
    }

    public Player(PlayerImportBean bean) {
        this.username = bean.getUsername();
        this.authId = bean.getAuthId();
    }

    /**
     * Convert the data in this player into a JavaBean.
     * 
     * @return A player export bean.
     */
    public PlayerExportBean export() {
        return new PlayerExportBean(
            this.getUsername(),
            this.getAuthId(),
            this.getScore(),
            this.getTotalIncorrect()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this.getClass() != other.getClass()) {
            return false;
        } else {
            return this.authId == ((Player) other).authId;
        }
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(authId);
    }

    @Override
    public String toString() {
        return String.format(
            "<Player username=\"%s\" authId\"%s\"/>",
            this.username,
            this.authId
        );
    }

    /**
     * Get the username of the player.
     * 
     * @return The username of the player.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the ID of the player.
     * 
     * @return The ID of the player.
     */
    public int getAuthId() {
        return this.authId;
    }

    /**
     * Get the player's current score.
     * 
     * @return The player's current score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Get the number of incorrect guesses the player has made.
     * 
     * @return The number of incorrect guesses.
     */
    public int getTotalIncorrect() {
        return this.currentIncorrect + this.previousIncorrect;
    }

    /**
     * Add the count of current incorrect guesses to the number of previous
     * incorrect guess and reset the number of incorrect guesses to 0.
     * 
     * @return This player object.
     */
    protected Player flushCurrentIncorrect() {
        this.previousIncorrect += this.currentIncorrect;
        this.currentIncorrect = 0;
        return this;
    }

    /**
     * Check whether the current player has been eliminated and is out of
     * the game.
     * 
     * @return True or false
     */
    protected boolean gameOver() {
        return this.currentIncorrect >= 2;
    }

    /**
     * Get how many chances the user has remaining.
     * 
     * @return Number of remaining chances.
     */
    protected int chancesLeft() {
        return Player.getAllowedChances() - this.currentIncorrect;
    }

    /**
     * Check if the password provided is correct for this user.
     * 
     * @param password The password.
     * @return Whether the password is correct.
     * @throws Exception If the hasher could not hash the username and/or
     * password.
     */
    public boolean authenticate(String password) throws Exception {
        return this.getAuthId() == Hasher.hashUsernameAndPassword(
            this.getUsername(),
            password
        );
    }

    /**
     * Tell the player that they have guessed the song correctly,
     * allowing the game to increase the number of points the player has
     * (if eligible).
     * 
     * @return The current player.
     * 
     * @throws Exception If the number of incorrect guesses is negative.
     */
    protected Player guessCorrectly() throws Exception {
        if (this.currentIncorrect < 0) {
            throw new Exception(
                "Player.currentIncorrect cannot be less than 0."
            );
        }
        switch (this.currentIncorrect) {
            case 0:
                this.score += 3;
                break;
            case 1:
                this.score += 1;
                break;
            default:
                return this;
        }
        return this.flushCurrentIncorrect();
    }

    /**
     * Tell the player object of the result of their guess (`true` if correct
     * and `false` if incorrect) and this method will return whether the player
     * has reached a "Game Over" state.
     * 
     * @param correct Whether the guess was correct.
     * @return Whether the player should be eliminated from the game.
     * @throws Exception If something went wrong.
     */
    public boolean guess(boolean correct) throws Exception {
        if (correct) {
            this.guessCorrectly();
        } else {
            this.currentIncorrect += 1;
        }
        return this.gameOver();
    }
}
