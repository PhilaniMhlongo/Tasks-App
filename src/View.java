import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
    static int frameX=1000;
    static int frameY=600;
    static int yLimit=480;
    static volatile boolean done;
    static JFrame frame;
    static JPanel graphic;
    static JPanel txt;
    static JLabel caught, missed, scr;

    static final JTextField textEntry = new JTextField("", 20);
    static JPanel b;
    static JButton startB, endB, quitB;

    static String textFromUser = "";
    public static void setupGUI(int frameX,int frameY,int yLimit) {
        // Frame init and dimensions
        frame = new JFrame("WordGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameX, frameY);

        graphic = new JPanel();
        graphic.setLayout(new BoxLayout(graphic, BoxLayout.PAGE_AXIS));
        graphic.setSize(frameX, frameY);

        w = new WordPanel(words, yLimit);
        w.setSize(frameX, yLimit+100);
        graphic.add(w);

        txt = new JPanel();
        txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS));
        caught =new JLabel("Caught: " + score.getCaught() + "    ");
        missed =new JLabel("Missed:" + score.getMissed()+ "    ");
        scr =new JLabel("Score:" + score.getScore()+ "    ");
        txt.add(caught);
        txt.add(missed);
        txt.add(scr);

        textEntry.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt) {
                textFromUser = textEntry.getText();
                textEntry.setText("");
                textEntry.requestFocus();
            }
        });

        txt.add(textEntry);
        txt.setMaximumSize(txt.getPreferredSize());
        graphic.add(txt);

        b = new JPanel();
        b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));

        startB = new JButton("Start");
        // add the listener to the jbutton to handle the "pressed" event
        startB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                done = false;
                //Start WordPanel thread - for redrawing animation
                w.start();
                caught.setText("Caught: " + score.getCaught() + "    ");
                missed.setText("Missed:" + score.getMissed()+ "    ");
                scr.setText("Score:" + score.getScore()+ "    ");
                textEntry.requestFocus();  //return focus to the text entry field
            }
        });

        endB = new JButton("End");
        // add the listener to the jbutton to handle the "pressed" event
        endB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                done = true;
                w.stop();
            }
        });

        quitB = new JButton("Quit");
        // add the listener to the jbutton to handle the "pressed" event
        quitB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //add jbutton to jpanel
        b.add(startB);
        b.add(endB);
        b.add(quitB);

        graphic.add(b);

        frame.setLocationRelativeTo(null);  // Center window on screen.
        frame.add(graphic); //add contents to window
        frame.setContentPane(graphic);
        //frame.pack();  // don't do this - packs it into small space
        frame.setVisible(true);
    }

}
