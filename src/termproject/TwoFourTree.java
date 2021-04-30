package termproject;

/**
 * Title:        Term Project 2-4 Trees
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author   David Cox and Joshua Stevens
 * @version 1.0
 */
public class TwoFourTree implements Dictionary {

    private Comparator treeComp;
    private int size = 0;
    private TFNode treeRoot = null;

    public TwoFourTree(Comparator comp) {
        treeComp = comp;
    }

    private TFNode root() {
        return treeRoot;
    }

    private void setRoot(TFNode root) {
        treeRoot = root;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Searches dictionary to determine if key is present
     * @param key to be searched for
     * @return object corresponding to key; null if not found
     */
    public Object findElement(Object key) {
        // Checks for a valid key
        if( !treeComp.isComparable( key )) {
            //throw new InvalidIntegerException();
        }

        // If the tree is empty the element is not in the tree
        if( isEmpty() ) {
            return null;
        }

        // Active node is the only node of the tree the element could be in
        TFNode activeNode = FFGTET( root(), key );

        // Loop to search through each item in the node
        for( int i = 0; i < activeNode.getNumItems(); i++ ) {
            // Current item in the node
            Item activeItem = activeNode.getItem( i );

            // If the keys are equal, return that element
            if(treeComp.isEqual( activeItem.key(), key )) {
                return activeItem.element();
            }
        }

        // If matching keys were not found in that node, it's not in the tree
        return null;
    }

    /**
     * Inserts provided element into the Dictionary
     * @param key of object to be inserted
     * @param element to be inserted
     */
    public void insertElement(Object key, Object element) {

        Item newItem = new Item( key, element );

        if( root() == null ) {
            TFNode newNode = new TFNode();
            newNode.addItem( 0, newItem );

            setRoot( newNode );
            return;
        }
		
		TFNode insertNode = FFGTET( root(), key );
		
		for( int i = 0; i < insertNode.getMaxItems(); i++ ) {
			if( treeComp.isLessThan(key, insertNode.getItem( i ) )) {
				insertNode.insertItem( i, newItem );
				break;
			}

			else if( i == ( insertNode.getMaxItems() - 1 ) ) {
				insertNode.insertItem( i + 1 , newItem);
			}
		}
		
		if( insertNode.getNumItems() > 3 ) {
			expandTree( insertNode );
		}
       
    }

    /**
     * Searches dictionary to determine if key is present, then
     * removes and returns corresponding object
     * @param key of data to be removed
     * @return object corresponding to key
     * @exception ElementNotFoundException if the key is not in dictionary
     */
    public Object removeElement(Object key) throws ElementNotFoundException {
        return null;
    }

    public static void main(String[] args) {
        Comparator myComp = new IntegerComparator();
        TwoFourTree myTree = new TwoFourTree(myComp);

        Integer myInt1 = 47;
        myTree.insertElement(myInt1, myInt1);
        Integer myInt2 = 83;
        myTree.insertElement(myInt2, myInt2);
        Integer myInt3 = 22;
        myTree.insertElement(myInt3, myInt3);
        Integer myInt4 = 16;
        myTree.insertElement(myInt4, myInt4);
        Integer myInt5 = 49;
        myTree.insertElement(myInt5, myInt5);
        Integer myInt6 = 100;
        myTree.insertElement(myInt6, myInt6);
        Integer myInt7 = 38;
        myTree.insertElement(myInt7, myInt7);
        Integer myInt8 = 3;
        myTree.insertElement(myInt8, myInt8);
        Integer myInt9 = 53;
        myTree.insertElement(myInt9, myInt9);
        Integer myInt10 = 66;
        myTree.insertElement(myInt10, myInt10);
        Integer myInt11 = 19;
        myTree.insertElement(myInt11, myInt11);
        Integer myInt12 = 23;
        myTree.insertElement(myInt12, myInt12);
        Integer myInt13 = 24;
        myTree.insertElement(myInt13, myInt13);
        Integer myInt14 = 88;
        myTree.insertElement(myInt14, myInt14);
        Integer myInt15 = 1;
        myTree.insertElement(myInt15, myInt15);
        Integer myInt16 = 97;
        myTree.insertElement(myInt16, myInt16);
        Integer myInt17 = 94;
        myTree.insertElement(myInt17, myInt17);
        Integer myInt18 = 35;
        myTree.insertElement(myInt18, myInt18);
        Integer myInt19 = 51;
        myTree.insertElement(myInt19, myInt19);

        myTree.printAllElements();
        System.out.println("done");

        myTree = new TwoFourTree(myComp);
        final int TEST_SIZE = 10000;

        for (int i = 0; i < TEST_SIZE; i++) {
            myTree.insertElement(i, i);
            //          myTree.printAllElements();
            //         myTree.checkTree();
        }
		

        System.out.println("removing");

        for (int i = 0; i < TEST_SIZE; i++) {
            int out = (Integer) myTree.removeElement(i);
            if (out != i) {
                throw new TwoFourTreeException("main: wrong element removed");
            }
            if (i > TEST_SIZE - 15) {
                myTree.printAllElements();
            }
        }
        System.out.println("done");
    }

    public void printAllElements() {
        int indent = 0;
        if (root() == null) {
            System.out.println("The tree is empty");
        }
        else {
            printTree(root(), indent);
        }
    }

    public void printTree(TFNode start, int indent) {
        if (start == null) {
            return;
        }
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
        printTFNode(start);
        indent += 4;
        int numChildren = start.getNumItems() + 1;
        for (int i = 0; i < numChildren; i++) {
            printTree(start.getChild(i), indent);
        }
    }

    public void printTFNode(TFNode node) {
        int numItems = node.getNumItems();
        for (int i = 0; i < numItems; i++) {
            System.out.print(((Item) node.getItem(i)).element() + " ");
        }
        System.out.println();
    }

    // checks if tree is properly hooked up, i.e., children point to parents
    public void checkTree() {
        checkTreeFromNode(treeRoot);
    }

    private void checkTreeFromNode(TFNode start) {
        if (start == null) {
            return;
        }

        if (start.getParent() != null) {
            TFNode parent = start.getParent();
            int childIndex = 0;
            for (childIndex = 0; childIndex <= parent.getNumItems(); childIndex++) {
                if (parent.getChild(childIndex) == start) {
                    break;
                }
            }
            // if child wasn't found, print problem
            if (childIndex > parent.getNumItems()) {
                System.out.println("Child to parent confusion");
                printTFNode(start);
            }
        }

        if (start.getChild(0) != null) {
            for (int childIndex = 0; childIndex <= start.getNumItems(); childIndex++) {
                if (start.getChild(childIndex) == null) {
                    System.out.println("Mixed null and non-null children");
                    printTFNode(start);
                }
                else {
                    if (start.getChild(childIndex).getParent() != start) {
                        System.out.println("Parent to child confusion");
                        printTFNode(start);
                    }
                    for (int i = childIndex - 1; i >= 0; i--) {
                        if (start.getChild(i) == start.getChild(childIndex)) {
                            System.out.println("Duplicate children of node");
                            printTFNode(start);
                        }
                    }
                }
            }
        }

        int numChildren = start.getNumItems() + 1;
        for (int childIndex = 0; childIndex < numChildren; childIndex++) {
            checkTreeFromNode(start.getChild(childIndex));
        }
    }

    // Find Final Greater Than or Equal To
    // This helper method returns the node that contains a key still greater
    // than the given key, but closest in value to the value of the given key.
    // If the method finds a node with a key equal to the given key, it returns
    // that node instead as equal to is as close as it can be.
    private TFNode FFGTET( TFNode activeNode, Object key ) {
        // If the given node is null, return null
		if( activeNode == null ) {
			return null;
		}
		// If the given node has no items, that is the only node to return
		if( activeNode.getNumItems() == 0 ) {
			return activeNode;
		}

		// For loop looks for something in the node greater than the key
		for( int i = 0; i < activeNode.getNumItems(); i++ ) {
			Item item = activeNode.getItem( i );

			// If it finds a key with equal value, break to return this node
			if( treeComp.isEqual( key, item.key() )) {
                break;
            }
			// If the node item is greater than the key, need to look at its child
			else if( treeComp.isGreaterThan( item.key(), key )) {
			    if( activeNode.getChild( i ) instanceof TFNode ) {
                    activeNode = FFGTET(activeNode.getChild( i ), key);
                }
				break;
			}
			// Usually this case doesn't matter, only if on the last item
			else if( treeComp.isLessThan( item.key(), key )) {
			    // If i is equal to 1 less than the number of items, the last
                // item has been searched and the node doesn't have any items
                // greater. So need to go to the last child.
			    if( i == ( activeNode.getNumItems() - 1 )) {
                    if( activeNode.getChild( i + 1 ) instanceof TFNode ) {
                        activeNode = FFGTET(activeNode.getChild( i + 1 ), key);
                    }
                }
			}
		}

		return activeNode;
	}
	
	private void expandTree( TFNode overflow ) {
		TFNode right = new TFNode();
		TFNode left = new TFNode();
		
		TFNode parent = overflow.getParent();
		
		if( parent == null ) {
			parent = new TFNode();
			setRoot( parent );
		}
		
		Item parentItem = overflow.removeItem(2);
		int parentItemIndex = 0;
		
		left.insertItem(0, overflow.removeItem(0) );
		left.insertItem(1, overflow.removeItem(0) );
		left.setParent( parent );
		
		right.insertItem(0, overflow.removeItem(0) );

		right.setParent( parent );
		
		if( parent.getNumItems() == 0 ) {
			parent.insertItem( 0, parentItem );
		}
		
		for( int i = 0; i < parent.getNumItems(); i++ ) {
			if( treeComp.isLessThan( parentItem.key(), parent.getItem( i ).key() )) {

				parent.insertItem( i, parentItem );
				parentItemIndex = i;
				break;
			}
			else if( i == ( parent.getMaxItems() - 1 ) ) {
				parent.insertItem( i + 1 , parentItem );
				parentItemIndex = i + 1;
				break;
			}
		}
		
		
		parent.setChild( parentItemIndex, left );
		parent.setChild( parentItemIndex + 1, right );
		
		if( parent.getNumItems() > 3 ) {
			expandTree( parent );
		}
	}
}
