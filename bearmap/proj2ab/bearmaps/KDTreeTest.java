package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    private static final Random r = new Random(500);

    private static KDTree buildTree() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        return kd;
    }

    @Test
    // test code by using examples from test slides
    public void testNearest() {
        KDTree kd = buildTree();
        Point actural = kd.nearest(1, 5);
        Point expected = new Point(1, 5);

        assertEquals(expected, actural);
    }

    @Test
    // test the window methods with examples
    public void testWindow() {
        KDTree kd = buildTree();
        List<Point> expected = new ArrayList<>(List.of(new Point(4, 5), new Point(4, 4)));
        List<Point> actural = kd.window(4, 7, 4, 7);

        for (int i = 0; i < expected.size(); i ++) {
            assertEquals(expected.get(i), actural.get(i));
        }
    }



    /** create random points */
    private Point randomPoint() {
        Double x = r.nextDouble();
        Double y = r.nextDouble();
        return new Point(x, y);
    }

    private List<Point> randompoint(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(randomPoint());
        }
        return points;
    }


    private void testWithNpointsandQQueries(int pointCount, int queryCount) {
        List<Point> points = randompoint(pointCount);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = randompoint(queryCount);
        for (Point p : queries) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actural = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actural);
        }
    }

    @Test
    public void testWith1000Points() {
        testWithNpointsandQQueries(1000, 200);
    }


    @Test
    public void testWith10000Points() {
        testWithNpointsandQQueries(10000, 2000);
    }




    /** create a time table of all kinds*/
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N(Tree)", "time (s)", "goal", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }


    /** add run time of construction into the table */
    private void timeTableConstruction() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        Stopwatch sw1 = new Stopwatch();
        List<Point> points = randompoint(2000000);
        KDTree kd = new KDTree(points);
        double timeInSecond1 = sw1.elapsedTime();
        Ns.add(2000000);
        times.add(timeInSecond1);
        opCounts.add(2000000);

        printTimingTable(Ns, times, opCounts);
    }

    @Test
    public void testConstructionTiming() {
        timeTableConstruction();
    }


    /** add run time of nearest into the table */
    private void timeTableNearest() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        List<Point> points = randompoint(2000000);
        List<Point> queries = randompoint(1000000);
        KDTree kd = new KDTree(points);
        Stopwatch sw1 = new Stopwatch();
        for (Point p : queries) {
            kd.nearest(p.getX(), p.getY());
        }
        double timeInSecond1 = sw1.elapsedTime();
        Ns.add(2000000);
        times.add(timeInSecond1);
        opCounts.add(1000000);

        printTimingTable(Ns, times, opCounts);
    }

    @Test
    public void testNearestTiming() {
        timeTableNearest();
    }
}


