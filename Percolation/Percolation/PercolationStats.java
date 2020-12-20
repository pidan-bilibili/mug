package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private Percolation nt;
    private double[] sample;
    private int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        sample = new double[T];
        for (int i = 0; i < T; i++) {
            nt = pf.make(N);
            while (!nt.percolates()) {
                nt.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            sample[i] = (double) nt.numberOfOpenSites() / (N * N);
        }
    }



    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(sample);
    }





    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(sample);
    }





    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return this.mean() - (1.96 * this.stddev() / Math.sqrt(T));
    }






    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return this.mean() + (1.96 * this.stddev() / Math.sqrt(T));
    }

}



