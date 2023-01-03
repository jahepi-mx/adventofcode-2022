package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day25 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day25/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day25 day25 = new Day25();
        System.out.println("Part 1: " + day25.part1(list));
    }

    private String part1(ArrayList<String> list) {
        long res = 0;
        for (String snafu : list) {
            res += toDec(snafu);
        }
        return toSNAFU(res);
    }
    
    int[] values = new int[] {-2, -1, 0, 1, 2};
    String keyStr = "=-012";
    
    private long toDec(String snafu) {
        long dec = 0, base = 1;
        for (int a = snafu.length() - 1; a >= 0; a--, base *= 5) {
            dec += values[keyStr.indexOf(snafu.charAt(a))] * base;
        }
        return dec;
    }

    private String toSNAFU(long n) {
        String str = "";
        boolean flag = false;
        long num =  (long) Math.pow(5, (int) Math.ceil(Math.log(n) / Math.log(5)));
        while (num > 0) {
            long minDist = Long.MAX_VALUE;
            int value = 0;
            for (int a : values) {
                if (Math.abs(n -  num * a) <= minDist) {
                    minDist = Math.abs(n -  num * a);
                    value = a;
                }
            }
            flag = value != 0 ? true : flag;
            str += flag ? keyStr.charAt(value + 2) : "";
            n -=  num * value;
            num /= 5;
        }
        return str;
    }
}
