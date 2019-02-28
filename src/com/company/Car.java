package com.company;

import java.io.IOException;
import java.util.*;
import com.codecool.termlib.*;

class Car extends Thread {

    int terminalWidth;
    int terminalHeight;
    private Terminal t = new Terminal();

    public Car(int termW, int termH){
        this.terminalWidth = termW;
        this.terminalHeight = termH;
    }

    private void init(){
        printCar(0,5);
    }


    @Override
    public void run(){
       char wheel = '-';

       init();
       while(true){
          char input = tryToRead();
          if (input != 'k') {
            jump();
          }
           try{
              Thread.sleep(1000);
           }catch(InterruptedException e){

           }
           switch(wheel){
             case '-':
               moveWheel('\\');
               wheel = '\\';
               break;
             case '\\':
               moveWheel('|');
               wheel = '|';
               break;
             case '|':
               moveWheel('/');
               wheel = '/';
               break;
             case '/':
               moveWheel('-');
               wheel = '-';
               break;

           }

       }
    }

    private void jump(){
        System.out.print("JUMP");
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

    private void moveWheel(char wheel){
      t.moveTo(this.terminalHeight-2,this.terminalWidth/4 + 4);
      System.out.print(wheel);
      t.moveTo(this.terminalHeight-2,this.terminalWidth/4 + 9);
      System.out.print(wheel);
    }

    private void printCar(int status, int topOfCar){
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

    private void clearCar(){

    }

}
