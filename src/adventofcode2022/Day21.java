package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day21 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day21/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day21 day21 = new Day21();
        System.out.println("Part 1: " + day21.part1(list));
        System.out.println("Part 2: " + day21.part2(list));
    }

    HashMap<String, String> map = new HashMap<>();
    private long part1(ArrayList<String> list) {
        for (String line : list) {
            String[] parts = line.split(":");
            map.put(parts[0], parts[1].trim());
        }
        return math("root", 1);
    }
    
    private long part2(ArrayList<String> list) {
        for (String line : list) {
            String[] parts = line.split(":");
            map.put(parts[0], parts[1].trim());
        }
        long left = 0;
        long right = 1_000_000_000_000_000l;
        while (true) {
            long middle = (left + right) / 2;
            map.put("humn", middle + "");
            long diff = math("root", 0);
            if (diff > 0) {
                left = middle;
            } else if (diff < 0) {
                right = middle;
            } else {
                return middle;
            }
        }
    }
    
    long math(String key, int flag) {
        String exp = map.get(key);
        if (exp.charAt(0) >= '0' && exp.charAt(0) <= '9') {
            return Long.valueOf(exp);
        }
        String key1 = exp.charAt(0) + "" + exp.charAt(1) + "" + exp.charAt(2) + "" + exp.charAt(3);
        String key2 = exp.charAt(7) + "" + exp.charAt(8) + "" + exp.charAt(9) + "" + exp.charAt(10);
        if (exp.charAt(5) == '+') {
            long a = math(key1, 1);
            long b = math(key2, 1);
            if (flag == 0) {
                return a - b;
            }
            return a + b;
        } else if (exp.charAt(5) == '-') {
            return math(key1, 1) - math(key2, 1);
        } else if (exp.charAt(5) == '*') {
            return math(key1, 1) * math(key2, 1);
        }
        return math(key1, 1) / math(key2, 1);
    }
}
