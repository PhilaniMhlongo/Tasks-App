public class Caught extends Thread{
    private WordRecord word;
    private String entry;

    /**
     * Class Constructor
     * @param word
     * @param entry
     */
    public Caught (WordRecord word, String entry) {

        this.word = word;
        this.entry = entry;
    }

    /**
     * void run method for increment speed or Score
     */
    public void run () {
        if (!word.matchWord(entry)){
            word.drop(word.getSpeed()/100);
        }
        else {
            WordApp.score.caughtWord(entry.length());
            WordApp.caught.setText("Caught: " + WordApp.score.getCaught() + "    ");
            WordApp.scr.setText("Score:" + WordApp.score.getScore()+ "    ");
        }
    }
}
