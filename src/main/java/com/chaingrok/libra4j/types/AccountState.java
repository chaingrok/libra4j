package com.chaingrok.libra4j.types;

import java.util.ArrayList;

import com.chaingrok.lib.Utils;

public class AccountState {
	
	private Long version;
	private Transaction transaction;
	private byte[] blob = null;
	private byte[] bitmap = null;
	private ArrayList<byte[]> nonDefaultSiblingsLedgerInfoToTransactionInfoProof = new ArrayList<byte[]>();
	private ArrayList<byte[]> nonDefaultSiblingsTransactionInfoToAccountProof = new ArrayList<byte[]>();
	
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
	
	@Override
	public String toString() {
		String result = "";
		if (transaction != null) {
			result += transaction.toString();
		}
		result += "blob (" + blob.length + "): " + Utils.byteArrayToHexString(blob) + "\n";
		result += "bitmap (" + bitmap.length + "): " + Utils.byteArrayToHexString(bitmap) + "\n";
		if ((nonDefaultSiblingsLedgerInfoToTransactionInfoProof != null) 
				&& (nonDefaultSiblingsLedgerInfoToTransactionInfoProof.size() > 0)) {
			result += "nonDefaultSiblingsLedgerInfoToTransactionInfoProof: " + "\n";
			for (byte[] sibling :nonDefaultSiblingsLedgerInfoToTransactionInfoProof) {
				result += "   sibling (" + sibling.length + "): " + Utils.byteArrayToHexString(sibling)+ "\n";
			}
		}
		if ((nonDefaultSiblingsTransactionInfoToAccountProof != null) 
				&& (nonDefaultSiblingsTransactionInfoToAccountProof.size() > 0)) {
			result += "nonDefaultSiblingsTransactionInfoToAccountProof: " + "\n";
			for (byte[] sibling :nonDefaultSiblingsTransactionInfoToAccountProof) {
				result += "   sibling (" + sibling.length + "): " + Utils.byteArrayToHexString(sibling) + "\n";
			}
		}
		return result;
	}
}
