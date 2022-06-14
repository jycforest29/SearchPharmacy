package com.jms.searchpharmacy.data.api.server;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
//
//public class RetrofitClient {
//    private static RetrofitClient instance = null;
//    private ServerApi serverApi;
//
//    private RetrofitClient() {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(ServerApi.SERVER_BASE_URL)
//                .addConverterFactory(MoshiConverterFactory.create())
//                .build();
//        serverApi = retrofit.create(ServerApi.class);
//    }
//
//    public static synchronized RetrofitClient getInstance() {
//        if (instance == null) {
//            instance = new RetrofitClient();
//        }
//        return instance;
//    }
//
//    public ServerApi getServerApi() {
//        return serverApi;
//    }
//}
