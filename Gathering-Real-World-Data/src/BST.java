
/******************************************************************************
 *  Compilation:  javac BST.java
 *  Execution:    java BST
 *  Dependencies: StdIn.java StdOut.java Queue.java
 *  Data files:   https://algs4.cs.princeton.edu/32bst/tinyST.txt
 *
 *  A symbol table implemented with a binary search tree.
 ******************************************************************************/

//========================================================================
//                   Ben Heinze, CSCI232 Lab 4                          ||
//     BST Source: https://algs4.cs.princeton.edu/32bst/BST.java.html   ||
//        The source for the BST came from the online text book         ||
//========================================================================

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 *  The {@code BST} class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides ordered methods for finding the <em>minimum</em>,
 *  <em>maximum</em>, <em>floor</em>, <em>select</em>, <em>ceiling</em>.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *  <p>
 *  This implementation uses an (unbalanced) <em>binary search tree</em>.
 *  The <em>put</em>, <em>contains</em>, <em>remove</em>, <em>minimum</em>,
 *  <em>maximum</em>, <em>ceiling</em>, <em>floor</em>, <em>select</em>, and
 *  <em>rank</em>  operations each take &Theta;(<em>n</em>) time in the worst
 *  case, where <em>n</em> is the number of key-value pairs.
 *  The <em>size</em> and <em>is-empty</em> operations take &Theta;(1) time.
 *  The keys method takes &Theta;(<em>n</em>) time in the worst case.
 *  Construction takes &Theta;(1) time.
 *  <p>
 *  For alternative implementations of the symbol table API, see {@link },
 *  {@link BinarySearchST}, {@link SequentialSearchST}, {@link RedBlackBST},
 *  {@link SeparateChainingHashST}, and {@link LinearProbingHashST},
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/32bst">Section 3.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

//Most of this code is from the online book:
//https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/BST.java.html

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST

    //Each node will contain a key, value, and two children (left and right).
    //The left and right children are null until a node is assigned to them.
    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }//Node constructor
    }
//=================================================================================
    /**
     * Initializes an empty symbol table.
     */
    public BST() {
    }//Constructor

//=================================================================================
    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    //Just like the rest of the functions, the first function is so the client can access it
    //the second allows it to be called recursively
    //Since size is non-accessible to clients, the functions return (node).size;
    public int size() {
        return size(root);
    }
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }
//=================================================================================
    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    //first get function is called by the client
    public Value get(Key key) {
        return get(root, key);
    } //Sends root to the below get() function

    //This searches for the key(recursively) then returns the value of given key when found
    private Value get(Node x, Key key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null; //if it reaches the end of the leaves and didn't find anything, return null
        int cmp = key.compareTo(x.key); //assigns compareTo returns 0 if equal, -1 if less than argument and 1 if greater than the argument
                                        //cmp will either be: -1, 0, 1
        if      (cmp < 0) return get(x.left, key); //if cmp is -1, search the left branches
        else if (cmp > 0) return get(x.right, key);//if cmp is 1, search right branches
        else              return x.val; //if its not -1 or 1, it found the value (and returns the value)
    }
//=================================================================================
    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */

    //client calls this put function
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key"); //error for invalid input

        //This will delete the key if val is assigned null
        if (val == null) {
            delete(key);
            return;
        }
        //sends put into the recursive put() function below
        root = put(root, key, val);
    }

    //recursive put function
    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1); //checks if any nodes exist, if not it creates the first
        int cmp = key.compareTo(x.key); // cmp generates -1, 0, 1. it dictates that number on if the node key is
                                        //equal than, less than, or greater than to the specified key
        if      (cmp < 0) x.left  = put(x.left,  key, val); //iterates left
        else if (cmp > 0) x.right = put(x.right, key, val);//right branch
        else              x.val   = x.val; //if its equal to key, update value
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }


    /**
     *https://dzone.com/articles/in-order-binary-tree-traversal-in-java
     * I used this website for most of the "inOrder" method,
     * it has minor tweaks to work with the book's code.
     */
    public void inOrder(Queue q)  {
        inOrder(root, q);
    } //client calls this

    // iterates through BST starting from the least value (in order) until the greatest value
    private void inOrder(Node node, Queue q) {
       // Queue<Key> keys = new Queue<Key>();
       // Queue<Node> queue = new Queue<Node>();
        //queue.enqueue(root);
        //when it finds the end it will stop. Also when it reaches the end of a branch it will go up
        //while(!queue.isEmpty())
            if (node == null) {
                return;
            }

        //reaches for the far left branch
        inOrder(node.left, q);
        //prints te node/value
        q.enqueue(node.val);
        //System.out.println(node.val);
        //then goes to the right branch
        inOrder(node.right, q);

        //this will repeat until there is no
    }
