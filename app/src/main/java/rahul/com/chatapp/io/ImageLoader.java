package rahul.com.chatapp.io;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rahul on 7/4/15.
 */
public class ImageLoader extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "ImageLoader";
    private setOnImageListener mListener;

    public ImageLoader(setOnImageListener imageLoader) {
        mListener = imageLoader;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        try {
            URL uri = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
            return BitmapFactory.decodeStream(urlConnection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        Log.i(TAG, "bitmap" + bitmap);
        mListener.onSuccess(bitmap);
    }

    public interface setOnImageListener {
        void onSuccess(Bitmap bitmap);
    }
}
