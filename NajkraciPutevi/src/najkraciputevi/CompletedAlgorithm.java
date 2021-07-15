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
    private double time;
    private int result;
    
    public CompletedAlgorithm(String name, double time, int result)
    {
        this.time = time;
        this.name = name;
        this.result = result;
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
    
}
