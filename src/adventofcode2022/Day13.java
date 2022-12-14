package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Day13 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day13/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                list.add(line);
            }
        }
        reader.close();
        Day13 day13 = new Day13();
        day13.run(list);
    }

    private void run(ArrayList<String> list) {
        int res = 0, index = 0;
        Object[] data = new Object[list.size() + 2];
        for (int a = 0; a < list.size(); a += 2) {
            Object left = parse(list.get(a));
            Object right = parse(list.get(a + 1));
            res += check(left, right) >= 0 ? a / 2 + 1 : 0;
            data[index++] = left;
            data[index++] = right;
        }
        data[index++] = parse("[[2]]");
        data[index++] = parse("[[6]]");
        System.out.println("Part 1 : " + res);
        sort(data, data[index - 1], data[index - 2]);
    }
    
    private void sort(Object[] data, Object target1, Object target2) {
        for (int a = 0; a < data.length - 1; a++) {
            for (int b = 0; b < data.length - a - 1; b++) {
                Object left = data[b];
                Object right = data[b + 1];
                int c = check(left, right);
                if (c == -1) {
                    data[b + 1] = left;
                    data[b] = right;
                }
            }
        }
        int i = 0, res = 1;
        for (Object m : data) {
            i++;
            res *= m == target1 || m == target2 ? i : 1;
        }
        System.out.println("Part 1 : " + res);
    }

    int check(Object left, Object right) {
        LinkedList<Object> tmpLeft = left instanceof LinkedList ? (LinkedList<Object>) left : new LinkedList<>();
        LinkedList<Object> tmpRight = right instanceof LinkedList ? (LinkedList<Object>) right : new LinkedList<>();
        int res = 0;
        if (left instanceof Integer) {
            tmpLeft.add(left);
        }
        if (right instanceof Integer) {
            tmpRight.add(right);
        }
        if (tmpLeft.size() == 0 && tmpRight.size() > 0) {
            return 1;
        }
        for (int a = 0; a < tmpLeft.size(); a++) {
            Object cLeft = tmpLeft.get(a);
            if (a >= tmpRight.size()) {
                return -1;
            }
            Object cRight = tmpRight.get(a);
            if (cLeft instanceof LinkedList || cRight instanceof LinkedList) {
                res = check(cLeft, cRight);
                if (res != 0) {
                    return res;
                }
            } else if ((int) cRight > (int) cLeft) {
                return 1;
            } else if ((int) cLeft > (int) cRight) {
                return -1;
            }
            if (tmpLeft.size() == a + 1 && tmpRight.size() > tmpLeft.size()) {
                return 1;
            }
        }
        return 0;
    }
    
    private Object parse(String str) {
        Stack<Object> stack = new Stack<>();
        String num = "";
        for (char ch : str.toCharArray()) {
            if (ch == ']') {
                if (num.length() > 0) {
                    stack.push(num);
                    num = "";
                }
                LinkedList<Object> tmp = new LinkedList<>();
                while (!stack.isEmpty()) {
                    Object elem = stack.pop();
                    if (elem instanceof String) {
                        if (elem.equals("[")) {
                            break;
                        }
                        tmp.addFirst(Integer.valueOf((String) elem));
                    } else {
                        tmp.addFirst(elem); 
                    }
                }
                stack.push(tmp);
            } else if (ch == '[') {
                stack.push(ch + "");
            } else if (ch == ',') {
                if (num.length() > 0) {
                    stack.push(num);
                    num = "";
                }
            } else {
                num = num + ch;
            }
        }
        return stack.pop();
    }
}
