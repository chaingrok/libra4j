package com.chaingrok.libra4j.grpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse;
import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfo;
import org.libra.grpc.types.LedgerInfoOuterClass.ValidatorSignature;

import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Hash;
import com.chaingrok.libra4j.types.Signature;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Int64Value;
import com.google.protobuf.Int32Value;
import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.UInt32ValueOrBuilder;
import com.google.protobuf.UInt64ValueOrBuilder;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.UnknownFieldSet.Field;

public class GrpcChecker {
	
	public final MandatoryObjectFieldsMap MANDATORY_OBJECT_FIELDS_MAP = new MandatoryObjectFieldsMap();
	
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
		if (signed.size() == 0) {
			new Libra4jError(Type.MISSING_DATA,"validator signature invalid: signed is null");
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
		if (!(object instanceof MessageOrBuilder)) {
			if (object != null) {
				new Libra4jError(Type.INVALID_CLASS,"trying to check fields of non MessageOrBuilder: " + object.getClass().getCanonicalName());
			} else {
				new Libra4jError(Type.MISSING_DATA,"trying to check fields of null object");
			}
			result=false;
		} else {
			MessageOrBuilder messageOrBuilder = (MessageOrBuilder) object;
			checkFieldErrors(messageOrBuilder.findInitializationErrors(),messageOrBuilder.getUnknownFields());
			Class<? extends MessageOrBuilder> grpcClass = messageOrBuilder.getClass();
			Map<FieldDescriptor, Object> fieldDescriptors = messageOrBuilder.getAllFields();
			if (fieldDescriptors.size() != 0) {
				for (FieldDescriptor fieldDescriptor : fieldDescriptors.keySet()) {
					boolean fieldResult = checkFieldDescriptor(grpcClass,fieldDescriptor,fieldDescriptors);
					if (!fieldResult) {
						result = false;
					}
				}
				if ((fieldDescriptors.size() < minExpectedItemsCount) 
						|| (fieldDescriptors.size() >  maxExpectedItemsCount)) {
					new Libra4jError(Type.INVALID_COUNT,"count different from expected for " + object.getClass() + ": " + minExpectedItemsCount + " <= " + fieldDescriptors.size() + " <= " + maxExpectedItemsCount);
					result = false;
				}
				if (result == null) {
					result = true;
				}
			}
		}
		if (result != null ) {
			if (result) {
				result = checkMandatoryFields((MessageOrBuilder)object); 
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
	
	public boolean checkFieldDescriptor(Class<?> grpcClass,FieldDescriptor fieldDescriptor, Map<FieldDescriptor,Object> fieldDescriptors) {
		return checkFieldDescriptor(grpcClass,fieldDescriptor.getFullName(),fieldDescriptor.isRepeated(),fieldDescriptors.get(fieldDescriptor));
	}
	
	public boolean checkFieldDescriptor(Class<?> grpcClass,String fieldFullName,Boolean fieldIsRepeated,Object fieldObject) {
		Boolean result = null;
		GrpcField grpcField = GrpcField.get(fieldFullName);
		if (grpcField == null) {
			new Libra4jError(Type.UNKNOWN_VALUE,"no GrpcField for fullName: " + fieldFullName);
			result = false;
		} else {
			if (grpcClass != null) {
				if (!grpcField.getParentFieldClass().equals(grpcClass)) {
					new Libra4jError(Type.INVALID_CLASS,"field descriptor class is invalid: " + fieldFullName + ": " 
							+ grpcClass.getCanonicalName()
							+ " <> " 
							+ grpcField.getParentFieldClass().getCanonicalName());
					result = false;
				}
			}
			if (fieldObject != null) {
				if (grpcField.getFieldClass().isAssignableFrom(fieldObject.getClass())) {
					result = true;
				} else if (fieldObject instanceof MessageOrBuilder) {
					result = checkMessageOrBuilderFieldDescriptor(grpcField,fieldObject);
				} else if (fieldIsRepeated) {
					result = checkRepeatedFieldDescriptor(grpcField,fieldObject);
				} else if ((fieldObject instanceof UInt64ValueOrBuilder)
							|| (fieldObject instanceof Int64Value)) {
					result = checkInt64FieldDescriptor(grpcField,fieldObject);
				} else if ((fieldObject instanceof UInt32ValueOrBuilder)
							|| (fieldObject instanceof Int32Value)) {
					result = checkInt32FieldDescriptor(grpcField,fieldObject);
				} else {
					new Libra4jError(Type.INVALID_CLASS,"field type checking is not implemented: " + fieldFullName + " (object class: " + fieldObject.getClass().getCanonicalName() + ")");
					result = false;
				}
			} else {
				new Libra4jError(Type.MISSING_DATA,"field object is null");
				result = false;
			}
		}
		return result;
	}
	
	public boolean checkMessageOrBuilderFieldDescriptor(GrpcField grpcField,Object fieldObject) {
		boolean result = true;
		MessageOrBuilder fieldObjetMessageOfBuilder = (MessageOrBuilder) fieldObject;
		Message defaultFieldInstance = fieldObjetMessageOfBuilder.getDefaultInstanceForType();
		if (!grpcField.getFieldClass().equals(defaultFieldInstance.getClass())) {
			new Libra4jError(Type.INVALID_CLASS,"returned field class is invalid: " + grpcField.getFullName() + ": " 
					+ defaultFieldInstance.getClass().getCanonicalName()
					+ " <> " 
					+ grpcField.getFieldClass().getCanonicalName());
			result = false;
		} 
		return result;
	}
	
	public boolean checkRepeatedFieldDescriptor(GrpcField grpcField,Object fieldObject) {
		boolean result = true;
		@SuppressWarnings("rawtypes")
		List list = (List)fieldObject;
		if (list.size() > 0) {
			Object listObject = list.get(0);
			if (listObject instanceof MessageOrBuilder) {
				MessageOrBuilder listGrpcItem = (MessageOrBuilder) listObject;
				Message defaultListInstance = listGrpcItem.getDefaultInstanceForType();
				if (!grpcField.getFieldClass().equals(defaultListInstance.getClass())) {
					new Libra4jError(Type.INVALID_CLASS,"returned field class is invalid: " + grpcField.getFullName() + ": " 
							+ defaultListInstance.getClass().getCanonicalName()
							+ " <> " 
							+ grpcField.getFieldClass().getCanonicalName());
					result = false;
				} 
			} else {
				new Libra4jError(Type.INVALID_CLASS,"listObject is not instanceOf MessageOrBuilder: " + listObject.getClass().getCanonicalName());
				result = false;
			}
		}
		return result;
	}
	
	public boolean checkInt64FieldDescriptor(GrpcField grpcField,Object fieldObject) {
		boolean result = true;
		UInt64ValueOrBuilder i64GrpcItem = (UInt64ValueOrBuilder) fieldObject;
		Message i64DefaultInstance = i64GrpcItem.getDefaultInstanceForType();
		if (!grpcField.getFieldClass().equals(i64DefaultInstance.getClass())) {
			new Libra4jError(Type.INVALID_CLASS,"returned field class is invalid: " + grpcField.getFullName() + ": " 
						+ i64DefaultInstance.getClass().getCanonicalName()
						+ " <> " 
						+ grpcField.getFieldClass().getCanonicalName());
			result = false;
		} 
		return result;
	}
	
	public boolean checkInt32FieldDescriptor(GrpcField grpcField,Object fieldObject) {
		boolean result = true;
		UInt32ValueOrBuilder i32GrpcItem = (UInt32ValueOrBuilder) fieldObject;
		Message i32DefaultInstance = i32GrpcItem.getDefaultInstanceForType();
		if (!grpcField.getFieldClass().equals(i32DefaultInstance.getClass())) {
			new Libra4jError(Type.INVALID_CLASS,"returned field class is invalid: " + grpcField.getFullName() + ": " 
						+ i32DefaultInstance.getClass().getCanonicalName()
						+ " <> " 
						+ grpcField.getFieldClass().getCanonicalName());
			result = false;
		} 
		return result;
	}
	
	public boolean checkMandatoryFields(MessageOrBuilder messageOrBuilder) {
		boolean result = false;
		if (messageOrBuilder != null) {
			result = true;
			//Map<FieldDescriptor, Object> fieldDescriptorMap = objectFieldsMap.get(object);
			Map<FieldDescriptor, Object> fieldDescriptorMap = messageOrBuilder.getAllFields();
			ArrayList<GrpcField> mandatoryFields = MANDATORY_OBJECT_FIELDS_MAP.get(messageOrBuilder.getClass());
			if (mandatoryFields != null) {
				/*
				//System.out.println("cheking of mandatory fields for " + object.getClass().getCanonicalName());
				if (fieldDescriptorMap == null) {
					result = false;
					new Libra4jError(Type.NULL_DATA,"field descriptor map not set for object: " + object.getClass().getCanonicalName());
				} else {*/
				for (GrpcField mandatoryField : mandatoryFields) {
					if (!isFieldSet(mandatoryField,messageOrBuilder,fieldDescriptorMap)) {
							result = false; //no loop break to collect all potential errors
							new Libra4jError(Type.MISSING_DATA,"mandatory field missing for " + messageOrBuilder.getClass().getCanonicalName() + ":" + mandatoryField);
					}
				}
				//}
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
	
	public boolean isFieldSet(GrpcField field,Object object,Map<FieldDescriptor, Object> objectFieldsMap) {
		boolean result = false;
		if (field != null) {
			String fullName = field.getFullName();
			if (object != null)  {
				if (objectFieldsMap == null) {
					new Libra4jError(Type.NULL_DATA,"field descriptor map not set for object: " + object.getClass().getCanonicalName());
				} else {
					for (FieldDescriptor fieldDescriptor : objectFieldsMap.keySet()) {
						if (fieldDescriptor.getFullName().equals(fullName)) {
							result = true;
							break;
						}
					}
				}
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
	public class MandatoryObjectFieldsMap extends HashMap<Class<?>,ArrayList<GrpcField>> {
		
		{
			ArrayList<GrpcField> list = new ArrayList<GrpcField>();
			list.add(GrpcField.RESPONSE_ITEMS);
			list.add(GrpcField.LEDGER_INFO_WITH_SIGS);
			this.put(UpdateToLatestLedgerResponse.class,list);
		}
		
	}

}
