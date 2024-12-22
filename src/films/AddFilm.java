package films;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db.MongoDBFilms;
import models.Film;
import models.Star;

public class AddFilm extends JFrame {

    static private final String[] roles = {"principal", "secondaire"};
    private final JTextField jtfTitle = new JTextField();
    private final JTextField jtfYear = new JTextField();
    private final JTextField jtfType = new JTextField();
    private final JTextField jtfNationality = new JTextField();
    private final JTextField jtfDirector_firstname = new JTextField();
    private final JTextField jtfDirector_lastname = new JTextField();
    private final JComboBox<String> jcDirector_role = new JComboBox<>(AddFilm.roles);
    private final JTextField jtfActor1_firstname = new JTextField();
    private final JTextField jtfActor1_lastname = new JTextField();
    private final JComboBox<String> jcActor1_role = new JComboBox<>(AddFilm.roles);
    private final JTextField jtfActor2_firstname = new JTextField();
    private final JTextField jtfActor2_lastname = new JTextField();
    private final JComboBox<String> jcActor2_role = new JComboBox<>(AddFilm.roles);
    private final JTextField jtfActor3_firstname = new JTextField();
    private final JTextField jtfActor3_lastname = new JTextField();
    private final JComboBox<String> jcActor3_role = new JComboBox<>(AddFilm.roles);
    private final JTextArea jtaResume = new JTextArea();
    private final JLabel jlError = new JLabel("");
    private final MongoDBFilms dbfilms;
    private final JTextField jtfDuration = new JTextField();

    /**
     * Constructor
     */
    public AddFilm() {
        // Connexion BDD
        dbfilms = new MongoDBFilms();

        // Init UI
        this.initUI();
    }

    /**
     * Initialize the UI
     */
    public void initUI() {

        this.setTitle("Nouveau film");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        Font police = new Font("Arial", Font.BOLD, 14);
        jtfTitle.setFont(police);
        jtfTitle.setPreferredSize(new Dimension(300, 30));
        jtfTitle.setForeground(Color.BLUE);
        jtfYear.setFont(police);
        jtfYear.setPreferredSize(new Dimension(300, 30));
        jtfYear.setForeground(Color.BLUE);
        jtfType.setFont(police);
        jtfType.setPreferredSize(new Dimension(300, 30));
        jtfType.setForeground(Color.BLUE);
        jtfNationality.setFont(police);
        jtfNationality.setPreferredSize(new Dimension(300, 30));
        jtfNationality.setForeground(Color.BLUE);

        jtfDirector_firstname.setFont(police);
        jtfDirector_firstname.setPreferredSize(new Dimension(300, 30));
        jtfDirector_firstname.setForeground(Color.BLUE);
        jtfDirector_lastname.setFont(police);
        jtfDirector_lastname.setPreferredSize(new Dimension(300, 30));
        jtfDirector_lastname.setForeground(Color.BLUE);
        jcDirector_role.setFont(police);
        jcDirector_role.setPreferredSize(new Dimension(200, 30));
        jcDirector_role.setForeground(Color.BLUE);

        jtfActor1_firstname.setFont(police);
        jtfActor1_firstname.setPreferredSize(new Dimension(300, 30));
        jtfActor1_firstname.setForeground(Color.BLUE);
        jtfActor1_lastname.setFont(police);
        jtfActor1_lastname.setPreferredSize(new Dimension(300, 30));
        jtfActor1_lastname.setForeground(Color.BLUE);
        jcActor1_role.setFont(police);
        jcActor1_role.setPreferredSize(new Dimension(200, 30));
        jcActor1_role.setForeground(Color.BLUE);

        jtfActor2_firstname.setFont(police);
        jtfActor2_firstname.setPreferredSize(new Dimension(300, 30));
        jtfActor2_firstname.setForeground(Color.BLUE);
        jtfActor2_lastname.setFont(police);
        jtfActor2_lastname.setPreferredSize(new Dimension(300, 30));
        jtfActor2_lastname.setForeground(Color.BLUE);
        jcActor2_role.setFont(police);
        jcActor2_role.setPreferredSize(new Dimension(200, 30));
        jcActor2_role.setForeground(Color.BLUE);

        jtfActor3_firstname.setFont(police);
        jtfActor3_firstname.setPreferredSize(new Dimension(300, 30));
        jtfActor3_firstname.setForeground(Color.BLUE);
        jtfActor3_lastname.setFont(police);
        jtfActor3_lastname.setPreferredSize(new Dimension(300, 30));
        jtfActor3_lastname.setForeground(Color.BLUE);
        jcActor3_role.setFont(police);
        jcActor3_role.setPreferredSize(new Dimension(200, 30));
        jcActor3_role.setForeground(Color.BLUE);

        // Generate the layout
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 5, 0, 5);

        // Grid position
        int gridy = 0;

