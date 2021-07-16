/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package najkraciputevi;

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
 * algorithm - name, can_weights_be_negative //mozda netreba
 * completed_algorithm - id, graph_id, algorithm_name, duration, result
 * shortest_path_edge - id, graph_id, alg_name, start, end, weight, order - pamti bridove za najkraci put u grafu po algoritmu
*/

public final class Database {
    
    String databaseName = "najkraciPutevi.db";
    String url = "jdbc:sqlite:" + databaseName ;
    Connection conn;
    
    /* konstruktor stvara bazu i tablice (ako ne postoje) te
     * popuni tablicu algorithm
    */
    public Database()
    {
        try {
            this.conn = DriverManager.getConnection( url );
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        try  {
            if ( conn != null ) {
                DatabaseMetaData meta = conn.getMetaData () ;
                System.out.println("Ime biblioteke za rad s bazom podataka " + meta.getDriverName() ) ;
                System.out.println(" Stvorena je nova baza.") ;
            }
        } 
       catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ;
        }
        
        createTables();
    }
    
    public void createTables()
    {
        createGraphTable();

        createEdgeTable();
        createCompletedAlgorithmTable();
        createShortestPathEdgeTable();
        
 
    }
    
    public void createGraphTable()
    {
        
        String sql = "CREATE TABLE IF NOT EXISTS graph (\n"+ 
          " graph_id integer PRIMARY KEY,\n"
          + " num_of_v integer NOT NULL\n" + ");";
        
        try 
         {
            Connection conn = DriverManager.getConnection( url );
            if ( conn != null ) { 
                Statement stmt = conn.createStatement();
                stmt.execute( sql ) ;}
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ; 
        }
    }
    
    /*public void createAlgorithmTable() 
    {
        String sql = " CREATE TABLE IF NOT EXISTS algorithm (\n"+ 
          " name text PRIMARY KEY ,\n" 
          + "neg_weights integer NOT NULL" + ");";
        try ( Connection conn = DriverManager.getConnection( url );
        Statement stmt = conn.createStatement() ) {
            if ( conn != null ) { stmt.execute ( sql ) ;}
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ; 
        }
    }*/
    
    public void createEdgeTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS edge (\n"+ 
          " id integer PRIMARY KEY,\n"
          + " graph_id integer NOT NULL,\n"
          + " start integer NOT NULL,\n" 
          + " end integer NOT NULL,\n" 
          + " weight integer NOT NULL\n" + ");";
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
        String sql = "CREATE TABLE IF NOT EXISTS completed_algorithm (\n"+ 
          " id integer PRIMARY KEY,\n"
          + " graph_id integer NOT NULL,\n" 
          + " alg_name text NOT NULL,\n"
          + " start int NOT NULL,\n"
          + " end int NOT NULL,\n" 
          + " duration int NOT NULL,\n"
          + " result int NOT NULL\n);";
        try ( 
            Connection conn = DriverManager.getConnection( url );
             Statement stmt = conn.createStatement() ) {
             if ( conn != null ) { stmt.execute( sql ) ;}
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ; 
        }
    }
    
