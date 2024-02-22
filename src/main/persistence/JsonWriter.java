package persistence;

import model.FitnessTracker;
import org.json.JSONObject;

import java.io.*;

// Modeled from CPSC210/JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String fileDestination;

    // EFFECTS: constructs JsonWriter to write data to the destination file
    public JsonWriter(String fileDestination) {
        this.fileDestination = fileDestination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer to write data,
    //          throws FileNotFoundException if file is not found
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(fileDestination));
    }

    // MODIFIES: this
    // EFFECTS: writes data as json representation of FitnessTracker data to json file
    public void write(FitnessTracker ft) {
        JSONObject json = ft.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS:  closes the writer, making it unable to be used until opened again
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS:  writes string to file
    public void saveToFile(String json) {
        writer.print(json);

    }




}
