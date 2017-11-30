package cn.tim.reactive.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * User: luolibing
 * Date: 2017/11/29 15:21
 */
@Component
public class DataLoader {

    @Autowired
    private PersonRepository personRepository;

    @PostConstruct
    public void initData() {
        List<Person> personToSave = Arrays.asList(
                new Person(UUID.randomUUID().toString(), "luolibing", "tim"),
                new Person(UUID.randomUUID().toString(), "liuxiaoling", "ling"));
        personRepository.deleteAll()
                .thenMany(personRepository.saveAll(personToSave))
                .subscribe();
    }
}
