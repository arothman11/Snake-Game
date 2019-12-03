package project;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Snake {

    private int width;
    private int height;
    private LinkedList<Coordinates> snake;
    private Coordinates apple;

    public Snake(int width, int height) {
        this.width = width;
        this.height = height;
        snake = new LinkedList<>();
        apple = null;
    }


	private static int left = KeyEvent.VK_LEFT;
	private static int right = KeyEvent.VK_RIGHT;
	private static int up = KeyEvent.VK_UP;
	private static int down = KeyEvent.VK_DOWN;
    private int direction;



	private static int random(int n){
	    int random = (int)(Math.random()*n);
	    return random;
    }


	public void draw() {
        StdDraw.clear();
        //board
        for (int i = 0; i < width; i++) {
            double iDub = (double) i / width;
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.line(iDub, 0, iDub, 1);
        }
        for (int j = 0; j < height; j++) {
            double jDub = (double) j / width;
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.line(0, jDub, 1, jDub);

        }
        //apple
        StdDraw.setPenColor(Color.RED);
        double radius = 1.0 / (2.0 * (Math.min(width, height)));
        StdDraw.filledCircle(((double) apple.getX() + 0.5) / (double) width, ((double) apple.getY() + 0.5) / (double) height, radius);

        //snake
        StdDraw.setPenColor(Color.GREEN);
        for (Coordinates d : snake) {
            StdDraw.filledRectangle(((double) d.getX() + 0.5) / (double) width, ((double) d.getY() + 0.5) / (double) height, 1.0 / (2.0 * width), 1.0 / (2.0 * height));
        }
        StdDraw.show();
    }


    private void generatingNewApple(){
        boolean checkApple = false;
        Coordinates newApple = null;

        while(!checkApple){
            int appleX = random(width);
            int appleY = random(height);
            newApple = new Coordinates(appleX, appleY);

            if (!snake.contains(newApple)){
                checkApple = true;
            }
        }
        apple = newApple;
    }


	public void runGame() {

	    StdDraw.clear();


        //create snake
        int startX = random(width-2)+2;
        int startY = random(height);

        Coordinates a = new Coordinates(startX, startY);
        Coordinates b = new Coordinates(startX+1, startY);
        Coordinates c = new Coordinates(startX+2, startY);
        direction = right;

        snake.addFirst(a);
        snake.addLast(b);
        snake.addLast(c);

        generatingNewApple();

        boolean gameOn = true;
        while (gameOn) {

           //checking if ate apple

            boolean eatApple = false;
                if (snake.getFirst().equals(apple)){
                    generatingNewApple();
                }
                else{
                    snake.removeLast();
                }


            //set direction based off key
            if (StdDraw.isKeyPressed(left) && direction!=right) {
                direction = left;
            }
            if (StdDraw.isKeyPressed(right)&& direction!=left) {
                direction = right;
            }
            if (StdDraw.isKeyPressed(up)&& direction!=down) {
                direction = up;
            }
            if (StdDraw.isKeyPressed(down)&& direction!=up) {
                direction = down;
            }

            //check if hit edge
            if (snake.getFirst().getX() <= 0 || snake.getFirst().getX() >= width-1) {
                gameOn = false;
                break;
            }
            if (snake.getFirst().getY() <= 0 || snake.getFirst().getY() >= height-1) {
                gameOn = false;
                break;
            }

            //check if hit itself
            int count = 0;
            for (Coordinates k : snake) {
                if (k.equals(snake.getFirst()) ) {
                    count++;
                }
            }
            if (count > 1) {
                gameOn = false;
                break;
            }



            //adds new head
            if (direction == up) {
                Coordinates updateUp = new Coordinates(snake.getFirst().getX(), snake.getFirst().getY() + 1);
                snake.addFirst(updateUp);
            }
            if (direction == down) {
                Coordinates updateDown = new Coordinates(snake.getFirst().getX(), snake.getFirst().getY() - 1);
                snake.addFirst(updateDown);
            }
            if (direction == left) {
                Coordinates updateLeft = new Coordinates(snake.getFirst().getX() - 1, snake.getFirst().getY());
                snake.addFirst(updateLeft);
            }
            if (direction == right) {
                Coordinates updateRight = new Coordinates(snake.getFirst().getX() + 1, snake.getFirst().getY());
                snake.addFirst(updateRight);
            }

            //draw apples and snake
            draw();
            StdDraw.show(200);
        }
        if (gameOn == false){

            System.out.println("You Lose!");
        }
    }
    

    public static void main(String[] args) {
	    Snake snake1 = new Snake(20,20);
	    snake1.runGame();

	}
	
}