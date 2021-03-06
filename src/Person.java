import java.time.Duration;
import java.time.LocalTime;

/**
 * @author Fabian Reinold
 * Objekt, um die einzelnen Personen übersichtlicher zu managen.
 */
public class Person {
    private String name;
    private String workplace;
    private LocalTime startingTime;
    private String meanOfTransport;
    private boolean atHome;
    private int lightNumber;


    public Person(String name, String workplace, String startingTime, String meanOfTransport, int lightNumber) {
        this.name = name;
        this.workplace = workplace;
        this.startingTime = LocalTime.parse(startingTime);
        this.meanOfTransport = meanOfTransport;
        atHome = true;
        this.lightNumber = lightNumber;
    }

    public String getName() {
        return name;
    }

    public String getWorkplace() {
        return workplace;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public String getMeanOfTransport() {
        return meanOfTransport;
    }

    public int getSecondsUntilLeave(int secondsOfTravel) {
        Duration secondsUntilLeave = Duration.between(LocalTime.now(), startingTime.minusSeconds((long) secondsOfTravel));
        return (int) secondsUntilLeave.getSeconds();
    }
    
    public void leave() {
        atHome = false;
    }

    public boolean isHome() {
        return atHome;
    }

    public int getLightNumber() {
        return lightNumber;
    }
}
