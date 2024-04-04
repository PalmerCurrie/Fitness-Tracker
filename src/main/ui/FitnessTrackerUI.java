package ui;

import model.*;
import model.exceptions.WorkoutNameAlreadyExistsException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents the FitnessTracker ui, its main window frame (desktop).
public class FitnessTrackerUI extends JFrame {

    private static final int WIDTH = 1080;
    private static final int HEIGHT = 720;
    private FitnessTracker ft;
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private JInternalFrame workoutListFrame;
    private JFrame graphFrame;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/fitnesstracker.json";
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> workoutJList = new JList<>(model);

    // Constructor sets up panel
    // MODIFIES: this, desktop
    // EFFECTS:  Creates UI, adds all components to main frame desktop
    public FitnessTrackerUI() {
        ft = new FitnessTracker();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        desktop = new JDesktopPane(); // creates desktop

        // creates the control panel
        controlPanel = new JInternalFrame("Fitness Tracker Panel", true, false, false);
        // For Control Panel
        controlPanel.setPreferredSize(new Dimension(200, 300));
        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);

        // creates workoutListFrame and graphFrame
        workoutListFrame = new JInternalFrame("Workout List", false, false, false);
        graphFrame = new JFrame("Graph");
        graphFrame.setPreferredSize(new Dimension(500, 500));

        setContentPane(desktop);
        setTitle("Fitness Tracker App");
        setSize(WIDTH, HEIGHT);

        addButtonPanel();
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    // Represents a Graph that will graph out User's Workout Weight data.
    public class Graph extends JPanel {
        private static final int MARGIN = 50;
        private static final int WIDTH = 450;
        private static final int HEIGHT = 400;
        private List<Double> weightList = null;


        // MODIFIES: this
        // EFFECTS: creates graph frame of preferred size, (WIDTH, HEIGHT)
        public Graph() {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setBackground(Color.BLACK);
        }

        // MODIFIES: graphFrame
        // EFFECTS: sets up GraphFrame, draws the axis and labels.
        public void setUpGraph(Graphics2D graphic2D) {
            // Draw the x-axis, y-axis and Labels
            graphic2D.setColor(Color.WHITE);
            graphic2D.drawLine(MARGIN, HEIGHT - MARGIN, WIDTH - MARGIN, HEIGHT - MARGIN);   // x-axis
            graphic2D.drawLine(MARGIN, HEIGHT - MARGIN, MARGIN, MARGIN);  // y-axis
            graphic2D.drawString("Weight Progress of Last 10 Workouts", (WIDTH / 2) - 100, 20);
            graphic2D.drawString("Workouts (Oldest to Newest)", (WIDTH / 2) - 75, HEIGHT - 10);
            graphic2D.drawString("Weight", 5, HEIGHT / 2);

        }

        // MODIFIES: graphFrame
        // EFFECTS: paints component (data) onto graph frame
        @Override
        protected void paintComponent(Graphics graphic) {
            super.paintComponent(graphic);
            Graphics2D graphic2D = (Graphics2D) graphic;
            setUpGraph(graphic2D);


            // Get data and Max height
            weightList = getWeightData();
            Double maxHeight = getMaxHeight(weightList);

            // Distance for separating each point
            int pointSeparatorX = (WIDTH - 2 * MARGIN) / (weightList.size() - 1);
            int pointSeparatorY = (int) ((HEIGHT - 2 * MARGIN) / maxHeight);

            // Draw each point and connect with line, label each point with weight text
            for (int p = 0; p < weightList.size() - 1; p++) {
                int x1 = (int) (MARGIN + p * pointSeparatorX);
                int y1 = (int) (HEIGHT - MARGIN - weightList.get(p) * pointSeparatorY);
                int x2 = MARGIN + (p + 1) * pointSeparatorX;
                int y2 = (int) (HEIGHT - MARGIN - weightList.get(p + 1) * pointSeparatorY);


                graphic2D.setColor(Color.WHITE);
                graphic2D.drawLine(x1, y1, x2, y2); // Connects point 1 and 2 with line
                graphic2D.drawString(String.valueOf(weightList.get(p)), x1 - 2, y1 - 15);
                graphic2D.drawString(String.valueOf(weightList.get(p + 1)), x2 - 2, y2 - 15);

                graphic2D.setColor(Color.BLUE);
                graphic2D.fillOval(x1 - 2, y1 - 5, 10, 10);  // Draws the point
                graphic2D.fillOval(x2 - 2, y2 - 5, 10, 10);  // Draws the point


            }


        }

