package cn.tim.reactive.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * User: luolibing
 * Date: 2017/11/29 15:57
 */
@Configuration
public class FunctionalReactiveConfig {

    @Autowired
    private PersonService personService;

    @Bean
    public RouterFunction routerFunction() {
        return route(
                        GET("/persons"), (req) ->
                            ServerResponse.ok().body(
                                    personService.getAllPersons(), Person.class))
                .andRoute(
                        GET("/person/{id}"), (req) ->
                            ServerResponse
                                    .ok().body(personService.getPersonById(req.pathVariable("id")), Person.class)
                                    .defaultIfEmpty(ServerResponse.notFound().build().block())
                )
                .andRoute(
                        GET("/person/{id}/events"), request -> ServerResponse
                                .ok()
                                .contentType(MediaType.TEXT_EVENT_STREAM)
                                .body(personService.getEventsForPerson(request.pathVariable("id")), PersonEvent.class)
                );
    }
}
