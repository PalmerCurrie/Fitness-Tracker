package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Represents an exercise having a name, number of sets, number of reps, and weight (in pounds (lbs))
public class Exercise {

    private String name;
    private double sets;
    private int reps;
    private double weight;

    /* REQUIRES: name has a non-zero length
     * EFFECTS: name of exercise is set to name, sets is set to sets,
     * reps set to reps, weight set to weight
     */
    public Exercise(String name, double sets, int reps, double weight) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSets() {
        return this.sets;
    }

    public void setSets(double sets) {
        this.sets = sets;
    }

    public int getReps() {
        return this.reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    // Modeled from CPSC210/JsonSerializationDemo
    // EFFECTS: Writes Exercise to Json
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("sets", sets);
        jsonObject.put("reps", reps);
        jsonObject.put("weight", weight);
        return jsonObject;
    }

}
