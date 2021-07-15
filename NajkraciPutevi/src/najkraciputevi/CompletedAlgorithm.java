/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package najkraciputevi;

/*
 * izvršeni algoritam
 * @param name - ime algoritma
 * @param time - vrijeme izvršavanja
   @param result - duljina najkraćeg puta koji je alg našao
*/

public class CompletedAlgorithm {
    
    private String name;
    private long time;
    private int result;
    private int start;
    private int end;
    
    public CompletedAlgorithm(String name, long time, int result, int start, int end)
    {
        this.time = time;
        this.name = name;
        this.result = result;
        this.start = start;
        this.end = end;
    }
    
    public CompletedAlgorithm()
    {
        name = "";
        time = -1;
        result = -1;
        start = -1;
        end = -1;
    }
    
    public String getName()
    {
        return name;
    }
    
    public double getTime()
    {
        return time;
    }
    
    public int getResult()
    {
        return result;
    }
    
    public int getStart()
    {
        return start;
    }
    
    public int getEnd()
    {
        return end;
    }
    
}
