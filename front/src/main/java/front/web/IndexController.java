package front.web;


import front.MovieService;
import front.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    @Autowired
    MovieService movieService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("movie", movieService.getMovies());
        return "index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        //model.addAttribute("movie", movieService.getMovies());
        return "save";
    }

    @PostMapping("/movies")
    public String movies(@RequestBody Movie movie) {
        System.out.println(movie.getMovieName());
        System.out.println(movie.getRunTime());
        movieService.postMovie(movie);
        return "index";
    }
}
