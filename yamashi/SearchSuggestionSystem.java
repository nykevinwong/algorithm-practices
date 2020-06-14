
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;


public class SearchSuggestionSystem implements IInterviewQuestion {
    // System Design interview for auto suggestions: https://www.youtube.com/watch?v=xrYTjaK5QVM
    // https://leetcode.com/problems/search-suggestions-system
     class Trie {
     Trie[] sub = new Trie[26];
     List<String> suggestion = new LinkedList<>();
     }
 
     // autocomplete/typehead
     // suggest 3 items based on a searchWord currently typed.
     public List<List<String>> suggestedProducts(String[] products, String searchWord) {
         Arrays.sort(products);
         
         List<List<String>> res = new ArrayList<>();
         Trie root = new Trie();
         for(String p: products) // pre-compute suggest list based on sorted order
         {
             Trie r = root;
             for(char c : p.toCharArray())
             {
                 if(r.sub[c-'a']==null) r.sub[c-'a'] = new Trie();
 
                 r = r.sub[c-'a'];
                 
                 if(r.suggestion.size() < 3) r.suggestion.add(p);
             }
         }
         
         for(char c: searchWord.toCharArray())
         {
             if(root!=null) root = root.sub[c-'a'];
             res.add( (root==null) ? Arrays.asList() : root.suggestion);
         }
         
         return res;
     }
 
     public void performTest()
     {
         String[] products = new String[]{"mobile","mouse","moneypot","monitor","mousepad"};
         String searchWord = "mouse";
         Helper.equals(suggestedProducts(products,searchWord), 
         new String[][] {
             {"mobile","moneypot","monitor"},
             {"mobile","moneypot","monitor"},
             {"mouse","mousepad"},
             {"mouse","mousepad"},
             {"mouse","mousepad"}
         }, "AutoComplete/TypeAhead Suggestion: ");
     }
 
     public String toString() { return "Product Suggestions ([E]**):";}
 }