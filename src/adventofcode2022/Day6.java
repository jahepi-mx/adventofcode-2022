package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day6 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input/day6/input.txt"));
        Day6 day6 = new Day6();
        String str = reader.readLine();
        reader.close();

        System.out.println("Part 1: " + day6.getPosition(str, 4));
        System.out.println("Part 2: " + day6.getPosition(str, 14));
    }

    private int getPosition(String str, int nUniqueChs) {
        int left = 0, right = 0, count = 0;
        int[] hash = new int[255];
        while (right < str.length()) {
            hash[str.charAt(right)]++;
            count += hash[str.charAt(right)] == 2 ? 1 : 0;
            if (right >= nUniqueChs) {
                count -= hash[str.charAt(left)] == 2 ? 1 : 0;
                hash[str.charAt(left++)]--;
                if (count == 0) {
                    return ++right;
                }
            }
            right++;
        }
        return -1;
    }
}
