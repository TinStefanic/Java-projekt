/*
 *dretva koja izvodi algoritam
 */
package najkraciputevi;

/**
 *
 * @author helena
 */
public class AlgorithmThread implements Runnable{
   
    int graph_id;
    Database db;
    GraphAlgorithm alg;
    int start;
    int end;
    
    public AlgorithmThread( GraphAlgorithm Alg, Database d, int id, int s, int e)
    { 
        alg = Alg;
        graph_id = id;
        db = d;
        start = s;
        end = e;
    }
    
    @Override
    public void run()
    {
        int result = alg.query(start, end);
        double time = alg.getLastTime();
        db.insertCompletedAlgorithm( graph_id, alg.getName(), time, result );
        
    }
}
