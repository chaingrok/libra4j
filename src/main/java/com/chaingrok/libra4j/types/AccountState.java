package com.chaingrok.libra4j.types;

import java.util.ArrayList;

public class AccountState {
	
	Long version;
	Transaction transaction;
	byte[] blob = null;
	byte[] bitmap = null;
	ArrayList<byte[]> nonDefaultSiblingsLedgerInfoToTransactionInfoProof = new ArrayList<byte[]>();
	ArrayList<byte[]> nonDefaultSiblingsTransactionInfoToAccountProof = new ArrayList<byte[]>();
	
	public Long getVersion() {
		return version;
	}
	
	public void setVersion(Long version) {
		this.version = version;
	}
	
	public Transaction getTransaction() {
		return transaction;
	}
	
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	public byte[] getBlob() {
		return blob;
	}
	
	public void setBlob(byte[] blob) {
		this.blob = blob;
	}
	
	public byte[] getBitmap() {
		return bitmap;
	}
	
	public void setBitmap(byte[] bitmap) {
		this.bitmap = bitmap;
	}
	
	public ArrayList<byte[]> getNonDefaultSiblingsLedgerInfoToTransactionInfoProof() {
		return nonDefaultSiblingsLedgerInfoToTransactionInfoProof;
	}
	
	public void setNonDefaultSiblingsLedgerInfoToTransactionInfoProof(
			ArrayList<byte[]> nonDefaultSiblingsLedgerInfoToTransactionInfoProof) {
		this.nonDefaultSiblingsLedgerInfoToTransactionInfoProof = nonDefaultSiblingsLedgerInfoToTransactionInfoProof;
	}
	
	public ArrayList<byte[]> getNonDefaultSiblingsTransactionInfoToAccountProof() {
		return nonDefaultSiblingsTransactionInfoToAccountProof;
	}
	
	public void setNonDefaultSiblingsTransactionInfoToAccountProof(
			ArrayList<byte[]> nonDefaultSiblingsTransactionInfoToAccountProof) {
		this.nonDefaultSiblingsTransactionInfoToAccountProof = nonDefaultSiblingsTransactionInfoToAccountProof;
	}
}
