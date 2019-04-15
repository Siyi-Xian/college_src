import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.TestTimedOutException;

public class FloodStressTest {

    /* Description: Helper function that creates a board with alternating diagonal
     *              stripes of WaterColors to place further pressure on flood
     *              runtime.
     * board      - board to be filled with the color stripes (assumed to be square)
     * boardSize  - integer representing the size of the board
     */
    private Board boardMaker(Board board, int boardSize){
        //Sets two alternating line patterns to increase number of flood operations
        //required in order to put more pressure on runtime
        WaterColor[] colors = {WaterColor.BLUE, WaterColor.RED, WaterColor.CYAN, WaterColor.PINK, WaterColor.YELLOW};

        //Board creation, builds in diagonal lines
        for(int i = 0; i < boardSize; i++){
            int x = i;
            int j = 0;
            int colIndex = 0;
            do{
                if(i % 2 == 0) {
                    board.get(new Coord(x, j)).setColor(colors[colIndex]);
                    colIndex += 2;
                    if(colIndex >= colors.length)
                        colIndex = 0;
                }else{
                    board.get(new Coord(x, j)).setColor(colors[colIndex]);
                    colIndex += 2;
                    if(colIndex >= colors.length)
                        colIndex = 1;
                }
                j++;
            }while(x-- > 0);
        }

        for(int j = 1; j < boardSize; j++){
            int y = j;
            int i = boardSize-1;
            int colIndex = 0;
            do{
                if(j % 2 == 0) {
                    board.get(new Coord(i, y)).setColor(colors[colIndex]);
                    colIndex += 2;
                    if(colIndex >= colors.length)
                        colIndex = 0;
                }else{
                    board.get(new Coord(i, y)).setColor(colors[colIndex]);
                    colIndex += 2;
                    if(colIndex >= colors.length)
                        colIndex = 1;
                }
                i--;
            }while(++y < boardSize);
        }

        return board;
    }

  //  @Rule
  //  public TestRule watcher = new TestWatcher() {
  //      protected void starting(Description description) {
  //          System.out.println("Starting test: " + description.getMethodName());
  //      }
  //  };


    @Test (timeout = 100)
    public void stressTestSmall(){

        //Initializes the board, its size, and the WaterColor set
        int size = 10;
        Board test = new Board(size);
        WaterColor[] colors = {WaterColor.BLUE, WaterColor.RED, WaterColor.CYAN, WaterColor.PINK, WaterColor.YELLOW};

        test = boardMaker(test, size);

        //Floods the board until done
        int colIndex = 0;
        while (!test.fullyFlooded()) {
            test.flood(colors[colIndex++]);
            if (colIndex >= colors.length)
                colIndex = 0;
        }
    }

    @Test (timeout = 7000)
    public void stressTestMid(){

        //Initializes the board, its size, and the WaterColor set
        int size = 50;
        Board test = new Board(size);
        WaterColor[] colors = {WaterColor.BLUE, WaterColor.RED, WaterColor.CYAN, WaterColor.PINK, WaterColor.YELLOW};

        test = boardMaker(test, size);

        //Floods the board until done
        int colIndex = 0;
        while(!test.fullyFlooded()){
            test.flood(colors[colIndex++]);
            if(colIndex >= colors.length)
                colIndex = 0;
        }

    }

    @Test (timeout = 25000)
    public void stressTestLarge(){

        //Initializes the board, its size, and the WaterColor set
        int size = 75;
        Board test = new Board(size);
        WaterColor[] colors = {WaterColor.BLUE, WaterColor.RED, WaterColor.CYAN, WaterColor.PINK, WaterColor.YELLOW};

        test = boardMaker(test, size);

        //Floods the board until done
        int colIndex = 0;
        while(!test.fullyFlooded()){
            test.flood(colors[colIndex++]);
            if(colIndex >= colors.length)
                colIndex = 0;
        }

    }
}
