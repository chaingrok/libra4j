package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.test.TestData;
import com.chaingrok.libra4j.types.ValidatorEndpoint;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestValidatorEndpoint {
	
	@Test
	public void tes001_newInstance() {
		ValidatorEndpoint validatorEndpoint = new ValidatorEndpoint(TestData.LIBRA_TESTNET_VALIDATOR_ADDRESS,TestData.LIBRA_TESTNET_VALIDATOR_PORT);
		assertEquals(TestData.LIBRA_TESTNET_VALIDATOR_ADDRESS,validatorEndpoint.getDns());
		assertEquals(TestData.LIBRA_TESTNET_VALIDATOR_PORT,validatorEndpoint.getPort());
		assertNotNull(validatorEndpoint.getBlockingStub());
		assertNotNull(validatorEndpoint.getAsyncStub());
	}

}
