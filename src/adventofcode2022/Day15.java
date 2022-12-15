package adventofcode2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day15 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day15/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day15 day15 = new Day15();
        day15.run(list);
    }
    
    class DataPoint {
        long distance, x, y;
    }
    
    private void run(ArrayList<String> list) {
        long res = 0, offsetX = 2000_000, offsetY = 2000_000, mapWidth = 10_000_000;
        int index = 0, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        HashSet<Long> map = new HashSet<>();
        DataPoint[] dataPoints = new DataPoint[list.size()];
        for (String line : list) {
            String[] parts = line.split(":");
            long sensorX = Long.valueOf(parts[0].split(",")[0].split("=")[1]) + offsetX;
            long sensorY = Long.valueOf(parts[0].split(",")[1].split("=")[1]) + offsetY;
            long beaconX = Long.valueOf(parts[1].split(",")[0].split("=")[1]) + offsetX;
            long beaconY = Long.valueOf(parts[1].split(",")[1].split("=")[1]) + offsetY;
            map.add(sensorY * mapWidth + sensorX);
            map.add(beaconY * mapWidth + beaconX);
            long distance = Math.abs(sensorX - beaconX) + Math.abs(sensorY - beaconY);
            DataPoint point = new DataPoint();
            point.distance = distance;
            point.x = sensorX;
            point.y = sensorY;
            dataPoints[index++] = point; 
            min = Math.min(min, (int) (sensorX - (offsetX + distance)));
            max = Math.max(max, (int) (sensorX + distance - offsetX ));
        }
        for (int x = min; x <= max; x++) {
            long newX = x + offsetX;
            long newY = 2_000_000 + offsetY;
            for (DataPoint point : dataPoints) {
                long distance = Math.abs(point.x - newX) + Math.abs(point.y - newY);
                if (distance <= point.distance && !map.contains(newY * mapWidth + newX)) {
                    res++;
                    break;
                }
            }
        }
        System.out.println("Part 1: " + res);

        outer: for (int y = 0; y <= 4_000_000; y++) {
            for (int x = 0; x <= 4_000_000; x++) {
                long newX = x + offsetX;
                long newY = y + offsetY;
                boolean found = false;
                if (!map.contains(newY * mapWidth + newX)) {
                    for (DataPoint point : dataPoints) {
                        long distance = Math.abs(point.x - newX) + Math.abs(point.y - newY);
                        if (distance <= point.distance) {
                            found = true;
                            x += point.distance - distance;
                            break;
                        }
                    }
                    if (!found) {
                        res = (newX - offsetX) * 4_000_000l + (newY - offsetY);
                        System.out.println("Part 2: " + res);
                        break outer;
                    }
                }
            }
        }
    }
}
