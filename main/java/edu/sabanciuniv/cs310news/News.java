package edu.sabanciuniv.cs310news;

public class News {

    private int news_id;

    private String title, text, date, image, categoryName;

    public News(int news_id, String title, String text, String date, String image, String categoryName) {

        this.news_id = news_id;

        this.title = title;

        this.text = text;

        this.date = date;

        this.image = image;

        this.categoryName = categoryName;
    }

    public News() {

    }

    public String getCategoryName() {

        return categoryName;
    }

    public void setCategoryName(String categoryName) {

        this.categoryName = categoryName;
    }

    public int getNews_id() {

        return news_id;
    }

    public void setNews_id(int news_id) {

        this.news_id = news_id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public String getDate() {

       return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }
}