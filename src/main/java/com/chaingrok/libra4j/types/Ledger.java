package com.chaingrok.libra4j.types;

import java.util.ArrayList;
import java.util.List;

import org.libra.grpc.types.AccessPathOuterClass.AccessPath;
import org.libra.grpc.types.AccountStateBlobOuterClass.AccountStateBlob;
import org.libra.grpc.types.AccountStateBlobOuterClass.AccountStateWithProof;
import org.libra.grpc.types.Events.Event;
import org.libra.grpc.types.Events.EventWithProof;
import org.libra.grpc.types.Events.EventsForVersions;
import org.libra.grpc.types.Events.EventsList;
import org.libra.grpc.types.GetWithProof.GetAccountStateRequest;
import org.libra.grpc.types.GetWithProof.GetAccountStateResponse;
import org.libra.grpc.types.GetWithProof.GetAccountTransactionBySequenceNumberRequest;
import org.libra.grpc.types.GetWithProof.GetAccountTransactionBySequenceNumberResponse;
import org.libra.grpc.types.GetWithProof.GetEventsByEventAccessPathRequest;
import org.libra.grpc.types.GetWithProof.GetEventsByEventAccessPathResponse;
import org.libra.grpc.types.GetWithProof.GetTransactionsRequest;
import org.libra.grpc.types.GetWithProof.GetTransactionsResponse;
import org.libra.grpc.types.GetWithProof.RequestItem;
import org.libra.grpc.types.GetWithProof.ResponseItem;
import org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest;
import org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse;
import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfo;
import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfoWithSignatures;
import org.libra.grpc.types.LedgerInfoOuterClass.ValidatorSignature;
import org.libra.grpc.types.Proof.AccountStateProof;
import org.libra.grpc.types.Proof.AccumulatorProof;
import org.libra.grpc.types.Proof.EventProof;
import org.libra.grpc.types.Proof.SignedTransactionProof;
import org.libra.grpc.types.Proof.SparseMerkleProof;
import org.libra.grpc.types.Transaction.Program;
import org.libra.grpc.types.Transaction.RawTransaction;
import org.libra.grpc.types.Transaction.SignedTransaction;
import org.libra.grpc.types.Transaction.SignedTransactionWithProof;
import org.libra.grpc.types.Transaction.TransactionArgument;
import org.libra.grpc.types.Transaction.TransactionListWithProof;
import org.libra.grpc.types.TransactionInfoOuterClass.TransactionInfo;

import com.chaingrok.libra4j.grpc.GrpcChecker;
import com.chaingrok.libra4j.grpc.GrpcField;
import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;


import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.UInt64Value;

public class Ledger {
	
	private ValidatorEndpoint validatorEndpoint;
	private GrpcChecker grpcChecker = new GrpcChecker();
	private LedgerInfoWithSignatures ledgerInfoWithSignatures;
	private long requestCount = 0;
	
	public Ledger(ValidatorEndpoint validatorEndpoint) {
		this.validatorEndpoint = validatorEndpoint;
	}
	
	public ArrayList<Transaction> getTransactions(long version, long count) {
		return getTransactions(version,count,true);
	}
	
