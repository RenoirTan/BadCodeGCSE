package com.renoirtan.badcodegsce.musicquiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * A class that helps to oversee the adding and removing of Songs from the
 * queue. It can use a random number generator using {@link Random} to
 * select which song the player should guess next which can be stored
 * in an object of this class.
 */
public class SongsManager implements Iterator<Song> {
    protected ArrayList<Song> songs;
    protected Random rng;
    
    /**
     * Initialise the current {@link SongsManager} object with predetermined
     * values.
     * 
     * @return The current SongsManager object.
     */
    private SongsManager init() {
        return this;
    }

    /**
     * Reset the current manager object by clearing all the songs and removing
     * the random number generator.
     * 
     * @return Return the current SongsManager object.
     */
    protected SongsManager reset() {
        this.songs = new ArrayList<>();
        this.rng = null;
        return this;
    }

    /**
     * The default SongsManager constructor. This means that no random number
     * generator is used and the list of songs is empty.
     */
    public SongsManager() {
        this((Random) null);
    }

    /**
     * Create a SongsManager with no songs but with a random number generator.
     * @param rng The random number generator to use.
     */
    public SongsManager(Random rng) {
        this.songs = new ArrayList<>();
        this.rng = rng;
        this.init();
    }

    /**
     * Create a SongsManager from a list of songs.
     * @param songs A collection of songs.
     */
    public SongsManager(Collection<Song> songs) {
        this(songs, null);
    }

    /**
     * Create a SongsManager from a list with a random number generator
     * provided.
     * 
     * @param songs The list of songs.
     * @param rng The random number generator.
     */
    public SongsManager(Collection<Song> songs, Random rng) {
        this(songs.iterator(), rng);
    }

    /**
     * Create a SongsManager from an iterator. This can be used with database
     * cursors if they implement {@link Iterator}.
     * 
     * @param songs An iterator over songs.
     */
    public SongsManager(Iterator<Song> songs) {
        this(songs, null);
    }

    /**
     * Create a SongsManager from an iterator. This can be used with database
     * cursors if they implement {@link Iterator}. In addition, you
     * can provide a random number generator for this object.
     * 
     * @param songs An iterator over songs.
     * @param rng The random number generator.
     */
    public SongsManager(Iterator<Song> songs, Random rng) {
        this.songs = new ArrayList<>();
        this.addSongs(songs);
        this.rng = rng;
        this.init();
    }

    /**
     * Set/reset the random number generator.
     * 
     * @param rng The new random number generator to use. This parameter
     * accepts null references too.
     * @return This SongsManager object.
     */
    public SongsManager setRng(Random rng) {
        this.rng = rng;
        return this;
    }

    /**
     * Add a song to the SongsManager. If the song is already in the queue,
     * the new song will not be added to prevent duplicates.
     * 
     * @param song The new song.
     * @return true if the song was added, false if the song could not be
     * added (because of duplicates etc.)
     */
    public boolean addSong(Song song) {
        if (this.songs.contains(song)) {
            return false;
        } else {
            this.songs.add(song);
            return true;
        }
    }

    /**
     * Add a bunch of songs from a list.
     * 
     * @param songs The collection of songs.
     * @return How many songs were successfully added to the manager.
     */
    public int addSongs(Collection<Song> songs) {
        return this.addSongs(songs.iterator());
    }

    /**
     * Add songs from an iterator.
     * 
     * @param songs The iterator of songs.
     * @return How many songs were successfully added to the manager.
     */
    public int addSongs(Iterator<Song> songs) {
        int added = 0;
        while (songs.hasNext()) {
            if (this.addSong(songs.next())) {
                added++;
            }
        }
        return added;
    }

    /**
     * Count the number of songs in the manager.
     * 
     * @return The number of songs stored.
     */
    public int countSongs() {
        return this.songs.size();
    }

    /**
     * Check if the are any songs in the SongsManager.
     * 
     * @return true if SongsManager.countSongs is 0.
     */
    public boolean isEmpty() {
        return this.songs.isEmpty();
    }

    /**
     * Get a random song from the manager.
     * 
     * @return The random song.
     */
    public Song getSong() {
        return this.getSong(this.rng);
    }

    /**
     * Get a song at a specified index. If the index given is out of bounds,
     * null is returned instead.
     * 
     * @param index The index of the song.
     * @return The song.
     */
    public Song getSong(int index) {
        try {
            return this.songs.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Get a song using a specified random number generator. If no song can
     * be found, null is returned instead.
     * 
     * @param rng The new random number generator to use.
     * @return The song chosen by the RNG.
     */
    public Song getSong(Random rng) {
        if (this.isEmpty()) {
            return null;
        } else if (rng == null) {
            return this.getSong(0);
        } else {
            return this.getSong(rng.nextInt(this.countSongs()));
        }
    }

    /**
     * Remove and return a random song using the RNG in this SongsManager.
     * 
     * @return A random song.
     */
    public Song popSong() {
        return this.popSong(this.rng);
    }

    /**
     * Remove and return the song at a specified index. If the index is out
     * of bounds, null is returned instead.
     * 
     * @param index The index of the song.
     * @return The song at the index.
     */
    public Song popSong(int index) {
        try {
            return this.songs.remove(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Remove and return a random song using a specified random number
     * generator. If no song can be found, null is returned.
     * 
     * @param rng The new random number generator.
     * @return The song.
     */
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
