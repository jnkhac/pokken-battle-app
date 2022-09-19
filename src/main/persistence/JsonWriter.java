package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import model.Event;
import model.EventLog;
import model.PokemonTeam;
import org.json.JSONObject;

// CODE REFERENCES:
// JsonSerializationDemo - JsonWriter
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a writer that writes a JSON representation of pokemon team and score to a file
public class JsonWriter {
    private static final int INDENT_FACTOR = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a json writer to write data to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the json writer; throws FileNotFoundException if the
    //          destination file cannot be opened for writing data
    public void openWriter() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes the JSON representation of pokemon team to file
    public void write(PokemonTeam pt) {
        JSONObject json = pt.toJson();
        saveToFile(json.toString(INDENT_FACTOR));
    }

    // MODIFIES: this
    // EFFECTS: closes the json writer
    public void closeWriter() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
        EventLog.getInstance().logEvent(new Event("Saved pokemon team to: " + destination));
    }
}
