package com.example.aplicativo_com_api;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    // Constantes utilizadas pela API
    // URL para a API de assets de videos e imagems da Nasa.
    private static final String NASA_URL = "https://images-api.nasa.gov",
    //ENDPOINTS:
    QUERY_URL = "/search?",
    IMAGE_URL = "/asset/" ,
    VIDEO_URL = "/captions/",
    METADATA_URL = "/metadata/";

    //parametrôs da query
    private static final String QUERY_PARAM = "q",
    CENTER_PARAM = "center",
    DESCRIPTION_PARAM = "description",
    DESCRIPTION_508_PARAM = "description_508",
    KEYWORDS_PARAM = "keywords",
    LOCATION_PARAM = "location",
    MEDIA_TYPE_PARAM = "media_type",
    NASA_ID_PARAM = "nasa_id",
    PAGE_PARAM = "page",
    PHTOGRAPHER_PARAM = "photographer",
    SECONDARY_CREATOR_PARAM = "secondary_creator",
    TITLE_PARAM = "title",
    YEAR_START_PARAM = "year_start",
    YEAR_END_PARAM = "year_end";
    static JSONObject query(String query)
    {
        Uri builtURI = Uri.parse(NASA_URL+QUERY_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, query)
                .build();
        return request(builtURI);
    }

    static JSONObject request(Uri uri) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSON = null;
        JSONObject result = null;
        try {
            URL requestURL = new URL(uri.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                builder.append(linha);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            result = new JSONObject(builder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            // fecha a conexão e o buffer.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
