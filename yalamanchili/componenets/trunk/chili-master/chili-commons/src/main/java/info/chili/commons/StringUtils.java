/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import java.util.HashSet;

/**
 *
 * @author ayalamanchili
 */
public class StringUtils {

    public static String getStringCamelCase(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    /**
     * return value between 0 and 1.0 based on the strings similarity
     * http://www.cs.rit.edu/~vvs1100/Code/
     *
     * @param similar1
     * @param similar2
     * @return
     */
    public static double jaccardSimilarity(String similar1, String similar2) {
        HashSet<String> h1 = new HashSet<String>();
        HashSet<String> h2 = new HashSet<String>();

        for (String s : similar1.split("\\s+")) {
            h1.add(s);
        }
        System.out.println("h1 " + h1);
        for (String s : similar2.split("\\s+")) {
            h2.add(s);
        }
        System.out.println("h2 " + h2);

        int sizeh1 = h1.size();
        //Retains all elements in h3 that are contained in h2 ie intersection
        h1.retainAll(h2);
        //h1 now contains the intersection of h1 and h2
        System.out.println("Intersection " + h1);

        h2.removeAll(h1);
        //h2 now contains unique elements
        System.out.println("Unique in h2 " + h2);

        //Union 
        int union = sizeh1 + h2.size();
        int intersection = h1.size();

        return (double) intersection / union;
    }

    public static int stringSimilarity(String similar1, String similar2) {
        int S[][] = new int[similar1.length() + 1][similar2.length() + 1];

        for (int i = 0; i < similar1.length() + 1; i++) {
            S[i][0] = 0;
        }
        for (int j = 0; j < similar2.length() + 1; j++) {
            S[0][j] = 0;
        }

        for (int i = 1; i < similar1.length() + 1; i++) {
            for (int j = 1; j < similar2.length() + 1; j++) {
                if (similar1.charAt(i - 1) == similar2.charAt(j - 1)) {
                    S[i][j] = S[i - 1][j - 1] + 1;
                } else {
                    S[i][j] = Math.max(S[i - 1][j], S[i][j - 1]);
                }
            }
        }
        return S[similar1.length()][similar2.length()];
    }

    public static void main(String args[]) {
        System.out.println(stringSimilarity("NYSC", "New york stock exchange"));

    }
}
