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

public class TestExercise {

    private Exercise ex1;
    private Exercise ex2;

    @BeforeEach
    void setup() {
        ex1 = new Exercise("Chest Press", 4, 10, 185);
        ex2 = new Exercise("Squat", 5, 8, 275);

    }

    @Test
    public void testExercise() {
        assertEquals("Chest Press", ex1.getName());
        assertEquals(4, ex1.getSets());
        assertEquals(10, ex1.getReps());
        assertEquals(185, ex1.getWeight());

    }

    @Test
    public void testGetName() {
        assertEquals("Chest Press", ex1.getName());
    }

    @Test
    public void testSetName() {
        assertEquals("Chest Press", ex1.getName());
        ex1.setName("Shoulder Press");
        assertEquals("Shoulder Press", ex1.getName());
    }

    @Test
    public void getSets() {
        assertEquals(4, ex1.getSets());
        assertEquals(5, ex2.getSets());
    }

    @Test
    public void testSetSets() {
        assertEquals(4, ex1.getSets());
        ex1.setSets(3);
        assertEquals(3, ex1.getSets());
    }

    @Test
    public void getReps() {
        assertEquals(10, ex1.getReps());
        assertEquals(8, ex2.getReps());
    }

    @Test
    public void testSetReps() {
        assertEquals(10, ex1.getReps());
        ex1.setReps(12);
        assertEquals(12, ex1.getReps());
    }

    @Test
    public void testGetWeight() {
        assertEquals(185, ex1.getWeight());
        assertEquals(275, ex2.getWeight());
    }

    @Test
    public void testSetWeight() {
        assertEquals(185, ex1.getWeight());
        assertEquals(275, ex2.getWeight());
        ex1.setWeight(225);
        ex2.setWeight(315);
        assertEquals(225, ex1.getWeight());
        assertEquals(315, ex2.getWeight());
    }

    @Test
        void testToJson() {
        ex1.toJson();
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


