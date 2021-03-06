package com.company.Controller;


import com.company.Entity.*;
import com.company.TopMovies.Top5CurrentMovies;
import com.company.TopMovies.TopMovieFactory;
import com.company.Utils.FileIO;
import com.company.Utils.UserInputOutput;

import java.time.LocalDate;
import java.io.IOException;
import java.util.ArrayList;



/**
 * This is the main controller for the StaffUI.
 * It handles the movie & show time creating, modification and deletion along with the configuration of system settings.
 * @author Group 2 - SS6
 * @version 1.0
 * @since 2019-11-12
 */


public class StaffControl {
    //    MovieGoerController movieController = new MovieGoerController();
//    MovieGoerUI movieGoerUI = new MovieGoerUI();
    Movie movieFunctions = new Movie();

    /**
     * Adds a new movie to the movie.txt file
     */
    public void addMovieListing() {
        String title;
        String synopsis;
        String director;
        String[] cast;
        String[] genre;
        int duration;
        int movieClass; // 3D, blockbuster etc..
        int statusType; //now showing, etc..
        int ageType; //age type {"G", "PG", "PG13", "NC16", "M18", "R21"}
        LocalDate showTill = null;
        int i;

        title = UserInputOutput.getStringInput("Enter the movie title: ");
        synopsis = UserInputOutput.getStringInput("Enter the movie synopsis: ");
        director = UserInputOutput.getStringInput("Enter the movie director: ");
        cast = UserInputOutput.getStringInput("Enter the cast names with a comma separating the cast names. \nCast names: ")
                .split(",");
        genre = UserInputOutput.getStringInput("Enter the movie genre with a comma separating them. \nGenre: ")
                .split(",");

        System.out.println("Enter the movie duration in minutes: ");
        duration = UserInputOutput.getUserChoice(1, 500);
        i = 1;
        System.out.println("Select movie type from the following options:");
        for (String movietype : movieFunctions.getMovieClasses()) {
            System.out.println(i + ". " + movietype);
            i++;
        }
        movieClass = UserInputOutput.getUserChoice(1, i - 1) - 1;
        i = 1;
        System.out.println("Select movie age restriction from the following options:");
        for (String movieagetype : movieFunctions.getAgeTypes()) {
            System.out.println(i + ". " + movieagetype);
            i++;
        }
        ageType = UserInputOutput.getUserChoice(1, i - 1) - 1;

        i = 1;
        System.out.println("Select movie status type from the following options:");
        for (String statustype : movieFunctions.getStatusTypes()) {
            System.out.println(i + ". " + statustype);
            i++;
        }
        statusType = UserInputOutput.getUserChoice(1, i - 1) - 1;
        // DATE Showtill
        int year;
        int month;
        int day;

        boolean loop = true;
        while(loop) {
            System.out.println("Insert end date and time of the movie");
            day = UserInputOutput.getDateIntInput("Insert the day (in number)", 1, 31);
            month = UserInputOutput.getDateIntInput("Insert the month (in number)", 1, 12);
            year = UserInputOutput.getDateIntInput("Insert the year (in number)", LocalDate.now().getYear(), 9999);
            showTill = LocalDate.of(year, month, day);
            if(showTill.isAfter(LocalDate.now())){
                loop = false;
            }
            else{
                System.out.println("Sorry you cant enter a date from the past, please try again.");
            }
        }

        // newly created movie object
        Movie movie = new Movie( title, synopsis , director , cast , genre , showTill , duration , movieClass, ageType, statusType );

        // read the movielist from text file
        ArrayList<Movie> movieList;
        try{
            movieList = (ArrayList<Movie>) FileIO.readObject("movie.txt");
        }catch (ClassNotFoundException e){
            System.out.println("File not found. please try again.");
            return;
        }catch (IOException e){
            System.out.println("File not found. please try again.");
            return;
        }
        // add movie to movie list
        movieList.add(movie);
        // write movielist to text file
        try{
            FileIO.writeObject("movie.txt", movieList);
        }catch (IOException e){
            System.out.println("File not found. please try again.");
            return;
        }

        System.out.println("Movie successfully created!");
        // Todo print out the movie attribues?
    }

