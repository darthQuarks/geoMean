package rvs.geomean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import rvs.geomean.datamodel.Point;
import rvs.geomean.datamodel.PointCollection;
import rvs.geomean.service.MeanPointService;

@SpringBootTest
// Tests are package private
class GeomeanApplicationTests {

	@Autowired private MeanPointService meanPointService;

	@Test
	void contextLoads() {
	}

	@Test
    // Testing cardinal directions in 10 deg steps
    void cardinalMeanPointTest() {
		Point point0 = new Point(0, 0);
		Point point1 = new Point(0, 10);
		Point point2 = new Point(10, 0);

		Point expected = new Point(0, 5);
        Point actual = meanPointService.getMeanPoint(point0, point1, 1);
        Assert.isTrue(actual.pretty().equals(expected.pretty()), "Point are not equal to scale: " + Point.getScale() + ", east-west calculation is wrong");

        expected = new Point(5, 0);
        actual = meanPointService.getMeanPoint(point0, point2, 1);
        Assert.isTrue(actual.pretty().equals(expected.pretty()), "Point are not equal to scale: " + Point.getScale() + ", north-south calculation is wrong");
    }

    @Test
    // Testing cardinal directions in 10 deg steps
    void eastWestTest() {
        Point point0 = new Point(0, 0);
        Point point1 = new Point(0, 10);
        Point point2 = new Point(0, -10);

        Point actual = meanPointService.getMeanPoint(point0, point1, 1);
        Assert.isTrue(actual.pretty().contains("E"), "Point is not marked as east");

        actual = meanPointService.getMeanPoint(point0, point2, 1);
        Assert.isTrue(actual.pretty().contains("W"), "Point is not marked as west");
    }

    @Test
        // Testing cardinal directions in 10 deg steps
    void northSouthTest() {
        Point point0 = new Point(0, 0);
        Point point1 = new Point(10, 0);
        Point point2 = new Point(-10, 0);

        Point actual = meanPointService.getMeanPoint(point0, point1, 1);
        Assert.isTrue(actual.pretty().contains("N"), "Point is not marked as north");

        actual = meanPointService.getMeanPoint(point0, point2, 1);
        Assert.isTrue(actual.pretty().contains("S"), "Point is not marked as south");
    }

    @Test
    // Test mass works
    void testMass() {
        Point point0 = new Point(0, 0);
        Point point1 = new Point(0, 10);

        Point expected = new Point(0, 1);
        Point actual = meanPointService.getMeanPoint(point0, point1, 9);
        Assert.isTrue(actual.pretty().equals(expected.pretty()), "Point are not equal to scale: " + Point.getScale() + ", mass calculation is wrong");

        expected = new Point(0, 2);
        actual = meanPointService.getMeanPoint(point0, point1, 4);
        Assert.isTrue(actual.pretty().equals(expected.pretty()), "Point are not equal to scale: " + Point.getScale() + ", mass calculation is wrong");

        expected = new Point(0, 3.333333333333333);
        actual = meanPointService.getMeanPoint(point0, point1, 2);
        Assert.isTrue(actual.pretty().equals(expected.pretty()), "Point are not equal to scale: " + Point.getScale() + ", mass calculation is wrong");
    }

    @Test
        // Testing floating point numbers in a verified calculation
    void skumpamaalaTest() {
        Point skumpamaala = new Point(56.462044610198, 15.422228080524); // Where we wanna end up

        Point aarhus = new Point(56.154181770464, 10.205998420715);
        Point copenhagen = new Point(55.694809438319, 12.554969787598);
        Point stockholm = new Point(59.328986527418, 18.062210083008);
        Point warzawa = new Point(52.200505285726, 21.0390203125);

        PointCollection pointCollection = new PointCollection()
                .append(aarhus)
                .append(copenhagen)
                .append(copenhagen)
                .append(stockholm)
                .append(stockholm)
                .append(warzawa);

        Point meanPoint = meanPointService.getMeanPoint(pointCollection);

        Assert.isTrue(meanPoint.pretty().equals(skumpamaala.pretty()), "We did not end up in Skumpamåla (" + skumpamaala.pretty() + ") but in " + meanPoint.pretty());
    }

}
