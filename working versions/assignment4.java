import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
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
            head[0] = head[0].replaceAll("\"", "");
            head[1] = head[1].replaceAll("\"", "");
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
            head[0] = head[0].replaceAll("\"", "");
            head[1] = head[1].replaceAll("\"", "");
            // if(head[0].substring(0, 1).equals("\"")) head[0] = head[0].substring(1);
            // if(head[0].endsWith(",")) head[0] = head[0].substring(0,head[0].length() - 1);

            // if(head[1].substring(0, 1).equals("\"")) head[1] = head[1].substring(1);
            // if(head[1].endsWith(",")) head[1] = head[1].substring(0,head[1].length() - 1);
            // if(head[2].substring(0, 1).equals("\"")) head[2] = head[2].substring(1);
            // if(head[0].endsWith(",")) head[0] = head[0].substring(0,head[0].length() - 1);            
            
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

    
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////ANSWER FOR A PART STARTS////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
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
        // System.out.println(totEnt);
	if (totAsoc==0) System.out.print("0.00");
        else System.out.printf("%.2f", (totAsoc/totEnt));
	System.out.println();
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////ANSWER FOR A PART ENDS////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    

    














    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////ANSWER FOR B PART STARTS////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    
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
            }
        }
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
	System.out.println();
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////ANSWER FOR B PART ENDS////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    
















    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////ANSWER FOR C PART STARTS////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    


    //Use a hashmap of type String, Boolean which stores T if visited and F if not 
    //To store the DT, create an Arraylist of Arraylist of type String in which, each entry stores members
    //of CC. Sort them based on size and then before printing, Sort the internal array by character

    /**
     * 
     * @param graph
     * @param visited
     * @param chars
     * @param storage
     */
    public static void DFS(HashMap<String, HashMap<String, Integer>> graph, HashMap<String, Boolean> visited, String chars, ArrayList<ArrayList<String>> storage){
        if (visited.get(chars)==false){
            visited.put(chars, true);
            for (String iter: graph.get(chars).keySet()) {
                // Something prolly needs to be done here
                if (visited.get(iter)==false) storage.get(storage.size()-1).add(iter);
                DFS(graph, visited, iter, storage);
            }
        }
    }

    /**
     * 
     * @param graph
     * @return
     */
    public static ArrayList<ArrayList<String>> find_CC(HashMap<String, HashMap<String, Integer>> graph) {
        
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        for (String iter: graph.keySet()) {
            visited.put(iter, false);
        }
        ArrayList<ArrayList<String>> storage = new ArrayList<ArrayList<String>>();

        for (String chars: graph.keySet()){
            if(visited.get(chars)==false){
                storage.add(new ArrayList<String>());
                storage.get(storage.size()-1).add(chars);
                DFS(graph, visited, chars, storage);
            }
        }

        return storage;
    }



    /**
     * 
     * @param storage
     * @param low
     * @param high
     * @return
     */
    public static int partition3(ArrayList<ArrayList<String>> storage, int low, int high){
        
        // System.out.println("Partition ded");
        int randomIdx = (int)(low + (high-low)*Math.random());
        int pivot = storage.get(randomIdx).size();
        String pivotFirCh = storage.get(randomIdx).get(0); 
        int i = low;
        int j=high;
        while(i < j && i <= high){
            // System.out.println("High while");
            while (i <= high && (storage.get(i).size()>pivot || (storage.get(i).size()==pivot && storage.get(i).get(0).compareTo(pivotFirCh)>0 )   )) {
                i++;
                // System.out.println("first low while");
            } 
            while (storage.get(j).size()<pivot || (storage.get(j).size()==pivot && storage.get(j).get(0).compareTo(pivotFirCh)<0 )){
                j--;
                // System.out.println("second low while");
            }
            if(i<j){
                ArrayList<String> temp = storage.get(i);
                storage.set(i, storage.get(j));
                storage.set(j, temp);
            }
        }
        return j;
    }


    /**
     * 
     * @param storage
     * @param left
     * @param right
     */
    public static void Randomsort(ArrayList<ArrayList<String>> storage, int left, int right){
        int p;
        // System.out.println("RandomSort ded");
        if (left<right){
            p = partition3(storage, left, right);
            // System.out.println(p);
            Randomsort(storage, left, p-1);
            Randomsort(storage, p+1, right);
        }
    }





    /**
     * 
     * @param CC
     * @param low
     * @param high
     * @return
     */
    public static int partition2(ArrayList<String> CC, int low, int high){
        
        // System.out.println("Partition ded");
        int randomIdx = (int)(low + (high-low)*Math.random());
        String pivotchar = CC.get(randomIdx);
        int i = low;
        int j=high;
        while(i < j && i <= high){
            // System.out.println("High while is inf");
            while (i <= high && (CC.get(i).compareTo(pivotchar)>0) ) {
                i++;
                // System.out.println("first low while");
            } 
            while (CC.get(j).compareTo(pivotchar)<0){
                j--;
                // System.out.println("second low while");
            }
            if(i<j){
                String temp = CC.get(i);
                CC.set(i, CC.get(j));
                CC.set(j, temp);
            }
        }
        return j;
    }
    /**
     * 
     * @param CC
     * @param left
     * @param right
     */
    public static void Qsort(ArrayList<String> CC, int left, int right){
        int p;
        // System.out.println("RandomSort ded");
        if (left<right){
            // System.out.println("QSort is ded");
            p = partition2(CC, left, right);
            // System.out.println(p);
            // System.out.println("Partition2 is not inf loop");
            Qsort(CC, left, p-1);
            Qsort(CC, p+1, right);
        }
    }

    /**
     * 
     * @param graph
     */
    @SuppressWarnings("unchecked")
    public static void independent_storylines_dfs(HashMap<String, HashMap<String, Integer>> graph) {
        ArrayList<ArrayList<String>> stored_CC = find_CC(graph);
        Iterator iter = stored_CC.iterator();
        while(iter.hasNext()){
            // System.out.println("independent_storylines_dfs is infinite loop");
            ArrayList<String> temp = (ArrayList<String>) iter.next();
            Qsort(temp, 0, temp.size()-1);
            // System.out.println("QSort is done right");
        }
        Randomsort(stored_CC, 0, stored_CC.size()-1);
        // System.out.println("RandomSort done right");
        // At this point I should have a stored_CC which stores independent story lines sorted by decreasing
        // Number of characters in each storylines and that too correctly sorted
        // System.out.println("The total number of independent story lines are: " + stored_CC.size());
        Iterator iter2 = stored_CC.iterator();
        while(iter2.hasNext()){
            ArrayList<String> temp2 = (ArrayList<String>) iter2.next();
            if (temp2.size()>=1) System.out.print(temp2.get(0));
            for (int j=1; j<temp2.size(); j++) {
                System.out.print("," + temp2.get(j));
            }
            System.out.println();
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////ANSWER FOR C PART ENDS////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    


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
        else if (funcName.equals("independent_storylines_dfs")){
            independent_storylines_dfs(graph);
        }
        sc.close();
    }
}