    /**
     * Edits an attribute of one of the movie from the movie.txt file
     */
    public void editMovieListing() {
        String synopsis;
        String director;
        String[] cast;
        String[] genre;
        int duration;
        int movieClass; // 3D, blockbuster etc..
        int statusType; //now showing, etc..
        int ageType; //age type {"G", "PG", "PG13", "NC16", "M18", "R21"}
        LocalDate showTill = null;
        int i = 0;

        int k = 1;

        ArrayList<Movie> movieList;
        try{
            movieList = (ArrayList<Movie>) FileIO.readObject("movie.txt");
        }catch (ClassNotFoundException e){
            System.out.println("File not found. please try again.");
            return;
        }catch (IOException e){
            System.out.println("File not found. please try again.");
            return;
        }


        UserInputOutput.displayHeader("Movie List");
        for (Movie m: movieList)
        {
            System.out.println(k + ": " + m.getTitle() + ", movie status: " + m.getStatusType());
            k++;
        }
        System.out.println("Select movie to edit: ");
        int movieIndex = UserInputOutput.getUserChoice(0, movieList.size()) - 1;

        Movie movie = movieList.get(movieIndex);

        UserInputOutput.displayHeader("Movie Details");
        System.out.println("The details of "+ movie.getTitle() + " :");
        System.out.println("1) Duration: "+ movie.getDuration());
        System.out.println("2) Synopsis: "+ movie.getSynopsis());
        System.out.println("3) Status: "+ movie.getStatusType());
        System.out.println("4) Movie Type: "+ movie.getMovieClass());
        System.out.println("5) Age Type: "+ movie.getAgeType());
        String[] movieGenre = movie.getGenre();
        System.out.print("6) Genre: ");
        for (int j = 0; j < movieGenre.length; j++) {
            if (j != movieGenre.length - 1)
                System.out.print(movieGenre[j] + ", ");
            else
                System.out.print(movieGenre[j] + ".\n");}
        System.out.println("7) Director: "+ movie.getDirector());
        System.out.print("8) Cast: ");
        String[] movieCast = movie.getCast();
        for (i = 0; i < movieCast.length; i++) {
            if (i != movieCast.length - 1)
                System.out.print(movieCast[i] + ", ");
            else
                System.out.print(movieCast[i] + ".\n");	}
        showTill = movie.getShowTill();
        if (showTill!= null){
            System.out.println("9) Show Till: " + showTill);
        }
        else{
            System.out.println("9) Show Till: " + "Not specified.");
        }
        System.out.println("Select detail to edit: ");
        switch (UserInputOutput.getUserChoice(1, 9)) {
            case 1: // duration
                System.out.println("Enter the movie duration in minutes: ");
                duration = UserInputOutput.getUserChoice(1, 500);
                movie.setDuration(duration);
                break;
            case 2: // synopsis
                synopsis = UserInputOutput.getStringInput("Enter the movie synopsis: ");
                movie.setSynopsis(synopsis);
                break;
            case 3: // status: now showing
                i = 1;
                System.out.println("Select movie status type from the following options:");
                for (String statustype : movieFunctions.getStatusTypes()) {
                    System.out.println(i + ". " + statustype);
                    i++;
                }
                statusType = UserInputOutput.getUserChoice(1, i - 1) - 1;
                movie.setStatusType(movie.getStatusTypes()[statusType]);
                System.out.println("At staff control, statusType is " + movie.getStatusType());
                break;
            case 4: // movie type : 3D
                i = 1;
                System.out.println("Select movie type from the following options:");
                for (String movietype : movieFunctions.getMovieClasses()) {
                    System.out.println(i + ". " + movietype);
                    i++;
                }
                movieClass = UserInputOutput.getUserChoice(1, i - 1) - 1;
                movie.setMovieClass(movie.getMovieClasses()[movieClass]);
                break;
            case 5: // age type PG13
                i = 1;
                System.out.println("Select movie age restriction from the following options:");
                for (String movieagetype : movieFunctions.getAgeTypes()) {
                    System.out.println(i + ". " + movieagetype);
                    i++;
                }
                ageType = UserInputOutput.getUserChoice(1, i - 1) - 1;
                movie.setAgeType(movie.getAgeTypes()[ageType]);
                break;
            case 6: // Genre
                genre = UserInputOutput.getStringInput("Enter the movie genre with a comma separating them. \nGenre: ")
                        .split(",");
                movie.setGenre(genre);
                break;
            case 7: // Director
                director = UserInputOutput.getStringInput("Enter the movie director: ");
                movie.setDirector(director);
                break;
            case 8: // cast
                cast = UserInputOutput.getStringInput("Enter the cast names with a comma separating the cast names. \nCast names: ")
                        .split(",");
                movie.setCast(cast);
                break;
            case 9: // show till
                int year;
                int month;
                int day;

                boolean loop = true;
                while(loop) {
                    System.out.println("Insert end date and time of the movie");
                    day = UserInputOutput.getDateIntInput("Insert the day (in number) e.g. 09: ", 1, 31);
                    month = UserInputOutput.getDateIntInput("Insert the month (in number) e.g 08: ", 1, 12);
                    year = UserInputOutput.getDateIntInput("Insert the year (in number) e.g 2019: ", LocalDate.now().getYear(), 9999);

                    showTill = LocalDate.of(year, month, day);
                    if(showTill.isAfter(LocalDate.now())){
                        movie.setShowTill(showTill);
                        loop = false;
                    }
                    else{
                        System.out.println("Sorry you cant enter a date from the past, please try again.");
                    }
                }
                break;
            default:
                break;
        }

        movieList.set(movieIndex, movie);

        try{
            FileIO.writeObject("movie.txt", movieList);
        }catch (IOException e){
            System.out.println("File not found. please try again.");
            return;
        }
    }

