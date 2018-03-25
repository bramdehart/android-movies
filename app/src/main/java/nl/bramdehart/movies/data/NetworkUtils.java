/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.bramdehart.movies.data;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String TMDB_SEARCH_BASE_URL = "https://api.themoviedb.org/3/search/movie";
    final static String TMDB_TRENDING_BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    final static String TMDB_DETAIL_BASE_URL = "https://api.themoviedb.org/3/movie/";
    final static String PARAM_QUERY = "query";
    final static String PARAM_API_KEY = "api_key";
    final static String VALUE_API_KEY = "977da42f84c289b566542292c3343bc6";
    final static String PARAM_APPEND_TO_RESPONSE = "append_to_response";
    final static String VALUE_APPEND_TO_RESPONSE = "videos,casts";
    final static String PARAM_LANGUAGE = "language";

    /**
     * Builds the URL used to query on TMDB.
     *
     * @param TMDBSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the TMDB server.
     */
    public static URL buildSearchUrl(String TMDBSearchQuery) {
        Uri builtUri = Uri.parse(TMDB_SEARCH_BASE_URL).buildUpon()
            .appendQueryParameter(PARAM_QUERY, TMDBSearchQuery)
            .appendQueryParameter(PARAM_API_KEY, VALUE_API_KEY)
            .appendQueryParameter(PARAM_LANGUAGE, getLanguage())
            .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Builds the URL used to get trending movies on TMDB.
     *
     * @return The URL to use to query the TMDB server.
     */
    public static URL buildTrendingUrl() {
        Uri builtUri = Uri.parse(TMDB_TRENDING_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, VALUE_API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, getLanguage())
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Builds the URL used to query movie details.
     *
     * @param movieId The movie id
     * @return The URL to use to query the TMDB server.
     */
    public static URL buildMovieDetailUrl(int movieId) {
        String detailUrl = TMDB_DETAIL_BASE_URL + movieId;
        Uri detailUri = Uri.parse(detailUrl).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, VALUE_API_KEY)
                .appendQueryParameter(PARAM_APPEND_TO_RESPONSE, VALUE_APPEND_TO_RESPONSE)
                .appendQueryParameter(PARAM_LANGUAGE, getLanguage())
                .build();
        URL url = null;
        try {
            Log.e("info", detailUri.toString());
            url = new URL(detailUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static String getLanguage() {
        String language = Locale.getDefault().getLanguage();
        if (language.equals("nl")) {
            return "nl";
        }
        else if (language.equals("de")) {
            return "de";
        }
        return "en";
    }
}