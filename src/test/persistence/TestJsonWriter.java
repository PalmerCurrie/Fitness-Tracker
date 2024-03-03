package persistence;

import model.FitnessTracker;
import model.Workout;
import model.exceptions.WorkoutNameAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonWriter {


    @Test
    void testWriterInvalidFileName() {
        try {
            FitnessTracker fitnessTracker = new FitnessTracker();
            JsonWriter writer = new JsonWriter("./data/s\0illegalfilenamewontcompile.json");
            writer.open();
            fail("fails, IOException expected since illegal file name");
        } catch (IOException e) {
            // does not fail because it should catch the IOException
        }
    }

    @Test
    void testWriterWithEmptyFitnessTracker() {
        try {
            FitnessTracker fitnessTracker = new FitnessTracker();
            JsonWriter writer = new JsonWriter("./data/testWriterWithEmptyFitnessTracker.json");
            writer.open();
            writer.write(fitnessTracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterWithEmptyFitnessTracker.json");
            fitnessTracker = reader.read();
            assertEquals(0, fitnessTracker.getListSize());
        } catch (IOException e) {
            fail("IOException should not be thrown.");
        } catch (WorkoutNameAlreadyExistsException e) {
            fail("exception should not be thrown.");
        }
    }

    @Test
    void testWriterNormalFitnessTracker() {
        try {
            FitnessTracker fitnessTracker = new FitnessTracker();
            fitnessTracker.addWorkout(new Workout("Chest", "March 3", 200));
            fitnessTracker.addWorkout(new Workout("Pull", "March 4", 201));
            JsonWriter writer = new JsonWriter("./data/testWriterNormalFitnessTracker.json");
            writer.open();
            writer.write(fitnessTracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalFitnessTracker.json");
            fitnessTracker = reader.read();
            List<Workout> workoutList = fitnessTracker.getWorkoutList();
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
