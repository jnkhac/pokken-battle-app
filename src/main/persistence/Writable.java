package persistence;

import org.json.JSONObject;

// CODE REFERENCES:
// JsonSerializationDemo - Writable
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents an interface for classes that are writable
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
