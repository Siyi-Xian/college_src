import java.util.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class CoordTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(1);

    @Test
    public void onBoard() throws Exception {

        int boardSize = 10;

        Coord[] badCoords = new Coord[] {
            new Coord(-1, 0),
            new Coord(boardSize, 0),
            new Coord (0, -1),
            new Coord (0, boardSize)
        };

        Coord[] goodCoords = new Coord[] {
            new Coord(1,1),
            new Coord(2,2),
            new Coord(0, 9),
            new Coord(9, 0)
        };


        for(Coord c : badCoords){
            assertFalse("Coord NOT on board returns true.", c.onBoard(boardSize));
        }

        for(Coord c : goodCoords){
            assertTrue("Coord on board returns false.", c.onBoard(boardSize));
        }
    }



    @Test
    public void neighbors() throws Exception {

        int boardSize = 10;

        Coord[] corners = new Coord[] {
            new Coord(0,0),
            new Coord(0,boardSize - 1),
            new Coord(boardSize - 1,boardSize - 1),
            new Coord(boardSize - 1,0)
        };


        Coord[] sides = new Coord[] {
            new Coord(boardSize / 2, 0),
            new Coord(boardSize / 2,  boardSize - 1),
            new Coord(0, boardSize / 2),
            new Coord(boardSize - 1, boardSize / 2)
        };

        Coord mid = new Coord (boardSize / 2,boardSize / 2);

        for(Coord c : corners){
            List<Coord> neighbors = c.neighbors(boardSize);
            assertEquals("Neighbors for corners are incorrect.", 2, neighbors.size());
            for(Coord n : neighbors){
                assertTrue("Neighbors for corners are incorrect.", n.onBoard(boardSize));
            }
        }

        for(Coord c : sides){
            List<Coord> neighbors = c.neighbors(boardSize);
            assertEquals("Neighbors for sides are incorrect.", 3, neighbors.size());
            for(Coord n : neighbors){
                assertTrue("Neighbors for sides are incorrect.", n.onBoard(boardSize));
            }
        }


        List<Coord> neighbors = mid.neighbors(boardSize);
        assertEquals("Neighbors for middle coord are incorrect.", 4, neighbors.size());
        for(Coord n : neighbors){
            assertTrue("Neighbors for middle coord are incorrect.", n.onBoard(boardSize));
        }

    }

}
