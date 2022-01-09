import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

ublic class LCS {
    /**Note:
     * 1.we have two versions about our first homework, the first version is we read
     * two large files and then do callLCS(str1, str2), which will return int, and callLcsString(str1, str2), which will return String,
     * because a function can only return one data type, that's the reason why we have readFile1() and readFile2() to
     * compute callLCS() and callLcsString(), respectively.
     * 2. if we do not need to write the code about read Files, we also have the second version, you only need to test
     * callLCS(String str1, String str2) and callLcsString(String str1, String str2) and you will get the results*/
    //read two large file and get two string temp1 and temp2 then perform LCS algorithm
    public int readFile1(String path1, String path2){
        File file1 = new File(path1);
        String temp1 = "";

        try {
            FileInputStream fis = new FileInputStream(file1);
            int buf = 0;
            while((buf = fis.read()) != - 1){
                temp1 += (char)buf;
            }
            fis.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        File file2 = new File(path2);
        String temp2 = "";
        try {
            FileInputStream fis = new FileInputStream(file2);
            int buf = 0;
            while((buf = fis.read()) != - 1){
                temp2 += (char)buf;
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return callLCS(temp1, temp2);

    }

    public String readFile2(String path1, String path2){
        File file1 = new File(path1);
        String temp1 = "";

        try {
            FileInputStream fis = new FileInputStream(file1);
            int buf = 0;
            while((buf = fis.read()) != - 1){
                temp1 += (char)buf;
            }
            fis.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        File file2 = new File(path2);
        String temp2 = "";
        try {
            FileInputStream fis = new FileInputStream(file2);
            int buf = 0;
            while((buf = fis.read()) != - 1){
                temp2 += (char)buf;
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return callLcsString(temp1, temp2);

    }
    /**part 1 printing out the number of bytes the two files have in common*/
    public int callLCS(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] memo = new int[m][n];
        for(int i = 0; i < m; i ++)
            Arrays.fill(memo[i], - 1);
        return callMaxSub(str1, str2, 0, 0, memo);
    }
    private int callMaxSub(String str1, String str2, int l, int r, int[][] memo){
        if(l >= str1.length() || r >= str2.length())
            return 0;
        if(memo[l][r] != - 1)
            return memo[l][r];
        int res = 0;
        if(str1.charAt(l) == str2.charAt(r)){
            res += 1 + callMaxSub(str1, str2, l + 1, r + 1, memo);
            return memo[l][r] = res;
        }
        res = Math.max(callMaxSub(str1, str2, l + 1, r, memo), callMaxSub(str1, str2, l, r + 1, memo));
        return memo[l][r] = res;
    }

    /**part 2 for extra credit, printing all the letters in common (the longest common sub-sequence).*/
    public String callLcsString(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        char[] l = str1.toCharArray();
        char[] r = str2.toCharArray();
        int[][] dp = new int[m + 1][n + 1];

        for(int i = 1; i < m + 1; i ++){
            for(int j = 1; j < n + 1; j ++){
                if(l[i - 1] == r[j - 1])
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return generateString(dp, l, r);
    }
    private String generateString(int[][] dp, char[] str1, char[] str2){
        StringBuilder res = new StringBuilder();
        int cur = 1;
        for(int i = 0; i < str1.length + 1; i ++){
            for(int j = 0; j < str2.length + 1; j ++){
                if(dp[i][j] == cur){
                    res.append(str2[j - 1]);
                    cur ++;
                    break;
                }
            }
        }
        return res.toString();
    }
    public static void main(String[] args){
        String path1 = "str1.txt";
        String path2 = "str2.txt";
        System.out.println(new LCS().readFile1(path1, path2));
        System.out.println(new LCS().readFile2(path1, path2));


        String str1 = "thbvbvis inbnbs a tmnmnest";
        String str2 = "thopopis iqzqzqzs a wswstesrtrtt hjhj";
        System.out.println(new LCS().callLCS(str1, str2));
        System.out.println(new LCS().callLcsString(str1, str2));

        String str3 = "abcde";
        String str4 = "ace";
        System.out.println(new LCS().callLCS(str3, str4));
        System.out.println(new LCS().callLcsString(str3, str4));

        String str5 = "mhunuzqrkzsnidwbunkil";
        String str6 = "szulspmhwpazoxijwbqkmjil";
        System.out.println(new LCS().callLCS(str5, str6));
        System.out.println(new LCS().callLcsString(str5, str6));

        String str7 = "mzc";
        String str8 = "lkinu";
        System.out.println(new LCS().callLCS(str7, str8));
        System.out.println(new LCS().callLcsString(str7, str8));

        String str9 = "q1rlythwxwxiswxw iwxwsw awxw wtewxstwxwx";
        String str10 = "mnbvcthvbis ivs avb bng tevbvbst";
        System.out.println(new LCS().callLCS(str9, str10));
        System.out.println(new LCS().callLcsString(str9, str10));
    }
}
