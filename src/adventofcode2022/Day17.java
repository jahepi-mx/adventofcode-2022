package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Day17 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day17/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        long start = System.currentTimeMillis();
        Day17 day17 = new Day17();
        System.out.println(day17.run(list));
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000d);
    }
    
   
    HashMap<String, Integer> counts = new HashMap<>();
    private long run(ArrayList<String> list) {
        int[][][] shapes = new int[][][] {
            {{1,1,1,1}},
            
            {{0,1,0},
             {1,1,1},
             {0,1,0}},
            
            {{0,0,1},
            {0,0,1},
            {1,1,1}},
            
            {{1},
             {1},
             {1},
             {1}},
             
            {{1,1},
             {1,1}}
        };
        
        /*
         Rock: 44   72
         Rock: 79   125
         Rock: 114  178
         Rock: 149
         Rock: 184
         Rock: 219
         Rock: 254
         Rock: 289
         Rock: 324


          72 + (53x) = 1_000_000_000_000
          53x = 1_000_000_000_000 - 72
          x = (1_000_000_000_000 - 72) / 53
          50
          rock 96
          18867924526
         */
        /*long res = (1_000_000_000_000l - 44) / 35 * 53;
        long remain = (1_000_000_000_000l - 44) % 35;
        System.out.println("Res: " + res); 
        System.out.println("Res: " + (1514285714288l - res)); 
        System.out.println("Remain: " + remain); 
        */
        
        /*
         Rock: 285  433
         Rock: 1975  3080
         Rock: 3665  5727
         Rock: 5355  8374
         Rock: 7045  11021
         
         1690        2647
         */
        
        long res = (1_000_000_000_000l - 285) / 1690 * 2647;
        long remain = (1_000_000_000_000l - 285) % 1690;
        System.out.println("Res: " + (res + 880)); 
        //System.out.println("Res: " + (1514285714288l - res)); 
        System.out.println("Remain: " + remain); 
        
        
        String commands = list.get(0);
        long rocks = 560;
        long yMax = 0;
        long xOffset = 2;
        int cmdIndex = 0;
        HashSet<Long> map = new HashSet<>();
        for (long rock = 0; rock < rocks; rock++) {
            int[][] shape = shapes[(int) (rock % shapes.length)];
            long xLeft = xOffset;
            long y = yMax + (shape.length - 1) + 3;
            boolean yCollide = false;
            /*
            if (cmdIndex % commands.length() == 2 && (int) (rock % shapes.length) == 4) {
                System.out.println("Rock: " + rock);
            }*/
            if (cmdIndex % commands.length() == 1702 && (int) (rock % shapes.length) == 0) {
                System.out.println("Rock: " + rock + "  " + yMax);
            }
            int ka = (int) (rock % shapes.length);
            int kb = cmdIndex % commands.length();
            counts.put(kb + "," + ka, counts.containsKey(kb + "," + ka) ? counts.get(kb + "," + ka) + 1 : 1);
            
            while (!yCollide) {
                boolean xCollide = false;
                cmdIndex = cmdIndex % commands.length();
                char cmd = commands.charAt(cmdIndex++);
                int xDir = cmd == '<' ? -1 : 1; 
                
                for (int ys = 0; ys < shape.length; ys++) {
                    for (int xs = 0; xs < shape[ys].length; xs++) {
                        if (shape[ys][xs] == 1) {
                            long nx = xLeft + xs + xDir;
                            if (nx < 0 || nx >= 7 || map.contains((y - ys) * 7 + nx)) {
                                xCollide = true;
                            }
                        }
                    }
                }
                if (!xCollide) {
                    xLeft += xDir;
                }
                
                for (int ys = 0; ys < shape.length; ys++) {
                    for (int xs = 0; xs < shape[ys].length; xs++) {
                        if (shape[ys][xs] == 1) {
                            long nx = xs + xLeft;
                            long ny = y - (ys + 1);
                            if (ny < 0 || map.contains(ny * 7 + nx)) {
                                yCollide = true;
                            }
                        }
                    }
                }
                if (yCollide) {
                    for (int ys = 0; ys < shape.length; ys++) {
                        for (int xs = 0; xs < shape[ys].length; xs++) {
                            if (shape[ys][xs] == 1) {
                                long nx = xs + xLeft;
                                map.add((y - ys) * 7 + nx);
                                yMax = Math.max(yMax, y - ys + 1);
                            }
                        }
                    }
                } else {
                    y--;
                }
            }
            
            if (rock > 0 && rock % 1_000 == 0) {
                clean(map, yMax);
            }
            //print(map);
        }
        
        /*for (String key : counts.keySet()) {
            System.out.println(key + ": " + counts.get(key));
        }
        */
        return yMax;
    }
    
    int cleanCount = 0;
    long fromY = 0;
    private void clean(HashSet<Long> map, long yMax) {
        //System.out.println(yMax);
        for (long y = fromY; y < yMax - 100; y++) {
            for (long x = 0; x < 7; x++) {
                map.remove(y * 7 + x);
            }
        }
        fromY = yMax - 100;
        //System.out.println(map.size());
        cleanCount++;
    }
    
    private void print(HashSet<Long> map) {
        System.out.println("");
        for (int y = 10; y >= 0; y--) {
            for (int x = 0; x < 7; x++) {
                if (map.contains(y * 7 + x)) {
                    System.out.print('#');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println("");
        }
    }
}
