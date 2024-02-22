package persistence;

import model.Exercise;
import model.FitnessTracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Workout;
import model.exceptions.WorkoutNameAlreadyExistsException;
import org.json.*;

// Modeled from CPSC210/JsonSerializationDemo
// Represents a file reader that will read json data in file to FitnessTracker
public class JsonReader {
    private String sourceFile;

    // EFFECTS: constructs the JsonReader to read data from the sourceFile
    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;

    }

    // Modeled from CPSC210/JsonSerializationDemo
    // EFFECTS: reads FitnessTracker from sourceFile and then returns it
    // throws IOException if an error occurs when reading file
    public FitnessTracker read() throws IOException, WorkoutNameAlreadyExistsException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parseFitnessTracker(jsonObject);
    }


    // Modeled from CPSC210/JsonSerializationDemo
    // EFFECTS: reads the source file as a string then returns it
    private String readFile(String sourceFile) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(sourceFile), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();

    }

    // Modeled from CPSC210/JsonSerializationDemo
    // EFFECTS: parses FitnessTracker from the JSON object and then returns it
    private FitnessTracker parseFitnessTracker(JSONObject jsonObject) throws WorkoutNameAlreadyExistsException {
        FitnessTracker ft = new FitnessTracker();
        addWorkouts(ft, jsonObject);
        return ft;
    }

    // Modeled from CPSC210/JsonSerializationDemo
    // MODIFIES: ft
    // EFFECTS:  parses workouts from JSON object and adds it to FitnessTracker
    private void addWorkouts(FitnessTracker ft, JSONObject jsonObject) throws WorkoutNameAlreadyExistsException {
        JSONArray jsonArray = new JSONArray("workoutList");
        for (Object json : jsonArray) {
            JSONObject nextWorkout = (JSONObject) json;
            addWorkoutToFitnessTracker(ft, nextWorkout);
        }
    }

    // MODIFIES: ft
    // EFFECTS: adds parsed workout from JSON object to FitnessTracker workoutList
    private void addWorkoutToFitnessTracker(FitnessTracker ft, JSONObject jsonObject)
            throws WorkoutNameAlreadyExistsException {
        String name = jsonObject.getString("name");
        String date = jsonObject.getString("date");
        String weight = jsonObject.getString("weight");
        double weightDouble = Double.parseDouble(weight);
        Workout workout = new Workout(name, date, weightDouble);
        addExercises(workout, jsonObject);

        ft.addWorkout(workout);


    }

    // MODIFIES: wk
    // EFFECTS:  parses exercises from JSON object and adds it to Workout
    private void addExercises(Workout wk, JSONObject jsonObject) throws WorkoutNameAlreadyExistsException {
        JSONArray jsonArray = new JSONArray("exerciseList");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExerciseToWorkout(wk, nextExercise);
        }
    }

    // MODIFIES: ft
    // EFFECTS: adds parsed workout from JSON object to FitnessTracker workoutList
    private void addExerciseToWorkout(Workout wk, JSONObject jsonObject)
            throws WorkoutNameAlreadyExistsException {
        String name = jsonObject.getString("name");
        String sets = jsonObject.getString("sets");
        String reps = jsonObject.getString("reps");
        String weight = jsonObject.getString("weight");
        double weightDouble = Double.parseDouble(weight);
        double setsDouble = Double.parseDouble(sets);
        int repsInt = Integer.parseInt(reps);
        Exercise exercise = new Exercise(name, setsDouble, repsInt, weightDouble);
        wk.addExercise(exercise);


    }
}