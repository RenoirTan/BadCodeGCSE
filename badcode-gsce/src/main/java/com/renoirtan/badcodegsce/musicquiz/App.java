package com.renoirtan.badcodegsce.musicquiz;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class App {
    public static void main(String[] args) {
        App.game(args);
    }

    private static ArrayList<Player> createSomePlayers(int number) {
        ArrayList<Player> players = new ArrayList<>();
        for (int player = 1; player <= number; player++) {
            players.add(new Player(
                String.format("Player %d", player),
                player
            ));
        }
        return players;
    }

    public static void game(String[] args) {
        Game game = new Game();
        game.getPlayersManager().addPlayers(App.createSomePlayers(3));
        game.getSongsManager().addSongs(List.of(
            new Song("DMCA", "City Animals"),
            new Song("Oldwater", "20 Years of Winter"),
            new Song("Forwards", "no idea really on how to modify this guy's name frick"),
            new Song("Laughing outside the moon", "Ball")
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
