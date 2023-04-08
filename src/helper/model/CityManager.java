package helper.model;

import java.util.ArrayList;

public class CityManager {
	private static final CityManager instance = new CityManager();
    private final ArrayList<City> cities;

    public static CityManager getInstance() {
        return instance;
    }

    private CityManager() {
        cities = new ArrayList<>();
    }
    // Holds our cities

    // Adds a destination city
    public void addCity(City city) {
        cities.add(city);
    }

    // Get a city
    public City getCity(int index) {
        return cities.get(index);
    }

    // Get the number of destination cities
    public int numberOfCities() {
        return cities.size();
    }

    public void clear() {
        cities.clear();
    }
}
