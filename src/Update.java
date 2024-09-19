public class Update extends Thread {
    private WordRecord wordRecord;
    private int maxY;

    /**
     * Update Constructor
     * @param wordRecord
     * @param maxY
     */
    public Update(WordRecord wordRecord,int maxY){
        this.wordRecord=wordRecord;
        this.maxY=maxY;
    }

    @Override
    public void run() {

        if (!wordRecord.dropped()) {

            Caught caught = new Caught(wordRecord, WordApp.textUser);
            caught.start();
            try {
                caught.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        else {
            wordRecord.resetWord();
            WordApp.score.missedWord();
            WordApp.missed.setText("Missed:" + WordApp.score.getMissed() + "    ");
        }

    }
}
