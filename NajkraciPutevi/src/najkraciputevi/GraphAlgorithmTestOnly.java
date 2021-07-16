package najkraciputevi;

import java.util.Random;

/**
 * Nenativan "algoritam" samo za testiranje.
 */
public class GraphAlgorithmTestOnly extends GraphAlgorithm{
    public GraphAlgorithmTestOnly(Graph g) {
        super(g);
    }
    
    @Override
    protected Integer runAlgorithm(int start, int end) {
        try {
            Thread.sleep((new Random()).nextInt(1000));
        } catch (Exception e) {};
        return (Integer)(new Random()).nextInt(100);
    }
    
    @Override
    public String getName() {
        return "TestOnly";
    }
    
    @Override
    public ShortestPath getShortestPath() {
        ShortestPath ret = new ShortestPath(lStart, lEnd);
        ret.addEdge(lStart, lEnd, (new Random()).nextInt(100));
        return ret;
    }
}