//==========================================================================================
    /**
     * Returns the keys in the BST in level order (for debugging).
     *
     * @return the keys in the BST in level order traversal
     */

    //prints in the order of which level each node is on
    /*
    public Iterable<Key> levelOrder() {
        //uses a queue to store the values of each level
        Queue<Key> keys = new Queue<Key>();//creates a queue to store the keys
        Queue<Node> queue = new Queue<Node>(); //creates a queue to store the queue
        queue.enqueue(root); //enqueues the first

        //iterates while the queue is not empty
        while (!queue.isEmpty()) {
            Node x = queue.dequeue(); //
            if (x == null) continue;
            keys.enqueue(x.key);//enqueues the node key into the key queue
            System.out.println(x.key +"\t"+ x.val); //prints when it adds node to node queue
            queue.enqueue(x.left);//enqueues the left branch into the node queue
            queue.enqueue(x.right);// enqueues the right branch into the node queue
        }
        return keys; //returns the queue of keys
    }

     */

//===========================================================================================
    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    } // size is 0 when the tree is empty
    //returns 0 if it has nodes present in tree
//===========================================================================================
    /*
public static void main(String[] args) {
    BST bst = new BST();

    bst.put('V', 5);
    bst.put('E', 10);
    bst.put('R', 15);
    bst.put('Y', 20);
    bst.put('E', 25);
    bst.put('A', 30);
    bst.put('S', 35);
    bst.put('Y', 40);
    bst.put('Q', 45);
    bst.put('U', 50);
    bst.put('E', 55);
    bst.put('S', 60);
    bst.put('T', 65);
    bst.put('I', 70);
    bst.put('O', 75);
    bst.put('N', 80);

    System.out.println("In order Traversal:");
    bst.inOrder();
    System.out.println();
    System.out.println("Level order:");
    bst.levelOrder();

}
/*




//========================================================================================
//========================================================================================
//========================================================================================

    /**
     * Does this symbol table contain the given key?
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */

    //This allows the client to determine if a key is already present in the BST
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");//if the user forget to send in a key
        return get(key) != null;
    } // searches the Tree to see if it contains the specified key

//=================================================================================

    //client calls deleteMin and it sends the root to the recursive function
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);

    }

    //recursively iterates through tree
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;//changed direction when it reaches the end
        x.left = deleteMin(x.left);//
        x.size = size(x.left) + size(x.right) + 1;//updates size
        return x;
    }
//=================================================================================
    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */

    //sends root to recursive function below
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);

    }

    //it iterates through the tree to find the max and deletes it
    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;//updates size
        return x;
    }
//=================================================================================
    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */

    //
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
        //assert check();
    }

    //
    private Node delete(Node x, Key key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key); //compares the specified key(the one the client wants to delete)
                                        //and compares it to the current node key
        if      (cmp < 0) x.left  = delete(x.left,  key); //if cmp is less than zero, search the left branch
        else if (cmp > 0) x.right = delete(x.right, key); //if cmp is greater than zero, search the right branch
        else {
            if (x.right == null) return x.left;//if the right branch of current node doesnt exist, check the left
            if (x.left  == null) return x.right;//if the lest branch of current node doesnt existm check the right branch

            Node t = x;//assigns node T to current node
            x = min(t.right);//checks min of that right branch
            x.right = deleteMin(t.right);//sends right to delete Min
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;//updates size
        return x;//returns the deleted node
    }

//=================================================================================
    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else                return min(x.left);
    }
//=================================================================================
    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else
            return max(x.right);
    }
//=================================================================================
    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param  key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp <  0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    public Key floor2(Key key) {
        Key x = floor2(root, key, null);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x;

    }

    private Key floor2(Node x, Key key, Key best) {
        if (x == null) return best;
        int cmp = key.compareTo(x.key);
        if      (cmp  < 0) return floor2(x.left, key, best);
        else if (cmp  > 0) return floor2(x.right, key, x.key);
        else               return x.key;
    }
//=================================================================================
    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     *
     * @param  key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to ceiling() is too large");
        else return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) {
            Node t = ceiling(x.left, key);
            if (t != null) return t;
            else return x;
        }
        return ceiling(x.right, key);
    }
//=================================================================================
    /**
     * Return the key in the symbol table of a given {@code rank}.
     * This key has the property that there are {@code rank} keys in
     * the symbol table that are smaller. In other words, this key is the
     * ({@code rank}+1)st smallest key in the symbol table.
     *
     * @param  rank the order statistic
     * @return the key in the symbol table of given {@code rank}
     * @throws IllegalArgumentException unless {@code rank} is between 0 and
     *        <em>n</em>–1
     */
    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    // Return key in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private Key select(Node x, int rank) {
        if (x == null) return null;
        int leftSize = size(x.left);
        if      (leftSize > rank) return select(x.left,  rank);
        else if (leftSize < rank) return select(x.right, rank - leftSize - 1);
        else                      return x.key;
    }
//=================================================================================
    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param  key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    // Number of keys in the subtree less than key.
    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else              return size(x.left);
    }
//=================================================================================
    /**
     * Returns all keys in the symbol table in ascending order,
     * as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table in ascending order
     */

    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

//===============================================================================


}


//Copyright © 2000–2019, Robert Sedgewick and Kevin Wayne.
     //   Last updated: Thu Aug 11 09:13:03 EDT 2022.