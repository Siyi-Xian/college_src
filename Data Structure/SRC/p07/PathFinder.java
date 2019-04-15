import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;

/**
 * TODO
 * 
 * Most of the work for this project involves implementing the connectAllWires()
 * method in this class. Feel free to create any helper methods that you deem
 * necessary.
 * 
 * Your goal is to come up with an efficient algorithm that will find a layout
 * that connects all the wires (if one exists) while attempting to minimize the
 * overall wire length.
 */

public class PathFinder {
	
	private static Map<Coord, Integer> coach;

	private String coachToString(Map<Coord, Integer> c, Dimension dim) {
		String str = "";
		int[][] coa = new int[(int) dim.getHeight()][(int) dim.getWidth()];
		for (Coord i : c.keySet())
			coa[i.y][i.x] = c.get(i);

		for (int i = 0; i < dim.getHeight(); i++) {
			for (int j = 0; j < dim.getWidth(); j++) {
				if (coa[i][j] < 10)
					str += " ";
				str += "   " + coa[i][j];
			}
			str += "\n";
		}
		str += "\n";

		return str;
	}

	/*
	 * private int findPointPath(Map<Coord, Integer> grid, Dimension dim, Coord
	 * coord, Stack<Coord> s) { if (coach.containsKey(coord)) return
	 * coach.get(coord); if (grid.get(coord) != Constants.FREE) return
	 * Integer.MAX_VALUE - 1; if (s.contains(coord)) return Integer.MAX_VALUE -
	 * 1; s.add(coord);
	 * 
	 * int minValue = Integer.MAX_VALUE - 1; for (Coord next :
	 * coord.neighbors(dim)) minValue = Math.min(minValue,
	 * this.findPointPath(grid, dim, next, s)); minValue++; if (minValue <
	 * Integer.MAX_VALUE) coach.put(coord, minValue);
	 * 
	 * return minValue; }
	 */

	private static Path trackback(Wire wire, Dimension dim) {
		List<Coord> wirePath = new LinkedList<Coord>();

		Coord to = wire.to;
		while (coach.get(to) > 1) {
			wirePath.add(to);
			for (Coord coord : to.neighbors(dim))
				if (coach.containsKey(coord) && coach.get(coord) == coach.get(to) - 1)
					to = coord;
		}
		// wirePath.add(to);

		Path path = new Path(wire);
		for (int i = wirePath.size() - 1; i >= 0; i--)
			path.add(wirePath.get(i));

		// System.out.println(path);

		return path;
	}

	private static Map<Coord, Integer> search(Map<Coord, Integer> grid, Dimension dim, Wire wire) {
		Stack<Coord> s = new Stack<Coord>();
		s.add(wire.from);
		Stack<Coord> p = new Stack<Coord>();
		p.addAll(s);
		Map<Coord, Integer> c = new HashMap<Coord, Integer>();
		c.put(wire.from, 1);

		while (s.size() > 0) {
			Stack<Coord> temp = new Stack<Coord>();
			for (Coord coord : s)
				for (Coord next : coord.neighbors(dim))
					if (!p.contains(next) && (grid.get(next) == Constants.FREE || grid.get(next) == wire.wireId)) {
						c.putIfAbsent(next, c.get(coord) + 1);
						temp.add(next);
						p.add(next);
						if (c.containsKey(wire.to))
							return c;
					}
			s = temp;
		}

		return c;
	}

	/**
	 * 
	 * Build path for a single wire discard any conflicts with other wires
	 * 
	 * @param grid
	 *            Map only contains obstacles
	 * @param dim
	 *            Dimension
	 * @param wire
	 *            given wire
	 * @return List of coordinates of wire
	 */
	public static Path buildPath(Map<Coord, Integer> grid, Dimension dim, Wire wire) {
		coach = new HashMap<Coord, Integer>();
		coach.put(wire.from, 1);

		coach = search(grid, dim, wire);

		try {
			return trackback(wire, dim);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * TODO
	 * 
	 * Lays out a path connecting each wire on the chip, and then returns a map
	 * that associates a wire id numbers to the paths corresponding to the
	 * connected wires on the grid. If it is not possible to connect the
	 * endpoints of a wire, then there should be no association for the wire id#
	 * in the result.
	 */
	public static Map<Integer, Path> connectAllWires(Chip chip) {
		
		Chip temp = new Chip(chip.dim, chip.obstacles, chip.wires);
		
		Map<Integer, Path> pathes = new HashMap<Integer, Path>();
		
		int i = 0;
		while (temp.wires.size() > 0) {
			Wire wire = temp.wires.remove(0);
			Path wires = buildPath(temp.grid, temp.dim, wire);
			if (wires != null) {
				pathes.put(wire.wireId, wires);
				for (Coord coord : wires)
					temp.grid.put(coord, wire.wireId);
			}
			else{
				temp = new Chip(chip.dim, chip.obstacles, chip.wires); 
				temp.grid.replace(wire.from, Constants.FREE);
				temp.grid.replace(wire.to, Constants.FREE);
				wires = buildPath(temp.grid, temp.dim, wire);
				if (wires != null) {
					pathes.put(wire.wireId, wires);
					for (Coord coord : wires)
						temp.grid.put(coord, wire.wireId);
				}
				temp.wires.remove(wire);
			}
			if (i++ > chip.wires.size() * 10 && i < 2000)
				break;
		}
		
		chip.grid = temp.grid;

		// System.out.println(chip);

		return pathes;
	}

	/**
	 * TODO
	 * 
	 * Returns the sum of the lengths of all non-null paths in the given layout.
	 */
	public static int totalWireUsage(Map<Integer, Path> layout) {
		int sum = 0;
		
		for (Integer i : layout.keySet())
			sum += layout.get(i).size();
		
		return sum;
	}

}
