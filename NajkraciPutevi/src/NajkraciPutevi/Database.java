/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NajkraciPutevi;

import java.sql.Connection ;
import java.sql.DatabaseMetaData ;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/* baza se satoji od sljedećih tablica
 * graph - id, num_of_v
 * edge - id, graph_id, start, end, weight
 * algorithm - id, name, can_weights_be_negative
 * completed_algorithm - id, graph_id, algorithm_id, duration
*/

public class Database {
    
    String databaseName = "najkraciPutevi.db";
    String url = " jdbc:sqlite:" + databaseName ;
    
    /* konstruktor stvara bazu i tablice (ako ne postoje) te
     * popuni tablicu algorithm
    */
    public Database() 
    {
      
        try ( Connection conn = DriverManager.getConnection( this.url ) ) {
            if ( conn != null ) {
                DatabaseMetaData meta = conn.getMetaData () ;
                System.out.println("Ime biblioteke za rad s bazom podataka " + meta.getDriverName() ) ;
                System.out.println(" Stvorena je nova baza.") ;
            }
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ;
        }
        
        this.createTables();
    }
    
    public void createTables()
    {
        this.createGraphTable();
        this.createAlgorithmTable();
        this.createEdgeTable();
        this.createCompletedAlgorithmTable();
        
        //this.insertAlgorithms();
    }
    
    public void createGraphTable()
    {
        
        String sql = " CREATE TABLE IF NOT EXISTS graph (\n"+ 
          " id integer PRIMARY KEY ,\n"
          + " num_of_v integer NOT NULL ,\n" + ");";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        try ( Connection conn = DriverManager.getConnection( this.url );
        Statement stmt = conn.createStatement() ) {
            if ( conn != null ) { stmt.execute( sql ) ;}
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ; 
        }
    }
    
    public void createAlgorithmTable()
    {
        String sql = " CREATE TABLE IF NOT EXISTS algorithm (\n"+ 
          " id integer PRIMARY KEY ,\n"
          + " name text NOT NULL ,\n" 
          + "neg_weights integer NOT NULL ,\n" + ");";
        try ( Connection conn = DriverManager.getConnection( url );
        Statement stmt = conn.createStatement() ) {
            if ( conn != null ) { stmt . execute ( sql ) ;}
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ; 
        }
    }
    
    public void createEdgeTable()
    {
        String sql = " CREATE TABLE IF NOT EXISTS edge (\n"+ 
          " id integer PRIMARY KEY ,\n"
          + " start integer NOT NULL ,\n" 
          + " end integer NOT NULL ,\n" 
          + " weight integer NOT NULL" + ");";
        //Class.forName("org.sqlite.JDBC");
        try ( 
             
             Connection conn = DriverManager.getConnection( url );
             Statement stmt = conn.createStatement() ) {
             if ( conn != null ) { stmt.execute( sql ) ;}
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ; 
        }
    }
    
    public void createCompletedAlgorithmTable()
    {
        String sql = " CREATE TABLE IF NOT EXISTS completed_algorithm (\n"+ 
          " id integer PRIMARY KEY ,\n"
          + " graph_id integer NOT NULL ,\n" 
          + " algorithm_id integer NOT NULL ,\n" 
          + " duration real NOT NULL, \n"
          + " result integer NOT NULL);";
        try ( 
             Connection conn = DriverManager.getConnection( this.url );
             Statement stmt = conn.createStatement() ) {
             if ( conn != null ) { stmt.execute( sql ) ;}
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ; 
        }
    }
    
    public void insertAlgorithms()
    {
        
    }
    
    public int insertGraph( Graph G ) //vraca id grafa
    {
        int id = this.rowCount("graph") + 1; // odredi id
        String sql = " INSERT INTO graph (id, num_of_v ) "
                + "VALUES (? ,?)" ;
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            pstmt.setInt(1 , id );
            pstmt.setInt(2 , G.getN() );
            pstmt.executeUpdate ();
             }
        catch ( SQLException e ) { }
       
