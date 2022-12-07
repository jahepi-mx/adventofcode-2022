package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day4 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day4/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day4 day4 = new Day4();
        System.out.println("Part 1: " + day4.part1(list));
        System.out.println("Part 2: " + day4.part2(list));
    }

    private int part1(ArrayList<String> list) {
        int res = 0;
        for (String line : list) {
            String[] rangeA = line.split(",")[0].split("-");
            String[] rangeB = line.split(",")[1].split("-");
            int left = Math.max(Integer.valueOf(rangeA[0]), Integer.valueOf(rangeB[0]));
            int right = Math.min(Integer.valueOf(rangeA[1]), Integer.valueOf(rangeB[1]));
            int distA = Integer.valueOf(rangeA[1]) - Integer.valueOf(rangeA[0]);
            int distB = Integer.valueOf(rangeB[1]) - Integer.valueOf(rangeB[0]);
            res += right - left == distA || right - left == distB ? 1 : 0;
        }
        return res;
    }

    private int part2(ArrayList<String> list) {
        int res = 0;
        for (String line : list) {
            String[] rangeA = line.split(",")[0].split("-");
            String[] rangeB = line.split(",")[1].split("-");
            int left = Math.max(Integer.valueOf(rangeA[0]), Integer.valueOf(rangeB[0]));
            int right = Math.min(Integer.valueOf(rangeA[1]), Integer.valueOf(rangeB[1]));
            res += right >= left ? 1 : 0;
        }
        return res;
    }

}
