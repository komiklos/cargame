package com.company;

import java.io.IOException;
import java.util.*;
import com.codecool.termlib.*;

class Car extends Thread {

    private int terminalWidth;
    private int terminalHeight;
    private Terminal t = new Terminal();
    public boolean isAlive = true;


    public Car(int termW, int termH){
        this.terminalWidth = termW;
        this.terminalHeight = termH;
    }

    private void init(){
        printCar(5+Game.carStatus);
    }

    @Override
    public void run(){
       char wheel = '-';
       init();
       while(this.isAlive){
           char input = tryToRead();
           if (input != 'k') {
              jump();
           }
       }
    }

    public void jump(){
        for(int i =0;i<3;i++){
            clearCar(5+Game.carStatus);
            Game.carStatus += 1;
            printCar(5+Game.carStatus);
            sleep(200);
        }
        for(int i =0;i<3;i++){
            clearCar(5+Game.carStatus);
            Game.carStatus -= 1;
            printCar(5+Game.carStatus);
            sleep(200);
        }
    }

    private void printCar(int topOfCar){
      String[] car = new String[4];
      car[0] = "  ______";
      car[1] = " /|_||_\\`.__";
      car[2] = "(   _    _ _\\";
      car[3] = "=`-(o)--(o)-'";
      for(int i = 0; i < 4; i++){
        t.moveTo(this.terminalHeight-topOfCar+i,this.terminalWidth/4);
        System.out.print(car[i]);
      }
    }

    private void clearCar(int topOfCar){
        for(int i = 0; i < 4; i++){
          t.moveTo(this.terminalHeight-topOfCar+i,this.terminalWidth);
          t.eraseLine();
        }
    }
    private Character tryToRead() {
      try {
          if (System.in.available() > 0) {
              return (char)System.in.read();
          }
      }
      catch (IOException e) {
        // System.err.println("error" + e.getMessage());
      }
      return 'k';
    }

    private void sleep(int sleepTime){
        try{
            Thread.sleep(sleepTime);
        }
        catch(InterruptedException ex){
          Thread.currentThread().interrupt();
        }
    }

    public void carDeath(){
        clearCar(5);
        String[] car = new String[4];
        car[0] = "  ___ ___";
        car[1] = " /|_| |_\\` ._";
        car[2] = "=`-(o)---'\\ // \\ (0)";

        for(int i = 0; i < 3; i++){
          t.moveTo(this.terminalHeight-4+i,this.terminalWidth/4);
          System.out.print(car[i]);
        }

        t.moveTo(this.terminalHeight / 2 -2 , this.terminalWidth /2 -1);
        System.out.print("YOU CRASHED");

    }
}
