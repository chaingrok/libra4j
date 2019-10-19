package com.chaingrok.libra4j.types;


import java.util.concurrent.TimeUnit;

import org.libra.grpc.types.AdmissionControlGrpc;
import org.libra.grpc.types.AdmissionControlGrpc.AdmissionControlBlockingStub;
import org.libra.grpc.types.AdmissionControlGrpc.AdmissionControlStub;

import com.chaingrok.lib.ChaingrokException;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ValidatorEndpoint {
	
	private String dns = null;
	private int port = 0;
	
	private ManagedChannel channel;
	private AdmissionControlBlockingStub blockingStub = null;
	private AdmissionControlStub asyncStub = null;
	
	public ValidatorEndpoint(String dns, int port) {
		this.dns = dns;
		this.port = port;
		channel = ManagedChannelBuilder.forAddress(dns,port).usePlaintext().build();
		blockingStub = AdmissionControlGrpc.newBlockingStub(channel);
		asyncStub = AdmissionControlGrpc.newStub(channel);
	}

	public String getDns() {
		return dns;
	}

	public int getPort() {
		return port;
	}

	public AdmissionControlBlockingStub getBlockingStub() {
		return blockingStub;
	}

	public AdmissionControlStub getAsyncStub() {
		return asyncStub;
	}
	
	public boolean shutdown() {
		channel.shutdownNow();
		boolean terminated = false;
		while (!terminated) {
			try {
				terminated = channel.awaitTermination(100L, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				new ChaingrokException(e);
			}
		}
		return channel.isShutdown();
	}
	
}
