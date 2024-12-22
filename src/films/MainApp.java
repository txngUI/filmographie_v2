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
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import models.Film;

import db.MongoDBFilms;

public class MainApp extends JFrame implements ActionListener {

    private final JMenuBar menuBar = new JMenuBar();
    private final JTextField jtfTitle = new JTextField();
    private final JTextField jtfYear = new JTextField();
    private final JTextField jtfType = new JTextField();
    private final JTextArea resume = new JTextArea();
    private JTable tableFilms;
    private final MongoDBFilms dbfilms;
    private String selectedFilmID;

    /**
     * Constructor
     */
    public MainApp() {

        // UI initialization
        this.initUI();

        // Database connection
        this.dbfilms = new MongoDBFilms();
        this.dbfilms.connect();
    }

    /**
     * Initialize the UI
     */
    public void initUI() {

        // Window settings
        this.setTitle("Filmographie");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Menu
        JMenu menu;
        JMenuItem menuItem;
        menu = new JMenu("Menu");
        this.menuBar.add(menu);

        menuItem = new JMenuItem("Ajout d'un film", KeyEvent.VK_F);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                ActionEvent.ALT_MASK));
        menuItem.setName("ADD");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Modification d'un film", KeyEvent.VK_F);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                ActionEvent.ALT_MASK));
        menuItem.setName("EDIT");
        menuItem.addActionListener(this);
        menu.add(menuItem);


        menuItem = new JMenuItem("Suppression film", KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                ActionEvent.ALT_MASK));
        menuItem.setName("DEL");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Statistiques", KeyEvent.VK_F);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                ActionEvent.ALT_MASK));
        menuItem.setName("STAT");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu.addSeparator();
        menuItem = new JMenuItem("Quitter", KeyEvent.VK_F4);
        menuItem.setName("QUIT");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        this.setJMenuBar(this.menuBar);

        // Form fields
        Font police = new Font("Arial", Font.BOLD, 14);
        jtfTitle.setFont(police);
        jtfTitle.setPreferredSize(new Dimension(800, 30));
        jtfTitle.setForeground(Color.BLUE);
        jtfYear.setFont(police);
        jtfYear.setPreferredSize(new Dimension(800, 30));
        jtfYear.setForeground(Color.BLUE);
        jtfType.setFont(police);
        jtfType.setPreferredSize(new Dimension(800, 30));
        jtfType.setForeground(Color.BLUE);

        // Panel generation
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Title field
        c.insets = new Insets(2, 5, 0, 5);
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(new JLabel("Titre : ", JLabel.TRAILING), c);
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(jtfTitle, c);

        // Year field
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(new JLabel("Année : ", JLabel.TRAILING), c);
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(jtfYear, c);

        // Category field
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(new JLabel("Type : ", JLabel.TRAILING), c);
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        pane.add(jtfType, c);

        // Ok button
        JButton bOK = new JButton("Rechercher");
        bOK.addActionListener(new BoutonListener());
        c.insets = new Insets(10, 5, 10, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        pane.add(bOK, c);
        this.getRootPane().setDefaultButton(bOK);

        // List of films
        Object[][] data = {};
        String[] columnNames = {"ID", "Titre", "Réalisateur", "Année", "Durée",
                "Genre", "Nationalité"};
        tableFilms = new JTable(new DefaultTableModel(data, columnNames));
        tableFilms.setFillsViewportHeight(false);
        JScrollPane scrollTable = new JScrollPane(tableFilms);
        scrollTable.setPreferredSize(new Dimension(800, 200));
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 50;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 4;
        pane.add(scrollTable, c);
        ListSelectionModel listSelectionModel = tableFilms.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ControllerTable());

        // Summary display
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 200;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 5;
        JScrollPane scrollResume = new JScrollPane(resume);
        pane.add(scrollResume, c);

        // Display the window
        this.pack();
        this.setVisible(true);

    }

    /**
     * Menu actions
     */
    public void actionPerformed(ActionEvent e) {

        // Menu actions
        switch (e.getActionCommand()) {

            case "Quitter": // Close the application
                this.dispose();
                break;

            case "Ajout d'un film": // Add a film
                try {
                    // Open the form to add a film
                    AddFilm form = new AddFilm();
                    form.setVisible(true);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                break;

            case "Modification d'un film": // Edit a film
                // Open the form to edit a film if a film is selected
                if (tableFilms.getSelectedRowCount() > 0) {
                    try {
                        // Get the selected film
                        Film selectedFilm = dbfilms.getFilmById(selectedFilmID);

                        // Open the form to edit the film
                        EditFilm form = new EditFilm(selectedFilm);
                        form.setVisible(true);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No film selected !");
                }
                break;

            case "Statistiques": // Display statistics
                try {
                    // Open the statistics form
                    StatisticsFilms form = new StatisticsFilms();
                    form.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "Suppression film": // Delete a film

                // Delete the selected film if a film is selected
                if (tableFilms.getSelectedRowCount() > 0) {
                    // Delete the film
                    dbfilms.deleteFilm(selectedFilmID);
                    JOptionPane.showMessageDialog(null, "The film " + tableFilms.getValueAt(tableFilms.getSelectedRow(), 1) + " is deleted.");
                } else {
                    JOptionPane.showMessageDialog(null, "No film selected !");
                }
                // Refresh the list of films
                this.getRootPane().getDefaultButton().getActionListeners()[0].
                        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                break;
        }
    }

    /**
     * Update the summary of the selected film
     * @param film Film object to display
     */
    public void updateResume(Film film) {
        resume.setText("Titre : " + film.getTitle() + "\n"
                + "Réalisateur : " + film.getDirector() + "\n"
                + "Année : " + film.getYear() + "\n"
                + "Durée : " + film.getDuration() + " minutes\n"
                + "Genre : " + film.getCategory() + "\n"
                + "Nationalité : " + film.getNationality() + "\n\n"
                + "Résumé : " + film.getResume() + "\n\n"
                + "Acteurs : " + film.getActors() + "\n"
                + "Date d'ajout : " + film.getDateAdded());
    }

    /**
     * ButonListener class
     */
    class BoutonListener implements ActionListener {

        /**
         * Action performed
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {

            // Form values
            String title = null;
            String year = null;
            String category = null;

            // Get the form values
            if (!jtfTitle.getText().isEmpty()) {
                title = jtfTitle.getText();
            }
            if (!jtfYear.getText().isEmpty()) {
                year = jtfYear.getText();
            }
            if (!jtfType.getText().isEmpty()) {
                category = jtfType.getText();
            }

            // Get the films from the database
            ArrayList<Film> films = dbfilms.get_films(title, year, category);

            // Table model
            DefaultTableModel model = (DefaultTableModel) tableFilms.getModel();

            // Clear the table
            if (model.getRowCount() > 0) {
                for (int i = model.getRowCount() - 1; i > -1; i--) {
                    model.removeRow(i);
                }
            }

            // Clear the summary
            resume.setText("");

            // Add the films to the table
            films.forEach(film -> {
                model.addRow(new Object[]{
                        film.getId(),
                        film.getTitle(),
                        film.getDirector(),
                        film.getYear(),
                        film.getDuration(),
                        film.getCategory(),
                        film.getNationality()});
            });

            Film firstFilm = films.get(0);
            updateResume(firstFilm);
        }
    }

    /**
     * ControllerTable class
     */
    public class ControllerTable implements ListSelectionListener {

        /**
         * Value changed
         * @param listSelectionEvent the event to be processed
         */
        public void valueChanged(ListSelectionEvent listSelectionEvent) {

            // Ignore extra messages
            if (listSelectionEvent.getValueIsAdjusting()) {
                return;
            }

            // Get the selected row
            ListSelectionModel lsm = (ListSelectionModel) listSelectionEvent.getSource();
            if (lsm.isSelectionEmpty()) {
                System.out.println("No rows selected");
                return;
            }

            // Get the selected row
            int selectedRow = lsm.getMinSelectionIndex();
            System.out.println("The row " + selectedRow + " is now selected");

            // Get the selected film
            selectedFilmID = (String) tableFilms.getValueAt(selectedRow, 0);  // ID du film
            Film selectedFilm = dbfilms.getFilmById(selectedFilmID);

            // Update the summary
            updateResume(selectedFilm);
        }
    }

    /**
     * Main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            MainApp form = new MainApp();
            form.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Dispose the application
     */
    @Override
    public void dispose() {
        if (dbfilms != null) {
            dbfilms.close();
        }
        super.dispose();
    }
}


