package com.renoirtan.badcodegsce.musicquiz;

import java.lang.Integer;

/**
 * A class representing a player.
 */
public class Player {
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
            "com.renoirtan.badcodegsce.musicquiz.Player < username = \"%s\" >",
            this.username
        );
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
