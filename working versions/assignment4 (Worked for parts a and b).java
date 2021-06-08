import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * assignment4
 */
public class assignment4 {

    

    /**
     * 
     * @param file
     * @param graph
     * @return
     * @throws Exception
     */
    public static HashMap<String, HashMap<String, Integer>> nodeDelimiter(String file, HashMap<String, HashMap<String, Integer>> graph) throws Exception {
        BufferedReader parse = null;
        parse = new BufferedReader(new FileReader(file));
        String input;
        int a=0;
        while( (input = parse.readLine()) != null) {
            //Remove the comma unless it is in quotation marks: Read from Stack Overflow
            String[] head = input.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            head[0] = head[0].replace("\"", "");
            head[1] = head[1].replace("\"", "");
            if (head.length!=2) {
                System.out.println(head.length);
                throw new Exception("Line should have 2 columns");
            }
            if (a++==0) continue;

            //Store in 2 different strings for better-ness?
            String Id = head[0];
            String label= head[1];

            graph.put(label, new HashMap<String, Integer>());
        }
        parse.close();

        return graph;
    }


    /**
     * 
     * @param file
     * @param graph
     * @return
     * @throws Exception
     */
    public static HashMap<String, HashMap<String, Integer>> edgeDelimiter(String file, HashMap<String, HashMap<String, Integer>> graph) throws Exception {
        BufferedReader parse = null;
        parse = new BufferedReader(new FileReader(file));
        String input;
        int a=0;
        while( (input = parse.readLine()) != null) {
            //Remove the comma unless it is in quotation marks: Read from Stack Overflow
            String[] head = input.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            head[0] = head[0].replace("\"", "");
            head[1] = head[1].replace("\"", "");
            head[2] = head[2].replace("\"", "");
            if (head.length!=3) {
                throw new Exception("Line should have 3 columns");
            }
            if (a++==0) continue;
            //Store in 3 different strings for better-ness?
            String source = head[0];
            String target = head[1];
            String strWt = head[2];
            int weight = Integer.parseInt(strWt);

            graph.get(source).put(target, weight);
            graph.get(target).put(source, weight);
        }
        parse.close();

        return graph;
    }


    /**
     * Average as the answer of part a
     * @param graph
     */
    public static void average(HashMap<String, HashMap<String, Integer>> graph) {
        float totAsoc=0;
        float totEnt = graph.size();
        for( String iter: graph.keySet()){
            totAsoc+=graph.get(iter).size();
        }
        System.out.printf("%.2f", (totAsoc/totEnt));
    }


    
    /**
     * Partition for Quick Sort
     * @param chars
     * @param occurences
     * @param low
     * @param high
     * @return
     */
    public static int partition(String[] chars, int[] occurences, int low, int high){
        
        // System.out.println("Partition ded");
        int randomIdx = (int)(low + (high-low)*Math.random());
        int pivotOccur = occurences[randomIdx];
        String pivotchar = chars[randomIdx];
        int i = low;
        int j=high;
        while(i < j && i <= high){
            // System.out.println("High while");
            while (i <= high && (occurences[i]>pivotOccur || (occurences[i]==pivotOccur && chars[i].compareTo(pivotchar)>0) )) {
                i++;
                // System.out.println("first low while");
            } 
            while (occurences[j]<pivotOccur || (occurences[j]==pivotOccur && chars[j].compareTo(pivotchar)<0)){
                j--;
                // System.out.println("second low while");
            }
            if(i<j){
                String temp = chars[i];
                chars[i] = chars[j];
                chars[j] = temp;

                int temp2 = occurences[i];
                occurences[i] = occurences[j];
                occurences[j]=temp2;

                // i++;
                // j--;
            }
        }
        // chars[randomIdx] = chars[j];
        // chars[j]=pivotchar;
        // occurences[randomIdx] = occurences[j];
        // occurences[j]= pivotOccur;
        return j;
    }

    /**
     * Random sort recursive caller
     * @param occurences
     * @param chars
     * @param left
     * @param right
     */
    public static void Randomsort(int[] occurences, String[] chars, int left, int right){
        int p;
        // System.out.println("RandomSort ded");
        if (left<right){
            p = partition(chars, occurences, left, right);
            // System.out.println(p);
            Randomsort(occurences, chars, left, p-1);
            Randomsort(occurences, chars, p+1, right);
        }
    }

    /**
     * 
     * @param graph
     */
    public static void rank(HashMap<String, HashMap<String, Integer>> graph) {
        //Returns a hashmap of type string,int which stores total occurences
        //Gotta sort them after this 
        String[] chars = new String[graph.size()];
        int[] occurences = new int[graph.size()];
        int i=0;
        for (String iter: graph.keySet()){
            int temp=0;
            for (String iter2: graph.get(iter).keySet()){
                temp+=graph.get(iter).get(iter2);
            }
            chars[i]=iter;
            occurences[i]=temp;
            i++;
        }
        // for (int j=0; j<chars.length; j++) {
        //     System.out.println("," + chars[j]);
        // }
        Randomsort(occurences, chars, 0, graph.size()-1);
        
        // if (chars.length>=1) System.out.println(chars[0]+ ": "+ occurences[0]);
        // for (int j=1; j<chars.length; j++) {
        //     System.out.println("," + chars[j] + ": "+ occurences[j]);
        // }
        if (chars.length>=1) System.out.print(chars[0]);
        for (int j=1; j<chars.length; j++) {
            System.out.print("," + chars[j]);
        }
    }

    public static void main(String[] args) throws Exception {
        
        HashMap<String, HashMap<String, Integer>> graph = new HashMap<>(); 
        //Using this I am making an adjacency list. first hashmap's string can access a character
        //Which will be a hashmap. When I use another string, I will get the other character and 
        //The integer returned will be the weight
        Scanner sc = new Scanner(System.in);
        String nodeFileName = args[0];
        graph = nodeDelimiter(nodeFileName, graph);

        String edgeFileName = args[1];
        graph = edgeDelimiter(edgeFileName, graph);
        
        String funcName = args[2];
        // System.out.println(args[0]+args[1]+args[2]);
        if (funcName.equals("average")) {
            // System.out.println(args[2]);
            average(graph);
        }
        else if (funcName.equals("rank")) {
            rank(graph);
        }



        sc.close();
    }
}