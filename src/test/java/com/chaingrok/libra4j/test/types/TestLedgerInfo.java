package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Hash;
import com.chaingrok.libra4j.types.LedgerInfo;
import com.chaingrok.libra4j.types.Validator;
import com.chaingrok.libra4j.types.ValidatorId;
import com.google.protobuf.ByteString;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLedgerInfo extends TestClass {
	
	@Test
	public void test001NewInstance() {
		LedgerInfo ledgerInfo = new LedgerInfo();
		//
		byte[] consensusBlockIdBytes = Utils.getByteArray(Hash.BYTE_LENGTH,0x00);
		Hash consensusBlockId = new Hash(consensusBlockIdBytes);
		ledgerInfo.setConsensusBlockId(consensusBlockId);
		assertSame(consensusBlockId,ledgerInfo.getConsensusBlockId());
		//
		byte[] consensusDataHashBytes = Utils.getByteArray(Hash.BYTE_LENGTH,0x01);
		Hash consensusDataHash = new Hash(consensusDataHashBytes);
		ledgerInfo.setConsensusDataHash(consensusDataHash);
		assertSame(consensusDataHash,ledgerInfo.getConsensusDataHash());
		//
		byte[] transactionAccumulatorHashBytes = Utils.getByteArray(Hash.BYTE_LENGTH,0x02);
		Hash transactionAccumulatorHash = new Hash(transactionAccumulatorHashBytes);
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
		byte[] validatorIdBytes = Utils.getByteArray(ValidatorId.BYTE_LENGTH,0x55);
		ValidatorId validatorId = new ValidatorId(ByteString.copyFrom(validatorIdBytes));
		Validator validator = new Validator();
		validator.setValidatorId(validatorId);
		ArrayList<Validator> validators = new ArrayList<Validator>();
		validators.add(validator);
		ledgerInfo.setValidators(validators);
		assertSame(validators,ledgerInfo.getValidators());
		//
		String string = ledgerInfo.toString();
		assertTrue(string.contains(Utils.byteArrayToHexString(consensusBlockIdBytes)));
		assertTrue(string.contains(Utils.byteArrayToHexString(consensusDataHashBytes)));
		assertTrue(string.contains(Utils.byteArrayToHexString(transactionAccumulatorHashBytes)));
		assertTrue(string.contains(epochNum + ""));
		assertTrue(string.contains(timestampUsecs + ""));
		assertTrue(string.contains(version + ""));
		assertTrue(string.contains(Utils.byteArrayToHexString(validatorIdBytes)));
	}

}
