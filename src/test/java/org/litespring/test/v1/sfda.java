package org.litespring.test.v1;

class Solution {
    public String longestCommonPrefix(String[] strs) {
        StringBuffer commonPrefix = new StringBuffer();

        int minLength = strs[0].length();
        for (int i = 1; i < strs.length; i++){
            int currentStringLength = strs[i].length();
            if (minLength > currentStringLength)
                minLength = currentStringLength;
        }


        for (int i = 0; i < minLength; i++){
            for (int j = 0; j < strs.length - 1; j++){
                char pre = strs[j].charAt(i);
                char next = strs[j + 1].charAt(i);
                if (pre != next)
                    return commonPrefix.toString();
            }
            commonPrefix.append(strs[0].charAt(i));
        }
        return commonPrefix.toString();
    }

    public static void main(String[] args) {
        new Solution().longestCommonPrefix(new String[]{"flower","flow","flight"});
    }
}
