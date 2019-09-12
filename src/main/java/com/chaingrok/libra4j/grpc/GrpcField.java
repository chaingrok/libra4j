package com.chaingrok.libra4j.grpc;

import com.google.protobuf.ByteString;
import com.google.protobuf.UInt64Value;
import com.google.protobuf.Descriptors.EnumValueDescriptor;

import org.libra.grpc.types.AccessPathOuterClass.AccessPath;
import org.libra.grpc.types.AccountStateBlobOuterClass.AccountStateBlob;
import org.libra.grpc.types.AccountStateBlobOuterClass.AccountStateWithProof;
import org.libra.grpc.types.Events.Event;
import org.libra.grpc.types.Events.EventsForVersions;
import org.libra.grpc.types.Events.EventsList;
import org.libra.grpc.types.GetWithProof.GetAccountStateResponse;
import org.libra.grpc.types.GetWithProof.GetAccountTransactionBySequenceNumberResponse;
import  org.libra.grpc.types.GetWithProof.GetEventsByEventAccessPathResponse;
import org.libra.grpc.types.GetWithProof.GetTransactionsResponse;
import org.libra.grpc.types.GetWithProof.ResponseItem;
import org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse;
import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfo;
import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfoWithSignatures;
import org.libra.grpc.types.LedgerInfoOuterClass.ValidatorSignature;
import org.libra.grpc.types.Proof.AccountStateProof;
import org.libra.grpc.types.Proof.AccumulatorProof;
import org.libra.grpc.types.Proof.SignedTransactionProof;
import org.libra.grpc.types.Proof.SparseMerkleProof;
import org.libra.grpc.types.Transaction.TransactionListWithProof;
import org.libra.grpc.types.Transaction.RawTransaction;
import org.libra.grpc.types.Transaction.SignedTransaction;
import org.libra.grpc.types.Transaction.SignedTransactionWithProof;
import org.libra.grpc.types.TransactionInfoOuterClass.TransactionInfo;
import org.libra.grpc.types.Transaction.Program;
import org.libra.grpc.types.Transaction.TransactionArgument;

import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;

//ERROR #1: UNKNOWN_VALUE -- unknown com.chaingrok.libra4j.grpc.GrpcField:types.RawTransaction.gas_unit_price

