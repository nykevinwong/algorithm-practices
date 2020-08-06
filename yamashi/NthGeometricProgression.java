class NthGeometricProgression implements IInterviewQuestion  {

    public char[] getNthGP(double secondTerm, double thirdTerm, int nth)
    {
        double r = thirdTerm/ secondTerm;
        double a = secondTerm/r;
        double result = a*Math.pow(r, nth-1);
        String str = String.valueOf(result);
        int pos = str.indexOf('.');

        if(pos > 0) // trim up to 
        {
           int decimalPlaces = 3;
           str = str.substring(0, Math.min(pos+decimalPlaces+1, str.length())); 
        }

    //    System.out.println("res => [" + str + "]");
        return str.toCharArray();
    }

    public void performTest()
    {
        Helper.equals(String.valueOf( getNthGP(1,2,4) ), "4.0", "seoncd Term:1, thrid Term: 2, Find 4th Term = ? ");
    }

    public String toString() { 
        return "Nth Geometric Progression ([I]*) [https://leetcode.com/discuss/interview-question/432213/]";
    }
}