package ui;

import model.Exercise;
import model.Workout;
import model.FitnessTracker;

import java.util.Scanner;

// Fitness Tracker application
public class FitnessTrackerApp {

    private Scanner scan;

    // EFFECTS: runs the Fitness Tracker Application
    public FitnessTrackerApp() {
        runFitnessTrackerApp();
    }

    // EFFECTS: this
    // MODIFIES: processes the user's input
    public void runFitnessTrackerApp() {
        int selectedOption = -1;
        boolean keepRunning = true;

        initialize();

        while  (keepRunning) {
            interfaceMenu();
            selectedOption = scan.nextInt();
            if (selectedOption == 7) {
                keepRunning = false;
            } else {
                processMenuInput(selectedOption);
            }

        }
    // handle input

        // initialize application ()

    }



//
/*
Interface Menu:
1) add Exercise to Workout
2) remove exercise from workout
3) edit exercise
4) Save Workout to FitnessTracker
5) View Workout
6) View FitnessTracker
7) Save and Exit Application

 */
    // EFFECTS: displays menu interface where user will select option
    public void interfaceMenu() {
        System.out.println("\n Select Option From Below:");
        System.out.println("\t1) Add Exercise to Workout");
        System.out.println("\t2) Remove Exercise from Workout");
        System.out.println("\t3) Edit Exercise");
        System.out.println("\t4) Save Workout to FitnessTracker");
        System.out.println("\t5) View Workout");
        System.out.println("\t6) View FitnessTracker");
        System.out.println("\t7) Save and Exit Application");
    }

    // MODIFIES: this
    // EFFECTS:  processes user input and runs desired method
    public void processMenuInput(int option) {
        System.out.println("You have selected option: " + option);  //stub


    }

    // MODIFIES: this
    // EFFECTS:  initializes FitnessTracker app
    public void initialize() {
        scan = new Scanner(System.in);
        scan.useDelimiter("\n");

    }

}
