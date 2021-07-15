/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package najkraciputevi;

import java.util.ArrayList;

/**
 *
 * @param start - početni vrh
 * @param end- završni vrh
 * @param distance - duljina puta
 * @param isComplete - je li put dovršen
 * @param edges - bridovi na putu
 */
public class ShortestPath {
    
    private int start;
    private int end;
    private int distance = 0;
    private boolean isComplete = false;
    
    private ArrayList<Edge> edges;
    
    public ShortestPath(int s, int e)
    {
        start = s;
        end = e;
    }
    
    public int getStart()
    {
        return start;
    }
    
    public void addEdge(int start, int end, int weight)
    {
        if(isComplete == false){
            edges.add(new Edge(start, end, weight));
            if(edges.get(edges.size()-1).getEnd() == this.end)
            {
                isComplete = true;
                calculateDistance();
            }
                
        }
    }
    
    public void addEdgeFront(int start, int end, int weight) {
        if (isComplete == false) {
            edges.add(0, new Edge(start, end, weight));
            if (edges.get(0).getStart() == this.start) {
                isComplete = true;
                calculateDistance();
            }
        }
    }
    
    public void calculateDistance()
    {
        for(Edge edge: edges)
            distance += edge.getWeight();
    }
    
    public int getDistance()
    {
        return distance;
    }
    
    public ArrayList<Edge> getEdges()
    {
        return edges;
    }
    
    @Override public String toString()
    {
        String ret = Integer.toString(edges.get(0).getStart());
        for(Edge edge: edges)
            ret += "-(" + Integer.toString(edge.getWeight()) + ")->" + Integer.toString(edge.getEnd());
        return ret;
    }
}
