package com.renoirtan.badcodegsce.musicquiz;

import java.util.List;

import com.google.gson.Gson;

public class App {
    public static void main(String[] args) {
        App.song(args);
    }

    public static void game(String[] args) {
        Game game = new Game();
        game.getPlayersManager().addPlayers(List.of(
            new Player("me", 1)
        ));
        game.getSongsManager().addSongs(List.of(
            new Song("songname", "songperson"),
            new Song("another song", "ojfoefke")
        ));
        int songsPassed = 0;
        try {
            songsPassed = game.play();
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        System.out.println(String.format("Songs guessed: %d", songsPassed));
    }

    public static void song(String[] args) {
        Gson gson = new Gson();
        String json = "{'name': 'songname', 'artist': 'songartist'}";
        Song song = gson.fromJson(json, Song.class);
        System.out.println(song.toEnglish());
    }
}
