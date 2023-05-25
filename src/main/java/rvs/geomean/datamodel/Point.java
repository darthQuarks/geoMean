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

    public String prettyInMinutes() {
        double degreesLatitude = Math.abs(getDegreesLatitude());
        double degreesLongitude = Math.abs(getDegreesLongitude());
        StringBuilder builder = new StringBuilder();
        builder.append(formatNumber(degreesLatitude, 0,"\u00B0"))
                .append(formatNumber(getMinutes(degreesLatitude), 2, "\u2032"))
                .append(latitude < 0 ? "S" :  "N");

        builder.append(formatNumber(degreesLongitude, 0,"\u00B0"))
                .append(formatNumber(getMinutes(degreesLongitude), 2, "\u2032"))
                .append(latitude < 0 ? "W" :  "E");

        return builder.toString();
    }

    public String prettyInSeconds() {
        double degreesLatitude = Math.abs(getDegreesLatitude());
        double degreesLongitude = Math.abs(getDegreesLongitude());
        StringBuilder builder = new StringBuilder();
        builder.append(formatNumber(degreesLatitude, 0,"\u00B0"))
                .append(formatNumber(getMinutes(degreesLatitude), 0, "\u2032"))
                .append(formatNumber(getSeconds(degreesLatitude), 2, "\u2033"))
                .append(latitude < 0 ? "S" :  "N");

        builder.append(formatNumber(degreesLongitude, 0,"\u00B0"))
                .append(formatNumber(getMinutes(degreesLongitude), 0, "\u2032"))
                .append(formatNumber(getSeconds(degreesLongitude), 2, "\u2033"))
                .append(latitude < 0 ? "W" :  "E");

        return builder.toString();
    }

    private double getMinutes(double decimalDegrees) {
        double decimals = decimalDegrees - Math.floor(decimalDegrees);
        return 60 * decimals;
    }

    private double getSeconds(double decimalDegrees) {
        double minutes = getMinutes(decimalDegrees);
        return getMinutes(minutes); // seconds are to minutes what minutes are to degrees
    }

    private String formatNumber(double number, int digitsAfterDot, String suffix) {
        return getDigitsBeforeDot(Math.abs(getSeconds(number)), digitsAfterDot) + suffix;
    }

    private String getDigitsBeforeDot(double number, int digitsAfterDot) {
        String numberAsString = String.valueOf(number);
        int indexOfDot = numberAsString.indexOf('.');
        int finalCharacter = indexOfDot + digitsAfterDot + (digitsAfterDot > 0 ? 1 : 0);
        if (indexOfDot < 0 || finalCharacter >= numberAsString.length()) {
            return numberAsString;
        }
        return numberAsString.substring(0, finalCharacter);
    }

}
