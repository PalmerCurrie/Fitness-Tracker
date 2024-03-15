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

       // FitnessTrackerUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    something to exit program on X click

    }



    // Helper method that adds button panel
    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton(new HelperButtonClass()));
        buttonPanel.add(new JButton(new CreateNewWorkout()));
        buttonPanel.add(new JButton(new ViewWorkouts()));
        buttonPanel.add(new JButton(new LoadFitnessTracker()));

        controlPanel.add(buttonPanel);

    }

    // MODIFIES: this
    // EFFECTS: what does button do on button click
    public class HelperButtonClass extends AbstractAction {

        // MODIFIES: this
        // EFFECTS:    creates button with name
        HelperButtonClass() {
            super("button name");
        }

        // MODIFIES:  this or ft
        // EFFECTS: on button click performs x action
        @Override
        public void actionPerformed(ActionEvent event) {

        }
    }

    // MODIFIES: this
    // EFFECTS: what does button do on button click
    public class CreateNewWorkout extends AbstractAction {

        // MODIFIES: this
        // EFFECTS:  Creates button with name Create New Workout.
        CreateNewWorkout() {
            super("Create New Workout");
        }

        // MODIFIES: this, ft
        // EFFECTS: On button click creates new panel for users to enter workout information.
        //          on create workout button click creates new workout based on user information and adds to ft
        @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
        @Override
        public void actionPerformed(ActionEvent event) {

            JPanel newWorkoutPanel = new JPanel();
            newWorkoutPanel.setSize(350, 200);

            desktop.add(newWorkoutPanel);
            newWorkoutPanel.setVisible(true);

            // Creates prompt for user
            JLabel nameLabel = new JLabel("Name: ");
            nameLabel.setBounds(20, 20, 200, 20);
            JLabel dateLabel = new JLabel("Date: ");
            dateLabel.setBounds(20, 60, 200, 20);
            JLabel weightLabel = new JLabel("Weight: ");
            weightLabel.setBounds(20, 100, 200, 20);

            // Creates text field for uses to input
            JTextField workoutName = new JTextField();
            workoutName.setBounds(65, 20, 150, 25);
            JTextField workoutDate = new JTextField();
            workoutDate.setBounds(65, 60, 150, 25);
            JTextField workoutWeight = new JTextField();
            workoutWeight.setBounds(65, 100, 150, 25);

            // Creates button
            JButton submitButton = new JButton("Create Workout");
            submitButton.setBounds(100, 130, 150, 25);

            // Creates new method on button press
            submitButton.addActionListener(e -> {
                String wkName = workoutName.getText();
                String wkDate = workoutDate.getText();
                String wkWeight = workoutWeight.getText();
                JOptionPane.showMessageDialog(null, "You entered: " + wkName + wkDate + wkWeight);
                Workout newWorkout = new Workout(wkName, wkDate, Double.parseDouble(wkWeight));
                try {
                    ft.addWorkout(newWorkout);
                } catch (WorkoutNameAlreadyExistsException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // Adds ui to the newWorkoutPanel
            newWorkoutPanel.add(nameLabel);
            newWorkoutPanel.add(dateLabel);
            newWorkoutPanel.add(weightLabel);
            newWorkoutPanel.add(workoutName);
            newWorkoutPanel.add(workoutDate);
            newWorkoutPanel.add(workoutWeight);
            newWorkoutPanel.add(workoutName);
            newWorkoutPanel.add(submitButton);


            newWorkoutPanel.setLayout(null);



        }
    }



    // MODIFIES: this
    // EFFECTS: shows list of workouts in GUI, showing the name and date for each workout
    public class ViewWorkouts extends AbstractAction {

        // MODIFIES: this
        // EFFECTS: Creates button for viewing Workouts
        ViewWorkouts() {
            super("View Workouts");
        }


        // Modifies: this
        // EFFECTS: adds all workouts in list to JList and sets the workoutList frame visible. on button click
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

        // MODIFIES: this
        // EFFECTS: Creates button for loading previous fitness tracker
        LoadFitnessTracker() {
            super("Load Previous Fitness Tracker");
        }

        // Modifies: ft
        // EFFECTS: loads previously saved fitness tracker from json file into ft on button click
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
    -----* Create new Workout
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
