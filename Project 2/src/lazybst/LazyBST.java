package lazybst;
import java.util.ArrayList;

/**
 * LazyBST models a lazy binary tree as per the instructions 
 * given to us in project 2.
 * 
 * -turning in one day late (due date was Oct 22)
 * -using one grace day
 * 
 * @author Michael Neary <mneary1@umbc.edu>
 * @project CMSC 341 - Fall 2013 - Project 2
 * @section 01
 * @version Oct 23, 2013
 */
public class LazyBST< T extends Comparable<? super T> > {

	//the root of the LazyBST
	private LazyBSTNode<T> root;
	
	//span instance variable, used only for span()
	private int span;
	
	/**
	 * Constructor of LazyBST
	 * sets the root to null
	 * sets the span variable to 0
	 */
	public LazyBST(){
		this.root = null;
		this.span = 0;
	}

	/**
	 * adds a given item to the LazyBST
	 * @param x - item to insert
	 */
	public void insert(T x){
		root = insert(x,root);
	}	

	/**
	 * internal insert method
	 * recurses through the Lazy BST in O(log(n)) time
	 * max # of nodes to check is the same as height of tree
	 * 
	 * at each node checking if a rebalance is needed
	 * then comparing the data in the node to the item being inserted to determine where to insert
	 * once inserted the size and height of the node is updated
	 * 
	 * @param x - item to insert in a tree
	 * @param parent - node to insert at
	 * @return a LazyBSTNode for the recursion
	 */
	private LazyBSTNode<T> insert(T x, LazyBSTNode<T> parent){
		
		if(canRebalance(parent)){
			parent = rebalance(parent);
		}
		
		//if the parent is null, then insert a new LazyBSTNode at the parent
		if(parent == null){
			parent = new LazyBSTNode<T>(x,null,null);
			
		}
		
		//if the parent is not null, then you can compare the value in the 
		//parent with the value that you're trying to insert
		
		int comparison = x.compareTo(parent.data);
		
		/*
		 * if the comparison is less than 0:
		 *   x comes before the data in the parent
		 *   traverse the left subtree (that's where all the smaller keys are)
		 *
		 * if the comparison is greater than 0:
		 *   x comes after the data in the parent
		 *   traverse the right subtree (that's where all the larger keys are)
		 *  
		 * if the comparison equals zero
		 *   then x is a duplicate
		 *   do nothing (no duplicates allowed in LazyBST)
		 */
		
		if(comparison < 0){
			
			parent.leftChild = insert(x,parent.leftChild);
			
		}
		else if(comparison > 0){
			
			parent.rightChild = insert(x, parent.rightChild);
			
		}
		else{
			// nothing happens, its a duplicate
		}
		
		updateSizeAndHeight(parent);
		
		return parent; 
		
	}

	/**
	 * removes a given item from the LazyBST
	 * @param x - item to remove
	 * @return 
	 */
	public T remove(T x){
		LazyBSTNode<T> newRoot = remove(x, this.root);
		if(newRoot != null){
			this.root = newRoot;
		}	
		return x;
	}

	/**
	 * internal recursive remove method to remove an item from a LazyBST
	 * 
	 * runs in O(log(n)) time
	 * recurses through the tree until it finds the node to remove
	 * if that node has two children, it finds the smallest item in the right subtree
	 * it does this in a way that does not traverse the whole tree a second time
	 * 
	 * first checks to see if the current node can be rebalanced
	 * then compares the item being removed with the item in the current node
	 * decides where to go in the tree based on that comparison
	 * if the node is found then it decides what to from there
	 * 
	 * @param x - the item being removed
	 * @param parent - the node to remove
	 * @return LazyBSTNode<T> to recurse with, last one is the new root
	 */
	private LazyBSTNode<T> remove(T x, LazyBSTNode<T> parent){
		
		if(canRebalance(parent)){
			parent = rebalance(parent);
		}
		
		if(parent == null){
			return null;
		}
		
		int cmp = x.compareTo(parent.data);
		
		if(cmp < 0){
			parent.leftChild = remove(x, parent.leftChild);
			if(parent.leftChild ==null){
				parent.size --;
				parent.height --;
			}
		}
		else if(cmp > 0){
			parent.rightChild = remove(x, parent.rightChild);
			if(parent.rightChild == null){
				parent.size --;
				parent.height --;
			}
		}
		else{
			//reaching this clause means the node to remove has been found
			
			//if there are no children, just return null
			if(parent.leftChild == null && parent.rightChild == null){
				return null;
			}
			
			if(parent.leftChild == null){
				
				return parent.rightChild;
			}
			
			if(parent.rightChild == null){
				
				
				return parent.leftChild;
			}
			
			// if none of the above are satisfied then you have a node w/ two children
			
			//find the smallest node in the right subtree
			//the data in the node you want to remove changes to the data in that node
			//and then remove that smallest node in the right subtree
			LazyBSTNode<T> temp = findAndRemoveMin(parent.rightChild);
			parent.data = temp.data;
			
			
					
		}
		
		updateSizeAndHeight(parent);
		return parent;
	}
	
