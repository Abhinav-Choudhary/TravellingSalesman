package christofidesAlgo;

import java.util.*;

public class BinaryHeap {
    // Given the satellite, this is its key
    ArrayList<Double> key = new ArrayList<>();
    // Given the satellite, this is its position in the heap
    ArrayList<Integer> pos = new ArrayList<>();
    // This is the heap!
    ArrayList<Integer> satellite = new ArrayList<>();

    // Number of elements in the heap
    int size = 0;

    public BinaryHeap() {
        satellite.add(0);
    }


    void clear() {
        key.clear();
        pos.clear();
        satellite.clear();
    }

    public void insert(double k, int s) throws Exception {
        // Adjust the structures to fit new data
        if (s >= (int) pos.size()) {
            pos.add(-1);
            key.add(0.0);
            // Recall that position 0 of satellite is unused
            satellite.add(0); satellite.add(0);
        }
        //If satellite is already in the heap
        else if (pos.get(s) != -1) {
            throw new Exception("Error: satellite already in heap");
        }

        int i;
        for (i = ++size; i / 2 > 0 && (key.get(satellite.get(i / 2)) > k); i /= 2) {
            satellite.set(i, satellite.get(i / 2));
            pos.set(satellite.get(i), i);
        }
        satellite.set(i, s);
        pos.set(s, i);
        key.set(s, k);
    }

    int size() {
        return size;
    }

    public int deleteMin() throws Exception {
        if (size == 0) throw new Exception("Error: empty heap");

        int min = satellite.get(1);
        int slast = satellite.get(size--);

        int child;
        int i;
        for (i = 1, child = 2; child <= size; i = child, child *= 2) {
            if (child < size && (key.get(satellite.get(child)) > key.get(satellite.get(child + 1))))
                child++;

            if (key.get(slast) > key.get(satellite.get(child))) {
                satellite.set(i, satellite.get(child));
                pos.set(satellite.get(child), i);
            } else
                break;
        }
        satellite.set(i, slast);
        pos.set(slast, i);

        pos.set(min, -1);
        return min;
    }
}