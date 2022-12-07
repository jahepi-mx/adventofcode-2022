package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day1 {

	public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day1/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day1 day1 = new Day1();
        System.out.println(day1.part1(list));
        System.out.println(day1.part2(list));
    }

    private int part1(ArrayList<String> list) {
    	int max = Integer.MIN_VALUE;
    	int sum = 0;
    	for (String str : list) {
    		sum = str.equals("") ? 0 : sum + Integer.valueOf(str);
    		max = Math.max(max, sum);
    	}
        return max;
    }
   
    private int part2(ArrayList<String> list) {
    	int[] max = new int[3];
    	int sum = 0;
    	for (String str : list) {
    		sum = str.equals("") ? 0 : sum + Integer.valueOf(str);
    		if (sum >= max[0]) {
    			int tmp1 = max[0];
    			int tmp2 = max[1];
    			max[0] = sum;
    			max[1] = tmp1;
    			max[2] = tmp2;
    		} else if (sum >= max[1]) {
    			int tmp1 = max[1];
    			max[1] = sum;
    			max[2] = tmp1;
    		} else if (sum >= max[2]) {
    			max[2] = sum;
    		}
    		
    	}
        return max[0] + max[1] + max[2];
    }
}
