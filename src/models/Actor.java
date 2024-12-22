package models;

public class Actor extends Star {
    // Private attributes
    private Boolean mainCaracter;

    // Constructor
    public Actor(String firstname, String lastname, Boolean mainCaracter) {
        super(firstname, lastname);
        this.mainCaracter = mainCaracter;
    }

    // Getters and Setters
    public Boolean isMainCaracter() {
        return mainCaracter;
    }
    public void setMainCaracter(Boolean mainCaracter) {
        this.mainCaracter = mainCaracter;
    }
}
