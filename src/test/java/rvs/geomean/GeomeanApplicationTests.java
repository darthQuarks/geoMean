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

		Point result0 = new Point(0, 5);
		Point result1 = new Point(0, 1);
		Point result2 = new Point(5, 0);

        Point meanPoint = meanPointService.getMeanPoint(point0, point1, 1);
        Assert.isTrue(meanPoint.pretty().equals(result0.pretty()), "Point are not equal to scale: " + Point.getScale() + ", east-west calculation is wrong");

        meanPoint = meanPointService.getMeanPoint(point0, point2, 1);
        Assert.isTrue(meanPoint.pretty().equals(result2.pretty()), "Point are not equal to scale: " + Point.getScale() + ", north-south calculation is wrong");
    }

    @Test
    // Test mass works
    void testMass() {
        Point point0 = new Point(0, 0);
        Point point1 = new Point(0, 10);

        Point result = new Point(0, 1);
        Point meanPoint = meanPointService.getMeanPoint(point0, point1, 9);
        Assert.isTrue(meanPoint.pretty().equals(result.pretty()), "Point are not equal to scale: " + Point.getScale() + ", mass calculation is wrong");

        result = new Point(0, 2);
        meanPoint = meanPointService.getMeanPoint(point0, point1, 4);
        Assert.isTrue(meanPoint.pretty().equals(result.pretty()), "Point are not equal to scale: " + Point.getScale() + ", mass calculation is wrong");

        result = new Point(0, 3.333333333333333);
        meanPoint = meanPointService.getMeanPoint(point0, point1, 2);
        Assert.isTrue(meanPoint.pretty().equals(result.pretty()), "Point are not equal to scale: " + Point.getScale() + ", mass calculation is wrong");
    }

    @Test
        // Testing floating point numbers in a verified calculation
    void skumpamaalaTest() {
        Point skumpamaala = new Point(56.462044610198, 15.422228080524); // Where we wanna end up

        Point aarhus = new Point(56.154181770464, 10.205998420715);
        Point copenhagen = new Point(55.694809438319, 12.554969787598);
        Point stockholm = new Point(59.328986527418, 18.062210083008);
        Point warzawa = new Point(52.200505285726, 21.0390203125);

        PointCollection pointCollection = new PointCollection();

        pointCollection.append(aarhus)
                .append(copenhagen)
                .append(copenhagen)
                .append(stockholm)
                .append(stockholm)
                .append(warzawa);

        Point meanPoint = meanPointService.getMeanPoint(pointCollection);

        Assert.isTrue(meanPoint.pretty().equals(skumpamaala.pretty()), "We did not end up in Skumpamåla");
    }

}
