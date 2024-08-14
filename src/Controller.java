import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Controller {
    private Model model ;
    private View view ;
    private ScoreUpdater su ;
    Thread scoreUpdateThread;
    Thread refreshViewThread;
    Thread waitForGameOverThread ;
    private boolean waitingForGameOver = false ;
    public void initView () {
        /* Start refreshView and updateScore threads */
    }

    public void initController () {
        /* Give the WordRecord objects to the View */
        /* Add Ac t io nL is t en er s to buttons , textfield , and level selector . */
    }

    /**
     * Start the word threads
     *Start waiting for the game to finish on a new thread
     */
    public void startGame () {
        waitingForGameOver= false;
        reset();
        if (animator == null) {
            animator = new Thread(this);
            animator.start();
        }
    }

    /**
     * Stops the WordRecord threads and resets score
     */
    public boolean endGame () {
        if (WordApp.score.getTotal() >= WordApp.totalWords) {
            return false;
        }
        else {
            return true;
        }
        /* Stops the WordRecord threads and resets score */
    }

    private void enterText () {
        /* Action Listener for TextBox -- checks input against WordRecords , and acts accordingly */
    }

    /**
     * Loads words from dictionary file into dictionary object in the Model
     * @param filename
     * @return
     */
    public String [] getDictFromFile ( String filename ) {
        String [] dictStr = null;
        try {
            Scanner dictReader = new Scanner(new FileInputStream(filename));
            int dictLength = dictReader.nextInt();
            //System.out.println("read '" + dictLength+"'");

            dictStr=new String[dictLength];
            for (int i=0; i < dictLength; i++) {
                dictStr[i] = new String(dictReader.next());
                //System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
            }
            dictReader.close();
        } catch (IOException e) {
            System.err.println("Problem reading file " + filename + " default dictionary will be used");
        }
        return dictStr;
    }

    public void createWords () {
        /* Creates the word threads from a sample of the dictionary */
    }

}
