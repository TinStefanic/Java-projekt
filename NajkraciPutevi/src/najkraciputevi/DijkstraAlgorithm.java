package najkraciputevi;

/**
 * Dijkstrin algoritam za traženje najkraćeg puta u grafu.
 */
public class DijkstraAlgorithm extends GraphAlgorithm {
    public DijkstraAlgorithm(Graph g) {
        super(g);
        parent = new int[g.getN()];
    }
    
    @Override
    protected Integer runAlgorithm(int start, int end) {
        int ret = runAlgorithmNative(start, end);
        if (ret == -1) return null;
        else return (Integer)ret;
    }
    
    @Override
    public String getName() {
        return "Dijkstra";
    }
    
    @Override
    public ShortestPath getShortestPath() {
        ShortestPath ret = new ShortestPath(lStart, lEnd);
        if (getLastTime() == -1 || parent[lEnd] == -1) return null;
        else {
            int u = lEnd;
            while (u != lStart) {
                ret.addEdgeFront(parent[u], u, g.getWeightBetween(parent[u], u));
                u = parent[u];
            }
            return ret;
        }
    }
    
    static {
        System.loadLibrary("libNajkraciPutevi");
    }
    
    private native int runAlgorithmNative(int start, int end);
}
