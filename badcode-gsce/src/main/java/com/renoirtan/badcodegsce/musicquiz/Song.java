package com.renoirtan.badcodegsce.musicquiz;

import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.Integer;
import java.lang.System;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * A representation of a song in this quiz. It consists of its name and the
 * name of the artist. Due to the simplicity of this class, it should have
 * no problem being serialised and deserialised to JSON.
 */
public class Song implements Serializable {

    private static Type listOfImported =
        new TypeToken<ArrayList<Song>>() {}.getType();

    /**
     * Get a list of songs from a json file.
     * 
     * @param reader The json file input stream.
     * @return The list of songs.
     * @throws Exception If the json deserialiser could not read the file.
     */
    public static ArrayList<Song> importSongsFromJson(Reader reader)
    throws
        Exception
    {
        return new Gson().fromJson(reader, listOfImported);
    }

    /**
     * The name of the song, cannot be empty.
     */
    protected String name;

    /**
     * The name of the artist, cannot be empty.
     */
    protected String artist;

    /**
     * Default constructor for a song. This creates a `Song` whose name and
     * artist name are both "Unknown".
     */
    public Song() {
        this("Unknown", "Unknown");
    }

    /**
     * Create a `Song` from the 2 provided values.
     * 
     * @param name The name of the song, cannot be empty.
     * @param artist The name of the artist, cannot be empty.
     */
    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) {
            return false;
        } else {
            Song other = (Song) o;
            return this.name == other.name && this.artist == other.artist;
        }
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.name.hashCode() + this.artist.hashCode());
    }

    @Override
    public String toString() {
        return String.format(
            "<Song name=\"%s\" artist=\"%s\"/>",
            this.name,
            this.artist
        );
    }

    /**
     * Convert this song into an English phrase describing the song itself.
     * 
     * @return A simple phrase describing this song.
     */
    public String toEnglish() {
        return this.name + " by " + this.artist;
    }

    /**
     * Check whether the name guessed by a player matches the name of the song.
     * 
     * @param guessedName The name guessed by the player.
     * @return A boolean value determining whether the player has guessed the
     * name of the song correctly.
     */
    public boolean checkGuess(String guessedName) {
        return this.name.equals(guessedName);
    }

    /**
     * Get the name of the song.
     * 
     * @return The name of the song.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the name of the artist of the song.
     * 
     * @return The name of the song's artist.
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * Get the first letter of the name of the song.
     * 
     * @return The first letter of the name of the song.
     * 
     * @throws IndexOutOfBoundsException If the name of the song has length 0.
     */
    public char firstLetterOfName() throws IndexOutOfBoundsException {
        return this.name.charAt(0);
    }

    /**
     * Get the first letter of the name of the artist.
     * 
     * @return The first letter of the name of the artist.
     * @throws IndexOutOfBoundsException If the name of the artist is empty.
     */
    public char firstLetterOfAuthor() throws IndexOutOfBoundsException {
        return this.artist.charAt(0);
    }

    /**
     * Ask the player to guess the name of this song.
     * 
     * @param player The player to ask.
     * 
     * @return Whether the player can continue to be probed.
     * 
     * @throws Exception If something wrong happens.
     */
    public boolean quizPlayerOnce(Player player) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean result = this.quizPlayerOnce(System.out, scanner, player);
        scanner.close();
        return result;
    }

    /**
     * Ask the player to guess the song once. If the player fails to do
     * so, this method will not probe them again, which means you have to
     * invoke this method again.
     * 
     * @param out An output stream.
     * @param scanner A scanner which can read user input.
     * @param player The player that has to guess the song.
     * 
     * @return Whether the player can continued to be questioned.
     * 
     * @throws Exception If something wrong happens.
     */
    public boolean quizPlayerOnce(
        PrintStream out,
        Scanner scanner,
        Player player
    ) throws Exception {
        if (player.chancesLeft() == Player.getAllowedChances()) {
            out.println(String.format(
                "Player: %s\nGuess the name of the song:\n - First letter of name: %c\n - First letter of artist's name: %c",
                player.getUsername(),
                this.firstLetterOfName(),
                this.firstLetterOfAuthor()
            ));
        }
        out.println(String.format("Chances left: %d", player.chancesLeft()));
        out.print("Guess> ");
        String guess = scanner.nextLine().trim();
        if (this.checkGuess(guess)) {
            out.println("You guessed correctly!");
            return player.guess(true);
        } else {
            out.println("Your guess was wrong.");
            return !player.guess(false);
        }
    }

    /**
     * Ask a player to guess the name of this song. However, if they get the
     * name wrong, they will be asked again until they either obtain a correct
     * guess or run out of lives.
     * 
     * @param player The player to be asked to guess the name of this song.
     * 
     * @return Whether the player has run out of lives and therefore the game
     * is over.
     * 
     * @throws Exception If something wrong happens.
     */
    public boolean quizPlayer(Player player) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean result = this.quizPlayer(System.out, scanner, player);
        scanner.close();
        return result;
    }

    /**
     * Ask a player to guess the name of this song. However, if they get the
     * name wrong, they will be asked again until they either obtain a correct
     * guess or run out of lives.
     * 
     * @param out The output stream.
     * @param scanner The input stream.
     * @param player The player to be asked to guess the name of this song.
     * 
     * @return Whether the player has run out of lives and therefore the game
     * is over.
     * 
     * @throws Exception If something wrong happens.
     */
    public boolean quizPlayer(
        PrintStream out,
        Scanner scanner,
        Player player
    ) throws Exception {
        while (this.quizPlayerOnce(out, scanner, player)) {}
        return player.gameOver();
    }
}
