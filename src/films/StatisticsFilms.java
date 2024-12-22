package films;

import db.MongoDBFilms;
import models.Film;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsFilms extends JFrame {
    private final ArrayList<Film> films;

    /**
     * Constructor
     */
    public StatisticsFilms() {
        // Connexion BDD
        MongoDBFilms dbfilms = new MongoDBFilms();
        dbfilms.connect();
        films = dbfilms.get_films(null, null, null);

        // Init UI
        this.initUI();
    }

    /**
     * Initialize the UI
     */
    private void initUI() {
        // Set title
        setTitle("Statistics Films");

        // Set size
        setSize(600, 400);

        // Set default close operation
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set location
        setLocationRelativeTo(null);

        // Create layout for statistics
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panel);

        // Add statistics to the panel
        panel.add(createCategoryStatsPanel());
        panel.add(createYearStatsPanel());
        panel.add(createRecentAdditionsPanel());

        // Add scroll pane to frame
        add(scrollPane);

        // Display the UI
        setVisible(true);
    }

    /**
     * Create a panel with statistics about the categories
     *
     * @return JPanel
     */
    private JPanel createCategoryStatsPanel() {
        // Create a panel with a border and a title
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.setBorder(BorderFactory.createTitledBorder("Répartition par catégorie"));

        // Calculate the distribution by category
        Map<String, Long> categoryCount = films.stream()
                .collect(Collectors.groupingBy(Film::getCategory, Collectors.counting()));

        // Display the categories and counts
        JTextArea categoryText = new JTextArea();
        categoryText.setEditable(false);
        categoryCount.forEach((category, count) -> {
            categoryText.append(category + ": " + count + " film\n");
        });

        // Add the text area to the panel
        categoryPanel.add(new JScrollPane(categoryText), BorderLayout.CENTER);

        // Return the panel
        return categoryPanel;
    }

    /**
     * Create a panel with statistics about the years
     *
     * @return JPanel
     */
    private JPanel createYearStatsPanel() {
        // Create a panel with a border and a title
        JPanel yearPanel = new JPanel(new BorderLayout());
        yearPanel.setBorder(BorderFactory.createTitledBorder("Répartition par année de création"));

        // Calculate the distribution by year
        Map<Integer, Long> yearCount = films.stream()
                .collect(Collectors.groupingBy(Film::getYear, Collectors.counting()));

        // Display the years and counts
        JTextArea yearText = new JTextArea();
        yearText.setEditable(false);
        yearCount.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Trier par année
                .forEach(entry -> yearText.append(entry.getKey() + ": " + entry.getValue() + " film\n"));

        yearPanel.add(new JScrollPane(yearText), BorderLayout.CENTER);
        return yearPanel;
    }

    private JPanel createRecentAdditionsPanel() {
        JPanel recentPanel = new JPanel(new BorderLayout());
        recentPanel.setBorder(BorderFactory.createTitledBorder("Ajouts récents"));

        // Filter the 5 most recent films
        ArrayList<Film> recentFilms = films.stream()
                .sorted((f1, f2) -> f2.getDateAddedDate().compareTo(f1.getDateAddedDate())) // Décroissant
                .limit(5) // Limiter aux 5 plus récents
                .collect(Collectors.toCollection(ArrayList::new));

        // Display the recent films
        JTextArea recentText = new JTextArea();
        recentText.setEditable(false);
        for (Film film : recentFilms) {
            recentText.append(film.getTitle() + " (" + film.getYear() + ") - Ajouté le: " + film.getDateAdded() + "\n");
        }

        recentPanel.add(new JScrollPane(recentText), BorderLayout.CENTER);
        return recentPanel;
    }
}
