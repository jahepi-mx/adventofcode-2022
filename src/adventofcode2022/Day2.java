package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day2 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day2/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day2 day2 = new Day2();
        day2.run(list);
    }

    private void run(ArrayList<String> list) {
        int total1 = 0, total2 = 0;
        for (String outcome : list) {
            int index = "AXAYAZBXBYBZCXCYCZ".indexOf(outcome.charAt(0) + "" + outcome.charAt(2)) / 2;
            total1 += "483159726".charAt(index) - 48;
            total2 += "348159267".charAt(index) - 48;
        }
        System.out.println("Part 1: " + total1);
        System.out.println("Part 2: " + total2);
    }
}
