package com.chaingrok.libra4j.grpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfo;
import org.libra.grpc.types.LedgerInfoOuterClass.ValidatorSignature;

import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Libra4jInfo;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Hash;
import com.chaingrok.libra4j.types.Signature;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Int64ValueOrBuilder;
import com.google.protobuf.Int32ValueOrBuilder;
import com.google.protobuf.Int32Value;
import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.UnknownFieldSet.Field;

public class GrpcChecker {
	
	private final MandatoryObjectFieldsMap MANDATORY_OBJECT_FIELDS_MAP = new MandatoryObjectFieldsMap();
	private HashMap<Object,Map<FieldDescriptor, Object>> objectFieldsMap =  new HashMap<Object,Map<FieldDescriptor, Object>>();
	
	
	public boolean checkLedgerInfo(LedgerInfo ledgerInfo) {
		boolean result = true;
		long version = ledgerInfo.getVersion();
		if (version <= 0) {
			new Libra4jError(Type.INVALID_VERSION,"ledger info version invalid:" + version);
			result = false;
		}
		long timestamp = ledgerInfo.getTimestampUsecs();
		if (timestamp <= 0) {
			new Libra4jError(Type.INVALID_TIMESTAMP,"timestamp invalid:" + timestamp);
			result = false;
		}
		new Hash(ledgerInfo.getConsensusBlockId());
		new Hash(ledgerInfo.getConsensusDataHash());
		new Hash(ledgerInfo.getTransactionAccumulatorHash());
		return result;
	}
	
	public boolean checkValidatorSignature(ValidatorSignature validatorSignature) {
		boolean result = true;
		new AccountAddress(validatorSignature.getValidatorId());
		ByteString signed = validatorSignature.getSignature();
		if (signed == null) {
			new Libra4jError(Type.INVALID_TIMESTAMP,"validator signature invalid: signed is null");
			result = false;
		} else {
			Signature signature = new Signature(signed);
			result = signature.isValid();
		}
		return result;
	}
	
	public boolean checkExpectedFields(Object object,int expectedItemsCount) {
		 return checkExpectedFields(object,expectedItemsCount,expectedItemsCount);
	}
	