	/**
	 * internal method to find and remove the smallest node in the right subtree
	 * when a node being removed has two children 
	 * 
	 * @param parent - node to remove from
	 * @return the removed node
	 */
	private LazyBSTNode<T> findAndRemoveMin(LazyBSTNode<T> parent){
		
		if(parent.leftChild.leftChild == null){
			LazyBSTNode<T> temp = parent.leftChild;
			parent.leftChild = parent.leftChild.rightChild;
			parent.size --;
			
			//special case when the left child has a right child
			if(parent.leftChild != null){
				parent.height --;
				return parent;
			}
			
			return temp;
		}
		parent.size --;
		
		if(parent.height - parent.leftChild.height > 1){
			parent.height --;
		}
		
		return findAndRemoveMin(parent.leftChild);
	}
	
	/**
	 * finds an item in the lazy BST 
	 * returns null if it wasn't found
	 * 
	 * @param x the item to find
	 * @return the item that was found
	 */
	public T find(T x){

		LazyBSTNode<T> result = find(x, root);
		
		if( result == null){
			return null;
		}
		
		return result.data;		
		
	}
	
	/**
	 * internal method to find an item in a lazy BST
	 * @param x - item to find
	 * @param parent - node to look in
	 * @return - node that was found / null if not found
	 */
	private LazyBSTNode<T> find(T x, LazyBSTNode<T> parent){
		
		if (parent == null){
			return null;
		}
		
		//make a comparison between the item you're trying to find
		//and the item in the parent node that you're currently on
		
		int comparison = x.compareTo(parent.data);
		
		//as in insert and remove, decide what side of the tree the item
		//has to be in based on the value of this comparison
		
		// if comparison < 0, in left subtree
		// if comparison > 0, in right subtree
		// if comparison == 0, you've found the item
		
		if(comparison < 0 ){
			parent = find(x, parent.leftChild);
		}
		else if(comparison > 0 ){
			parent = find(x, parent.rightChild);
		}
		
		return parent;
		
		
	}

	/**
	 * finds the # of items between a lower and upper bound
	 * 
	 * does so in O (log(n)) time
	 * goes through two traversals of the lazyBST to find the upper and lower bounds
	 * taking log(n) time in each traversal, so 2 log(n) total
	 * manipulating the span instance variable along the way
	 * 
	 * @param x - lower bound
	 * @param y - upper bound
	 * @return the number of nodes between the lower and upper (inclusive)
	 */
	public int span(T x, T y){
		
		if(this.root != null){
			
			this.span = this.root.size;
			
			findX(x, this.root);
			
			findY(y, this.root);
			
			return this.span;
		}
		return -1;
	}

