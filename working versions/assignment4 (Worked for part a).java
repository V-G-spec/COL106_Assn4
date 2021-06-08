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
            
            if (head.length!=2) {
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



    public static void average(HashMap<String, HashMap<String, Integer>> graph) {
        float totAsoc=0;
        float totEnt = graph.size();
        for( String iter: graph.keySet()){
            totAsoc+=graph.get(iter).size();
        }
        System.out.printf("%.2f", (totAsoc/totEnt));
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
            System.out.println(args[2]);
            average(graph);
        }




        sc.close();
    }
}