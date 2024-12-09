package co.ke.spsat.bowip.payment.mpesa;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@RequiredArgsConstructor
public class MpesaIntegration {

    private final String appKey;
    private final String appSecret;


    private final IncrementingStringGenerator incrementingStringGenerator=new IncrementingStringGenerator();
    private static final String BUSINESS_SHORT_CODE = "6373";  // Your actual business shortcode
    private static final String PASS_KEY = "YourPassKeyProvidedBySafaricom";  // Your MPESA Passkey
    private static final String CALLBACK_URL = "https://yourapp.com/mpesa/callback";  // Your app's callback URL
    private static final String QUEUE_TIMEOUT_URL = "https://yourapp.com/mpesa/queue_timeout";  // Your app's queue timeout URL
    String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());



    public String authenticate() throws IOException {

            String appKeySecret = appKey + ":" + appSecret;
            byte[] bytes = appKeySecret.getBytes("ISO-8859-1");
            String encoded = Base64.getEncoder().encodeToString(bytes);


            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
                    .get()
                    .addHeader("authorization", "Basic "+encoded)
                    .addHeader("cache-control", "no-cache")

                    .build();

            Response response = client.newCall(request).execute();
            JSONObject jsonObject=new JSONObject(response.body().string());
            System.out.println(jsonObject.getString("access_token"));
            return jsonObject.getString("access_token");
        }
    public String processMpesaSTKPush(String customerPhoneNumber, BigDecimal amount) throws IOException {
        String transactionReference = incrementingStringGenerator.generateIncrementingString();
        //TODO change the code with the real business code.




        // Generate Password
       String  password = generatePassword();

        // Create JSON Object
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("BusinessShortCode", BUSINESS_SHORT_CODE);
        jsonObject.put("Password", password);
        jsonObject.put("Timestamp", timestamp);
        jsonObject.put("TransactionType", "CustomerPayBillOnline");
        jsonObject.put("Amount", amount);
        jsonObject.put("PartyA", customerPhoneNumber);
        jsonObject.put("PhoneNumber", customerPhoneNumber);
        jsonObject.put("PartyB", BUSINESS_SHORT_CODE);
        jsonObject.put("TransactionDesc", "Order Payment");
        jsonObject.put("TransactionReference", transactionReference);
        jsonObject.put("CallbackURL", CALLBACK_URL);
        jsonObject.put("QueueTimeOutURL", QUEUE_TIMEOUT_URL);

        // Send the request as before
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + authenticate())
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        JSONObject responseJson = new JSONObject(responseBody);
        // Check if the response has a CheckoutRequestID
        if (responseJson.has("CheckoutRequestID")) {
            return responseJson.getString("CheckoutRequestID");
        } else {
            throw new IOException("CheckoutRequestID not found in the response: " + responseBody);
        }

    }

    public String STKPushTransactionStatus( String checkoutRequestID) throws IOException {
        // Create JSON Object for the STK push status query

        String password = generatePassword();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("BusinessShortCode", BUSINESS_SHORT_CODE);
        jsonObject.put("Password", password);
        jsonObject.put("Timestamp", timestamp);
        jsonObject.put("CheckoutRequestID", checkoutRequestID);

        // Prepare the request JSON
        String requestJson = jsonObject.toString();

        // Send the STK Push Query request using OkHttp
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/stkpushquery/v1/query")  // Sandbox URL
                .post(body)
                .addHeader("Authorization", "Bearer " + authenticate())  // Get the OAuth token using the authenticate() method
                .addHeader("Content-Type", "application/json")
                .build();

        // Execute the request and get the response
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        // Parse the response
        JSONObject responseJson = new JSONObject(responseBody);
        // Check the ResultCode to see if the payment was successful
        if (responseJson.has("ResultCode")) {
            String resultCode = responseJson.getString("ResultCode");
            if ("0".equals(resultCode)) {
                return "Payment was successful.";
            } else {
                return "Payment failed with ResultCode: " + resultCode;
            }
        } else {
            return "No ResultCode found in the response.";
        }
    }



    private String generatePassword() {
        String toEncode = MpesaIntegration.BUSINESS_SHORT_CODE + MpesaIntegration.PASS_KEY + timestamp;
        return java.util.Base64.getEncoder().encodeToString(toEncode.getBytes());
    }

}
