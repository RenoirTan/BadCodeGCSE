package com.renoirtan.badcodegsce.musicquiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PlayersManager implements Iterator<Player> {
    protected ArrayList<Player> players;
    protected int index;
    protected int turns;

    private PlayersManager init() {
        this.index = 0;
        this.turns = 0;
        return this;
    }

    public PlayersManager() {
        this.players = new ArrayList<>();
        this.init();
    }

    public PlayersManager(Collection<Player> players) {
        this(players.iterator());
    }

    public PlayersManager(Iterator<Player> players) {
        this.players = new ArrayList<>();
        while (players.hasNext()) {
            this.players.add(players.next());
        }
        this.init();
    }

    public int playerCount() {
        return this.players.size();
    }

    public boolean isEmpty() {
        return this.players.isEmpty();
    }

    public int turnsPassed() {
        return this.turns;
    }

    public boolean addPlayer(Player player) {
        if (this.players.contains(player)) {
            return false;
        } else {
            this.players.add(player);
            return true;
        }
    }

    public int addPlayers(Collection<Player> players) {
        return this.addPlayers(players.iterator());
    }

    public int addPlayers(Iterator<Player> players) {
        int added = 0;
        while (players.hasNext()) {
            this.addPlayer(players.next());
            added++;
        }
        return added;
    }

    public int nextIndex() throws Exception {
        if (this.isEmpty()) {
            throw new Exception("There are no players.");
        }
        int currIndex = this.index;
        this.index++;
        this.index %= this.playerCount();
        return currIndex;
    }

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
