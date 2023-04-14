package christofides.optimizations;

import java.util.ArrayList;
import java.util.Random;

//import christofides.Edge;
import christofides.Graph;
//import christofides.Node;

public class GeneticAlgoSolver {
	
//	Graph tour;
//	int numVertices = 0;
//	ArrayList<Node> nodes;
//	private static final double mutationProb = 0.015;
//    private static final int tournamentSize = 8;
//	
//	public GeneticAlgoSolver(Graph tour) {
//		this.tour = tour;
//		this.numVertices = this.tour.getVertexCount();
//		this.nodes = this.tour.getNodes();
//	}
//	
//	public Graph buildTour(int populationSize) {
//		String[][] initialPopulation = initializePopulation(populationSize);
//        for (int generations = 0; generations < 1000; generations++) {
//        	double[] fitness = evaluateFitness(initialPopulation);
//        	String[][] parents = selectParents(initialPopulation, fitness);
//        	String[][] newPopulation = performCrossover(parents, initialPopulation.length);
//        	mutatePopulation(newPopulation);
//        	initialPopulation = newPopulation;
//        }
//        
//        double[] fitness = evaluateFitness(initialPopulation);
//        int bestTourIndex = findBestTourIndex(fitness);
//        String[] GATour = initialPopulation[bestTourIndex];
//        ArrayList<Edge> newGATour = generateArrayListOfEdgesFromTour(GATour);
//        return new Graph(newGATour);
//	}
//	
//	public String[][] initializePopulation(int popSize) {
//	    String[][] population = new String[popSize][numVertices];
//
//	    for (int i = 0; i < popSize; i++) {
//	        population[i] = getRandomTour(numVertices);
//	    }
//
//	    return population;
//	}
//	
//	private String[] getRandomTour(int vertices) {
//		Random random = new Random();
//        String[] tour = new String[vertices];
//        for (int i = 0; i < vertices; i++) {
//        	Node currentNode = nodes.get(i);
//            tour[i] = currentNode.getID();
//        }
//        for (int i = vertices - 1; i > 0; i--) {
//            int index = random.nextInt(i + 1);
//            String temp = tour[index];
//            tour[index] = tour[i];
//            tour[i] = temp;
//        }
//        return tour;
//    }
//	
//	public double[] evaluateFitness(String[][] population) {
//	    double[] fitness = new double[population.length];
//
//	    for (int i = 0; i < population.length; i++) {
//	        double tourLength = calculateTourLength(population[i]);
//	        fitness[i] = 1 / tourLength;
//	    }
//
//	    return fitness;
//	}
//
//	private double calculateTourLength(String[] tour) {
//	    double tourLength = 0.0;
//	    int n = tour.length;
//
//	    // Compute the length of each edge in the tour
//	    for (int i = 0; i < n; i++) {
//	        int j = (i + 1) % n;
//	        Edge newEdge = new Edge(nodes.get(findNodeIndex(tour[i])), nodes.get(findNodeIndex(tour[j])));
//	        tourLength += newEdge.getEdgeWeight();
//	    }
//
//	    return tourLength;
//	}
//	
//	private int findNodeIndex(String ID) {
//		int length = nodes.size();
//		int foundIndex = -1;
//		for (int i=0; i < length; i++) {
//			if (nodes.get(i).getID() == ID) {
//				foundIndex = i;
//				break;
//			}
//		}
//		return foundIndex;
//	}
//	
//	public String[][] selectParents(String[][] population, double[] fitness) {
//	    String[][] parents = new String[2][numVertices];
//
//	    for (int i = 0; i < 2; i++) {
//	        String[] tournament = getRandomTournament(population, fitness);
//	        parents[i] = tournament;
//	    }
//
//	    return parents;
//	}
//
//	public String[] getRandomTournament(String[][] population, double[] fitness) {
//	    int[] tournament = new int[tournamentSize];
//
//	    // Randomly select `tournamentSize` individuals from the population
//	    for (int i = 0; i < tournamentSize; i++) {
//	        int index = (int) (Math.random() * population.length);
//	        tournament[i] = index;
//	    }
//
//	    // Find the individual with the highest fitness in the tournament
//	    int winner = tournament[0];
//	    for (int i = 1; i < tournamentSize; i++) {
//	        int competitor = tournament[i];
//	        if (fitness[competitor] > fitness[winner]) {
//	            winner = competitor;
//	        }
//	    }
//
//	    // Return the winner of the tournament
//	    return population[winner];
//	}
//	
//	public String[][] performCrossover(String[][] parents, int populationLength) {
//		String[][] offspring = new String[populationLength][numVertices];
//
//	    for (int i = 0; i < populationLength; i++) {
//	    	String[] parent1 = parents[0];
//	    	String[] parent2 = parents[1];
//
//	    	String[] child = getRandomCrossover(parent1, parent2);
//	        offspring[i] = child;
//	    }
//
//	    return offspring;
//	}
//	
//	private String[] getRandomCrossover(String[] parent1, String[] parent2) {
//	    int length = parent1.length;
//	    String[] child = new String[length];
//	    
//	    int startPoint = (int) (Math.random() * length);
//	    int endPoint = (int) (Math.random() * length);
//	    
//	    if (startPoint < endPoint) {
//	    	copyParentToChild(child, parent1, parent2, startPoint, endPoint, length, false);
//	    } else if (endPoint > startPoint) {
//	    	copyParentToChild(child, parent1, parent2, endPoint, startPoint, length, false);
//	    } else {
//	    	copyParentToChild(child, parent1, parent2, startPoint, endPoint, length, true);
//	    }
//
//	    return child;
//	}
//	
//	private void copyParentToChild(String[] child, String[] parent1, String[] parent2, int startPoint, int endPoint, int length, boolean isSame) {
//		if (!isSame) {
//			for (int i=startPoint; i < endPoint; i++) {
//	    		child[i] = parent1[i];
//	    	}
//	    	for (int i=0; i < parent2.length; i++) {
//	    		if (!containsNode(child, parent2[i])) {
//	    			for (int j=0; j < child.length; j++) {
//	    				if (child[j] == null) {
//	    					child[j] = parent2[i];
//	    					break;
//	    				}
//	    			}
//	    		}
//	    	}	
//		} else {
//			// Copy the first part of the parent1 into the child
//		    for (int i = 0; i < startPoint; i++) {
//		        child[i] = parent1[i];
//		    }
//	
//		    // Copy the remaining part of the parent2 into the child
//		    
//		    for (int i=0; i < parent2.length; i++) {
//	    		if (!containsNode(child, parent2[i])) {
//	    			for (int j=0; j < child.length; j++) {
//	    				if (child[j] == null) {
//	    					child[j] = parent2[i];
//	    					break;
//	    				}
//	    			}
//	    		}
//	    	}
//		}
//	}
//	
//	private boolean containsNode(String[] array, String target) {
//        for(String item:array){
//            if(item!=null && item.equals(target)){
//                return true;
//            }
//        }
//        return false;
//    }
//	
//	public void mutatePopulation(String[][] population) {
//	    for (int i = 0; i < population.length; i++) {
//	        if (Math.random() < mutationProb) {
//	            String[] mutatedTour = mutateTour(population[i]);
//	            population[i] = mutatedTour;
//	        }
//	    }
//	}
//	
//	private String[] mutateTour(String[] tour) {
//	    int tourSize = tour.length;
//	    int tourPos1 = (int) (tourSize * Math.random());
//	    int tourPos2 = (int) (tourSize * Math.random());
//	    
//	    // Swap two cities in the tour
//	    String tempCity = tour[tourPos1];
//	    tour[tourPos1] = tour[tourPos2];
//	    tour[tourPos2] = tempCity;
//
//	    return tour;
//	}
//	
//	public int findBestTourIndex(double[] fitness) {
//	    double bestFitness = Double.NEGATIVE_INFINITY;
//	    int bestIndex = -1;
//
//	    for (int i = 0; i < fitness.length; i++) {
//	        if (fitness[i] > bestFitness) {
//	            bestFitness = fitness[i];
//	            bestIndex = i;
//	        }
//	    }
//
//	    return bestIndex;
//	}
//	
//	public ArrayList<Edge> generateArrayListOfEdgesFromTour(String[] tour) {
//		ArrayList<Edge> newTour = new ArrayList<>();
//		int n = tour.length;
//		for (int i=0; i < n; i++) {
//			int j = (i + 1) % n;
//	        Edge newEdge = new Edge(nodes.get(findNodeIndex(tour[i])), nodes.get(findNodeIndex(tour[j])));
//	        newTour.add(newEdge);
//		}
//		
//		return newTour;
//	}
}
