import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;


import java.util.Scanner;
import java.util.concurrent.*;
//model is separate from the view.

public class WordApp{
//shared variables
	static int noWords=4;
	static int totalWords;
   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;
	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually
	static WordRecord[] words;
	static volatile boolean done;  //must be volatile
	static 	Score score = new Score();
	static WordPanel w;
	static JFrame frame;
	static JPanel graphic;
	static JPanel txt;
	static JLabel caught,missed,scr;
	static final JTextField textEntry=new JTextField("",20);
	static JPanel button;
	static JButton startButton,endButton,quitButton;
	static String textUser="";

	/**
	 * Setup Graphical User Interface
	 * @param frameX
	 * @param frameY
	 * @param yLimit
	 */
	public static void setupGUI(int frameX,int frameY,int yLimit) {
		// Frame init and dimensions
    	JFrame frame = new JFrame("WordGame"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameX, frameY);
      graphic = new JPanel();
      graphic.setLayout(new BoxLayout(graphic, BoxLayout.PAGE_AXIS));
      graphic.setSize(frameX,frameY);
      w = new WordPanel(words,yLimit);
	  w.setSize(frameX,yLimit+100);
	   graphic.add(w);
	   txt = new JPanel();
      txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
       caught =new JLabel("Caught: " + score.getCaught() + "    ");
       missed =new JLabel("Missed:" + score.getMissed()+ "    ");
       scr =new JLabel("Score:" + score.getScore()+ "    ");
      txt.add(caught);
	   txt.add(missed);
	   txt.add(scr);
    
	    //[snip]

		textEntry.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) {
				textUser = textEntry.getText();
				textEntry.setText("");
				textEntry.requestFocus();
			}
		});

		//[snip]

	   txt.add(textEntry);
	   txt.setMaximumSize( txt.getPreferredSize() );
	   graphic.add(txt);
	    
	    button = new JPanel();
      button.setLayout(new BoxLayout(button, BoxLayout.LINE_AXIS));
	  startButton = new JButton("Start");;
		
			// add the listener to the jbutton to handle the "pressed" event
		startButton.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
		      //[snip]
			   done = false;
			   //Start WordPanel thread - for redrawing animation
			   w.start();
			   caught.setText("Caught: " + score.getCaught() + "    ");
			   missed.setText("Missed:" + score.getMissed()+ "    ");
			   scr.setText("Score:" + score.getScore()+ "    ");
			   //[snip]
		      textEntry.requestFocus();  //return focus to the text entry field
		   }
		});
		 endButton = new JButton("End");;
			
				// add the listener to the jbutton to handle the "pressed" event
		endButton.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
			   done = true;
			   w.stop();
		      //[snip]
		   }
		});
		quitButton = new JButton("Quit");
		// add the listener to the jbutton to handle the "pressed" event
		quitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		
		button.add(startButton);
		button.add(endButton);
		button.add(quitButton);
    	graphic.add(button);
		frame.setLocationRelativeTo(null);  // Center window on screen.
      frame.add(graphic); //add contents to window
      frame.setContentPane(graphic);
       	//frame.pack();  // don't do this - packs it into small space
      frame.setVisible(true);
	}

	/**
	 * Convert words from file to Array
	 * @param filename
	 * @return
	 */
   public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();
			//System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				//System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
	        System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;
	}

	/**
	 * The main class for I/O interaction
	 * @param args
	 */
	public static void main(String[] args) {
    	
		//deal with command line arguments
		totalWords=Integer.parseInt(args[0]);  //total words to fall
		noWords=Integer.parseInt(args[1]); // total words falling at any point
		assert(totalWords>=noWords); // this could be done more neatly
		String[] tmpDict=getDictFromFile(args[2]); //file of words
		if (tmpDict!=null)
			dict= new WordDictionary(tmpDict);
		
		WordRecord.dict=dict; //set the class dictionary for the words.
		
		words = new WordRecord[noWords];  //shared array of current words
		
		//[snip]
		
		//setupGUI(frameX, frameY, yLimit);
    	//Start WordPanel thread - for redrawing animation

		//int x_inc=(int)frameX/noWords;
		int x_inc=frameX/noWords;
	  	//initialize shared array of current words

		for (int i=0;i<noWords;i++) {
			words[i]=new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
		}
		setupGUI(frameX,frameY,yLimit);
	}


	}

