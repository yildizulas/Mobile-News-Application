package com.example.homework2;

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

public class NewsRepository {

    public void getNewsById(ExecutorService svr, Handler uiHandler, int news_id){
        svr.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + String.valueOf(news_id));

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while((line = reader.readLine())!= null){
                    buffer.append(line);
                }



                JSONObject deneme = new JSONObject(buffer.toString());
                JSONArray arr = deneme.getJSONArray("items");
                JSONObject object = arr.getJSONObject(0);

                News data = new News(object.getInt("id"),
                        object.getString("title"),
                        object.getString("text"),
                        object.getString("date"),
                        object.getString("image"),
                        object.getString("categoryName"));
                conn.disconnect();

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }




    public void getAllNews(ExecutorService svr, Handler uiHandler){
        svr.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getall");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while((line = reader.readLine())!= null){
                    buffer.append(line);
                }

                JSONArray arr = new JSONArray(buffer.toString());
                List<News> data = new ArrayList<>();

                for(int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);
                    News news = new News(current.getInt("id"),
                            current.getString("title"),
                            current.getString("text"),
                            current.getString("date"),
                            current.getString("image"),
                            current.getString("categoryName"));
                    data.add(news);
                }

                conn.disconnect();
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    public void getNewsByCategory(ExecutorService svr, Handler uiHandler, int id){
        svr.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while((line = reader.readLine())!= null){
                    buffer.append(line);
                }

                JSONObject deneme = new JSONObject(buffer.toString());
                JSONArray arr = deneme.getJSONArray("items");
                List<News> data = new ArrayList<>();

                for(int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);
                    News news = new News(current.getInt("id"),
                            current.getString("title"),
                            current.getString("text"),
                            current.getString("date"),
                            current.getString("image"),
                            current.getString("categoryName"));
                    data.add(news);
                }

                conn.disconnect();
                Message msg = new Message();


                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    public void getAllNewsCategory(ExecutorService svr, Handler uiHandler){
        svr.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getallnewscategories");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while((line = reader.readLine())!= null){
                    buffer.append(line);
                }

                JSONObject deneme = new JSONObject(buffer.toString());
                JSONArray arr = deneme.getJSONArray("items");
                List<NewsCategories> data = new ArrayList<>();

                for(int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);
                    NewsCategories categories = new NewsCategories(current.getInt("id"),current.getString("name"));
                    data.add(categories);
                }
                conn.disconnect();
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    public void getCommentsById(ExecutorService svr, Handler uiHandler, int id){

        svr.execute(()->{
            try {
                Log.i("DEV","----------ENTERED--------------");
                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + String.valueOf(id));


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while((line = reader.readLine())!= null){
                    buffer.append(line);
                }

                JSONObject deneme = new JSONObject(buffer.toString());
                JSONArray arr = deneme.getJSONArray("items");
                List<Comments> data = new ArrayList<>();

                for(int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);
                    Comments comments = new Comments(current.getInt("id"),
                            current.getInt("news_id"),
                            current.getString("text"),
                            current.getString("name"));
                    data.add(comments);
                }

                conn.disconnect();
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


    }

    public void postComment(ExecutorService svr, Handler uiHandler, String name, String text, String news_id){
        svr.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");
                JSONObject outputData = new JSONObject();

                outputData.put("name", name);
                outputData.put("text", text);
                outputData.put("news_id", news_id);
                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());

                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }


                JSONObject reVal = new JSONObject(buffer.toString());
                /*
                PersonData data = new PersonData(reVal.getString("date"),
                        reVal.getString("fullname"));
                 */

                int retVal = reVal.getInt("serviceMessageCode");

                Message msg = new Message();
                msg.obj = retVal;

                uiHandler.sendMessage(msg);
                conn.disconnect();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }

    public void downloadImage(ExecutorService srv, Handler uiHandler, String path){
        srv.execute(()->{
            try {
                URL url = new URL(path);
                HttpURLConnection  conn = (HttpURLConnection)url.openConnection();

                Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



    }


}
