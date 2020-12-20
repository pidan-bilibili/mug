package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] open;
    private int top = 0;
    private int bottom;
    private int size;
    private int number;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF full;

    // get the position of the points in the grid
    private int getPosition(int row, int col) {
        return size * row + col;
    }

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("nt");
        }
        size = N;
        top = 0;
        bottom = size * size + 1;
        number = 0;
        grid = new WeightedQuickUnionUF(size * size + 2);
        full = new WeightedQuickUnionUF(size * size + 1);
        open = new boolean[size][size];
    }





    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (0 <= row && 0 <= col && row < size && col < size) {
            if (!isOpen(row, col)) {
                open[row][col] = true;
                number += 1;
                if (row == 0) {
                    grid.union(getPosition(row, col), top);
                    full.union(getPosition(row, col), top);
                }
                if (row == size - 1) {
                    grid.union(getPosition(row, col), bottom);
                }
                if (col > 0 && isOpen(row, col - 1)) {
                    grid.union(getPosition(row, col), getPosition(row, col - 1));  // left
                    full.union(getPosition(row, col), getPosition(row, col - 1));  // left
                }
                if (col < size - 1 && isOpen(row, col + 1)) {
                    grid.union(getPosition(row, col), getPosition(row, col + 1));  // right
                    full.union(getPosition(row, col), getPosition(row, col + 1));  // right
                }
                if (row > 0 && isOpen(row - 1, col)) {
                    grid.union(getPosition(row, col), getPosition(row - 1, col));  // up
                    full.union(getPosition(row, col), getPosition(row - 1, col));  // up
                }
                if (row < size - 1 && isOpen(row + 1, col)) {
                    grid.union(getPosition(row, col), getPosition(row + 1, col));  // down
                    full.union(getPosition(row, col), getPosition(row + 1, col));  // down
                }
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }






    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (0 <= row && 0 <= col && row < size && col < size) {
            return open[row][col];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }





    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (0 <= row && 0 <= col && row < size && col < size) {
            if (isOpen(row, col)) {
                return full.connected(getPosition(row, col), top);
            } else {
                return false;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }






    // number of open sites
    public int numberOfOpenSites() {
        return number;
    }





    // does the system percolate?
    public boolean percolates() {
        return grid.connected(top, bottom);
    }






    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
        Percolation sb = new Percolation(3);
        sb.open(0, 2);
        sb.open(1, 2);
        sb.open(2, 2);
        sb.open(2, 0);
        System.out.println(sb.isFull(2, 0));
        Percolation nt = new Percolation(5);
        nt.open(1, 1);
        nt.open(2, 1);
        nt.open(3, 1);
        nt.open(4, 1);
        nt.open(0, 1);
        System.out.println(nt.isOpen(1, 1));
        System.out.println(nt.isFull(1, 1));
        nt.isFull(1, 1);
        System.out.println(nt.numberOfOpenSites());
        System.out.println(nt.percolates());
    }






}




