import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WordPanel extends JPanel implements Runnable {
    public static volatile boolean done;
    private WordRecord[] words;
    private int noWords;
    private int maxY;

    private Thread animator;

    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.clearRect(0, 0, width, height);
        g.setColor(Color.red);
        g.fillRect(0, maxY-10, width, height);

        g.setColor(Color.black);
        g.setFont(new Font("Helvetica", Font.PLAIN, 26));
        // Draw the words.
        // Animation must be added
        if (!done) {
            for (int i=0; i < noWords; i++){
                g.drawString(words[i].getWord(), words[i].getX(), words[i].getY());
            }
        }
    }

    WordPanel(WordRecord[] wds, int maxY) {
        this.words = new WordRecord[wds.length];
        for (int i = 0; i < wds.length; i++) {
            this.words[i] = new WordRecord(wds[i]);
        }

        noWords = words.length;
        done = false;
        this.maxY = maxY;
    } // End of WordPanel Constructor

    public void reset() {for (WordRecord w : words) {w.resetWord();} WordApp.score.resetScore();} // Reset game from beginning

    public void start() {
        done = false;
        reset();
        if (animator == null) {
            animator = new Thread(this);
            animator.start();
        }
    } // End of start

    public void stop() {
        done = true;
        if (animator != null) {
            animator.stop();
            animator = null;
        }
        repaint();
    } // End of stop

    public boolean endGame () {
        if (WordApp.score.getTotal() >= WordApp.totalWords) {
            return false;
        }
        else {
            return true;
        }
    } // End of endGame

    public void run() {
        // Code to animate this.
        // Game loop.
        while(WordApp.done || endGame()) {
            // Allocate threads.
            UpdateThread[] updateThreads = new UpdateThread[noWords];
            for (int i = 0; i < noWords; i++) {
                updateThreads[i] = new UpdateThread(words[i], maxY);
                // Initiate threads.
                updateThreads[i].start();
                // Wait for threads.
                try {
                    updateThreads[i].join();
                }
                catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                // Repaint.
                repaint();
            }
        }

        System.out.println("Game Over!");
        System.out.println("Caught: " + WordApp.score.getCaught());
        System.out.println("Missed:" + WordApp.score.getMissed());
        System.out.println("Score:" + WordApp.score.getScore());
        System.exit(0);

    } // End of run
} // End of WordPanel



