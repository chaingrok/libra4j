package com.chaingrok.libra4j.types;

import java.security.PublicKey;
import java.util.ArrayList;

import com.chaingrok.libra4j.misc.Libra4jWarning;

public class Transaction {
	
	private AccountAddress senderAccountAddress = null;
	private UInt64 sequenceNumber = null;
	private TransactionPayloadType transactionPayloadType = null;
	private Program program = null;
	private WriteSet writeSet = null;
	private UInt64 maxGasAmount = null;
	private UInt64 gasUnitPrice = null;
	private UInt64 expirationTime = null;
	//
	private Long version = null;
	private Long majorStatus = null;
	private PublicKey senderPublicKey = null;
	private Signature signature;
	private Long gasUsed =  null;
	private Integer  txnInfoSerializedSize = null;
	private Integer  signedTxnSerializedSize = null;
	private Hash signedTransactionHash = null ;
	private Hash eventRootHash = null;
	private Hash stateRootHash = null;
	private byte[] fullTxnBytes = null;
	private byte[] rawTxnBytes = null;
	private ArrayList<Event> eventsList = null;
	
	public Type getType() {
		return Type.get(this);
	}
	
	public void setType() {
		Type.get(this);
	}
	
	//raw transaction fields
	
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

	public PublicKey getSenderPublicKey() {
		return senderPublicKey;
	}

	public void setSenderPublicKey(PublicKey senderPublicKey) {
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

	public byte[] getFullTxnBytes() {
		return fullTxnBytes;
	}

	public void setFullTxnBytes(byte[] fullTxnBytes) {
		this.fullTxnBytes = fullTxnBytes;
	}

	public byte[] getRawTxnBytes() {
		return rawTxnBytes;
	}

	public void setRawTxnBytes(byte[] rawTxnBytes) {
		this.rawTxnBytes = rawTxnBytes;
	}

	public ArrayList<Event> getEventsList() {
		return eventsList;
	}

	public void setEventsList(ArrayList<Event> eventsList) {
		this.eventsList = eventsList;
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
			ArrayList<Argument> arguments = getProgram().getArguments();
			if ((arguments != null)
				&& (arguments.size() > 0)) {
				for (Argument argument : arguments) {
					result += "      argument: " +  argument + "\n";
				}
			}
			ArrayList<Module> modules = program.getModules();
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
		ArrayList<Event> events = getEventsList();
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
	
	public class RawTransaction {

	}
	
	public class SignedTransaction {

	}
	
}
