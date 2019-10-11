package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.misc.Libra4jWarning;

public class Transaction implements LCSInterface {
	//raw transaction fields
	private byte[] signedTransactionBytes = null;
	private AccountAddress senderAccountAddress = null;
	private UInt64 sequenceNumber = null;
	private TransactionPayloadType transactionPayloadType = null;
	private Program program = null;
	private WriteSet writeSet = null;
	private Script script = null;
	private UInt64 maxGasAmount = null;
	private UInt64 gasUnitPrice = null;
	private UInt64 expirationTime = null;
	//transaction fields
	private PubKey senderPublicKey = null;
	private Signature signature = null;
	//transactionInfo fields
	private Long version = null;
	private Long majorStatus = null;
	private Long gasUsed =  null;
	private Hash signedTransactionHash = null ;
	private Hash eventRootHash = null;
	private Hash stateRootHash = null;
	//
	private Integer  txnInfoSerializedSize = null;
	private Integer  signedTxnSerializedSize = null;
	//private byte[] fullTxnBytes = null;
	//
	private Events events = null;
	
	public Type getType() {
		return Type.get(this);
	}
	
	public void setType() {
		Type.get(this);
	}
	
	//raw transaction fields
	
	public byte[] getSignedTransactionBytes() {
		return signedTransactionBytes;
	}

	public void setSignedTransactionBytes(byte[] signedTransactionBytes) {
		this.signedTransactionBytes = signedTransactionBytes;
	}
	
	public AccountAddress getSenderAccountAddress() {
		return senderAccountAddress;
	}

	public void setSenderAccountAddress(AccountAddress senderAccountAddress) {
		this.senderAccountAddress = senderAccountAddress;
	}
	
	public UInt64 getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(UInt64 sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	public TransactionPayloadType getTransactionPayloadType() {
		return transactionPayloadType;
	}

	public void setTransactionPayloadType(TransactionPayloadType transactionPayloadType) {
		this.transactionPayloadType = transactionPayloadType;
	}
	
	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}
	
	public WriteSet getWriteSet() {
		return writeSet;
	}

	public void setWriteSet(WriteSet writeSet) {
		this.writeSet = writeSet;
	}
	
	public Script getScript() {
		return script;
	}

	public void setScript(Script script) {
		this.script = script;
	}

	public UInt64 getMaxGasAmount() {
		return maxGasAmount;
	}

	public void setMaxGasAmount(UInt64 maxGasAmount) {
		this.maxGasAmount = maxGasAmount;
	}
	
	public UInt64 getGasUnitPrice() {
		return gasUnitPrice;
	}

	public void setGasUnitPrice(UInt64 gasUnitPrice) {
		this.gasUnitPrice = gasUnitPrice;
	}
	
	public UInt64 getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(UInt64 expirationTime) {
		this.expirationTime = expirationTime;
	}
	
	//signed transaction fields
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	public Long getMajorStatus() {
		return majorStatus;
	}

	public void setMajorStatus(Long majorStatus) {
		this.majorStatus = majorStatus;
	}

	public PubKey getSenderPublicKey() {
		return senderPublicKey;
	}

	public void setSenderPublicKey(PubKey senderPublicKey) {
		this.senderPublicKey = senderPublicKey;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public Long getGasUsed() {
		return gasUsed;
	}

	public void setGasUsed(Long gasUsed) {
		this.gasUsed = gasUsed;
	}

	public Integer getTxnInfoSerializedSize() {
		return txnInfoSerializedSize;
	}

	public void setTxnInfoSerializedSize(Integer txnInfoSerializedSize) {
		this.txnInfoSerializedSize = txnInfoSerializedSize;
	}

	public Integer getSignedTxnSerializedSize() {
		return signedTxnSerializedSize;
	}

	public void setSignedTxnSerializedSize(Integer signedTxnSerializedSize) {
		this.signedTxnSerializedSize = signedTxnSerializedSize;
	}

	public Hash getSignedTransactionHash() {
		return signedTransactionHash;
	}

	public void setSignedTransactionHash(Hash signedTransactionHash) {
		this.signedTransactionHash = signedTransactionHash;
	}

	public Hash getEventRootHash() {
		return eventRootHash;
	}

	public void setEventRootHash(Hash eventRootHash) {
		this.eventRootHash = eventRootHash;
	}

	public Hash getStateRootHash() {
		return stateRootHash;
	}

	public void setStateRootHash(Hash stateRootHash) {
		this.stateRootHash = stateRootHash;
	}

	public Events getEventsList() {
		return events;
	}

	public void setEventsList(Events eventsList) {
		this.events = eventsList;
	}

	@Override
	public String toString() {
		String result = "";
		result += "transaction " + getVersion() + ":\n";
		if (getSenderAccountAddress() != null) {
			result += "   sender account address: " + getSenderAccountAddress().toString() + "\n";
		}
		if (getSignature() != null) {
			result += "   signature: " + getSignature().toString() + "\n";
		}
		result += "   signed transaction hash:" + getSignedTransactionHash() + "\n";
		result += "   sequence number: " + getSequenceNumber() + "\n";
		result += "   gas used: "  + getGasUsed() + "\n";
		result += "   max gas amount: "  + getMaxGasAmount() + "\n";
		result += "   gas unit price: "  + getGasUnitPrice() + "\n";
		result += "   program: " +  "\n";
		if (program != null) {
			Arguments arguments = getProgram().getArguments();
			if ((arguments != null)
				&& (arguments.size() > 0)) {
				for (Argument argument : arguments) {
					result += "      argument: " +  argument + "\n";
				}
			}
			Modules modules = program.getModules();
			if ((modules != null)
				&& (modules.size() > 0)) {
				for (Module module : modules) {
					result += "      module (" + module.getBytes().length+ " bytes): " +  module + "\n";
				}
			}
			Code code = getProgram().getCode();
			if (code != null) {
				result += "      code (" + code.getBytes().length+ " bytes): " +  code + "\n";
			}
		}
		Events events = getEventsList();
		if ((events != null) 
				&& (events.size() > 0)) {
			result += "   events: " + ":\n";
			for (Event event : events) {
				result += "         " + event.toString() +  "\n";
			}
		}
		result += "   type = " + getType() +  "\n";
		return result;
	}
	
	public enum Type {
		MINT,
		PEER_TO_PEER_TRANSFER,
		CREATE_ACCOUNT,
		ROTATE_AUTHENTICATION_KEY,
		UNKNOWN,
		
		;
		
		public static Type get(Transaction transaction) {
			Type result = UNKNOWN;
			if (transaction.getProgram() != null) {
				if (Code.MINT.equals(transaction.getProgram().getCode())) {
					result = MINT;
				} else if (Code.PEER_TO_PEER_TRANSFER.equals(transaction.getProgram().getCode())) {
					result = PEER_TO_PEER_TRANSFER;
				}
			}
			if (result == UNKNOWN) {
				new Libra4jWarning(com.chaingrok.libra4j.misc.Libra4jLog.Type.UNKNOWN_VALUE,"Transaction type is unknown: " + transaction.getVersion());
			}
			return result;
		}
	}

	@Override
	public LCSProcessor encodeToLCS(LCSProcessor lcsProcessor) {
		if (lcsProcessor != null) {
			lcsProcessor.encode(getSenderAccountAddress())
				.encode(getSequenceNumber())
				.encode(getProgram())
				.encode(getMaxGasAmount())
				.encode(getGasUnitPrice())
				.encode(getExpirationTime());
		}
		return lcsProcessor;
	}
}
