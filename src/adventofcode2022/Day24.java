package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day24 {
    
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day24/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day24 day24 = new Day24();
        System.out.println("Part 1: " + day24.run(list, 1));
        System.out.println("Part 2: " + day24.run(list, 3));
    }
    
    int width, height;   
    int[][]  dirs = new int[][] {{1,0},{0,1},{-1,0},{0,-1},{0,0}};
    int[][] states;
    
    class Blizzard {
        int x, y, dir;
        
        Blizzard(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
        
        void move() {
            int[] vector = dirs[dir];
            x += vector[0];
            y += vector[1];
            x = x == 0 ? width - 2 : (x == width - 1 ? 1 : x);
            y = y == 0 ? height - 2 : (y == height - 1 ? 1 : y);
        }
    }
    
    private int run(ArrayList<String> list, int iterarions) {
        int player = 1;
        int target;
        ArrayList<Blizzard> blizzards = new ArrayList<>();
        ArrayList<Integer> walls = new ArrayList<>();
        
        height = list.size();
        for (int y = 0; y < list.size(); y++) {
            width = list.get(y).length();
            for (int x = 0; x < list.get(y).length(); x++) {
                char ch = list.get(y).charAt(x);
                int dir = ch == '>' ? 0 : (ch == 'v' ? 1 : (ch == '<' ? 2 : (ch == '^' ? 3 : -1)));
                if (dir >= 0) {
                    blizzards.add(new Blizzard(x, y, dir));
                }
                if (ch == '#') {
                    walls.add(y * width + x);
                }
            }
        }
        target = (height - 1) * width + (width - 2);
        states = new int[1024][width * height];
        for (int a = 0; a < states.length; a++) {
            for (Blizzard blizzard : blizzards) {
                states[a][blizzard.y * width + blizzard.x] = 1;
            }
            for (int wall : walls) {
                states[a][wall] = 1;
            }
            for (Blizzard blizzard : blizzards) {
                blizzard.move();
            }
        }
        
        int minute = 0;
        for (int i = 0; i < iterarions; i++) {
            mem = new int[width * height][states.length];
            for (int a = 0; a < width * height; a++) {
                for (int b = 0; b < mem[a].length; b++) {
                    mem[a][b] = -1;
                }
            }
            minute = find(player, target, minute);
            int tmp = target;
            target = player;
            player = tmp;
        }
        return minute;
    }

    int[][] mem;
    int find(int player, int target, int minutes) {
        
        int tmpMinutes = Integer.MAX_VALUE;
        
        if (player == target) {
            return minutes;
        }
        
        if (minutes >= states.length - 1) {
            return tmpMinutes;
        }
        
        if (mem[player][minutes] >= 0) {
            return mem[player][minutes];
        }
        
        int[] state = states[minutes + 1];
        
        int playerX = player % width;
        int playerY = player / width;
        boolean found = false;
        for (int[] vector : dirs) {
            int tmpX = playerX + vector[0];
            int tmpY = playerY + vector[1];
            if (tmpY >= 0 && tmpX >= 0 && tmpX < width && tmpY < height
                    && state[tmpY * width + tmpX] == 0) {
                found = true;
                tmpMinutes = Math.min(tmpMinutes, find(tmpY * width + tmpX, target, minutes + 1));
            }
        }
        if (found == false) {
            tmpMinutes = Integer.MAX_VALUE;
        }
        
        mem[player][minutes] = tmpMinutes;
        return tmpMinutes;
    }
}
