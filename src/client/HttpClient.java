package client;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

public class HttpClient {
	private static final Campaign[] NO_CAMPAIGN = {};
	private static final Creative[] NO_CREATIVE = {};
	
	public static void main(String[] args) throws IOException, SQLException {
		
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
			PreparedStatement stmt;
			String query = "INSERT INTO AdjusterHomework(campaign_startdate, campaign_cpm, campaign_id, campaign_name, total_clicks, total_views) VALUES(?,?,?,?,?,?)";
			stmt = connection.prepareStatement(query);
			
			Campaign[] campaignList = getCampaigns();
			Creative[] creativeList = getCreatives();
			
	        Map< Integer, Integer[]> map = new HashMap<Integer,Integer[]>();
	        for(int i = 0; i < creativeList.length; i++){
	        	int parent_id = creativeList[i].getParentId();
	        	if(!map.containsKey(parent_id)){
	        		Integer[] arr = new Integer[2];
	        		arr[0] = creativeList[i].getClicks();
	        		arr[1] = creativeList[i].getViews();
	        		map.put(parent_id, arr);
	        	}
	        	else{
	        		Integer[] arr = map.get(parent_id);
	        		arr[0] += creativeList[i].getClicks();
	        		arr[1] += creativeList[i].getViews();
	        	}
	        }
	        FileWriter fileWriter = null;
	        try{
		        fileWriter = new FileWriter("ngo_mandy.csv");
		        String FILE_HEADER = "Start Date, CPM, ID, Name, Total Clicks, Total Views";
		        fileWriter.append(FILE_HEADER);		// Write csv header
		        fileWriter.append("\n");
		        
		        for(int i = 0; i < map.size(); i++){
		        	int campaign_id = campaignList[i].getId();
		        	Integer[] values = map.get(campaign_id);
		        	fileWriter.append(campaignList[i].getStartDate());
		        	fileWriter.append(",");
		        	fileWriter.append(campaignList[i].getCpm());
		        	fileWriter.append(",");
		        	fileWriter.append(String.valueOf(campaign_id));
		        	fileWriter.append(",");
		        	fileWriter.append(campaignList[i].getName());
		        	fileWriter.append(",");
		        	fileWriter.append(String.valueOf(values[0]));
		        	fileWriter.append(",");
		        	fileWriter.append(String.valueOf(values[1]));
		        	fileWriter.append("\n");
		        	System.out.println(campaign_id+", "+campaignList[i].getName()+", "+campaignList[i].getStartDate()+", "+campaignList[i].getCpm()+", "+values[0]+", "+values[1]);
		        }
	        }catch(Exception e){
	        	System.out.println("FileWriter Failed!");
	        	e.printStackTrace();
	        }finally{
	        	try{
	        		fileWriter.flush();
	        		fileWriter.close();
	        	}catch(IOException e){
	        		System.out.println("flushing/closing fileWriter failed !");
	        		e.printStackTrace();
	        	}
	        }
		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
			return;
		}
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
		HttpGet httpget = new HttpGet("http://54.172.67.92:8080/api/creative");
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
