/*
 *dretva koja izvodi algoritam
 */
package najkraciputevi;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 * dretva za izvođenje algoritma - nakon izvođenja algoritma, sprema rezultat u bazu
 * 
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
        db.insertCompletedAlgorithm( graph_id, alg.getName(), time, result, start, end );
        return result;
    }
    
    protected void done()
    {
        try {
            int res = get();
        } catch (InterruptedException ex) {
            Logger.getLogger(AlgorithmThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(AlgorithmThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override protected void process()
    {
        
    }
}
