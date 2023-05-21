package rvs.geomean.service;

import org.springframework.stereotype.Service;
import rvs.geomean.datamodel.Point;
import rvs.geomean.datamodel.PointCollection;

@Service
public class PointServiceImpl implements PointService {
    
    private double getDotProduct(Point current, Point next) {
        return current.getX() * next.getX()
                + current.getY() * next.getY()
                + current.getZ() * next.getZ();
    }

    public Point getMeanPoint(Point current, Point next, double mass) {
        if (current == null) {
            return next;
        }
        double dotProduct = getDotProduct(current, next);
        if (dotProduct == 0) {
            return current;
        }
        double angle = Math.acos(dotProduct);
        double angleToMove = angle / (mass + 1) ;

        double orthogonalNorm = 1 / Math.sqrt(1 - dotProduct * dotProduct);

        double orthonormalNextX = (next.getX() - dotProduct * current.getX() )* orthogonalNorm;
        double orthonormalNextY = (next.getY() - dotProduct * current.getY()) * orthogonalNorm;
        double orthonormalNextZ = (next.getZ() - dotProduct * current.getZ()) * orthogonalNorm;

        double meanX = Math.cos(angleToMove) * current.getX() + Math.sin(angleToMove) * orthonormalNextX;
        double meanY = Math.cos(angleToMove) * current.getY() + Math.sin(angleToMove) * orthonormalNextY;
        double meanZ = Math.cos(angleToMove) * current.getZ() + Math.sin(angleToMove) * orthonormalNextZ;

        return new Point(meanX, meanY, meanZ);
    }

    public Point getMeanPoint(PointCollection pointCollection) {
        double mass = 0;
        Point meanPoint = null;
        for (Point point : pointCollection) {
            meanPoint = getMeanPoint(meanPoint, point, mass);
            mass++;
        }
        return meanPoint;
    }
}
