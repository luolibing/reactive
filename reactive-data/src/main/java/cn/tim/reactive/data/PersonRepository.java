package cn.tim.reactive.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * User: luolibing
 * Date: 2017/11/29 15:19
 */
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
}
