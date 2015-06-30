package mnz.miller777;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by mille_000 on 10.06.2015.
 */
public class LogGui implements Observer {

    protected JTextArea textArea;



    ArrayList<Event> log;
    JScrollPane scrollBar;
    private static int logGuiCount = 0; //счетчик открытых окон;
    int logGuiNumber;
    String s = "";
    DAOlog daOlog;
    PhoneBook book;

    public LogGui(ArrayList<Event> log, PhoneBook book) {
        this.log = log;
        this.book = book;
        logGuiNumber = logGuiCount;
        logGuiCount++;

    }

    public void build(){


        s = "Log " + logGuiNumber;

        JFrame frame = new JFrame(s);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(200, 200, 600, 400);

        textArea = new JTextArea();
        scrollBar = new JScrollPane(textArea);


        frame.getContentPane().add(BorderLayout.CENTER, scrollBar);
        frame.setVisible(true);

        outputLog(book.getLog());
        textArea.setCaretPosition(textArea.getText().length());//Устанавливаем курсор на последнюю строку;



    }

    public void outputLog(ArrayList<Event> log){
        String s = "";
//        log = daOlog.showLog();
        log = book.getLog();



        for (int i = 0; i < log.size(); i++) {

            String s1 = "  " + log.get(i).toString() + "\n";
            s = s + s1;
        }

        textArea.setText(s);
        textArea.setCaretPosition(textArea.getText().length());//Устанавливаем курсор на последнюю строку;

    }


    @Override
    public void update(Observable o, Object arg) {



        outputLog(book.getLog());




    }
}
