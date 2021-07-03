package com.renoirtan.badcodegsce.musicquiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class SongsManager implements Iterator<Song> {
    protected ArrayList<Song> songs;
    protected Random rng;
    
    protected SongsManager init() {
        return this;
    }

    protected SongsManager reset() {
        this.songs = new ArrayList<>();
        this.rng = null;
        return this;
    }

    public SongsManager() {
        this((Random) null);
    }

    public SongsManager(Random rng) {
        this.songs = new ArrayList<>();
        this.rng = rng;
    }

    public SongsManager(Collection<Song> songs) {
        this(songs, null);
    }

    public SongsManager(Collection<Song> songs, Random rng) {
        this(songs.iterator(), rng);
    }

    public SongsManager(Iterator<Song> songs) {
        this(songs, null);
    }

    public SongsManager(Iterator<Song> songs, Random rng) {
        this.songs = new ArrayList<>();
        this.addSongs(songs);
        this.rng = rng;
    }

    public SongsManager setRng(Random rng) {
        this.rng = rng;
        return this;
    }

    public boolean addSong(Song song) {
        if (this.songs.contains(song)) {
            return false;
        } else {
            this.songs.add(song);
            return true;
        }
    }

    public int addSongs(Collection<Song> songs) {
        return this.addSongs(songs.iterator());
    }

    public int addSongs(Iterator<Song> songs) {
        int added = 0;
        while (songs.hasNext()) {
            this.addSong(songs.next());
            added++;
        }
        return added;
    }

    public int countSongs() {
        return this.songs.size();
    }

    public boolean isEmpty() {
        return this.songs.isEmpty();
    }

    public Song getSong() {
        return this.getSong(this.rng);
    }

    public Song getSong(int index) {
        try {
            return this.songs.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Song getSong(Random rng) {
        if (this.isEmpty()) {
            return null;
        } else if (rng == null) {
            return this.getSong(0);
        } else {
            return this.getSong(rng.nextInt(this.countSongs()));
        }
    }

    public Song popSong() {
        return this.popSong(this.rng);
    }

    public Song popSong(int index) {
        try {
            return this.songs.remove(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Song popSong(Random rng) {
        if (this.isEmpty()) {
            return null;
        } else if (rng == null) {
            return this.popSong(0);
        } else {
            return this.popSong(rng.nextInt(this.countSongs()));
        }
    }

    // Iterator<Song>

    @Override
    public boolean hasNext() {
        return !this.isEmpty();
    }

    @Override
    public Song next() {
        return this.popSong();
    }
}
