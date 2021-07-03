package com.renoirtan.badcodegsce.musicquiz;

import java.io.PrintStream;
import java.lang.Integer;
import java.lang.System;
import java.util.Scanner;

public class Song {
    protected String name;
    protected String artist;

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

    public String toEnglish() {
        return this.name + " by " + this.artist;
    }

    public boolean checkGuess(String guessedName) {
        return this.name.equals(guessedName);
    }

    public char firstLetterOfName() {
        return this.name.charAt(0);
    }

    public char firstLetterOfAuthor() {
        return this.artist.charAt(0);
    }

    public boolean quizPlayerOnce(Player player) throws Exception {
        return this.quizPlayerOnce(System.out, new Scanner(System.in), player);
    }

    public boolean quizPlayerOnce(
        PrintStream out,
        Scanner scanner,
        Player player
    ) throws Exception {
        if (player.chancesLeft() == Player.getAllowedChances()) {
            out.println(String.format(
                "Guess the name of the song:\n - First letter of name: %c\n - First letter of artist's name: %c",
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

    public boolean quizPlayer(Player player) throws Exception {
        return this.quizPlayer(System.out, new Scanner(System.in), player);
    }

    public boolean quizPlayer(
        PrintStream out,
        Scanner scanner,
        Player player
    ) throws Exception {
        while (this.quizPlayerOnce(out, scanner, player)) {}
        return player.gameOver();
    }
}
