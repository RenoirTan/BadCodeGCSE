package com.renoirtan.badcodegsce.musicquiz;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import static com.renoirtan.badcodegsce.musicquiz.Player.PlayerImportBean;

public class App {
    public static void main(String[] args) {
        String task = args[0];
        if (task == null) {
            System.out.println("Using default task.");
            task = "";
        }
        String[] taskArgs = Arrays.copyOfRange(args, 1, args.length + 1);
        switch (task) {
            case "":
            case "game":
                App.game(taskArgs);
                break;
            case "debugGame":
                App.debugGame(taskArgs);
                break;
            case "deserializeSong":
                App.deserializeSong(taskArgs);
                break;
            case "newPlayer":
                App.newPlayer(taskArgs);
                break;
            case "readPlayersJson":
                try {
                    App.readPlayersJson(taskArgs);
                } catch (Exception e) {
                    System.err.println(e);
                }
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
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Path to JSON file of players: ");
            String playersFilePath = scanner.nextLine();
            FileReader jsonFile = new FileReader(playersFilePath);
            ArrayList<Player> players = Player.importPlayersFromJson(jsonFile);

            System.out.print("Path to JSON file of songs: ");
            String songsFilePath = scanner.nextLine();
            jsonFile = new FileReader(songsFilePath);
            ArrayList<Song> songs = Song.importSongsFromJson(jsonFile);

            Game game = new Game();
            game.getPlayersManager().addPlayers(players);
            game.getSongsManager().addSongs(songs);
            int songsPassed = game.play();
            System.out.println(String.format("Songs guessed: %d", songsPassed));
        } catch (final Exception e) {
            System.out.println(e);
        } finally {
            scanner.close();
        }
    }

    public static void _game(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Path to JSON file of players: ");
        String playersFilePath = scanner.nextLine();
        ArrayList<Player> players = null;
        try {
            FileReader jsonFile = new FileReader(playersFilePath);
            players = Player.importPlayersFromJson(jsonFile);
        } catch (Exception e) {
            System.err.println(e);
            scanner.close();
            return;
        }
        System.out.print("Path to JSON file of songs: ");
        String songsFilePath = scanner.nextLine();
        ArrayList<Song> songs = null;
        try {
            FileReader jsonFile = new FileReader(songsFilePath);
            songs = Song.importSongsFromJson(jsonFile);
        } catch (Exception e) {
            System.err.println(e);
            scanner.close();
            return;
        }
        Game game = new Game();
        game.getPlayersManager().addPlayers(players);
        game.getSongsManager().addSongs(songs);
        int songsPassed = 0;
        try {
            songsPassed = game.play();
        } catch (Exception e) {
            System.err.println(e);
            scanner.close();
            return;
        }
        System.out.println(String.format("Songs guessed: %d", songsPassed));
        scanner.close();
    }

    public static void debugGame(String[] args) {
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
            System.out.println(new Gson().toJson(bean));
        } catch (Exception e) {
            System.out.println("Failed to create user.");
            return;
        }
    }

    public static void readPlayersJson(String[] args) throws Exception {
        String filePath = args[0];
        Scanner scanner = new Scanner(System.in);
        if (filePath == null) {
            System.out.print("Path to players.json: ");
            filePath = scanner.nextLine();
        }
        FileReader jsonFile = new FileReader(filePath);
        ArrayList<Player> players = Player.importPlayersFromJson(jsonFile);
        System.out.println(players);
        scanner.close();
    }
}
