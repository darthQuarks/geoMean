package rvs.geomean.datamodel;

public class Point {

    private static double scale = 1e12;
    private final double x, y, z, latitude, longitude;

    public Point(String latitude, String longitude) {
        this(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    public Point(double degreesLatitude, double degreesLongitude) {
        // By calling cartesian constructor, lat and lon are automatically sanitized
        this(Math.cos(Math.toRadians(degreesLatitude)) * Math.cos(Math.toRadians(degreesLongitude)),
                Math.cos(Math.toRadians(degreesLatitude)) * Math.sin(Math.toRadians(degreesLongitude)),
                Math.sin(Math.toRadians(degreesLatitude)));
    }

    public Point(double x, double y, double z) {
        double radius = Math.sqrt(x * x + y * y + z * z); // Sanitize coordinates
        this.x = x / radius;
        this.y = y / radius;
        this.z = z / radius;
        this.latitude = Math.asin(this.z);
        this.longitude = Math.atan2(this.y, this.x);
    }

    public static void setScale(double scale) {
        Point.scale = scale;
    }

    public static double getScale() {
        return scale;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getLattitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDegreesLatitude() {
        return Math.round(scale * Math.toDegrees(latitude)) / scale;
    }

    public double getDegreesLongitude() {
        return Math.round(scale * Math.toDegrees(longitude)) / scale;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getDegreesLatitude())
                .append(", ")
                .append(getDegreesLongitude())
                .toString();
    }

    public String pretty() {
        StringBuilder builder = new StringBuilder();
        if (latitude < 0) {
            builder.append(Math.abs(getDegreesLatitude()) + "\u00B0S");
        } else {
            builder.append(getDegreesLatitude() + "\u00B0N");
        }
        builder.append(", ");
        if (longitude < 0) {
            builder.append(Math.abs(getDegreesLongitude()) + "\u00B0W");
        } else {
            builder.append(getDegreesLongitude() + "\u00B0E");
        }
        return builder.toString();
    }

}
