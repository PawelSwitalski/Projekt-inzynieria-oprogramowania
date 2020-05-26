package sample;

import javafx.scene.layout.Pane;

import java.util.concurrent.Semaphore;

public class Wygaszanie implements Runnable{
    private Semaphore semaphore;
    private Pane hideButtonPane;
    private Thread thread;

    public Wygaszanie(Semaphore semaphore, Pane hideButtonPane) {
        this.semaphore = semaphore;
        this.hideButtonPane = hideButtonPane;
        this.thread = new Thread(this);
        this.thread.start();
    }



    @Override
    public void run() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hideButtonPane.setVisible(false);
        System.out.println("Funkcja wygaszenieHiddenButtonPane dziala");
        semaphore.release();
    }



}
