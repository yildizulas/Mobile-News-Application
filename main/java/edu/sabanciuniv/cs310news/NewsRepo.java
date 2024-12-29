package edu.sabanciuniv.cs310news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

//android:theme="@style/Theme.CS310News"

public class NewsRepo {

    public void getNewsById(ExecutorService exe_svr, Handler handler, int newsid){

        exe_svr.execute(()->{

            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + String.valueOf(newsid));

                HttpURLConnection urlConne = (HttpURLConnection) url.openConnection();

                BufferedReader read = new BufferedReader(new InputStreamReader(urlConne.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while((line = read.readLine()) != null){

                    buffer.append(line);
                }

                JSONObject js = new JSONObject(buffer.toString());

                JSONArray arr = js.getJSONArray("items");

                JSONObject getvar = arr.getJSONObject(0);

                News data = new News(getvar.getInt("id"),
                        getvar.getString("title"),
                        getvar.getString("text"),
                        getvar.getString("date"),
                        getvar.getString("image"),
                        getvar.getString("categoryName"));

                urlConne.disconnect();

                Message msg = new Message();

                msg.obj = data;

                handler.sendMessage(msg);
            }

            catch (MalformedURLException e) {

                e.printStackTrace();
            }

            catch (IOException e) {

                e.printStackTrace();
            }

            catch (JSONException e) {

                e.printStackTrace();
            }
        });
    }

    public void getAllNews(ExecutorService exe_svr, Handler handler){

        exe_svr.execute(()->{

            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getall");

                HttpURLConnection urlConne = (HttpURLConnection) url.openConnection();

                BufferedReader read = new BufferedReader(new InputStreamReader(urlConne.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while((line = read.readLine()) != null){

                    buffer.append(line);
                }

                JSONArray jsArr = new JSONArray(buffer.toString());

                List<News> data = new ArrayList<>();

                for (int i = 0; i < jsArr.length(); i++){

                    JSONObject getvar = jsArr.getJSONObject(i);

                    News news = new News(getvar.getInt("id"),
                            getvar.getString("title"),
                            getvar.getString("text"),
                            getvar.getString("date"),
                            getvar.getString("image"),
                            getvar.getString("categoryName"));

                    data.add(news);
                }

                urlConne.disconnect();

                Message msg = new Message();

                msg.obj = data;

                handler.sendMessage(msg);
            } catch (IOException | JSONException e) {

                e.printStackTrace();
            }
        });
    }

    public void getNewsByCategory(ExecutorService exe_svr, Handler handler, int id){

        exe_svr.execute(()->{

            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + String.valueOf(id));

                HttpURLConnection urlConne = (HttpURLConnection) url.openConnection();

                BufferedReader read = new BufferedReader(new InputStreamReader(urlConne.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while((line = read.readLine())!= null){

                    buffer.append(line);
                }

                JSONObject js = new JSONObject(buffer.toString());

                JSONArray jsArr = js.getJSONArray("items");

                List<News> data = new ArrayList<>();

                for(int i = 0; i < jsArr.length(); i++){

                    JSONObject getvar = jsArr.getJSONObject(i);

                    News news = new News(getvar.getInt("id"),
                            getvar.getString("title"),
                            getvar.getString("text"),
                            getvar.getString("date"),
                            getvar.getString("image"),
                            getvar.getString("categoryName"));

                    data.add(news);
                }

                urlConne.disconnect();

                Message msg = new Message();

                msg.obj = data;

                handler.sendMessage(msg);
            } catch (IOException | JSONException e) {

                e.printStackTrace();
            }
        });
    }

    public void getAllNewsCategory(ExecutorService exe_svr, Handler handler){

        exe_svr.execute(()->{

            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getallnewscategories");
                HttpURLConnection urlConne = (HttpURLConnection) url.openConnection();
                BufferedReader read = new BufferedReader(new InputStreamReader(urlConne.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while ((line = read.readLine())!= null){

                    buffer.append(line);
                }

                JSONObject js = new JSONObject(buffer.toString());

                JSONArray jsArr = js.getJSONArray("items");

                List<NewsCategories> data = new ArrayList<>();

                for (int i = 0; i < jsArr.length(); i++){

                    JSONObject getvar = jsArr.getJSONObject(i);

                    NewsCategories categories = new NewsCategories(getvar.getInt("id"),getvar.getString("name"));

                    data.add(categories);
                }

                urlConne.disconnect();

                Message msg = new Message();

                msg.obj = data;

                handler.sendMessage(msg);
            } catch (IOException | JSONException e) {

                e.printStackTrace();
            }
        });
    }

    public void getCommentsById(ExecutorService exe_svr, Handler handler, int id){

        exe_svr.execute(()->{

            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + String.valueOf(id));

                HttpURLConnection urlConne = (HttpURLConnection) url.openConnection();

                BufferedReader read = new BufferedReader(new InputStreamReader(urlConne.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while ((line = read.readLine())!= null){

                    buffer.append(line);
                }

                JSONObject js = new JSONObject(buffer.toString());

                JSONArray jsArr = js.getJSONArray("items");

                List<Comments> data = new ArrayList<>();

                for (int i = 0; i < jsArr.length(); i++){

                    JSONObject getvar = jsArr.getJSONObject(i);

                    Comments comments = new Comments(getvar.getInt("id"),
                            getvar.getInt("news_id"),
                            getvar.getString("text"),
                            getvar.getString("name"));

                    data.add(comments);
                }

                urlConne.disconnect();

                Message msg = new Message();

                msg.obj = data;

                handler.sendMessage(msg);
            } catch (IOException | JSONException e) {

                e.printStackTrace();
            }
        });
    }

    public void postComment(ExecutorService exe_svr, Handler handler, String categname, String text, String newsid){

        exe_svr.execute(()->{

            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");

                HttpURLConnection urlConne = (HttpURLConnection) url.openConnection();

                urlConne.setDoOutput(true);

                urlConne.setDoInput(true);

                urlConne.setRequestMethod("POST");

                urlConne.setRequestProperty("Content-Type","application/JSON");

                JSONObject js = new JSONObject();

                js.put("name", categname);

                js.put("text", text);

                js.put("news_id", newsid);

                BufferedOutputStream write = new BufferedOutputStream(urlConne.getOutputStream());

                write.write(js.toString().getBytes(StandardCharsets.UTF_8));

                write.flush();

                BufferedReader read = new BufferedReader(new InputStreamReader(urlConne.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while ((line = read.readLine()) != null){

                    buffer.append(line);
                }

                JSONObject getvar = new JSONObject(buffer.toString());

                int retVal = getvar.getInt("serviceMessageCode");

                Message msg = new Message();

                msg.obj = retVal;

                handler.sendMessage(msg);

                urlConne.disconnect();
            } catch (IOException | JSONException e) {

                e.printStackTrace();
            }
        });
    }

    public void Image(ExecutorService exe_srv, Handler handler, String img){

        exe_srv.execute(()->{

            try {

                URL url = new URL(img);

                HttpURLConnection  urlConne = (HttpURLConnection)url.openConnection();

                Bitmap bitmap = BitmapFactory.decodeStream(urlConne.getInputStream());

                Message msg = new Message();

                msg.obj = bitmap;

                handler.sendMessage(msg);
            } catch (IOException e) {

                e.printStackTrace();
            }
        });
    }
}