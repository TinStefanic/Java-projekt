/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package najkraciputevi;


import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
/**
 * crtanje zadanog grafa (koristeći biblioteku graphstream)
 * @param g - graf koji se crta
 */
public class DrawGraph {
    
    najkraciputevi.Graph g;
    
    public DrawGraph(najkraciputevi.Graph g)
    {
        this.g = g;
    }
    public void draw() {
		System.setProperty("org.graphstream.ui", "swing");
		
		org.graphstream.graph.Graph graph = new SingleGraph("Graph");
                for(int i = 0; i < g.getN(); ++i)
                    graph.addNode(Integer.toString(i)).setAttribute("label", Integer.toString(i));
		for(int i = 0; i < g.getN(); ++i){
                    for( int j = i; j< g.getN(); ++j){
                        var w = g.getWeightBetween(i,j);
                        if( w != null){
                            String is = Integer.toString(i);
                            String js = Integer.toString(j);
                            graph.addEdge(is+js, is, js).setAttribute("length", w);
                        }
                    }
                }
                graph.nodes().forEach(n -> n.setAttribute("ui.style", "fill-color: lightblue; size: 20; text-size: 13;"));
                graph.edges().forEach(e -> e.setAttribute("label", "" + (int) e.getNumber("length")));
                graph.edges().forEach(e -> e.setAttribute("ui.style", "text-color: darkred; text-size: 13;"));
                
		graph.display();
	}
}