package ufc.unit;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import ufc.TestClass;
import org.junit.Assert;
import org.junit.Test;

public class GeometryLibraryTests extends TestClass {

    @Test
    public void testIntersection() {
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate p1 = new Coordinate(10, 10);
        Coordinate p2 = new Coordinate(10, 0);
        Coordinate[] line = {p1, p2};
        LineString lineString1 = geometryFactory.createLineString(line);

        Coordinate p3 = new Coordinate(5, 5);
        Coordinate p4 = new Coordinate(15, 5);
        Coordinate[] line2 = {p3, p4};
        LineString lineString2 = geometryFactory.createLineString(line2);
        Coordinate c = lineString1.intersection(lineString2).getCoordinate();
        Assert.assertEquals(10, c.x, 0);
        Assert.assertEquals(5, c.y, 0);
    }

}
