package mii.co.id.emsserverside.controller;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import mii.co.id.emsserverside.model.Topic;
import mii.co.id.emsserverside.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//</editor-fold>

@RestController
@RequestMapping("/topic")
public class TopicController {
    private TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }
    
    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopic() {
        return new ResponseEntity(topicService.getAllTopic(), HttpStatus.OK);
    }
}