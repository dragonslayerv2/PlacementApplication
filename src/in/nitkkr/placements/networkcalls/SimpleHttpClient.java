package in.nitkkr.placements.networkcalls;

import in.nitkkr.placements.json.JSONConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.util.Log;

public class SimpleHttpClient {
	public static final int HTTP_TIMEOUT = 6000;
	private static HttpClient mHttpClient = null;

	private static HttpClient getHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new DefaultHttpClient();
			HttpParams params = mHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
		}
		return mHttpClient;
	}

	public static String executeHttpPost(String url, JSONObject requestObject)
			throws Exception {
		Log.i("Http Client", "Calling URL = " + url + " JSONObject = "
				+ requestObject);
		BufferedReader inputStreamReader = null;
		try {
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(url);

			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			if (requestObject != null)
				params.add(new BasicNameValuePair(
						JSONConstants.JSON_REQUEST_PARAMETER, requestObject
								.toString()));
			request.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse response = client.execute(request);
			Log.i("Http Client", "Got the status code "
					+ response.getStatusLine().getStatusCode());
			inputStreamReader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), HTTP.UTF_8));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = inputStreamReader.readLine()) != null)
				sb.append(line);

			request.abort();
			Log.i("Hiting URL: ", "Hitting URL : " + url + " GOT STATUS CODE: "
					+ response.getStatusLine().getStatusCode()
					+ " GOT RESPONSE AS : " + sb.toString());
			return sb.toString();

		} finally {
			if (inputStreamReader != null)
				inputStreamReader.close();
		}
	}

	public static String executeHttpGet(String url) throws Exception {
		BufferedReader inputStreamReader = null;
		try {
			HttpClient client = getHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			inputStreamReader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer sb = new StringBuffer();
			String line;

			while ((line = inputStreamReader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} finally {
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
		}
	}
}