	public ArrayList<Transaction> getTransactions(long version, long count, boolean withEvents) {
		ArrayList<Transaction> result = new ArrayList<Transaction>();
		GetTransactionsRequest getTransactionsRequest = GetTransactionsRequest.newBuilder()
				.setLimit(count)
				.setStartVersion(version)
				.setFetchEvents(withEvents)
				.build();
		RequestItem requestItem = RequestItem.newBuilder()
				.setGetTransactionsRequest(getTransactionsRequest)
				.build();
		UpdateToLatestLedgerRequest request = UpdateToLatestLedgerRequest.newBuilder()
			.addRequestedItems(requestItem)
			.build();
		//
		ResponseItem responseItem = getResponseItem(requestItem);
		GetTransactionsResponse transactionsResponse = responseItem.getGetTransactionsResponse();
		if (transactionsResponse != null) {
			grpcChecker.checkExpectedFields(transactionsResponse,1);
			TransactionListWithProof transactionListWithProof = transactionsResponse.getTxnListWithProof();
			if (transactionListWithProof != null) {
				if (count == 1) {
					if (withEvents) {
						grpcChecker.checkExpectedFields(transactionListWithProof,5);
					} else {
						grpcChecker.checkExpectedFields(transactionListWithProof,4);
					}
				} else {
					if (withEvents) {
						grpcChecker.checkExpectedFields(transactionListWithProof,6);
					} else {
						grpcChecker.checkExpectedFields(transactionListWithProof,5);
					}
				}
				UInt64Value u64Version = transactionListWithProof.getFirstTransactionVersion();
				long transactionVersion = u64Version.getValue();
				List<TransactionInfo> transactionInfoList = transactionListWithProof.getInfosList();
				if (transactionInfoList != null) {
					for (TransactionInfo transactionInfo : transactionInfoList) {
						Transaction transaction = new Transaction();
						transaction.setVersion(transactionVersion++);
						processTransactionInfo(transactionInfo,transaction);
						result.add(transaction);
					}
				}
				List<SignedTransaction> signedTransactions = transactionListWithProof.getTransactionsList();
				if (signedTransactions != null) {
					if (signedTransactions.size() != result.size()) {
						new Libra4jError(Type.INVALID_LENGTH,"result size <> signedTransactions size: " + result.size() + " <> " + signedTransactions.size());
					}
					int resCount = 0;
					for (SignedTransaction signedTransaction : signedTransactions) {
						Transaction transaction = result.get(resCount++);
						processSignedTransaction(signedTransaction,transaction);
					}
				}
				EventsForVersions eventsForVersions = transactionListWithProof.getEventsForVersions();
				if (eventsForVersions != null) {
					grpcChecker.checkExpectedFields(eventsForVersions,1);
					List<EventsList> listOfEventsList = eventsForVersions.getEventsForVersionList();
					if (listOfEventsList != null) {
						int resCount = 0;
						for (EventsList eventsList : listOfEventsList) {
							Transaction transaction = result.get(resCount++);
							ArrayList<com.chaingrok.libra4j.types.Event> events = processEventsList(eventsList);
							transaction.setEventsList(events);
						}
					}
				}
			}
		} else {
			new Libra4jError(Type.NULL_DATA,request);
		}
		return result;
	}
	
	public Transaction getTransaction(long version) {
		return getTransaction(version,true);
	}
	
	public Transaction getTransaction(long version, boolean withEvents) {
		Transaction result = null;
		ArrayList<Transaction> list = getTransactions(version,1L,withEvents);
		if (list.size() != 1) {
			new Libra4jError(Type.LIST_TOO_LONG,"list size: " + list.size());
		} else {
			result = list.get(0);
		}
		return result;
	}
	
	public Transaction getAccountTransactionBySequenceNumber(AccountAddress accountAddress,long sequence) {
		return getAccountTransactionBySequenceNumber(accountAddress,sequence,false);
	}
	
