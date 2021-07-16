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
import java.util.Random;


/**
 *
 * 
 */
public class DatabaseTest {
    
    String databaseName = "najkraciPutevi.db";
    String url = "jdbc:sqlite:" + databaseName ;
    Connection conn;
    //Database db;
    
    public DatabaseTest() {
        try {
            this.conn = DriverManager.getConnection( url );
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        //db = new Database();
    }

   @Test
    public void testInsertGraph() {
        System.out.println("insertGraph");
        Graph G = new Graph(5);
        Database instance = new Database();
        int id = instance.insertGraph(G);
        //int id = db.insertGraph(G); //instance.insertGraph(G);
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
        assertEquals(G.getN(), g.getN());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insertEdge method, of class Database.
     */
    @Test
    public void testInsertEdge() {
        System.out.println("insertEdge");
        int graph_id = new Random().nextInt(5000);
        int start = 1;
        int end = 5;
        int weight = 12;
        Database instance = new Database();
        instance.insertEdge(graph_id, start, end, weight);
      // db.insertEdge(graph_id, start, end, weight);
        
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
        
    }

    /**
     * Test of insertCompletedAlgorithm method, of class Database.
     */
    @Test
    public void testInsertCompletedAlgorithm() {
        System.out.println("insertCompletedAlgorithm");
        int graph_id = new Random().nextInt(50000);
        String alg_name = "Dijkstra";
        long time = 25L;
        int result_2 = 5;
        int start = 1;
        int end = 6;
        Database instance = new Database();
        instance.insertCompletedAlgorithm(graph_id, alg_name, time, result_2, start, end);
        //db.insertCompletedAlgorithm(graph_id, alg_name, time, result_2, start, end);
        String sql = "SELECT * FROM completed_algorithm "
                + "WHERE graph_id = ? AND start = ? AND end = ? AND duration = ? and result = ?";
        try{
         PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, graph_id );
            pstmt.setInt( 2, start );
            pstmt.setInt( 3, end );
            pstmt.setLong( 4, time );
            pstmt.setInt( 4, result_2 );
            ResultSet rs = pstmt.executeQuery( );
            rs.next();
            int id = rs.getInt("graph_id");
            int s = rs.getInt("start");
            int e = rs.getInt("end");
            long t = rs.getLong("duration");
            int r = rs.getInt("result");
            assertEquals(graph_id, id);
            assertEquals(start, s);
            assertEquals(end, e);
            assertEquals(time, t );
            assertEquals(result_2, r );
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
        
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
        int graph_id = new Random().nextInt(5000);
        String alg_name = "FloydWarshall";
        Database instance = new Database();
        instance.insertShortestPath(sp, graph_id, alg_name);
        //db.insertShortestPath(sp, graph_id, alg_name);
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
        int start = new Random().nextInt(15);
        int end = new Random().nextInt(15);
        int weight = new Random().nextInt(15);
        Edge edge = new Edge(start,end,weight);
        int pos = 5;
        int graph_id = new Random().nextInt(50000);
        String alg_name = "FloydWarshall";
        Database instance = new Database();
        instance.insertShortestPathEdge(edge, pos, graph_id, alg_name);
        //db.insertShortestPathEdge(edge, pos, graph_id, alg_name);
        String sql = "SELECT * FROM shortest_path_edge "
                + "WHERE graph_id = ? AND alg_name = ? AND pos = ? AND start = ? AND end = ? AND weight = ?";
        try{
         PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, graph_id );
            pstmt.setString( 2, alg_name);
            pstmt.setInt( 3, pos);
            pstmt.setInt( 4, start);
            pstmt.setInt( 5, end);
            pstmt.setInt( 6, weight);
            ResultSet rs = pstmt.executeQuery( );
            rs.next();
            int id = rs.getInt("graph_id");
            String name = rs.getString("alg_name");
            int p = rs.getInt("pos");
            int s = rs.getInt("s");
            int e = rs.getInt("e");
            int w = rs.getInt("weight");
            assertEquals(id, graph_id);
            assertEquals(name, alg_name);
            assertEquals(p, pos);
            assertEquals(s, start);
            assertEquals(e, end);
            assertEquals(w, weight);
            
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of selectGraphById method, of class Database.
     */
    @Test
    public void testSelectGraphById() {
        System.out.println("selectGraphById");
        int id = new Random().nextInt(50000);
        Database instance = new Database();
        Graph expResult = new Graph(5);
        String sql = "INSERT INTO graph(graph_id,num_of_v) "
                + "VALUES(?,?)" ;
        try {
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            pstmt.setInt(1, id);
            pstmt.setInt(2, expResult.getN());
            pstmt.executeUpdate ();
            }
        catch ( SQLException e ) { System.out.println( e.getMessage() ); }
        Graph result = instance.selectGraphById(id);
        assertEquals(expResult.getN(), result.getN());
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of selectEdgesByGraphId method, of class Database.
     */
    @Test
    public void testSelectEdgesByGraphId() {
        System.out.println("selectEdgesByGraphId");
        int graph_id = new Random().nextInt(5000);
        Database instance = new Database();
        int s1 = new Random().nextInt(100);
        int e1 = new Random().nextInt(100);
        int w1 = new Random().nextInt(100);
        int s2 = new Random().nextInt(100);
        int e2 = new Random().nextInt(100);
        int w2 = new Random().nextInt(100);
        Edge edge1 = new Edge(s1,e1,w1);
        Edge edge2 = new Edge(s2,e2,w2);
        ArrayList<Edge> expResult = new ArrayList<Edge>();
        expResult.add(edge1); expResult.add(edge2);
        String sql = "INSERT INTO edge(id,graph_id,start,end,weight) "
                + "VALUES(?,?,?,?,?)" ;
        try {
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            pstmt.setInt(1, new Random().nextInt(5000));
            pstmt.setInt(2, graph_id);
            pstmt.setInt(3, s1);
            pstmt.setInt(4, e1);
            pstmt.setInt(5, w1);
            pstmt.executeUpdate ();
            pstmt.close();
            }
        catch ( SQLException e ) { System.out.println( e.getMessage() ); }
        sql = "INSERT INTO edge(id,graph_id,start,end,weight) "
                + "VALUES(?,?,?,?,?)" ;
        try {
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            pstmt.setInt(1, new Random().nextInt(5000));
            pstmt.setInt(2, graph_id);
            pstmt.setInt(3, s2);
            pstmt.setInt(4, e2);
            pstmt.setInt(5, w2);
            pstmt.executeUpdate ();
            pstmt.close();
            }
        catch ( SQLException e ) { System.out.println( e.getMessage() ); }
        ArrayList<Edge> result = instance.selectEdgesByGraphId(graph_id);
        assertEquals(2, result.size());
        assertEquals(s1, result.get(0).getStart());
        assertEquals(e1, result.get(0).getEnd());
        assertEquals(w1, result.get(0).getWeight());
        assertEquals(s2, result.get(1).getStart());
        assertEquals(e2, result.get(1).getEnd());
        assertEquals(w2, result.get(1).getWeight());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
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
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of selectShortestPathByGraphAndAlg method, of class Database.
     */
    @Test
    public void testSelectShortestPathByGraphAndAlg() {
        System.out.println("selectShortestPathByGraphAndAlg");
        int graph_id = new Random().nextInt(5000);
        String alg_name = "ellmanFord";
        Database instance = new Database();
        int id = new Random().nextInt(5000);
        int start = new Random().nextInt(20);
        int end = new Random().nextInt(20);
        int weight = new Random().nextInt(20);
        int pos = 1;
        ShortestPath expResult = new ShortestPath(start, end);
        expResult.addEdge(start,end,weight);
         String sql = "INSERT INTO shortest_path_edge(id,graph_id,alg_name,start,end,weight,pos)"
                + "VALUES(?,?,?,?,?,?,?)" ;
        try {
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            
            pstmt.setInt(1 , id );
            pstmt.setInt(2 , graph_id );
            pstmt.setString(3 , alg_name );
            pstmt.setInt(4 , start );
            pstmt.setInt(5 , end );
            pstmt.setInt(6, weight);
            pstmt.setInt(7, pos);
            pstmt.executeUpdate ();
             }
        catch ( SQLException e ) { }
        ShortestPath result = instance.selectShortestPathByGraphAndAlg(graph_id, alg_name);
        assertEquals(result.getStart(), start);
        assertEquals(result.getEnd(), end);
        assertEquals(result.getEdges().size(), 1);
        assertEquals(result.getDistance(), weight);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

 
    
}