        // EFFECTS: returns the max point in data, to set upper limit for data points
        public double getMaxHeight(List<Double> weightList) {
            double maxHeight = Double.MIN_VALUE;
            for (double d : weightList) {
                if (d > maxHeight) {
                    maxHeight = d;
                }
            }
            return maxHeight;
        }

        // EFFECTS: returns a list of the users latest 10 workout weights.
        public List<Double> getWeightData() {
            List<Double> weightData = new ArrayList<>();
            if (ft.getListSize() > 10) {
                for (int i = (ft.getListSize() - 1); i >= (ft.getListSize() - 10); i--) {
                    Workout wk = ft.getWorkoutList().get(i);
                    weightData.add(wk.getWeight());
                }
            } else {
                for (int i = (ft.getListSize() - 1); i >= 0; i--) {
                    Workout wk = ft.getWorkoutList().get(i);
                    weightData.add(wk.getWeight());
                }
            }

            Collections.reverse(weightData);
            return weightData;
        }


    }


    // Helper method that adds button panel
    // MODIFIES: buttonPanel
    // EFFECTS: adds all buttons to button panel
    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton(new CreateNewWorkout()));
        buttonPanel.add(new JButton(new ViewWorkouts()));
        buttonPanel.add(new JButton(new ViewWeightProgress()));
        buttonPanel.add(new JButton(new LoadFitnessTracker()));
        buttonPanel.add(new JButton(new SaveFitnessTracker()));
        buttonPanel.add(new JButton(new ExitWithoutSaving()));

        controlPanel.add(buttonPanel);

    }

    // Represents a button that will display User's weight progress on click
    public class ViewWeightProgress extends AbstractAction {

        // MODIFIES: this
        // EFFECTS:  creates button with given Name
        ViewWeightProgress() {
            super("View Weight Progress");
        }

        // MODIFIES:  this or ft
        // EFFECTS: on button click creates Graph and displays it
        @Override
        public void actionPerformed(ActionEvent event) {
            graphFrame.getContentPane().add(new Graph());
            graphFrame.pack();

            Graph graph = new Graph();
            graphFrame.add(graph);

            graph.setVisible(true);
            graphFrame.setVisible(true);


        }
    }

    // Represents a button that will open a create new Workout menu on click
    public class CreateNewWorkout extends AbstractAction {

        // MODIFIES: this
        // EFFECTS:  Creates button with name Create New Workout.
        CreateNewWorkout() {
            super("Create New Workout");
        }

        // MODIFIES: this, desktop, newWorkoutPanel
        // EFFECTS:  sets up newWorkoutPanel with frame and text fields
        public JPanel setUpCreateWorkoutFrame(JPanel newWorkoutPanel) {
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

            // Adds ui to the newWorkoutPanel
            newWorkoutPanel.add(nameLabel);
            newWorkoutPanel.add(dateLabel);
            newWorkoutPanel.add(weightLabel);

            newWorkoutPanel.setLocation(0, 300);
            newWorkoutPanel.setLayout(null);

            return newWorkoutPanel;

        }

        // MODIFIES: ft
        // EFFECTS:  adds new workout to ft WorkoutList
        public void tryAddWorkout(Workout newWorkout) {
            try {
                ft.addWorkout(newWorkout);
            } catch (WorkoutNameAlreadyExistsException ex) {
                throw new RuntimeException(ex);
            }
        }

        // MODIFIES: this, ft
        // EFFECTS: On button click creates new panel for users to enter workout information.
        //          on create workout button click creates new workout based on user information and adds to ft
        @Override
        public void actionPerformed(ActionEvent event) {
            JPanel newWorkoutPanel = setUpCreateWorkoutFrame(new JPanel());

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

            // Adds ui to the newWorkoutPanel
            newWorkoutPanel.add(workoutName);
            newWorkoutPanel.add(workoutDate);
            newWorkoutPanel.add(workoutWeight);
            newWorkoutPanel.add(submitButton);

            // Creates new method on button press
            submitButton.addActionListener(e -> {
                String wkName = workoutName.getText();
                String wkDate = workoutDate.getText();
                String wkWeight = workoutWeight.getText();
                JOptionPane.showMessageDialog(null, "Creating Workout: " + wkName + " "
                        + wkDate + " " + wkWeight);
                newWorkoutPanel.setVisible(false);
                Workout newWorkout = new Workout(wkName, wkDate, Double.parseDouble(wkWeight));
                tryAddWorkout(newWorkout);
            });


        }
    }


    // Represents a button that will show list of workouts in GUI, showing the name and date for each workout
    public class ViewWorkouts extends AbstractAction {
        JPanel workoutButtonPanel;


        // MODIFIES: this
        // EFFECTS: Creates button for viewing Workouts
        ViewWorkouts() {
            super("View Workouts");
        }


        // MODIFIES: desktop, workoutListFrame
        // EFFECTS:  updates and creates WorkoutListFrame and adds to desktop panel
        public void createWorkoutListFrame() {
            desktop.remove(workoutListFrame);
            workoutListFrame = new JInternalFrame("Workout List", false, false, false);
            desktop.add(workoutListFrame);
            workoutListFrame.setBackground(Color.gray);
            workoutListFrame.setPreferredSize(new java.awt.Dimension(300, 300));
            workoutListFrame.pack();
            workoutListFrame.setLocation(200, 0);
            workoutListFrame.setBackground(Color.gray);
            workoutListFrame.setVisible(true);

            workoutButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            workoutButtonPanel.setLayout(new BoxLayout(workoutButtonPanel, BoxLayout.Y_AXIS));
            workoutButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        }


        // MODIFIES: this
        // EFFECTS: adds all workouts in list to JList and sets the workoutList frame visible. on button click
        @Override
        public void actionPerformed(ActionEvent event) {
            // Creates panel for WorkoutList
            createWorkoutListFrame();

            model = new DefaultListModel<>();
            workoutJList = new JList<>(model);

            // updates workoutList
            List<Workout> workoutList = ft.getWorkoutList();
            for (int i = 0; i < workoutList.size(); i++) {
                model.addElement(i + ") " + ft.getWorkoutList().get(i).getName() + " - "
                        + ft.getWorkoutList().get(i).getDate());
            }


            // Adds workoutList Frame to Swing Panel
            workoutListFrame.add(workoutJList);
            workoutJList.setLayoutOrientation(JList.VERTICAL);
            workoutJList.setVisible(true);
            workoutListFrame.setVisible(true);
            workoutButtonPanel.setVisible(true);
            workoutListFrame.add(workoutButtonPanel, BorderLayout.SOUTH);

            // Creates select workout button
            JButton selectedButton = new JButton(new SelectWorkoutButton());
            selectedButton.setVisible(true);
            workoutButtonPanel.add(selectedButton);

            // Creates remove workout button
            JButton removeWorkoutButton = new JButton(new RemoveWorkoutButton());
            removeWorkoutButton.setVisible(true);
            workoutButtonPanel.add(removeWorkoutButton);

            // Creates reorder workouts button
            JButton reorderWorkoutsButton = new JButton(new ReorderWorkoutsButton());
            reorderWorkoutsButton.setVisible(true);
            workoutButtonPanel.add(reorderWorkoutsButton);


        }
    }

    // Represents a button that will select workout from workout list and
    //      opens up workout menu for use to edit the workout
    public class SelectWorkoutButton extends AbstractAction {

        // MODIFIES: this
        // EFFECTS:    creates button with specified name
        SelectWorkoutButton() {
            super("Select Workout");

        }

        // MODIFIES: ft
        // EFFECTS: on button click selects the workout that is selected by user
        @Override
        public void actionPerformed(ActionEvent event) {
            String selectedItem = workoutJList.getSelectedValue();
            JOptionPane.showMessageDialog(null, "You selected: " + selectedItem);
            workoutListFrame.setVisible(false);
            workoutJList.setVisible(false);
            if (selectedItem != null) {
                char indexChar = selectedItem.charAt(0);
                int index = indexChar - '0';
                Workout selectedWorkout = ft.getWorkoutList().get(index);
//                openWorkoutMenu(selectedWorkout);
            }
        }
    }


    // EFFECTS: Opens Workout menu for the selected workout, has buttons to add, edit, view, remove, and save exercises
    //          to the workout, and to save workout to fitness tracker.