	public Transaction getAccountTransactionBySequenceNumber(AccountAddress accountAddress,long sequence, boolean withEvents) {
		Transaction result = null;
		GetAccountTransactionBySequenceNumberRequest getAccountTransactionBySequenceNumberRequest = GetAccountTransactionBySequenceNumberRequest.newBuilder()
			.setAccount(accountAddress.getByteString())
			.setFetchEvents(withEvents)
			.setSequenceNumber(sequence)
			.build();
		RequestItem requestItem = RequestItem.newBuilder()
			.setGetAccountTransactionBySequenceNumberRequest(getAccountTransactionBySequenceNumberRequest)
			.build();
		ResponseItem responseItem = getResponseItem(requestItem);
		grpcChecker.checkFieldErrors(responseItem.findInitializationErrors(),responseItem.getUnknownFields());
		grpcChecker.checkExpectedFields(responseItem,1);
		if (grpcChecker.isFieldSet(GrpcField.GET_ACCOUNT_TRANSACTION_BY_SEQUENCE_NUMBER_RESPONSE,responseItem,responseItem.getAllFields())) {
			GetAccountTransactionBySequenceNumberResponse accountTransactionBySequenceNumberResponse = responseItem.getGetAccountTransactionBySequenceNumberResponse();
			grpcChecker.checkFieldErrors(accountTransactionBySequenceNumberResponse.findInitializationErrors(),accountTransactionBySequenceNumberResponse.getUnknownFields());
			grpcChecker.checkExpectedFields(accountTransactionBySequenceNumberResponse,1);
			if (grpcChecker.isFieldSet(GrpcField.SIGNED_TRANSACTION_WITH_PROOF,accountTransactionBySequenceNumberResponse,accountTransactionBySequenceNumberResponse.getAllFields())) {
				SignedTransactionWithProof signedTransactionWithProof = accountTransactionBySequenceNumberResponse.getSignedTransactionWithProof();
				grpcChecker.checkFieldErrors(signedTransactionWithProof.findInitializationErrors(),signedTransactionWithProof.getUnknownFields());
				grpcChecker.checkExpectedFields(signedTransactionWithProof,3);
				EventsList eventsList = signedTransactionWithProof.getEvents();
				processEventsList(eventsList);
				SignedTransaction signedTransaction = signedTransactionWithProof.getSignedTransaction();
				Transaction transaction = processSignedTransaction(signedTransaction);
				SignedTransactionProof signedTransactionProof = signedTransactionWithProof.getProof();
				grpcChecker.checkFieldErrors(signedTransactionProof.findInitializationErrors(),signedTransactionProof.getUnknownFields());
				grpcChecker.checkExpectedFields(signedTransactionProof,2);
				AccumulatorProof accumulatorProof = signedTransactionProof.getLedgerInfoToTransactionInfoProof();
				processAccumulatorProof(accumulatorProof);
				TransactionInfo transactionInfo = signedTransactionProof.getTransactionInfo();
				result = processTransactionInfo(transactionInfo,transaction);
			} else {
				new Libra4jError(Type.MISSING_DATA,"expected field missing in " + accountTransactionBySequenceNumberResponse.getClass().getCanonicalName() + ": " + GrpcField.SIGNED_TRANSACTION);
			}
		} else {
			new Libra4jError(Type.MISSING_DATA,"expected field missing in " + responseItem.getClass().getCanonicalName() + ": " + GrpcField.GET_ACCOUNT_TRANSACTION_BY_SEQUENCE_NUMBER_RESPONSE_PROOF);
		}
		return result;
	}
	
	public void getEventsbyEventAccessPath(AccountAddress accountAddress, long count) {
		AccessPath accessPath = AccessPath.newBuilder()
				.setAddress(accountAddress.getByteString())
				.build();
		GetEventsByEventAccessPathRequest getEventsByAccesspathRequest = GetEventsByEventAccessPathRequest.newBuilder()
				.setAccessPath(accessPath)
				.setLimit(count)
				.build();
		RequestItem requestItem = RequestItem.newBuilder()
				.setGetEventsByEventAccessPathRequest(getEventsByAccesspathRequest)
				.build();
		ResponseItem responseItem = getResponseItem(requestItem);
		GetEventsByEventAccessPathResponse eventsByEventAccessPathResponse = responseItem.getGetEventsByEventAccessPathResponse();
		grpcChecker.checkFieldErrors(eventsByEventAccessPathResponse.findInitializationErrors(),eventsByEventAccessPathResponse.getUnknownFields());
		grpcChecker.checkExpectedFields(eventsByEventAccessPathResponse,1);
		AccountStateWithProof proofOfLatestEvent = eventsByEventAccessPathResponse.getProofOfLatestEvent();
		processAccounStateWithProof(proofOfLatestEvent);
		List<EventWithProof> eventsWithProofList = eventsByEventAccessPathResponse.getEventsWithProofList();
		if ((eventsWithProofList != null)
				&& (eventsWithProofList.size() > 0)) {
			for(EventWithProof eventWithProof : eventsWithProofList) {
				grpcChecker.checkFieldErrors(eventWithProof.findInitializationErrors(),eventWithProof.getUnknownFields());
				grpcChecker.checkExpectedFields(eventWithProof,2);
				Event provedEvent = eventWithProof.getEvent();
				processEvent(provedEvent);
				EventProof eventProof = eventWithProof.getProof();
				grpcChecker.checkFieldErrors(eventProof.findInitializationErrors(),eventProof.getUnknownFields());
				grpcChecker.checkExpectedFields(eventProof,2);
				TransactionInfo transactionInfo = eventProof.getTransactionInfo();
				processTransactionInfo(transactionInfo);
				AccumulatorProof ledgerInfoToTransactionInfoProof = eventProof.getLedgerInfoToTransactionInfoProof();
				processAccumulatorProof(ledgerInfoToTransactionInfoProof);
			}		
		}
	}
	
