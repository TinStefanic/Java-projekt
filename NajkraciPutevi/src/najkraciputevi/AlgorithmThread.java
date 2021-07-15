/*
 *dretva koja izvodi algoritam
 */
package najkraciputevi;
import javax.swing.SwingWorker;

/**
 *
 * @author helena
 */
public class AlgorithmThread extends SwingWorker<Integer, Void>{
   
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
    
    @Override protected Integer doInBackground () throws Exception 
    {
        int result = alg.query(start, end);
        long time = alg.getLastTime();
        db.insertCompletedAlgorithm( graph_id, alg.getName(), time, result );
        return result;
    }
    
    protected void done()
    {
        
    }
    
    @Override protected void process()
    {
        
    }
}
