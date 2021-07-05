package com.renoirtan.badcodegsce.musicquiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import static com.renoirtan.badcodegsce.musicquiz.Player.PlayerImportBean;

public class App {
    public static void main(String[] args) {
        String task = "";
        try {
            task = args[0];
        } catch (Exception e) {
            System.out.println("Using default musicquiz subcommand.");
            task = "";
        }
        if (task == null) {
            task = "";
        }
        String[] taskArgs = Arrays.copyOfRange(args, 1, args.length + 1);
        switch (task) {
            case "":
            case "game":
                App.game(taskArgs);
                break;
            case "deserializeSong":
                App.deserializeSong(taskArgs);
                break;
            case "newPlayer":
                App.newPlayer(taskArgs);
                break;
            default:
                System.out.println("Unknown task requested.");
                break;
        }
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

    public static void deserializeSong(String[] args) {
        Gson gson = new Gson();
        String json = "{'name': 'songname', 'artist': 'songartist'}";
        Song song = gson.fromJson(json, Song.class);
        System.out.println(song.toEnglish());
    }

    public static void newPlayer(String[] args) {
        System.out.println("Create a new User.");
        String username;
        String password;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        username = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        scanner.close();
        try {
            PlayerImportBean bean = PlayerImportBean.newUser(
                username,
                password
            );
            System.out.println("You: " + bean.toString());
        } catch (Exception e) {
            System.out.println("Failed to create user.");
            return;
        }
    }
}
