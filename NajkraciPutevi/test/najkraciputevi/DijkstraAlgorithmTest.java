/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package najkraciputevi;

import najkraciputevi.DijkstraAlgorithm;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DijkstraAlgorithmTest {
    private Graph g;
    
    public DijkstraAlgorithmTest() {
        g = new Graph(6);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 2);
        g.addEdge(1, 2, 3);
        g.addEdge(1, 3, 10);
        g.addEdge(2, 1, 3);
        g.addEdge(2, 3, 5);
        g.addEdge(3, 4, 3);
    }

    /**
     * Test of runAlgorithm method, of class DijkstraAlgorithm.
     */
    @Test
    public void testRunAlgorithm() {
        DijkstraAlgorithm instance = new DijkstraAlgorithm(g);
        assertEquals((Integer)1, instance.runAlgorithm(0, 1));
        assertEquals((Integer)3, instance.runAlgorithm(1, 2));
        assertEquals((Integer)5, instance.runAlgorithm(2, 3));
        assertEquals(null, instance.runAlgorithm(0, 5));
        assertEquals((Integer)10, instance.runAlgorithm(0, 4));
    }

    /**
     * Test of getShortestPath method, of class DijkstraAlgorithm.
     */
    @Test
    public void testGetShortestPath() {
        DijkstraAlgorithm instance = new DijkstraAlgorithm(g);
        assertEquals(null, instance.getShortestPath());
        instance.query(0, 1);
        assertEquals("0->1", instance.getShortestPath().toString());
        instance.query(1, 2);
        assertEquals("1->2", instance.getShortestPath().toString());
        instance.query(2, 3);
        assertEquals("2->3", instance.getShortestPath().toString());
        instance.query(0, 5);
        assertEquals(null, instance.getShortestPath());
        instance.query(0, 4);
        assertEquals("0->2->3->4", instance.getShortestPath().toString());
    }
    
}
