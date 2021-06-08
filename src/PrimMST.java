import java.util.*;

public class PrimMST {

    private static Queue<Node>[] adjacencyList = new LinkedList[8];
    static PriorityQueue<Node> Q = new PriorityQueue<>(new nodeCompare());
    static ArrayList<Node> T = new ArrayList<>();
    static ArrayList<Node> S = new ArrayList<>();
    static int INFINITY = 100000;

    // create the nodes that will be added to Q, set pi to "infinity" for each one
    static Node a = new Node('a', INFINITY);
    static Node b = new Node('b', INFINITY);
    static Node c = new Node('c', INFINITY);
    static Node d = new Node('d', INFINITY);
    static Node e = new Node('e', INFINITY);
    static Node f = new Node('f', INFINITY);
    static Node g = new Node('g', INFINITY);
    static Node h = new Node('h', INFINITY);

    public static void main(String[]  args){
        char userChoice = '.';
        boolean validChoice = false;
        char[] validChars = {'a','b','c','d','e','f','g','h'};
        Scanner input = new Scanner(System.in);

        // create class object and create adjacency list representation of the graph
        PrimMST p = new PrimMST();
        p.createAdjacencyList();
        System.out.println("The graph is represented as an adjacency list.");

        // display data structure to console, get user input for start node, validate user input
        while(!validChoice){
            System.out.println("Please enter the character associated with the node you wish to start at: ");
            userChoice = input.next().charAt(0);
            for(char c : validChars){
                if (userChoice == c) {
                    validChoice = true;
                    break;
                }
            }
        }

        // call prim's algorithm on the user-chosen start node
        p.Prim(getNode(userChoice));

    }

    // method to perform prim's algorithm on the graph
    public void Prim(Node s){

        // counter fro iterations
        int counter = 1;

        // build the priority queue
        buildPQ();

        // print contents of S, T, and Q to the console
        System.out.println("Before 1st Iteration:");
        s.pi = 0;
        printS();
        printT();
        printQ();
        System.out.println("\n");

        while(Q.size() != 0){

            // extract min node, add to S
            Node v = extractMin();
            S.add(v);

            // if v isn't s, add edge to T
            if(v != s){
                T.add(v.pred);
                T.add(v);
            }

            // get adjacency list index for node
            int index = getIndex(v.name);

            // for each edge (v,w) such that w is not in S
            for(Node n : adjacencyList[index]){
                if(!S.contains(getNode(n.name))){
                    if(n.pi < getNode(n.name).pi){
                        getNode(n.name).pi = n.pi;
                        getNode(n.name).pred = getNode(v.name);
                        changeKey(getNode(n.name), getNode(n.name).pi);
                    }
                }
            }
            // print results of iteration to console
            System.out.println("\nAt the end of Iteration " + counter + ":");
            counter++;
            printS();
            printT();
            printQ();
            System.out.println("\n");

        }
    }


    // method to create priority queue Q
    public void buildPQ(){
        Q.add(a);
        Q.add(b);
        Q.add(c);
        Q.add(d);
        Q.add(e);
        Q.add(f);
        Q.add(g);
        Q.add(h);
    }

    // method to extract and return the node with the minimum pi value
    public Node extractMin(){
        return Q.remove();
    }

    // method to change the key of a node
    public void changeKey(Node n, int newPi){

        // get contents of Q, read into arraylist
        ArrayList<Node> nodeArray = new ArrayList<>();
        while(Q.size() != 0){
            nodeArray.add(Q.remove());
        }

        // find the right node, change its key
        for(int i = 0; i< nodeArray.size(); i++){
            // if node was found, change its key
            if(nodeArray.get(i).getName() == n.getName()){
                nodeArray.get(i).pi = newPi;
            }
        }

        // key has been changed, rebuild the queue
        for(int i = 0; i < nodeArray.size(); i++){
            Q.add(nodeArray.get(i));
        }

    }


    // method to sort the priority queue for printing to console.
    public void printQ(){
        ArrayList<Node> nodeArray = new ArrayList<>();

        // get contents of queue
        while(Q.size() != 0){
            nodeArray.add(Q.remove());
        }

        // sort them, print them to console
        nodeArray.sort(new queueSorter());
        System.out.print("\nQ = {");
        for(int i = 0; i < nodeArray.size(); i++){

            if(i == nodeArray.size() -1)
                System.out.print(nodeArray.get(i) + "}");
            else
                System.out.print(nodeArray.get(i) + ", ");
        }
        // rebuild the queue
        for(int j = 0; j < nodeArray.size(); j++){
            Q.add(nodeArray.get(j));
        }

        if(Q.size() == 0){
            System.out.print("}");
        }

    }


    // method to print the contents of S
    public void printS(){
        System.out.print("S = {");
        // print out the character associated with each node in S
        for(int i = 0; i < S.size(); i++){
            if(S.size() == 1 || i == S.size() -1){
                System.out.print(S.get(i).name);
            }
            else{
                System.out.print(S.get(i).name +",");
            }
        }
        System.out.println("}");
    }


