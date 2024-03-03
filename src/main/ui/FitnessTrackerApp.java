package ui;

import model.Exercise;
import model.FitnessTracker;
import model.Workout;
import model.exceptions.WorkoutNameAlreadyExistsException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Fitness Tracker application
public class FitnessTrackerApp {

    private static final String JSON_STORE = "./data/fitnesstrackertestingmarch3.json";
    private Scanner scan;
    private FitnessTracker fitnessTracker;
    private Workout workout;
    private Exercise exercise;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Fitness Tracker Application
    public FitnessTrackerApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runFitnessTrackerApp();
    }

    // MODIFIES: this
    // EFFECTS:  initializes FitnessTracker app
    public void initialize() {
        scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        fitnessTracker = new FitnessTracker();

        // load or set FitnessTracker object
    }

    // EFFECTS: this
    // MODIFIES: processes the user's input
    public void runFitnessTrackerApp() {
        int selectedOption;
        boolean keepRunning = true;

        initialize();

        while  (keepRunning) {
            fitnessTrackerMenu();
            selectedOption = scan.nextInt();

            if (selectedOption == 6) {
                saveApplication();
                System.out.println("Exiting.");
                keepRunning = false;
            } else {
                processFitnessTrackerMenuInput(selectedOption);
            }

        }

    }


    // EFFECTS: displays menu interface where user will select option
    public void fitnessTrackerMenu() {
        System.out.print("   Fitness Tracker Options");
        System.out.println("\n Select Option From Below:");
        System.out.println("\t1) Create new Workout");
        System.out.println("\t2) Edit Workout");
        System.out.println("\t3) Remove Workout");
        System.out.println("\t4) View Workouts");
        System.out.println("\t5) Load/Reload In Previous Data");
        System.out.println("\t6) Save and Exit Application");
    }
    
    // EFFECTS:  processes user input and runs desired method
    public void processFitnessTrackerMenuInput(int option) {
        if (option == 1) {
            goIntoWorkoutMenu();
        } else if (option == 2) {
            editWorkout();
        } else if (option == 3) {
            removeWorkout();
        } else if (option == 4) {
            viewWorkouts();
        } else if (option == 5) {
            loadApplication();
        } else {
            System.out.println("That is not a valid option.");
            System.out.println("Please enter the number corresponding to the option you would like to select");
        }

    }

    // EFFECTS:  displays Workout Menu and handles user input
    public void goIntoWorkoutMenu() {
        int workoutMenuOption;
        boolean inWorkoutMenu = true;
        createNewWorkout();
        while  (inWorkoutMenu) {
            workoutMenu();
            workoutMenuOption = scan.nextInt();
            if (workoutMenuOption == 5) {
                saveWorkout(workout);
                System.out.println("Exiting Workout Menu entering FitnessMenu");
                inWorkoutMenu = false;
            } else {
                processWorkoutMenuInput(workoutMenuOption);
            }
        }
    }

    //EFFECTS:  displays workout interface for user to select option
    public void workoutMenu() {
        System.out.print("   Workout Options");
        System.out.println("\n Select Option From Below:");
        System.out.println("\t1) Add Exercise to Workout");
        System.out.println("\t2) Remove Exercise from Workout");
        System.out.println("\t3) Edit Exercise");
        System.out.println("\t4) View Exercises");
        System.out.println("\t5) Save Workout to FitnessTracker");
    }

    // MODIFIES: this
    // EFFECTS:  processes user input and runs desired method
    public void processWorkoutMenuInput(int option) {
        if (option == 1) {
            createNewExercise();
        } else if (option == 2) {
            removeExercise(workout);
        } else if (option == 3) {
            editExercise(workout);
        } else if (option == 4) {
            viewExercises(workout);
        } else {
            System.out.println("That is not a valid option.");
            System.out.println("Please enter the number corresponding to the option you would like to select");
        }
    }


    // FITNESS MENU METHODS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    // MODIFIES: this
    // EFFECTS:  creates a new Workout object with user input information
    public void createNewWorkout() {
        System.out.println("Enter Workout Name:");
        String name = scan.next();
        System.out.println("Enter Date of Workout:");
        String date = scan.next();
        System.out.println("Enter Your Current Weight:");
        double weight = scan.nextDouble();
        workout = new Workout(name, date, weight);
    }

    // REQUIRES: ft.getListSize() > 0
    // MODIFIES: this
    // EFFECTS: edits workout based on user input
    public void editWorkout() {
        System.out.println("Select which Workout you would like to edit:");
        viewWorkouts();
        int selectedWorkoutOption = scan.nextInt();
        if (selectedWorkoutOption > fitnessTracker.getListSize()) {
            System.out.println("That is not a valid input");
        } else {
            Workout workoutToEdit = fitnessTracker.getWorkoutList().get(selectedWorkoutOption);
            System.out.println("What would you like to edit: \n1) Name \n2) Date \n3) Weight \n4) Exercises");
            int editOption = scan.nextInt();
            if (editOption == 1) {
                System.out.println("Enter new Workout Name:");
                workoutToEdit.setName(scan.next());
            } else if (editOption == 2) {
                System.out.println("Enter new Workout Date:");
                workoutToEdit.setDate(scan.next());
            } else if (editOption == 3) {
                System.out.println("Enter new Weight:");
                workoutToEdit.setWeight(scan.nextDouble());
            } else if (editOption == 4) {
                editExercise(workoutToEdit);
            }
        }
    }


    // MODIFIES: this
    // EFFECTS:  removes exercise at given inputs from Workouts exerciseList.
    public void removeWorkout() {
        if (fitnessTracker.getListSize() == 0) {
            System.out.println("There are no Workouts to remove");
        } else {
            System.out.println("Select the Exercise you would like to delete: ");
            viewWorkouts();
            int selectedWorkout = scan.nextInt();
            System.out.println("removing: " + fitnessTracker.getWorkoutList().get(selectedWorkout).getName());
            fitnessTracker.removeWorkout(selectedWorkout);
        }

    }

    // EFFECTS: Prints out Workout list by printing the name of each workout
    public void viewWorkouts() {
        System.out.println("printing Workouts");
        if (fitnessTracker.getListSize() == 0) {
            System.out.println("There are no workouts saved.");
        } else {
            for (int i = 0; i < fitnessTracker.getListSize(); i++) {
                Workout currentWorkout = fitnessTracker.getWorkoutList().get(i);
                System.out.println(i + ") " + currentWorkout.getName() + " - " + currentWorkout.getDate());
            }
        }
    }




    // WORKOUT MENU METHODS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!





    // MODIFIES: this
    // EFFECTS:  creates a new Exercise object with user input information
    public void createNewExercise() {
        System.out.println("Enter Exercise Name:");
        String exerciseName = scan.next();
        System.out.println("Enter Number of Sets:");
        int exerciseSets = scan.nextInt();
        System.out.println("Enter Number of Reps:");
        int exerciseReps = scan.nextInt();
        System.out.println("Enter Exercise Weight:");
        int exerciseWeight = scan.nextInt();
        exercise = new Exercise(exerciseName, exerciseSets, exerciseReps, exerciseWeight);
        workout.addExercise(exercise);
    }

    // MODIFIES: this
    // EFFECTS:  removes exercise at given inputs from Workouts exerciseList.
    public void removeExercise(Workout workout) {
        System.out.println("Select the Exercise you would like to delete: ");
        viewExercises(workout);
        int selectedExercise = scan.nextInt();
        if (selectedExercise < workout.getListSize()) {
            System.out.println("removing: " + workout.getExerciseList().get(selectedExercise).getName());
            workout.removeExercise(selectedExercise);
        } else {
            System.out.println("That is not a valid input.");
        }
    }

    // REQUIRES: selectedWorkout.getListSize() > 0
    // MODIFIES: this
    // EFFECTS:  edits selected exercise based on user input
    public void editExercise(Workout selectedWorkout) {
        System.out.println("Select which Exercise you would like to edit:");
        viewExercises(selectedWorkout);
        Exercise exerciseToEdit = selectedWorkout.getExerciseList().get(scan.nextInt());
        System.out.println("What would you like to edit: \n1) Name \n2) Sets \n3) Reps \n4) Weight");
        int editOption = scan.nextInt();
        if (editOption == 1) {
            System.out.println("Enter new Exercise Name:");
            exerciseToEdit.setName(scan.next());
        } else if (editOption == 2) {
            System.out.println("Enter new Sets:");
            exerciseToEdit.setSets(scan.nextDouble());
        } else if (editOption == 3) {
            System.out.println("Enter new Reps:");
            exerciseToEdit.setReps(scan.nextInt());
        } else if (editOption == 4) {
            System.out.println("Enter new Weight:");
            exerciseToEdit.setWeight(scan.nextDouble());
        } else {
            System.out.println("That is not a valid input.");
        }
    }

    // EFFECTS: Prints out Workout list by printing the name of each workout
    public void viewExercises(Workout workout) {
        System.out.println("printing Exercises for this workout");
        if (workout.getListSize() == 0) {
            System.out.println("There are no Exercises saved.");
        } else {
            for (int i = 0; i < workout.getListSize(); i++) {
                Exercise currentExercise = workout.getExerciseList().get(i);
                System.out.println(i + ") " + currentExercise.getName());
            }
        }
    }


    // MODIFIES: this
    // EFFECTS:  adds workout to FitnessTracker workout list
    public void saveWorkout(Workout workout) {
        try {
            fitnessTracker.addWorkout(workout);
        } catch (WorkoutNameAlreadyExistsException e) {
            System.out.println("Workout Name already exists, please enter a new name:");
            String newWorkoutName = scan.next();
            workout.setName(newWorkoutName);
            saveWorkout(workout);
        }
    }



    // EFFECTS: writes application to file
    public void saveApplication() {
        try {
            jsonWriter.open();
            jsonWriter.write(fitnessTracker);
            jsonWriter.close();
            System.out.println("Saved Fitness Tracker Application");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file...: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS:  loads in FitnessTracker from previously saved Json file in data folder.
    // catches exception if an error happens when trying to load in Json file.
    public void loadApplication() {
        try {
            fitnessTracker = jsonReader.read();
            System.out.println("Loaded Fitness Tracker App");
        } catch (IOException | WorkoutNameAlreadyExistsException e) {
            System.out.println("Unable to read file...: " + JSON_STORE);
        }
    }

}
