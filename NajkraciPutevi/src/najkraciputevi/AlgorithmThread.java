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
    javax.swing.JTextArea textbox;
    long extime;
    int exresult;
    boolean pathExists;
    
    
    public AlgorithmThread( GraphAlgorithm Alg, Database d, int id, int s, int e, javax.swing.JTextArea tb)
    { 
        alg = Alg;
        graph_id = id;
        db = d;
        start = s;
        end = e;
        textbox=tb;
    }
    
    @Override protected Integer doInBackground () throws Exception 
    {
        if (alg.query(start, end) != null) {
            exresult = alg.getLastResult();
            extime = alg.getLastTime();
            db.insertCompletedAlgorithm( graph_id, alg.getName(), extime, exresult, start, end );
            db.insertShortestPath(alg.getShortestPath(), graph_id, alg.getName());
            pathExists = true;
            return exresult;
        } else {
            extime = alg.getLastTime();
            pathExists = false;
            return null;
        }
    }
    
    protected void done()
    {
        try {
            Integer res = get();
            if (pathExists)
                textbox.setText(String.valueOf(extime)+" ns\r\nCijena najkraćeg puta je: "+String.valueOf(exresult));
            else
                textbox.setText(String.valueOf(extime)+" ns\r\nNajkraći put ne postoji.");
        } catch (InterruptedException ex) {
            Logger.getLogger(AlgorithmThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(AlgorithmThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
