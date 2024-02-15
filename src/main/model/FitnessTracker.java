package model;

import model.exceptions.WorkoutNameAlreadyExistsException;

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

    // REQUIRES: Workout name is not already in Workout List
    // MODIFIES: this
    // EFFECTS:  adds workout to workoutList
    public void addWorkout(Workout workout) throws WorkoutNameAlreadyExistsException {
        boolean workoutNameAlreadyExists = false;
        for (int i = 0; i < this.getListSize(); i++) {
            if (workout.getName() == this.workoutList.get(i).getName()) {
                workoutNameAlreadyExists = true;
            }
        }
        if (workoutNameAlreadyExists) {
            throw new WorkoutNameAlreadyExistsException();
        } else {
            this.workoutList.add(workout);
        }
    }

}