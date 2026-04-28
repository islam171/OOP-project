package main;

import academic.Course;
import academic.Lesson;
import academic.News;
import research.ResearchProject;
import users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Database {

    private static Database instance;

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Lesson> lessons = new ArrayList<>();
    private List<ResearchProject> projects = new ArrayList<>();
    private List<News> news = new ArrayList<>();

    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public List<User> getUsers(){
        return this.users;
    }

    public void addCourse(Course course){
        this.courses.add(course);
    }

    public List<Course> getCourses(){
        return this.courses;
    }

    public void addLesson(Lesson lesson){
        this.lessons.add(lesson);
    }

    public List<Lesson> getLessons(){
        return this.lessons;
    }

    public void addProject(ResearchProject project){
        this.projects.add(project);
    }

    public List<ResearchProject> getProjects(){
        return this.projects;
    }


    // NEWS
    public void addNews(News news){
        this.news.add(news);
    }

    public List<News> getNews(){
        return this.news;
    }

    public void deleteNewById(int id){
        this.news.removeIf(news -> news.id == id);
    }

    public void updateNews(int id, News newPost){
        this.news = this.news.stream().map(item -> item.getId() == id ? newPost : item).collect(Collectors.toList());
    }

}
