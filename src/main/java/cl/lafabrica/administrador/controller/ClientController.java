package cl.lafabrica.administrador.controller;

import cl.lafabrica.administrador.modelo.Message;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class ClientController {

    @GetMapping("/list")
    public List<Message> list(){
        return Collections.singletonList(new Message("test list"));
    };

    @PostMapping("/create")
    public Message create(@RequestBody Message message){
        return message;
    }

    @GetMapping("/authorized")
    public Map<String, String> authorized(@RequestParam String code){
        return Collections.singletonMap("code", code);
    }
}
