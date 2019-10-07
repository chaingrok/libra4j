package com.chaingrok.libra4j.test;

import java.util.ArrayList;

import org.libra.grpc.types.AdmissionControlGrpc.AdmissionControlBlockingStub;
import org.libra.grpc.types.AdmissionControlGrpc.AdmissionControlStub;

import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Program;
import com.chaingrok.libra4j.types.Argument;
import com.chaingrok.libra4j.types.Argument.Type;
import com.chaingrok.libra4j.types.Arguments;
import com.chaingrok.libra4j.types.Transaction;
import com.chaingrok.libra4j.types.UInt64;
import com.chaingrok.libra4j.types.ValidatorEndpoint;

public class TestData {
	
	//all data below is subject to change on the Libra testnet at any point in time : testnet tends to be reinitialized very frequently...

	public static final String LIBRA_TESTNET_VALIDATOR_ADDRESS = "ac.testnet.libra.org";
	public static final int LIBRA_TESTNET_VALIDATOR_PORT = 8000;
	public static final ValidatorEndpoint VALIDATOR_ENDPOINT = new ValidatorEndpoint(TestData.LIBRA_TESTNET_VALIDATOR_ADDRESS, TestData.LIBRA_TESTNET_VALIDATOR_PORT);
	public static final AdmissionControlBlockingStub  VALIDATOR_BLOCKING_STUB = VALIDATOR_ENDPOINT.getBlockingStub();
	public static final AdmissionControlStub VALIDATOR_ASYNC_STUB = VALIDATOR_ENDPOINT.getAsyncStub();
	
	public static int TESTNET_VALIDATORS_COUNT = 7;
	
	public static final String TEST_PRIVATE_KEY_HEX1 = "3051020101300506032b6570042204207422e9df27029f7b83c37035622f93cd0e9b3a2a705d0745d573252756fd8c888121008e23fbceaa5b7a038c8994ca8258c8815e6e9007e3de86598cd46357e5e60024";
	public static final String TEST_PUBLIC_KEY_HEX1  = "302a300506032b65700321008e23fbceaa5b7a038c8994ca8258c8815e6e9007e3de86598cd46357e5e60024";
	public static final String TEST_ADDRESS1 = "6674633c78e2e00c69fd6e027aa6d1db2abc2a6c80d78a3e129eaf33dd49ce1c";
	
	private static Transaction TXN1 = null;
	
	public static  Transaction getTxn1() {
		if (TXN1 == null) {
			TXN1 = new Transaction();
			//
			TXN1.setVersion(123L);
			TXN1.setSequenceNumber(new UInt64(121L));
			TXN1.setSenderAccountAddress(new AccountAddress("0000000000000000000000000000000000000000000000000000000000000000"));
			TXN1.setMaxGasAmount(new UInt64(100000L));
			TXN1.setExpirationTime(new UInt64(1564593713L));
			TXN1.setGasUnitPrice(new UInt64(0L));
			TXN1.setGasUsed(0L);
			TXN1.setTxnInfoSerializedSize(102);
			TXN1.setSignedTxnSerializedSize(400);
			//
			Program program = new Program();
			program.setCodeSize(195L);
			TXN1.setProgram(program);
			//
			Arguments argList = new Arguments();
			Argument arg1 = new Argument((new AccountAddress("bfbdc605cd15dfa8afc1c202cc1d51d0f768fdd9c67d42c332627cc9b9487392")).getBytes());
			arg1.setType(Type.ADDRESS);
			argList.add(arg1);
			Argument arg2 = new Argument(new UInt64(0L).getBytes());
			arg2.setType(Type.U64);
			argList.add(arg2);
			program.setArguments(argList);
		}
		return TXN1;
	}

}
