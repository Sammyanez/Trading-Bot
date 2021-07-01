import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class Yahoo {
	
	private static String MAIN_ENDPOINT = "https://yahoo-finance15.p.rapidapi.com/api/yahoo/";
	private static String API_KEY = "8aa66e0b7bmshdc7f8bdcbb1201dp1ce5cejsn59112c64ee82";

    public static double getSMA(String ticker,int period,int index){
        double sma = 0.0;
    	String ENDPOINT = "https://www.alphavantage.co/query?function=SMA&symbol="+ticker+"&interval=daily&time_period="+period+"&series_type=open&apikey=";
    	String[] keys = {"O3LJGOZBDPIIH54J","UB8D8APZUHU8HAUC","P6YX5BGBFQH42QJV"};
    	System.out.println(keys[index]);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(ENDPOINT+keys[index]))
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			String body = response.body();
			try {
				JSONObject json = new JSONObject(body);
				String sma_string = json.getJSONObject("Technical Analysis: SMA")
							      .getJSONObject("2021-04-16").getString("SMA");
				sma = Double.parseDouble(sma_string);
				System.out.println(sma_string);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.out.println(body);
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sma;
	}
	//Takes in the Ticker of a stock and returns the current raw price
	public static double getStockPrice (String ticker){
		String ENDPOINT = MAIN_ENDPOINT + "qu/quote/" + ticker + "/financial-data";
		double price = 0.0;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(ENDPOINT))
				.header("x-rapidapi-key", API_KEY)
				.header("x-rapidapi-host", "yahoo-finance15.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			String body = response.body();
			try {
				JSONObject json = new JSONObject(body);
				price = json.getJSONObject("financialData")
						.getJSONObject("currentPrice")
						.getDouble("raw");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return price;
	}
	
	
	//Takes in the Ticker of a stock and returns the last dividend value
	//Dividend is the amount of money you 'profit' off of the shares
		public double getSharesShorted (String ticker){
			String ENDPOINT = MAIN_ENDPOINT + "qu/quote/" + ticker + "/default-key-statistics";
			double shares_shorted  = 0.0;
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(ENDPOINT))
					.header("x-rapidapi-key", API_KEY)
					.header("x-rapidapi-host", "yahoo-finance15.p.rapidapi.com")
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();
			HttpResponse<String> response;
			try {
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
				String body = response.body();
				try {
					JSONObject json = new JSONObject(body);
					shares_shorted = json.getJSONObject("defaultKeyStatistics")
							.getJSONObject("sharesShort")
							.getDouble("raw");
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return shares_shorted ;
		}
		
		
		//Takes in the Ticker of a stock and returns the current raw price 
		public String getRecommendation (String ticker){
			String ENDPOINT = MAIN_ENDPOINT + "qu/quote/" + ticker + "/financial-data";
			String recommendation = null;
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(ENDPOINT))
					.header("x-rapidapi-key", API_KEY)
					.header("x-rapidapi-host", "yahoo-finance15.p.rapidapi.com")
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();
			HttpResponse<String> response;
			try {
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
				String body = response.body();
				try {
					JSONObject json = new JSONObject(body);
					recommendation = json.getJSONObject("financialData")
							.getString("recommendationKey");
							
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return recommendation ;
		}
	
	
}
