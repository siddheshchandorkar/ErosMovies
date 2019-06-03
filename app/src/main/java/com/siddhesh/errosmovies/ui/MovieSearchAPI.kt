package com.siddhesh.errosmovies.ui

import android.content.Context
import android.util.Log
import com.example.erostest.model.MovieListItem
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.util.ArrayList
import java.util.HashMap

class MovieSearchAPI {

    private val TAG = MovieSearchAPI::class.java!!.getSimpleName()
    private val OUT_JSON = "/json"
    private var response = ""
    private val USER_AGENT = "Mozilla/5.0"

    fun autocomplete(context: Context, input: String, apiKey:String): ArrayList<MovieListItem>? {
        var input = input
        var resultList: ArrayList<MovieListItem>? = null
        val conn: HttpURLConnection? = null
        val jsonResults = StringBuilder()
        try {
            input = input.trim { it <= ' ' }
            input = input.replace(" ", "%20")
            //            StringBuilder sb = new StringBuilder(APIConstants.PROVIDER_APP_ENDPOINT + APIInterface.LEDGER_SEARCH_CONTACT);

            response = sendGet( input, apiKey)

            Log.d("Siddhesh","Check search: "+response)
        } catch (e: MalformedURLException) {
            Log.e(TAG, "Error processing Places API URL", e)
            return resultList
        } catch (e: Exception) {
            Log.e(TAG, "Error connecting to Places API", e)
            return resultList
        } finally {
            conn?.disconnect()
        }
        try {
            resultList = ArrayList<MovieListItem>()
            // Create a JSON object hierarchy from the results
            val jsonObj = JSONObject(response)
            if (jsonObj.has("results") ) {
                val array = jsonObj.getJSONArray("results")
                var gson:Gson= Gson()
                for (i in 0 until array.length()) {
                    val object1:JSONObject = array.getJSONObject(i)
                    resultList.add(gson.fromJson(object1.toString(), MovieListItem::class.java))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Cannot process JSON results", e)
        }

        return resultList
    }


    @Throws(Exception::class)
    private fun sendGet(query: String, appKey: String): String {

        //        String url = "https://selfsolve.apple.com/wcResults.do";

        //http://api.themoviedb.org/3/search/movie?query=Avenger&api_key=732bdacc6ee9b8efcbae09cab5843c5d
        val result = StringBuilder("http://api.themoviedb.org/3/search/movie?query=Avenger&api_key=732bdacc6ee9b8efcbae09cab5843c5d")

        //        HttpClient Client = new DefaultHttpClient();


        result.append("?search_param=")
        //        result.append("=");
//        result.append(URLEncoder.encode(param, "UTF-8"))


        val mParams = HashMap<String, String>()
        mParams["query"] = query
        mParams["api_key"] = appKey

        val urlParameters = getEncodedURLWithQueryParam(mParams)


        var urlL: URL? = null
        try {
            urlL = URL(urlParameters)
            var urlConnection: HttpURLConnection? = null
            try {
                urlConnection = urlL.openConnection() as HttpURLConnection
//                urlConnection.setRequestProperty("Authorization", auth)

                val `in` = BufferedInputStream(urlConnection.inputStream)
                response = readStream(`in`)

                return response
            } catch (e: IOException) {
                //throw new RuntimeException(e);
                e.printStackTrace()
                return response


            } finally {
                urlConnection?.disconnect()
                //                return ERROR;
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            //            return ERROR;
            return response

        }

    }

    fun getEncodedURLWithQueryParam(mParams: Map<String, String>): String {
        val result = StringBuilder("http://api.themoviedb.org/3/search/movie")
        val startLength = result.length
        for (key in mParams.keys) {
            try {
                val encodedValue = mParams[key]
                if (result.length > startLength) {
                    result.append("&")
                } else {
                    result.append("?")
                }
                result.append(key)
                result.append("=")
                result.append(encodedValue)
            } catch (e: Exception) {
            }

        }
        return result.toString().replace(" ", "%20")
    }

    private fun readStream(`is`: InputStream): String {
        try {
            val bo = ByteArrayOutputStream()
            var i = `is`.read()
            while (i != -1) {
                bo.write(i)
                i = `is`.read()
            }
            return bo.toString()
        } catch (e: IOException) {
            return ""
        }

    }

}