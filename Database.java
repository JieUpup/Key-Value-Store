package utils;

import java.util.HashMap;

/*
 *  Database support three operations
 *  PUT    <KEY> <VALUE>
 *  GET    <KEY>
 * Return the value when everything went well.
 *  DELETE <KEY>
 */
public class Database {
    private HashMap<String, String> store;
    private Logger logger;

    public Database(Logger logger) {
        this.store = new HashMap<>();
        this.logger = logger;
    }

    /*
     *  Return success when everything went well
     */
    public String put(String key, String value) {
        store.put(key, value);

        return "Success";
    }

    public String delete(String key) {
        store.remove(key);

        return "Success";
    }

    public String get(String key) {
        if (!store.containsKey(key)) {
            logger.debugLog("Key: " + key + " not existed");

            return "Failure";
        }

        String ret = store.get(key);

        return ret;
    }
}
