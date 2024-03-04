package model;

import model.Exercise;

import java.io.IOException;
import java.util.ArrayList;

import model.exceptions.WorkoutNameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class TestWorkout {

    private Exercise ex1;
    private Exercise ex2;
    private Workout wk1;
    private Workout wk2;
    private ArrayList<Exercise> exerciseList1 = new ArrayList<Exercise>();
    private ArrayList<Exercise> exerciseList2 = new ArrayList<Exercise>();

    @BeforeEach
    void setup() {
        ex1 = new Exercise("Chest Press", 4, 10, 185);
        ex2 = new Exercise("Squat", 5, 8, 275);
        wk1 = new Workout("Push Day", "2024-02-04", 175);
        wk2 = new Workout("Pull Day", "2024-02-05", 173);
        wk1.addExercise(ex1);
        wk2.addExercise(ex1);
        wk2.addExercise(ex2);
        exerciseList1.add(ex1);
        exerciseList2.add(ex1);
        exerciseList2.add(ex2);
    }



    @Test
    public void testGetName() {
        assertEquals("Push Day", wk1.getName());
        assertEquals("Pull Day", wk2.getName());
    }

    @Test
    public void testSetName() {
        assertEquals("Push Day", wk1.getName());
        wk1.setName("Pull Day (Hard)");
        assertEquals("Pull Day (Hard)", wk1.getName());
    }

    @Test
    public void testGetDate() {
        assertEquals("2024-02-04", wk1.getDate());
        assertEquals("2024-02-05", wk2.getDate());
    }

    @Test
    public void testSetDate() {
        assertEquals("2024-02-04", wk1.getDate());
        assertEquals("2024-02-05", wk2.getDate());
        wk1.setDate("2024-02-05");
        wk2.setDate("2024-02-06");
        assertEquals("2024-02-05", wk1.getDate());
        assertEquals("2024-02-06", wk2.getDate());
    }

    @Test
    public void testGetWeight() {
        assertEquals(175, wk1.getWeight());
        assertEquals(173, wk2.getWeight());
    }

    @Test
    public void testSetWeight() {
        assertEquals(175, wk1.getWeight());
        assertEquals(173, wk2.getWeight());
        wk1.setWeight(180);
        wk2.setWeight(178);
        assertEquals(180, wk1.getWeight());
        assertEquals(178, wk2.getWeight());
    }

    @Test
    public void testGetExerciseList() {
        assertEquals(exerciseList1, wk1.getExerciseList());
        assertEquals(exerciseList2, wk2.getExerciseList());
    }

    @Test
    public void testGetListSize() {
        assertEquals(1, wk1.getListSize());
        assertEquals(2, wk2.getListSize());
    }

    @Test
    public void testRemoveExercise() {
        wk2.removeExercise(1);
        assertEquals(exerciseList1, wk2.getExerciseList());
    }

    @Test
    public void testAddExercise() {
        wk1.addExercise(ex2);
        assertEquals(exerciseList2, wk1.getExerciseList());
    }

    @Test
    void testToJson() {
        wk1.exerciseListToJson();
        JsonReader reader = new JsonReader("./data/testWriterNormalFitnessTracker.json");
        try {
            FitnessTracker fitnessTracker = reader.read();
            assertEquals(2, fitnessTracker.getListSize());
            assertEquals("Chest", fitnessTracker.getWorkoutList().get(0).getName());
            assertEquals("March 3", fitnessTracker.getWorkoutList().get(0).getDate());
            assertEquals(200, fitnessTracker.getWorkoutList().get(0).getWeight());

            Workout workout = fitnessTracker.getWorkoutList().get(0);

            assertEquals("Bench", workout.getExerciseList().get(0).getName());
            assertEquals(4, workout.getExerciseList().get(0).getSets());
            assertEquals(4, workout.getExerciseList().get(0).getReps());
            assertEquals(20, workout.getExerciseList().get(0).getWeight());

            assertEquals("Pull", fitnessTracker.getWorkoutList().get(1).getName());
            assertEquals("March 4", fitnessTracker.getWorkoutList().get(1).getDate());
            assertEquals(201, fitnessTracker.getWorkoutList().get(1).getWeight());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (WorkoutNameAlreadyExistsException e) {
            fail("exception should not be thrown.");
        }
    }



}


