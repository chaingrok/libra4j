package com.chaingrok.libra4j.misc;

import java.io.IOException;

import com.chaingrok.libra4j.types.AccountAddress;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Minter {
	
	public static final String MINTER_URL = "http://faucet.testnet.libra.org";
	
	private String mintUrl;
	
	public Minter(String mintUrl) {
		this.mintUrl = mintUrl;
	}
	
	public int mint(AccountAddress accountAddress,long microLibras) {
		int result = -1;
		OkHttpClient client = new OkHttpClient();
		HttpUrl url = new HttpUrl.Builder()
					.query(mintUrl)
					.addQueryParameter("address",Utils.byteArrayToHexString(accountAddress.getBytes()))
					.addQueryParameter("amount",microLibras + "")
					.build();
        Request request = new Request.Builder()
            .url(url)
            .post(null)
            .build();
        Response response = null;
        try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			new Libra4jError(Libra4jLog.Type.HTTP_ERROR,e);
		}
        if (response != null) {
        	result = response.code();
        }
        return result;
	}

}
