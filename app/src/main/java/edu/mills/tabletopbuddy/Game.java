package edu.mills.tabletopbuddy;


import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

//Game class with fields, getters and setters.
public class Game {

    public static ArrayList<Integer> gamesLibrary = new ArrayList<>();

    private String image, name, description, theme, age;
    private int minplayers, maxplayers, bggid, playtime;
    private SQLiteDatabase db;

    public Game(String image, String name, String description, String theme, int bggid,
                int minplayers, int maxplayers, int playtime, String age) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.minplayers = minplayers;
        this.maxplayers = maxplayers;
        this.age = age;
        this.bggid = bggid;
        this.playtime = playtime;

        gamesLibrary.add(bggid);
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public String getTheme() {
        return theme;
    }

    public int getBggId() {
        return bggid;
    }

    public int getMaxplayers() {
        return maxplayers;
    }

    public int getMinplayers() {
        return minplayers;
    }

    public int getPlaytime() {
        return playtime;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public void setBggid(int bggid) {
        this.bggid = bggid;
    }

    public void setMinplayers(int minplayers) {
        this.minplayers = minplayers;
    }

    public void setMaxplayers(int maxplayers) {
        this.maxplayers = maxplayers;
    }

    public void setPlaytime(int playTime) {
        this.playtime = playTime;
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

    //need to implement comparators

    /*  SQLiteDatabase db, String image, String name, String description, String theme, Integer pubdate,
    Double myrating, Double bggrating, Integer minplayers, Integer maxplayers,
    Integer mintime, Integer maxtime, Integer age, Integer FAVE_INIT)*/

}

