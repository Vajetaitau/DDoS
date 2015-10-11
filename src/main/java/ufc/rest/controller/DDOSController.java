package ufc.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ufc.constants.Urls;
import ufc.core.service.firstLayer.DDOSService;
import ufc.dto.ddos.GroupedSourceIpsDetails;
import ufc.rest.response.ddos.DDOSGetGroupedSourceIpsResponse;
import ufc.rest.response.ddos.DDOSGetLineResponse;

import java.util.List;

@Controller()
@RequestMapping("ddos")
public class DDOSController extends AbstractController {

    private final DDOSService ddosService;

    @Autowired
    public DDOSController(DDOSService ddosService) {
        this.ddosService = ddosService;
    }

    @RequestMapping(value = Urls.GET_LINE, method = RequestMethod.GET)
    @ResponseBody
    public DDOSGetLineResponse getCurrentUser() {
        DDOSGetLineResponse response = new DDOSGetLineResponse();
        try {
            response.setLine(ddosService.getLine());
        } catch (Exception e) {

        }
        return response;
    }

    @RequestMapping(value = Urls.UPLOAD_FILE_TO_THE_DATABASE, method = RequestMethod.POST)
    @ResponseBody
    public void uploadFileToTheDatabase() {
        try {
            ddosService.uploadFileToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = Urls.GET_GROUPED_SOURCE_IPS, method = RequestMethod.GET)
    @ResponseBody
    public DDOSGetGroupedSourceIpsResponse getGroupedSourceIps(
            @RequestParam("threshold") Integer countTreshold
            , @RequestParam("limit") Integer limit
            , @RequestParam("order") String order) {
        DDOSGetGroupedSourceIpsResponse response = new DDOSGetGroupedSourceIpsResponse();
        try {
            List<GroupedSourceIpsDetails> list = ddosService.getGroupedSourceIps(countTreshold, limit, order);
            response.setList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
