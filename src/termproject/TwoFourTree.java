package termproject;

/**
 * Title:        Term Project 2-4 Trees
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
public class TwoFourTree
        implements Dictionary {

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
        if( !treeComp.isComparable(key) ) {
            //throw exception
        }

        // If the tree is empty the element is not in the tree
        if( isEmpty() ) {
            return null;
        }

        TFNode activeNode = root();
        int activeIndex = FFGTET( activeNode, key );
        Item activeItem = activeNode.getItem( activeIndex );

        while( activeItem.key() != key ) {
            if( activeNode.getChild(activeIndex) instanceof TFNode ) {
                activeNode = activeNode.getChild( activeIndex );
                activeIndex = FFGTET( activeNode, key );
                activeItem = activeNode.getItem( activeIndex );
            }
            else {
                return null;
            }
        }

        return activeItem.element();
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

        TFNode insertNode = root();
        int insertIndex = FFGTET( root(), key );
        while( insertNode.getChild(0) instanceof TFNode) {

        }

        /*
	TFNode insertLocation = FFGTET( root(), key );

		if( insertLocation.getMaxItems() <= insertLocation.getNumItems() ) {
			for( int i = 0; i < insertLocation.getNumItems(); i++ ) {
				Item item = insertLocation.getItem( i );

				if( treeComp.isEqual( key, item.key() )) {

				}
				else if( treeComp.isLessThan( key, item.key() )) {

				}
				else if( treeComp.isGreaterThan( key, item.key() )) {

				}
			}
	}
	*/
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


    private int FFGTET( TFNode activeNode, Object key ) {
        // If nothing is in the node, index 0 is greater
        if( activeNode.getNumItems() == 0) {
            return 0;
        }

        // Loop to search through each item in the node
        for( int i = 0; i < activeNode.getNumItems(); i++ ) {
            Item item = activeNode.getItem( i );
            // If an items key is greater or larger, return that index
            if( treeComp.isGreaterThanOrEqualTo( key, item.key() )) {
                return i;
            }
        }

        // If nothing in the tree is larger, return the last index
        return activeNode.getNumItems();
    }

    /* MAJOR CHANGE!
    This may need to return an index instead of a node

    // Find First Greater Than or Equal To
    private TFNode FFGTET( TFNode activeNode, Object key ) {
	if( activeNode.getNumItems() == 0 ) {
            return activeNode;
        }

        // Boolean for the case of the loop not finding anything
        Boolean foundItem = false;

        // For loop looks for something in the node greater than the key
        for( int i = 0; i < activeNode.getNumItems(); i++ ) {
            Item item = activeNode.getItem( i );

            if( treeComp.isEqual( key, item.key() )) {
                foundItem = true;
                break;
            }
            // If it's less than should just continue until the first greater than
            else if( treeComp.isGreaterThan( key, item.key() )) {
                foundItem = true;
                activeNode = FFGTET( activeNode.getChild( i ), key );
                break;
            }
        }
        // If the for loop doesn't find something larger in the node, need to go
        // to the very last child, which is the number of items in the node.
        if(!foundItem) {
            if( activeNode.getChild( activeNode.getNumItems() ) instanceof TFNode) {
                activeNode = FFGTET( activeNode.getChild( activeNode.getNumItems() ), key );
            }
            // I'm not sure, but I'm guessing if nothing in the tree is larger
            // need to return null.
            else {
                return null;
            }
        }

	return activeNode;
    }
    */
}
