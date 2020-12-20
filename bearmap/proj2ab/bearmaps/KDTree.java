package bearmaps;

import java.util.*;

public class KDTree implements PointSet {

    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;



    private static class Node {
        private final Point p;
        private final boolean orientation;
        private Node leftchild; // also refers to "down" child
        private Node rightchild; // also refers to "up" child

        Node(Point givenP, boolean o) {
            p = givenP;
            orientation = o;
        }
    }

    /** construct the whole map */
    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }
    }



    /** add an item to the KDTree */
    private Node add(Point p, Node n, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }
        if (p.equals(n.p)) {
            return n;
        }
        double cmp = comparePoints(n.p, p, orientation);
        if (cmp < 0) {
            n.leftchild = add(p, n.leftchild, !orientation);
        } else if (cmp >= 0) {
            n.rightchild = add(p, n.rightchild, !orientation);
        }
        return n;
    }


    /** compare the position of two points */
    private double comparePoints(Point p1, Point p2, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return p2.getX() - p1.getX();
        } else {
            return p2.getY() - p1.getY();
        }
    }


    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearestHelper(root, goal, root).p;
    }


    /** Helper method， return the best Node */
    private Node nearestHelper(Node n, Point goal, Node best) {
        Node goodSide;
        Node badSide;

        if (n == null) {
            return best;
        }

        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }


        double cmp = comparePoints(n.p, goal, n.orientation);
        if (cmp < 0) {
            goodSide = n.leftchild;
            badSide = n.rightchild;
        } else {
            goodSide = n.rightchild;
            badSide = n.leftchild;
        }
        best = nearestHelper(goodSide, goal, best);

        if (n.orientation == HORIZONTAL) {
            Point nt = new Point(n.p.getX(), goal.getY());
            if (Point.distance(goal, best.p) >= Point.distance(goal, nt)) {
                best = nearestHelper(badSide, goal, best);
            }
        } else if (n.orientation == VERTICAL){
            Point nt = new Point(goal.getX(), n.p.getY());
            if (Point.distance(goal, best.p) >= Point.distance(goal, nt)) {
                best = nearestHelper(badSide, goal, best);
            }
        }
        return best;
    }

    /** return a list of points contained in the given window, including the boundary */
    public List<Point> window(double x1, double x2, double y1, double y2) {
        List<Point> all = new ArrayList<>();
        windowHelper(root, all, x1, x2, y1, y2);
        return all;
    }

    /** helper method for the window method */
    private void windowHelper(Node n, List<Point> points, double x1, double x2, double y1, double y2) {
        if (n == null) {
            return;
        }
        if (n.p.isInside(x1, x2, y1, y2)) {
            points.add(n.p);
        }
        if (n.orientation == HORIZONTAL) {
            if (n.p.getX() <= x2) {
                windowHelper(n.rightchild, points, x1, x2, y1, y2);
            }
            if (n.p.getX() > x1) {
                windowHelper(n.leftchild, points, x1, x2, y1, y2);
            }
        } else {
            if (n.p.getY() <= y2) {
                windowHelper(n.rightchild, points, x1, x2, y1, y2);
            }
            if (n.p.getY() > x1) {
                windowHelper(n.leftchild, points, x1, x2, y1, y2);
            }
        }
    }
}


