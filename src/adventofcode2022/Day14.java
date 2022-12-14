package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day14 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day14/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day14 day14 = new Day14();
        System.out.println("Part 1: " + day14.run(list, true));
        System.out.println("Part 2: " + day14.run(list, false));
    }

    private int run(ArrayList<String> list, boolean part1) {
        int yMaxBounds = 0, res = 0, mapWidth = 600;
        HashSet<Integer> positions = new HashSet<>();
        for (String line : list) {
            String[] parts = line.split("->");
            for (int a = 0; a < parts.length - 1; a++) {
                String[] posA = parts[a].trim().split(",");
                String[] posB = parts[a + 1].trim().split(",");
                int xFrom = Math.min(Integer.valueOf(posA[0]), Integer.valueOf(posB[0]));
                int xTo = Math.max(Integer.valueOf(posA[0]), Integer.valueOf(posB[0]));
                int yFrom = Math.min(Integer.valueOf(posA[1]), Integer.valueOf(posB[1]));
                int yTo = Math.max(Integer.valueOf(posA[1]), Integer.valueOf(posB[1]));
                yMaxBounds = Math.max(Math.max(yFrom, yTo), yMaxBounds);
                for (int y = yFrom; y <= yTo; y++) {
                    for (int x = xFrom; x <= xTo; x++) {
                        positions.add(y * mapWidth + x);
                    }
                }
            }
        }
        outer: while (true) {
            int sandX = 500, sandY = 0;
            inner: while (true) {
                boolean couldMove = false;
                for (int[] vector : new int[][] {{0,1}, {-1,1}, {1,1}}) {
                    int tmpX = sandX + vector[0];
                    int tmpY = sandY + vector[1];
                    if (!positions.contains(tmpY * mapWidth + tmpX) && tmpY < yMaxBounds + 2) {
                        sandX = tmpX;
                        sandY = tmpY;
                        couldMove = true;
                        if (part1 && sandY > yMaxBounds) {
                            break outer;
                        }
                        break;
                    }
                }
                if (!couldMove) {
                    positions.add(sandY * mapWidth + sandX);
                    res++;
                    if (sandY * mapWidth + sandX == 500) {
                        break outer;
                    }
                    break inner;
                }
            }
        }
        return res;
    }
}
