package ui;

import model.Exercise;
import model.FitnessTracker;
import model.Workout;

import java.util.Scanner;
import java.util.ArrayList;

// Fitness Tracker application
public class FitnessTrackerApp {

    private Scanner scan;
    private FitnessTracker ft;
    private Workout workout;
    private Exercise exercise;

    // EFFECTS: runs the Fitness Tracker Application
    public FitnessTrackerApp() {
        runFitnessTrackerApp();
    }

    // MODIFIES: this
    // EFFECTS:  initializes FitnessTracker app
    public void initialize() {
        scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        ft = new FitnessTracker();




        // load or set FitnessTracker object
    }

    // EFFECTS: this
    // MODIFIES: processes the user's input
    public void runFitnessTrackerApp() {
        int selectedOption = -1;
        boolean keepRunning = true;

        initialize();

        while  (keepRunning) {
            fitnessTrackerMenu();
            selectedOption = scan.nextInt();

            if (selectedOption == 5) {
                // saveApplication();
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
        System.out.println("\t5) Save and Exit Application");
    }

    // MODIFIES: this
    // EFFECTS:  processes user input and runs desired method
    public void processFitnessTrackerMenuInput(int option) {
        if (option == 1) {
            goIntoWorkoutMenu();

        } else if (option == 2) {
            // editWorkout()
            System.out.println("You have selected option: " + option);  //stub
        } else if (option == 3) {
            // removeWorkout();
            System.out.println("You have selected option: " + option);  //stub
        } else if (option == 4) {
            viewWorkouts();
            System.out.println("You have selected option: " + option);  //stub
        } else if (option == 5) {
            // saveFitnessTracker()
            System.out.println("You have selected option: " + option);  //stub
        } else {
            System.out.println("That is not a valid option.");
            System.out.println("Please enter the number corresponding to the option you would like to select");
        }

    }

    public void goIntoWorkoutMenu() {
        int workoutMenuOption = -1;
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
            removeExercise();
            System.out.println("You have selected option: " + option);  //stub
        } else if (option == 3) {
            // editExercise()
            System.out.println("You have selected option: " + option);  //stub
        } else if (option == 4) {
            viewExercises();
        } else {
            System.out.println("That is not a valid option.");
            System.out.println("Please enter the number corresponding to the option you would like to select");
        }
    }


    // MODIFIES: this
    // EFFECTS:  adds workout to FitnessTracker workout list
    public void saveWorkout(Workout workout) {
        ft.addWorkout(workout);
    }

    // EFFECTS: Prints out Workout list by printing the name of each workout
    public void viewWorkouts() {
        System.out.println("printing Workouts");
        if (ft.getListSize() == 0) {
            System.out.println("There are no workouts saved.");
        } else {
            for (int i = 0; i < ft.getListSize(); i++) {
                Workout currentWorkout = ft.getWorkoutList().get(i);
                System.out.println(i + ") " + currentWorkout.getName() + " - " + currentWorkout.getDate());
            }
        }
    }

    // EFFECTS: Prints out Workout list by printing the name of each workout
    public void viewExercises() {
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
    public void removeExercise() {
        System.out.println("Select the Exercise you would like to delete: ");
        viewExercises();
        int selectedExercise = scan.nextInt();
        System.out.println("removing: " + workout.getExerciseList().get(selectedExercise).getName());
        workout.removeExercise(selectedExercise);

    }


}
