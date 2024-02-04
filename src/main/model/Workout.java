package model;

import java.util.ArrayList;


// Represents a workout having a name, data, body weight (in pounds), and a list of Exercises completed
public class Workout {

    private String name;                        // workout's name
    private String date;                        // date of the Workout
    private ArrayList<Exercise> exerciseList;   // list of exercises completed during workout
    private double weight;


    /* REQUIRES: name, date have non-zero length
     * EFFECTS: name of workout is set to name, date of workout is set to date
     */
    public Workout(String name, String date, double weight) {
        this.name = name;
        this.date = date;
        this.weight = weight;
        exerciseList = null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.name = date;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ArrayList<Exercise> getExerciseList() {
        return this.exerciseList;
    }

    public int getListSize() {
        return this.exerciseList.size();
    }

    // REQUIRES: getListSize() > 0 and index < getListSize()
    // MODIFIES: this
    // EFFECTS: removes exercise at index from exerciseList
    public void removeExercise(int index) {
        this.exerciseList.remove(index);
    }

    // MODIFIES: this
    // EFFECTS: adds exercise to exerciseList
    public void addExercise(Exercise exercise) {
        this.exerciseList.add(exercise);
    }

}



// REQUIRES:
// MODIFIES:
// EFFECTS:
