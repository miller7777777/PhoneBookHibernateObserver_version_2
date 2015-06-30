package mnz.miller777;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;


/**
 * Created by mille_000 on 10.06.2015.
 */
public class DAOlog {

    private SessionFactory factory;

    public DAOlog() {factory = new Configuration().configure().buildSessionFactory();}

    public void addEvent(Event event){

        Session session = null;

        try {
            session = factory.openSession();
            session.beginTransaction();
            session.save(event);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }

        }

    }

    public ArrayList<Event> showLog(){

        Session session = null;
        ArrayList<Event> logList = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            logList = (ArrayList<Event>)session.createCriteria(Event.class).list();


        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return logList;
    }

    public void close(){
        factory.close();

    }
}
