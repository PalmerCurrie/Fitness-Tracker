package persistence;

import model.FitnessTracker;
import model.Workout;
import model.Exercise;
import model.exceptions.WorkoutNameAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonReader {


    @Test
    void testWriterInvalidFileName() {
        JsonReader reader = new JsonReader("./data/filedoesnotexist.json");
        try {
            FitnessTracker fitnessTracker = reader.read();
            fail("fails, IOException expected since file does not exist");
        } catch (IOException e) {
            // does not fail because it should catch the IOException
        } catch (WorkoutNameAlreadyExistsException e) {
            fail("exception should not be thrown");
        }
    }

    @Test
    void testWriterWithEmptyFitnessTracker() {
        JsonReader reader = new JsonReader("./data/testWriterWithEmptyFitnessTracker.json");
        try {
            FitnessTracker fitnessTracker = reader.read();
            assertEquals(0, fitnessTracker.getListSize());
        } catch (IOException e) {
            fail("IOException should not be thrown.");
        } catch (WorkoutNameAlreadyExistsException e) {
            fail("exception should not be thrown.");
        }
    }

    @Test
    void testWriterNormalFitnessTracker() {
        JsonReader reader = new JsonReader("./data/testWriterNormalFitnessTracker.json");
        try {
            FitnessTracker fitnessTracker = reader.read();
            assertEquals(2, fitnessTracker.getListSize());
            assertEquals("Chest", fitnessTracker.getWorkoutList().get(0).getName());
            assertEquals("March 3", fitnessTracker.getWorkoutList().get(0).getDate());
            assertEquals(200, fitnessTracker.getWorkoutList().get(0).getWeight());
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
