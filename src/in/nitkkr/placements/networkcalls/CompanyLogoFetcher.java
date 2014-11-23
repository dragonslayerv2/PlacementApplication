package in.nitkkr.placements.networkcalls;

import in.nitkkr.placements.database.DatabaseHandler;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class CompanyLogoFetcher extends AsyncTask<Void, Void, Void> {
	final String URL;
	final int companyId;
	final Context context;
	final int REQUIRED_IMAGE_SIZE = 100;

	public CompanyLogoFetcher(Context context, String URL, int companyId) {
		this.URL = URL;
		this.companyId = companyId;
		this.context = context;
	}

	private byte[] getLogoImage(String url) throws IOException {
		URL imageUrl = new URL(url);
		URLConnection ucon = imageUrl.openConnection();

		InputStream is = ucon.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);

		ByteArrayBuffer baf = new ByteArrayBuffer(500);
		int current = 0;
		while ((current = bis.read()) != -1) {
			baf.append((byte) current);
		}

		byte[] inputImage = baf.toByteArray();
		return compressImage(inputImage);
	}

	private byte[] compressImage(byte[] image) {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inSampleSize=6;
		Bitmap inputImage = BitmapFactory.decodeByteArray(image, 0, image.length,o);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		inputImage.compress(CompressFormat.JPEG,0, byteArrayOutputStream);
		Log.i("Logo Fetcher","Logo Fetcher: Compressed Image Size : "+byteArrayOutputStream.toByteArray().length);
		return byteArrayOutputStream.toByteArray();
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			Log.i("Logo Fetcher.", "Logo Fecher: Started Fetcheding");
			byte[] logoImage = getLogoImage(URL);
			
			Log.i("Logo Fetcher.", "Logo Fecher: URL Fetched Successfully "+logoImage.length+" bytes of data");
			int updatedRows=DatabaseHandler.getInstance(context).insertLogo(companyId,
					logoImage);
			Log.i("Updated Rows = ","Logo Fetcher: "+updatedRows);
			Log.i("Logo Fetcher. ","Inserted into database successfully");
		} catch (Exception e) {
			Log.i("Logo Fetcher.",
					"Logo Fecher: Got Exception " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
