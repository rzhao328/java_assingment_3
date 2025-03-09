import java.util.Stack;

public class CampusWalk {
    private Map map;

    public CampusWalk(String filename, boolean showMap) {
        try {
            map = new Map(filename);
        } catch (Exception e) {
            System.out.println("Error occurred");
            return;
        }
        if (map != null) {
            if (showMap) {
                map.showGUI();
            } else {
                map.hideGUI();
            }
        }
    }

    public int neighbourGooseCount(Hexagon cell) {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            Hexagon[] neighbours = new Hexagon[]{cell.getNeighbour(i)};//我不确定这一行是对的！！！！！
            Hexagon neighbour = neighbours[i];
            if (neighbour != null && neighbour.isGooseCell()) {
                count++;
            }
        }
        return count;
    }

    public Hexagon findBest(Hexagon cell) {
        Hexagon bestChoice = null;
        int minGooseCount = Integer.MAX_VALUE;

        // firstly, find if curr cell adjacent to the end cell, go to the end cell
        for (int j = 0; j < 6; j++) {
            Hexagon[] neighbours = new Hexagon[]{cell.getNeighbour(j)};//我不确定这一行是对的！！！！！
            if (neighbours[j].isEnd()) {
                return neighbours[j];
            }
        }
        // if curr cell isn't adjacent to the end cell, do other basic rules
        for (int i = 0; i < 6; i++) {
            Hexagon[] neighbours = new Hexagon[]{cell.getNeighbour(i)};//我不确定这一行是对的！！！！！
            int neighbourGooseCount = neighbourGooseCount(cell.getNeighbour(i));
            // if the next cell is a goose cell or null or marked or more than 2 goose neighbour, go to next cell
            if (neighbours[i] == null && neighbours[i].isGooseCell() && neighbourGooseCount > 2 && neighbours[i].isMarked()) {
                continue;
            }
            //if curr is adjacent to one or more cells that contain a book, go to the book cell neighbour with the smallest index
            if (neighbours[i].isBookCell()) {
                bestChoice = neighbours[i];
            }

            //if curr is adjacent to one or more grass cells, go to the grass cell neighbour with the smallest index
            // that has the lowest goose neighbour
            else if (neighbours[i].isGrassCell()) {
                for (int k = 0; k < 6 - i; k++) {
                    if (neighbourGooseCount(cell.getNeighbour(i + k)) < minGooseCount) {
                        bestChoice = neighbours[i + k];
                    }
                }
            }

            //if curr is adjacent to one or more snow cells, go to the snow cell with the smallest index
            else if (neighbours[i].isSnowCell()) {
                bestChoice = neighbours[i];

            }

            //if none of these conditions are met, return null to indicate that you cannot proceed and must backtrack
            else {
                bestChoice = null;
            }
        }
        return bestChoice;
    }

    public String findPath() {
        if (map == null) return "No path found";
        Stack<Hexagon> stack = new Stack<>();
        Hexagon start = map.getStart();
        if (start == null) return "No path found";
        stack.push(start);
        boolean running = true;
        StringBuilder pathString = new StringBuilder();
        start.markInStack();
        Hexagon current = null;
        while (running && !stack.isEmpty()) {
            current = stack.peek();
            pathString.append(current.getID()).append(" ");
            if (current.isEnd()) {
                running = false;
                break;
            }
        }
        Hexagon next = findBest(current);
        if (next == null) {
            stack.pop();
            current.isMarkedOutStack();
        } else {
            stack.push(next);
            next.markInStack();
        }
        if (!running) {
            return pathString.toString();
        } else {
            return "No path found";
        }
    }

    public void exit() {
        map.exit();
    }
}