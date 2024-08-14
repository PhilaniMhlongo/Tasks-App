public class CaughtThread extends Thread {

    private WordRecord word;
    private String entry;

    public CaughtThread (WordRecord word, String entry) {
        this.word = word;
        this.entry = entry;
    } // End of CaughtThread Constructor

    public void run () {
        if (!word.matchWord(entry)){
            word.drop(word.getSpeed()/100);
        }
        else {
            WordApp.score.caughtWord(entry.length());
            WordApp.caught.setText("Caught: " + WordApp.score.getCaught() + "    ");
            WordApp.scr.setText("Score:" + WordApp.score.getScore()+ "    ");
        }
    } // End of run
} // End of CaughtThread
