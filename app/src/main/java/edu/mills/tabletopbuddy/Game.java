package edu.mills.tabletopbuddy;


import android.database.sqlite.SQLiteDatabase;

//Game class with fields, getters and setters.
public class Game {

    private String image, name, description, theme;
    private Integer pubdate, minplayers, maxplayers, mintime,
    maxtime, age, FAVE_INT;
    private Double myrating;
    private Double bggrating;
    SQLiteDatabase db;

public Game(String image, String name, String description, String theme,
            Integer pubdate, Integer minplayers, Integer maxplayers,
            Integer mintime, Integer maxtime, Integer age, Integer FAVE_INT,
            Double myrating, Double bggrating) {
    this.image = image;
    this.name = name;
    this.description = description;
    this.theme = theme;
    this.pubdate = pubdate;
    this.minplayers = minplayers;
    this.maxplayers = maxplayers;
    this.mintime = mintime;
    this.maxtime = maxtime;
    this.age = age;
    this.FAVE_INT = FAVE_INT;
    this.myrating = myrating;
    this.bggrating = bggrating;
}

    public Double getBbgrating() {
        return bggrating;
    }

    public Double getMyrating() {
        return myrating;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getFAVE_INT() {
        return FAVE_INT;
    }

    public Integer getMaxplayers() {
        return maxplayers;
    }

    public Integer getMinplayers() {
        return minplayers;
    }

    public Integer getMaxtime() {
        return maxtime;
    }

    public Integer getMintime() {
        return mintime;
    }

    public Integer getPubdate() {
        return pubdate;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getTheme() {
        return theme;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBbgrating(Double bbgrating) {
        this.bggrating = bbgrating;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFAVE_INT(Integer FAVE_INT) {
        this.FAVE_INT = FAVE_INT;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMaxplayers(Integer maxplayers) {
        this.maxplayers = maxplayers;
    }

    public void setMaxtime(Integer maxtime) {
        this.maxtime = maxtime;
    }

    public void setMinplayers(Integer minplayers) {
        this.minplayers = minplayers;
    }

    public void setMintime(Integer mintime) {
        this.mintime = mintime;
    }

    public void setMyrating(Double myrating) {
        this.myrating = myrating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPubdate(Integer pubdate) {
        this.pubdate = pubdate;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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

