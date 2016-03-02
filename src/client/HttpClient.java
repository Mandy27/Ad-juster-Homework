package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

public class HttpClient {
	private static final Campaign[] NO_CAMPAIGN = {};
	private static final Creative[] NO_CREATIVE = {};
	
	public static void main(String[] args) throws IOException {
		
		/************ DATABASE CONNECTION **************/
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		Connection connection = null;
        try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/AdjusterHomework", "Mandy",
					"");
		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
			return;
		}
        if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Connection failed!");
		}
//        Campaign[] campaignList = getCampaigns();
//		Creative[] creativeList = getCreatives();
	}

	public static Campaign[] getCampaigns() throws IOException{
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
			return gson.fromJson(campaignStringList.toString(), Campaign[].class);

		}catch (ClientProtocolException e) {
			e.printStackTrace();	 
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			response.close();
		}
		return NO_CAMPAIGN;
	}
	
	public static Creative[] getCreatives() throws IOException{
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
			return gson.fromJson(creativeStringList.toString(), Creative[].class);

		}catch (ClientProtocolException e) {
			e.printStackTrace();	 
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			response.close();
		}
		return NO_CREATIVE;
	}

}
