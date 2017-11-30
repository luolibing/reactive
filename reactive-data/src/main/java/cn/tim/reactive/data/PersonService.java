package cn.tim.reactive.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * User: luolibing
 * Date: 2017/11/29 15:42
 */
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Flux<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Mono<Person> getPersonById(String id) {
        return personRepository.findById(id);
    }

    public Flux<PersonEvent> getEventsForPerson(String id) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(index -> new PersonEvent(id, LocalDateTime.now()))
                .log();
    }
}
