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
    this.terminalSize = getTerminalSize();
    this.terminalHeight = this.terminalSize[0];
    this.terminalWidth = this.terminalSize[1];
    t.moveTo(terminalHeight,1);
    System.out.print(generateLowerRoad());
    generateRoad();
    printUpperRoad();
    printCar(0);
    System.out.print(terminalWidth);

    while (true) {

    }

  }
  private int[] getTerminalSize(){
    t.moveTo(3100,1000);
    int[] size = new int[2];
    Scanner scanner = new Scanner(System.in);
    System.out.print("\033[6n");
    try
    {
      Robot enterpresser = new Robot();
      enterpresser.keyPress(10);
      enterpresser.keyRelease(10);
    }
    catch (AWTException e)
    {
      e.printStackTrace();
    }
    String response = scanner.next();
    t.clearScreen();
    scanner.close();
    String[] splitresponse = response.split(";");
    size[0] = Integer.parseInt( splitresponse[0].substring(2, splitresponse[0].length()));
    size[1] = Integer.parseInt( splitresponse[1].substring(0, splitresponse[1].length()-1));

    return size;
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
  private void printUpperRoad(){
    t.moveTo(this.terminalHeight-1,1);
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
      t.moveTo(this.terminalHeight-5+i,this.terminalWidth/2);
      System.out.print(car[i]);
    }


  }
}
