// Vishnu Kartha
// CSE 143 BN with Soham Pardeshi
// Homework #8 Huffman Code

// A HuffmanCode object allows the user to compress and decompress files using the Huffman
// algorithm. The user can create a code relating to a file and later decompress the file
// using the code.
import java.io.*;
import java.util.*;

public class HuffmanCode {
   
   // the root of the tree which contains the relationship between the code created by the
   //  huffman algorithm and the characters in a file.
   private HuffmanNode overallRoot;
   
   // params: frequencies - an array relating ascii values of a character to the
   //                       number of times they appear.
   // throws: none
   // returns: none
   // description: creates a tree using the Huffman algorithm to represent the characters in a file.
   public HuffmanCode(int[] frequencies){
      Queue<HuffmanNode> q = new PriorityQueue<HuffmanNode>();
      for(int i = 0; i < frequencies.length; i++) {
         if(frequencies[i] > 0) {
            HuffmanNode node = new HuffmanNode(frequencies[i], i);
            q.add(node);
         }
      }
      while(q.size() > 1) {
         HuffmanNode node1 = q.remove();
         HuffmanNode node2 = q.remove();
         int freqSum = node1.frequency + node2.frequency;
         q.add(new HuffmanNode(freqSum,node1,node2));
      }
      overallRoot = q.remove();
   }
   
   // params: input - the inputted code that is used to create a tree
   // throws: none
   // returns: none
   // description: creates a tree using a previously constructed code and the Huffman algorithm.
   public HuffmanCode(Scanner input) {
      overallRoot = new HuffmanNode(-1);
      while(input.hasNextLine()){
         int asciiValue = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         HuffmanNode leaf = new HuffmanNode(asciiValue);
         constructorHelper(overallRoot,leaf,code);
      }
   }
   
   // params: curr - the current HuffmanNode that is looked at
   //         leaf - the the leaf consisting of the current character that is looked at in the code
   //         codeRemaining - the code representing the position of the leaf in the tree.
   // throws: none
   // returns: returns a HuffmanNode that represents the current node considered
   //          and its corresponding links
   // description: creates a tree using a previously constructed code and the Huffman algorithm.
   private HuffmanNode constructorHelper(HuffmanNode curr, HuffmanNode leaf, String codeRemaining) {
      if(codeRemaining.equals("")){
         return leaf;
      } else {
         if(codeRemaining.charAt(0) == '0') {
            if(curr.left == null) {
               curr.left = new HuffmanNode(-1);
            }
            curr.left = constructorHelper(curr.left, leaf, codeRemaining.substring(1));
         } else {
            if(curr.right == null) {
               curr.right =  new HuffmanNode(-1);
            }
            curr.right = constructorHelper(curr.right, leaf, codeRemaining.substring(1));
         }
         return curr;
      }
   }
   // params: output - a necessary parameter to write output to a given file;
   // throws: none
   // returns: none
   // description: writes information corresponding to the leves in  the current tree
   //              of nodes to an output file. The output contains the ascii
   //              values of the leaves followed by the its corresponding codes in 
   //              traversal order.  
   public void save(PrintStream output) {
      save(output, overallRoot,""); 
   }
   
   // params: output - a necessary parameter to write output to a given file;
   //         curr - the current node that is considered
   //         code - the series of 1s and 0s that should be put in the output file
   // throws: none
   // returns: none
   // description: writes information corresponding to the leves in  the current tree
   //              of nodes to an output file. The output contains the ascii
   //              values of the leaves followed by the its corresponding codes in 
   //              traversal order.  
   private void save(PrintStream output, HuffmanNode curr,String code) {
      if(curr.left != null){
         save(output,curr.left,code+"0");
         save(output,curr.right,code+"1");
         
      } else {
         output.println(curr.ascii);
         output.println(code);
      }
   }
   
   // params: input - the inputted info that is looked up and matched to the tree
   //         output - a necessary parameter to write output to a given file;
   // throws: none
   // returns: none
   // description: translates a compressed file back into its normal state before
   //              compression.
   public void translate(BitInputStream input, PrintStream output) {
      while(input.hasNextBit()){
         HuffmanNode curr = overallRoot;
         while(curr.left != null && curr.right != null){
            if(input.nextBit() == 0){
               curr = curr.left;
            } else {
               curr = curr.right;
            }
         }
         output.write(curr.ascii);
      }
   }
   
   // A HuffmanNode is a representation of a character that appears in a file.
   private static class HuffmanNode implements Comparable<HuffmanNode>{
      
      // the ascii value relating to a character
      public int ascii;
      // the number of times a given character appears in a file.
      public int frequency;
      // reference to left sub tree
      public HuffmanNode left;
      // reference to right sub tree
      public HuffmanNode right;
      
      // params: frequency - the given number of times a character appears.
      // throws: none
      // returns: none
      // description: creates a HuffmanNode consisting of the given frequency.
      //              the ascii value is set to -1.
      public HuffmanNode(int ascii){
         this.ascii = ascii;
      }
      
      // params: frequency - the given number of times a character appears.
      //         ascii - the given ascii value for a single character.
      // throws: none
      // returns: none
      // description: creates a HuffmanNode consisting of the given frequency and
      //              ascii value.
      public HuffmanNode(int frequency, int ascii) {
         this.frequency = frequency;
         this.ascii = ascii;
      }
      
      // params: frequency - the given number of times a character appears.
      //         left - the given left subtree.
      //         right - the givenright subtree.
      // throws: none
      // returns: none
      // description: creates a HuffmanNode consisting of the given frequency and left
      //              and right subtrees. Ascii value is set to -1.
      public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
         this.frequency = frequency;
         this.ascii = -1;
         this.left = left;
         this.right = right;
      }
      
      // params: other - the other HuffmanNode that this node is being compared to.
      // throws: none
      // returns: returns a positive integer if this frequency is greater than the other frequency.
      //          returns a negative integer if this frequency is less than the other frequency.
      //          returns 0 if this frequency is equal to the other frequency.
      // description: compares this HuffmanNode to another HuffmanNode by comparing frequencies.
      public int compareTo(HuffmanNode other) {
         return this.frequency - other.frequency;
      }
   }
}





































































































































































































































































































































































































































































































