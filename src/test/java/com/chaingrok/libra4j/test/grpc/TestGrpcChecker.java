package com.chaingrok.libra4j.test.grpc;

import static org.junit.Assert.assertFalse;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.grpc.GrpcChecker;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Hash;
import com.google.protobuf.ByteString;

import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGrpcChecker extends TestClass {
	
	@Test
	public void test000_checkLedgerInfo() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		long version = 123L;
		long timestampUsecs = 456789L;
		ByteString consensusBlockId = ByteString.copyFrom(Utils.getByteArray(Hash.BYTE_LENGTH,0x11));
		ByteString consensusDataHash = ByteString.copyFrom(Utils.getByteArray(Hash.BYTE_LENGTH,0x22));
		ByteString transactionAccumulatorHash = ByteString.copyFrom(Utils.getByteArray(Hash.BYTE_LENGTH,0x33));
		LedgerInfo ledgerInfo = LedgerInfo.newBuilder()
									.setVersion(version)
									.setTimestampUsecs(timestampUsecs)
									.setConsensusBlockId(consensusBlockId)
									.setConsensusDataHash(consensusDataHash)
									.setTransactionAccumulatorHash(transactionAccumulatorHash)
									.build();
		grpcChecker.checkLedgerInfo(ledgerInfo);
		assertFalse(Libra4jLog.hasLogs());
	}
	
}