    public void createShortestPathEdgeTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS shortest_path_edge (\n"+ 
          " id integer PRIMARY KEY,\n"
          + " graph_id integer NOT NULL,\n" 
          + " alg_name text NOT NULL,\n" 
          + " start int NOT NULL,\n"
          + " end int NOT NULL,\n"
          + " weight int NOT NULL,\n"
          + " pos int NOT NULL\n);";
        try ( 
             Connection conn = DriverManager.getConnection( url );
             Statement stmt = conn.createStatement() ) {
             if ( conn != null ) { stmt.execute( sql ) ;}
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage() ) ; 
        }
    }
    
    
    public int insertGraph( Graph G ) //vraca id grafa
    {
        int id = rowCount("graph") + 1; // odredi id
        String sql = "INSERT INTO graph(graph_id,num_of_v) "
                + "VALUES(?,?)" ;
        try {
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            pstmt.setInt(1, id);
            pstmt.setInt(2, G.getN());
            pstmt.executeUpdate ();
             }
        catch ( SQLException e ) { System.out.println( e.getMessage() ); }
       
        for( int i = 0; i < G.getN(); ++i ){
            for( int j = 0; j <  G.getN(); ++j){
                if(G.getWeightBetween(i, j)!= null  )
                    insertEdge(id, i, j, G.getWeightBetween(i, j));
            }
        }
        
        return id;
    }
    
    public void insertEdge( int graph_id, int start, int end, int weight )
    {
        int id = rowCount("edge") + 1; // odrediti id (row count + 1)
        String sql = "INSERT INTO edge(id,graph_id,start,end,weight) "
                + "VALUES(?,?,?,?)" ;
        
        try {
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
    
    public void insertCompletedAlgorithm( int graph_id, String alg_name, long time, int result, int start, int end)
    {
        int id = rowCount("completed_algorithm") + 1; // odrediti id (row count + 1)
        String sql = "INSERT INTO completed_algorithm(id,graph_id,alg_name,start,end,duration,result) "
                + "VALUES(?,?,?,?,?,?)" ;
        
        try {
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            
            pstmt.setInt(1 , id );
            pstmt.setInt(2 , graph_id );
            pstmt.setString(3 , alg_name );
            pstmt.setInt(4 , start );
            pstmt.setInt(5 , end );
            pstmt.setLong(6 , time );
            pstmt.setInt(7,  result );
            pstmt.executeUpdate ();
             }
        catch ( SQLException e ) { }
    }
    
    public void insertShortestPath( ShortestPath sp, int graph_id, String alg_name) //modificiraj
    {   
        int i = 1;
        for(Edge edge: sp.getEdges()){
            insertShortestPathEdge(edge, i, graph_id, alg_name);
            ++i;
        }
    }
    
    public void insertShortestPathEdge(Edge edge, int pos, int graph_id, String alg_name) //modificiraj
    {
        int id = rowCount("shortest_path_edge") + 1;
        String sql = "INSERT INTO shortest_path_edge(id, graph_id,alg_name,start,end,weight,pos)"
                + "VALUES(?,?,?,?,?,?,?)" ;
        try {
            PreparedStatement pstmt = conn.prepareStatement ( sql );
            
            pstmt.setInt(1 , id );
            pstmt.setInt(2 , graph_id );
            pstmt.setString(3 , alg_name );
            pstmt.setInt(4 , edge.getStart() );
            pstmt.setInt(5 , edge.getEnd() );
            pstmt.setInt(6, edge.getWeight());
            pstmt.setInt(7, pos);
            pstmt.executeUpdate ();
             }
        catch ( SQLException e ) { }
        
    }
    
    public Graph selectGraphById( int id )
    {
        String sql = "SELECT num_of_v FROM graph "
                + "WHERE graph_id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, id );
            
            ResultSet rs = pstmt.executeQuery( );
            rs.next();
            Graph g = new Graph(rs.getInt("num_of_v"));
            ArrayList<Edge> edges = selectEdgesByGraphId(id);
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
        String sql = "SELECT start, end, weight FROM edge "
                + "WHERE graph_id = ?";
        ArrayList<Edge> edges = new ArrayList<Edge>();
        try {
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, id );
            ResultSet rs = pstmt.executeQuery( );
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
        String sql = "SELECT graph_id, alg_name, duration, result FROM completed_algorithm "
                + "WHERE graph_id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, id );
            
            ResultSet rs = pstmt.executeQuery(  );
            while ( rs.next() ) {
               String name = rs.getString("alg_name");
               long time = rs.getLong("duration");
               int result = rs.getInt("result");
               int start = rs.getInt("start");
               int end = rs.getInt("end");
               alg.add(new CompletedAlgorithm(name, time, result, start, end));
            }
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
        
        return alg;
    }
    
    public CompletedAlgorithm selectCompletedAlgorithmByGraphIdandAlgName( int id, String alg_name)
    {
        CompletedAlgorithm alg = new CompletedAlgorithm();
        String sql = "SELECT graph_id, alg_name, duration, result FROM completed_algorithm "
                + "WHERE graph_id = ? AND alg_name = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, id );
            pstmt.setString( 2, alg_name );
            
            ResultSet rs = pstmt.executeQuery(  );
            if(rs.next()){
               String name = rs.getString("alg_name");
               long time = rs.getLong("duration");
               int result = rs.getInt("result");
               int start = rs.getInt("start");
               int end = rs.getInt("end");
               alg = new CompletedAlgorithm(name, time, result, start, end);
        }
            
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
           
        }
        return alg;
    }
    
    public ShortestPath selectShortestPathByGraphAndAlg(int graph_id, String alg_name)
    {
        String sql = "SELECT * FROM shortest_path_edge "
                + "WHERE graph_id = ? AND alg_name = ? ORDER BY pos ASC";
        ArrayList<Edge> edges = new ArrayList<Edge>();
        
        try {
            PreparedStatement pstmt = conn.prepareStatement( sql );
            pstmt.setInt( 1, graph_id );
            pstmt.setString(2, alg_name);
            
            ResultSet rs = pstmt.executeQuery( );
            while ( rs.next() ) {
               int start = rs.getInt("start");
               int end = rs.getInt("end");
               int weight = rs.getInt("weight");
               edges.add(new Edge(start, end, weight));
            }
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
        
        ShortestPath sp = new ShortestPath(edges.get(0).getStart(), edges.get(edges.size()-1).getEnd());
        for(Edge edge: edges)
            sp.addEdge(edge.getStart(), edge.getEnd(), edge.getWeight());
        return sp;
        
    }
    
    public ArrayList selectGraphIds()
    {
        ArrayList ids = new ArrayList();
        String sql = "SELECT graph_id FROM graph "
                + "ORDER BY graph_id ASC";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement( sql );
            
            ResultSet rs = pstmt.executeQuery( );
            while ( rs.next() ) {
               ids.add(rs.getInt("graph_id"));
            }
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
        
        return ids;
        
    }
   
    
    public int rowCount( String column)
    {
        String sql = "SELECT count(*) FROM " + column + ";";
        int count = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement( sql );
            
            ResultSet rs = pstmt.executeQuery( );
            rs.next();
            count = rs.getInt(1);
            
        } 
        catch ( SQLException e ) {
            System.out.println( e.getMessage () ) ;
        }
        return count;
    }
    
}
