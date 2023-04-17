package christofides;

import java.util.*;

public class MainRunner {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        TSPRoute tspRoute = new TSPRoute();
        tspRoute.build(reader);
        
        reader.close();
    }    
}