package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day3 {

	public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day3/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day3 day3 = new Day3();
        System.out.println(day3.part1(list));
        System.out.println(day3.part2(list));
    }

    private int part1(ArrayList<String> list) {
    	int res = 0;
    	for (String line : list) {
    		int[] hash = new int[255];
    		for (int a = 0; a < line.length() / 2; a++) {
    			hash[line.charAt(a)] = 1;
    		}
    		for (int a = line.length() / 2; a < line.length(); a++) {
    			int ch =  line.charAt(a);
				res += hash[ch] == 1 ? ch - (ch >= 'a' ? 96 : 38) : 0;
				hash[ch] = 0;
    		}
    	}
        return res;
    }

    private int part2(ArrayList<String> list) {
    	int res = 0;
    	for (int a = 0; a < list.size(); a += 3) {
    		int[] hash = new int[255];
    		for (int ch : list.get(a).getBytes()) {
    			hash[ch] = 1;
    		}
    		for (int ch : list.get(a + 1).getBytes()) {
    			hash[ch] = hash[ch] == 1 ? 2 : hash[ch];
    		}
    		for (int ch : list.get(a + 2).getBytes()) {
				res += hash[ch] == 2 ? ch - (ch >= 'a' ? 96 : 38) : 0;
				hash[ch] = 0;
    		}
    	}
        return res;
    }
}
