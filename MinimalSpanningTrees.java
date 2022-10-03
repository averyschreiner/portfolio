/*
 * Class: Survey of Alg
 * Author: Avery Schreiner
 * Description: p2 mst
 * Due: 29 April 2022
 * I pledge that I have completed the programming assignment independently.
 * I have not copied the code from a student or any source.
 * I have not given my code to any other student.
 * I have not given my code to any other student and will not share this code
 * with anyone under any circumstances.
 */
package com.mycompany.project2_mst;
/**
 *
 * @author S538964
 */
import org.jgrapht.graph.*;
import java.util.*;


public class p2mst_main 
{
    public static void main(String[] args) 
    {
        //add sizes to al
        ArrayList<Integer> sizes = new ArrayList<>();
        sizes.add(99); //n-1
        sizes.add(500);
        sizes.add(1000);
        sizes.add(1500);
        sizes.add(2000);
        sizes.add(2500);
        sizes.add(3000);
        sizes.add(3500);
        sizes.add(4000);
        sizes.add(4500);
        sizes.add(4950); //(n-1)*n /2 

        //establish our timings list
        ArrayList<Long> p_timings = new ArrayList<>();
        ArrayList<Long> k_timings = new ArrayList<>();
        for (int t = 0; t < sizes.size(); t++)
        {
            p_timings.add(t, 0L);
            k_timings.add(t, 0L);
        }

        //timed each size of graph 100 times for more accurate output
        for (int i = 1; i <= 100; i ++)
        {
            for (int j = 0; j < sizes.size(); j++)
            {
                //create our graph
                SimpleWeightedGraph<Integer, DefaultEdge> g = new SimpleWeightedGraph(DefaultEdge.class);
                g = gen(g, sizes.get(j));

                //collect our timings
                long start, end;
                //prim
                start = System.nanoTime();
                ArrayList<DefaultEdge> p = prim(g);
                end = System.nanoTime();
                p_timings.add(j, p_timings.get(j) + (end - start));
                //kruskal
                start = System.nanoTime();
                ArrayList<DefaultEdge> k = kruskal(g);
                end = System.nanoTime();
                k_timings.add(j, k_timings.get(j) + (end - start));

                //get the total weight of each solution, check if equal
                double p_weight = 0;
                for (DefaultEdge d: p)
                {
                    p_weight += g.getEdgeWeight(d);
                }

                double k_weight = 0;
                for (DefaultEdge d: k)
                {
                    k_weight += g.getEdgeWeight(d);
                }

                //if prim and kruskal got different mst, print an error message
                if (p_weight != k_weight)
                {
                    System.out.println("Unequal weight on iteration " + i + " size " + sizes.get(j) + 
                            ", p = " +  p_weight + ", k = " + k_weight);
                } 
            }
        }

        //timings output
        System.out.print("Size:");
        for (int i = 0; i < sizes.size(); i++)
        {
            System.out.print("\t" + sizes.get(i));
        }
        System.out.println();
        System.out.print("Prim:");
        for (int i = 0; i < sizes.size(); i++)
        {
            System.out.print("\t" + p_timings.get(i));
        }
        System.out.println();
        System.out.print("Krus:");
        for (int i = 0; i < sizes.size(); i++)
        {
            System.out.print("\t" + k_timings.get(i));
        }
        System.out.println();
    }
    
    
    public static ArrayList<DefaultEdge> prim(SimpleWeightedGraph<Integer, DefaultEdge> g)
    {
        //empty list for edges
        ArrayList<DefaultEdge> edges = new ArrayList<>();
        
        //list of visited nodes, start with middle node why not
        ArrayList<Integer> visited = new ArrayList<>();
        visited.add(50); 
        
        //pick 99 edges
        while (edges.size() < 99)
        {        
            //establish our variables
            double min = 200; //our abs max weight is 100 so 200 will do
            Integer sourceNode = -1;
            Integer targetNode = -1;
            Integer smallestSource = -1;
            Integer smallestTarget = -1;
            
            //iter over visited nodes
            for (int j = 0; j < visited.size(); j++)
            {
                //iter through the nodes they lead to 
                for (DefaultEdge e: g.edgesOf(visited.get(j)))
                {   
                    //if we havent already chosen this edge as apart of our mst
                    if (!edges.contains(e))
                    {  
                        sourceNode = g.getEdgeSource(e);
                        targetNode = g.getEdgeTarget(e);

                        //we cant consider an edge if it creates a loop in our mst
                        if (!(visited.contains(sourceNode) && visited.contains(targetNode)))
                        {
                            //weight of the current edge we are looking at
                            double tempWeight = g.getEdgeWeight(e);

                            //if its weight is the new smallest
                            if (tempWeight < min)
                            {
                                smallestSource = sourceNode;
                                smallestTarget = targetNode;
                                min = tempWeight;
                            }
                        }
                    }            
                }
            }

            //add our new visited node to list
            //if visited already has our current "source" node, just add the "target" node instead
            if (visited.contains(smallestSource))
            {
                visited.add(smallestTarget);
            }
            else
            {
                visited.add(smallestSource);
            }
            
            //add our smallest edge to our list
            edges.add(g.getEdge(smallestSource, smallestTarget));
        }
        return edges;
    }
    
