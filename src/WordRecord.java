

public class WordRecord {
	private String text;
	private  int x;
	private int y;
	private int maxY;
	private boolean dropped;
	
	private int fallingSpeed;
	private static int maxWait=1500;
	private static int minWait=100;

	public static WordDictionary dict;


	/**
	 * Word Record Empty Constructor
	 */
	WordRecord() {
		text="";
		x=0;
		y=0;	
		maxY=300;
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
	}

	/**
	 * Word Record Constructor
	 * @param text
	 */
	WordRecord(String text) {
		this();
		this.text=text;
	}
	
	WordRecord(String text,int x, int maxY) {
		this(text);
		this.x=x;
		this.maxY=maxY;
	}

	/**
	 * Copy Constructor
	 * @param original
	 */
	WordRecord(WordRecord original) {
		if (original == null) {System.out.println("Fatal error."); System.exit(0);}
		text = original.text;
		x = original.x;
		y = original.y;
		maxY = original.maxY;
		dropped = original.dropped;
		fallingSpeed = original.fallingSpeed;
	}
	
// all getters and setters must be synchronized

	/**
	 * synchronized setY
	 * @param y
	 */
	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true;
		}
		this.y=y;
	}

	/**
	 * synchronized setX
	 * @param x
	 */
	public synchronized  void setX(int x) {
		this.x=x;
	}

	/**
	 * synchronized setWord
	 * @param text
	 */
	public synchronized  void setWord(String text) {
		this.text=text;
	}

	/**
	 * synchronized getWord
	 * @return
	 */
	public synchronized  String getWord() {
		return text;
	}

	/**
	 * synchronized getX
	 * @return
	 */
	public synchronized  int getX() {
		return x;
	}

	/**
	 * synchronized getY
	 * @return
	 */
	public synchronized  int getY() {
		return y;
	}

	/**
	 * synchronized getSpeed
	 * @return
	 */
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	/**
	 * synchronized setPos
	 * @param x
	 * @param y
	 */
	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}

	/**
	 * synchronized resetPos
	 */
	public synchronized void resetPos() {
		setY(0);
	}

	/**
	 * synchronized resetWord
	 */
	public synchronized void resetWord() {
		resetPos();
		text=dict.getNewWord();
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " falling speed = " + getSpeed());

	}

	/**
	 * synchronized matchWord
	 * @param typedText
	 * @return
	 */
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.text)) {
			resetWord();
			return true;
		}
		else
			return false;
	}

	/**
	 * synchronized drop
	 * @param inc
	 */
	public synchronized  void drop(int inc) {
		setY(y+inc);
	}

	/**
	 * synchronized dropped
	 * @return
	 */
	public synchronized  boolean dropped() {
		return dropped;
	}

	/**
	 * toString
	 * @return
	 */
	public String toString() {
		return ("text = " + text + "\n" +
				" x = " + x + "\n" +
				" y = " + y + "\n" +
				" maxY = " + maxY + "\n" +
				" dropped = " + dropped + "\n" +
				" fallingSpeed = " + fallingSpeed + "\n");
	}

}
