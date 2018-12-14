/**************************************************************
 Purpose/Description: 
 Problem 2
 A. Returns the sum of all keys in the tree.
 B. Deletes the minimum element in the tree.
 C. Prints the nodes in increasing order.
 D. Prints the nodes in post-order traversal.
 E. Pure answer found below D as a /* Text */ //comment..
/**
 Author’s Panther ID: 4999406
 Certification:
 I hereby certify that this work is my own and none of it is the work of
 any other person.
 **************************************************************/ 

public class Problem_2 
{
	public static class BinarySearchTreeNode
	{
		BinarySearchTreeNode(int _key)
		{
			key = _key;
		}
		
		public int key;
		
		public BinarySearchTreeNode left ;
		public BinarySearchTreeNode right;
	}
	
	public static class BinarySearchTree
	{
		//Public
		public BinarySearchTree()
		{
			root = null;
		}
		
		/** Inserts a key into the tree. */ 
		public void insert(int key)
		{
			root = recursiveNodeInsertion(key, root);
		}
		
		/** Deletes node with given key. */
		public void delete(int key)
		{
			root = recursiveNodeDeletion(key, root);
		}
		
		/** Finds if a specified key exists in the tree. */ 
		public boolean find(int key)
		{
			if      (root.key == key)
			{
				return true;
			}
			else if (root.key > key)
			{
				if (recursiveNodeSearch(key, root.left).key == key)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				if (recursiveNodeSearch(key, root.right).key == key)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		
		/**************************************Problem 2 Implementation*****************************************/
		//A
		public int keySum()
		{
			return recursiveSum(root);   //Returns the result of recursiveSum starting from the root.
		}
		
		private int recursiveSum(BinarySearchTreeNode _focalNode)
		{
			if (_focalNode == null)   
			{
				return 0;   //If the current node is null return 0.
			}
			else
			{
				return (_focalNode.key + recursiveSum(_focalNode.left) + recursiveSum(_focalNode.right));   //Add to the current key the sum of its left and right children.
			}
		}
		
		//B
		public void deleteMin()
		{
			delete(findMin(root));   //Use the delete function the smallest key found by findMin.
		}
		
		/** Its a duplication of 'minKey', I placed it here for convenience */
		private int findMin(BinarySearchTreeNode _parentNode)
		{
			int minKey = _parentNode.key;   //Set minimum to key of parent for now.
			
			while (_parentNode.left != null)
			{
				minKey = _parentNode.left.key;  //While the lesser side still has a key set minimum key to it.
				
				_parentNode = _parentNode.left;   //Set parent to its lesser child.
			}
			
			return minKey;
		}
		
		//C
		public void printTree()
		{
			System.out.print("[");
			
			recursiveInOrderPrint(root);   //Run the recursive in-order printer to populate the set representing the tree in-order traversal.
			
			System.out.println("]");
		}
		
		private void recursiveInOrderPrint(BinarySearchTreeNode _focalNode)
		{
			if (_focalNode == null)   //If the current node is null, do not print.
			{
				return;
			}
			
			recursiveInOrderPrint(_focalNode.left);   //Print every node from the lesser children.
			
			System.out.print(_focalNode.key + " "); //Print current node.
			
			recursiveInOrderPrint(_focalNode.right);   //Print every node from the greater children.
		}
		
		//D
		public void printPostorder()
		{
			System.out.print("[");
			
			recursivePostOrderPrint(root);   //Run the recursive post-order printer to populate the set representing the tree post-order traversal.
			
			System.out.println("]");
		}
		
		private void recursivePostOrderPrint(BinarySearchTreeNode _focalNode)
		{
			if (_focalNode == null)   //If the focal node is null do not print.
			{
				return;
			}
			
			recursivePostOrderPrint(_focalNode.left);    //Print all the lesser children.
			
			recursivePostOrderPrint(_focalNode.right);   //Print all the greater children.
			
			System.out.print(_focalNode.key + " ");   //Print current node.
		}
		
		//E
		/*
		 * Leaf: /
		 * B: Set of keys in path P of /.
		 * A: Set of keys to the left  of the path P.
		 * C: Set of keys to the right of the path P.
		 * 
		 * Given any element a in A; b in B; c in C;
		 * a <= b <= c...

		 * Left in BST is lesser. Right in BST is greater. Center is mid value.
		 * 
		 * Test Sets:
		 * Left  : A path [22, 19,  4,  8]
		 * Center: P path [20, 29, 23, 45]
		 * Right : C path [31, 34        ]
		 * 
		 * In the above case, the first element of B: 20 is less than the first element in A: 22. Thus a is not <= b for all cases.
		 * As for the C path, the first and second elements: 31, 34, are both less than the last element of the B set: 45. Thus b is not <= c for all cases.
		 * 
		 * Proof by contradiction thus shows that the claim a <= b <= c does not hold.
		 */
		
		/**************************************End of Problem 2 Implementation*********************************/
		
		//Private
		/** Finds the minimum value key from the parent node down. */
		private int minKey(BinarySearchTreeNode _parentNode)
		{
			int minKey = _parentNode.key;
			
			while (_parentNode.left != null)
			{
				minKey = _parentNode.left.key;
				
				_parentNode = _parentNode.left;
			}
			
			return minKey;
		}
		
		/** A recursive method for locating a node of the given key. Used by find method as a aid to look through the tree. */ 
		private BinarySearchTreeNode recursiveNodeSearch(int _key, BinarySearchTreeNode _focalNode)
		{
			if (_focalNode == null)
			{
				return _focalNode;   //Current node is null, cannot look further for node with key.
			}
			else
			{
				if     (_focalNode.key == _key)
				{
					return _focalNode;   //Node with key found.
				}
				else if (_focalNode.key > _key)   //If focal is greater than the key, then the key may be found in the lesser children.
				{
					return recursiveNodeSearch(_key, _focalNode.left);   //Go further down to the left to see if key is there.
				}
				else   //If focal is less than, then the key may be found in the greater children.
				{
					return recursiveNodeSearch(_key, _focalNode.right);   //Go further down to the right to see if key is there.
				}
			}
		}
		
		/** A recursive method for inserting a node of the given key. Used by insert method as an aid to go through the tree. */
		private BinarySearchTreeNode recursiveNodeInsertion(int _key, BinarySearchTreeNode _focalNode)
		{
			if      (_focalNode == null)
			{
				return new BinarySearchTreeNode(_key);   //Current node is null, can populate this space with a new node with key.
			}
			else if (_focalNode.key < _key)
			{
				_focalNode.right = recursiveNodeInsertion(_key, _focalNode.right);   //Go down to the right to find a proper position.
			}
			else if (_focalNode.key > _key)
			{
				_focalNode.left = recursiveNodeInsertion(_key, _focalNode.left);   //Go down to the left to find a proper position.
			}
			
			return _focalNode;   //Key is within current node. No need to insert.
		}
		
		/** A recursive method for deleting a node of the given key. Used by the delete method as an aid to go through the tree. */
		private BinarySearchTreeNode recursiveNodeDeletion(int _key, BinarySearchTreeNode _focalNode)
		{
			if      (_focalNode == null)
			{
				return _focalNode;
			}
			else if (_focalNode.key > _key)
			{
				_focalNode.left = recursiveNodeDeletion(_key, _focalNode.left);
			}
			else if (_focalNode.key < _key)
			{
				_focalNode.right = recursiveNodeDeletion(_key, _focalNode.right);
			}
			else
			{
				if      (_focalNode.left == null)
				{
					return _focalNode.right;
				}
				else if (_focalNode.right == null)
				{
					return _focalNode.left;
				}
				
				_focalNode.key   = minKey               (      _focalNode.right);
				_focalNode.right = recursiveNodeDeletion(_key, _focalNode.right);
			}
			
			return _focalNode;
		}
		
		//Declares
		private BinarySearchTreeNode root;
	}
	
	public static void main(String[] args)
	{
		BinarySearchTree bstTest = new BinarySearchTree();
		
		bstTest.insert(1);
		bstTest.insert(2);
		bstTest.insert(3);
		bstTest.insert(4);
		bstTest.insert(5);
		bstTest.insert(6);
		bstTest.insert(7);
		bstTest.insert(8);
		bstTest.insert(9);
		
		bstTest.printTree();
		
		bstTest.delete(1);
		
		System.out.println(bstTest.find(9));
		
		bstTest.printTree();
		
		bstTest.deleteMin();
		
		bstTest.printTree();
		
		bstTest.keySum();
		
		bstTest.printTree	  ();
		bstTest.printPostorder();
	}
}