	public AccountState getAccountState(AccountAddress accountAddress) {
		AccountState result = new AccountState();
		GetAccountStateRequest getAccountStateRequest = GetAccountStateRequest.newBuilder()
				.setAddress(accountAddress.getByteString())
				.build();
		RequestItem requestItem = RequestItem.newBuilder()
				.setGetAccountStateRequest(getAccountStateRequest)
				.build();
		ResponseItem responseItem = getResponseItem(requestItem);
		GetAccountStateResponse accountStateResponse = responseItem.getGetAccountStateResponse();
		if (accountStateResponse != null) {
			grpcChecker.checkFieldErrors(accountStateResponse.findInitializationErrors(),accountStateResponse.getUnknownFields());
			grpcChecker.checkExpectedFields(accountStateResponse,1);
			AccountStateWithProof accountStateWithProof = accountStateResponse.getAccountStateWithProof();
			if (accountStateWithProof != null) {
				result = processAccounStateWithProof(accountStateWithProof);
			}
		}
		return result;
	}
	
	public com.chaingrok.libra4j.types.LedgerInfo getLedgerInfo() {
		if (getLedgerInfoWithSignatures() == null) {
			getTransaction(1L,false); //should be needed only if 1st request on ledger otherwise last request is used
		}
		if (getLedgerInfoWithSignatures() == null) {
			new Libra4jError(Type.NULL_DATA,"ledger info was not obtained");
		}
		return processLedgerInfo(getLedgerInfoWithSignatures());
	}
	
	private com.chaingrok.libra4j.types.LedgerInfo processLedgerInfo(LedgerInfoWithSignatures ledgerInfoWithSignatures) {
		com.chaingrok.libra4j.types.LedgerInfo result = new com.chaingrok.libra4j.types.LedgerInfo();
		//
		LedgerInfo ledgerInfo = ledgerInfoWithSignatures.getLedgerInfo();
		grpcChecker.checkFieldErrors(ledgerInfo.findInitializationErrors(),ledgerInfo.getUnknownFields());
		grpcChecker.checkExpectedFields(ledgerInfo,5);
		grpcChecker.checkLedgerInfo(ledgerInfo);
		result.setConsensusBlockId(new Hash(ledgerInfo.getConsensusBlockId().toByteArray()));
		result.setConsensusDataHash(new Hash(ledgerInfo.getConsensusDataHash()));
		result.setTransactionAccumulatorHash(new Hash(ledgerInfo.getTransactionAccumulatorHash()));
		result.setEpochNum(ledgerInfo.getEpochNum());
		result.setTimestampUsecs(ledgerInfo.getTimestampUsecs());
		result.setVersion(ledgerInfo.getVersion());
		//
		List<ValidatorSignature> signaturesList = ledgerInfoWithSignatures.getSignaturesList();
		if (signaturesList.size() > 0 ) {
			ArrayList<Validator> validators = new ArrayList<Validator>();
			for(ValidatorSignature signature : signaturesList) {
				grpcChecker.checkFieldErrors(signature.findInitializationErrors(),signature.getUnknownFields());
				grpcChecker.checkExpectedFields(signature,2);
				Validator validator = new Validator();
				validator.setValidatorId(new ValidatorId(signature.getValidatorId()));
				validator.setSignature(new Signature(signature.getSignature()));
				validators.add(validator);
			}
			result.setValidators(validators);
		}
		//
		return result;
	}

	private LedgerInfoWithSignatures getLedgerInfoWithSignatures() {
		return ledgerInfoWithSignatures;
	}

	private void setLedgerInfoWithSignatures(LedgerInfoWithSignatures ledgerInfoWithSignatures) {
		this.ledgerInfoWithSignatures = ledgerInfoWithSignatures;
	}
	
