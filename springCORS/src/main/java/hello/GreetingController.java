package hello;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/greeting")
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Greeting greeting(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
    public ResponseEntity<Greeting> greetingPost(
            @RequestBody Greeting userProvided
            ) {
        System.out.println("POST");
        System.out.println(userProvided.getContent());
        return new ResponseEntity<Greeting>(new Greeting(counter.incrementAndGet(), userProvided.getContent()), 
                new HttpHeaders(), HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PATCH, consumes = { "application/json" })
    public ResponseEntity<Greeting> greetingUpdate(
            @RequestBody Greeting userProvided
            ) {
        System.out.println("PATCH");
        System.out.println(userProvided.getContent());
        return new ResponseEntity<Greeting>(new Greeting(counter.incrementAndGet(), userProvided.getContent()), 
                new HttpHeaders(), HttpStatus.OK);
    }

}