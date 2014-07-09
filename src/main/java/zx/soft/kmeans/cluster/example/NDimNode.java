package zx.soft.kmeans.cluster.example;

/**
 * 一个N维的点
 * 
 * @author wanggang
 *
 */
public class NDimNode {

	// 维度
	private final int dimension;
	// 坐标
	private final double[] coordinates;

	public NDimNode(int dimension) {
		this.dimension = dimension;
		coordinates = new double[dimension];
	}

	public NDimNode(NDimNode node) {
		this.dimension = node.getDimension();
		this.coordinates = node.getCoordinates();
	}

	/**
	 * 坐标还原
	 */
	public void setToOrigin() {
		for (int i = 0; i < dimension; i++)
			coordinates[i] = 0;
	}

	/**
	 * 计算该点与原点的Euclid距离
	 */
	public double norm() {
		double sum = 0;
		for (int i = 0; i < dimension; i++)
			sum = sum + Math.pow(coordinates[i], 2);
		return Math.sqrt(sum);
	}

	/**
	 * 增加一个点
	 */
	public void add(NDimNode node) {
		for (int i = 0; i < dimension; i++)
			coordinates[i] = coordinates[i] + node.getCoordinate(i);
	}

	/**
	 * 减去一个点
	 */
	public void subtract(NDimNode node) {
		for (int i = 0; i < dimension; i++)
			coordinates[i] = coordinates[i] - node.getCoordinate(i);
	}

	/**
	 * 乘以一个点
	 */
	public void multiply(double node) {
		for (int i = 0; i < dimension; i++)
			coordinates[i] = node * coordinates[i];
	}

	/**
	 * N次方
	 */
	public void pow(double exp) {
		for (int i = 0; i < dimension; i++)
			coordinates[i] = Math.pow(coordinates[i], exp);
	}

	/**
	 * 计算与另一个点的Euclid距离
	 */
	public double dist(NDimNode node) {
		NDimNode n = new NDimNode(this);
		n.subtract(node);
		return n.norm();
	}

	/**
	 * 返回坐标点维度中的最大值
	 */
	public double max() {
		double value;
		double max = coordinates[0];
		for (int i = 1; i < dimension; i++) {
			value = coordinates[i];
			if (value > max)
				max = value;
		}
		return max;
	}

	/**
	 * 通过参数构建一个正态分布
	 * 
	 * @param mean：均值
	 * @param sigma：系数对角协方差矩阵
	 * @return
	 */
	public double normal(NDimNode mean, NDimNode sigma) {
		double mahalanobis;
		double productSigma = 1;
		NDimNode temp = new NDimNode(this);
		temp.subtract(mean);
		// compute the product of the deviations and the mahalanobis distance
		for (int i = 0; i < dimension; i++) {
			productSigma = sigma.getCoordinate(i) * productSigma;
			temp.setCoordinate(i, temp.getCoordinate(i) / sigma.getCoordinate(i));
		}
		mahalanobis = Math.pow(temp.norm(), 2);
		return (1.0 / (Math.pow((2 * Math.PI), dimension / 2.0) * productSigma) * Math.exp(-1.0 / 2 * mahalanobis));
	}

	public double getCoordinate(int i) {
		return coordinates[i];
	}

	public void setCoordinate(int i, double value) {
		coordinates[i] = value;
	}

	public int getDimension() {
		return dimension;
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	/**
	 * 输出坐标
	 */
	@Override
	public String toString() {
		String s = "" + coordinates[0];
		for (int i = 1; i < dimension; i++)
			s = s + " " + coordinates[i];
		return s;
	}

}