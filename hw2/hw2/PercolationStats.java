package hw2;

public class PercolationStats {
    private PercolationFactory pf;
    private double [] result;
    private double mean = 0.0;
    private double std = 0.0;
    private double confidenceLow = 0.0;
    private double confidenceHigh = 0.0;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        validate(N);
        validate(T);
        //初始化pc机器
        this.pf = pf;
        result = new double[T];
        //初始化每个测试的图，测试percolation，计算打开的个数，private method
        for (int i = 0; i < T; i++) {
            Percolation pc = pf.make(N);
            result[i] = percolationCalculation(pc, N);
        }
        mean = edu.princeton.cs.algs4.StdStats.mean(result);
        std = edu.princeton.cs.algs4.StdStats.stddev(result);
        confidenceLow = mean - 1.96 * std / Math.pow(T, 0.5);
        confidenceHigh = mean + 1.96 * std / Math.pow(T, 0.5);
    }

    public double mean() {
        return this.mean;
    }

    public double stddev() {
        return this.std;
    }

    public double confidenceLow() {
        return this.confidenceLow;
    }

    public double confidenceHigh() {
        return this.confidenceHigh;
    }

    private void validate(int index) {
        if (index <= 0) {
            throw new java.lang.IllegalArgumentException("illegal argument");
        }
    }

    private double percolationCalculation(Percolation pc, int N) {
        //产生两个随机数
        //直到已经渗透，跳出循环
        while (!pc.percolates()) {
            int row = edu.princeton.cs.introcs.StdRandom.uniform(N);
            int col = edu.princeton.cs.introcs.StdRandom.uniform(N);
            pc.open(row, col);
        }
        return ((double) pc.numberOfOpenSites()) / (N * N);
    }


    /*
    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(100,10, pf);
        System.out.println(ps.mean);
    }
     */
}
