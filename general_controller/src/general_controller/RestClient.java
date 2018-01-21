package general_controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;

public class RestClient {

	public static String get(String url) {
		// Create an instance of HttpClient.
		HttpClient client = new HttpClient();

		// Create a method instance.
		GetMethod method = new GetMethod(url);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		String jsonString = null;
		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK)
				System.err.println("Method failed: " + method.getStatusLine());

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Retrieve the response body in String
			jsonString = method.getResponseBodyAsString();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary data
			System.out.println(new String(responseBody));
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error:" + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection
			method.releaseConnection();
		}
		return jsonString;
	}

	public static void post(String url, JSONObject json) {
		// Create an instance of HttpClient.
		HttpClient client = new HttpClient();

		// Create a method instance.
		PostMethod method = new PostMethod(url);
		StringRequestEntity requestEntity = null;
		try {
			requestEntity = new StringRequestEntity(json.toString(), "application/json", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Provide custom retry handler is necessary
		method.setRequestEntity(requestEntity);

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK)
				System.err.println("Method failed: " + method.getStatusLine());
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error:" + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection
			method.releaseConnection();
		}
	}

	
	
	private static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
		public static final String METHOD_NAME = "DELETE";

		public String getMethod() {
			return METHOD_NAME;
		}

		public HttpDeleteWithBody(final String uri) {
			super();
			setURI(URI.create(uri));
		}

		public HttpDeleteWithBody(final URI uri) {
			super();
			setURI(uri);
		}

		public HttpDeleteWithBody() {
			super();
		}
	}

	@SuppressWarnings("deprecation")
	public static void delete(String url, JSONObject json) {
		// Create an instance of HttpClient.
        CloseableHttpClient client = HttpClients.createDefault();
		// Create a method instance.
		HttpDeleteWithBody method = new HttpDeleteWithBody(url);
		StringEntity requestEntity = null;
		try {
			requestEntity = new StringEntity(json.toString(), "application/json", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Provide custom retry handler is necessary
		method.addHeader("header", "header");
		method.setEntity(requestEntity);

		try {
			// Execute the method.
			CloseableHttpResponse response = client.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK)
				System.err.println("Method failed: " + response.getStatusLine());
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error:" + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection
			method.releaseConnection();
		}
	}

}
