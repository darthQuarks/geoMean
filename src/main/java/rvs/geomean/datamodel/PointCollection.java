package rvs.geomean.datamodel;

import java.util.ArrayList;
import java.util.List;

public class PointCollection extends ArrayList<Point> implements List<Point> {

    public PointCollection append(Point point) {
        add(point);
        return this;
    }
}
