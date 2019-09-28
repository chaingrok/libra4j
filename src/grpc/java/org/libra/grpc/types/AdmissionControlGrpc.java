package org.libra.grpc.types;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * -----------------------------------------------------------------------------
 * ---------------- Service definition
 * -----------------------------------------------------------------------------
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.21.0)",
    comments = "Source: admission_control.proto")
public final class AdmissionControlGrpc {

  private AdmissionControlGrpc() {}

  public static final String SERVICE_NAME = "admission_control.AdmissionControl";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest,
      org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse> getSubmitTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubmitTransaction",
      requestType = org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest.class,
      responseType = org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest,
      org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse> getSubmitTransactionMethod() {
    io.grpc.MethodDescriptor<org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest, org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse> getSubmitTransactionMethod;
    if ((getSubmitTransactionMethod = AdmissionControlGrpc.getSubmitTransactionMethod) == null) {
      synchronized (AdmissionControlGrpc.class) {
        if ((getSubmitTransactionMethod = AdmissionControlGrpc.getSubmitTransactionMethod) == null) {
          AdmissionControlGrpc.getSubmitTransactionMethod = getSubmitTransactionMethod = 
              io.grpc.MethodDescriptor.<org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest, org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "admission_control.AdmissionControl", "SubmitTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AdmissionControlMethodDescriptorSupplier("SubmitTransaction"))
                  .build();
          }
        }
     }
     return getSubmitTransactionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest,
      org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse> getUpdateToLatestLedgerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateToLatestLedger",
      requestType = org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest.class,
      responseType = org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest,
      org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse> getUpdateToLatestLedgerMethod() {
    io.grpc.MethodDescriptor<org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest, org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse> getUpdateToLatestLedgerMethod;
    if ((getUpdateToLatestLedgerMethod = AdmissionControlGrpc.getUpdateToLatestLedgerMethod) == null) {
      synchronized (AdmissionControlGrpc.class) {
        if ((getUpdateToLatestLedgerMethod = AdmissionControlGrpc.getUpdateToLatestLedgerMethod) == null) {
          AdmissionControlGrpc.getUpdateToLatestLedgerMethod = getUpdateToLatestLedgerMethod = 
              io.grpc.MethodDescriptor.<org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest, org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "admission_control.AdmissionControl", "UpdateToLatestLedger"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AdmissionControlMethodDescriptorSupplier("UpdateToLatestLedger"))
                  .build();
          }
        }
     }
     return getUpdateToLatestLedgerMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AdmissionControlStub newStub(io.grpc.Channel channel) {
    return new AdmissionControlStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AdmissionControlBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AdmissionControlBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AdmissionControlFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AdmissionControlFutureStub(channel);
  }

  /**
   * <pre>
   * -----------------------------------------------------------------------------
   * ---------------- Service definition
   * -----------------------------------------------------------------------------
   * </pre>
   */
  public static abstract class AdmissionControlImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Public API to submit transaction to a validator.
     * </pre>
     */
    public void submitTransaction(org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest request,
        io.grpc.stub.StreamObserver<org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSubmitTransactionMethod(), responseObserver);
    }

