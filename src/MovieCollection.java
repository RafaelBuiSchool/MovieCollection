import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCastMember();
        }
        else if (option.equals("k"))
        {
            searchKeyword();
        }
        else if (option.equals("g"))
        {
            searchGenre();
        }
        else if (option.equals("r"))
        {
            topRatedMovies();
        }
        else if (option.equals("h"))
        {
            highestRevenueMovies();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }
    private void searchKeyword()
    {
        System.out.print("Enter a keyword search term: ");
        String keyWordSearchTerm = scanner.nextLine();

        keyWordSearchTerm = keyWordSearchTerm.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieKeyWord = movies.get(i).getKeywords();
            movieKeyWord = movieKeyWord.toLowerCase();

            if (movieKeyWord.indexOf(keyWordSearchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String keyWord = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + keyWord);
        }

        System.out.println("Which Movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }
    private void searchCastMember(){
        System.out.print("Enter a cast member: ");
        String searchCastMember = scanner.nextLine();
        searchCastMember = searchCastMember.toLowerCase();

        ArrayList<String> results = new ArrayList<>();

        for (Movie i: movies)
        {
            String[] castList = i.getCast().split("\\|");
            for(String x: castList){
                x = x.toLowerCase();
                if (!results.contains(x)) {
                    results.add(x);
                }
            }
        }

        ArrayList<String> filteredCast = new ArrayList<>();

        for (String i: results)
        {
            if(i.contains(searchCastMember)){
                filteredCast.add(i);
            }
        }
        sortResultsString(filteredCast);

        for(int i = 0;i<filteredCast.size();i++){
            System.out.println(i+1 + ". " + filteredCast.get(i));
        }

        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String chosenCast = filteredCast.get(choice-1);
        chosenCast = chosenCast.toLowerCase();
        ArrayList<String> moviesWithCharacter = new ArrayList<>();

        for(Movie i: movies){
            if (i.getCast().toLowerCase().contains(chosenCast)){
                moviesWithCharacter.add(i.getTitle());
            }
        }

        sortResultsString(moviesWithCharacter);
        for(int i = 0;i<moviesWithCharacter.size();i++){
            System.out.println(i+1+". " + moviesWithCharacter.get(i));
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int chosenMovie = scanner.nextInt();
        scanner.nextLine();

        String selectedMovieTitle = moviesWithCharacter.get(chosenMovie - 1);
        Movie userMovie = null;
        for(Movie i: movies){
            if(i.getTitle().equalsIgnoreCase(selectedMovieTitle)){
                userMovie = i;
                break;
            }
        }
        displayMovieInfo(userMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }
    private void searchGenre(){
        ArrayList<String> everyGenre = new ArrayList<>();
        for(Movie i: movies){
            String[] genres = i.getGenres().split("\\|");
            for(String x:genres){
                if(!everyGenre.contains(x)){
                    everyGenre.add(x);
                }
            }
        }
        sortResultsString(everyGenre);

        for(int i = 0; i<everyGenre.size();i++){
            System.out.println(i+1+". " + everyGenre.get(i));
        }
        System.out.println("Choose a genre: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String chosenGenre = everyGenre.get(choice-1);

        ArrayList<String> moviesGenre = new ArrayList<>();

        for(Movie i: movies){
            if(i.getGenres().toLowerCase().contains(chosenGenre.toLowerCase())){
                moviesGenre.add(i.getTitle());
            }
        }
        sortResultsString(moviesGenre);

        for(int i = 0; i<moviesGenre.size();i++){
            System.out.println(i+1 + ". " + moviesGenre.get(i));
        }

        System.out.println("Choose a title: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        String selectedMovieTitle = moviesGenre.get(choice - 1);
        Movie userMovie = null;
        for(Movie i: movies){
            if(i.getTitle().equalsIgnoreCase(selectedMovieTitle)){
                userMovie = i;
                break;
            }
        }
        displayMovieInfo(userMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }
    private void topRatedMovies(){
        ArrayList<Movie> highestRatedMovies = new ArrayList<>(movies);
        for(int i = 0; i < highestRatedMovies.size()-1;i++){
            for(int x = 0; x < highestRatedMovies.size()-i-1;x++){
                if(highestRatedMovies.get(x).getUserRating() < highestRatedMovies.get(x+1).getUserRating()){
                    Movie tempVariable = highestRatedMovies.get(x);
                    highestRatedMovies.set(x,highestRatedMovies.get(x+1));
                    highestRatedMovies.set(x+1,tempVariable);
                }
            }
        }
        for(int i = 0; i < Math.min(50,highestRatedMovies.size());i++){
            Movie x = highestRatedMovies.get(i);
            System.out.println(i+1 + ". " + x.getTitle()+ ": " + x.getUserRating());
        }
        System.out.println("Choose a title: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedTitle = highestRatedMovies.get(choice - 1);

        displayMovieInfo(selectedTitle);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void highestRevenueMovies(){
        ArrayList<Movie>highestRevenue = new ArrayList<>(movies);
        for(int i = 0; i < highestRevenue.size()-1;i++){
            for(int x = 0; x < highestRevenue.size()-i-1;x++){
                if(highestRevenue.get(x).getRevenue() < highestRevenue.get(x+1).getRevenue()){
                    Movie tempVariable = highestRevenue.get(x);
                    highestRevenue.set(x,highestRevenue.get(x+1));
                    highestRevenue.set(x+1,tempVariable);
                }
            }
        }
        for(int i = 0; i < Math.min(50,highestRevenue.size());i++){
            Movie x = highestRevenue.get(i);
            System.out.println(i+1 + ". " + x.getTitle()+ ": " + x.getRevenue());
        }
        System.out.println("Choose a title: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedTitle = highestRevenue.get(choice - 1);

        displayMovieInfo(selectedTitle);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }
    private void sortResultsString(ArrayList<String> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            String temp = listToSort.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }


    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {

    }

    private void searchKeywords()
    {

    }

    private void listGenres()
    {

    }

    private void listHighestRated()
    {

    }

    private void listHighestRevenue()
    {

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}