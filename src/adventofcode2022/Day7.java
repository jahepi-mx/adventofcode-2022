package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Day7 {
	public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day7/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day7 day7 = new Day7();
        day7.run(list);
    }
	
	private class Dir {
		Dir parent;
		HashMap<String, Dir> dirs = new HashMap<>();
		int filesSize = 0;
		public int size() {
			int size = 0;
			for (String key : dirs.keySet()) {
				size += dirs.get(key).size();
			}
			return size + filesSize;
		}
	}

    private void run(ArrayList<String> list) {
    	Dir root = new Dir(), current = root;
    	for (String line : list) {
    		String[] parts = line.split(" ");
    		if (line.indexOf("$ cd ..") >= 0) {
    			current = current.parent;
    		} else if (line.indexOf("$ cd") >= 0) {
    			current = parts[2].charAt(0) != '/' ? current.dirs.get(parts[2]) : current;
    		} else if (line.charAt(0) != '$') {
    			if (line.indexOf("dir") >= 0) {
    				Dir dir = new Dir();
    				dir.parent = current;
    				current.dirs.put(parts[1], dir);
    			} else {
    				current.filesSize += Integer.valueOf(parts[0]);
    			}
    		}
    	}
    	int resA = 0, resB = 0;
    	Stack<Dir> stack = new Stack<>();
    	stack.push(root);
    	int leftOver = 70_000_000 - root.size();
    	int minDist = Integer.MAX_VALUE;
    	while (!stack.isEmpty()) {
    		Dir dir = stack.pop();
    		int dirSize = dir.size();
    		resA += dirSize <= 100_000 ? dirSize : 0;
    		int left = leftOver + dirSize - 30_000_000;
			if (left >= 0 && left <= minDist) {
				minDist = left;
				resB = dirSize;
			}
    		for (String key : dir.dirs.keySet()) {
    			stack.push(dir.dirs.get(key));
    		}
    	}
    	System.out.println("Part 1: " + resA);
    	System.out.println("Part 2: " + resB);
    }
}