	/**
	 * internal method to traverse the lazy BST to find the lower bound of span
	 * @param x - lower bound of span
	 * @param parent - node to look at, becomes node of lower bound
	 */
	private void findX(T x, LazyBSTNode<T> parent){
		
		if(parent != null){
			
			int cmp = x.compareTo(parent.data);
			
			if(cmp != 0 && parent.leftChild == null && parent.rightChild == null){
				
				if(cmp <0){
					//do nothing 
				}
				else{
					
					this.span --;
					
				}
			}

			if(cmp < 0){
				
				findX(x,parent.leftChild);
				
			}
			
			else if(cmp > 0){
				
				if(parent.leftChild != null){
					
					this.span = this.span - parent.leftChild.size - 1;
					
				}
				else if(parent.leftChild == null && parent.rightChild != null){
					
					this.span --;
					
				}
				
				findX(x, parent.rightChild);
			}
			
			else if(cmp == 0){
				
				if(parent.leftChild != null){
					
					this.span = this.span - parent.leftChild.size;
					
				}
			}
		}
	}

	
	/**
	 * internal method to traverse the lazy BST to find the upper bound of span
	 * @param y - upper bound of span
	 * @param parent - node to look at, becomes node of upper bound
	 */
	private void findY(T y, LazyBSTNode<T> parent){
		
		if(parent != null){
			
			int cmp = y.compareTo(parent.data);
			
			if(cmp != 0 && parent.leftChild == null && parent.rightChild == null){
				
				if(cmp > 0){
					//do nothing
				}
				else{
					
					this.span --;
					
				}
			}
			
			if(cmp < 0){
				
				if(parent.rightChild != null){
					
					this.span = this.span - parent.rightChild.size -1 ;
				
				}
				else if(parent.rightChild == null && parent.leftChild != null){
					
					this.span --;
					
				}
				
				findY(y,parent.leftChild);
				
			}
			else if(cmp > 0){
				
				findY(y,parent.rightChild);
				
			}
			else if(cmp == 0){

				if(parent.rightChild != null){
					
					this.span = this.span - parent.rightChild.size;
					
				}
			}
		}
	}
	
	
	/**
	 * internal method to decide if a node is a rebalance candidate
	 * @param parent - node to check
	 * @return true if it can be rebalanced, false if not
	 */
	private boolean canRebalance(LazyBSTNode<T> parent){
		//can't be null
		if(parent == null){
			return false;
		}
		//height must be 4 or higher
		if(parent.height > 3){
			
			//size of subtree has to twice as large as the other
			// or one subtree can be null
			if(parent.leftChild != null && parent.rightChild != null){
				
				if(parent.leftChild.size >= 2 * parent.rightChild.size){
					
					return true;
				}
				else if(2 * parent.leftChild.size <= parent.rightChild.size){
				
					return true;
				}
				
			}
			else if(parent.leftChild == null && parent.rightChild != null){
				
				return true;
			}
			else if(parent.leftChild != null && parent.rightChild == null){
				
				return true;
			}
		}
		return false;
	}


	/**
	 * rebalances a node of a lazy BST
	 * does so in O(t) time
	 * the recursion visits every single item in the subtree
	 * @param parent - the node to rebalance
	 * @return the rebalanced node
	 */
	public LazyBSTNode<T> rebalance(LazyBSTNode<T> parent){
	    //System.out.println("Rebalancing....");
		//create a sorted arraylist in increasing order from the lazy bst

		ArrayList<T> list = new ArrayList<T>(parent.size);

		//populate the arrayList in t time
		toArrayList(parent, list);
		
		//use this now populated arrayList to reconstruct the lazy bst
		int start = 0;
		int end = list.size() - 1;
		int middle = (start + end) / 2;
		
		parent = new LazyBSTNode<T>(list.get(middle),null,null);
		//System.out.println("new root is: " + list.get(middle));
		
		rebalance(parent, list, start, middle - 1);
		rebalance(parent, list, middle + 1, end);
		
		return parent;
        
	}	
	

