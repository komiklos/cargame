package com.codecool.termlib;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
public class Terminal {
    /**
     * The beginning of control sequences.
     */
    // HINT: In \033 the '0' means it's an octal number. And 33 in octal equals 0x1B in hexadecimal.
    // Now you have some info to decode that page where the control codes are explained ;)
    private static final String CONTROL_CODE = "\033[";
    /**
     * Command for whole screen clearing.
     *
     * Might be partitioned if needed.
     */
    private static final String CLEAR = "2J";
    /**
     * Command for moving the cursor.
     */
    private static final String MOVE = "H";
    /**
     * Command for printing style settings.
     *
     * Handles foreground color, background color, and any other
     * styles, for example color brightness, or underlines.
     */
    private static final String STYLE = "m";

    /**
     * Reset printing rules in effect to terminal defaults.
     *
     * Reset the color, background color, and any other style
     * (i.e.: underlined, dim, bright) to the terminal defaults.
     */
    public void resetStyle() {
      System.out.print(CONTROL_CODE + "c");
    }

    /**
     * Clear the whole screen.
     *
     * Might reset cursor position.
     */
     public int[] getTerminalSize(){
       moveTo(3100,1000);
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
       clearScreen();
       scanner.close();
       String[] splitresponse = response.split(";");
       size[0] = Integer.parseInt( splitresponse[0].substring(2, splitresponse[0].length()));
       size[1] = Integer.parseInt( splitresponse[1].substring(0, splitresponse[1].length()-1));

       return size;
     }
    public void clearScreen() {
      System.out.println(CONTROL_CODE + CLEAR);
    }

    /**
     * Move cursor to the given position.
     *
     * Positions are counted from one.  Cursor position 1,1 is at
     * the top left corner of the screen.
     *
     * @param x Column number.
     * @param y Row number.
     */
    public void moveTo(Integer x, Integer y) {
      if(x == null || (y == null)){
        System.out.print(CONTROL_CODE + "H");

      }
      else{
        System.out.print(CONTROL_CODE  + x + ";" + y + "H");
    }}

    /**
     * Set the foreground printing color.
     *
     * Already printed text is not affected.
     *
     * @param color The color to set.
     */
    public void setColor(Color color) {
      String colorcode = "37";
      switch(color){
        case BLACK:
          colorcode = "30";
          break;
        case RED:
          colorcode = "31";
          break;
        case GREEN:
          colorcode = "32";
          break;
        case YELLOW:
          colorcode = "33";
          break;
        case BLUE:
          colorcode = "34";
          break;
        case MAGENTA:
          colorcode = "35";
          break;
        case CYAN:
          colorcode = "36";
          break;
        case WHITE:
          colorcode = "37";
          break;

      }
      System.out.print(CONTROL_CODE +colorcode+"m");
    }

    /**
     * Set the background printing color.
     *
     * Already printed text is not affected.
     *
     * @param color The background color to set.
     */
    public void setBgColor(Color color) {
    }

    /**
     * Make printed text underlined.
     *
     * On some terminals this might produce slanted text instead of
     * underlined.  Cannot be turned off without turning off colors as
     * well.
     */
    public void setUnderline() {
    }

    /**
     * Move the cursor relatively.
     *
     * Move the cursor amount from its current position in the given
     * direction.
     *
     * @param direction Step the cursor in this direction.
     * @param amount Step the cursor this many times.
     */
    public void moveCursor(Direction direction, Integer amount) {
      String dir = "A";
      switch(direction){
        case UP:
          dir = "A";
          break;
        case DOWN:
          dir = "B";
          break;
        case FORWARD:
          dir = "C";
          break;
        case BACKWARD:
          dir = "D";
          break;
      }
      System.out.print(CONTROL_CODE + amount + dir);

    }

    /**
     * Set the character diplayed under the current cursor position.
     *
     * The actual cursor position after calling this method is the
     * same as beforehand.  This method is useful for drawing shapes
     * (for example frame borders) with cursor movement.
     *
     * @param c the literal character to set for the current cursor
     * position.
     */
    public void eraseLine(){
      System.out.print(CONTROL_CODE + "1K");
    }

    public void setChar(char c) {
    }

    /**
     * Helper function for sending commands to the terminal.
     *
     * The common parts of different commands shall be assembled here.
     * The actual printing shall be handled from this command.
     *
     * @param commandString The unique part of a command sequence.
     */
    private void command(String commandString) {
    }
}
