package rvs.geomean.datamodel;

import java.util.ArrayList;
import java.util.List;

public class PointCollection extends ArrayList<Point> implements List<Point> {

    int currentIndex = 0;

    public Point getLast() {
        if (size() > 0) {
            return get(size() - 1);
        }
        return null;
    }

    public Point getNext() {
        if (currentIndex >= size()) {
            return null;
        }
        return get(currentIndex++);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void resetCurrentIndex() {
        currentIndex = 0;
    }
}
