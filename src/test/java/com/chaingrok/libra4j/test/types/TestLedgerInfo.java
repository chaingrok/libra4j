package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Hash;
import com.chaingrok.libra4j.types.LedgerInfo;
import com.chaingrok.libra4j.types.Validator;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLedgerInfo extends TestClass {
	
	@Test
	public void test001_newInstance() {
		LedgerInfo ledgerInfo = new LedgerInfo();
		//
		Hash consensusBlockId = new Hash(Utils.getByteArray(Hash.BYTE_LENGTH));
		ledgerInfo.setConsensusBlockId(consensusBlockId);
		assertSame(consensusBlockId,ledgerInfo.getConsensusBlockId());
		//
		Hash consensusDataHash = new Hash(Utils.getByteArray(Hash.BYTE_LENGTH,0x01));
		ledgerInfo.setConsensusDataHash(consensusDataHash);
		assertSame(consensusDataHash,ledgerInfo.getConsensusDataHash());
		//
		Hash transactionAccumulatorHash = new Hash(Utils.getByteArray(Hash.BYTE_LENGTH,0x02));
		ledgerInfo.setTransactionAccumulatorHash(transactionAccumulatorHash);
		assertSame(transactionAccumulatorHash,ledgerInfo.getTransactionAccumulatorHash());
		//
		Long epochNum = new Long(123);
		ledgerInfo.setEpochNum(epochNum);
		assertSame(epochNum,ledgerInfo.getEpochNum());
		//
		Long timestampUsecs= new Long(456);
		ledgerInfo.setTimestampUsecs(timestampUsecs);
		assertSame(timestampUsecs,ledgerInfo.getTimestampUsecs());
		//
		Long version = new Long(789);
		ledgerInfo.setVersion(version);
		assertSame(version,ledgerInfo.getVersion());
		//
		ArrayList<Validator> validators = new ArrayList<Validator>();
		ledgerInfo.setValidators(validators);
		assertSame(validators,ledgerInfo.getValidators());
	}

}
