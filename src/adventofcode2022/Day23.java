package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Day23 {
    
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day23/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day23 day23 = new Day23();
        System.out.println("Part 1: " + day23.run(list, true));
        System.out.println("Part 2: " + day23.run(list, false));
    }
    
    private int run(ArrayList<String> list, boolean part1) {
        
        int[][][] vectors = new int[][][] {
            {{0,-1},{-1,-1},{1,-1}},
            {{0,1},{-1,1},{1,1}},
            {{-1,0},{-1,-1},{-1,1}},
            {{1,0},{1,-1},{1,1}},
        };
        int currPos = 0;
        HashSet<Integer> set = new HashSet<>();
        int width = 50_000;
        int offset = 5_000;
        int xMin = Integer.MAX_VALUE, xMax = Integer.MIN_VALUE, yMin = Integer.MAX_VALUE, yMax = Integer.MIN_VALUE;
        
        for (int y = 0; y < list.size(); y++) {
            for (int x = 0; x < list.get(y).length(); x++) {
                if (list.get(y).charAt(x) == '#') {
                    int ny = y + offset;
                    int nx = x + offset;
                    set.add(ny * width + nx);
                }
            }
        }
        boolean isRunning = true;
        int round = 0;
        while (isRunning) {
            round++;
            isRunning = false;
            HashMap<Integer, Integer> fromTo = new HashMap<>();
            HashMap<Integer, Integer> counting = new HashMap<>();
            for (int pos : set) {
                int x = pos % width;
                int y = pos / width;
                int tmpCurrPos = currPos;
                boolean doesntMove = true;
                boolean hasNeighbors = false;
                for (int a = 0; a < 4; a++, tmpCurrPos++) {
                    for (int[] vector : vectors[tmpCurrPos % 4]) {
                        int tmpX = x + vector[0];
                        int tmpY = y + vector[1];
                        hasNeighbors = set.contains(tmpY * width + tmpX) ? true : hasNeighbors;
                    }
                }
                tmpCurrPos = currPos;
                for (int a = 0; a < 4 && hasNeighbors; a++, tmpCurrPos++) {
                    boolean found = false;
                    for (int[] vector : vectors[tmpCurrPos % 4]) {
                        int tmpX = x + vector[0];
                        int tmpY = y + vector[1];
                        found = set.contains(tmpY * width + tmpX) ? true : found;
                    }
                    if (found == false) {
                        int[] vector = vectors[tmpCurrPos % 4][0];
                        int npos = (y + vector[1]) * width + (x + vector[0]);
                        fromTo.put(y * width + x, npos);
                        counting.put(npos, counting.containsKey(npos) ? counting.get(npos) + 1 : 1);
                        doesntMove = false;
                        break;
                    }
                }
                if (doesntMove) {
                    fromTo.put(y * width + x, y * width + x);
                    counting.put(y * width + x, 1);
                }
            }
            set.clear();
            for (int from : fromTo.keySet()) {
                int to = fromTo.get(from);
                int finalPos = counting.get(to) > 1 ? from : to;
                isRunning = finalPos != from ? true : isRunning;
                xMin = Math.min(finalPos % width, xMin);
                xMax = Math.max(finalPos % width, xMax);
                yMin = Math.min(finalPos / width, yMin);
                yMax = Math.max(finalPos / width, yMax);
                set.add(finalPos);
            }
            isRunning = part1 && round == 10 ? false : isRunning;
            currPos++;
        }
        int res = 0;
        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                res += set.contains(y * width + x) ? 0 : 1;
            }
        }
        return part1 ? res : round;
    }
}
