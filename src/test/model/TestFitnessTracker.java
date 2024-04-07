package model;

import java.util.ArrayList;

import model.exceptions.WorkoutNameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestFitnessTracker {
    private FitnessTracker ft1 = new FitnessTracker();
    private FitnessTracker ft2 = new FitnessTracker();
    private ArrayList<Workout> workoutList1 = new ArrayList<Workout>();
    private ArrayList<Workout> workoutList2 = new ArrayList<Workout>();


    private Exercise ex1;
    private Exercise ex2;
    private Workout wk1;
    private Workout wk11;
    private Workout wk2;
    private ArrayList<Exercise> exerciseList1 = new ArrayList<Exercise>();
    private ArrayList<Exercise> exerciseList2 = new ArrayList<Exercise>();


    @BeforeEach
    void setup() throws WorkoutNameAlreadyExistsException {
        ex1 = new Exercise("Chest Press", 4, 10, 185);
        ex2 = new Exercise("Squat", 5, 8, 275);
        wk1 = new Workout("Push Day", "2024-02-04", 175);
        wk11 = new Workout("Push Day", "2024-02-07", 177);
        wk2 = new Workout("Pull Day", "2024-02-05", 173);
        wk1.addExercise(ex1);
        wk2.addExercise(ex1);
        wk2.addExercise(ex2);
        exerciseList1.add(ex1);
        exerciseList2.add(ex1);
        exerciseList2.add(ex2);

        ft1.addWorkout(wk1);
        ft2.addWorkout(wk1);
        ft2.addWorkout(wk2);

        workoutList1.add(wk1);
        workoutList2.add(wk1);
        workoutList2.add(wk2);
    }


    @Test
    public void testGetExerciseList() {
        assertEquals(workoutList1, ft1.getWorkoutList());
        assertEquals(workoutList2, ft2.getWorkoutList());
    }

    @Test
    public void testGetListSize() {
        assertEquals(1, ft1.getListSize());
        assertEquals(2, ft2.getListSize());
    }

    @Test
    public void testRemoveWorkoutByIndex() {
        assertEquals(2, ft2.getListSize());
        ft2.removeWorkout(1);
        assertEquals(workoutList1, ft2.getWorkoutList());
    }
    @Test
    public void testRemoveWorkoutByWorkout() {
        ft2.removeWorkout(wk2);
        assertEquals(workoutList1, ft2.getWorkoutList());
    }

    @Test
    public void testAddExerciseNoException() throws WorkoutNameAlreadyExistsException {
        try {
            ft1.addWorkout(wk2);
            assertEquals(workoutList2, ft2.getWorkoutList());
        } catch (WorkoutNameAlreadyExistsException e) {
            fail ("WorkoutNameAlreadyExistsException thrown when should not have thrown");
        }
    }

    @Test
    public void testAddExerciseYesException() throws WorkoutNameAlreadyExistsException {
        try {
            ft1.addWorkout(wk2);
            ft1.addWorkout(wk11);
        } catch (WorkoutNameAlreadyExistsException e) {
            // do nothing
        }
        assertEquals(workoutList2, ft1.getWorkoutList());
    }
}
