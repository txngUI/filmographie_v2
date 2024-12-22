package models;

import java.util.ArrayList;
import java.util.Date;

public class Film {
    private String id;
    private String title;
    private int year;
    private int duration;
    private Date date_added;
    private String category;
    private Star director;
    private ArrayList<Actor> actors;
    private String resume;
    private String nationality;

    public Film(String id, String title, int year, int duration, Date date_added, String category, Star director, ArrayList<Actor> actors, String resume, String nationality) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.duration = duration;
        this.date_added = date_added;
        this.category = category;
        this.director = director;
        this.actors = actors;
        this.resume = resume;
        this.nationality = nationality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDateAdded() {
        String day = "";
        String month = "";

        if (date_added.getDate() < 10) {
            day = "0" + date_added.getDate();
        } else {
            day = "" + date_added.getDate();
        }

        if (date_added.getMonth() < 10) {
            month = "0" + (date_added.getMonth() + 1);
        } else {
            month = "" + (date_added.getMonth() + 1);
        }

        return day + "/" + month + "/" + (date_added.getYear() + 1900);
    }

    public Date getDateAddedDate() {
        return date_added;
    }

    public void setDateAdded(Date date_added) {
        this.date_added = date_added;
    }

    public String getCategory() {
        return category;
    }

    public void setCategories(String category) {
        this.category = category;
    }

    public String getDirector() {
        return director.toString();
    }

    public Star getDirectorStar() {
        return director;
    }

    public void setDirector(Star director) {
        this.director = director;
    }

    public String getActors() {
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (Actor actor : actors) {
            result.append(actor);
            if (count < actors.size() - 1) {
                result.append(", ");
            }
            count++;
        }
        return result.toString();
    }

    public ArrayList<Actor> getActorsList() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
