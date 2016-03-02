package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

public class HttpClient {
	static Campaign[] campaignList;
	static Creative[] creativeList;
	
	public static void main(String[] args) {

		
	}

	public static void getCampaigns() throws IOException{
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
			campaignList = gson.fromJson(campaignStringList.toString(), Campaign[].class);

		}catch (ClientProtocolException e) {
			e.printStackTrace();	 
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			response.close();
		}
	}
	
	public static void getCreatives() throws IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://54.172.67.92:8080/api/creative ");
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader bufferReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			StringBuilder creativeStringList = new StringBuilder();
			String output;
			// Iterate through XML response.
			while ((output = bufferReader.readLine()) != null) {
				creativeStringList.append(output);
			}
			// Convert String to JsonArray
			Gson gson = new Gson();
			creativeList = gson.fromJson(creativeStringList.toString(), Creative[].class);

		}catch (ClientProtocolException e) {
			e.printStackTrace();	 
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			response.close();
		}
	}

}
