package pdytr.ftp.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: FTPService.proto")
public final class FtpGrpc {

  private FtpGrpc() {}

  public static final String SERVICE_NAME = "pdytr.ftp.grpc.Ftp";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<pdytr.ftp.grpc.FTPService.WriteRequest,
      pdytr.ftp.grpc.FTPService.ReadResponse> METHOD_WRITE =
      io.grpc.MethodDescriptor.<pdytr.ftp.grpc.FTPService.WriteRequest, pdytr.ftp.grpc.FTPService.ReadResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "pdytr.ftp.grpc.Ftp", "write"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pdytr.ftp.grpc.FTPService.WriteRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pdytr.ftp.grpc.FTPService.ReadResponse.getDefaultInstance()))
          .setSchemaDescriptor(new FtpMethodDescriptorSupplier("write"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<pdytr.ftp.grpc.FTPService.ReadRequest,
      pdytr.ftp.grpc.FTPService.WriteResponse> METHOD_READ =
      io.grpc.MethodDescriptor.<pdytr.ftp.grpc.FTPService.ReadRequest, pdytr.ftp.grpc.FTPService.WriteResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "pdytr.ftp.grpc.Ftp", "read"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pdytr.ftp.grpc.FTPService.ReadRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pdytr.ftp.grpc.FTPService.WriteResponse.getDefaultInstance()))
          .setSchemaDescriptor(new FtpMethodDescriptorSupplier("read"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FtpStub newStub(io.grpc.Channel channel) {
    return new FtpStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FtpBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FtpBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FtpFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FtpFutureStub(channel);
  }

  /**
   */
  public static abstract class FtpImplBase implements io.grpc.BindableService {

    /**
     */
    public void write(pdytr.ftp.grpc.FTPService.WriteRequest request,
        io.grpc.stub.StreamObserver<pdytr.ftp.grpc.FTPService.ReadResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_WRITE, responseObserver);
    }

    /**
     */
    public void read(pdytr.ftp.grpc.FTPService.ReadRequest request,
        io.grpc.stub.StreamObserver<pdytr.ftp.grpc.FTPService.WriteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_READ, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_WRITE,
            asyncUnaryCall(
              new MethodHandlers<
                pdytr.ftp.grpc.FTPService.WriteRequest,
                pdytr.ftp.grpc.FTPService.ReadResponse>(
                  this, METHODID_WRITE)))
          .addMethod(
            METHOD_READ,
            asyncUnaryCall(
              new MethodHandlers<
                pdytr.ftp.grpc.FTPService.ReadRequest,
                pdytr.ftp.grpc.FTPService.WriteResponse>(
                  this, METHODID_READ)))
          .build();
    }
  }

  /**
   */
  public static final class FtpStub extends io.grpc.stub.AbstractStub<FtpStub> {
    private FtpStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FtpStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FtpStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FtpStub(channel, callOptions);
    }

    /**
     */
    public void write(pdytr.ftp.grpc.FTPService.WriteRequest request,
        io.grpc.stub.StreamObserver<pdytr.ftp.grpc.FTPService.ReadResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_WRITE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void read(pdytr.ftp.grpc.FTPService.ReadRequest request,
        io.grpc.stub.StreamObserver<pdytr.ftp.grpc.FTPService.WriteResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_READ, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FtpBlockingStub extends io.grpc.stub.AbstractStub<FtpBlockingStub> {
    private FtpBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FtpBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FtpBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FtpBlockingStub(channel, callOptions);
    }

    /**
     */
    public pdytr.ftp.grpc.FTPService.ReadResponse write(pdytr.ftp.grpc.FTPService.WriteRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_WRITE, getCallOptions(), request);
    }

    /**
     */
    public pdytr.ftp.grpc.FTPService.WriteResponse read(pdytr.ftp.grpc.FTPService.ReadRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_READ, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FtpFutureStub extends io.grpc.stub.AbstractStub<FtpFutureStub> {
    private FtpFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FtpFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FtpFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FtpFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pdytr.ftp.grpc.FTPService.ReadResponse> write(
        pdytr.ftp.grpc.FTPService.WriteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_WRITE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pdytr.ftp.grpc.FTPService.WriteResponse> read(
        pdytr.ftp.grpc.FTPService.ReadRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_READ, getCallOptions()), request);
    }
  }

  private static final int METHODID_WRITE = 0;
  private static final int METHODID_READ = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FtpImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FtpImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_WRITE:
          serviceImpl.write((pdytr.ftp.grpc.FTPService.WriteRequest) request,
              (io.grpc.stub.StreamObserver<pdytr.ftp.grpc.FTPService.ReadResponse>) responseObserver);
          break;
        case METHODID_READ:
          serviceImpl.read((pdytr.ftp.grpc.FTPService.ReadRequest) request,
              (io.grpc.stub.StreamObserver<pdytr.ftp.grpc.FTPService.WriteResponse>) responseObserver);
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

  private static abstract class FtpBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FtpBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pdytr.ftp.grpc.FTPService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Ftp");
    }
  }

  private static final class FtpFileDescriptorSupplier
      extends FtpBaseDescriptorSupplier {
    FtpFileDescriptorSupplier() {}
  }

  private static final class FtpMethodDescriptorSupplier
      extends FtpBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FtpMethodDescriptorSupplier(String methodName) {
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
      synchronized (FtpGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FtpFileDescriptorSupplier())
              .addMethod(METHOD_WRITE)
              .addMethod(METHOD_READ)
              .build();
        }
      }
    }
    return result;
  }
}
