public class BinarySearch implements IInterviewQuestion, IImportTechnique 
{

    public int binarySearch(int[] arr, int key, boolean lowerBound)
    {
        int left = 0;
        int right = arr.length - 1;
        int lastKeyPos = -1;

        while(left <= right)
        {
            int mid = (left+right) / 2;
            int cur  = arr[mid];

            if(key > cur) left = mid+1;
            else if(key < cur) right = mid-1;
            else // key == cur
            {
                lastKeyPos = mid;

                if(lowerBound) right = mid-1;
                else left = mid+1;
            }
        
        }

        return (lastKeyPos==-1) ? -(left+1) : lastKeyPos;
    }

    public void performTest()
    {
        int[] nums = new int[] { 1,2,2,2,2,3,3,3,4,4,6,6,6,7,8,9,10};
        

        System.out.print("Array: ");
        for(int i=0;i < nums.length; i++) System.out.print(String.format("[%2s]", nums[i]));
        System.out.println();

        System.out.print("Pos:   ");
        for(int i=0;i < nums.length; i++) System.out.print(String.format("{%2s}", i));
        System.out.println();

        System.out.println("\nLowerBound BinarySearch");
        for(int i=0;i < 12; i++)
        {
            int insertPosition =  binarySearch(nums, i, true);
            System.out.println("key = " + i + " => " + insertPosition);
        }

        System.out.println("\nUpperBound BinarySearch");
        for(int i=0;i < 12; i++)
        {
            int insertPosition =  binarySearch(nums, i, false);
            System.out.println("key = " + i + " => " + insertPosition);
        }

        System.out.println("Positive insert position = the actual found key position.");
        System.out.println("Negative insert postion (-insertPos -1) = the position of the least integer greater than key ");
    }

    public String toString() { 
        return "Binary Search []";
    }
}
