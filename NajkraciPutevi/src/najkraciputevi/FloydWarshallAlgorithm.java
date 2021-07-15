package najkraciputevi;

public class FloydWarshallAlgorithm extends GraphAlgorithm {
    public FloydWarshallAlgorithm(Graph g) {
        super(g);
        minDist = new int[g.getN()];
    }
    
    @Override
    protected Integer runAlgorithm(int start, int end) {
        int ret = runAlgorithmNative(start, end);
        if (ret == Integer.MAX_VALUE) return null;
        else return (Integer)ret;
    }
    
    @Override
    public String getName() {
        return "FloydWarshall";
    }
    
    @Override
    public ShortestPath getShortestPath() {
        if (getLastTime() == -1 || minDist[lEnd] == Integer.MAX_VALUE) return null;
        else {
            ShortestPath ret = new ShortestPath(lStart, lEnd);
            int v = lEnd;
            while (v != lStart) {
                for (int u = 0; u < g.getN(); ++u) {
                    if (minDist[u] == Integer.MAX_VALUE || g.getWeightBetween(u, v) == null) continue;
                    if (g.getWeightBetween(u, v) + minDist[u] == minDist[v]) {
                        ret.addEdgeFront(u, v, g.getWeightBetween(u, v));
                        v = u;
                        break;
                    }
                }
            }
            return ret;
        }
    }
    
    static {
        System.loadLibrary("libNajkraciPutevi");
    }
    
    private native int runAlgorithmNative(int start, int end);
}
