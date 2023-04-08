package helper.pso;

import helper.genetic.GeneticAlgorithmEngine;
import helper.genetic.Population;
import helper.model.City;
import helper.model.CityManager;
import helper.model.Tour;
import helper.pso.Particle;

public class Test {
	public static void main(String[] args) {
        CityManager.getInstance().addCity(new City(1,1));
        CityManager.getInstance().addCity(new City(2,2));
        CityManager.getInstance().addCity(new City(3,3));
        CityManager.getInstance().addCity(new City(4,4));
        CityManager.getInstance().addCity(new City(5,5));

        Particle particle = new Particle();
        for(int i=0;i<10;i++) {
            System.out.println("particle.getCurrentSolution() = " + particle.getCurrentSolution());
            System.out.println("particle.getVelocity = " + particle.getVelocity());
            particle.updatePosition();
        }
    }
}
