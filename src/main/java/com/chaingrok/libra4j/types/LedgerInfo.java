package com.chaingrok.libra4j.types;

import java.util.ArrayList;

import com.chaingrok.libra4j.misc.Utils;

public class LedgerInfo {
	
	private Hash consensusBlockId;
	private Hash consensusDataHash;
	private Hash transactionAccumulatorHash ;
	private Long epochNum = null;
	private Long timestampUsecs= null;
	private Long version = null;
	private ArrayList<Validator> validators = null;
	
	public Hash getConsensusBlockId() {
		return consensusBlockId;
	}
	
	public void setConsensusBlockId(Hash consensusBlockId) {
		this.consensusBlockId = consensusBlockId;
	}
	
	public Hash getConsensusDataHash() {
		return consensusDataHash;
	}
	
	public void setConsensusDataHash(Hash consensusDatahHash) {
		this.consensusDataHash = consensusDatahHash;
	}
	
	public Hash getTransactionAccumulatorHash() {
		return transactionAccumulatorHash;
	}
	
	public void setTransactionAccumulatorHash(Hash transactionAccumulatorHash) {
		this.transactionAccumulatorHash = transactionAccumulatorHash;
	}
	
	public Long getEpochNum() {
		return epochNum;
	}
	
	public void setEpochNum(Long epochNum) {
		this.epochNum = epochNum;
	}
	
	public Long getTimestampUsecs() {
		return timestampUsecs;
	}
	
	public void setTimestampUsecs(Long timestampUsecs) {
		this.timestampUsecs = timestampUsecs;
	}
	
	public Long getVersion() {
		return version;
	}
	
	public void setVersion(Long version) {
		this.version = version;
	}

	public ArrayList<Validator> getValidators() {
		return validators;
	}

	public void setValidators(ArrayList<Validator> validators) {
		this.validators = validators;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "ledger info " + getVersion() + ":\n";
		result += "   epoch num: " + getEpochNum() + "\n";
		result += "   timestamp: " +  Utils.timestampMillisToDateString(getTimestampUsecs()/1000) + " (" + getTimestampUsecs() + " usecs)\n";
		result += "   consensus data hash: " + consensusDataHash + "\n";
		result += "   transaction accumulator hash: " + transactionAccumulatorHash + "\n";
		result += "   consensus block id: " + consensusBlockId + "\n";
		result += "   validators (" + validators.size() + "): ";
		if (validators.size() > 0) {
			for (Validator validator : validators) {
				result += validator.getValidatorId() + ",";
			}
		}
		result += "\n";
		return result;
	}

}
