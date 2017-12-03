import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
//Bsp args: Agnes-Pockels-Bogen+21+80992+München 8:15 bicycling Ingolstädter+Str.+38+80992+München 10:00 driving Bunzlauer+Str.+8+80992+München 8:30 transit

/**
 * @author Leon Lukas, Fabian Reinold
 */
public class Main {
    public static final String BASEGOOGLE = "https://maps.googleapis.com/maps/api/distancematrix/json?language=de&units=metric";
    private static final String BROWSERURL = "https://www.google.de/maps/dir/Lothstraße+6,+80335+München/";


    public static void main(String[] args) throws InterruptedException, URISyntaxException, IOException {
        HueController c = new HueController();

        List<Person> persons = new ArrayList<>();

        /*
         * Fügt die Personen mit Daten von der Konsole ein.
         */
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 1; i < 4; i++) {
            System.out.println("Namen von Person "+i+" angeben");
            String name = bufferedReader.readLine();
            System.out.println("Arbeitsort von "+name+" angeben");
            String workplace = bufferedReader.readLine().replace(" ", "+");
            System.out.println("Arbeitsbeginn von "+name+" angeben");
            String start = bufferedReader.readLine();
            System.out.println("Fortbewegungsmittel von "+name+" angeben, entweder 'driving', 'bicycling' oder 'transit'");
            String mot = bufferedReader.readLine();
            Person p = new Person(name, workplace, start, mot, i);
            persons.add(p);
        }

        /*
        Person leonLukas = new Person("Leon Lukas", "Agnes-Pockels-Bogen+21+80992+München", "11:10", "bicycling",1);
        Person paulaPuenktlich = new Person("Paula Pünktlich", "Ingolstädter+Str.+38+80992+München", "11:20", "driving",2);
        Person lotharLate = new Person("Lothar Late", "Bunzlauer+Str.+8+80992+München", "11:30", "transit",3);
        */

        /*
         * Thread zum Prüfen, ob Person geht.
         */
        Thread atHome = new Thread(new Runnable() { public void run() {
            while (true) {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Gib deinen Namen ein wenn du gehst!!");
                    String s = br.readLine();
                    for (Person p : persons) {
                        if (p.getName().equals(s)) {
                            p.leave();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }});
        atHome.start();

        /*
         * Öffnet die Routen im Browser.
         */
        if (Desktop.isDesktopSupported()) {
            for (Person p : persons) {
                String uri = BROWSERURL + p.getWorkplace();
                Desktop.getDesktop().browse(new URI(uri));
            }
        }

        /*
         * Schleife, die prüft, wann der Benutzer gehen muss
         */
        while (true) {
            for (Person p : persons) {
                if(p.isHome()) {
                    int secondsToTravel = GoogleController.getTravelduration(p.getWorkplace(), p.getMeanOfTransport());
                    int secondsUntilLeave = p.getSecondsUntilLeave(secondsToTravel);
                    if (secondsUntilLeave > 120) {
                        c.changeLight(p.getLightNumber(), Color.WHITE);
                    } else if (secondsUntilLeave > 60) {
                        c.changeLight(p.getLightNumber(), Color.ORANGE);
                    } else if (secondsUntilLeave > 0) {
                        c.changeLight(p.getLightNumber(), Color.RED);
                    } else {
                        while(p.isHome()) {
                            c.changeLight(0, Color.RED);
                            Thread.sleep(1000);
                            c.changeLight(0, Color.OFF);
                            Thread.sleep(1000);
                        }
                    }
                } else {
                    c.changeLight(p.getLightNumber(), Color.OFF);
                }
            }
            TimeUnit.SECONDS.sleep(5);
        }

    }
}

    /*public static void main(String []args ) throws InterruptedException, URISyntaxException, IOException {
            List<String > ort = new LinkedList<>();
            List<String> zeit = new LinkedList<>();
            List<String> verkehrsmittel = new LinkedList<>();
            int[] durations = new int[3]; //Zeit für Arbeitsweg in Sekunden
            int[] timeToLeave = new int[3];//Zeit bis man gehen muss in Sekunden

            for (int i = 0;i<9;i = i +3){
                ort.add(args[i]);
                zeit.add(args[i+1]);
                verkehrsmittel.add(args[i+2]);
            }
            if(Desktop.isDesktopSupported())
            {
                for (int i=0;i<3;i++) {
                    String uri = BROWSERURL + ort.get(i);
                    Desktop.getDesktop().browse(new URI(uri));
                }
            }
            while(true){
                for (int i = 0;i<3;i++) {
                    durations[i] = GoogleController.getTravelduration(ort.get(i), verkehrsmittel.get(i));
                }
                for (int i = 0;i<3;i++){                    //berechnet die Zeit in Sekunden zwischen gewünschter Ankunftszeit und aktueller Uhrzeit
                    DateTime dt = new DateTime();
                    dt = dt.dayOfMonth().roundFloorCopy();
                    dt = dt.plusHours(Integer.parseInt(zeit.get(i).split(":")[0]));
                    dt = dt.plusMinutes(Integer.parseInt(zeit.get(i).split(":")[1]));
                    Period diff = new Period( new DateTime(),dt);
                    timeToLeave[i]=diff.toStandardSeconds().getSeconds() - durations[i]; // Abfahrtszeit = Zeit bis Arbeitsbeginn - dauer Arbeitsweg

                }

                System.out.println("User 1 has to leave in " + timeToLeave[0]);
                System.out.println("User 2 has to leave in " +timeToLeave[1]);
                System.out.println("User 3 has to leave in " +timeToLeave[2]);

                //Hier Code zur steuerung der lampen
                TimeUnit.SECONDS.sleep(5);
            }
        /*for (int i = 0;i<9;i = i + 3){
            ort.add(args[i]);
            zeit.add(args[i+1]);
            verkehrsmittel.add(args[i+2]);
        }
        while (true){
            for (int i = 0; i < 3; i++) {
                durations[i] = GoogleController.getTravelduration(ort.get(i), verkehrsmittel.get(i));
            }
            System.out.println(durations[0]);
            System.out.println(durations[1]);
            System.out.println(durations[2]);
            TimeUnit.SECONDS.sleep(5);
        } */