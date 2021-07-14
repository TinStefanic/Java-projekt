/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NajkraciPutevi;

/*
 * izvršeni algoritam
 * @param name - ime algoritma
 * @param - vrijeme izvršavanja
*/


public class CompletedAlgorithm {
   
    private String name;
    private double time;
    private int result;
    
    public CompletedAlgorithm(String name, double time, int result)
    {
        this.time = time;
        this.name = name;
        this.result=result;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public double getTime()
    {
        return this.time;
    }
    
    public int getResult()
    {
        return this.result;
    }
   
}
