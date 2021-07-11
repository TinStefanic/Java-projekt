/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NajkraciPutevi;

import java . sql . Connection ;
import java . sql . DatabaseMetaData ;
import java . sql . DriverManager ;
import java . sql . SQLException ;

/* baza se satoji od sljedećih tablica
 * graph - id, num_of_v
 * edge - id, graph_id, start_v, end_v, weight
 * algorithm - id, name, can_weights_be_negative
 * izvedeni_algoritam - id, graph_id, algoritm_id, duration
*/

public class Database {
    
    /* konstruktor stvara bazu i tablice (ako ne postoje) te
     * popuni tablicu algorithm
    */
    public Database() 
    {
        String imeBaze = " najkraciPutevi.db";
        String url = " jdbc : sqlite :" + imeBaze ;
        try ( Connection conn = DriverManager.getConnection( url ) ) {
            if ( conn != null ) {
                DatabaseMetaData meta = conn.getMetaData () ;
                System.out.println("Ime biblioteke za rad s bazom podataka " + meta.getDriverName() ) ;
                System.out.println(" Stvorena je nova baza.") ;
            }
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ;
        }
        
        Database.createTables();
    }
    
    public static void createTables()
    {
        Database.createGraphTable();
        Database.createAlgorithmTable();
        Database.createEdgeTable();
        Database.createCompletedAlgorithmTable();
        
        Database.insertAlgorithms();
    }
    
    public static void createGraphTable()
    {
        String fileName = "najkraciPutevi.db";
        String url = " jdbc : sqlite :" + fileName ;
        String sql = " CREATE TABLE IF NOT EXISTS graph (\n"+ 
          " id integer PRIMARY KEY ,\n"
          + " num_of_v integer NOT NULL ,\n" + ");";
        try ( Connection conn = DriverManager.getConnection( url );
        Statement stmt = conn.createStatement() ) {
            if ( conn != null ) { stmt.execute( sql ) ;}
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ; 
        }
    }
    
    public static void createAlgorithmTable()
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
    
    public static void createEdgeTable()
    {
        String fileName = "najkraciPutevi.db";
        String url = " jdbc : sqlite :" + fileName ;
        String sql = " CREATE TABLE IF NOT EXISTS edge (\n"+ 
          " id integer PRIMARY KEY ,\n"
          + " start_v integer NOT NULL ,\n" 
          + " end_v integer NOT NULL ,\n" 
          + " weight integer NOT NULL" + ");";
        try ( Connection conn = DriverManager.getConnection( url );
        Statement stmt = conn.createStatement() ) {
            if ( conn != null ) { stmt.execute( sql ) ;}
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ; 
        }
    }
    
    public static void createCompletedAlgorithmTable()
    {
        String fileName = "najkraciPutevi.db";
        String url = " jdbc : sqlite :" + fileName ;
        String sql = " CREATE TABLE IF NOT EXISTS completed_algorithm (\n"+ 
          " id integer PRIMARY KEY ,\n"
          + " graph_id integer NOT NULL ,\n" 
          + " algorithm_id integer NOT NULL ,\n" 
          + " duration text NOT NULL" + ");";
        try ( Connection conn = DriverManager.getConnection( url );
        Statement stmt = conn.createStatement() ) {
            if ( conn != null ) { stmt.execute( sql ) ;}
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ; 
        }
    }
    
    public static void insertAlgorithms()
    {
        
    }
    
    public static void insertGraph( )
    {
        
    }
    
    public static void insertEdge()
    {
        
    }
    
    public static void insertCompletedAlgorithm()
    {
        
    }
    
}
