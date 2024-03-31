package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


// Represents a workout having a name, data, body weight (in pounds), and a list of Exercises completed
public class Workout {

    private String name;                        // workout's name
    private String date;                        // date of the Workout
    private double weight;                      // user's current body weight at time of workout
    private ArrayList<Exercise> exerciseList;   // list of exercises completed during workout



    /* REQUIRES: name, date have non-zero length
     * EFFECTS: name of workout is set to name, date of workout is set to date
     */
    public Workout(String name, String date, double weight) {
        this.name = name;
        this.date = date;
        this.weight = weight;
        exerciseList = new ArrayList<Exercise>();
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
        this.date = date;
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

        EventLog.getInstance().logEvent(new Event("Removed Exercise: "
                + this.exerciseList.get(index).getName()));

    }

    // MODIFIES: this
    // EFFECTS: adds exercise to exerciseList
    public void addExercise(Exercise exercise) {
        this.exerciseList.add(exercise);

        EventLog.getInstance().logEvent(new Event("Added Exercise: "
                + exercise.getName()));
    }



    // Modeled from CPSC210/JsonSerializationDemo
    // EFFECTS: Writes Workout to Json
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("date", date);
        jsonObject.put("weight", weight);
        jsonObject.put("exerciseList", exerciseListToJson());

        EventLog.getInstance().logEvent(new Event("Write Workout to Json"));

        return jsonObject;
    }

    // Modeled from CPSC210/JsonSerializationDemo
    // EFFECTS: returns the exercises in the exerciseList as a json array
    public JSONArray exerciseListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise ex : exerciseList) {
            jsonArray.put(ex.toJson());
        }

        EventLog.getInstance().logEvent(new Event("Write ExerciseList to Json array"));

        return jsonArray;
    }


}