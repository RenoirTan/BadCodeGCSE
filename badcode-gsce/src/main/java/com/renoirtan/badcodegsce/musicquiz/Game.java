package com.renoirtan.badcodegsce.musicquiz;

import java.util.Random;

/**
 * A game of `musicquiz`.
 */
public class Game {
    /**
     * A random number generator.
     */
    protected Random rng;

    /**
     * The ID of the current game.
     */
    protected int gameId;

    /**
     * A manager controlling and overseeing all of the players in the game.
     */
    protected PlayersManager playersManager;

    /**
     * An object in charge of managing the songs used in the game.
     */
    protected SongsManager songsManager;

    /**
     * A value telling the game whether it has ended.
     */
    protected boolean gameOver;

    /**
     * A constructor for a new game.
     */
    public Game() {
        this.rng = new Random();
        this.gameId = this.rng.nextInt();
        this.playersManager = new PlayersManager();
        this.songsManager = new SongsManager(this.rng);
    }

    /**
     * Get the current game Id.
     * 
     * @return The game Id.
     */
    public int getGameId() {
        return this.gameId;
    }

    /**
     * Get a reference to the PlayersManager object. From there, you can add
     * players to the game.
     * 
     * @return The PlayersManager managing the players in this game.
     */
    public PlayersManager getPlayersManager() {
        return this.playersManager;
    }

    /**
     * Get a reference to the SongsManager object which you can use to add
     * songs.
     * 
     * @return The SongsManager managing the songs in this game.
     */
    public SongsManager getSongsManager() {
        return this.songsManager;
    }

    /**
     * Check if the game is over.
     * 
     * @return true if the game has (or should have) ended.
     */
    public boolean isGameOver() {
        return this.gameOver ||
            this.getSongsManager().isEmpty() ||
            this.getPlayersManager().isEmpty();
    }

    /**
     * Play the game using one song (and therefore one player).
     * 
     * @return true if game over.
     * @throws Exception If something wrong happens, read the error messages.
     */
    public boolean playOnce() throws Exception {
        Player player = this.playersManager.next();
        if (player == null) {
            return true;
        }
        Song song = this.songsManager.next();
        if (song == null) {
            return true;
        }
        if (song.quizPlayer(player)) {
            System.out.println(String.format(
                "%s messed up at %s and has been eliminated.",
                player.getUsername(),
                song.toEnglish()
            ));
            this.getPlayersManager().removeCurrentPlayer();
        } else {
            System.out.println("...");
        }
        System.out.println(String.format(
            "[Game.playOnce] is_game_over? %b",
            this.isGameOver()
        ));
        System.out.println(String.format(
            "[Game.playOnce] no_more_songs? %b",
            this.getSongsManager().isEmpty()
        ));
        System.out.println(String.format(
            "[Game.playOnce] no_more_players %b",
            this.getPlayersManager().isEmpty()
        ));
        return this.isGameOver();
    }

    /**
     * Play the entire game until all players and/or songs have been exhausted
     * from the manager objects.
     * 
     * @return The number of songs guessed correctly.
     * @throws Exception If something went wrong.
     */
    public int play() throws Exception {
        int songs = 0;
        while (!this.playOnce()) {
            songs++;
        }
        return songs;
    }
}
