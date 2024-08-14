public class Model {
    int noWords ;
    // Words on the screen at a point in time
    int totalWords ;
// Total words that will fall
    // Dictionary of words
    WordDictionary dict = new WordDictionary () ;
    // Actual array of word ( threads )
    WordRecord [] words ;
    // Shared indicator of program status
    static volatile boolean done ; // Volatile to ensure freshness
    // Object to keep track of scores
    Score score = new Score () ;

    public int getNoWords () {...}
    public void setNoWords ( int noWords ) {...}
    public int getTotalWords () {...}
    public void setTotalWords ( int totalWords ) {...}
    public WordDictionary getDict () {...}
    public void setDict ( WordDictionary dict ) {...}
    public WordRecord [] getWords () {...}
    public void setWords ( WordRecord [] words ) {...}

    synchronized public Score getScore () {...}

}
