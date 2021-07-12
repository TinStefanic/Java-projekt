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


/* baza se satoji od sljedećih tablica
 * graph - id, num_of_v
 * edge - id, graph_id, start_v, end_v, weight
 * algorithm - id, name, can_weights_be_negative
 * completed_algorithm - id, graph_id, algorithm_id, duration
*/

public class Database {
    
    String databaseName = " najkraciPutevi.db";
    String url = " jdbc : sqlite :" + databaseName ;
    
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
        
        this.insertAlgorithms();
    }
    
    public void createGraphTable()
    {
   
        String sql = " CREATE TABLE IF NOT EXISTS graph (\n"+ 
          " id integer PRIMARY KEY ,\n"
          + " num_of_v integer NOT NULL ,\n" + ");";
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
        String fileName = "najkraciPutevi.db";
        String url = " jdbc : sqlite :" + fileName ;
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
        String fileName = "najkraciPutevi.db";
        String url = " jdbc : sqlite :" + fileName ;
        String sql = " CREATE TABLE IF NOT EXISTS edge (\n"+ 
          " id integer PRIMARY KEY ,\n"
          + " start_v integer NOT NULL ,\n" 
          + " end_v integer NOT NULL ,\n" 
          + " weight integer NOT NULL" + ");";
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
        String fileName = "najkraciPutevi.db";
        String url = " jdbc : sqlite :" + fileName ;
        String sql = " CREATE TABLE IF NOT EXISTS completed_algorithm (\n"+ 
          " id integer PRIMARY KEY ,\n"
          + " graph_id integer NOT NULL ,\n" 
          + " algorithm_id integer NOT NULL ,\n" 
          + " duration text NOT NULL" + ");";
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
    
    public void insertGraph( Graph G)
    {
        String sql = " INSERT INTO edge (id, num_of_v ) "
                + "VALUES (? ,?)" ;
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            int id = 0; // odrediti id (row count + 1)
            pstmt.setInt(1 , id );
            pstmt.setInt(2 , G.getN() );
            pstmt.executeUpdate ();
             }
        catch ( SQLException e ) { }
        
        //this.insertEdge(...)
    }
    
    public void insertEdge( int graph_id, int start, int end, int weight )
    {
        String sql = " INSERT INTO edge (id, graph_id , start_v , end_v, weight ) "
                + "VALUES (? ,? ,? ,? ,?)" ;
        
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            int id = 0; // odrediti id (row count + 1)
            pstmt.setInt(1 , id );
            pstmt.setInt(2 , graph_id );
            pstmt.setInt(3 , start );
            pstmt.setInt(4 , end );
            pstmt.setInt(5 , weight );
            pstmt.executeUpdate ();
             }
        catch ( SQLException e ) { }
    }
    
    public void insertCompletedAlgorithm( int graph_id, int alg_id, String time)
    {
        String sql = " INSERT INTO completed_algorithm (id, graph_id , alg_id , duration ) "
                + "VALUES (? ,? ,? ,?)" ;
        
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            int id = 0; // odrediti id (row count + 1)
            pstmt.setInt(1 , id );
            pstmt.setInt(2 , graph_id );
            pstmt.setInt(3 , alg_id );
            pstmt.setString(4 , time );
            pstmt.executeUpdate ();
             }
        catch ( SQLException e ) { }
    }
    
    public void selectGraphById( int id )
    {
        
    }
    
    public void selectCompletedAlgorithmsByGraphId( int id )
    {
        String sql = " SELECT graph_id , algorithm_id , duration FROM completed_algorithm"
                + "WHERE graph_id = ? ";
        try {
            Connection conn = DriverManager.getConnection( this.url );
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt(1 , id );
            
            ResultSet rs = pstmt.executeQuery ( sql );
            while ( rs.next() ) {
               
            }
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
    }
    
}
