import java.time.Duration;
import java.time.LocalTime;

public class Person {
    private String name;
    private String workplace;
    private LocalTime startingTime;
    private String meanOfTransport;
    private boolean athome;

    public Person(String name, String workplace, String startingTime, String meanOfTransport) {
        this.name = name;
        this.workplace = workplace;
        this.startingTime = LocalTime.parse(startingTime);
        this.meanOfTransport = meanOfTransport;
        athome = true;
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
        athome = false;
    }

    public boolean isHome() {
        return athome;
    }
}
