package com.renoirtan.badcodegsce.musicquiz;

public class App {
    public static void main(String[] args) {
        Game game = new Game();
        game.getPlayersManager().addPlayer(new Player("me", 1));
        game.getSongsManager().addSong(new Song("songname", "songperson"));
        int songsPassed = 0;
        try {
            songsPassed = game.play();
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        System.out.println(String.format("Songs guessed: %d", songsPassed));
    }
}
