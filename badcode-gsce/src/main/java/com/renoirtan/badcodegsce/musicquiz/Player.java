package com.renoirtan.badcodegsce.musicquiz;

import java.lang.Integer;

/**
 * A class representing a player.
 */
public class Player {
    protected String username;
    protected int authId;
    protected int score;
    protected int previousIncorrect;
    protected int currentIncorrect;

    protected static int allowedChances = 2;

    public static int getAllowedChances() {
        return Player.allowedChances;
    }

    private Player init() {
        this.score = 0;
        this.previousIncorrect = 0;
        this.currentIncorrect = 0;
        return this;
    }

    public Player() {
        this("Unknown", 0);
    }

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

    public int getScore() {
        return this.score;
    }

    public int getTotalIncorrect() {
        return this.currentIncorrect + this.previousIncorrect;
    }

    protected Player flushCurrentIncorrect() {
        this.previousIncorrect += this.currentIncorrect;
        this.currentIncorrect = 0;
        return this;
    }

    protected boolean gameOver() {
        return this.currentIncorrect >= 2;
    }

    protected int chancesLeft() {
        return 2 - this.currentIncorrect;
    }

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
                break;
        }
        return this;
    }

    /**
     * Tell the player object of the result of their guess (`true` if correct
     * and `false` if incorrect) and this method will return whether the player
     * has reached a "Game Over" state.
     * 
     * @param correct
     * @return
     * @throws Exception
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
