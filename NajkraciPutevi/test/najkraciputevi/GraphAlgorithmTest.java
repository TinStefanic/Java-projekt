/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package najkraciputevi;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thund
 */
public class GraphAlgorithmTest {
    final int n = 1000;
    final boolean directed = false;
    final double density = 3. / n;
    final int maxWeight = 1;
    
    public GraphAlgorithmTest() {
    }

    /**
     * Testira/uspoređuje algoritme.
     */
    @Test
    public void testCompareAlgorithms() {
        Graph g = Graph.randomGraph(n, directed, density, maxWeight);
        if (n <= 100) System.out.println(g);
        DijkstraAlgorithm da = new DijkstraAlgorithm(g);
        BellmanFordAlgorithm bfa = new BellmanFordAlgorithm(g);
        FloydWarshallAlgorithm fwa = new FloydWarshallAlgorithm(g);
        
        Integer daDist = da.query(0, n-1);
        Integer bfaDist = bfa.query(0, n-1);
        Integer fwaDist = fwa.query(0, n-1);
        
        System.out.println("");
        assertEquals(daDist, bfaDist);
        assertEquals(daDist, fwaDist);
        System.out.println("Najkraći put: " + daDist);
        
        System.out.println("");
        System.out.println("Dijkstra");
        System.out.println(da.getShortestPath());
        System.out.println("BellmanFord");
        System.out.println(bfa.getShortestPath());
        System.out.println("FloydWarshall");
        System.out.println(fwa.getShortestPath());
    }
}
