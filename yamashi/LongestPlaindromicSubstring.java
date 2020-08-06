class LongestPlaindromicSubstring implements IInterviewQuestion {

    public String longestPalindrome(String s) {
        if(s==null || s.length() < 2) return s;
        
        int left = 0, right = 0;
        int len = s.length();
        boolean[][] isPalindrome = new boolean[len][len];        
        // P(i,j) answers the question 'is the substring from index i to index j  is a paindrome?'.
        
        // j starts from 1. must compare at least two chars. one char from j, another char from i.
        
        for(int j=1; j < s.length();j++) 
            for(int i=0; i < j ; i++)
            {
                //j-i <=2 means current length doesnt have an inner string to check.
                boolean isInnerPalindrome = isPalindrome[i+1][j-1] || j-i <=2;
                
                if(isInnerPalindrome && s.charAt(i)==s.charAt(j) )
                {
                     isPalindrome[i][j] = true;
                    if(j-i > right-left) // update current max length
                    {
                        right = j;
                        left = i;
                    }
                }
            }
        return s.substring(left,right+1);
    }

    public String longestPalindrome_ExpandFromCenter(String s) {
        String max = "";
        
        for(int i=0;i< s.length();i++)
        {
            String s1 = expandFromCenter(s, i, i);
            String s2 = expandFromCenter(s, i, i+1);
            String curMax = (s1.length() > s2.length()) ? s1:s2;
            max = (curMax.length() > max.length()) ? curMax:max;            
        }
        
        return max;
    }
    
    public String expandFromCenter(String s, int l, int r)
    {
        while(l>=0 && r < s.length() && s.charAt(l) == s.charAt(r))
        { 
            l--; r++; 
        }
        
        int len = ((r-1)-(l+1));        
        return s.substring(l+1, r);
    }

    public void performTest()
    {
        Helper.equals(longestPalindrome("babad"),  "bab", " longest plaindrome ?" );
        Helper.equals(longestPalindrome("cbbd"),  "bab", " longest plaindrome ?" );

        Helper.equals(longestPalindrome_ExpandFromCenter("babad"),  "bab", "(EXPAND from Center) longest plaindrome ?" );
        Helper.equals(longestPalindrome_ExpandFromCenter("cbbd"),  "bab", "(EXPAND from Center)  longest plaindrome ?" );
    }

    public String toString() { 
        return "Longest Palindromic Substring ([I]**) [https://leetcode.com/problems/longest-palindromic-substring/]";
    }
}