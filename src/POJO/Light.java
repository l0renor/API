package POJO;
import java.util.HashMap;
import java.util.Map;

public class Light {

    private boolean on;
    private int hue;
    private int sat;
    private int bri;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Light() {
    }

    /**
     *
     * @param bri Brightness.
     * @param sat Saturation.
     * @param hue Hue Color.
     * @param on Status.
     */
    public Light(boolean on, int hue, int sat, int bri) {
        super();
        this.on = on;
        this.hue = hue;
        this.sat = sat;
        this.bri = bri;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public Light withOn(boolean on) {
        this.on = on;
        return this;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public Light withHue(int hue) {
        this.hue = hue;
        return this;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public Light withSat(int sat) {
        this.sat = sat;
        return this;
    }

    public int getBri() {
        return bri;
    }

    public void setBri(int bri) {
        this.bri = bri;
    }

    public Light withBri(int bri) {
        this.bri = bri;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Light withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
