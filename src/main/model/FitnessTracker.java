package model;

import model.exceptions.WorkoutNameAlreadyExistsException;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

// Represents a FitnessTracker, having a list of completed workouts that have the name, date,
// and your weight at the time completed.
public class FitnessTracker {

    private ArrayList<Workout> workoutList;


     // EFFECTS: Constructs new fitness tracker with an empty Workout list.
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
    // EFFECTS:  removes specified workout from FitnessTracker workout list
    public void removeWorkout(int index) {
        this.workoutList.remove(index);
        EventLog.getInstance().logEvent(new Event("Removed Workout: "
                + this.workoutList.get(index).getName() + " - " + this.workoutList.get(index).getDate()));
    }

    // REQUIRES: getListSize() > 0
    // MODIFIES: this
    // EFFECTS:  removes specified workout from FitnessTracker workout list
    public void removeWorkout(Workout workout) {
        this.workoutList.remove(workout);
        EventLog.getInstance().logEvent(new Event("Removed Workout: "
                + workout.getName() + " - " + workout.getDate()));
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
            EventLog.getInstance().logEvent(new Event("Duplicate Workout Name Detected: "
                    + workout.getName()));
            throw new WorkoutNameAlreadyExistsException();

        } else {
            this.workoutList.add(workout);
            EventLog.getInstance().logEvent(new Event("Added Workout: "
                    + workout.getName() + " - " + workout.getDate()));
        }
    }


    // Modeled from CPSC210/JsonSerializationDemo
    // EFFECTS: Writes FitnessTracker to Json
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("workoutList", workoutListToJson());

        EventLog.getInstance().logEvent(new Event("Write FitnessTracker to Json"));

        return jsonObject;
    }

    // Modeled from CPSC210/JsonSerializationDemo
    // EFFECTS: returns the workouts in the workoutList as a json array
    public JSONArray workoutListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Workout wk : workoutList) {
            jsonArray.put(wk.toJson());
        }

        EventLog.getInstance().logEvent(new Event("Write WorkoutList to Json array"));

        return jsonArray;
    }

    // EFFECTS: prints out all logs in EventLog to console
    public void printLogs() {
        EventLog eventLog = EventLog.getInstance();
        for (Event event : eventLog) {
            System.out.println(event.toString());
        }

    }




}