	public boolean checkExpectedFields(Object object,int minExpectedItemsCount,int maxExpectedItemsCount) {
		Boolean result = null;
		if (object instanceof MessageOrBuilder) {
			MessageOrBuilder grpcItem = (MessageOrBuilder) object;
			Message message = grpcItem.getDefaultInstanceForType();
			Class<? extends MessageOrBuilder> grpcOwningClass = grpcItem.getClass();
			if (!grpcOwningClass.equals(message.getClass())) {
				throw new Libra4jException("classes are different for grpc item: " + grpcItem.getClass().getCanonicalName() + " <> " + message.getClass());
			}
			Map<FieldDescriptor, Object> fieldDescriptors = grpcItem.getAllFields();
			objectFieldsMap.put(object,fieldDescriptors);
			System.out.println(dumpFields(object,fieldDescriptors));
			int mapSize = fieldDescriptors.size();
			//System.out.println("   field map size: " + fieldDescriptorMap.size());
			if (fieldDescriptors.size() == 0) {
				result = true;
			} else {
				int count = 0;
				for (FieldDescriptor fieldDescriptor : fieldDescriptors.keySet()) {
					++count;
					String fieldFullName = fieldDescriptor.getFullName();
					GrpcField grpcField = GrpcField.get(fieldDescriptor.getFullName());
					if (grpcField != null) {
						System.out.println("grpc field being processed: " + grpcField.toString());
						if (!grpcField.getOwningClass().equals(grpcOwningClass)) {
							throw new Libra4jException("owning class is invalid for field: " + fieldFullName + ": " 
									+ grpcOwningClass.getCanonicalName()
									+ " <> " 
									+ grpcField.getOwningClass().getCanonicalName());
						}
						Object fieldObject = fieldDescriptors.get(fieldDescriptor);
						if (grpcField.getFieldClass().isAssignableFrom(fieldObject.getClass())) {
							/*
							System.out.println("   checked field " + fieldFullName + " -> " 
										+ fieldObject.getClass().getCanonicalName() 
										+ " (" + grpcField.getFieldClass().getCanonicalName() + ") - ok"); //fieldObject class may be an instance of an invisible subclass of grpcField.getFieldClass()
						    */
						} else if (fieldObject instanceof MessageOrBuilder) {
							MessageOrBuilder fieldGrpcItem = (MessageOrBuilder) fieldObject;
							Message defaultFieldInstance = fieldGrpcItem.getDefaultInstanceForType();
							if (!grpcField.getFieldClass().equals(defaultFieldInstance.getClass())) {
								throw new Libra4jException("returned field class is invalid: " + fieldFullName + ": " 
										+ defaultFieldInstance.getClass().getCanonicalName()
										+ " <> " 
										+ grpcField.getFieldClass().getCanonicalName());
							} else {
								System.out.println("   checked messageOrBuilder field " + fieldFullName + " -> " + defaultFieldInstance.getClass().getCanonicalName() + " - ok");
							}
						} else if (fieldDescriptor.isRepeated()) {
							@SuppressWarnings("rawtypes")
							List list = (List)fieldObject;
							Object listObject = list.get(0);
							if (listObject instanceof MessageOrBuilder) {
								MessageOrBuilder listGrpcItem = (MessageOrBuilder) listObject;
								Message defaultListInstance = listGrpcItem.getDefaultInstanceForType();
								if (!grpcField.getFieldClass().equals(defaultListInstance.getClass())) {
									throw new Libra4jException("returned field class is invalid: " + fieldFullName + ": " 
											+ defaultListInstance.getClass().getCanonicalName()
											+ " <> " 
											+ grpcField.getFieldClass().getCanonicalName());
								} /*else {
									System.out.println("   checked repeated field " + fieldFullName + " -> " + defaultListInstance.getClass().getCanonicalName() + " - ok");
								}*/
							}
						} else if (fieldObject instanceof Int64ValueOrBuilder) {
							 Int64ValueOrBuilder i64GrpcItem = (Int64ValueOrBuilder) fieldObject;
							 Message i64DefaultInstance = i64GrpcItem.getDefaultInstanceForType();
							 if (!grpcField.getFieldClass().equals(i64DefaultInstance.getClass())) {
									throw new Libra4jException("returned field class is invalid: " + fieldFullName + ": " 
											+ i64DefaultInstance.getClass().getCanonicalName()
											+ " <> " 
											+ grpcField.getFieldClass().getCanonicalName());
								} else {
									System.out.println("   checked i64OrBuilder field " + fieldFullName + " -> " + i64DefaultInstance.getClass().getCanonicalName() + " - ok");
								}
						} /*else if (fieldObject instanceof Int64Value) {
							 Int64Value i64GrpcItem = (Int64Value) fieldObject;
							 Message i64DefaultInstance = i64GrpcItem.getDefaultInstanceForType();
							 if (!grpcField.getFieldClass().equals(i64DefaultInstance.getClass())) {
									throw new Libra4jException("returned field class is invalid: " + fieldFullName + ": " 
											+ i64DefaultInstance.getClass().getCanonicalName()
											+ " <> " 
											+ grpcField.getFieldClass().getCanonicalName());
								} else {
									System.out.println("   checked i64 field " + fieldFullName + " -> " + i64DefaultInstance.getClass().getCanonicalName() + " - ok");
								}
						} */ else if (fieldObject instanceof Int32ValueOrBuilder) {
							 new Libra4jInfo(Type.UNKNOWN_VALUE,"Int32ValueOrBuilder" + fieldObject.getClass().getCanonicalName()); //TODO: remove
							 Int32ValueOrBuilder i32GrpcItem = (Int32ValueOrBuilder) fieldObject;
							 Message i32DefaultInstance = i32GrpcItem.getDefaultInstanceForType();
							 if (!grpcField.getFieldClass().equals(i32DefaultInstance.getClass())) {
									throw new Libra4jException("returned field class is invalid: " + fieldFullName + ": " 
											+ i32DefaultInstance.getClass().getCanonicalName()
											+ " <> " 
											+ grpcField.getFieldClass().getCanonicalName());
								} else {
									System.out.println("   checked i32 field " + fieldFullName + " -> " + i32DefaultInstance.getClass().getCanonicalName() + " - ok");
								}
						} else if (fieldObject instanceof Int32Value) {
							 new Libra4jInfo(Type.UNKNOWN_VALUE,"Int32Value" + fieldObject.getClass().getCanonicalName()); //TODO: remove
							 Int32Value i32GrpcItem = (Int32Value) fieldObject;
							 Message i32DefaultInstance = i32GrpcItem.getDefaultInstanceForType();
							 if (!grpcField.getFieldClass().equals(i32DefaultInstance.getClass())) {
									throw new Libra4jException("returned field class is invalid: " + fieldFullName + ": " 
											+ i32DefaultInstance.getClass().getCanonicalName()
											+ " <> " 
											+ grpcField.getFieldClass().getCanonicalName());
								} else {
									System.out.println("   checked i32 field " + fieldFullName + " -> " + i32DefaultInstance.getClass().getCanonicalName() + " - ok");
								}
						} else {
							throw new Libra4jException("field type checking is not implemented: " + fieldFullName + " (object class: " + fieldObject.getClass().getCanonicalName() + ")");
						}
					} else {
						result = false;
					}
				}
				if (count != mapSize) {
					new Libra4jError(Type.INVALID_COUNT,"issue in traversing field map: " + count + " <> " + mapSize);
					result = false;
				}
				if ((count < minExpectedItemsCount) 
						|| (count >  maxExpectedItemsCount)) {
					new Libra4jError(Type.INVALID_COUNT,"count different from expected for " + object.getClass() + ": " + minExpectedItemsCount + " <= " + count + " <= " + maxExpectedItemsCount);
					result = false;
				}
				if (result == null) {
					result = true;
				}
			}
		}
		if (result != null) {
			if (result) {
				result = checkMandatoryFields(object);
			} 
		} else {
			if ((minExpectedItemsCount == 0)
					&& (maxExpectedItemsCount == 0)) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}
	
	public boolean checkMandatoryFields(Object object) {
		//System.out.println("entering checkMandatoryFields() ...");
		boolean result = false;
		if (object != null) {
			result = true;
			Map<FieldDescriptor, Object> fieldDescriptorMap = objectFieldsMap.get(object);
			ArrayList<GrpcField> mandatoryFields = MANDATORY_OBJECT_FIELDS_MAP.get(object.getClass());
			if (mandatoryFields != null) {
				//System.out.println("cheking of mandatory fields for " + object.getClass().getCanonicalName());
				if (fieldDescriptorMap == null) {
					result = false;
					new Libra4jError(Type.NULL_DATA,"field descriptor map not set for object: " + object.getClass().getCanonicalName());
				} else {
					for (GrpcField mandatoryField : mandatoryFields) {
						if (!isFieldSet(object,mandatoryField)) {
							result = false; //no loop break to collect all potential errors
							new Libra4jError(Type.MISSING_DATA,"mandatory field missing for" + object.getClass().getCanonicalName() + ":" + mandatoryField);
						}
					}
				}
			}
		}
		return result;
	}
	
	public boolean checkFieldErrors(List<String> initializationErrors,UnknownFieldSet unknownFieldSet) {
		Boolean result = null;
		if (unknownFieldSet != null) {
			Map<Integer, Field> map = unknownFieldSet.asMap();
			if (map.keySet().size() != 0) {
				new Libra4jError(Type.INIT_ERROR,unknownFieldSet);
				result = false;
			}
		} 
		if ((initializationErrors != null) 
			&& (initializationErrors.size() != 0)) {
			new Libra4jError(Type.INIT_ERROR,initializationErrors);
			result = false;
		}
		if (result == null) {
			result = true;
		}
		return result;
	}
	
	public boolean isFieldSet(Object object,GrpcField field) {
		boolean result = false;
		String fullName = field.getFullName();
		Map<FieldDescriptor, Object> fieldDescriptorMap = objectFieldsMap.get(object);
		if (fieldDescriptorMap == null) {
			new Libra4jError(Type.NULL_DATA,"field descriptor map not set for object: " + object.getClass().getCanonicalName());
		}
		for (FieldDescriptor fieldDescriptor : fieldDescriptorMap.keySet()) {
			if (fieldDescriptor.getFullName().equals(fullName)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public String dumpFields(Object object, Map<FieldDescriptor, Object> fieldDescriptorMap) {
		StringBuilder result = new StringBuilder();
		if (object != null) {
			result.append("field analysis for : " + object.getClass().getCanonicalName());
			result.append("\n");
			if ((fieldDescriptorMap != null) 
					&& (fieldDescriptorMap.size()  > 0)) {
				for (FieldDescriptor fieldDescriptor : fieldDescriptorMap.keySet()) {
					result.append("   field name: " + fieldDescriptor.getFullName());
					result.append("\n");
				}
			}
		}
		return result.toString();
	}
	
	@SuppressWarnings("serial")
	private class MandatoryObjectFieldsMap extends HashMap<Class<?>,ArrayList<GrpcField>> {
		
		{
			ArrayList<GrpcField> list = new ArrayList<GrpcField>();
			list.add(GrpcField.RESPONSE_ITEMS);
			list.add(GrpcField.LEDGER_INFO_WITH_SIGS);
			this.put(org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse.class,list);
		}
		
	}

}
