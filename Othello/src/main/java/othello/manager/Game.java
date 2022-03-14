/*
 * Code created by B19DCCN117 - Vuong Dinh Doanh
 * (c) All rights reserved
 */
package othello.manager;

import othello.global.State;
import static othello.global.General.*;
import static othello.manager.Handler.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import othello.entity.Board;
import othello.entity.Bot;

/**
 *
 * @author Admin
 */

public class Game extends Canvas implements Runnable {
    
    public static final int WIDTH = grid_size*board_col, HEIGHT = grid_size*board_row;
    public static final String TITLE = "OTHELLO";
    public static State gameState = State.Game;
    
    private Thread thread;
    public static Thread botThread;
    public static boolean running = false;
    
    private Handler handler;
    
    public static void clearObjects(){
        for (LinkedList<GameObject> list : OBJECT) {
            list.clear();
        }
    }
    
    public void initObjects() {
        new Bot(new Board(0, 0));
    }
    
    public Game() {
        handler = new Handler();
        new Window(WIDTH, HEIGHT, TITLE, this);
    }
    
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop() {
        try{
            thread.join();
            running = false;
        } catch(Exception e){
            e.printStackTrace();
        }
        running = false;
    }
    
    @Override
    public void run(){
        //Init
        handler.initSortingLayer();
        handler.loadImages();
        handler.loadFonts();
        initObjects();
        MouseInput mouseInput = new MouseInput();
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        this.setFocusable(true);
        //Running...
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            if (isBotThinking) {
                botThinkingTime += (now-lastTime)/1000000000f;
            }
            lastTime = now;
            while(delta >= 1){
                handler.tick();
                delta--;
            }
            if(running){
                render();
            }
            frames++;
            if(System.currentTimeMillis()-timer > 1000){
                timer += 1000;
                //if (!pause) System.out.println("FPS: " + frames + " OBJS: " + handler.numberOfObject);
                frames = 0;
            }
        }
        stop();
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(new Color(0, 144, 103));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        handler.render(g);
        g.dispose();
        bs.show();
    }
    
    public static void main(String args[]){
        new Game();
    }
    
}