    public static ArrayList<DefaultEdge> kruskal(SimpleWeightedGraph<Integer, DefaultEdge> g)
    {
        //empty list for edges
        ArrayList<DefaultEdge> edges = new ArrayList<>();
        
        //ll of edge weights
        LinkedList<Double> weights = new LinkedList<>();
        
        //put each node in own list
        ArrayList<ArrayList<Integer>> alal = new ArrayList<>();
        for (int i = 1; i<= 100; i++) 
        {   
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(i);
            alal.add(temp);
        }   

        //create ht with weights and edges pairings
        Hashtable<Double, ArrayList<DefaultEdge>> ht = new Hashtable<>();

        //add weights to ll and pairings to ht
        for (DefaultEdge e: g.edgeSet())
        {
            //add weight to ll
            Double w = g.getEdgeWeight(e);
            weights.add(w);
            
            //if not already a key, create pairing
            if (!ht.containsKey(w))
            {
                ht.put(w, new ArrayList<>());
            }
            
            //add to existing key
            ht.get(w).add(e);
        }
        //and sort ll
        Collections.sort(weights);
        
        //pick 99 edges
        while (edges.size() < 99) 
        {
            //smallest weight in list
            Double weight = weights.removeFirst();

            //get edge with that weight
            DefaultEdge edge = ht.get(weight).remove(0);
            
            //assign source and target
            Integer source = g.getEdgeSource(edge);
            Integer dest = g.getEdgeTarget(edge);
            
            //vars
            int sourceList = -1;
            int destList = -1;
            
            //iter through lists
            for (ArrayList list: alal)
            {
                //need list of source
                if (list.contains(source))
                {
                    sourceList = alal.indexOf(list);
                }
                
                //and list of dest
                if (list.contains(dest))
                {
                    destList = alal.indexOf(list);
                }
            }
            
            //if nodes are in seperate lists
            if (sourceList != destList)
            {
                //add edge to list
                edges.add(g.getEdge(source, dest));    
                
                //combine the nodes into the same list
                ArrayList<Integer> rip = alal.get(sourceList);
                alal.get(destList).addAll(rip);
                alal.remove(sourceList);
            }
        }
        return edges;
    }
    
    public static SimpleWeightedGraph<Integer, DefaultEdge> gen(SimpleWeightedGraph<Integer, DefaultEdge> g, int numEdges)
    {
        Random random = new Random();
        
        //add nodes to graph start a 1 go to 100
        for (int i = 1; i <= 100; i++)
        {
            g.addVertex(i);
        }

        //connect "adjacent" nodes for min num of edges
        for (int j = 1; j < 100; j++)
        {
            g.addEdge(j, j+1);

            //add random weight from -100 to 100
            int sign = random.nextInt(2); //either 0 or 1
            int num = random.nextInt(100) + 1;
            if (sign == 0) //positive
            {
                g.setEdgeWeight(j, j+1, num);
            }
            else //negative
            {
                g.setEdgeWeight(j, j+1, num*-1);
            }
        }
   
        if (numEdges == 4950) //max edges, "special" case
        {
            //make a completed connect graph, a web if you will
            //connect every node to every other node but itself (no self loops)
            for (int a = 1; a <= 100; a++)
            {
                for (int b = 1; b <= 100; b++)
                {
                    //no loops
                    if (a != b) 
                    {
                        g.addEdge(a, b);
                        
                        //add random weight from -100 to 100
                        int sign = random.nextInt(2); //either 0 or 1
                        int num = random.nextInt(100) + 1; //1-100
                        if (sign == 0) //positive
                        {
                            g.setEdgeWeight(a, b, num);
                        }
                        else //negative
                        {
                            g.setEdgeWeight(a, b, num*-1);
                        }
                    }
                }
            }
        }
        else 
        {
            while (numEdges - 99 > 0) //subtract amount of initially added edges
            {
                int source = random.nextInt(100) + 1;
                int dest = random.nextInt(100) + 1;
                
                //if not a self loop or already an existing edge
                if (source != dest && !g.containsEdge(source, dest))
                {
                        g.addEdge(source, dest);
                        
                        //add random weight from -100 to 100
                        int sign = random.nextInt(2); //either 0 or 1
                        int num = random.nextInt(100) + 1; //1-100
                        if (sign == 0) //positive
                        {
                            g.setEdgeWeight(source, dest, num);
                        }
                        else //negative
                        {
                            g.setEdgeWeight(source, dest, num*-1);
                        }
                        numEdges--;
                }
            }
        }
        return g;
    }
}
