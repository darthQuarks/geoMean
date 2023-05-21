package rvs.geomean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import rvs.geomean.datamodel.Point;
import rvs.geomean.service.MeanPointService;

@SpringBootTest
class GeomeanApplicationTests {

	@Autowired private MeanPointService meanPointService;

	@Test
	void contextLoads() {
	}

	@Test
	void cardinalMeanPointTest() {
		Point point0 = new Point(0, 0);
		Point point1 = new Point(0, 10);
		Point point2 = new Point(10, 0);

		Point result0 = new Point(0, 5);
		Point result1 = new Point(0, 1);
		Point result2 = new Point(5, 0);

		Point meanPoint = meanPointService.getMeanPoint(point0, point1, 1);
		Assert.isTrue(meanPoint.pretty().equalsIgnoreCase(result0.pretty()), "Point are not equal to scale: " + Point.getScale() + ", east-west calculation is wrong");

		meanPoint = meanPointService.getMeanPoint(point0, point1, 9);
		Assert.isTrue(meanPoint.pretty().equalsIgnoreCase(result1.pretty()), "Point are not equal to scale: " + Point.getScale() + ", mass calculation is wrong");

		meanPoint = meanPointService.getMeanPoint(point0, point2, 1);
		Assert.isTrue(meanPoint.pretty().equalsIgnoreCase(result2.pretty()), "Point are not equal to scale: " + Point.getScale() + ", north-south calculation is wrong");
	}

}
