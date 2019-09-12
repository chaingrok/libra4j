package com.chaingrok.libra4j.types;

public class Code extends ByteArrayObject {

	public static final Code MINT = new Code("4c49425241564d0a010007014a000000060000000350000000060000000c56000000060000000d5c0000000600000005620000003300000004950000002000000007b50000000e000000000000010002000300010400020002040200030003020402063c53454c463e0c4c696272614163636f756e74094c69627261436f696e046d61696e0f6d696e745f746f5f6164647265737300000000000000000000000000000000000000000000000000000000000000000001020104000c000c0111010002");
	public static final Code PEER_TO_PEER_TRANSFER = new Code("4c49425241564d0a010007014a00000004000000034e000000060000000c54000000060000000d5a0000000600000005600000002900000004890000002000000007a90000000e00000000000001000200010300020002040200030003020402063c53454c463e0c4c696272614163636f756e74046d61696e0f7061795f66726f6d5f73656e64657200000000000000000000000000000000000000000000000000000000000000000001020104000c000c0111010002");
	
	public Code(String hex) {
		super(hex);
	}
	
	public Code(byte[] bytes) {
		super(bytes);
	}

}
