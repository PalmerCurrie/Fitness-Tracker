package ui;

import model.*;
import model.exceptions.WorkoutNameAlreadyExistsException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

// Represents the applications ui, its main window frame.
public class FitnessTrackerUI extends JFrame {

    private static final int WIDTH = 1080;
    private static final int HEIGHT = 720;
    private FitnessTracker ft;
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private JInternalFrame workoutListFrame;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/fitnesstracker.json";

    // Constructor sets up panel
    public FitnessTrackerUI() {
        ft = new FitnessTracker();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        desktop = new JDesktopPane(); // creates desktop

        // creates the control panel
        controlPanel = new JInternalFrame("Control Panel", true, false, false);
        // For Control Panel
        controlPanel.setPreferredSize(new java.awt.Dimension(200, 300));
        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);



        // Creates panel for WorkoutList
        workoutListFrame = new JInternalFrame("Workout List", false, false, false);
        workoutListFrame.setBackground(Color.blue);
        workoutListFrame.setPreferredSize(new java.awt.Dimension(300, 300));
        workoutListFrame.pack();
        workoutListFrame.setVisible(true);
        desktop.add(workoutListFrame);


        setContentPane(desktop);
        setTitle("Fitness Tracker App");
        setSize(WIDTH, HEIGHT);


        addButtonPanel();


        setVisible(true);

    }



    // Helper method that adds button panel
    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton(new HelperButtonClass()));
        buttonPanel.add(new JButton(new ViewWorkouts()));
        buttonPanel.add(new JButton(new LoadFitnessTracker()));

        controlPanel.add(buttonPanel);

    }

    // represents what happens when user clicks this button.
    public class HelperButtonClass extends AbstractAction {

        // constructor
        HelperButtonClass() {
            super("button name");
        }

        // performs action on button click
        @Override
        public void actionPerformed(ActionEvent event) {
            // do something on button click
        }
    }

    // represents what happens when user clicks this button.
    public class ViewWorkouts extends AbstractAction {
        // constructor
        ViewWorkouts() {
            super("View Workouts");
        }

            // performs action on button click
        @Override
        public void actionPerformed(ActionEvent event) {
            DefaultListModel<String> model = new DefaultListModel<>();
            JList<String> workoutJList = new JList<>(model);


            List<Workout> workoutList = ft.getWorkoutList();
            for (Workout wk : workoutList) {
                model.addElement(wk.getName() + " - " + wk.getDate());
            }



            workoutListFrame.add(workoutJList);
            workoutJList.setLayoutOrientation(JList.VERTICAL);
            workoutJList.setVisible(true);
            workoutJList.ensureIndexIsVisible(4); // always shows 4 indexes???
            workoutListFrame.setVisible(true);

        }

    }

    // MODIFIES: ft
    // EFFECTS: loads in previous FitnessTracker JSon file into ft
    //          on button click
    public class LoadFitnessTracker extends AbstractAction {

        // constructor
        LoadFitnessTracker() {
            super("Load Previous Fitness Tracker");
        }

        // performs action on button click
        @Override
        public void actionPerformed(ActionEvent event) {
            loadApplication();

        }

        // MODIFIES: this
        // EFFECTS:  loads in FitnessTracker from previously saved Json file in data folder.
        // catches exception if an error happens when trying to load in Json file.
        public void loadApplication() {
            try {
                ft = jsonReader.read();
                System.out.println("Loaded Fitness Tracker App");
            } catch (IOException | WorkoutNameAlreadyExistsException e) {
                System.out.println("Unable to read file...: " + JSON_STORE);
            }
        }

    }




    /* BUTTONS:   (FitnessTrackerMenu)
    * Create new Workout
    * Edit Workout
    * Remove Workout
    -----* View Workouts
    -----* Load/Reload Previous data
    * Save and Exit application
    * Exit Application without saving
    *
     */




    // Starts the application
    public static void main(String[] args) {
        new FitnessTrackerUI();
    }
}
