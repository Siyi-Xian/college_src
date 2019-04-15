import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Iterator;

import static org.junit.Assert.*;


public class BoardTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(1);

    @Test
    public void get() throws Exception {

        int boardSize = 10;

        Board b = new Board(boardSize);


        Coord[] goodCoords = new Coord[] {
            new Coord(5,5),
            new Coord(0, boardSize - 1),
            new Coord(boardSize - 1, 0),
            new Coord(boardSize - 1, boardSize -1)
        };

        //test the outside
        for (Coord c : goodCoords){
            Tile t = b.get(c);
            assertEquals("Not properly getting coords from outside HashMap.", c, t.getCoord());
        }

        //test the inside
        for (Coord c : goodCoords){

            //move from the outside to the inside
            Tile t = b.get(c);
            if(b.outside.containsKey(c)){
               b.outside.remove(c);
                b.inside.put(c, t);
            }

            //see if it can grab the tile from the inside list
            t = b.get(c);
            assertEquals("Not properly getting coords from inside HashMap.", c, t.getCoord());
        }
    }


    @Test
    public void fullyFlooded() throws Exception {
        Board b = new Board(10);

        Iterator<Coord> iter = b.outside.keySet().iterator();

        //move all tiles from the inside to the outside
        while(iter.hasNext()){
            assertFalse("Returns true on a board that is not fully flooded.", b.fullyFlooded());
            Coord next = iter.next();
            b.inside.put(next, b.get(next));
            iter.remove();
        }

        assertTrue("Returns false on a board that is fully flooded.", b.fullyFlooded());

    }


    @Test
    public void suggest() throws Exception {
        Board b = new Board(10);
        b.flood(WaterColor.BLUE);
        b.flood(WaterColor.RED);
        b.flood(WaterColor.PINK);
        b.flood(WaterColor.YELLOW);
        b.flood(WaterColor.CYAN);
        assertNotEquals("You suggested the color of the already flooded region.", WaterColor.CYAN, b.suggest());
    }

}
