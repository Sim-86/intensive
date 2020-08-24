package front;

import front.domain.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getMovies();
    Long postMovie(Movie movie);
}
