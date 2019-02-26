package com.company;
import java.util.*;
import java.util.concurrent.TimeUnit;
import com.codecool.termlib.*;

public class Game{
  private Terminal t = new Terminal();
  private Queue<Character> road = new LinkedList<>();
  public void init(){


    t.clearScreen();
    t.moveTo(31,1);
    System.out.print(generateLowerRoad());
    generateRoad();
    printUpperRoad();
    printCar(0);
    while (true) {

    }

  }
  private String generateLowerRoad(){
    String lowerRoad = "";
    for(int i = 0; i< 122; i++){
      lowerRoad+= '#';
    }
    return lowerRoad;
  }
  private void generateRoad(){
    for(int i = 0; i< 122; i++){
      this.road.add('#');
    }


  }
  private void printUpperRoad(){
    t.moveTo(30,1);
    for(char ch : this.road){
      System.out.print(ch);
    }
  }
  private void printCar(int status){
    String[] car = new String[4];
    car[0] = "      ____";
    car[1] = "  ___//_]|_______";
    car[2] = " (o _ |  -|     o|";
    car[3] = "  `(_)-------(_)-'";
    for(int i = 0; i < 4; i++){
      t.moveTo(26+i,60);
      System.out.print(car[i]);
    }


  }
}
