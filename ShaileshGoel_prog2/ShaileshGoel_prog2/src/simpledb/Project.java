package simpledb;
import java.util.*;
import java.io.*;

/**
 * Project is an operator that implements a relational projection.
 */
public class Project extends AbstractDbIterator {
    DbIterator child;
    TupleDesc td;
    ArrayList<Integer> outFieldIds;

    /**
     * Constructor accepts a child
     * operator to read tuples to apply projection to and a list of fields
     * in output tuple
     *
     * @param fieldList The ids of the fields child's tupleDesc to project out
     * @param typesList the types of the fields in the final projection
     * @param child The child operator
     */
    public Project(ArrayList<Integer> fieldList, ArrayList<Type> typesList,  DbIterator child) {
        this.child = child;
        outFieldIds = fieldList;
        td= new TupleDesc(typesList.toArray(new Type[0]));
    }

    public TupleDesc getTupleDesc() {
        return td;
    }

    public void open()
        throws DbException, NoSuchElementException, TransactionAbortedException, IOException {
        child.open();
    }

    public void close() {
        child.close();
    }

    public void rewind() throws DbException, TransactionAbortedException, IOException {
        child.rewind();
    }

    /**
     * AbstractDbIterator.readNext implementation.
     * Iterates over tuples from the child operator, projecting out the fields from the tuple
     * @return The next tuple, or null if there are no more tuples
     */
    protected Tuple readNext()
        throws NoSuchElementException, TransactionAbortedException, DbException {
        try{
	    while (child.hasNext()) {
		Tuple t = child.next();
		Tuple newTuple = new Tuple(td);
		for (int i = 0; i < td.numFields(); i++) {
		    newTuple.setField(i, t.getField(outFieldIds.get(i)));
		}
		return newTuple;
	    }
	 } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
