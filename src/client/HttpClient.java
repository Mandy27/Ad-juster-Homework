package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpClient {

	public static void main(String[] args) throws IOException{
		 CloseableHttpClient httpclient = HttpClients.createDefault();
		 
		 HttpGet httpget = new HttpGet("http://54.172.67.92:8080/api/campaign");
		 
		 CloseableHttpResponse response = httpclient.execute(httpget);
		 
		 try {
			if (response.getStatusLine().getStatusCode() != 200) {
				 throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader bufferReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			
			StringBuilder campaignStringList = new StringBuilder();
			String output;
			// Iterate through XML response.
			while ((output = bufferReader.readLine()) != null) {
				campaignStringList.append(output);
			}
			// Convert String to JsonArray
			Gson gson = new Gson();
			Campaign[] campaignList = gson.fromJson(campaignStringList.toString(), Campaign[].class);
//			for(int i = 0; i < campaignList.length; i++){
//				System.out.print(gson.toJson(campaignList));
//			}
		 }catch (ClientProtocolException e) {
			e.printStackTrace();	 
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 finally {
		     response.close();
		 }
	}

}
