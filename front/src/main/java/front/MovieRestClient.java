package front;

import front.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Service
public class MovieRestClient implements MovieService{

    @Autowired
    private MovieFeignClient movieFeignClient;

    @Override
    public List<Movie> getMovies() {
        return movieFeignClient.getMovies();

    }

    @Override
    public Long postMovie(Movie movie) {
        return movieFeignClient.postMovie(movie);
    }

    @FeignClient(name = "movie", url = "${api.url.movie}")
    public interface MovieFeignClient {

        @GetMapping(value={"/samples"})
        List<Movie> getMovies();

        @PostMapping(value={"/movies"})
        Long postMovie(Movie movie);

    }

}
