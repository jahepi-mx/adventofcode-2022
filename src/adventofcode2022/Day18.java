package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

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
        System.out.println(day18.part1(list));
        System.out.println(day18.part2(list));
    }

    class Vector {
        int x, y, z;
        public String toString() {
            return x + " " + y + " " + z;
        }
    }

    private int part1(ArrayList<String> list) {
        int res = 0, index = 0;
        HashSet<String> hash = new HashSet<>();
        Vector[] vectors = new Vector[list.size()];
        for (String line : list) {
            String[] parts = line.split(",");
            Vector vector = new Vector();
            vector.x = Integer.valueOf(parts[0]); 
            vector.y = Integer.valueOf(parts[1]);
            vector.z = Integer.valueOf(parts[2]);
            vectors[index++] = vector;
            hash.add(vector.toString());
        }
        for (Vector vector : vectors) {
            for (int[] dir : new int[][] {{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}}) {
                int nx = vector.x + dir[0];
                int ny = vector.y + dir[1];
                int nz = vector.z + dir[2];
                res += hash.contains(nx + " " + ny + " " + nz) ? 0 : 1;
            }
        }
        return res;
    }

    private int part2(ArrayList<String> list) {
        int res = 0;

        return res;
    }

}
