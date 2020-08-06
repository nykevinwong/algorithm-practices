import java.util.Map;
import java.util.HashMap;

class Window<E>
{
    Map<E,Integer> m = new HashMap<>();
    int kinds = 0; // the total number of types available in the map
    
    public Window() {}
    
    public void add(E x)
    {
        m.put(x, m.getOrDefault(x,0)+1);
        if(m.get(x)==1) kinds++;
    }
    
    public void remove(E x)
    {
        m.put(x, m.get(x)-1);
        if(m.get(x)==0) kinds--;
    }
    
    public int kinds() { return kinds; }
}