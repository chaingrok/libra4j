package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertSame;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Signature;
import com.chaingrok.libra4j.types.Validator;
import com.chaingrok.libra4j.types.ValidatorId;
import com.google.protobuf.ByteString;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestValidator extends TestClass {
	
	@Test
	public void test001NewInstance() {
		Validator validator = new Validator();
		//
		ValidatorId validatorId = new ValidatorId(ByteString.copyFrom(Utils.getByteArray(ValidatorId.BYTE_LENGTH)));
		validator.setValidatorId(validatorId);
		assertSame(validatorId,validator.getValidatorId());
		//
		Signature signature = new Signature(Utils.getByteArray(Signature.BYTE_LENGTH));
		validator.setSignature(signature);
		assertSame(signature,validator.getSignature());
	}

}
