package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day10 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day10/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day10 day10 = new Day10();
        day10.run(list);
    }
    
    private void run(ArrayList<String> list) {
        int[] screen = new int[40 * 6];
        int tick = 1, x = 1, res = 0, start = 20;
        for (String line : list) {
            int ly = tick / 40;
            int lx = tick % 40;
            if (lx >= (x - 1) && lx < (x + 2)) {
                screen[ly * 40 + lx] = 1;
            }
            res += ++tick % start == 0 ? tick * x : 0;
            start += tick % start == 0 ? 40 : 0;
            if (!line.equals("noop")) {
                x += Integer.valueOf(line.split(" ")[1]);
                ly = tick / 40;
                lx = tick % 40;
                if (lx >= (x - 1) && lx < (x + 2)) {
                    screen[ly * 40 + lx] = 1;
                }
                res += ++tick % start == 0 ? tick * x : 0;
                start += tick % start == 0 ? 40 : 0;
            }
        }
        System.out.println("Part 1: " + res);
        for (int y = 0; y < 6; y++) {
            for (x = 0; x < 40; x++) {
                System.out.print(screen[y * 40 + x] == 1 ? '#' : ' ');
            }
            System.out.println("");
        }
    }
}