package edu.sabanciuniv.cs310news;

public class NewsCategories {

    private int id;
    private String categoryName;

    public NewsCategories(int id, String categoryName) {

        this.id = id;

        this.categoryName = categoryName;
    }

    public NewsCategories() {

    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getCategoryName() {

        return categoryName;
    }

    public void setCategoryName(String categoryName) {

        this.categoryName = categoryName;
    }
}