        for( int i = 0; i < G.getN(); ++i ){
            for( int j = 0; j <  G.getN(); ++j){
                this.insertEdge(id, i, j, G.getWeightBetween(i, j));
            }
        }
        
        return id;
    }
    
    public void insertEdge( int graph_id, int start, int end, int weight )
    {
        int id = this.rowCount("edge") + 1; // odrediti id (row count + 1)
        String sql = " INSERT INTO edge (id, graph_id , start , end, weight ) "
                + "VALUES (? ,? ,? ,? ,?)" ;
        
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            
            pstmt.setInt(1 , id );
            pstmt.setInt(2 , graph_id );
            pstmt.setInt(3 , start );
            pstmt.setInt(4 , end );
            pstmt.setInt(5 , weight );
            pstmt.executeUpdate ();
             }
        catch ( SQLException e ) { }
    }
    
    public void insertCompletedAlgorithm( int graph_id, int alg_id, int time)
    {
        int id = this.rowCount("completed_algorithm") + 1; // odrediti id (row count + 1)
        String sql = " INSERT INTO completed_algorithm (id, graph_id , alg_id , duration ) "
                + "VALUES (? ,? ,? ,?)" ;
        
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            
            pstmt.setInt(1 , id );
            pstmt.setInt(2 , graph_id );
            pstmt.setInt(3 , alg_id );
            pstmt.setInt(4 , time );
            pstmt.executeUpdate ();
             }
        catch ( SQLException e ) { }
    }
    
    public Graph selectGraphById( int id )
    {
        String sql = " SELECT num_of_v FROM graph"
                + "WHERE graph_id = ? ";
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, id );
            
            ResultSet rs = pstmt.executeQuery( sql );
            rs.next();
            Graph g = new Graph(rs.getInt("num_of_v"));
            ArrayList<Edge> edges = this.selectEdgesByGraphId(id);
            for(Edge edge: edges){
                try {
                    g.addEdge(edge.getStart(), edge.getEnd(), edge.getWeight());
                } catch (Exception ex) { }
            }
            return g;
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
            Graph g = new Graph(0);
            return g;
        }
    }
    
    public ArrayList<Edge> selectEdgesByGraphId(int id)
    {
        String sql = " SELECT start, end, weight FROM graph"
                + "WHERE graph_id = ? ";
        ArrayList<Edge> edges = new ArrayList<Edge>();
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, id );
            ResultSet rs = pstmt.executeQuery( sql );
            while( rs.next() ){
                edges.add( new Edge(rs.getInt("start"), rs.getInt("end"), rs.getInt("weight")));
            }
            
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
        return edges;
    }
    
    public ArrayList<CompletedAlgorithm> selectCompletedAlgorithmsByGraphId( int id )
    {
        ArrayList<CompletedAlgorithm> alg = new ArrayList<CompletedAlgorithm>();
        String sql = " SELECT graph_id , algorithm_id , duration, result FROM completed_algorithm"
                + "WHERE graph_id = ? ";
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, id );
            
            ResultSet rs = pstmt.executeQuery( sql );
            while ( rs.next() ) {
               String name = this.selectAlgNameByAlgId(rs.getInt("algorithm_id"));
               double time = rs.getDouble("duration");
               int result = rs.getInt("result");
               alg.add(new CompletedAlgorithm(name, time, result));
            }
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
        
        return alg;
    }
    
    public String selectAlgNameByAlgId(int id)
    {
        String sql = " SELECT name FROM algorithm WHERE id=?";
        String name;
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, id );
            
            ResultSet rs = pstmt.executeQuery( sql );
            rs.next();
            name = rs.getString("name");
            
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
            name="";
        }
        return name;
    }
    
    public int rowCount( String column)
    {
        String sql = " SELECT count(*) FROM " + column;
        int count = 0;
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement( sql );
            
            ResultSet rs = pstmt.executeQuery( sql );
            rs.next();
            count = rs.getInt(1);
            
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
        return count;
    }
    
}
