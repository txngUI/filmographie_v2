package films;

import db.MongoDBFilms;
import models.Actor;
import models.Film;
import models.Star;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class EditFilm extends JFrame {
    private final Film selectedFilm;
    private final MongoDBFilms dbfilms;

    private final JTextField titleField = new JTextField();
    private final JTextField yearField = new JTextField();
    private final JTextField durationField = new JTextField();
    private final JTextField categoryField = new JTextField();
    private final JTextField nationalityField = new JTextField();
    private final JTextField directorFirstNameField = new JTextField();
    private final JTextField directorLastNameField = new JTextField();
    private final JTextField actorOneFirstNameField = new JTextField();
    private final JTextField actorOneLastNameField = new JTextField();
    private final JTextField actorTwoFirstNameField = new JTextField();
    private final JTextField actorTwoLastNameField = new JTextField();
    private final JTextField actorThreeFirstNameField = new JTextField();
    private final JTextField actorThreeLastNameField = new JTextField();
    private final JTextArea summaryArea = new JTextArea();
    private final JLabel jlError = new JLabel("");

    /**
     * Constructor
     * @param film Film to update
     */
    public EditFilm(Film film) {
        // Set the selected film
        this.selectedFilm = film;

        // Connect to the database
        dbfilms = new MongoDBFilms();
        dbfilms.connect();

        // Initialize the UI
        this.initUI();
        fillForm();
    }

    /**
     * Initialize the UI
     */
    public void initUI() {
        this.setTitle("ModificAtion du film" + this.selectedFilm.getTitle());
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        Font police = new Font("Arial", Font.BOLD, 14);

        // Add the title
        titleField.setFont(police);
        titleField.setPreferredSize(new Dimension(300, 30));
        titleField.setForeground(Color.BLUE);

        // Add the year
        yearField.setFont(police);
        yearField.setPreferredSize(new Dimension(300, 30));
        yearField.setForeground(Color.BLUE);

        // Add the duration
        durationField.setFont(police);
        durationField.setPreferredSize(new Dimension(300, 30));
        durationField.setForeground(Color.BLUE);

        // Add the category
        categoryField.setFont(police);
        categoryField.setPreferredSize(new Dimension(300, 30));
        categoryField.setForeground(Color.BLUE);

        // Add the nationality
        nationalityField.setFont(police);
        nationalityField.setPreferredSize(new Dimension(300, 30));
        nationalityField.setForeground(Color.BLUE);

        // Add the director
        directorFirstNameField.setFont(police);
        directorFirstNameField.setPreferredSize(new Dimension(300, 30));
        directorFirstNameField.setForeground(Color.BLUE);
        directorLastNameField.setFont(police);
        directorLastNameField.setPreferredSize(new Dimension(300, 30));
        directorLastNameField.setForeground(Color.BLUE);

        // Add the actors
        actorOneFirstNameField.setFont(police);
        actorOneFirstNameField.setPreferredSize(new Dimension(300, 30));
        actorOneFirstNameField.setForeground(Color.BLUE);
        actorOneLastNameField.setFont(police);
        actorOneLastNameField.setPreferredSize(new Dimension(300, 30));
        actorOneLastNameField.setForeground(Color.BLUE);
        actorTwoFirstNameField.setFont(police);
        actorTwoFirstNameField.setPreferredSize(new Dimension(300, 30));
        actorTwoFirstNameField.setForeground(Color.BLUE);
        actorTwoLastNameField.setFont(police);
        actorTwoLastNameField.setPreferredSize(new Dimension(300, 30));
        actorTwoLastNameField.setForeground(Color.BLUE);
        actorThreeFirstNameField.setFont(police);
        actorThreeFirstNameField.setPreferredSize(new Dimension(300, 30));
        actorThreeFirstNameField.setForeground(Color.BLUE);
        actorThreeLastNameField.setFont(police);
        actorThreeLastNameField.setPreferredSize(new Dimension(300, 30));
        actorThreeLastNameField.setForeground(Color.BLUE);

        // Add the summary
        summaryArea.setFont(police);
        summaryArea.setPreferredSize(new Dimension(300, 30));
        summaryArea.setForeground(Color.BLUE);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);

        // Generate the panel
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 5, 0, 5);
        int gridy = 0;

        // Add the title
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(new JLabel("Titre : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        c.gridwidth = 3;
        pane.add(titleField, c);

        // Add the year
        c.gridx = 0;
        c.gridy = ++gridy;
        c.gridwidth = 1;
        pane.add(new JLabel("Année : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(yearField, c);

        // Add the duration
        c.gridx = 0;
        c.gridy = ++gridy;
        pane.add(new JLabel("Durée : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(durationField, c);

        // Add the category
        c.gridx = 0;
        c.gridy = ++gridy;
        pane.add(new JLabel("Catégorie : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(categoryField, c);

        // Add the nationality
        c.gridx = 0;
        c.gridy = ++gridy;
        pane.add(new JLabel("Nationalité : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(nationalityField, c);

        // Add the director
        c.gridx = 0;
        c.gridy = ++gridy;
        pane.add(new JLabel("Réalisateur : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(directorFirstNameField, c);
        c.gridx = 2;
        c.gridy = gridy;
        pane.add(directorLastNameField, c);

        // Add the actors
        c.gridx = 0;
        c.gridy = ++gridy;
        pane.add(new JLabel("Acteur 1 : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(actorOneFirstNameField, c);
        c.gridx = 2;
        c.gridy = gridy;
        pane.add(actorOneLastNameField, c);

        c.gridx = 0;
        c.gridy = ++gridy;
        pane.add(new JLabel("Acteur 2 : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(actorTwoFirstNameField, c);
        c.gridx = 2;
        c.gridy = gridy;
        pane.add(actorTwoLastNameField, c);

        c.gridx = 0;
        c.gridy = ++gridy;
        pane.add(new JLabel("Acteur 3 : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(actorThreeFirstNameField, c);
        c.gridx = 2;
        c.gridy = gridy;
        pane.add(actorThreeLastNameField, c);

        // Add the summary
        JScrollPane jtaResumeScrollPane = new JScrollPane(summaryArea);
        jtaResumeScrollPane.setPreferredSize(new Dimension(300, 100));
        jtaResumeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        c.gridx = 0;
        c.gridy = ++gridy;
        pane.add(new JLabel("Résumé : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        c.gridwidth = 3;
        pane.add(jtaResumeScrollPane, c);

        // Add the error message
        c.gridx = 0;
        c.gridy = ++gridy;
        c.gridwidth = 4;
        pane.add(jlError, c);

        // Add the button
        gridy++;
        JButton bOK = new JButton("Mettre à jour");
        bOK.addActionListener(new EditFilm.BoutonListener());
        c.insets = new Insets(10, 5, 10, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 0.0;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(bOK, c);
        this.getRootPane().setDefaultButton(bOK);

        // Affiche l'application
        this.pack();
        this.setVisible(true);
    }

    /**
     * Fill the form with the selected film
     */
    public void fillForm() {
        titleField.setText(selectedFilm.getTitle());
        yearField.setText(String.valueOf(selectedFilm.getYear()));
        durationField.setText(String.valueOf(selectedFilm.getDuration()));
        categoryField.setText(selectedFilm.getCategory());
        nationalityField.setText(selectedFilm.getNationality());
        directorFirstNameField.setText(selectedFilm.getDirectorStar().getFirstname());
        directorLastNameField.setText(selectedFilm.getDirectorStar().getLastname());
        actorOneFirstNameField.setText(selectedFilm.getActorsList().get(0).getFirstname());
        actorOneLastNameField.setText(selectedFilm.getActorsList().get(0).getLastname());
        actorTwoFirstNameField.setText(selectedFilm.getActorsList().get(1).getFirstname());
        actorTwoLastNameField.setText(selectedFilm.getActorsList().get(1).getLastname());
        actorThreeFirstNameField.setText(selectedFilm.getActorsList().get(2).getFirstname());
        actorThreeLastNameField.setText(selectedFilm.getActorsList().get(2).getLastname());
        summaryArea.setText(selectedFilm.getResume());
    }

    /**
     * Listener for the button
     */
    class BoutonListener implements ActionListener {

        // Listener for the button
        public void actionPerformed(ActionEvent e) {

            // Create the updated film
            Film updatedFilm = new Film(
                    selectedFilm.getId(),
                    titleField.getText(),
                    Integer.parseInt(yearField.getText()),
                    Integer.parseInt(durationField.getText()),
                    new Date(),
                    categoryField.getText(),
                    new Star(directorFirstNameField.getText(), directorLastNameField.getText()),
                    new ArrayList<Actor>(Arrays.asList(
                            new Actor(actorOneFirstNameField.getText(), actorOneLastNameField.getText(), true),
                            new Actor(actorTwoFirstNameField.getText(), actorTwoLastNameField.getText(), false),
                            new Actor(actorThreeFirstNameField.getText(), actorThreeLastNameField.getText(), false)
                    )),
                    summaryArea.getText(),
                    nationalityField.getText()
            );

            // Update the film
            dbfilms.updateFilm(updatedFilm);

            // Close the window
            dispose();
        }
    }
}
