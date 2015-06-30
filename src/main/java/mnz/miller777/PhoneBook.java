package mnz.miller777;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

/**
 * Created by mille_000 on 07.06.2015.
 */
public class PhoneBook extends Observable {

    private BufferedReader reader;
    private Contact contact;
    private String answer;
    private int choose;
    private ArrayList<Contact> list;
    private ArrayList<Event> log;
    private Contact c;
    private DAO dao;
    private DAOlog daoLog;
    private Event event;


    public void start() {

        dao = new DAO();
        daoLog = new DAOlog();

        reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            System.out.println("");
            System.out.println("Phonebook menue:");
            System.out.println("1 - add new contact");
            System.out.println("2 - delete contact by name");
            System.out.println("3 - show all contact");
            System.out.println("4 - find contact by name");
            System.out.println("5 - edit contact by name");
            System.out.println("6 - show log");
            System.out.println("7 - exit");
            System.out.println("");
            System.out.println("Input command:");

            try {
                answer = reader.readLine();
                choose = Integer.parseInt(answer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (choose) {

                case 1: {
                    addContact();
                    break;
                }
                case 2: {
                    deleteContact();
                    break;
                }
                case 3: {
                    showAll();
                    break;
                }
                case 4: {
                    findContact();
                    break;
                }
                case 5: {
                    editByName();
                    break;
                }
                case 6: {
                    showLog();
                    break;
                }
                case 7: {
                    close();
                    break;
                }
                default: {
                    System.out.println("Wrong input!");
                    break;
                }
            }

        }

    }

    private void showLog() {


        log = daoLog.showLog();
        LogGui window = new LogGui(log, this);

        addObserver(window);
        window.build();
        String s = "Log showed.";
        logNotify(s);
    }

    private void close() {
        try {
            reader.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editByName() {

        System.out.println("Edit contact by name");
        try {
            System.out.println("Input name:");

            String name = reader.readLine();
            Contact c = dao.getContactByName(name);
            if (c == null) {
                System.out.println("Contact not found!");
                return;
            }
            System.out.println("Contact:");
            System.out.println(c.toString());
            System.out.println("");
            System.out.println("Input new name:");
            String name1 = reader.readLine();


            System.out.println("Input new phone:");
            String phone1 = reader.readLine();

            System.out.println("Input new email:");
            String email1 = reader.readLine();

            c.setName(name1);
            c.setPhone(phone1);
            c.setEmail(email1);
            dao.updateContact(c, c.getId());


            String s = "Contact id= " + c.getId() + " updated.";

            logNotify(s);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Contact findContact() {

        System.out.println("Find contact by name");
        try {
            System.out.println("Input name:");

            String name = reader.readLine();
            Contact c = dao.getContactByName(name);

            System.out.println("Contact:");
            System.out.println(c.toString());
            System.out.println("");
            String s = c.toString() + " finded by name";

            logNotify(s);


        } catch (IOException e) {
            e.printStackTrace();
        }


        return c;
    }

    private Contact findContact(String name) {


        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getName().equals(name)) {
                Contact c = list.get(i);
                System.out.println("Contact:");
                System.out.println(c.toString());
                System.out.println("");
                String s = c.toString() + " finded by name";
                Event event = new Event(time(), s);
                log.add(event);
                super.setChanged();
                notifyObservers(log);
                return c;


            } else {
                System.out.println("Contact not finding");
                c = null;
            }


        }


        return c;

    }

    private void showAll() {

        System.out.println("");
        System.out.println("All Contacts:");
        System.out.println("");
        list = null;
        list = dao.getAllContacts();

        for (int i = 0; i < list.size(); i++) {

            System.out.println(list.get(i).toString());

        }

        System.out.println("");

        String s = "All contacts showed.";

        logNotify(s);
    }

    private void deleteContact() {


        System.out.println("Delete contact:");
        System.out.println("Enter name:");
        String name = null;
        try {
            name = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Contact c = dao.getContactByName(name);
        dao.deleteContact(name);
        System.out.println("Contact:");
        System.out.println(c.toString() + " deleted.");
        System.out.println("");
        String s = c.toString() + " removed.";
        System.out.println(s);

        logNotify(s);


    }

    public ArrayList<Event> getLog() {
        return log;
    }

    private void addContact() {


        System.out.println("Add new contact");
        try {
            System.out.println("Input name:");
            String name = reader.readLine();
            System.out.println("Input phone:");
            String phone = reader.readLine();
            System.out.println("Input email:");
            String email = reader.readLine();

            Contact c = new Contact(name, phone, email);

            dao.addContact(c);
            String s = c.toString() + " added.";

            logNotify(s);

            String message = "New contact (name = " + name + ") added.";


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void logNotify(String s) {
        Event event = new Event(time(), s);
        daoLog.addEvent(event);
        log = daoLog.showLog();
        super.setChanged();
        notifyObservers(log);
    }

    private String time() {

        long curTime = System.currentTimeMillis();
        String curStringDate = new SimpleDateFormat("dd.MM.yyyy   HH:mm:ss").format(curTime);
        return curStringDate;
    }
}
