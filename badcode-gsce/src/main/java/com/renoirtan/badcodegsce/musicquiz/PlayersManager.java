package com.renoirtan.badcodegsce.musicquiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A class that watches and cycles over the players in a game. It stores an
 * array of player objects and the index of the player currently trying to
 * guess the song.
 */
public class PlayersManager implements Iterator<Player> {
    protected ArrayList<Player> players;
    protected int index;
    protected int turns;

    /**
     * Initialises the PlayersManager object.
     * 
     * @return The PlayersManager object.
     */
    private PlayersManager init() {
        this.index = 0;
        this.turns = 0;
        return this;
    }

    /**
     * The default constructor for PlayersManager.
     */
    public PlayersManager() {
        this.players = new ArrayList<>();
        this.init();
    }

    /**
     * Construct a manager using a predefined list.
     * 
     * @param players The list of players.
     */
    public PlayersManager(Collection<Player> players) {
        this(players.iterator());
    }

    /**
     * Construct a manager from an iterator of players.
     * 
     * @param players The iterator of players.
     */
    public PlayersManager(Iterator<Player> players) {
        this.players = new ArrayList<>();
        while (players.hasNext()) {
            this.players.add(players.next());
        }
        this.init();
    }

    /**
     * The number of players in the game.
     * 
     * @return The number of players in the game.
     */
    public int playerCount() {
        return this.players.size();
    }

    /**
     * Check to see if there are no players in the game.
     * 
     * @return true if there are no players in the game.
     */
    public boolean isEmpty() {
        return this.players.isEmpty();
    }

    /**
     * The number of turns that have already passed.
     * 
     * @return The number of turns done.
     */
    public int turnsPassed() {
        return this.turns;
    }

    /**
     * Add a player to the game.
     * 
     * @param player The new player.
     * @return Whether the player could be added.
     */
    public boolean addPlayer(Player player) {
        if (this.players.contains(player)) {
            return false;
        } else {
            this.players.add(player);
            return true;
        }
    }

    /**
     * Add players from a list of players.
     * 
     * @param players The list of players.
     * @return How many players could be added.
     */
    public int addPlayers(Collection<Player> players) {
        return this.addPlayers(players.iterator());
    }

    /**
     * Add players from an iterator of players.
     * 
     * @param players The iterator of players.
     * @return How many players could be added.
     */
    public int addPlayers(Iterator<Player> players) {
        int added = 0;
        while (players.hasNext()) {
            if (this.addPlayer(players.next())) {
                added++;
            }
        }
        return added;
    }

    /**
     * Remove a player by their index in the list and return them. If the index
     * is out of bounds, null is returned.
     * 
     * @param index The index of the player.
     * @return The player that used to be at that index.
     */
    public Player removePlayer(int index) {
        try {
            return this.players.remove(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Remove the last player.
     * 
     * @return The last player.
     */
    public Player removeLastPlayer() {
        if (this.playerCount() == 1) {
            this.index = 0;
            return this.removePlayer(0);
        }
        this.index--;
        return this.removePlayer(this.wrapIndex().getCurrentIndex());
    }

    /**
     * Remove the player the cursor (index) is currently pointing at.
     * If there are no more players left, null is returned.
     * 
     * @return The current player.
     */
    public Player removeCurrentPlayer() {
        int currentIndex = this.wrapIndex().getCurrentIndex();
        this.index -= 1;
        return this.removePlayer(currentIndex);
    }

    /**
     * The index of the current player.
     * 
     * @return The index of the current player.
     */
    public int getCurrentIndex() {
        return this.index;
    }

    /**
     * Wrap the index arround if it exceeds the maximum indexable length.
     * 
     * @return The current PlayersManager object.
     */
    public PlayersManager wrapIndex() {
        if (this.index >= this.playerCount()) {
            this.index -= this.playerCount();
        }
        return this;
    }

    /**
     * Get the index of the next player.
     * 
     * @return The index of the next player.
     * 
     * @throws Exception If there is nobody playing.
     */
    public int nextIndex() throws Exception {
        if (this.isEmpty()) {
            throw new Exception("There are no players.");
        }
        int currIndex = this.index++;
        this.wrapIndex();
        return currIndex;
    }

    /**
     * Give up the list of players and remove the pointer to the list of
     * players in this object to prevent data races.
     * 
     * @return The list of players.
     */
    protected ArrayList<Player> absolve() {
        ArrayList<Player> temp = this.players;
        this.players = new ArrayList<>();
        this.init();
        return temp;
    }

    // Iterator<Player>

    @Override
    public boolean hasNext() {
        return !this.isEmpty();
    }

    @Override
    public Player next() {
        if (this.isEmpty()) {
            return null;
        } else {
            try {
                return this.players.get(this.nextIndex());
            } catch (Exception e) {
                return null;
            }
        }
    }
}
