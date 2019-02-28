package com.company;

import java.util.*;
import java.util.concurrent.TimeUnit;
import com.codecool.termlib.*;
import java.awt.*;
import java.io.IOException;

public class Game {

  private int[] terminalSize = new int[2];
  private int terminalHeight;
  private int terminalWidth;
  private int score;
  private Terminal t = new Terminal();
  private String road = "";
  private int time = 40;
  private int loopCount = 0;
  private int wheelPosition = 2;

  public void init(){
    t.clearScreen();
    this.terminalSize = t.getTerminalSize();
    this.terminalHeight = this.terminalSize[0];
    this.score = 0;
    this.terminalWidth = this.terminalSize[1];
    t.moveTo(terminalHeight,1);
    System.out.print(generateLowerRoad());
    generateRoad();
    printUpperRoad();
    printCar(5);
    printTitle();
  }

  public void run(){
    int pieceCounter = 0;
    int rangeOfRandom = 71; //have to be an odd number, mégegy helyen bekéri
    int remainingHoles = 0;
    int[] holeArray = new int[3];
    char wheel = '-';
    int minRefreshMs = 15;
    char input;

    while (true) {
      try{
          if (checkDeath()) {
            carDeath();
            break;
          }
          input = tryToRead();
          changeLoopCount(input);
          moveCar();
          Thread.sleep(this.time);
          checkDeath();
          printScore();
          holeArray = holeGen(25, pieceCounter, rangeOfRandom, remainingHoles);
          pieceCounter = holeArray[0];
          rangeOfRandom = holeArray[1];
          remainingHoles = holeArray[2];
          changeTime(minRefreshMs);
          wheel = changeWheel(wheel);
      }
      catch(InterruptedException ex){
        Thread.currentThread().interrupt();
      }
    }
  }

  private Character tryToRead() {
    try {
        if (System.in.available() > 0) {
            return (char)System.in.read();
        }
    }
    catch (IOException e) {
      System.err.println("error" + e.getMessage());
    }
    return 'k';
  }

  private void changeTime(int minRefreshMs) {
    if(this.score % 50 == 0 && this.time > minRefreshMs){
      this.time-=1;
    }
  }

  private void printTitle(){
    String title = new String();
    title = "Hungarian road simulator 2019";
    t.moveTo(this.terminalHeight/4, (this.terminalWidth/2)-title.length()/2);
    System.out.println(title);
  }

  private void changeLoopCount(char input) {
    if (input != 'k' && this.loopCount == 0) {
      this.loopCount = 1;
    }
    if (this.loopCount == makeLoopCountDinamic(18)) {
      this.loopCount = 0;
      this.wheelPosition = 2;
    }
    if (this.loopCount > 0 ) {
      this.loopCount++;
    }
  }

  private void moveWheel(char wheel){
    t.moveTo(this.terminalHeight-this.wheelPosition,this.terminalWidth/4 + 4);
    System.out.print(wheel);
    t.moveTo(this.terminalHeight-this.wheelPosition,this.terminalWidth/4 + 9);
    System.out.print(wheel);
  }


  private char changeWheel (char wheel) {
    switch(wheel){
      case '-':
        moveWheel('\\');
        return '\\';
      case '\\':
        moveWheel('|');
        return '|';
      case '|':
        moveWheel('/');
        return '/';
      case '/':
        moveWheel('-');
        return '-';
    }
    return '-';
  }


  private void printScore(){
      String scoreString = new String();
      scoreString = "Score: "+ score;
      t.moveTo(this.terminalHeight / 2, (this.terminalWidth /2)-scoreString.length()/2);
      System.out.print(scoreString);
  }

  private boolean checkDeath(){
    if(road.charAt(this.terminalWidth/4 + 4) == ' ' || road.charAt(this.terminalWidth/4 + 9) == ' '){
      if(this.wheelPosition == 2){
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

  private void deleteCar(int topOfCar) {
    for(int i = 0; i < 4; i++){
      t.moveTo(this.terminalHeight-topOfCar+i,this.terminalWidth);
      t.eraseLine();
    }
  }

  private void moveCar() {

      if (this.loopCount == 0) {

        this.wheelPosition = 2;
      }
      else if (this.loopCount == 2){
        deleteCar(5);
        printCar(6);
        this.wheelPosition++;
      }
      else if (this.loopCount == makeLoopCountDinamic(8)){
        deleteCar(6);
        printCar(7);
        this.wheelPosition++;
      }
      else if (this.loopCount == makeLoopCountDinamic(13)){
        deleteCar(7);
        printCar(6);
        this.wheelPosition--;
      }
      else if (this.loopCount == makeLoopCountDinamic(18)){ //you need to change this above too
        deleteCar(6);
        printCar(5);
        this.wheelPosition--;
      }
  }

  public int makeLoopCountDinamic(int baseLoopNumber){
    int baseTime = 40;
    return baseLoopNumber * baseTime/time;
  }

  public void carDeath(){
        deleteCar(5);
        String[] car = new String[4];
        car[0] = "  ___ ___";
        car[1] = " /|_| |_\\` ._";
        car[2] = "=`-(o)---'\\ // \\ (0)";

        for(int i = 0; i < 3; i++){
          t.moveTo(this.terminalHeight-4+i,this.terminalWidth/4);
          System.out.print(car[i]);
        }
    }
}
