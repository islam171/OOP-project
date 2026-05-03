package research;

import java.time.LocalDate;

public class ResearchPaper {

    private String name;
    private int pages;
    private LocalDate date;
    private int citation;

    public ResearchPaper(String name, int pages, LocalDate date, int citation){
        this.name = name;
        this.pages = pages;
        this.date = date;
        this.citation = citation;
    }

    public String getName(){
        return this.name;
    }


    public int getPages(){
        return this.pages;
    }

    public int getCitation(){
        return this.citation;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public void addCitation(){
        this.citation += 1;
    }


}
