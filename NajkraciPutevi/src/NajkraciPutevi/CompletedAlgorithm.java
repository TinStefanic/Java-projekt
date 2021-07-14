/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package najkraciputevi;

/*
 * izvršeni algoritam
 * @param name - ime algoritma
 * @param - vrijeme izvršavanja
*/


public class CompletedAlgorithm {
   
    private String name;
    private long time;
    
    public CompletedAlgorithm(String name, long time)
    {
        this.time = time;
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public long getTime()
    {
        return this.time;
    }
    
   
}
