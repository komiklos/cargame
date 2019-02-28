package com.company;

import java.util.*;
import java.util.concurrent.TimeUnit;
import com.codecool.termlib.*;
import java.awt.*;
import java.io.IOException;

public class Game extends Thread{

  private int[] terminalSize = new int[2];
  private int terminalHeight;
  private int terminalWidth;
  private int score;
  private Terminal t = new Terminal();
  private String road = "";
  private int time = 100;
  public static int carStatus = 0;

  public void init(){

    t.clearScreen();
    this.terminalSize = t.getTerminalSize();
    this.terminalHeight = this.terminalSize[0];
    this.score = 0;
    this.terminalWidth = this.terminalSize[1];
    printTitle();
    t.moveTo(terminalHeight,1);
    System.out.print(generateLowerRoad());
    generateRoad();
    printUpperRoad();

  }

  @Override
  public void run(){
    init();
    Car c = new Car(this.terminalWidth, this.terminalHeight);
    c.start();
    int pieceCounter = 0;
    int rangeOfRandom = 71; //have to be an odd number, mégegy helyen bekéri
    int remainingHoles = 0;
    int[] holeArray = new int[3];
    char wheel = '-';
    int minRefreshMs = 30;
    char input;

    while (true) {
      try{
          Thread.sleep(this.time);


          printScore();
          holeArray = holeGen(25, pieceCounter, rangeOfRandom, remainingHoles);
          pieceCounter = holeArray[0];
          rangeOfRandom = holeArray[1];
          remainingHoles = holeArray[2];
          if(checkDeath()){
              c.carDeath();
              break;
          }
          if(score % 200 == 0 && this.time > minRefreshMs){
            this.time-=10;
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
      catch(InterruptedException ex){
        Thread.currentThread().interrupt();
      }
    }
  }

  private void printScore(){
      t.moveTo(this.terminalHeight / 2, this.terminalWidth /2);
      System.out.print("Score: "+score);
  }

  private boolean checkDeath(){
    if(road.charAt(this.terminalWidth/4 + 4) == ' ' || road.charAt(this.terminalWidth/4 + 9) == ' '){
      if(this.carStatus == 0){
        return true;
      }
    }else{
      this.score+=1;
    }
    return false;
  }

  private int[] holeGen(int distance, int pieceCounter, int rangeOfRandom,int remainingHoles){
    clearUpperRoad();
    Random randomGenerator = new Random();
    if(pieceCounter<distance){
      moveRoad('#');
      pieceCounter++;
    }
    else{
      Integer randomInt;
      if(remainingHoles == 0){
        randomInt = randomGenerator.nextInt(rangeOfRandom) + 1;
      }
      else{
        randomInt = 1;
      }

      if (randomInt.equals(1)){
        if(remainingHoles == 0){
          remainingHoles = randomGenerator.nextInt(5)+1;
        }
        moveRoad(' ');
        remainingHoles--;
        if(remainingHoles==0){
          pieceCounter = 0;
          rangeOfRandom = 71;
        }
      }
      else {
        rangeOfRandom -= 2;
        moveRoad('#');
      }
    }
    printUpperRoad();
    int[] resultArray = new int[3];
    resultArray[0] = pieceCounter;
    resultArray[1] = rangeOfRandom;
    resultArray[2] = remainingHoles;

    return resultArray;
  }

  private void moveWheel(char wheel){
    t.moveTo(this.terminalHeight-2-this.carStatus,this.terminalWidth/4 + 4);
    System.out.print(wheel);
    t.moveTo(this.terminalHeight-2-this.carStatus,this.terminalWidth/4 + 9);
    System.out.print(wheel);
  }

  private String generateLowerRoad(){
    String lowerRoad = "";
    for(int i = 0; i< this.terminalWidth; i++){
      lowerRoad+= '#';
    }
    return lowerRoad;
  }

  private void generateRoad(){
    for(int i = 0; i< this.terminalWidth; i++){
      this.road+="#";
    }
  }

  private void moveRoad(char roadPiece){
    this.road = this.road.substring(1,this.road.length()) + roadPiece;
  }

  private void clearUpperRoad(){
    t.moveTo(this.terminalHeight-1,terminalWidth);
    t.eraseLine();
  }

  private void printUpperRoad(){
    t.moveTo(this.terminalHeight-1,1);
    System.out.print(this.road);
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

  private void printTitle(){
      t.moveTo(2, this.terminalWidth/4);
      System.out.print("Hungarian Road Simulator 2019");
  }
}