	/**
	 * internal method to rebalance a ndoe of a lazy BST
	 * 
	 * @param node - node to rebalance
	 * @param list - list of data
	 * @param start - start index
	 * @param end - end index
	 */
	private void rebalance(LazyBSTNode<T> node, ArrayList<T> list, int start, int end){
		
		if(start <= end){
			
			int middle = (start + end) / 2;
			int comparison = list.get(middle).compareTo(node.data);
			
			if(comparison < 0){
				node.leftChild = new LazyBSTNode<T>(list.get(middle),null,null);
				//System.out.println("added " + list.get(middle) + " to the left of " + node.data);
				
				
				//recurse with the left subtree / left sublist
				rebalance(node.leftChild, list, start, middle - 1);
				
				//recurse with the left subtree / right sublist
				rebalance(node.leftChild, list, middle + 1, end);
			}
			else if(comparison > 0){
				node.rightChild = new LazyBSTNode<T>(list.get(middle),null,null);
				//System.out.println("added " + list.get(middle) + " to the right of " + node.data);
				
				
				//recurse with the right subtree / left sublist
				rebalance(node.rightChild, list, start, middle - 1);
				
				//recurse with the right subtree / right sublist
				rebalance(node.rightChild,list,middle+1,end);
			}
			else{
				//just in case a duplicate has snuck into the data structure
				//do nothing
			}
			
			updateSizeAndHeight(node);
			
		}
		
	}


	/**
	 * simply prints out all the nodes of a lazy BST in increasing order
	 */
	public void dump(){
		//perform an in order traversal 
		print(root);
	}
	

	/**
	 * internal method to perform an in order traversal of the tree
	 * @param parent - node to look at
	 */
	private void print(LazyBSTNode<T> parent){
		if(parent != null){
			
			print(parent.leftChild);
			System.out.println(parent.data + " size: " + parent.size + " height: " + parent.height);
			print(parent.rightChild);
		}
	}


	/**
	 * internal method used by rebalance() to turn the tree into an arraylist
	 * takes O(t) time, has to go through the whole list
	 * @param parent - node to start at / recurse with
	 * @param list - arrayList to add to
	 */
	private void toArrayList(LazyBSTNode<T> parent, ArrayList<T> list){
		
		if(parent != null){
			
			toArrayList(parent.leftChild,list);
			
			list.add(parent.data);
			
			toArrayList(parent.rightChild,list);
		}
	}
	

	/**
	 * constant time method to update the size and height of a node in the tree
	 * @param node - the node to update
	 */
	private void updateSizeAndHeight(LazyBSTNode<T> node){
		
		// conditional statements to determine the height of the subtree in each recursive
		// step and then store it accordingly
		
		// first you have to check that there is a subtree either on the left or right
		if(node.leftChild != null || node.rightChild != null){
			
			// if you've entered into the first conditional then a subtree exists
			//
			// there are three cases:
			// 1 -> only a right subtree
			// 2 -> only a left subtree
			// 3 -> two subtrees
			
			// if there is only a right subtree, then the height of the current subtree
			// is one more than the height of the right subtree
			if(node.leftChild == null){
				node.height = 1 + node.rightChild.height;
				node.size = 1 + node.rightChild.size;
			}
			
			// if there is only a left subtree, then the height of the current subtree
			// is one more than the height of the left subtree
			else if(node.rightChild == null){
				node.height = 1 + node.leftChild.height;
				node.size = 1 + node.leftChild.size;
			}
			
			// if there are two subtrees, then the height of the current subtree
			// is one more than the height of the maximum height between the two subtrees
			else{
				node.height = 1 + Math.max(node.leftChild.height, node.rightChild.height);
				node.size = 1 + node.rightChild.size + node.leftChild.size;
			}
			
		}
	}	

	
	/**
	 * 
	 * LazyBSTNode handles the node and the left / right subtree
	 * written as a private class so LazyBST can access fields directly
	 * only LazyBST needs access to LazyBSTNode
	 * 
	 * 
	 * @author Michael Neary <mneary1@umbc.edu>
	 * @project CMSC 341 - Fall 2013 - Project 2
	 * @section 01
	 * @version Oct 23, 2013
	 */
	private class LazyBSTNode< T extends Comparable<? super T> > {

		private LazyBSTNode<T> leftChild;
		private LazyBSTNode<T> rightChild;
		private T data;
		private int size;
		private int height;
		
		/**
		 * Constructor 
		 * @param data
		 * @param left
		 * @param right
		 */
		public LazyBSTNode(T data, LazyBSTNode<T> left, LazyBSTNode<T> right){
				
			leftChild = left;
			rightChild = right;
			this.data = data;
			size = 1;
			height = 0;
			
		}
	}
}
