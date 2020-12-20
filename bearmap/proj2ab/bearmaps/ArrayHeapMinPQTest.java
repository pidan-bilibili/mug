package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ArrayHeapMinPQTest<T> {
    private static Random r = new Random(500);


    /** create random points added in the MinPQ */
    private List<Integer> randomPoint(int N) {
        List<Integer> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(r.nextInt());
        }
        return points;
    }

    /** create random priority correspond to the points in MinPQ */
    private List<Double> randomPriority(int N) {
        List<Double> priority = new ArrayList<>();
        for (int i = 0; i< N; i++) {
            priority.add(r.nextDouble());
        }
        return priority;
    }

    /** create same random nps and AHM, and test Contains method*/
    private void testWithContains(int pointCount, int queryCount) {
        List<Integer> points = randomPoint(pointCount);
        List<Double> priority = randomPriority(pointCount);

        NaiveMinPQ<Integer> nps = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> AHM = new ArrayHeapMinPQ<>();

        // create a same random nps and AHM
        for (int i = 0; i < points.size(); i++) {
            if (!AHM.contains(points.get(i))) {
                nps.add(points.get(i), priority.get(i));
                AHM.add(points.get(i), priority.get(i));
            }
        }

        List<Integer> queries = randomPoint(queryCount);
        for (int p : queries) {
            boolean expected = nps.contains(p);
            boolean actural = AHM.contains(p);
            assertEquals(expected, actural);
        }
    }

    @Test
    public void testBooleanWith1000000() {
        testWithContains(8000000, 1000);
    }


    /** create same random nps and AHM, and test getSmallest method*/
    private void testWithgetSmallest(int pointCount) {
        List<Integer> points = randomPoint(pointCount);
        List<Double> priority = randomPriority(pointCount);

        NaiveMinPQ<Integer> nps = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> AHM = new ArrayHeapMinPQ<>();

        // create a same random nps and AHM
        for (int i = 0; i < points.size(); i++) {
            if (!AHM.contains(points.get(i))) {
                nps.add(points.get(i), priority.get(i));
                AHM.add(points.get(i), priority.get(i));
            }
        }

        Object expected = nps.getSmallest();
        Object actural = AHM.getSmallest();
        assertEquals(expected, actural);
    }


    @Test
    public void testgetSmallestWith1000000() {
        testWithgetSmallest(1000000);
    }



    /** create same random nps and AHM, and test Remove method*/
    private void testWithRemove(int pointCount) {
        List<Integer> points = randomPoint(pointCount);
        List<Double> priority = randomPriority(pointCount);
        NaiveMinPQ<Integer> nps = new NaiveMinPQ<>();
        ArrayHeapMinPQ AHM = new ArrayHeapMinPQ<>();

        // create a same random nps and AHM
        for (int i = 0; i < points.size(); i++) {
            if (!AHM.contains(points.get(i))) {
                nps.add(points.get(i), priority.get(i));
                AHM.add(points.get(i), priority.get(i));
            }
        }

        for (int i = 0; i < 1000; i ++) {
            Object x = nps.getSmallest();
            Object expected = nps.removeSmallest();
            Object actural = AHM.removeSmallest();
            assertEquals(expected, actural);
            assertFalse(AHM.contains(x));
        }
    }

    @Test
    public void testRemoveSmallestWith1000() {
        testWithRemove(2000000);
    }


    /** create same random nps and AHM, and test change method*/
    private void testWithChange(int pointCount) {
        List<Integer> points = randomPoint(pointCount);
        List<Double> priority = randomPriority(pointCount);
        NaiveMinPQ nps = new NaiveMinPQ();
        ArrayHeapMinPQ AHM = new ArrayHeapMinPQ();

        // create a same random nps and AHM
        for (int i = 0; i < points.size(); i++) {
            if (!AHM.contains(points.get(i))) {
                nps.add(points.get(i), priority.get(i));
                AHM.add(points.get(i), priority.get(i));
            }
        }

        //change priority method
        List<Double> Change = randomPriority(10000);
        for (int i = 0; i < 10000; i++) {
            if (nps.contains(points.get(i))) {
                nps.changePriority(points.get(i), Change.get(i));
                AHM.changePriority(points.get(i), Change.get(i));
                Object x = nps.getSmallest();
                Object expected = nps.removeSmallest();
                Object actural = AHM.removeSmallest();
                assertEquals(expected, actural);
                assertFalse(AHM.contains(x));
            }
        }
    }


    @Test
    public void testChangetWith1000() {
        testWithChange(2000000);
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



    /** print MinPQ time */
    private void minPQTimeTableConstruction() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        List<Integer> points = randomPoint(20000000);
        List<Double> priority = randomPriority(20000000);
        ArrayHeapMinPQ AHP = new ArrayHeapMinPQ();

        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < 20000000; i++) {
            if (!AHP.contains(points.get(i))) {
                AHP.add(points.get(i), priority.get(i));
            }
        }
        double timeInSecond1 = sw1.elapsedTime();

        Ns.add(20000000);
        times.add(timeInSecond1);
        opCounts.add(20000000);

        printTimingTable(Ns, times, opCounts);
    }

    @Test
    public void testAHPTime() {
        minPQTimeTableConstruction();
    }



    /** print naive remove time */
    private void RemoveTimeTableConstruction() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        List<Integer> points = randomPoint(2000000);
        List<Double> priority = randomPriority(2000000);
        ArrayHeapMinPQ<Integer> AHM = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> NM = new NaiveMinPQ<>();

        for (int i = 0; i < points.size(); i++) {
            if (!AHM.contains(points.get(i))) {
                AHM.add(points.get(i), priority.get(i));
            }
        }

        //time for removeSmallest in Naive.
        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            AHM.removeSmallest();
        }
        double timeInSecond2 = sw2.elapsedTime();


        Ns.add(points.size());
        times.add(timeInSecond2);
        opCounts.add(10000);
        printTimingTable(Ns, times, opCounts);
    }

    @Test
    public void testRemoveTime() {
        RemoveTimeTableConstruction();
    }


    private void ChangeTimeTableConstruction() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        List<Integer> points = randomPoint(2000000);
        List<Double> priority = randomPriority(2000000);
        ArrayHeapMinPQ<Integer> AHM = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> NM = new NaiveMinPQ<>();

        for (int i = 0; i < points.size(); i++) {
            if (!AHM.contains(points.get(i))) {
                AHM.add(points.get(i), priority.get(i));
            }
        }

        //time for change.
        List<Double> Change = randomPriority(10000);
        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            if (AHM.contains(points.get(i))) {
                AHM.changePriority(points.get(i), Change.get(i));
            }
        }
        double timeInSecond3 = sw3.elapsedTime();


        Ns.add(points.size());
        times.add(timeInSecond3);
        opCounts.add(10000);
        printTimingTable(Ns, times, opCounts);
    }

    @Test
    public void testChangeTime() {
        ChangeTimeTableConstruction();
    }


    private void NaiveTimeTable() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        List<Integer> points = randomPoint(200000);
        List<Double> priority = randomPriority(200000);
        NaiveMinPQ<Integer> NM = new NaiveMinPQ<>();

        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < points.size(); i++) {
            if (!NM.contains(points.get(i))) {
                NM.add(points.get(i), priority.get(i));
            }
        }
        double timeInSecond3 = sw3.elapsedTime();


        Ns.add(points.size());
        times.add(timeInSecond3);
        opCounts.add(200000);
        printTimingTable(Ns, times, opCounts);
    }

    @Test
    public void testnaiveTime() {
        NaiveTimeTable();
    }


}