    // method to print the contents of T
    public void printT(){
        System.out.print("T = {");
        for(int i = 0; i < T.size(); i++){
            // print out the edges, as edge e = (v, w)
            if(i % 2 == 0){
                System.out.print("(" + T.get(i).name + ", " + T.get(i + 1).name + ")");
                if(i + 1 != T.size()- 1){
                    System.out.print(",");
                }
            }
        }
        System.out.print("}");
    }


    // helper function to get the index for thew adjacency list for a node
    public int getIndex(char c){
        // switch statement to get correct index for adjacency list
        switch(c) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;

        }
        return 0;
    }


    // helper function to get the correct node given the char of its name
    // used to help interact with the adjacency list representation of the graph
    public static Node getNode(char character){

        switch(character) {
            case 'a':
                return a;
            case 'b':
                return b;
            case 'c':
                return c;
            case 'd':
                return d;
            case 'e':
                return e;
            case 'f':
                return f;
            case 'g':
                return g;
            case 'h':
                return h;

        }
        return a;
    }


    // method for  adjacency list representation of the graph
    // Note: pi values are the weight from the node being looked at
    // for example, adjacencyList[0] has the neighbors for a, and the weights
    // for those edges.
    public void createAdjacencyList(){

        // for a
        adjacencyList[0] = new LinkedList();
        adjacencyList[0].add(new Node('b',9));
        adjacencyList[0].add(new Node('f', 14));
        adjacencyList[0].add(new Node('g',15));

        // for b
        adjacencyList[1] = new LinkedList();
        adjacencyList[1].add(new Node('a',9));
        adjacencyList[1].add(new Node('c',24));

        // for c
        adjacencyList[2] = new LinkedList();
        adjacencyList[2].add(new Node('b',24));
        adjacencyList[2].add(new Node('f',18));
        adjacencyList[2].add(new Node('e',2));
        adjacencyList[2].add(new Node('d',6));
        adjacencyList[2].add(new Node('h',19));

        // for d
        adjacencyList[3] = new LinkedList();
        adjacencyList[3].add(new Node('e',11));
        adjacencyList[3].add(new Node('c',6));
        adjacencyList[3].add(new Node('h',6));

        // for e
        adjacencyList[4] = new LinkedList();
        adjacencyList[4].add(new Node('g',20));
        adjacencyList[4].add(new Node('f',30));
        adjacencyList[4].add(new Node('c',2));
        adjacencyList[4].add(new Node('d',11));
        adjacencyList[4].add(new Node('h',16));

        // for f
        adjacencyList[5] = new LinkedList();
        adjacencyList[5].add(new Node('a',14));
        adjacencyList[5].add(new Node('c',18));
        adjacencyList[5].add(new Node('e',30));
        adjacencyList[5].add(new Node('g',5));

        // for g
        adjacencyList[6] = new LinkedList();
        adjacencyList[6].add(new Node('a',15));
        adjacencyList[6].add(new Node('f',5));
        adjacencyList[6].add(new Node('e',20));
        adjacencyList[6].add(new Node('h',44));

        // for h
        adjacencyList[7] = new LinkedList();
        adjacencyList[7].add(new Node('g',44));
        adjacencyList[7].add(new Node('e',16));
        adjacencyList[7].add(new Node('d',6));
        adjacencyList[7].add(new Node('c',19));
    }


    // node class for adjacency list representation and for use by the
    // priority queue . Contains the nodes name, pi value, and predecessor
    static class Node {
        public char name;
        public int pi;
        public Node pred;

        // constructor
        public Node(char name, int pi){
            this.name = name;
            this.pi = pi;
        }

        // getters
        public char getName() {
            return name;
        }
        public int getPi(){
            return pi;
        }

        // allow for nodes to be printed as (name, pi)
        public String toString() {
            if(this.pi == INFINITY){
                return "(" + this.name + ", -)";
            }
            else{
                return "(" + this.name + ", " + this.pi + ")";
            }
        }
    }


    // NOTE: I used code from the following site: https://www.geeksforgeeks.org/implement-priorityqueue-comparator-java/
    // as a template for this function
    // function allows for the min priority queue to use pi value as priority key
    static class nodeCompare implements Comparator<Node> {

        // compare the Node objects by their pi value
        public int compare(Node firstNode, Node secondNode) {
            if (firstNode.pi > secondNode.pi)
                return 1;
            else if (firstNode.pi < secondNode.pi)
                return -1;
            return 0;
        }
    }


    // method for sorting the queue alphabetically for printing to console
    public class queueSorter implements Comparator<Node>
    {
        public int compare(Node n1, Node n2) {
            int c1 = Character.getNumericValue(n1.getName());
            int c2 = Character.getNumericValue(n2.getName());
            return Integer.compare(c1, c2);
        }
    }
}

