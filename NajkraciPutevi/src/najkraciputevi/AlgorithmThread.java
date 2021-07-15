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
        exresult = alg.query(start, end);
        extime = alg.getLastTime();
        db.insertCompletedAlgorithm( graph_id, alg.getName(), extime, exresult, start, end );
        return exresult;
    }
    
    protected void done()
    {
        try {
            int res = get();
            System.out.println(extime);
            System.out.println(exresult);
            textbox.append(String.valueOf(extime)+"\r\n"+String.valueOf(exresult));
        } catch (InterruptedException ex) {
            Logger.getLogger(AlgorithmThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(AlgorithmThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
