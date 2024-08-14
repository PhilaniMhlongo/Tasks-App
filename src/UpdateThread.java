public class UpdateThread extends Thread {

    private WordRecord wordRecord;
    private int maxY;

    public UpdateThread (WordRecord wordRecord, int maximum) {
        this.wordRecord = wordRecord;
        maxY = maximum;
    } // End of UpdateThread Constructor

    public void run () {
        if (!wordRecord.dropped()) {
            // Increment the position.
            // Allocate thread.
            CaughtThread caughtThread = new CaughtThread (wordRecord, WordApp.textFromUser);
            // Initiate thread.
            caughtThread.start();
            // Wait for thread.
            try {
                caughtThread.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }

            try {
                // Creating a pause depending falling speed
                // so that the movement is recognizable.
                Thread.sleep(100);
            }
            catch (InterruptedException e0) {
                System.out.println(e0);
            }
        }
        else {
            wordRecord.resetWord();
            WordApp.score.missedWord();
            WordApp.missed.setText("Missed:" + WordApp.score.getMissed() + "    ");
        }
    } // End of run
} // End of UpdateThread
