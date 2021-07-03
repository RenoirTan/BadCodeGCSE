package com.renoirtan.badcodegsce.musicquiz;

import java.util.Random;

public class Game {
    protected Random rng;
    protected int gameId;
    protected PlayersManager playersManager;
    protected SongsManager songsManager;
    protected boolean gameOver;

    public Game() {
        this.rng = new Random();
        this.gameId = this.rng.nextInt();
        this.playersManager = new PlayersManager();
        this.songsManager = new SongsManager(this.rng);
    }

    public int getGameId() {
        return this.gameId;
    }

    public PlayersManager getPlayersManager() {
        return this.playersManager;
    }

    public SongsManager getSongsManager() {
        return this.songsManager;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public boolean playOnce() throws Exception {
        Player player = this.playersManager.next();
        if (player == null) {
            throw new NullPointerException(
                "Received a null reference when getting the next player. This might be because there are no players in the game."
            );
        }
        Song song = this.songsManager.next();
        if (song == null) {
            return true;
        }
        if (song.quizPlayer(player)) {
            System.out.println(
                player.username + " messed up at " + song.toEnglish()
            );
            this.gameOver = true;
        } else {
            System.out.println(player.username + " guessed correctly.");
        }
        return this.isGameOver();
    }

    public int play() throws Exception {
        int songs = 0;
        while (!this.playOnce()) {
            songs++;
        }
        return songs;
    }
}
