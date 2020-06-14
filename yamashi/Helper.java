
import java.util.List;
import java.util.Arrays;

interface IInterviewQuestion
{
    public void performTest();
}

interface IImportTechnique
{
    public void performTest();
}

public class Helper
{
    public static <T>  void equals(List<T> left, T[] right, String msg)
    {       
        if(msg!=null) System.out.println(msg + " =  " + Arrays.toString(right) + " => actual: " + Arrays.toString(left.toArray()) +" => " + left.equals(Arrays.asList(right)) );
    }

    public static void equals(int[] left, int[] right, String msg)
    {
        if(msg!=null) System.out.println(msg + " =  " + right + " => actual: " + Arrays.toString(left) + " => " + Arrays.equals(left, right) );
    }

    public static <T> void equals(T[] left, T[] right, String msg)
    {
        if(msg!=null) System.out.println(msg + " =  " + right + " => actual: " + Arrays.toString(left) + " => " + Arrays.equals(left, right) );
    }

    public static <T> void equals(T left, T right, String msg)
    {
        if(msg!=null) System.out.println(msg + "... =  " + right + " => " + (left.equals(right)) );
    }


    public static <T> void equals(List<List<T>> left, T[][] right, String msg)
    {
        if(msg!=null) System.out.println(msg + " =  " + right + " => " + equalsTo(left,right) );
    }


    public static <T> boolean equalsTo(List<List<T>> left, T[][] right)
    {
        if(left.size() != right.length) return false;
        System.out.println("\n * compare list and array: ");

        for(int i=0;i<left.size();i++)
        {
            List<T> l2 = left.get(i);
            T[] r2 = right[i];
            System.out.print(Arrays.toString(l2.toArray()) + " == " + Arrays.toString(r2) + " ?");
            for(int j=0;j<l2.size();j++)
            {
                T ls = l2.get(j);
                T rs = r2[j];
                if(!ls.equals(rs)) 
                {
                    System.out.println(" **FALSE**");
                    return false; 
                }
            }
            System.out.println(" true");
        }
        return true;
    }

    public static boolean arrayEquals(int[][] left, int[][] right)
    {
        if(left.length != right.length) return false;
        System.out.println("\n * compare two arrays: ");

        for(int i=0;i<left.length;i++)
        {
            int[] l2 = left[i];
            int[] r2 = right[i];
            System.out.print(Arrays.toString(l2) + " == " + Arrays.toString(r2) + " ?");
            for(int j=0;j < l2.length;j++)
            {
                int ls = l2[j];
                int rs = r2[j];
                if(ls!=rs) 
                {
                    System.out.println(" **FALSE**");
                    return false; 
                }
            }
            System.out.println(" true");
        }
        return true;
    }

}