	private Transaction processTransactionInfo(TransactionInfo transactionInfo) {
		return processTransactionInfo(transactionInfo,null);
	}
	
	private Transaction processTransactionInfo(TransactionInfo transactionInfo,Transaction transaction) {
		Transaction result = null;
		if (transaction == null) {
			result = new Transaction();
		} else {
			result = transaction;
		}
		if (transactionInfo != null) {
			grpcChecker.checkExpectedFields(transactionInfo,3,4);
			result.setSignedTransactionHash(new Hash(transactionInfo.getSignedTransactionHash()));
			result.setEventRootHash(new Hash(transactionInfo.getEventRootHash()));
			result.setStateRootHash(new Hash(transactionInfo.getStateRootHash()));
			result.setGasUsed(transactionInfo.getGasUsed());
			result.setTxnInfoSerializedSize(transactionInfo.getSerializedSize());
		} else {
			new Libra4jError(Type.MISSING_DATA,"transaction info is null");
		}
		return result;
	}
	
	private Transaction processSignedTransaction(SignedTransaction signedTransaction) {
		return processSignedTransaction(signedTransaction,null);
	}
	
	private Transaction processSignedTransaction(SignedTransaction signedTransaction,Transaction transaction) {
		Transaction result = null;
		if (transaction == null) {
			result = new Transaction();
		} else {
			result = transaction;
		}
		if (signedTransaction != null) {
			grpcChecker.checkExpectedFields(signedTransaction,3);
			result.setSignedTxnSerializedSize(signedTransaction.getSerializedSize());
			//transaction.setSenderPublicKey(KeyPair.publicKeyFromByteString(signedTransaction.getSenderPublicKey()));
			byte[] signatureBytes = signedTransaction.getSenderSignature().toByteArray();
			if (signatureBytes.length == 0) {
				new Libra4jError(Type.MISSING_DATA,"missing signature for transaction: " + result.getVersion());
			} else {
				result.setSignature(new Signature(signatureBytes));
			}
			ByteString rawTxnByteString = signedTransaction.getRawTxnBytes();
			result.setRawTxnBytes(rawTxnByteString.toByteArray());
			RawTransaction rawTransaction;
			try {
				rawTransaction = RawTransaction.newBuilder()
					.mergeFrom(rawTxnByteString)
					.build();
			} catch (InvalidProtocolBufferException e) {
				throw new Libra4jException(e);
			}
			if (rawTransaction != null) {
				grpcChecker.checkExpectedFields(rawTransaction,3,6);
				result.setSenderAccountAddress(new AccountAddress(rawTransaction.getSenderAccount()));
				result.setSequenceNumber(rawTransaction.getSequenceNumber());
				result.setMaxGasAmount(rawTransaction.getMaxGasAmount());
				result.setExpirationTime(rawTransaction.getExpirationTime());
				result.setGasUnitPrice(rawTransaction.getGasUnitPrice());
				Program rawTransactionProgram = rawTransaction.getProgram();
				com.chaingrok.libra4j.types.Program program = new com.chaingrok.libra4j.types.Program();
				if (rawTransactionProgram != null) {
					grpcChecker.checkExpectedFields(rawTransactionProgram,2);
					program.setCode(new Code(rawTransactionProgram.getCode().toByteArray()));
					List<ByteString> modulesList = rawTransactionProgram.getModulesList();
					if (modulesList.size() != 0) {
						ArrayList<Module> modules = new ArrayList<Module>();
						for (ByteString module : modulesList) {
							modules.add(new Module(module.toByteArray()));
						}
						program.setModules(modules);
					}
					List<TransactionArgument> programArgumentsList = rawTransactionProgram.getArgumentsList();
					if (programArgumentsList != null) {
						ArrayList<Argument> arguments = new ArrayList<Argument>();
						for(TransactionArgument programArgument : programArgumentsList) {
							grpcChecker.checkExpectedFields(programArgument,1,2);
							Argument argument = new Argument();
							argument.setType(Argument.Type.get(programArgument.getType()));
							argument.setData(programArgument.getData().toByteArray());
							arguments.add(argument);
						}
						program.setArguments(arguments);
					}
					result.setProgram(program);
					result.setType(); //to set & check the type;
				} else {
					new Libra4jError(Type.MISSING_DATA,"raw transaction is null");
				}
			}
		} else {
			new Libra4jError(Type.MISSING_DATA,"signed transaction is null");
		}
		return result;
	}