    /**
     * <pre>
     * This API is used to update the client to the latest ledger version and
     * optionally also request 1..n other pieces of data.  This allows for batch
     * queries.  All queries return proofs that a client should check to validate
     * the data. Note that if a client only wishes to update to the latest
     * LedgerInfo and receive the proof of this latest version, they can simply
     * omit the requested_items (or pass an empty list)
     * </pre>
     */
    public void updateToLatestLedger(org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest request,
        io.grpc.stub.StreamObserver<org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateToLatestLedgerMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSubmitTransactionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest,
                org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse>(
                  this, METHODID_SUBMIT_TRANSACTION)))
          .addMethod(
            getUpdateToLatestLedgerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest,
                org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse>(
                  this, METHODID_UPDATE_TO_LATEST_LEDGER)))
          .build();
    }
  }

  /**
   * <pre>
   * -----------------------------------------------------------------------------
   * ---------------- Service definition
   * -----------------------------------------------------------------------------
   * </pre>
   */
  public static final class AdmissionControlStub extends io.grpc.stub.AbstractStub<AdmissionControlStub> {
    private AdmissionControlStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AdmissionControlStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdmissionControlStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AdmissionControlStub(channel, callOptions);
    }

    /**
     * <pre>
     * Public API to submit transaction to a validator.
     * </pre>
     */
    public void submitTransaction(org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest request,
        io.grpc.stub.StreamObserver<org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSubmitTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * This API is used to update the client to the latest ledger version and
     * optionally also request 1..n other pieces of data.  This allows for batch
     * queries.  All queries return proofs that a client should check to validate
     * the data. Note that if a client only wishes to update to the latest
     * LedgerInfo and receive the proof of this latest version, they can simply
     * omit the requested_items (or pass an empty list)
     * </pre>
     */
    public void updateToLatestLedger(org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest request,
        io.grpc.stub.StreamObserver<org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateToLatestLedgerMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * -----------------------------------------------------------------------------
   * ---------------- Service definition
   * -----------------------------------------------------------------------------
   * </pre>
   */
  public static final class AdmissionControlBlockingStub extends io.grpc.stub.AbstractStub<AdmissionControlBlockingStub> {
    private AdmissionControlBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AdmissionControlBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdmissionControlBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AdmissionControlBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Public API to submit transaction to a validator.
     * </pre>
     */
    public org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse submitTransaction(org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest request) {
      return blockingUnaryCall(
          getChannel(), getSubmitTransactionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * This API is used to update the client to the latest ledger version and
     * optionally also request 1..n other pieces of data.  This allows for batch
     * queries.  All queries return proofs that a client should check to validate
     * the data. Note that if a client only wishes to update to the latest
     * LedgerInfo and receive the proof of this latest version, they can simply
     * omit the requested_items (or pass an empty list)
     * </pre>
     */
    public org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse updateToLatestLedger(org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateToLatestLedgerMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * -----------------------------------------------------------------------------
   * ---------------- Service definition
   * -----------------------------------------------------------------------------
   * </pre>
   */
  public static final class AdmissionControlFutureStub extends io.grpc.stub.AbstractStub<AdmissionControlFutureStub> {
    private AdmissionControlFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AdmissionControlFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdmissionControlFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AdmissionControlFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Public API to submit transaction to a validator.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse> submitTransaction(
        org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSubmitTransactionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * This API is used to update the client to the latest ledger version and
     * optionally also request 1..n other pieces of data.  This allows for batch
     * queries.  All queries return proofs that a client should check to validate
     * the data. Note that if a client only wishes to update to the latest
     * LedgerInfo and receive the proof of this latest version, they can simply
     * omit the requested_items (or pass an empty list)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse> updateToLatestLedger(
        org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateToLatestLedgerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBMIT_TRANSACTION = 0;
  private static final int METHODID_UPDATE_TO_LATEST_LEDGER = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AdmissionControlImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AdmissionControlImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBMIT_TRANSACTION:
          serviceImpl.submitTransaction((org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionRequest) request,
              (io.grpc.stub.StreamObserver<org.libra.grpc.types.AdmissionControlOuterClass.SubmitTransactionResponse>) responseObserver);
          break;
        case METHODID_UPDATE_TO_LATEST_LEDGER:
          serviceImpl.updateToLatestLedger((org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerRequest) request,
              (io.grpc.stub.StreamObserver<org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AdmissionControlBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AdmissionControlBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.libra.grpc.types.AdmissionControlOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AdmissionControl");
    }
  }

  private static final class AdmissionControlFileDescriptorSupplier
      extends AdmissionControlBaseDescriptorSupplier {
    AdmissionControlFileDescriptorSupplier() {}
  }

  private static final class AdmissionControlMethodDescriptorSupplier
      extends AdmissionControlBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AdmissionControlMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AdmissionControlGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AdmissionControlFileDescriptorSupplier())
              .addMethod(getSubmitTransactionMethod())
              .addMethod(getUpdateToLatestLedgerMethod())
              .build();
        }
      }
    }
    return result;
  }
}
