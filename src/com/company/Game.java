package com.company;
import java.util.*;
import java.util.concurrent.TimeUnit;
import com.codecool.termlib.*;
import java.awt.*;

public class Game{
  private int[] terminalSize = new int[2];
  private int terminalHeight;
  private int terminalWidth;
  private Terminal t = new Terminal();
  private Queue<Character> road = new LinkedList<>();
  public void init(){
    t.clearScreen();
    this.terminalSize = t.getTerminalSize();
    this.terminalHeight = this.terminalSize[0];
    this.terminalWidth = this.terminalSize[1];
    t.moveTo(terminalHeight,1);
    System.out.print(generateLowerRoad());
    generateRoad();
    printUpperRoad();
    printCar(0);



  }
  public void run(){
    int pieceCounter = 0;
    while (true) {
      try
      {
          Thread.sleep(100);
          pieceCounter = holeGen(16,pieceCounter);

      }
      catch(InterruptedException ex)
      {
        Thread.currentThread().interrupt();
      }
    }
  }
  private int holeGen(int distance, int pieceCounter){
    clearUpperRoad();
    if(pieceCounter<distance){
      moveRoad('#');
      pieceCounter++;
    }
    else{
      moveRoad(' ');
      pieceCounter = 0;
    }
    printUpperRoad();
    return pieceCounter;
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
      this.road.add('#');
    }
  }
  private void moveRoad(char roadPiece){
    this.road.remove();
    this.road.add(roadPiece);
  }
  private void clearUpperRoad(){
    t.moveTo(this.terminalHeight-1,terminalWidth);
    t.eraseLine();
  }

  private void printUpperRoad(){
    t.moveTo(this.terminalHeight-1,1);
    for(char ch : this.road){
      System.out.print(ch);
    }
  }
  private void printCar(int status){
    String[] car = new String[4];
    car[0] = "  ______";
    car[1] = " /|_||_\\`.__";
    car[2] = "(   _    _ _\\";
    car[3] = "=`-(_)--(_)-'";
    for(int i = 0; i < 4; i++){
      t.moveTo(this.terminalHeight-5+i,this.terminalWidth/4);
      System.out.print(car[i]);
    }


  }
}