//    public void openWorkoutMenu(Workout wk) {
//
//    }


    // Represents a button that will select workout from workout list and remove that selected workout
    public class RemoveWorkoutButton extends AbstractAction {

        // MODIFIES: this
        // EFFECTS:    creates button with specified name
        RemoveWorkoutButton() {
            super("Remove Workout");

        }

        // MODIFIES: ft, workoutList
        // EFFECTS: on button click removes the selected workout from ft and workoutList
        @Override
        public void actionPerformed(ActionEvent event) {
            String selectedItem = workoutJList.getSelectedValue();
            JOptionPane.showMessageDialog(null, "You Removed: " + selectedItem);
            workoutListFrame.setVisible(false);
            workoutJList.setVisible(false);
            if (selectedItem != null) {
                char indexChar = selectedItem.charAt(0);
                int index = indexChar - '0';
                List<Workout> workoutList = ft.getWorkoutList();
                Workout selectedWorkout = workoutList.get(index);
                ft.removeWorkout(selectedWorkout);
            }
        }
    }

    // Represents a button that will reorder the Workout list in Descending Order, (Newest -> Oldest)
    public class ReorderWorkoutsButton extends AbstractAction {

        // MODIFIES: this
        // EFFECTS:  creates button with specified name
        ReorderWorkoutsButton() {
            super("Reorder in Descending Order");

        }

        // MODIFIES: ft
        // EFFECTS: on button click updates the displayed workoutList to be in reverse order
        @Override
        public void actionPerformed(ActionEvent event) {
            model.removeAllElements();
            // updates workoutList
            List<Workout> workoutList = ft.getWorkoutList();
            for (int i = (workoutList.size() - 1); i >= 0; i--) {
                model.addElement(i + ") " + ft.getWorkoutList().get(i).getName() + " - "
                        + ft.getWorkoutList().get(i).getDate());
            }
        }
    }


    // Represents a button that will load in FitnessTracker data on click
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


    //  Represents a button that will save FitnessTracker data to file on click
    public class SaveFitnessTracker extends AbstractAction {

        // MODIFIES: this
        // EFFECTS: Creates button for loading previous fitness tracker
        SaveFitnessTracker() {
            super("Save Fitness Tracker");
        }

        // Modifies: ft
        // EFFECTS: loads previously saved fitness tracker from json file into ft on button click
        @Override
        public void actionPerformed(ActionEvent event) {
            saveApplication();

        }

        // MODIFIES: jsonWriter
        // EFFECTS: writes application to file
        public void saveApplication() {
            try {
                jsonWriter.open();
                jsonWriter.write(ft);
                jsonWriter.close();
                System.out.println("Saved Fitness Tracker Application");
                ft.printLogs();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file...: " + JSON_STORE);
            }
        }

    }

    //  Represents a button that will exit the FitnessTracker without saving
    public class ExitWithoutSaving extends AbstractAction {

        // MODIFIES: this
        // EFFECTS: Creates button for exiting FitnessTracker without saving
        ExitWithoutSaving() {
            super("Exit Without Saving");
        }

        // EFFECTS: on click closes FitnessTracker without saving
        @Override
        public void actionPerformed(ActionEvent event) {
            ft.printLogs();
            System.exit(0);
        }
    }



    // Starts the application
    public static void main(String[] args) {
        new FitnessTrackerUI();
    }

}