	private ArrayList<com.chaingrok.libra4j.types.Event> processEventsList(EventsList eventsList) {
		ArrayList<com.chaingrok.libra4j.types.Event> result = new ArrayList<com.chaingrok.libra4j.types.Event>();
		grpcChecker.checkExpectedFields(eventsList,1);
		List<Event> events = eventsList.getEventsList();
		if (events != null) {
			for(Event event : events) {
				result.add(processEvent(event));
			}
		}
		return result;
	}
	
	private com.chaingrok.libra4j.types.Event processEvent(Event event) {
		com.chaingrok.libra4j.types.Event result = new com.chaingrok.libra4j.types.Event();
		grpcChecker.checkExpectedFields(event,2,3);
		result.setData(new EventData(event.getEventData()));
		result.setSequenceNumber(event.getSequenceNumber());
		AccessPath respAccessPath = event.getAccessPath();
		if (respAccessPath != null) {
			grpcChecker.checkExpectedFields(respAccessPath,2);
			result.setAccessPath(new com.chaingrok.libra4j.types.AccessPath(respAccessPath.getPath()));
			result.setAddress(new AccountAddress(respAccessPath.getAddress().toByteArray()));
		
		} else {
			new Libra4jError(Type.MISSING_DATA,"event data is null");
		}
		return result;
	}
	
	private void processAccumulatorProof(AccumulatorProof accumulatorProof) {
		grpcChecker.checkFieldErrors(accumulatorProof.findInitializationErrors(),accumulatorProof.getUnknownFields());
		grpcChecker.checkExpectedFields(accumulatorProof,2);
		long bitmap = accumulatorProof.getBitmap();
		//result.setBitmap(Utils.longToByteArray(bitmap));
		List<ByteString> siblings = accumulatorProof.getNonDefaultSiblingsList();
		if( (siblings != null)
				&& (siblings.size() > 0)) {
			ArrayList<byte[]> nonDefaultSiblings = new ArrayList<byte[]>();
			for (ByteString sibling : siblings) {
				if (sibling.size() == 0) {
					new Libra4jError(Type.INVALID_LENGTH,"sibling size cannot be 0");
					nonDefaultSiblings.add(null);
				} else {
					nonDefaultSiblings.add(sibling.toByteArray());
				}
			}
			//result.setNonDefaultSiblings(nonDefaultSiblings);
		}
	}
	
