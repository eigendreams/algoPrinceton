import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private Percolation per;
	private int[]        timesToPercolate;
	private int         nLength;
	private int         nLength2;
	private int         trialsn;

	public PercolationStats(int n, int trials)
	{
		// Check bounds
		if (( n < 0) || (trials < 0))
		{
			throw new java.lang.IllegalArgumentException();
		}
		
		nLength          = n;
		nLength2         = nLength * nLength;
		trialsn          = trials;
		timesToPercolate = new int[trials];
		
		// Definition says, choose a tile uniformly at random, so do so
		for ( int i = 0; i < trials; i++ )
		{
			// Clean percolation class to start new try
			per = new Percolation(n);
			
			while(true)
			{
				// Get index of element that we try to set open, one by one
				int p = StdRandom.uniform(n);
				int q = StdRandom.uniform(n);
				
				// This should preserve uniformity of distribution
				if ( per.isFull(p + 1, q + 1) ) 
				{
					// Open tile chosen at random
					per.open(p + 1, q + 1);
					
					// Increase counter 
					timesToPercolate[i]++;
				}
				
				// Check if open or closed
				if ( per.percolates() ) 
				{
					break;
				};
			}
		}
	}
	
	// sample mean of percolation threshold
	public double mean()    
	{
		return StdStats.mean(timesToPercolate) / (double)(nLength2);
	}
	
	// sample standard deviation of percolation threshold
	public double stddev()
	{
		return StdStats.stddev(timesToPercolate) / (double)(nLength2);
	}
	
	// low  endpoint of 95% confidence interval
	public double confidenceLo()
	{
		double lo = mean() - 1.96 * stddev() / java.lang.Math.sqrt((double)trialsn);
		return lo;
	}
	
	// high endpoint of 95% confidence interval
	public double confidenceHi()
	{
		double hi = mean() + 1.96 * stddev() / java.lang.Math.sqrt((double)trialsn);
		return hi;
	}
	
	public static void main(String[] args) {
		
		int n = StdIn.readInt(); 
		int t = StdIn.readInt();
		
		PercolationStats perstats = new PercolationStats(n, t);
		
		//MessageFormat mean = new MessageFormat("mean                    = {0}");
		//MessageFormat stdd = new MessageFormat("stddev                  = {0}");
		//MessageFormat intr = new MessageFormat("95% confidence interval = {0}, {1}");
		
		//Object[] argsInterval = {perstats.confidenceLo(), perstats.confidenceHi()};
		
		System.out.println( String.format("mean                    = %1.10f", perstats.mean()) );
		System.out.println( String.format("stddev                  = %1.10f", perstats.stddev()) );
		System.out.println( String.format("95%% confidence interval = %1.10f, %1.10f", perstats.confidenceLo(), perstats.confidenceHi()));
	}

}