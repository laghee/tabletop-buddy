/**
 * Implementation of the Tabletop Buddy application. Created for Mills
 * CS 115: Mobile Application Development, Spring 2017.
 *
 * @author Kristen Cutler, Jennifer Diaz, Arianne Agogino Gieringer,
 * Kate Manning, Erin Walter
 */
package edu.mills.tabletopbuddy;

import android.database.sqlite.SQLiteDatabase;

/**
 * Gets and sets the board game properties to be called by in {@link LibraryDBUtilities}.
 */
public class Game {

    private String image, name, description, theme, age;
    private int minplayers, maxplayers, bggid, playtime;
    private SQLiteDatabase db;

    /**
     * Constructs a board game.
     *
     * @param image the game image
     * @param name the game name
     * @param description the game description
     * @param theme the game theme
     * @param age the game's suggested age group
     * @param minplayers the minimum players
     * @param maxplayers the maximum players
     * @param bggid the bggid
     * @param playtime the max playtime
     */
    public Game(String image, String name, String description, String theme, int bggid,
                int minplayers, int maxplayers, int playtime, String age) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.bggid = bggid;
        this.minplayers = minplayers;
        this.maxplayers = maxplayers;
        this.playtime = playtime;
        this.age = age;

    }

    /**
     * Gets the image.
     * @return image of the game
     */
    public String getImage() {
        return image;
    }

    /**
     * Gets the name, otherwise known as title.
     * @return name the name of the game
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the suggested minimum age of players.
     * @return age
     */
    public String getAge() {
        return age;
    }

    /**
     * Gets the description.
     * @return the game description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the theme.
     * @return the game theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Gets the bggid.
     * @return the bggid
     */
    public int getBggId() {
        return bggid;
    }

    /**
     * Gets the suggested maximum players.
     * @return maxplayers the maximum players
     */
    public int getMaxplayers() {
        return maxplayers;
    }

    /**
     * Gets the suggested minimum players.
     * @return minplayers the minimum players
     */
    public int getMinplayers() {
        return minplayers;
    }

    /**
     * Gets the estimated playtime.
     * @return playtime
     */
    public int getPlaytime() {
        return playtime;
    }

    /**
     * Gets the database.
     * @return db the game's database
     */
    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * Sets the age.
     * @param age the age of a game's players
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Sets the name.
     * @param name the name of the game
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the image.
     * @param image the game image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Sets the description.
     * @param description the game description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the theme.
     * @param theme the game theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Sets the database.
     * @param db the SQLite database
     */
    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Sets the id.
     * @param bggid the bgg id
     */
    public void setBggid(int bggid) {
        this.bggid = bggid;
    }

    /**
     * Sets the min players.
     * @param minplayers the minimum players
     */
    public void setMinplayers(int minplayers) {
        this.minplayers = minplayers;
    }

    /**
     * Sets the max players.
     * @param maxplayers the maximum players
     */
    public void setMaxplayers(int maxplayers) {
        this.maxplayers = maxplayers;
    }

    /**
     * Sets the play time.
     * @param playtime the playtime
     */
    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}