	private AccountState processAccounStateWithProof(AccountStateWithProof accountStateWithProof) {
		AccountState result = new AccountState();
		grpcChecker.checkFieldErrors(accountStateWithProof.findInitializationErrors(),accountStateWithProof.getUnknownFields());
		grpcChecker.checkExpectedFields(accountStateWithProof,2,3);
		result.setVersion(accountStateWithProof.getVersion());
		AccountStateBlob accountStateBlob = accountStateWithProof.getBlob();
		grpcChecker.checkFieldErrors(accountStateBlob.findInitializationErrors(),accountStateBlob.getUnknownFields());
		grpcChecker.checkExpectedFields(accountStateBlob,1);
		result.setBlob(accountStateBlob.getBlob().toByteArray());
		AccountStateProof accountStateProof = accountStateWithProof.getProof();
		grpcChecker.checkFieldErrors(accountStateProof.findInitializationErrors(),accountStateProof.getUnknownFields());
		grpcChecker.checkExpectedFields(accountStateProof,3);
		AccumulatorProof ledgerInfoToTransactionInfoProof = accountStateProof.getLedgerInfoToTransactionInfoProof();
		grpcChecker.checkFieldErrors(ledgerInfoToTransactionInfoProof.findInitializationErrors(),ledgerInfoToTransactionInfoProof.getUnknownFields());
		grpcChecker.checkExpectedFields(ledgerInfoToTransactionInfoProof,2);
		long bitmap = ledgerInfoToTransactionInfoProof.getBitmap();
		result.setBitmap(Utils.longToByteArray(bitmap));
		List<ByteString> siblings = ledgerInfoToTransactionInfoProof.getNonDefaultSiblingsList();
		if ((siblings != null)
				&& (siblings.size() > 0)) {
			ArrayList<byte[]> nonDefaultSiblings = new ArrayList<byte[]>();
			for (ByteString sibling : siblings) {
				if (sibling.size() == 0) {
					new Libra4jError(Type.INVALID_LENGTH,"sibling size cannot be 0");
					nonDefaultSiblings.add(null);
				} else {
					nonDefaultSiblings.add(sibling.toByteArray());
				}
			}
			result.setNonDefaultSiblingsLedgerInfoToTransactionInfoProof(nonDefaultSiblings);
		}
		accountStateProof.getLedgerInfoToTransactionInfoProof();
		SparseMerkleProof transactionInfoToAccountProof = accountStateProof.getTransactionInfoToAccountProof();
		grpcChecker.checkFieldErrors(transactionInfoToAccountProof.findInitializationErrors(),transactionInfoToAccountProof.getUnknownFields());
		grpcChecker.checkExpectedFields(transactionInfoToAccountProof,3);
		transactionInfoToAccountProof.getBitmap().toByteArray();
		transactionInfoToAccountProof.getLeaf().toByteArray();
		siblings = transactionInfoToAccountProof.getNonDefaultSiblingsList(); 
		if( (siblings != null)
				&& (siblings.size() > 0)) {
			ArrayList<byte[]> nonDefaultSiblings = new ArrayList<byte[]>();
			for (ByteString sibling : siblings) {
				if (sibling.size() == 0) {
					new Libra4jError(Type.INVALID_LENGTH,"sibling size cannot be 0");
					nonDefaultSiblings.add(null);
				} else {
					nonDefaultSiblings.add(sibling.toByteArray());
				}
			}
			result.setNonDefaultSiblingsTransactionInfoToAccountProof(nonDefaultSiblings);
		}
		TransactionInfo transactionInfo = accountStateProof.getTransactionInfo();
		grpcChecker.checkFieldErrors(transactionInfo.findInitializationErrors(),transactionInfo.getUnknownFields());
		grpcChecker.checkExpectedFields(transactionInfo,3);
		Transaction transaction = new Transaction();
		transaction.setSignedTransactionHash(new Hash(transactionInfo.getSignedTransactionHash()));
		transaction.setEventRootHash(new Hash(transactionInfo.getEventRootHash()));
		transaction.setStateRootHash(new Hash(transactionInfo.getStateRootHash()));
		transaction.setGasUsed(transactionInfo.getGasUsed());
		transaction.setTxnInfoSerializedSize(transactionInfo.getSerializedSize());
		result.setTransaction(transaction);
		return result;
	}
	
	private ResponseItem getResponseItem(RequestItem requestItem) {
		ResponseItem result = null;
		UpdateToLatestLedgerRequest request = UpdateToLatestLedgerRequest.newBuilder()
				.addRequestedItems(requestItem)
				.build();
		//
		UpdateToLatestLedgerResponse response = validatorEndpoint.getBlockingStub().updateToLatestLedger(request);
		grpcChecker.checkFieldErrors(response.findInitializationErrors(),response.getUnknownFields());
		grpcChecker.checkExpectedFields(response,2);
		setLedgerInfoWithSignatures(response.getLedgerInfoWithSigs());
		List<ResponseItem> responseItemsList = response.getResponseItemsList();
		if (responseItemsList != null) {
			if (responseItemsList.size() > 0) {
				if (responseItemsList.size() > 1) {
					new Libra4jError(Type.TOO_MANY_ITEMS,responseItemsList);
				}
				ResponseItem responseItem = responseItemsList.get(0);
				grpcChecker.checkFieldErrors(responseItem.findInitializationErrors(),responseItem.getUnknownFields());
				grpcChecker.checkExpectedFields(responseItem,1);
				result = responseItem;
			}
		}
		++requestCount;
		return result;
	}
	
	public long getRequestCount() {
		return requestCount;
	}
}