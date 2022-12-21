package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Day18 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day18/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day18 day18 = new Day18();
        System.out.println("Part 1: " + day18.part1(list));
        System.out.println("Part 2: " + day18.part2(list));
    }

    class Vector {
        int x, y, z;
        Vector (int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public String toString() {
            return x + " " + y + " " + z;
        }
    }

    int[][] dirs = new int[][] {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}};
    
    private int part1(ArrayList<String> list) {
        int res = 0;
        HashSet<String> hash = new HashSet<>();
        Vector[] vectors = new Vector[list.size()];
        for (int a = 0; a < list.size(); a++) {
            String[] parts = list.get(a).split(",");
            Vector vector = new Vector(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
            vectors[a] = vector;
            hash.add(vector.toString());
        }
        for (Vector vector : vectors) {
            for (int[] dir : dirs) {
                res += hash.contains((vector.x + dir[0]) + " " + (vector.y + dir[1]) + " " + (vector.z + dir[2])) ? 0 : 1;
            }
        }
        return res;
    }

    private int part2(ArrayList<String> list) {
        int res = 0;
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
        int minZ = Integer.MAX_VALUE, maxZ = Integer.MIN_VALUE;
        HashSet<String> hash = new HashSet<>();
        Vector[] vectors = new Vector[list.size()];
        for (int a = 0; a < list.size(); a++) {
            String[] parts = list.get(a).split(",");
            Vector vector = new Vector(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
            minX = Math.min(minX, vector.x);
            maxX = Math.max(maxX, vector.x);
            minY = Math.min(minY, vector.y);
            maxY = Math.max(maxY, vector.y);
            minZ = Math.min(minZ, vector.z);
            maxZ = Math.max(maxZ, vector.z);
            vectors[a] = vector;
            hash.add(vector.toString());
        }
        for (Vector vector : vectors) {
            for (int[] dir : dirs) {
                Vector tmpVec = new Vector(vector.x + dir[0], vector.y + dir[1], vector.z + dir[2]);
                if (!hash.contains(tmpVec.toString())) {  
                    HashSet<String> visited = new HashSet<>();
                    Queue<Vector> queue = new LinkedList<>();
                    queue.add(tmpVec);
                    visited.add(tmpVec.toString());
                    boolean outOfBounds = false;
                    queue: while (!queue.isEmpty()) {
                        Vector vector2 = queue.remove();
                        for (int[] dir2 : dirs) {
                            Vector tmpVec2 = new Vector(vector2.x + dir2[0], vector2.y + dir2[1], vector2.z + dir2[2]);
                            String tmpVec2Key = tmpVec2.toString();
                            if (!hash.contains(tmpVec2Key) && !visited.contains(tmpVec2Key)) {
                                if (tmpVec2.x < minX || tmpVec2.x > maxX ||
                                    tmpVec2.y < minY || tmpVec2.y > maxY ||
                                    tmpVec2.z < minZ || tmpVec2.z > maxZ) {
                                    outOfBounds = true;
                                    break queue;
                                }
                                queue.add(tmpVec2);
                                visited.add(tmpVec2Key);
                            }
                        }     
                    }
                    if (!outOfBounds) {
                        for (String key : visited) {
                            hash.add(key);
                        }
                    }
                    break;
                }
            }
        }
        for (Vector vector : vectors) {
            for (int[] dir : dirs) {
                res += hash.contains((vector.x + dir[0]) + " " + (vector.y + dir[1]) + " " + (vector.z + dir[2])) ? 0 : 1;
            }
        }
        return res;
    }
}
