package NajkraciPutevi;

import najkraciputevi.Graph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {
    private Graph g, gg;
    
    public GraphTest() {
        g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 2);
        g.addEdge(1, 2, 3);
        g.addEdge(2, 1, 3);
        g.addEdge(2, 3, 5);
        
        gg = new Graph(3);
        gg.addEdge(0, 1, 1);
        gg.addEdge(1, 2, 3);
        gg.addEdge(2, 0, -1);
    }
    
    /**
     * Test of addEdge method, of class Graph.
     */
    @Test
    public void testAddEdge() {
        Graph instance = new Graph(3);
        instance.addEdge(0, 1, 1);
        String expected = "0. 1:1\n1.\n2.\n";
        assertEquals(instance.toString(), expected);
        instance.addEdge(1, 2, 3);
        expected = "0. 1:1\n1. 2:3\n2.\n";
        assertEquals(instance.toString(), expected);
        instance.addEdge(2, 0, 5);
        expected = "0. 1:1\n1. 2:3\n2. 0:5\n";
        assertEquals(instance.toString(), expected);
        instance.addEdge(0, 1, 7);
        expected = "0. 1:7\n1. 2:3\n2. 0:5\n";
        assertEquals(instance.toString(), expected);
        instance.addEdge(0, 2, 7);
        expected = "0. 1:7, 2:7\n1. 2:3\n2. 0:5\n";
        assertEquals(instance.toString(), expected);
        expected = "0. 1:1, 2:2\n1. 2:3\n2. 1:3, 3:5\n3.\n";
        assertEquals(g.toString(), expected);
    }

    /**
     * Test of getWeightBetween method, of class Graph.
     */
    @Test
    public void testGetWeightBetween() {
       assertEquals(g.getWeightBetween(0, 1), (Integer)1);
       assertEquals(g.getWeightBetween(0, 2), (Integer)2);
       assertEquals(g.getWeightBetween(1, 2), (Integer)3);
       assertEquals(g.getWeightBetween(2, 1), (Integer)3);
       assertEquals(g.getWeightBetween(2, 3), (Integer)5);
       assertEquals(g.getWeightBetween(0, 3), (Integer)null);
    }

    /**
     * Test of getNeighbourAt method, of class Graph.
     */
    @Test
    public void testGetNeighbourAt() {
        assertEquals(g.getNeighbourAt(0, 0) , (Integer)1);
        assertEquals(g.getNeighbourAt(0, 1) , (Integer)2);
        assertEquals(g.getNeighbourAt(2, 1) , (Integer)3);
    }

    /**
     * Test of getWeightAt method, of class Graph.
     */
    @Test
    public void testGetWeightAt() {
        assertEquals(g.getWeightAt(0, 0), (Integer)1);
        assertEquals(g.getWeightAt(0, 1), (Integer)2);
        assertEquals(g.getWeightAt(2, 0), (Integer)3);
    }

    /**
     * Test of getNumNeighbours method, of class Graph.
     */
    @Test
    public void testGetNumNeighbours() {
        assertEquals(g.getNumNeighbours(0), 2);
        assertEquals(g.getNumNeighbours(1), 1);
        assertEquals(g.getNumNeighbours(2), 2);
        assertEquals(g.getNumNeighbours(3), 0);
    }

    /**
     * Test of hasNegativeWeights method, of class Graph.
     */
    @Test
    public void testHasNegativeWeights() {
        assertEquals(g.hasNegativeWeights(), false);
        assertEquals(gg.hasNegativeWeights(), true);
    }

    /**
     * Test of randomGraph method, of class Graph.
     */
    @Test
    public void testRandomGraph() {
        System.out.println("testRandomGraph");
        Graph instance = Graph.randomGraph();
        System.out.println(instance);
        instance = Graph.randomGraph(8);
        System.out.println(instance);
        instance = Graph.randomGraph(8, true);
        System.out.println(instance);
        instance = Graph.randomGraph(8, true, 0.8);
        System.out.println(instance);
        instance = Graph.randomGraph(8, true, 0.8, 1000);
        System.out.println(instance);
    }
}
