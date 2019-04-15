import java.util.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class FloodTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);

    private boolean allTilesInOnlyOneList(Board b){
        return b.inside.size() + b.outside.size() == b.getSize() * b.getSize();
    }


    @Test
    public void floodColorChange(){
        int boardSize = 10;

        Board b = new Board(boardSize);

        //create an interspersed flooded region
        for(int i = 0; i < b.getSize(); i++){
            for(int j = 0; j < b.getSize(); j++){

                if(i % 2 == 0 && j % 2 == 0){
                    Coord c = new Coord(i, j);
                    //Tile t = b.get(new Coord(i,j));
                    if(b.outside.containsKey(c)){
                        Tile t = b.outside.remove(c);
                        b.inside.put(c, t);
                    };
                }
            }
        }

        b.flood(WaterColor.BLUE);

        for(Tile t : b.inside.values()){
            assertEquals("Flood does not properly change the color of the flooded region.", WaterColor.BLUE, t.getColor());
        }

    }


    @Test
    public void simpleFlood() {
        int boardSize = 10;

        Board b = new Board(boardSize);

        Map<Coord, Tile> outside = new HashMap<>();
        Map<Coord, Tile> inside = new HashMap<>();

        //set the whole board to PINK
        for(int i = 0; i < b.getSize(); i++){
            for(int j = 0; j < b.getSize(); j++){
                Coord c = new Coord(i, j);
                Tile t = new Tile(c);
                t.setColor(WaterColor.PINK);
                outside.put(c,t);
            }
        }

        Coord cornerC = Coord.ORIGIN;
        inside.put(cornerC, outside.remove(cornerC));
        b.inside = inside; b.outside = outside;

        //flood to right neighbor
        b.get(new Coord(1, 0)).setColor(WaterColor.BLUE);
        b.flood(WaterColor.BLUE);
        assertEquals("Flood does not spread to immediate neighbors (right).", 2, b.inside.size());
        assertTrue("Tiles in both regions on flood to immediate neighbors (right).", allTilesInOnlyOneList(b));

        //flood to bottom neighbor
        b.get(new Coord(0, 1)).setColor(WaterColor.BLUE);
        b.flood(WaterColor.BLUE);
        assertEquals("Flood does not spread to immediate neighbors (below).", 3, b.inside.size());
        assertTrue("Tiles in both regions on flood to immediate neighbors (below).", allTilesInOnlyOneList(b));


        //add bottom right to flood
        Coord bottomRightC = new Coord(boardSize -1, boardSize - 1);
        Tile bottomRightT = b.get(bottomRightC);
        b.outside.remove(bottomRightC);
        b.inside.put(bottomRightC, bottomRightT);

        //flood to top neighbor
        b.get(new Coord(boardSize - 1, boardSize - 2)).setColor(WaterColor.BLUE);
        b.flood(WaterColor.BLUE);
        assertEquals("Flood does not spread to immediate neighbors (top).", 5, b.inside.size());
        assertTrue("Tiles in both regions on flood to immediate neighbors (top).", allTilesInOnlyOneList(b));

        //flood to left neighbor
        b.get(new Coord(boardSize - 2, boardSize - 1)).setColor(WaterColor.BLUE);
        b.flood(WaterColor.BLUE);
        assertEquals("Flood does not spread to immediate neighbors (left).", 6, b.inside.size());
        assertTrue("Tiles in both regions on flood to immediate neighbors (left).", allTilesInOnlyOneList(b));


        //add middle to flood and stage
        Coord midC = new Coord(boardSize / 2, boardSize /2 );
        Tile midT = b.get(midC);
        b.outside.remove(midC);
        b.inside.put(midC, midT);
        Coord[] surrounding = new Coord[] {
                new Coord(boardSize / 2, boardSize / 2 -1),
                new Coord(boardSize / 2 - 1, boardSize / 2),
                new Coord(boardSize / 2 + 1, boardSize / 2),
                new Coord(boardSize / 2, boardSize / 2  + 1),
        };
        for (Coord c : surrounding){
            b.get(c).setColor(WaterColor.BLUE);
        }

        //flood in multiple directions
        b.flood(WaterColor.BLUE);
        assertEquals("Flood does not spread to immediate neighbors (multi-directional).", 11, b.inside.size());
        assertTrue("Tiles in both regions on flood to immediate neighbors (multi-directional).", allTilesInOnlyOneList(b));

    }


    @Test
    public void complexFlood() {

        int boardSize = 10;

        /*************************************
        **** HORIZONTAL FLOOD
        *************************************/
        Board b = new Board(boardSize);

        Map<Coord, Tile> outside = new HashMap<>();
        Map<Coord, Tile> inside = new HashMap<>();

        //set the top row to BLUE and the rest to PINK
        for(int i = 0; i < b.getSize(); i++){
            for(int j = 0; j < b.getSize(); j++){
                Coord c = new Coord(i, j);
                Tile t = new Tile(c);
                t.setColor(i == 0 ? WaterColor.BLUE : WaterColor.PINK);
                outside.put(c,t);
            }
        }

        Coord cornerC = new Coord(Coord.ORIGIN);
        inside.put(cornerC, outside.remove(cornerC));
        b.inside = inside; b.outside = outside;

        b.flood(WaterColor.BLUE);
        assertEquals("Flood does not spread more than one tile horizontally.", boardSize, b.inside.size());
        assertTrue("Tiles in both regions when flooding more than one tile horizontally.", allTilesInOnlyOneList(b));


        /*************************************
         **** VERTICAL FLOOD
         *************************************/
        b = new Board(boardSize);

        outside = new HashMap<>();
        inside = new HashMap<>();

        //set the first column to BLUE and the rest to PINK
        for(int i = 0; i < b.getSize(); i++){
            for(int j = 0; j < b.getSize(); j++){
                Coord c = new Coord(i, j);
                Tile t = new Tile(c);
                t.setColor(j == 0 ? WaterColor.BLUE : WaterColor.PINK);
                outside.put(c,t);
            }
        }
        
        inside.put(cornerC, outside.remove(cornerC));
        b.inside = inside; b.outside = outside;

        b.flood(WaterColor.BLUE);
        assertEquals("Flood does not spread more than one tile vertically.", boardSize, b.inside.size());
        assertTrue("Tiles in both regions when flooding more than one tile vertically.", allTilesInOnlyOneList(b));


        /*************************************
         **** BORDER FLOOD
         *************************************/
        b = new Board(boardSize);

        inside = new HashMap<>();
        outside = new HashMap<>();

        //set the edges to BLUE and the rest to PINK
        for(int i = 0; i < b.getSize(); i++){
            for(int j = 0; j < b.getSize(); j++){
                Coord c = new Coord(i, j);
                Tile t = new Tile(c);
                t.setColor(i == 0 || j == 0 || i == boardSize -1 || j == boardSize - 1 ? WaterColor.BLUE : WaterColor.PINK);
                outside.put(c,t);
            }
        }

        inside.put(cornerC, outside.remove(cornerC));
        b.inside = inside; b.outside = outside;

        b.flood(WaterColor.BLUE);
        assertEquals("Flood does not spread more than one tile in multiple directions.", (boardSize - 1) * 4, b.inside.size());
        assertTrue("Tiles in both regions when flooding more than one tile in multiple directions.", allTilesInOnlyOneList(b));


        /*************************************
         **** CENTER FLOOD
         *************************************/
        b = new Board(boardSize);

        inside = new HashMap<>();
        outside = new HashMap<>();

        //set the interior to BLUE and the edges to PINK
        for(int i = 0; i < b.getSize(); i++){
            for(int j = 0; j < b.getSize(); j++){
                Coord c = new Coord(i, j);
                Tile t = new Tile(c);
                t.setColor(i != 0 && j != 0 && i != boardSize -1 && j != boardSize - 1 ? WaterColor.BLUE : WaterColor.PINK);
                outside.put(c,t);
            }
        }

        inside.put(cornerC, outside.remove(cornerC));
        b.inside = inside; b.outside = outside;

        Coord midC = new Coord(boardSize / 2, boardSize / 2);
        Tile cornerT = b.get(cornerC), midT = b.get(midC);
        b.inside.remove(cornerC);
        b.outside.put(cornerC, cornerT);
        b.outside.remove(midC);
        b.inside.put(midC, midT);

        b.flood(WaterColor.BLUE);
        assertEquals("Flood does not spread more than one tile in multiple directions.", boardSize *  boardSize - ((boardSize - 1) * 4), b.inside.size());
        assertTrue("Tiles in both regions when flooding more than one tile in multiple directions.", allTilesInOnlyOneList(b));

    }
}
