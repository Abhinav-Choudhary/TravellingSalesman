package edu.neu.psa.tsp;

public class SimulatedAnnealing {

	public static void main(String[] args) {
		int[][] distanceMatrix = {{0, 20, 42, 35}, {20, 0, 30, 34}, {42, 30, 0, 12}, {35, 34, 12, 0}};
        int[] path = solveTSP(distanceMatrix);
        System.out.println("Path:");
        for (int i = 0; i < path.length; i++) {
            System.out.print(path[i] + " ");
        }
	}
	
	public static int[] solveTSP(int[][] distanceMatrix) {
        int[] path = new int[distanceMatrix.length];
        for (int i = 0; i < path.length; i++) {
            path[i] = i;
        }
        double temperature = 10000;
        double coolingRate = 0.003;
        while (temperature > 1) {
            int[] newPath = swap(path);
            double currentEnergy = calculateTotalDistance(distanceMatrix, path);
            double newEnergy = calculateTotalDistance(distanceMatrix, newPath);
            if (acceptanceProbability(currentEnergy, newEnergy, temperature) > Math.random()) {
                path = newPath;
            }
            temperature *= (1 - coolingRate);
        }
        return path;
    }

    public static int[] swap(int[] path) {
        int index1 = (int) (path.length * Math.random());
        int index2 = (int) (path.length * Math.random());
        while (index1 == index2) {
            index2 = (int) (path.length * Math.random());
        }
        int temp = path[index1];
        path[index1] = path[index2];
        path[index2] = temp;
        return path;
    }

    public static double calculateTotalDistance(int[][] distanceMatrix, int[] path) {
        double totalDistance = distanceMatrix[path[path.length - 1]][path[0]];
        for (int i = 0; i < path.length - 1; i++) {
            totalDistance += distanceMatrix[path[i]][path[i + 1]];
        }
        return totalDistance;
    }

    public static double acceptanceProbability(double currentEnergy, double newEnergy, double temperature) {
        if (newEnergy < currentEnergy) {
            return 1.0;
        }
        return Math.exp((currentEnergy - newEnergy) / temperature);
    }

}
