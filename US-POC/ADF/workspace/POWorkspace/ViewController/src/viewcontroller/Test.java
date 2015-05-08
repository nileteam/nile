package viewcontroller;

import com.poworkspace.view.utils.BPMContextUtils;

import java.util.Iterator;
import java.util.List;

import java.util.Map;

import oracle.bpel.services.bpm.common.IBPMContext;

import oracle.bpm.services.processmetadata.IProcessMetadataService;
import oracle.bpm.services.processmetadata.ProcessMetadataSummary;

public class Test {
    public Test() {
        super();
    }

    public static void main(String[] args) throws Exception {
        IBPMContext ibpmContext = BPMContextUtils.getIBPMContext("shekhar", "welcome1");
        IProcessMetadataService service = BPMContextUtils.getBPMServiceClient().getProcessMetadataService();
        List<ProcessMetadataSummary> initiableTasks = service.getInitiatableProcesses(ibpmContext);

        //        System.out.println("Size of initiatable tasks list >> " + initiableTasks.size());
        for (int i = 0; i < initiableTasks.size(); i++) {

            ProcessMetadataSummary pms = initiableTasks.get(i);

            System.out.println(pms.getCompositeDN() + "/" + pms.getProcessName());
            
            System.out.println("Has Role>>>>" + pms.isHasSwimlaneRole());
            
            Map<String, String> map = service.getProcessRoles(ibpmContext, pms.getCompositeDN());
            
            Iterator itr = map.keySet().iterator();
            while(itr.hasNext()) {
                Object logicalRole = itr.next();
                Object physicalRole = map.get(logicalRole);
                
                System.out.println(logicalRole + "<--------------->" + physicalRole);
                
            }
            
        }
        
        
        
        
    }
}
