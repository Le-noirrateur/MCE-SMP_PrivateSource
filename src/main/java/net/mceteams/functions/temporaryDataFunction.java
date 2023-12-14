package net.mceteams.functions;

import java.util.HashMap;
import java.util.Map;

public class temporaryDataFunction {
    private static final Map<String, Object> temporaryData = new HashMap<>();

    public static void setTemporaryData(String key, Object value) {
        temporaryData.put(key, value);
    }

    public static Object getTemporaryData(String key) {
        return temporaryData.get(key);
    }

    public static void removeTemporaryData(String key) {
        temporaryData.remove(key);
    }
}