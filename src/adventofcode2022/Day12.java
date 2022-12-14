package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Day12 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day12/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day12 day12 = new Day12();
        day12.run(list);
    }
    
    private void run(ArrayList<String> list) {
        ArrayList<Integer> positions = new ArrayList<>();
        int width = list.get(0).length();
        int height = list.size();
        int end = 0, sPos = 0, min = Integer.MAX_VALUE;
        for (int a = 0; a < width * height; a++) {
            char ch = list.get(a / width).charAt(a % width);
            end = ch == 'E' ? a : end;
            sPos = ch == 'S' ? a : sPos;
            if (ch == 'a' || ch == 'S') {
                positions.add(a);
            }
        }
        int[] visited = new int[width * height];
        int[] distances = new int[width * height];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(end);
        visited[end] = 1;
        while (!queue.isEmpty()) {
            int node = queue.remove();
            int x = node % width;
            int y = node / width;
            for (int[] vector : new int[][] {{1,0}, {-1,0}, {0,1}, {0,-1}}) {
                int nx = x + vector[0];
                int ny = y + vector[1];
                int nNode = ny * width + nx;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && visited[nNode] == 0) {
                    char ch = list.get(y).charAt(x);
                    int valueA = ch == 'E' ? 25 : ch - 'a';
                    ch = list.get(ny).charAt(nx);
                    int valueB = ch == 'S' ? 0 : ch - 'a';
                    if (valueA - valueB <= 1) {
                        queue.add(nNode);
                        visited[nNode] = 1;
                        distances[nNode] = distances[node] + 1;
                    }
                }
            }
        }
        for (int pos : positions) {
            min = Math.min(min, distances[pos] > 0 ? distances[pos] : Integer.MAX_VALUE);
            if (pos == sPos) {
                System.out.println("Part 1: " + distances[pos]);
            }
        }
        System.out.println("Part 2: " + min);
    }
}
