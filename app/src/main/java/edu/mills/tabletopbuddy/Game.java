package edu.mills.tabletopbuddy;


import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

/**
 * Game class with fields to be accessed.
 */
public class Game {

    public static ArrayList<Integer> gamesLibrary;

    private String image, name, description, theme, age;
    private int minplayers, maxplayers, bggid, playtime;
    private SQLiteDatabase db;

    public Game(String image, String name, String description, String theme, String age,
                int minplayers, int maxplayers,
                int bggid, int playtime) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.minplayers = minplayers;
        this.maxplayers = maxplayers;
        this.age = age;
        this.bggid = bggid;
        this.playtime = playtime;

        gamesLibrary.add(this.bggid);
    }

    /**
     * Returns the game's image.
     * @return image of the game
     */
    public String getImage() {
        return image;
    }

    /**
     * Returns the game's name otherwise known as title.
     * @return name the name of the game
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the suggested minimum age of players.
     * @return age
     */
    public String getAge() {
        return age;
    }

    /**
     * Returns the description of the game.
     * @return the game description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the theme of the game.
     * @return the game theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Returns the bggid of the game.
     * @return the bggid
     */
    public int getBggId() {
        return bggid;
    }

    /**
     * Returns the suggested maximum players of the game.
     * @return maxplayers the maximum players
     */
    public int getMaxplayers() {
        return maxplayers;
    }

    /**
     * Returns the suggested minimum players of the game.
     * @return minplayers the minimum players
     */
    public int getMinplayers() {
        return minplayers;
    }

    /**
     * Returns the estimated playtime for the game.
     * @return playtime
     */
    public int getPlaytime() {
        return playtime;
    }

    /**
     * Returns the game database.
     * @return db the game's database
     */
    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * Sets the age of the game.
     * @param age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Sets the name of the game.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the image of the game.
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Sets the description of the game.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the theme of the game.
     * @param theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Sets the game database.
     * @param db
     */
    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Sets the id of the game.
     * @param bggid
     */
    public void setBggid(int bggid) {
        this.bggid = bggid;
    }

    /**
     * Sets the min players.
     * @param minplayers
     */
    public void setMinplayers(int minplayers) {
        this.minplayers = minplayers;
    }

    /**
     * Sets the max players.
     * @param maxplayers
     */
    public void setMaxplayers(int maxplayers) {
        this.maxplayers = maxplayers;
    }

    /**
     * Sets the play time.
     * @param playtime
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

