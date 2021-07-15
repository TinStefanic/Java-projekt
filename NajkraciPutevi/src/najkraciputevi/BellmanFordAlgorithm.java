package najkraciputevi;

public class BellmanFordAlgorithm extends GraphAlgorithm {
    public BellmanFordAlgorithm(Graph g) {
        super(g);
        parent = new int[g.getN()];
    }
    
    @Override
    protected Integer runAlgorithm(int start, int end) {
        int ret = runAlgorithmNative(start, end);
        if (ret == Integer.MAX_VALUE) return null;
        else return (Integer)ret;
    }
    
    @Override
    public String getName() {
        return "BellmanFord";
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
