package com.chaingrok.libra4j.types;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.UInt64;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.LCSProcessor;

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
	
	//raw transaction fields
	
	public byte[] getSignedTransactionBytes() {
		return signedTransactionBytes;
	}

	public Transaction setSignedTransactionBytes(byte[] signedTransactionBytes) {
		this.signedTransactionBytes = signedTransactionBytes;
		return this;
	}
	
	public AccountAddress getSenderAccountAddress() {
		return senderAccountAddress;
	}

	public Transaction setSenderAccountAddress(AccountAddress senderAccountAddress) {
		this.senderAccountAddress = senderAccountAddress;
		return this;
	}
	
	public UInt64 getSequenceNumber() {
		return sequenceNumber;
	}

	public Transaction setSequenceNumber(UInt64 sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
		return this;
	}
	
	public TransactionPayloadType getTransactionPayloadType() {
		return transactionPayloadType;
	}

	public Transaction setTransactionPayloadType(TransactionPayloadType transactionPayloadType) {
		this.transactionPayloadType = transactionPayloadType;
		return this;
	}
	
	public Program getProgram() {
		return program;
	}

	public Transaction setProgram(Program program) {
		this.program = program;
		return this;
	}
	
	public WriteSet getWriteSet() {
		return writeSet;
	}

	public Transaction setWriteSet(WriteSet writeSet) {
		this.writeSet = writeSet;
		return this;
	}
	
	public Script getScript() {
		return script;
	}

	public Transaction setScript(Script script) {
		this.script = script;
		return this;
	}

	public UInt64 getMaxGasAmount() {
		return maxGasAmount;
	}

	public Transaction setMaxGasAmount(UInt64 maxGasAmount) {
		this.maxGasAmount = maxGasAmount;
		return this;
	}
	
	public UInt64 getGasUnitPrice() {
		return gasUnitPrice;
	}

	public Transaction setGasUnitPrice(UInt64 gasUnitPrice) {
		this.gasUnitPrice = gasUnitPrice;
		return this;
	}
	
	public UInt64 getExpirationTime() {
		return expirationTime;
	}

	public Transaction setExpirationTime(UInt64 expirationTime) {
		this.expirationTime = expirationTime;
		return this;
	}
	
	//signed transaction fields
	
	public Long getVersion() {
		return version;
	}

	public Transaction setVersion(Long version) {
		this.version = version;
		return this;
	}
	
	public Long getMajorStatus() {
		return majorStatus;
	}

	public Transaction setMajorStatus(Long majorStatus) {
		this.majorStatus = majorStatus;
		return this;
	}

	public PubKey getSenderPublicKey() {
		return senderPublicKey;
	}

	public Transaction setSenderPublicKey(PubKey senderPublicKey) {
		this.senderPublicKey = senderPublicKey;
		return this;
	}

	public Signature getSignature() {
		return signature;
	}

	public Transaction setSignature(Signature signature) {
		this.signature = signature;
		return this;
	}

	public Long getGasUsed() {
		return gasUsed;
	}

	public Transaction setGasUsed(Long gasUsed) {
		this.gasUsed = gasUsed;
		return this;
	}

	public Integer getTxnInfoSerializedSize() {
		return txnInfoSerializedSize;
	}

	public Transaction setTxnInfoSerializedSize(Integer txnInfoSerializedSize) {
		this.txnInfoSerializedSize = txnInfoSerializedSize;
		return this;
	}

	public Integer getSignedTxnSerializedSize() {
		return signedTxnSerializedSize;
	}

	public Transaction setSignedTxnSerializedSize(Integer signedTxnSerializedSize) {
		this.signedTxnSerializedSize = signedTxnSerializedSize;
		return this;
	}

	public Hash getSignedTransactionHash() {
		return signedTransactionHash;
	}

	public Transaction setSignedTransactionHash(Hash signedTransactionHash) {
		this.signedTransactionHash = signedTransactionHash;
		return this;
	}

	public Hash getEventRootHash() {
		return eventRootHash;
	}

	public Transaction setEventRootHash(Hash eventRootHash) {
		this.eventRootHash = eventRootHash;
		return this;
	}

	public Hash getStateRootHash() {
		return stateRootHash;
	}

	public Transaction setStateRootHash(Hash stateRootHash) {
		this.stateRootHash = stateRootHash;
		return this;
	}

	public Events getEventsList() {
		return events;
	}

	public Transaction setEventsList(Events eventsList) {
		this.events = eventsList;
		return this;
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
		Long expirationTime = null;
		if (getExpirationTime() != null) {
			expirationTime = getExpirationTime().getAsLong();
			result += "   expiration time: "  + Utils.timestampMillisToDateString(expirationTime*1000) + " (" + expirationTime + ")" +"\n";
		} else {
			result += "   expiration time: " +"\n";
		}
		TransactionPayloadType transactionPayloadType = getTransactionPayloadType();
		result += "   payload type: " + transactionPayloadType + "\n";
		if (transactionPayloadType != null) {
			switch (transactionPayloadType) {
				case PROGRAM:
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
							result += "      code (" + code.getBytes().length + " bytes): " +  code + "\n";
						}
					}
					break;
				case WRITESET:
					new ChaingrokError(ChaingrokLog.Type.NOT_IMPLEMENTED,"toString not implemented yet for write set");
					break;
				case SCRIPT:
					result += "   script: " +  "\n";
					if (script != null) {
						Arguments arguments = getScript().getArguments();
						if ((arguments != null)
							&& (arguments.size() > 0)) {
							for (Argument argument : arguments) {
								result += "      argument: " +  argument + "\n";
							}
						}
						Code code = getScript().getCode();
						if (code != null) {
							result += "      code (" + code.getBytes().length + " bytes): " +  code + "\n";
						}
					}
					break;
				case MODULE:
					new ChaingrokError(ChaingrokLog.Type.NOT_IMPLEMENTED,"tranasaction type discovery not implemented yet for module");
					break;
				default:
					new ChaingrokError(ChaingrokLog.Type.NOT_IMPLEMENTED,"should not happen");
					break;
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
		if (transactionPayloadType != null) {
			result += "   type = " + getType() +  "\n";
		}
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
			Code code = null;
			TransactionPayloadType transactionPayloadType = transaction.getTransactionPayloadType();
			if (transactionPayloadType != null) {
				switch (transactionPayloadType) {
					case PROGRAM:
						code = transaction.getProgram().getCode();
						break;
					case WRITESET:
						new ChaingrokError(ChaingrokLog.Type.NOT_IMPLEMENTED,"tranasaction type discovery not implemented yet for write set");
						break;
					case SCRIPT:
						code = transaction.getScript().getCode();
						break;
					case MODULE:
						new ChaingrokError(ChaingrokLog.Type.NOT_IMPLEMENTED,"tranasaction type discovery not implemented yet for module");
						break;
					default:
						new ChaingrokError(ChaingrokLog.Type.NOT_IMPLEMENTED,"should not happen");
						break;
				}
				if (Code.MINT.equals(code)) {
					result = MINT;
				} else if (Code.PEER_TO_PEER_TRANSFER.equals(code)) {
					result = PEER_TO_PEER_TRANSFER;
				} 
			}
			if (result == UNKNOWN) {
				String msg = "transaction type is unknown for transaction version: " + transaction.getVersion();
				if (code != null) {
					msg += " - code: " + Utils.byteArrayToHexString(code.getBytes());
				}
				new ChaingrokError(com.chaingrok.lib.ChaingrokLog.Type.UNKNOWN_VALUE,msg);
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
