public enum Color {
    WHITE("{ \"on\": true, \"hue\": 0, \"sat\": 0, \"bri\": 200 }"),
    ORANGE("{ \"on\": true, \"hue\": 4500, \"sat\": 230, \"bri\": 200 }"),
    RED("{ \"on\": true, \"hue\": 0, \"sat\": 254, \"bri\": 254 }"),
    OFF("{ \"on\": true, \"bri\": 0 }");

    private String jsonMessage;

    Color(String jsonMessage) {
        this.jsonMessage = jsonMessage;
    }

    public String getJsonMessage() {
        return jsonMessage;
    }
}
