import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A Board represents the current state of the game. Boards know their
 * dimension, the collection of tiles that are inside the current flooded
 * region, and those tiles that are on the outside.
 * 
 * @author <put your name here>
 */

public class Board {
	public Map<Coord, Tile> inside, outside;
	private int size;

	/**
	 * Constructs a square game board of the given size, initializes the list of
	 * inside tiles to include just the tile in the upper left corner, and puts
	 * all the other tiles in the outside list.
	 */
	public Board(int size) {
		// A tile is either inside or outside the current flooded region.
		inside = new HashMap<>();
		outside = new HashMap<>();
		this.size = size;
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++) {
				Coord coord = new Coord(x, y);
				outside.put(coord, new Tile(coord));
			}
		// Move the corner tile into the flooded region and run flood on its
		// color.
		Tile corner = outside.remove(Coord.ORIGIN);
		inside.put(Coord.ORIGIN, corner);
		flood(corner.getColor());
	}

	/**
	 * Returns the tile at the specified coordinate.
	 */
	public Tile get(Coord coord) {
		if (outside.containsKey(coord))
			return outside.get(coord);
		return inside.get(coord);
	}

	/**
	 * Returns the size of this board.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * TODO
	 * 
	 * Returns true iff all tiles on the board have the same color.
	 */
	public boolean fullyFlooded() {
		return this.outside.isEmpty();
	}

	/**
	 * TODO
	 * 
	 * Updates this board by changing the color of the current flood region and
	 * extending its reach.
	 */
	public void flood(WaterColor color) {
		boolean[][] status = new boolean[size][size];
		ArrayList<Coord> stack = new ArrayList<Coord>();

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				status[i][j] = true;

		status[0][0] = false;
		stack.add(Coord.ORIGIN);

		while (true) {
			ArrayList<Coord> temp = new ArrayList<Coord>();

			for (Coord i : stack) {
				Tile now = inside.remove(i);
				now.setColor(color);
				inside.put(i, now);

				ArrayList<Coord> neighbors = (ArrayList<Coord>) i.neighbors(getSize());
				for (Coord j : neighbors) {
					if (inside.containsKey(j)) {
						if (status[j.getX()][j.getY()]) {
							temp.add(j);
							status[j.getX()][j.getY()] = false;
						}
					} else if (outside.get(j).getColor().compareTo(color) == 0) {
						inside.put(j, outside.get(j));
						outside.remove(j);
						temp.add(j);
						status[j.getX()][j.getY()] = false;
					}
				}
			}
			stack = (ArrayList<Coord>) temp.clone();

			if (stack.isEmpty())
				break;
		}
	}

	/**
	 * TODO
	 * 
	 * Explore a variety of algorithms for handling a flood. Use the same
	 * interface as for flood above, but add an index so your methods are named
	 * flood1, flood2, ..., and so on. Then, use the batchTest() tool in Driver
	 * to run game simulations and then display a graph showing the run times of
	 * all your different flood functions. Do not delete your flood code
	 * attempts. For each variety of flood, including the one above that you
	 * eventually settle on, write a comment that describes your algorithm in
	 * English. For those implementations that you abandon, state your reasons.
	 * 
	 */

	private boolean[][] status;

	/**
	 * Helper function o do recursion.
	 * 
	 * @param color
	 *            the color which need to be changed to
	 * @param star
	 *            the searching start coordinate
	 */
	private void search(WaterColor color, Coord star) {
		if (!status[star.getX()][star.getY()])
			return;
		Tile now = inside.remove(star);
		now.setColor(color);
		inside.put(star, now);
		status[star.getX()][star.getY()] = false;

		ArrayList<Coord> stack = (ArrayList<Coord>) star.neighbors(size);

		for (Coord i : stack) {
			if (!status[i.getX()][i.getY()])
				continue;
			if (inside.containsKey(i))
				this.search(color, i);
			else if (inside.get(Coord.ORIGIN).getColor().compareTo(outside.get(i).getColor()) == 0) {
				inside.put(i, outside.get(i));
				outside.remove(i);
				this.search(color, i);
			}
		}

	}

	/**
	 * In my flood function, I am using wide search with while loop. In flood1,
	 * i am using deep search using recursion.
	 * 
	 * @param color
	 *            the color which need to be changed to
	 */
	public void flood1(WaterColor color) {
		status = new boolean[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				status[i][j] = true;
		this.search(color, Coord.ORIGIN);
	}

	/**
	 * public void flood2(WaterColor color) {
	 * 
	 * }
	 */

	/**
	 * TODO
	 * 
	 * Returns the "best" GameColor for the next move.
	 * 
	 * Modify this comment to describe your algorithm. Possible strategies to
	 * pursue include maximizing the number of tiles in the current flooded
	 * region, or maximizing the size of the perimeter of the current flooded
	 * region.
	 */

	/**
	 * In the suggest function, i am using the outside variable to determine
	 * which color are near the inside one. To make a suggestion, I will
	 * recommend players to choose the color which has the most amount which
	 * near to inside flood.
	 * 
	 * @return
	 */
	public WaterColor suggest() {
		WaterColor cornerColor = inside.get(Coord.ORIGIN).getColor();
		WaterColor[] colors = { WaterColor.BLUE, WaterColor.RED, WaterColor.CYAN, WaterColor.PINK, WaterColor.YELLOW };
		int[] sizeFlood = new int[5];

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				Coord c = new Coord(i, j);
				if (outside.containsKey(c))
					for (Coord k : c.neighbors(size)) {
						if (inside.containsKey(k)) {
							switch (outside.get(c).getColor().toString()) {
							case "blue":
								sizeFlood[0]++;
								break;
							case "red":
								sizeFlood[1]++;
								break;
							case "cyan":
								sizeFlood[2]++;
								break;
							case "pink":
								sizeFlood[3]++;
								break;
							case "yellow":
								sizeFlood[4]++;
								break;
							}
							break;
						}
					}
			}

		int maxValue = -1, max = -1;
		for (int i = 0; i < 5; i++)
			if (sizeFlood[i] > maxValue) {
				max = i;
				maxValue = sizeFlood[i];
			}

		return colors[max];
	}

	/**
	 * Returns a string representation of this board. Tiles are given as their
	 * color names, with those inside the flooded region written in uppercase.
	 */
	public String toString() {
		StringBuilder ans = new StringBuilder();
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				Coord curr = new Coord(x, y);
				WaterColor color = get(curr).getColor();
				ans.append(inside.containsKey(curr) ? color.toString().toUpperCase() : color);
				ans.append("\t");
			}
			ans.append("\n");
		}
		return ans.toString();
	}

	/**
	 * Simple testing.
	 */
	public static void main(String... args) {
		// Print out boards of size 1, 2, ..., 5
		int n = 5;
		for (int size = 1; size <= n; size++) {
			Board someBoard = new Board(size);
			System.out.println(someBoard);
		}
	}
}
