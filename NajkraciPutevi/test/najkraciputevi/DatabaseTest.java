/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package najkraciputevi;


import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection ;
import java.sql.DatabaseMetaData ;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * 
 */
public class DatabaseTest {
    
    String databaseName = "najkraciPutevi.db";
    String url = "jdbc:sqlite:" + databaseName ;
    Connection conn;
    
    public DatabaseTest() {
        try {
            this.conn = DriverManager.getConnection( url );
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of createTables method, of class Database.
     */
    @Test
    public void testCreateTables() {
        System.out.println("createTables");
        Database instance = new Database();
        instance.createTables();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of createEdgeTable method, of class Database.
     */
    @Test
    public void testCreateEdgeTable() {
        System.out.println("createEdgeTable");
        Database instance = new Database();
        instance.createEdgeTable();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of createCompletedAlgorithmTable method, of class Database.
     */
    @Test
    public void testCreateCompletedAlgorithmTable() {
        System.out.println("createCompletedAlgorithmTable");
        Database instance = new Database();
        instance.createCompletedAlgorithmTable();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of createShortestPathEdgeTable method, of class Database.
     */
    @Test
    public void testCreateShortestPathEdgeTable() {
        System.out.println("createShortestPathEdgeTable");
        Database instance = new Database();
        instance.createShortestPathEdgeTable();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insertGraph method, of class Database.
     */
    @Test
    public void testInsertGraph() {
        System.out.println("insertGraph");
        Graph G = new Graph(5);
        Database instance = new Database();
        int id = instance.insertGraph(G);
        String sql = "SELECT num_of_v FROM graph "
                + "WHERE graph_id = ?";
        Graph g;
        try{
         PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, id );
            ResultSet rs = pstmt.executeQuery( );
            rs.next();
            g = new Graph(rs.getInt("num_of_v"));
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
            g = new Graph(0);
        }
        assertEquals(g.getN(), G.getN());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insertEdge method, of class Database.
     */
    @Test
    public void testInsertEdge() {
        System.out.println("insertEdge");
        int graph_id = 100;
        int start = 1;
        int end = 5;
        int weight = 12;
        Database instance = new Database();
        instance.insertEdge(graph_id, start, end, weight);
        
        String sql = "SELECT * FROM edge "
                + "WHERE graph_id = ? AND start = ? AND end = ? AND weight = ?";
        Edge edge;
        int id;
        try{
         PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, graph_id );
            pstmt.setInt( 2, start );
            pstmt.setInt( 3, end );
            pstmt.setInt( 4, weight );
            ResultSet rs = pstmt.executeQuery( );
            rs.next();
            edge = new Edge(rs.getInt("start"), rs.getInt("end"), rs.getInt("weight"));
            id = rs.getInt("graph_id");
            assertEquals(graph_id, id);
            assertEquals(start, edge.getStart());
            assertEquals(end, edge.getEnd());
            assertEquals(weight, edge.getWeight() );
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insertCompletedAlgorithm method, of class Database.
     */
    @Test
    public void testInsertCompletedAlgorithm() {
        System.out.println("insertCompletedAlgorithm");
        int graph_id = 0;
        String alg_name = "";
        long time = 0L;
        int result_2 = 0;
        int start = 0;
        int end = 0;
        Database instance = new Database();
        instance.insertCompletedAlgorithm(graph_id, alg_name, time, result_2, start, end);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertShortestPath method, of class Database.
     */
    @Test
    public void testInsertShortestPath() {
        System.out.println("insertShortestPath");
        ShortestPath sp = new ShortestPath(1,6);
        sp.addEdge(1,3,5);
        sp.addEdge(3,5,2);
        sp.addEdge(5,6,1);
        int graph_id = 90;
        String alg_name = "FloydWarshall";
        Database instance = new Database();
        instance.insertShortestPath(sp, graph_id, alg_name);
        String sql = "SELECT * FROM shortest_path_edge "
                + "WHERE graph_id = ? AND alg_name = ? ORDER BY pos ASC";
        ShortestPath new_sp = new ShortestPath(1,6);
        try{
         PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, graph_id );
            pstmt.setString( 2, alg_name);
            ResultSet rs = pstmt.executeQuery( );
            while(rs.next()){
                new_sp.addEdge(rs.getInt("start"), rs.getInt("end"), rs.getInt("weight"));
            }
            assertEquals(sp.getDistance(), new_sp.getDistance());
            assertEquals(sp.getEdges().size(), new_sp.getEdges().size());
            assertEquals(sp.toString(), new_sp.toString());
            
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
        
        
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insertShortestPathEdge method, of class Database.
     */
    @Test
    public void testInsertShortestPathEdge() {
        System.out.println("insertShortestPathEdge");
        Edge edge = null;
        int pos = 0;
        int graph_id = 0;
        String alg_name = "";
        Database instance = new Database();
        instance.insertShortestPathEdge(edge, pos, graph_id, alg_name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectGraphById method, of class Database.
     */
    @Test
    public void testSelectGraphById() {
        System.out.println("selectGraphById");
        int id = 0;
        Database instance = new Database();
        Graph expResult = null;
        Graph result = instance.selectGraphById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectEdgesByGraphId method, of class Database.
     */
    @Test
    public void testSelectEdgesByGraphId() {
        System.out.println("selectEdgesByGraphId");
        int id = 0;
        Database instance = new Database();
        ArrayList<Edge> expResult = null;
        ArrayList<Edge> result = instance.selectEdgesByGraphId(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectCompletedAlgorithmsByGraphId method, of class Database.
     */
    @Test
    public void testSelectCompletedAlgorithmsByGraphId() {
        System.out.println("selectCompletedAlgorithmsByGraphId");
        int id = 0;
        Database instance = new Database();
        ArrayList<CompletedAlgorithm> expResult = null;
        ArrayList<CompletedAlgorithm> result = instance.selectCompletedAlgorithmsByGraphId(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectShortestPathByGraphAndAlg method, of class Database.
     */
    @Test
    public void testSelectShortestPathByGraphAndAlg() {
        System.out.println("selectShortestPathByGraphAndAlg");
        int graph_id = 0;
        String alg_name = "";
        Database instance = new Database();
        ShortestPath expResult = null;
        ShortestPath result = instance.selectShortestPathByGraphAndAlg(graph_id, alg_name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rowCount method, of class Database.
     */
    @Test
    public void testRowCount() {
        System.out.println("rowCount");
        String column = "graph";
        Database instance = new Database();
        int expResult = 0;
        int result = instance.rowCount(column);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
