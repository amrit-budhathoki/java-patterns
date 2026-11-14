// Title: Adapter Pattern - JSON to XML Configuration Converter

// The Adapter pattern allows incompatible interfaces to work together by wrapping
// one interface in another. Use it when you need to integrate legacy or third-party
// code with a different interface than what your system expects.

import java.util.*;

public class JsonToXmlConfigAdapter {
    public static void main(String[] args) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.put("database", "postgres");
        jsonConfig.put("port", "5432");
        
        ConfigAdapter adapter = new ConfigAdapter(jsonConfig);
        
        System.out.println("Adapted output:");
        System.out.println(adapter.toXml());
    }
}

interface ConfigReader {
    String toXml();
}

class JsonConfig {
    private Map<String, String> data = new HashMap<>();
    
    public void put(String key, String value) {
        data.put(key, value);
    }
    
    public Map<String, String> getAll() {
        return data;
    }
}

class ConfigAdapter implements ConfigReader {
    private JsonConfig jsonConfig;
    
    public ConfigAdapter(JsonConfig jsonConfig) {
        this.jsonConfig = jsonConfig;
    }
    
    @Override
    public String toXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\"?>\n");
        xml.append("<config>\n");
        
        for (Map.Entry<String, String> entry : jsonConfig.getAll().entrySet()) {
            xml.append("  <").append(entry.getKey()).append(">");
            xml.append(entry.getValue());
            xml.append("</").append(entry.getKey()).append(">\n");
        }
        
        xml.append("</config>");
        return xml.toString();
    }
}