    /**
     * Deletes a movie from the movie.txt file
     */
    public void deleteMovieListing() {
        ArrayList<Movie> movieList;
        try{
            movieList = (ArrayList<Movie>) FileIO.readObject("movie.txt");
        }catch (ClassNotFoundException e){
            System.out.println("File not found. please try again.");
            return;
        }catch (IOException e){
            System.out.println("File not found. please try again.");
            return;
        }

        int k = 1;
        UserInputOutput.displayHeader("Movie List");
        for (Movie m: movieList)
        {
            System.out.println(k + ": " + m.getTitle() + ", movie status: " + m.getStatusType());
            k++;
        }
        System.out.println("Select movie to delete: ");
        int movieIndex = UserInputOutput.getUserChoice(1, movieList.size()) - 1;
        movieList.remove(movieIndex);
        try{
            FileIO.writeObject("movie.txt", movieList);
        }catch (IOException e){
            System.out.println("File not found. please try again.");
            return;
        }
        System.out.println("Movie deleted successfully!");
    }

    /**
     * Listing Top Movies for staff
     */
    public void listTopMovies() {
        UserInputOutput.displayHeader("Top 5 Movies");
        System.out.println(
                "1. List top 5 ranking movies by ticket sales.\n" +
                        "2. List top 5 ranking movies by Overall reviewers' rating.\n"+
                        "3. Go back");
        TopMovieFactory movieFactory = new TopMovieFactory();
        Top5CurrentMovies top5CurrentMovies = null;
        switch (UserInputOutput.getUserChoice(1, 3)) {
            case 1:
                top5CurrentMovies = movieFactory.makeTop5Movie("ticket");
                top5CurrentMovies.printTop5Movies();
                break;
            case 2:
                top5CurrentMovies = movieFactory.makeTop5Movie("rating");
                top5CurrentMovies.printTop5Movies();
                break;
            case 3:
                break;
        }
    }
}