public enum GrpcField {
	RESPONSE_ITEMS("types.UpdateToLatestLedgerResponse.response_items",ResponseItem.class,UpdateToLatestLedgerResponse.class),
	LEDGER_INFO_WITH_SIGS("types.UpdateToLatestLedgerResponse.ledger_info_with_sigs",LedgerInfoWithSignatures.class,UpdateToLatestLedgerResponse.class),
	SIGNATURES("types.LedgerInfoWithSignatures.signatures",ValidatorSignature.class,LedgerInfoWithSignatures.class),
	LEDGER_INFO("types.LedgerInfoWithSignatures.ledger_info",LedgerInfo.class,LedgerInfoWithSignatures.class),
	LEDGER_INFO_VERSION("types.LedgerInfo.version",Long.class,LedgerInfo.class),
	TRANSACTION_ACCUMULATOR_HASH("types.LedgerInfo.transaction_accumulator_hash",ByteString.class,LedgerInfo.class),
	CONSENSUS_DATA_HASH("types.LedgerInfo.consensus_data_hash",ByteString.class,LedgerInfo.class),
	CONSENSUS_BLOCK_ID("types.LedgerInfo.consensus_block_id",ByteString.class,LedgerInfo.class),
	TIMESTAMP_USECS("types.LedgerInfo.timestamp_usecs",Long.class,LedgerInfo.class),
	VALIDATOR_ID("types.ValidatorSignature.validator_id",ByteString.class,ValidatorSignature.class),
	SIGNATURE("types.ValidatorSignature.signature",ByteString.class,ValidatorSignature.class),
	GET_TRANSACTION_RESPONSE("types.ResponseItem.get_transactions_response",GetTransactionsResponse.class,ResponseItem.class),
	GET_ACCOUNT_STATE_RESPONSE("types.ResponseItem.get_account_state_response",GetAccountStateResponse.class,ResponseItem.class),
	GET_EVENTS_BY_EVENT_ACCESS_PATH("types.ResponseItem.get_events_by_event_access_path_response",GetEventsByEventAccessPathResponse.class,ResponseItem.class),
	GET_ACCOUNT_TRANSACTION_BY_SEQUENCE_NUMBER_RESPONSE("types.ResponseItem.get_account_transaction_by_sequence_number_response",GetAccountTransactionBySequenceNumberResponse.class,ResponseItem.class),
	GET_ACCOUNT_TRANSACTION_BY_SEQUENCE_NUMBER_RESPONSE_PROOF("types.GetAccountTransactionBySequenceNumberResponse.proof_of_current_sequence_number",AccountStateWithProof .class,org.libra.grpc.types.GetWithProof.GetAccountTransactionBySequenceNumberResponse.class),
	TXN_LIST_WITH_PROOF("types.GetTransactionsResponse.txn_list_with_proof",TransactionListWithProof.class,GetTransactionsResponse.class),
	TXN_LIST_EVENTS_FOR_VERSION("types.TransactionListWithProof.events_for_versions",EventsForVersions.class,TransactionListWithProof.class),
	EVENTS_FOR_VERSION("types.EventsForVersions.events_for_version",EventsList.class,EventsForVersions.class),
	TRANSACTIONS("types.TransactionListWithProof.transactions",SignedTransaction.class,TransactionListWithProof.class),
	INFOS("types.TransactionListWithProof.infos",TransactionInfo.class,TransactionListWithProof.class),
	FIRST_TRANSACTION_VERSION("types.TransactionListWithProof.first_transaction_version",UInt64Value.class,TransactionListWithProof.class),
	PROOF_OF_FIRST_TRANSACTION("types.TransactionListWithProof.proof_of_first_transaction",AccumulatorProof.class,TransactionListWithProof.class),
	PROOF_OF_LAST_TRANSACTION("types.TransactionListWithProof.proof_of_last_transaction",AccumulatorProof.class,TransactionListWithProof.class),
	SIGNED_TRANSACTION_HASH("types.TransactionInfo.signed_transaction_hash",ByteString.class,TransactionInfo.class),
	STATE_ROOT_HASH("types.TransactionInfo.state_root_hash",ByteString.class,TransactionInfo.class),
	EVENT_ROOT_HASH("types.TransactionInfo.event_root_hash",ByteString.class,TransactionInfo.class),
	GAS_USED("types.TransactionInfo.gas_used",Long.class,TransactionInfo.class),
	RAW_TXN_BYTES("types.SignedTransaction.raw_txn_bytes",ByteString.class,SignedTransaction.class),
	SENDER_PUBLIC_KEY("types.SignedTransaction.sender_public_key",ByteString.class,SignedTransaction.class),
	SENDER_SIGNATURE("types.SignedTransaction.sender_signature",ByteString.class,SignedTransaction.class),
	SENDER_ACCOUNT("types.RawTransaction.sender_account",ByteString.class,RawTransaction.class),
	TRANSACTION_SEQUENCE_NUMBER("types.RawTransaction.sequence_number",Long.class,RawTransaction.class),
	PROGRAM("types.RawTransaction.program",Program.class,RawTransaction.class),
	MAX_GAS_AMOUNT("types.RawTransaction.max_gas_amount",Long.class,RawTransaction.class),
	GAS_UNIT_PRICE("types.RawTransaction.gas_unit_price",Long.class,RawTransaction.class),
	EXPIRATION_TIME("types.RawTransaction.expiration_time",Long.class,RawTransaction.class),
	CODE("types.Program.code",ByteString.class,Program.class),
	ARGUMENTS("types.Program.arguments",TransactionArgument.class,Program.class),
	TYPE("types.TransactionArgument.type",EnumValueDescriptor.class,TransactionArgument.class),
	DATA("types.TransactionArgument.data",ByteString.class,TransactionArgument.class),
	ACCOUNT_STATE_WITH_PROOF("types.GetAccountStateResponse.account_state_with_proof",AccountStateWithProof.class,GetAccountStateResponse.class),
	ACCOUNT_STATE_VERSION("types.AccountStateWithProof.version",Long.class,AccountStateWithProof.class),
	ACCOUNT_STATE_BLOB("types.AccountStateWithProof.blob",AccountStateBlob.class,AccountStateWithProof.class),
	ACCOUNT_STATE_PROOF("types.AccountStateWithProof.proof",AccountStateProof.class,AccountStateWithProof.class),
	ACCOUNT_BLOB("types.AccountStateBlob.blob",ByteString.class,AccountStateBlob.class),
	LEDGER_INFO_TO_TRANSACTION_INFO_PROOF("types.AccountStateProof.ledger_info_to_transaction_info_proof",AccumulatorProof.class,AccountStateProof.class),
	TRANSACTION_INFO("types.AccountStateProof.transaction_info",TransactionInfo.class,AccountStateProof.class),
	TRANSACTION_INFO_TO_ACCOUNT_PROOF("types.AccountStateProof.transaction_info_to_account_proof",SparseMerkleProof.class,AccountStateProof.class),
	PROOF_BITMAP("types.AccumulatorProof.bitmap",Long.class,AccumulatorProof.class),
	PROOF_NON_DEFAULT_SIBLINGS("types.AccumulatorProof.non_default_siblings",Long.class,AccumulatorProof.class),
	MERKLE_LEAF("types.SparseMerkleProof.leaf",ByteString.class,SparseMerkleProof.class),
	MERKLE_BITMAP("types.SparseMerkleProof.bitmap",ByteString.class,SparseMerkleProof.class),
	MERKLE_NON_DEFAULT_SIBLINGS("types.SparseMerkleProof.non_default_siblings",Long.class,SparseMerkleProof.class),
	PROOF_OF_LATEST_EVENT("types.GetEventsByEventAccessPathResponse.proof_of_latest_event",AccountStateWithProof.class,GetEventsByEventAccessPathResponse.class),
	SIGNED_TRANSACTION_WITH_PROOF("types.GetAccountTransactionBySequenceNumberResponse.signed_transaction_with_proof",SignedTransactionWithProof.class,GetAccountTransactionBySequenceNumberResponse.class),
	SIGNED_TRANSACTION_LEDGER_INFO_TO_TRANSACTION_IMFO_PROOF("types.SignedTransactionProof.ledger_info_to_transaction_info_proof",AccumulatorProof.class,SignedTransactionProof.class),
	SIGNED_TRANSACTION_TRANSACTION_IMFO("types.SignedTransactionProof.transaction_info",TransactionInfo.class,SignedTransactionProof.class),
	VERSION("types.SignedTransactionWithProof.version",Long.class,SignedTransactionWithProof.class),
	SIGNED_TRANSACTION("types.SignedTransactionWithProof.signed_transaction",SignedTransaction.class,SignedTransactionWithProof.class),
	SIGNED_TRANSACTION_PROOF("types.SignedTransactionWithProof.proof",SignedTransactionProof.class,SignedTransactionWithProof.class),
	SIGNED_TRANSACTION_EVENTS("types.SignedTransactionWithProof.events",EventsList.class,SignedTransactionWithProof.class),
	EVENTS("types.EventsList.events",Event.class,EventsList.class),
	EVENT_ACCESS_PATH("types.Event.access_path",AccessPath.class,Event.class),
	EVENT_SEQUENCE_NUMBER("types.Event.sequence_number",Long.class,Event.class),
	EVENT_DATA("types.Event.event_data",ByteString.class,Event.class),
	ACCESS_PATH_ADDRESS("types.AccessPath.address",ByteString.class,AccessPath.class),
	ACCESS_PATH_PATH("types.AccessPath.path",ByteString.class,AccessPath.class),

	;
	
	private String fieldFullName = null;
	private Class<?> fieldClass;
	private Class<?> owningClass;
	
	private GrpcField(String fieldFullName,Class<?> fieldClass,Class<?> owningClass) {
		this.fieldFullName = fieldFullName;
		this.fieldClass = fieldClass;
		this.owningClass = owningClass;
	}
	
	public String getFullName() {
		return fieldFullName;
	}
	
	public Class<?> getFieldClass() {
		return fieldClass;
	}
	
	public Class<?> getOwningClass() {
		return owningClass;
	}
	
	public static GrpcField get(String fullName) {
		GrpcField result = null;
		for (GrpcField field : GrpcField.values()) {
			if (field.fieldFullName.equals(fullName)) {
				result = field;
			}
		}
		if (result == null) {
			new Libra4jError(Type.UNKNOWN_VALUE,"unknown " + GrpcField.class.getCanonicalName() + ":" + fullName);
		}
		return result;
	}
	
}