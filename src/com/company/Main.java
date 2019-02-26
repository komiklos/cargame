package com.company;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Main {
  public static void main(String[] args) {
      final int CHARACTERS_PER_LINE = 80;

      String[][] ground = new String[2][80];
      for(int i=0;i<30;i++){
          fillGround(ground, "#");
          System.out.println("");
          for (int j=0;j<ground[0].length;j++ ) {
              System.out.print(ground[0][j]);
              System.out.print(ground[1][j]);
          }

          try
          {
              Thread.sleep(1000);
              System.out.print("\033[H\033[2J");
          }
          catch(InterruptedException ex)
          {
              Thread.currentThread().interrupt();
          }
          fillGround(ground, "0");
          System.out.println("");
          for (int j=0;j<ground[0].length;j++ ) {
              System.out.print(ground[0][j]);
              System.out.print(ground[1][j]);
          }
          try
          {
              Thread.sleep(1000);
              System.out.print("\033[H\033[2J");
          }
          catch(InterruptedException ex)
          {
              Thread.currentThread().interrupt();
          }
      }


    // Print what is left
  }
  private static void fillGround(String[][] a, String icon){
      for(int j=0;j<2;j++){
          for(int i=0;i<80;i++){
              a[j][i] = (icon);
          }
      }

  }
}
