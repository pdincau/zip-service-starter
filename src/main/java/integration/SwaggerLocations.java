package integration;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import zip.Locations;
import zip.Query;
import zip.ZipInfo;

import java.io.IOException;

public class SwaggerLocations implements Locations {

    private String URL = "https://virtserver.swaggerhub.com/zipinfo/zipinfo/1.0.0/locations";

    @Override
    public ZipInfo findBy(Query query) {
        OkHttpClient client = new OkHttpClient();
        Request request = requestAt(URL);

        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            return new Gson().fromJson(json, ZipInfo.class);
        } catch (IOException e) {
            return null;
        }

    }

    private Request requestAt(String url) {
        return new Request.Builder()
                    .url(url)
                    .build();
    }
}
