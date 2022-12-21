package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day20 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day20/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day20 day20 = new Day20();
        System.out.println("Part 1: " + day20.run(list, 1, 1));
        System.out.println("Part 2: " + day20.run(list, 10, 811589153l));
    }

    class Node {
        Number num;
        Node prev, next;
        int index;
    }

    class Number {
        long num;
    }

    class DoubleLinkedList {
        Node start;
        Node prev;
        int size;

        void add(Number num) {
            Node node = new Node();
            node.num = num;
            node.prev = prev;
            if (prev != null) {
                prev.next = node;
            }
            if (start == null) {
                start = node;
            }
            node.next = start;
            start.prev = node;
            prev = node;
            size++;
        }

        Node find(Number num) {
            Node tmp = start;
            for (int a = 0; a < size; a++) {
                if (tmp.num == num) {
                    tmp.index = a;
                    return tmp;
                }
                tmp = tmp.next;
            }
            return null;
        }

        void move(Number n) {
            if (n.num == 0) {
                return;
            }
            Node node = find(n);
            Node tmp = node;

            long target = (node.index + n.num) % (size - 1);
            target = target < 0 ? target + (size - 1) : target;
            long to = target - node.index;
            if (to == 0) {
                return;
            }

            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
            if (node == start) {
                start = node.next;
            }

            for (int a = 0; a < (to < 0 ? Math.abs(to) + 1 : to); a++) {
                tmp = to < 0 ? tmp.prev : tmp.next;
            }

            Node nextTmp = tmp.next;
            tmp.next = node;
            nextTmp.prev = node;
            node.prev = tmp;
            node.next = nextTmp;
        }

        Number[] toArray() {
            Node tmp = start;
            Number[] arr = new Number[size];
            for (int a = 0; a < size; a++) {
                arr[a] = tmp.num;
                tmp = tmp.next;
            }
            return arr;
        }
    }


    private long run(ArrayList<String> list, int iterations, long factor) {
        DoubleLinkedList col = new DoubleLinkedList();
        for (String line : list) {
            long n = Long.valueOf(line) * factor;
            Number num = new Number();
            num.num = n;
            col.add(num);
        }
        Number[] arr = col.toArray();
        for (int i = 0; i < iterations; i++) {
            for (Number num : arr) {
                col.move(num);
            }
        }
        arr = col.toArray();
        int index = 0;
        for (int a = 0; a < arr.length; a++) {
            Number num = arr[a];
            index = num.num == 0 ? a : index;
        }
        long sum = 0;
        int[] offsets = new int[] {1000, 2000, 3000};
        for (int offset : offsets) {
            int newIndex = (index + offset) % arr.length;
            sum += arr[newIndex].num;
        }
        return sum;
    }
}
