package com.example.ruchita.popularmovies;

/**
 * Created by ruchita on 9/5/16.
 */
public class GridItem {
    private String image;
    private String title;
    private String plot;
    private String releasedate;
    private String vote;


    public GridItem() {
        super();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return releasedate;
    }

    public void setDate(String releasedate) {
        this.releasedate = releasedate;
    }



}