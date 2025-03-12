import java.util.Stack;

/**
 *
 */
public class CampusWalk {
    private Map map; //
    private static final int HEXAGON_SIZE = 6; //
    /**
     * constructor
     * @param filename the location of file that will initiate the MapFrame
     * @param showMap whether to show the GUI of the map
     */
    public CampusWalk(String filename, boolean showMap) {
        try {
            map = new Map(filename);
            if (showMap) {
                map.showGUI();
            } else {
                map.hideGUI();
            }
        } catch (Exception e) {
            System.out.println("Error occurred");
        }
    }

    /**
     * Count and return the number of goose cells surrounding the given cell
     * @param cell the cell to be count
     * @return the number of goose cells surrounding the given cell
     */
    public int neighbourGooseCount(Hexagon cell) {
        int count = 0;
        // get all neighbour cells
        for (int i = 0; i < HEXAGON_SIZE; i++) {
            Hexagon neighbour = cell.getNeighbour(i);
            if (neighbour != null && neighbour.isGooseCell()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Determine the next cell to walk onto from the current cell
     *
     * @param cell current cell
     * @return the next cell to walk onto from the current cell
     */
    public Hexagon findBest(Hexagon cell) {
        Hexagon bestChoice = null;
        // loop each neighbour cell of current cell
        for (int i = 0; i < HEXAGON_SIZE; i++) {
            Hexagon neighbour = cell.getNeighbour(i);
            if (neighbour == null) {
                continue; // this cell doesn't exist, skip
            }
            // if it's the end cell, return directly
            if (neighbour.isEnd()) {
                return neighbour;
            }
            // if the next cell is a goose cell, skip;
            if (neighbour.isGooseCell()) {
                continue;
            }
            // the next cell does not have a goose-neighbour-count (see below) of 3 or more, skip
            if (neighbourGooseCount(neighbour) > 2) {
                continue;
            }
            // if the next cell is marked， skip
            if (neighbour.isMarked()) {
                continue;
            }
            // if curr is adjacent to one or more cells that contain a book, go to the book cell neighbour with the smallest index (
            if (neighbour.isBookCell()) {
                bestChoice = neighbour;
                break;
            }
        }
        if (bestChoice == null) { // bestChoice is null here means no book cell or end cell is adjacent
            /* if curr is adjacent to one or more grass cells,
             * go to the grass cell neighbour with the smallest index
             * that has the lowest goose-neighbour-count.
             */
            int lowestGooseCount = Integer.MAX_VALUE;
            for (int i = 0; i < HEXAGON_SIZE; i++) {
                Hexagon neighbour = cell.getNeighbour(i);
                if (neighbour != null && neighbour.isGrassCell()) {
                    int gooseCount = neighbourGooseCount(neighbour);
                    if (gooseCount < lowestGooseCount) {
                        lowestGooseCount = gooseCount;
                        bestChoice = neighbour;
                    }
                }
            }
        }
        if (bestChoice == null) { // bestChoice is null here means no grass cell with 2 or less goose-neighbour-count cell is adjacent
            /* if curr is adjacent to one or more snow cells,
             * go to the snow cell with the smallest index.
             */
            for (int i = 0; i < HEXAGON_SIZE; i++) {
                Hexagon neighbour = cell.getNeighbour(i);
                if (neighbour != null && neighbour.isSnowCell()) {
                    bestChoice = neighbour;
                }
            }
        }
        return bestChoice;
    }

    /**
     * Determine the path from the starting point to the end cell
     *
     * @return
     */
    public String findPath() {
        if (this.map == null) {
            return "No path found";
        }
        // initialize Stack
        Stack<Hexagon> stack = new Stack<>();
        Hexagon start = this.map.getStart();
        if (start == null) {
            return "No path found";
        }
        // push the starting cell onto S
        stack.push(start);
        // set a boolean variable running to be true
        boolean running = true;
        // mark the starting cell as in-stack
        start.markInStack();
        StringBuilder pathString = new StringBuilder();
        Hexagon current = null;
        while (running && !stack.isEmpty()) {
            current = stack.peek();
            pathString.append(current.getID()).append(" ");
            if (current.isEnd()) {
                running = false;
            }
            Hexagon next = findBest(current);
            if (next == null) {
                stack.pop();
                current.markOutStack();
            } else {
                stack.push(next);
                next.markInStack();
            }
        }
        if (!running) {
            return pathString.toString();
        } else {
            return "No path found";
        }
    }

    /**
     * make the map to be closed
     */
    public void exit() {
        this.map.exit();
    }

    public static void main(String[] args) {
        Hexagon.TIME_DELAY = 500; // Change speed of animation.
        String file = "map1.txt"; // Change when trying other maps.
        CampusWalk walk = new CampusWalk(file, true);
        String result = walk.findPath();
        System.out.println(result);
    }
}