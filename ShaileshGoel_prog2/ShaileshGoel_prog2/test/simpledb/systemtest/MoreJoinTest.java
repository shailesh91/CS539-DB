package simpledb.systemtest;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import simpledb.*;


       

public class MoreJoinTest {
    private static final int COLUMNS = 4;
    //runs the join but does not check the results (to save memory)
    public void performSpecificJoin(File file1, File file2, int joinAlgo)
            throws IOException, DbException, TransactionAbortedException {
        // Read the two tables
        //ArrayList<ArrayList<Integer>> t1Tuples = new ArrayList<ArrayList<Integer>>();

	//	Type[] types = new Type[COLUMNS];
	//	for (int i=0; i<COLUMNS; i++)
	    //  types[i]=Type.INT_TYPE;

	//TupleDesc td = new TupleDesc(types);
	
        //HeapFile hf = openHeapFile(cols, f);
	// HeapPageId pid = new HeapPageId(hf.id(), 0);
    	

	HeapFile table1 = Utility.openHeapFile(COLUMNS,file1);
	System.out.println("JoinTest Relation 1 is  "+ table1.numPages() +"  pages");


        //ArrayList<ArrayList<Integer>> t2Tuples = new ArrayList<ArrayList<Integer>>();
	
	HeapFile table2 = Utility.openHeapFile(COLUMNS,file2);
      	System.out.println("JoinTest Relation 2 is  "+ table2.numPages() +"  pages");
      	
      //if(joinAlgo ==0) joinAlgo = 1;

        // Begin the join
        TransactionId tid = new TransactionId();
        SeqScan ss1 = new SeqScan(tid, table1.id(), "");
        SeqScan ss2 = new SeqScan(tid, table2.id(), "");
        JoinPredicate p = new JoinPredicate(0, Predicate.Op.EQUALS, 0);
        Join joinOp = new Join(p, ss1, ss2);
        joinOp.setJoinAlgorithm(joinAlgo);

        // create and drop the join results
        SystemTestUtil.countJoinTuples(joinOp);

        joinOp.close();
        Database.getBufferPool().transactionComplete(tid);
        Database.getBufferPool().flushAllPages();
		System.out.println("Outer Relation: "+ss1.getPagesRead()+" pages read");
		System.out.println("Inner Relation: "+ss2.getPagesRead()+" pages read");
		System.out.println("Number of Joined Tuples: "+joinOp.getNumMatches());
    }
    @Test public void testSmallSNL()
          throws IOException, DbException, TransactionAbortedException {
    	System.out.println("--------------------------------------------");
    	System.out.println("JoinTest - SNL on small relations");
	File file1 = new File("./test/simpledb/systemtest/testoutput/smallunsortedtable1.dat");
	File file2 = new File("./test/simpledb/systemtest/testoutput/smallunsortedtable2.dat");
	performSpecificJoin(file1,file2,0);
    }
    @Test public void testMediumSNL()
          throws IOException, DbException, TransactionAbortedException {
    	System.out.println("--------------------------------------------");
    	System.out.println("JoinTest - SNL on medium relations");
	File file1 = new File("./test/simpledb/systemtest/testoutput/mediumunsortedtable1.dat");
	File file2 = new File("./test/simpledb/systemtest/testoutput/mediumunsortedtable2.dat");
	performSpecificJoin(file1,file2,0);
    }
    @Test public void testLargeSNL()
          throws IOException, DbException, TransactionAbortedException {
    	System.out.println("--------------------------------------------");
    	System.out.println("JoinTest - SNL on large relations");
	File file1 = new File("./test/simpledb/systemtest/testoutput/largeunsortedtable1.dat");
	File file2 = new File("./test/simpledb/systemtest/testoutput/largeunsortedtable2.dat");
	performSpecificJoin(file1,file2,0);
    }
    @Test public void testSmallSNLonSorted()
          throws IOException, DbException, TransactionAbortedException {
    	System.out.println("--------------------------------------------");
    	System.out.println("JoinTest - SNL on small sorted relations");
	File file1 = new File("./test/simpledb/systemtest/testoutput/smallsortedtable1.dat");
	File file2 = new File("./test/simpledb/systemtest/testoutput/smallsortedtable2.dat");
	performSpecificJoin(file1,file2,0);
    }
    @Test public void testMediumSNLonSorted()
          throws IOException, DbException, TransactionAbortedException {
    	System.out.println("--------------------------------------------");
    	System.out.println("JoinTest - SNL on medium sorted relations");
	File file1 = new File("./test/simpledb/systemtest/testoutput/mediumsortedtable1.dat");
	File file2 = new File("./test/simpledb/systemtest/testoutput/mediumsortedtable2.dat");
	performSpecificJoin(file1,file2,0);
    }
    @Test public void testLargeSNLonSorted()
          throws IOException, DbException, TransactionAbortedException {
    	System.out.println("--------------------------------------------");
    	System.out.println("JoinTest - SNL on large sortedrelations");
	File file1 = new File("./test/simpledb/systemtest/testoutput/largesortedtable1.dat");
	File file2 = new File("./test/simpledb/systemtest/testoutput/largesortedtable2.dat");
	performSpecificJoin(file1,file2,0);
    }
    @Test public void testSmallSMJ()
          throws IOException, DbException, TransactionAbortedException {
    	System.out.println("--------------------------------------------");
    	System.out.println("JoinTest - SMJ on small sorted relations");
	File file1 = new File("./test/simpledb/systemtest/testoutput/smallsortedtable1.dat");
	File file2 = new File("./test/simpledb/systemtest/testoutput/smallsortedtable2.dat");
	performSpecificJoin(file1,file2,3);
    }
    @Test public void testMediumSMJ()
          throws IOException, DbException, TransactionAbortedException {
    	System.out.println("--------------------------------------------");
    	System.out.println("JoinTest - SMJ on medium sorted relations");
	File file1 = new File("./test/simpledb/systemtest/testoutput/mediumsortedtable1.dat");
	File file2 = new File("./test/simpledb/systemtest/testoutput/mediumsortedtable2.dat");
	performSpecificJoin(file1,file2,3);
    }
    @Test public void testLargeSMJ()
          throws IOException, DbException, TransactionAbortedException {
    	System.out.println("--------------------------------------------");
    	System.out.println("JoinTest - SMJ on large sorted relations");
	File file1 = new File("./test/simpledb/systemtest/testoutput/largesortedtable1.dat");
	File file2 = new File("./test/simpledb/systemtest/testoutput/largesortedtable2.dat");
	performSpecificJoin(file1,file2,3);
    }
    

    /** Make test compatible with older version of ant. */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(MoreJoinTest.class);
    }
}
