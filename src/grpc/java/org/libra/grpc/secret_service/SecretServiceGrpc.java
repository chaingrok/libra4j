package org.libra.grpc.secret_service;

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
    comments = "Source: secret_service.proto")
public final class SecretServiceGrpc {

  private SecretServiceGrpc() {}

  public static final String SERVICE_NAME = "secret_service.SecretService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest,
      org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse> getGenerateKeyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateKey",
      requestType = org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest.class,
      responseType = org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest,
      org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse> getGenerateKeyMethod() {
    io.grpc.MethodDescriptor<org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest, org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse> getGenerateKeyMethod;
    if ((getGenerateKeyMethod = SecretServiceGrpc.getGenerateKeyMethod) == null) {
      synchronized (SecretServiceGrpc.class) {
        if ((getGenerateKeyMethod = SecretServiceGrpc.getGenerateKeyMethod) == null) {
          SecretServiceGrpc.getGenerateKeyMethod = getGenerateKeyMethod = 
              io.grpc.MethodDescriptor.<org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest, org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "secret_service.SecretService", "GenerateKey"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SecretServiceMethodDescriptorSupplier("GenerateKey"))
                  .build();
          }
        }
     }
     return getGenerateKeyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest,
      org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse> getGetPublicKeyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPublicKey",
      requestType = org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest.class,
      responseType = org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest,
      org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse> getGetPublicKeyMethod() {
    io.grpc.MethodDescriptor<org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest, org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse> getGetPublicKeyMethod;
    if ((getGetPublicKeyMethod = SecretServiceGrpc.getGetPublicKeyMethod) == null) {
      synchronized (SecretServiceGrpc.class) {
        if ((getGetPublicKeyMethod = SecretServiceGrpc.getGetPublicKeyMethod) == null) {
          SecretServiceGrpc.getGetPublicKeyMethod = getGetPublicKeyMethod = 
              io.grpc.MethodDescriptor.<org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest, org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "secret_service.SecretService", "GetPublicKey"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SecretServiceMethodDescriptorSupplier("GetPublicKey"))
                  .build();
          }
        }
     }
     return getGetPublicKeyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest,
      org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse> getSignMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Sign",
      requestType = org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest.class,
      responseType = org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest,
      org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse> getSignMethod() {
    io.grpc.MethodDescriptor<org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest, org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse> getSignMethod;
    if ((getSignMethod = SecretServiceGrpc.getSignMethod) == null) {
      synchronized (SecretServiceGrpc.class) {
        if ((getSignMethod = SecretServiceGrpc.getSignMethod) == null) {
          SecretServiceGrpc.getSignMethod = getSignMethod = 
              io.grpc.MethodDescriptor.<org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest, org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "secret_service.SecretService", "Sign"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SecretServiceMethodDescriptorSupplier("Sign"))
                  .build();
          }
        }
     }
     return getSignMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SecretServiceStub newStub(io.grpc.Channel channel) {
    return new SecretServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SecretServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SecretServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SecretServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SecretServiceFutureStub(channel);
  }

  /**
   * <pre>
   * -----------------------------------------------------------------------------
   * ---------------- Service definition
   * -----------------------------------------------------------------------------
   * </pre>
   */
  public static abstract class SecretServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * API to request key generation
     * </pre>
     */
    public void generateKey(org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest request,
        io.grpc.stub.StreamObserver<org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGenerateKeyMethod(), responseObserver);
    }

    /**
     * <pre>
     * API to request a public key
     * </pre>
     */
    public void getPublicKey(org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest request,
        io.grpc.stub.StreamObserver<org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetPublicKeyMethod(), responseObserver);
    }

    /**
     * <pre>
     * API to request a signature
     * </pre>
     */
    public void sign(org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest request,
        io.grpc.stub.StreamObserver<org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSignMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGenerateKeyMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest,
                org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse>(
                  this, METHODID_GENERATE_KEY)))
          .addMethod(
            getGetPublicKeyMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest,
                org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse>(
                  this, METHODID_GET_PUBLIC_KEY)))
          .addMethod(
            getSignMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest,
                org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse>(
                  this, METHODID_SIGN)))
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
  public static final class SecretServiceStub extends io.grpc.stub.AbstractStub<SecretServiceStub> {
    private SecretServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SecretServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecretServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SecretServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * API to request key generation
     * </pre>
     */
    public void generateKey(org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest request,
        io.grpc.stub.StreamObserver<org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGenerateKeyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * API to request a public key
     * </pre>
     */
    public void getPublicKey(org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest request,
        io.grpc.stub.StreamObserver<org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetPublicKeyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * API to request a signature
     * </pre>
     */
    public void sign(org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest request,
        io.grpc.stub.StreamObserver<org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSignMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * -----------------------------------------------------------------------------
   * ---------------- Service definition
   * -----------------------------------------------------------------------------
   * </pre>
   */
  public static final class SecretServiceBlockingStub extends io.grpc.stub.AbstractStub<SecretServiceBlockingStub> {
    private SecretServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SecretServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecretServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SecretServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * API to request key generation
     * </pre>
     */
    public org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse generateKey(org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest request) {
      return blockingUnaryCall(
          getChannel(), getGenerateKeyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * API to request a public key
     * </pre>
     */
    public org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse getPublicKey(org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetPublicKeyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * API to request a signature
     * </pre>
     */
    public org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse sign(org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest request) {
      return blockingUnaryCall(
          getChannel(), getSignMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * -----------------------------------------------------------------------------
   * ---------------- Service definition
   * -----------------------------------------------------------------------------
   * </pre>
   */
  public static final class SecretServiceFutureStub extends io.grpc.stub.AbstractStub<SecretServiceFutureStub> {
    private SecretServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SecretServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecretServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SecretServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * API to request key generation
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse> generateKey(
        org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGenerateKeyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * API to request a public key
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse> getPublicKey(
        org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetPublicKeyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * API to request a signature
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse> sign(
        org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSignMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GENERATE_KEY = 0;
  private static final int METHODID_GET_PUBLIC_KEY = 1;
  private static final int METHODID_SIGN = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SecretServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SecretServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GENERATE_KEY:
          serviceImpl.generateKey((org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyRequest) request,
              (io.grpc.stub.StreamObserver<org.libra.grpc.secret_service.SecretServiceOuterClass.GenerateKeyResponse>) responseObserver);
          break;
        case METHODID_GET_PUBLIC_KEY:
          serviceImpl.getPublicKey((org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyRequest) request,
              (io.grpc.stub.StreamObserver<org.libra.grpc.secret_service.SecretServiceOuterClass.PublicKeyResponse>) responseObserver);
          break;
        case METHODID_SIGN:
          serviceImpl.sign((org.libra.grpc.secret_service.SecretServiceOuterClass.SignRequest) request,
              (io.grpc.stub.StreamObserver<org.libra.grpc.secret_service.SecretServiceOuterClass.SignResponse>) responseObserver);
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

  private static abstract class SecretServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SecretServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.libra.grpc.secret_service.SecretServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SecretService");
    }
  }

  private static final class SecretServiceFileDescriptorSupplier
      extends SecretServiceBaseDescriptorSupplier {
    SecretServiceFileDescriptorSupplier() {}
  }

  private static final class SecretServiceMethodDescriptorSupplier
      extends SecretServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SecretServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SecretServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SecretServiceFileDescriptorSupplier())
              .addMethod(getGenerateKeyMethod())
              .addMethod(getGetPublicKeyMethod())
              .addMethod(getSignMethod())
              .build();
        }
      }
    }
    return result;
  }
}
