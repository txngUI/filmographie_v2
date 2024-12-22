package db;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.client.model.*;
import models.Actor;
import models.Film;
import models.Star;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBFilms {

    // Private attributes
    private MongoClient mongoClient;
    private MongoCollection<Document> movies;

    /**
     * Constructor
     */
    public MongoDBFilms() {
        super();
    }

    /**
     * Connect to the MongoDB database
     */
    public void connect() {
        try {
            // Connect to the MongoDB server
            this.mongoClient = new MongoClient("localhost", 27017);

            // Get the database
            MongoDatabase db = mongoClient.getDatabase("films");

            // Get the collection
            this.movies = db.getCollection("films");
        } catch (MongoException e) {
            System.out.println("Error while connecting to the MongoDB database");
        }
    }

    /**
     * Close the connection to the MongoDB database
     */
    public void close() {
        // Close the connection
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    /**
     * Get all films from the database corresponding to the filters
     * @param title : the title of the film
     * @param year : the year of the film
     * @param category : the category of the film
     * @return the list of films
     */
    public ArrayList<Film> get_films(String title, String year, String category) {
        // Initialize the list of films
        ArrayList<Film> films = new ArrayList<>();

        try {
            // Create a list of filters
            List<Bson> filters = new ArrayList<>();

            // Add a filter for the title if it is specified
            if (title != null && !title.isEmpty()) {
                filters.add(Filters.regex("title", title, "i")); // Filtrage par titre avec insensibilité à la casse
            }

            // Add a filter for the year if it is specified
            if (year != null && !year.isEmpty()) {
                filters.add(Filters.eq("year", Integer.parseInt(year)));
            }

            // Add a filter for the category if it is specified
            if (category != null && !category.isEmpty()) {
                filters.add(Filters.regex("category", category, "i"));
            }

            // Create the filter
            Bson filter = filters.isEmpty() ? new Document() : Filters.and(filters);
            FindIterable<Document> iterable = movies.find(filter);

            // for each document, create a film with its model and add it to the list
            for (Document doc : iterable) {
                films.add(getFilmModel(doc));
            }

        } catch (Exception e) {
            System.out.println("Error while getting films from the MongoDB database");
        }

        // Return the list of films
        return films;
    }

    /**
     * Get a film by its id
     * @param filmId : the id of the film
     * @return the film
     */
    public Film getFilmById(String filmId) {
        // Get the film by its id in the database
        Document doc = movies.find(eq("_id", new ObjectId(filmId))).first();

        // If the document is not null, return the film model
        if (doc != null) {
            return getFilmModel(doc);
        }

        // Otherwise, return null
        return null;
    }

    /**
     * Add a film to the database
     * @param title : the title of the film
     * @param year : the year of the film
     * @param duration : the duration of the film
     * @param dateAdded : the date when the film was added
     * @param category : the category of the film
     * @param director : the director of the film
     * @param actors : the list of actors of the film
     * @param summary : the summary of the film
     * @param nationality : the nationality of the film
     */
    public void addFilm(String title, int year, int duration, Date dateAdded, String category, Star director,
                        List<Actor> actors, String summary, String nationality) {

        // Create a list of actors documents
        ArrayList<Document> actorsDoc = new ArrayList<>();

        // Add each actor to the list of actors documents
        for (Actor actor : actors) {
            actorsDoc.add(new Document("firstname", actor.getFirstname())
                    .append("lastname", actor.getLastname())
                    .append("type", actor.isMainCaracter() ? "Principal" : "Secondaire"));
        }

        // Create the document
        Document doc = new Document("title", title)
                .append("year", year)
                .append("duration", duration)
                .append("date_added", dateAdded)
                .append("category", category)
                .append("nationality", nationality)
                .append("director", new Document("firstname", director.getFirstname())
                        .append("lastname", director.getLastname()))
                .append("actors", actorsDoc)
                .append("summary", summary);

        // Insert the document in the collection
        movies.insertOne(doc);
    }

    /**
     * Delete a film from the database
     * @param filmId : the id of the film
     */
    public void deleteFilm(String filmId) {
        // Delete the film by its id in the database
        movies.deleteOne(eq("_id", new ObjectId(filmId)));
    }

    /**
     * Update a film in the database
     * @param film : the film to update
     */
    public void updateFilm(Film film) {
        // Create a list of actors documents
        List<Document> actors = new ArrayList<>();

        // Add each actor to the list of actors documents
        film.getActorsList().forEach(actor -> {
            actors.add(new Document("firstname", actor.getFirstname())
                    .append("lastname", actor.getLastname())
                    .append("type", actor.isMainCaracter() ? "Principal" : "Secondaire"));
        });

        // Update the film in the database
        movies.updateOne(
                eq("_id", new ObjectId(film.getId())),
                new Document("$set",
                        new Document("title", film.getTitle())
                                .append("year", film.getYear())
                                .append("duration", film.getDuration())
                                .append("date_added", film.getDateAddedDate())
                                .append("category", film.getCategory())
                                .append("nationality", film.getNationality())
                                .append("director",
                                        new Document("firstname", film.getDirectorStar().getFirstname())
                                                .append("lastname", film.getDirectorStar().getLastname()))
                                .append("actors", actors)
                                .append("summary", film.getResume())
                )
        );
    }

    /**
     * Get the film model from the document
     * @param document : the document
     * @return the film model
     */
    public Film getFilmModel(Document document) {

        // Get the director of the film from the document
        Document directorDoc = (Document) document.get("director");

        // Create the director star
        Star director = null;

        // If the director document is not null, create the director star
        if (directorDoc != null) {
            director = new Star(
                    directorDoc.getString("firstname"),
                    directorDoc.getString("lastname")
            );
        }

        // Create the list of actors
        ArrayList<Actor> actors = new ArrayList<>();

        // Get the actors documents from the document
        List<Document> actorsDocs = (List<Document>) document.get("actors");

        // If the actors documents are not null, create the actors list
        if (actorsDocs != null) {

            // For each actor document, create the actor and add it to the list
            for (Document actorDoc : actorsDocs) {

                // Create the actor
                actors.add(new Actor(
                        actorDoc.getString("firstname"),
                        actorDoc.getString("lastname"),
                        "Principal".equals(actorDoc.getString("type"))
                ));
            }
        }

        // Return the film model
        return (
                new Film(
                        document.getObjectId("_id").toString(),
                        document.getString("title"),
                        document.getInteger("year"),
                        document.getInteger("duration"),
                        document.getDate("date_added"),
                        document.getString("category"),
                        director,
                        actors,
                        document.getString("summary"),
                        document.getString("nationality")
                )
        );
    }
}
