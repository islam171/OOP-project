package research;

import java.time.LocalDate;

public class ResearchPaper {

    private String name;
    private int pages;
    private LocalDate date;
    private int citation;

    public ResearchPaper(String name, int pages, LocalDate date){
        this.name = name;
        this.pages = pages;
        this.date = date;
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

    public String toString(){
        return "Project: " + this.getName() + "; pages: " + this.getPages() + "; citation: " + this.getCitation() + "; " + this.getDate();
    }
}
