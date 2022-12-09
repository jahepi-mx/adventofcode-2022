package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day9 {

	public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day9/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day9 day9 = new Day9();
        System.out.println("Part 1: " + day9.run(list, 2));
        System.out.println("Part 2: " + day9.run(list, 10));
    }
	
    private int run(ArrayList<String> list, int size) {
    	HashSet<Integer> set = new HashSet<Integer>();
    	set.add(0);
    	int[][] rope = new int[size][2];
    	for (String line : list) {
    		int move = Integer.valueOf(line.split(" ")[1]);
    		while (move-- > 0) {
    			rope[0][0] += line.charAt(0) == 'R' ? 1 : 0;
    			rope[0][0] += line.charAt(0) == 'L' ? -1 : 0;
    			rope[0][1] += line.charAt(0) == 'U' ? 1 : 0;
    			rope[0][1] += line.charAt(0) == 'D' ? -1 : 0;
    			for (int a = 0; a < rope.length - 1; a++) {
		    		int subX = rope[a][0] - rope[a + 1][0];
		    		int subY = rope[a][1] - rope[a + 1][1];
		    		if (subX * subX + subY * subY >= 4) {
		    			subX = subX != 0 ? subX / Math.abs(subX) : subX;
		    			subY = subY != 0 ? subY / Math.abs(subY) : subY;
		    			rope[a + 1][0] += subX;
		    			rope[a + 1][1] += subY;
		    		}
    			}
    			set.add(rope[size - 1][1] * 1000 + rope[size - 1][0]);
    		}
    	}
        return set.size();
    }
}
