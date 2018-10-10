package uk.gov.dvla.osl.commons.metadata;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class ThreadLocalMetadata {

    private static ThreadLocal<HashMap<String, String>> metadata = new ThreadLocal<HashMap<String, String>>(){
        @Override
        protected HashMap<String, String> initialValue() {
            return new HashMap<>();
        }
    };

    public static String get(String key){
        return metadata.get().get(key);
    }

    public static ThreadLocal<HashMap<String, String>> get(){ return metadata; }

    public static void set(String key, String value){
        metadata.get().put(key, value);
    }

    public static void clear(){ metadata.remove(); }

    public static void set(HashMap<String, String> map){ metadata.set(map); }

    public static void append(Map<String, String> map) { map.forEach((key, val) -> metadata.get().put(key, val)); }

    public static Map<String, String> getStartingWith(String prefix) {

        Map<String, String> matches = metadata.get().entrySet()
                .stream()
                .filter(p -> p.getKey().startsWith(prefix))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

        return matches;

    }

}
