package mii.co.id.emsserverside.service;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import mii.co.id.emsserverside.model.MasterStatus;
import mii.co.id.emsserverside.repository.MasterStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//</editor-fold>

@Service
public class MasterStatusService {

    private MasterStatusRepository masterStatusRepository;

    @Autowired
    public MasterStatusService(MasterStatusRepository masterStatusRepository) {
        this.masterStatusRepository = masterStatusRepository;
    }

    public List<MasterStatus> getStatusByType(Long id) {
        return masterStatusRepository.findByStatusType_id(id);
    }
}
