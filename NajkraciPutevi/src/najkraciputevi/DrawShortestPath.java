/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package najkraciputevi;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.Viewer;

/**
 *
 * @param g - graf na kojem se crta najkraći put
 * @param sp - najkraći pput
 */
public class DrawShortestPath {
    
    najkraciputevi.Graph g;
    ShortestPath sp;
    
    public DrawShortestPath( najkraciputevi.Graph g, ShortestPath sp)
    {
        this.g = g;
        this.sp = sp;
    }
    
    public void draw()
    {
        System.setProperty("org.graphstream.ui", "swing");
		
	org.graphstream.graph.Graph graph = new SingleGraph("Graph");
        for(int i = 0; i < g.getN(); ++i)
           graph.addNode(Integer.toString(i)).setAttribute("label", Integer.toString(i));
        graph.nodes().forEach(n -> n.setAttribute("ui.style", "fill-color: lightblue; size: 20; text-size: 13;"));
        graph.getNode(Integer.toString(sp.getStart())).setAttribute("ui.style", "fill-color: lightgreen; size: 25; text-size: 13;");
        graph.getNode(Integer.toString(sp.getEnd())).setAttribute("ui.style", "fill-color: lightgreen; size: 25; text-size: 13;");

        for(Edge edge: sp.getEdges()){ //bridovi u najkracem putu
            String s = Integer.toString(edge.getStart());
            String e = Integer.toString(edge.getEnd());
            int w = edge.getWeight();
            graph.addEdge(s+e, s, e).setAttribute("length", w);
        }
        
        graph.edges().forEach(e -> e.setAttribute("label", "" + (int) e.getNumber("length")));
        graph.edges().forEach(e -> e.setAttribute("ui.style", "text-color: black; text-size: 14; fill-color: green; size: 2;"));
        
        for( int i = 0; i < g.getN(); ++i){
            for( int j = 0; j< g.getN(); ++j){
                var w = g.getWeightBetween(i,j);
                if( w != null){
                    String is = Integer.toString(i);
                    String js = Integer.toString(j);
                    Node n = graph.getNode(is);
                    if( !n.hasEdgeToward(js)){
                        graph.addEdge(is+js, is, js).setAttribute("length", w);
                        org.graphstream.graph.Edge e = graph.getEdge(is+js);
                        e.setAttribute("label", "" + (int) e.getNumber("length"));
                        e.setAttribute("ui.style","text-color:darkred; text-size: 14; size:1.5; fill-color:darkgrey;" );
                    }
                }
            }
        }
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    }
}