        // Title field
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(new JLabel("Titre : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        c.gridwidth = 3;
        pane.add(jtfTitle, c);

        // Year field
        gridy++;
        c.gridx = 0;
        c.gridy = gridy;
        c.gridwidth = 1;
        pane.add(new JLabel("Année : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(jtfYear, c);

        jtfDuration.setFont(police);
        jtfDuration.setPreferredSize(new Dimension(300, 30));
        jtfDuration.setForeground(Color.BLUE);

        // Duration field
        gridy++;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(new JLabel("Durée (minutes) : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(jtfDuration, c);

        // Category field
        gridy++;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(new JLabel("Genre : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(jtfType, c);

        // Nationality field
        gridy++;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(new JLabel("Nationalité : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(jtfNationality, c);

        // Director field - Firstname
        gridy++;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(new JLabel("Réalisateur : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(jtfDirector_firstname, c);

        // Director field - Lastname
        c.gridx = 2;
        pane.add(jtfDirector_lastname, c);

        // List of actors (max 3)

        // Actor 1 - Firstname
        gridy++;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(new JLabel("Acteur 1 : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(jtfActor1_firstname, c);

        // Actor 1 - Lastname
        c.gridx = 2;
        pane.add(jtfActor1_lastname, c);

        // Actor 1 - Role
        c.gridx = 3;
        pane.add(jcActor1_role, c);

        // Actor 2 - Firstname
        gridy++;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(new JLabel("Acteur 2 : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(jtfActor2_firstname, c);

        // Actor 2 - Lastname
        c.gridx = 2;
        pane.add(jtfActor2_lastname, c);

        // Actor 2 - Role
        c.gridx = 3;
        pane.add(jcActor2_role, c);

        // Actor 3 - Firstname
        gridy++;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(new JLabel("Acteur 3 : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        pane.add(jtfActor3_firstname, c);

        // Actor 3 - Lastname
        c.gridx = 2;
        pane.add(jtfActor3_lastname, c);

        // Actor 3 - Role
        c.gridx = 3;
        pane.add(jcActor3_role, c);

        gridy++;
        c.gridx = 0;
        c.gridy = gridy;
        c.gridwidth = 4;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        jlError.setForeground(Color.RED);
        jlError.setFont(new Font("Arial", Font.BOLD, 12));
        pane.add(jlError, c);

        jlError.setPreferredSize(new Dimension(400, 30));
        jlError.setText("");

        // Add a JTextArea for the summary
        jtaResume.setFont(police);
        jtaResume.setPreferredSize(new Dimension(300, 100));
        jtaResume.setForeground(Color.BLUE);
        jtaResume.setLineWrap(true);
        jtaResume.setWrapStyleWord(true);

        // Add a JScrollPane to the JTextArea
        JScrollPane jtaResumeScrollPane = new JScrollPane(jtaResume);
        jtaResumeScrollPane.setPreferredSize(new Dimension(300, 100));
        jtaResumeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add the JTextArea to the pane
        gridy++;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(new JLabel("Résumé : ", JLabel.TRAILING), c);
        c.gridx = 1;
        c.gridy = gridy;
        c.gridwidth = 3; // 3 columns wide
        pane.add(jtaResumeScrollPane, c);

        // Add a button to insert the film
        gridy++;
        JButton bOK = new JButton("Insérer");
        bOK.addActionListener(new BoutonListener());
        c.insets = new Insets(10, 5, 10, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 0.0;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = gridy;
        pane.add(bOK, c);
        this.getRootPane().setDefaultButton(bOK);

        // Display the window
        this.pack();
        this.setVisible(true);

    }

    /**
     * ActionListener for the button
     */
    class BoutonListener implements ActionListener {

        /**
         * Method called when the button is clicked
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {

            // Check if all necessaries fields are filled
            if (jtfTitle.getText().isEmpty() || jtfYear.getText().isEmpty() || jtfType.getText().isEmpty() ||
            jtfNationality.getText().isEmpty() || jtfDirector_firstname.getText().isEmpty() ||
            jtfDirector_lastname.getText().isEmpty()) {

                jlError.setText("Tous les champs sont obligatoires");
                return;

            }

            // Connect to the database
            dbfilms.connect();

            // Insert the film
            dbfilms.addFilm(
                    jtfTitle.getText(),
                    Integer.parseInt(jtfYear.getText()),
                    Integer.parseInt(jtfDuration.getText()),
                    new Date(),
                    jtfType.getText(),
                    new Star(jtfDirector_firstname.getText(), jtfDirector_lastname.getText()),
                    new ArrayList<models.Actor>(Arrays.asList(
                            new models.Actor(jtfActor1_firstname.getText(), jtfActor1_lastname.getText(), jcActor1_role.getSelectedItem().equals("principal")),
                            new models.Actor(jtfActor2_firstname.getText(), jtfActor2_lastname.getText(), jcActor2_role.getSelectedItem().equals("principal")),
                            new models.Actor(jtfActor3_firstname.getText(), jtfActor3_lastname.getText(), jcActor3_role.getSelectedItem().equals("principal"))
                    )),
                    jtaResume.getText(),
                    jtfNationality.getText()
            );

            // Close the connection
            dbfilms.close();

            // Close the window
            AddFilm.this.dispose();
        }
    }
}
