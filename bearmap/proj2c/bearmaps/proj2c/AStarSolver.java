package bearmaps.proj2c;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

/** the estimatedDistanceToGoal will return the smallest weighted edge of the vertex.
 * (set f represents estimatedDistanceToGoal)
 * How A* work is:
 * 1: source will point to other vertexes according to the weighted edges,
 * and the fringe will store each vertex as (Vertex, f(vertex) + disTo(vertex),
 * for those do not changed will remain infinity.
 * 2: To avoid missing the shortest path, relax will replace the disTo and edgeTo
 * Summary: The A* algorithm is basically think one more step of the vertex.
 *          the shortest path will occur most likely in the shortest path. */


public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private int numStatesExplored;
    private final double timeout;
    private final double explorationTime;

    private final Vertex end;
    private final AStarGraph<Vertex> input;

    private final Map<Vertex, Double> disTo = new HashMap<>();
    private final Map<Vertex, Vertex> edgeTo = new HashMap<>();
    private final ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();



    /** construct the solution of a puzzle by using A* algorithm.
     * if solving the puzzle reach the time, there will be no solution. */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Map<Vertex, Boolean> visit = new HashMap<>();
        Stopwatch time = new Stopwatch();
        disTo.put(start, 0.0);
        edgeTo.put(start, null);
        double distance = input.estimatedDistanceToGoal(start, end);
        fringe.add(start, distance);
        Vertex smallest = fringe.getSmallest();
        this.timeout = timeout;
        this.end = end;
        this.input = input;

        while (!smallest.equals(end) && time.elapsedTime() <= timeout && fringe.size() > 0) {
            Vertex v = fringe.removeSmallest();
            visit.put(v, true);
            numStatesExplored += 1;

            for (WeightedEdge<Vertex> a : input.neighbors(v)) {
                if (!visit.containsKey(a.to())) {
                    disTo.put(a.to(), Double.POSITIVE_INFINITY);
                    visit.put(a.to(), false);
                }
                relax(a);
            }

            if (fringe.size() > 0) {
                smallest = fringe.getSmallest();
            }

        }
        explorationTime = time.elapsedTime();
    }

    /** Helper method for the constructor add into the fringe iff it has a shorter distance.
     * get two vertex of two edge and calculate if the estimated distance + the from's distance,
     * and check if it is smaller than thr distance to the to's distance.
     * If smaller, replace to's disTo, add/replace the priority in the MinPQ, and change the path.*/
    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();

        if (disTo.get(p) + w < disTo.get(q)) {
            disTo.put(q, disTo.get(p) + w);
            edgeTo.put(q, p);
            if (fringe.contains(q)) {
                fringe.changePriority(q, disTo.get(q) + input.estimatedDistanceToGoal(q, end));
            } else {
                fringe.add(q, disTo.get(q) + input.estimatedDistanceToGoal(q, end));
            }
        }
    }

    /** if timeout then return timeout, if the fringe is empty return unsolvable. */
    @Override
    public SolverOutcome outcome() {
        if (fringe.size() == 0) {
            return SolverOutcome.UNSOLVABLE;
        } else if (explorationTime >= timeout) {
            return SolverOutcome.TIMEOUT;
        } else {
            return SolverOutcome.SOLVED;
        }
    }

    /** return a list of vertices corresponding to a solution. */
    @Override
    public List<Vertex> solution() {
        ArrayList<Vertex> solution = new ArrayList<>();
        if (outcome().equals(SolverOutcome.SOLVED)) {
            ArrayList<Vertex> helper = new ArrayList<>();

            for (Vertex x = end; x != null; x = edgeTo.get(x)) {
                helper.add(x);
            }

            for (int i = helper.size() - 1; i >= 0; i--) {
                Vertex v1 = helper.get(i);
                solution.add(v1);
            }
        }

        return solution;
    }

    /** return the total weight of the given solution, taking into account edge weights. */
    @Override
    public double solutionWeight() {
        if (outcome().equals(SolverOutcome.UNSOLVABLE)) {
            return 0;
        }

        if (outcome().equals(SolverOutcome.TIMEOUT)) {
            return 0;
        }
        return disTo.get(end);
    }

    /** return the total number of priority queue dequeue operations. */
    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    /** return the total time spent in seconds by the constructor. */
    @Override
    public double explorationTime() {
        return explorationTime;
    }
}

