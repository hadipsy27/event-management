package mii.co.id.emsserverside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import mii.co.id.emsserverside.model.Topic;
import mii.co.id.emsserverside.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//</editor-fold>

@Service
public class TopicService {
        private TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
    
    public List<Topic> getAllTopic(){
        return topicRepository.findAll();
    }
}
