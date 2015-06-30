package mnz.miller777;

import javax.persistence.*;


/**
 * Created by mille_000 on 10.06.2015.
 */


@Entity
@Table(name = "log")
public class Event {

    private int id;
    //    private static int count = 0;
    private String time;
    private String eventItem;

    public Event() {
    }

    public Event(String time, String eventItem) {
        this.time = time;
        this.eventItem = eventItem;

    }


    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getEventItem() {
        return eventItem;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setEventItem(String eventItem) {
        this.eventItem = eventItem;
    }


    @Override
    public String toString() {

        return id + " \t" + time + " \t" + eventItem;
    }


}
