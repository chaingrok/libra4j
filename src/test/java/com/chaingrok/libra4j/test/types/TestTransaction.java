package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Code;
import com.chaingrok.libra4j.types.Program;
import com.chaingrok.libra4j.types.Transaction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTransaction extends TestClass {
	
	@Test
	public void test001_newInstance() {
		Transaction transaction = new Transaction();
		assertNull(transaction.getVersion());
		assertNull(transaction.getSequenceNumber());
		assertNull(transaction.getSenderPublicKey());
		assertNull(transaction.getSignature());
		assertNull(transaction.getMaxGasAmount());
		assertNull(transaction.getExpirationTime());
		assertNull(transaction.getGasUnitPrice());
		assertNull(transaction.getGasUsed());
		assertNull(transaction.getProgram());
		assertNull(transaction.getTxnInfoSerializedSize());
		assertNull(transaction.getSignedTxnSerializedSize());
		assertNull(transaction.getSignedTransactionHash());
		assertNull(transaction.getEventRootHash());
		assertNull(transaction.getStateRootHash());
		assertNull(transaction.getFullTxtBytes());
		assertNull(transaction.getRawTxnBytes());
		assertNull(transaction.getEventsList());
	}
	
	@Test
	public void test002_checkType() {
		Transaction transaction = new Transaction();
		Program program= new Program();
		program.setCode(Code.MINT);
		transaction.setProgram(program);
		assertEquals(Transaction.Type.MINT,Transaction.Type.get(transaction));
		//
		byte[] bytes = {0x00};
		Code code = new Code(bytes);
		program.setCode(code);
		assertEquals(Transaction.Type.UNKNOWN,Transaction.Type.get(transaction));
		assertTrue(Libra4jLog.hasLogs());
		Libra4jLog.purgeLogs();
	}

}
