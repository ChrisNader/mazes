package chrisna.sandbox.mazes.api.http;

import chrisna.sandbox.mazes.api.MazesService;
import chrisna.sandbox.mazes.api.RenderingService;
import chrisna.sandbox.mazes.domain.Grid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/mazes")
public class MazesHttpApi {
    @Autowired
    MazesService mazesService;
    @Autowired
    RenderingService renderingService;

    @GetMapping("/maze")
    public Mono<Grid> generateGrid(
            @RequestParam int rows,
            @RequestParam int columns,
            @RequestParam(defaultValue = "bt") String algo) {
        return switch (algo) {
            case "bt" -> Mono.just(mazesService.binaryTree(rows, columns));
            default -> Mono.just(mazesService.sidewinder(rows, columns));
        };
    }

    @GetMapping(path = "/mazeImg", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage generateImg(
            @RequestParam int rows,
            @RequestParam int columns,
            @RequestParam(defaultValue = "bt") String algo,
            @RequestParam(defaultValue = "16") int scale) {
        return switch (algo) {
            case "bt" -> renderingService.getBufferedImage(
                    mazesService.binaryTree(rows, columns), scale);
            default -> renderingService.getBufferedImage(
                    mazesService.sidewinder(rows, columns), scale);
        };
    }

}
