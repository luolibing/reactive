package cn.tim.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * User: luolibing
 * Date: 2017/11/28 11:14
 */
public class ReactorTest {

    private static List<String> words = Arrays.asList(
            "the",
            "quick",
            "brown",
            "fox",
            "jumped",
            "over",
            "the",
            "lazy",
            "dog"
    );

    /**
     * Flux和Mono
     * Flux可以触发0到多个事件，Mono最多只触发一个事件
     */
    @Test
    public void flux() {
        // 类似与RxJava
        Flux<String> fewWords = Flux.just("Hello", "World");
        Flux<String> manyWords = Flux.fromIterable(words);
        fewWords.subscribe(System.out::println);
        manyWords.subscribe(System.out::println);
    }

    @Test
    public void missing() {
        Mono<String> missing = Mono.just("s");

        Flux.fromIterable(words)
                // 将单词拆分为字母
                .flatMap(word -> Flux.fromArray(word.split("")))
                // 连接Flux
                .concatWith(missing)
                .distinct()
                .sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE), (str, count) -> String.format("%2d. %s", count, str))
                .subscribe(System.out::println);
    }

    @Test
    public void shortCircuit() throws InterruptedException {
        Flux<String> helloPauseWorld =
                Mono.just("Hello")
                .concatWith(Mono.just("World"))
                .delaySubscription(Duration.ofMillis(200));
        // 异步非阻塞实例
        helloPauseWorld.subscribe(s -> System.out.println(s + Thread.currentThread().getId()));
        Thread.sleep(500);
    }

    @Test
    public void block() {
        Flux<String> helloPauseWorld =
                Mono.just("Hello")
                        .concatWith(Mono.just("World"))
                        .delaySubscription(Duration.ofSeconds(2));
        // toStream()转换为非响应式模式，生成阻塞实例
        helloPauseWorld.toStream().forEach(System.out::println);
    }

    @Test
    public void firstEmitting() {
        Mono<String> a = Mono.just("mono just")
                .delaySubscription(Duration.ofMillis(600));
        Flux<String> b = Flux.just("flux", "just", "start")
                .delaySubscription(Duration.ofMillis(550));
        // 选择第一个到达的来触发, 这个会选择Flux来触发
        Flux.first(a, b)
                .toIterable()
                .forEach(System.out::println);

    }
}
