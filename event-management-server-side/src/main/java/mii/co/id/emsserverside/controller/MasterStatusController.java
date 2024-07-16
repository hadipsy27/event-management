package mii.co.id.emsserverside.controller;

//<editor-fold defaultstate="collapsed" desc="comment">
import java.util.List;
import mii.co.id.emsserverside.model.MasterStatus;
import mii.co.id.emsserverside.service.MasterStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//</editor-fold>

@RestController
@RequestMapping("/master-status")
public class MasterStatusController {
    
    private MasterStatusService masterStatusService;

    @Autowired
    public MasterStatusController(MasterStatusService masterStatusService) {
        this.masterStatusService = masterStatusService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<List<MasterStatus>> countEvent(@PathVariable("id") Long id) {
        return new ResponseEntity(masterStatusService.getStatusByType(id), HttpStatus.OK);
    }
}
