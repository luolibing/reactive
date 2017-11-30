package cn.tim.reactive.web;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * User: luolibing
 * Date: 2017/11/29 11:04
 */
@RestController
public class ExampleRestController {

    @GetMapping("/hello/{who}")
    public Mono<String> hello(@PathVariable String who) {
        return Mono.just(who)
                .map(w -> "Hello " + w + "!");
    }

    @PostMapping("/heyMister")
    public Flux<String> hey(@RequestBody Mono<Person> person) {
        return Mono.just("Hey mister ")
                .concatWith(person
                        .flatMap(sir -> Flux.fromArray(sir.getLastName().split("")).single())
                        .map(String::toUpperCase)
                ).concatWith(Mono.just(". how are you?"));
    }
}
