package com.example.stpl.b4u;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    //this is the URL of the paytm folder that we added in the server
    //make sure you are using your ip else it will not work
    String BASE_URL = "http://vijeth11.000webhostapp.com/paytmkit/";

    @FormUrlEncoded
    @POST("generateChecksum.php")
    Call<Checksum> getChecksum(
            @Field("MID") String mId,
            @Field("ORDER_ID") String orderId,
            @Field("CUST_ID") String custId,
            @Field("CHANNEL_ID") String channelId,
            @Field("TXN_AMOUNT") String txnAmount,
            @Field("WEBSITE") String website,
            @Field("CALLBACK_URL") String callbackUrl,
            @Field("INDUSTRY_TYPE_ID") String industryTypeId
    );

}
