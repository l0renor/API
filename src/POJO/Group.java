package POJO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {

    private List<String> lights = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public Group() {
    }

    /**
     * @param lights The lights in the group.
     */
    public Group(List<String> lights) {
        super();
        this.lights = lights;
    }

    public List<String> getLights() {
        return lights;
    }

    public void setLights(List<String> lights) {
        this.lights = lights;
    }

    public Group withLights(List<String> lights) {
        this.lights = lights;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Group withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}