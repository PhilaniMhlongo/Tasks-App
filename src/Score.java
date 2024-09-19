import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Score Constructor
 */
public class Score {
	private AtomicInteger missedWords;
	private AtomicInteger caughtWords;
	private AtomicInteger gameScore;
	private  AtomicInteger highScore;
	
	Score() {
		missedWords=new AtomicInteger(0);
		caughtWords=new AtomicInteger(0);
		gameScore=new AtomicInteger(0);
	}
		
	// all getters and setters must be synchronized

	/**
	 * getMissed
	 * @return
	 */
	synchronized public int getMissed() {
		return missedWords.get();
	}

	/**
	 * getCaught
	 * @return
	 */
	synchronized public int getCaught() {
		return caughtWords.get();
	}

	/**
	 * getTotal
	 * @return
	 */
	synchronized public int getTotal() {
		return (missedWords.get()+caughtWords.get());
	}

	/**
	 * getScore
	 * @return
	 */
	public int getScore() {
		return gameScore.get();
	}

	/**
	 * missedWord
	 */
	synchronized public void missedWord() {
		missedWords.getAndIncrement();
	}

	/**
	 * caughtWord
	 * @param length
	 */
	synchronized public void caughtWord(int length) {
		caughtWords.getAndIncrement();
		gameScore.getAndAdd(length);
	}

	/**
	 * updateHighScore
	 */
	synchronized public void updateHighScore(){
		if(gameScore.get()>highScore.get()){
			highScore=gameScore;
		}
	}

	/**
	 * reset Hight Score
	 */
	synchronized public void resetHightScore(){
		highScore=new AtomicInteger(0);
	}

	/**
	 * reset Score
	 */
	synchronized public void resetScore() {
		caughtWords=new AtomicInteger(0);
		missedWords=new AtomicInteger(0);
		gameScore=new AtomicInteger(0);
	}

	/**
	 * toString
	 * @return
	 */
	public String toString(){
		return "Caught: "+caughtWords+"\nMissed: "+missedWords+"\nScore"+gameScore+"\nHigh Score: "+highScore;
	}
}
