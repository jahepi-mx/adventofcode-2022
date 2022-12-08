package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day8 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day8/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day8 day8 = new Day8();
        System.out.println("Part 1: " + day8.part1(list));
        System.out.println("Part 2: " + day8.part2(list));
    }

    private int part1(ArrayList<String> list) {
        int res = 0;
        int width = list.get(0).length();
        int height = list.size();
        int[] flags = new int[width  * height];
        for (int y = 0; y < height; y++) {
            int maxLeft = Integer.MIN_VALUE;
            int maxRight = Integer.MIN_VALUE;
            int maxDown = Integer.MIN_VALUE;
            int maxUp = Integer.MIN_VALUE;
            for (int xLeft = 0, xRight = width - 1; xLeft < width; xLeft++, xRight--) {
                int num = list.get(y).charAt(xLeft) - '0';
                if (num > maxLeft) {
                    maxLeft = num;
                    res += flags[y * width + xLeft] == 0 ? 1 : 0;
                    flags[y * width + xLeft] = 1;
                }
                num = list.get(y).charAt(xRight) - '0';
                if (num > maxRight) {
                    maxRight = num;
                    res += flags[y * width + xRight] == 0 ? 1 : 0;
                    flags[y * width + xRight] = 1;
                }
                num = list.get(xLeft).charAt(y) - '0';
                if (num > maxDown) {
                    maxDown = num;
                    res += flags[xLeft * width + y] == 0 ? 1 : 0;
                    flags[xLeft * width + y] = 1;
                }
                num = list.get(xRight).charAt(y) - '0';
                if (num > maxUp) {
                    maxUp = num;
                    res += flags[xRight * width + y] == 0 ? 1 : 0;
                    flags[xRight * width + y] = 1;
                }
            }
        }
        return res;
    }

    private int part2(ArrayList<String> list) {
        int width = list.get(0).length();
        int height = list.size();
        int max = Integer.MIN_VALUE;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int num = list.get(y).charAt(x) - '0';
                int xTmp = 0, yTmp = 0, total = 1, count = 0;
                for (xTmp = x; ++xTmp < width; count++) {
                    xTmp = list.get(y).charAt(xTmp) - '0' >= num ? width : xTmp;     
                }
                total *= count;
                for (count = 0, xTmp = x; --xTmp >= 0; count++) {
                    xTmp = list.get(y).charAt(xTmp) - '0' >= num ? 0 : xTmp; 
                }
                total *= count;
                for (count = 0, yTmp = y; --yTmp >= 0; count++) {
                    yTmp = list.get(yTmp).charAt(x) - '0' >= num ? 0 : yTmp;    
                }
                total *= count;
                for (count = 0, yTmp = y; ++yTmp < height; count++) {
                    yTmp = list.get(yTmp).charAt(x) - '0' >= num ? height : yTmp;   
                }
                max = Math.max(total * count, max);
            }
        }
        return max;
    }
}
