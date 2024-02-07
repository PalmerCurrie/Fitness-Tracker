package model;

import java.util.ArrayList;


public class FitnessTracker {

    private ArrayList<Workout> workoutList;

    public FitnessTracker() {
        workoutList = new ArrayList<Workout>();

    }



    public ArrayList<Workout> getWorkoutList() {
        return this.workoutList;
    }

    public int getListSize() {
        return this.workoutList.size();
    }

    // REQUIRES: getListSize() > 0 and index < getListSize()
    // MODIFIES: this
    // EFFECTS:  removes exercise at index from exerciseList
    public void removeWorkout(int index) {
        this.workoutList.remove(index);
    }

    // MODIFIES: this
    // EFFECTS:  adds workout to workoutList
    public void addWorkout(Workout workout) {
        this.workoutList.add(workout);
    }


}