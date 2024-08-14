public class ScoreUpdater implements Runnable {
    // Has a reference to both the model and the view
    private Model m;
    private View v;

    /* Constructor */
    ScoreUpdater(Model m,View v){
        this.m=m;
        this.v=v;
    }
    @Override
    public void run() {
        // Always update the score
        while (true) {
            updateScore();

            // If the total number of words has exceeded the desired amount
            if ((m.getScore().getCaught() + m.getScore().getMissed() + m.getNoWords()) > (m.getTotalWords() - 1)) {
                // Tell the model to finish up
                Model.done = true;
                // Tell the WordRecord class to stop creating new words
                WordRecord.finished = true;
            }
        }
    }
}