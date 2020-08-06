import java.util.List;
import java.util.ArrayList;

class GenerateParentheses implements IInterviewQuestion {
    
    public List<String> generateParentheses(int n) {
        List<String> res = new ArrayList<>();
        dfs("", n, n, res);   
        return res;
    }
    
    public void dfs(String s, int left, int right, List<String> res)
    {
        if(left > right) return;
        if(left <0 || right < 0) return;
        if(left==0 && right==0) res.add(s);
        
        dfs(s+"(", left-1, right, res);
        dfs(s+")", left, right-1, res);
    }

    public void performTest()
    {
        Helper.equals( generateParentheses(3), new String[] {"((()))","(()())","(())()","()(())","()()()"} , "Valid parentheses generation? ");
    }

   
    public String toString() { 
        return "Generate Parentheses ([I] *) [https://leetcode.com/problems/generate-parentheses/]";
    }
}