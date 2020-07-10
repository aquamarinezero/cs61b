package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int[] grid;
    WeightedQuickUnionUF wf;
    int N;
    int openCount;
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("illegal argument");
        }
        grid = new int[N * N];
        this.N = N;
        openCount = 0;
        wf = new WeightedQuickUnionUF(N * N);
    }

    public void open(int row, int col) {      // open the site (row, col) if it is not open already
        validate(row);
        validate(col);
        //如果open，直接返回
        if (isOpen(row, col)) {
            return;
        }
        //如果没open
        grid[xyTo1D(row, col)] = 1;
        openCount = openCount + 1;
        //open的同时连接上下左右open的格子
        //检查上面的格子
        if (row > 0) {
            if (isOpen(row - 1, col)) {
                wf.union(xyTo1D(row - 1, col), xyTo1D(row, col));
            }
        }
        //检查下面的格子
        if (row < N - 1) {
            if (isOpen(row + 1, col)) {
                wf.union(xyTo1D(row + 1, col), xyTo1D(row, col));
            }
        }
        //检查左边的格子
        if (col > 0) {
            if (isOpen(row, col - 1)) {
                wf.union(xyTo1D(row, col - 1), xyTo1D(row, col));
            }
        }
        //检查右面的格子
        if (col < N - 1) {
            if (isOpen(row, col + 1)) {
                wf.union(xyTo1D(row, col + 1), xyTo1D(row, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {  // is the site (row, col) open?
        validate(row);
        validate(col);
        return grid[xyTo1D(row, col)] == 1;
    }

    public boolean isFull(int row, int col) {  // is the site (row, col) full?
    //full没full检查是否与第一行的连接，并且第一行的open
        validate(row);
        validate(col);
        for (int i = 0; i < N; i++) {
            if (wf.connected(xyTo1D(row, col), i) && (isOpen(0, i))) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() {          // number of open sites
        return openCount;
    }

    public boolean percolates() {             // does the system percolate?
    //检查最后一行是否full
        for (int i = 0; i < N; i++) {
            if (isFull(N - 1, i)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
    }

    private void validate(int index) {
        if ((index < 0) || (index >= N)) {
            throw new java.lang.IndexOutOfBoundsException("out of bounds");
        }
    }

    //二维专一维数字
    private int xyTo1D(int r, int c) {
        return r * N + c;
    }
}
