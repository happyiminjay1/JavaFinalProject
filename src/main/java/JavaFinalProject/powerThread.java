package JavaFinalProject;
public class powerThread implements Runnable{

	long from, to, totalPower;
	float powerNum;

	@Override
	public void run() {
		totalPower = getTotalSumFromTo();
	}

	public powerThread(long from, long to, float powerNum) {
		this.from = from;
		this.to= to;
		this.powerNum = powerNum;
	}

	public long getTotalSumFromTo() {
		long total = 1;
		for(long i=from;i<=to;i++)
			total*=powerNum;
		return total;
	}
}