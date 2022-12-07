package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Day5 {

	public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day5/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day5 day5 = new Day5();
        day5.run(list);
    }
	
	private void parse(ArrayList<String> list) {
		for (int a = 0; a < list.size(); a++) {
			String line = list.get(a);
			if (line.charAt(1) == '1') {
				stacksA = new Stack[line.split("   ").length];
				stacksB = new Stack[line.split("   ").length];
				start = a;
				break;
			}
		}
		for (int a = start - 1; a >= 0; a--) {
			String line = list.get(a);
			for (int b = 0, i = 1; b < stacksA.length; b++, i += 4) {
				char ch = line.charAt(i);
				if (ch != ' ') {
					stacksA[b] = stacksA[b] == null ? new Stack<Character>() : stacksA[b];
					stacksB[b] = stacksB[b] == null ? new Stack<Character>() : stacksB[b];
					stacksA[b].push(ch);
					stacksB[b].push(ch);
				}
			}
		}
	}

	private Stack[] stacksA = null, stacksB = null;
	private int start = 0;
	
    private void run(ArrayList<String> list) {
    	parse(list);
    	for (int a = start + 2; a < list.size(); a++) {
    		String[] parts = list.get(a).split(" ");
    		int num = Integer.valueOf(parts[1]);
    		char[] chs = new char[num];
    		while (num-- > 0) {
    			stacksA[Integer.valueOf(parts[5]) - 1].push(stacksA[Integer.valueOf(parts[3]) - 1].pop());
    			chs[num] = (char) stacksB[Integer.valueOf(parts[3]) - 1].pop();
    		}
    		for (char ch : chs) {
    			stacksB[Integer.valueOf(parts[5]) - 1].push(ch);
    		}
    	}
    	String resA = "", resB = "";
    	for (int a = 0; a < stacksA.length; a++) {
    		resA += stacksA[a].peek();
    		resB += stacksB[a].peek();
    	}
    	System.out.println("Part 1: " + resA);
    	System.out.println("Part 2: " + resB);
    }
}
