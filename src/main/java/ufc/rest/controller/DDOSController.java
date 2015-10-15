package ufc.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufc.constants.Urls;
import ufc.core.service.firstLayer.DDOSService;
import ufc.dto.ddos.GroupedIpDetails;
import ufc.dto.ddos.PacketCountInTimeInterval;
import ufc.rest.request.PacketCountInTimeIntervalsRequest;
import ufc.rest.response.ddos.DDOSGetGroupedSourceIpsResponse;
import ufc.rest.response.ddos.DDOSGetLineResponse;
import ufc.rest.response.ddos.DDOSGetPacketCountsInTimeIntervalResponse;

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

    @RequestMapping(value = Urls.GET_GROUPED_IPS, method = RequestMethod.GET)
    @ResponseBody
    public DDOSGetGroupedSourceIpsResponse getGroupedSourceIps(
            @RequestParam("threshold") Integer countTreshold
            , @RequestParam("limit") Integer limit
            , @RequestParam(value = "source", required = true) boolean source
            , @RequestParam("order") String order) {
        DDOSGetGroupedSourceIpsResponse response = new DDOSGetGroupedSourceIpsResponse();
        try {
            if (source) {
                List<GroupedIpDetails> list = ddosService.getGroupedSourceIps(countTreshold, limit, order);
                response.setList(list);
            } else {
                List<GroupedIpDetails> list = ddosService.getGroupedDestinationIps(countTreshold, limit, order);
                response.setList(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

//    @RequestMapping(value = Urls.GET_PACKET_COUNTS_IN_TIME_INTERVAL, method = RequestMethod.GET)
//    @ResponseBody
//    public DDOSGetPacketCountsInTimeIntervalResponse getPacketCountsInTimeInterval(
//            @RequestParam("multiplier") Long multiplier
//            , @RequestParam("dividor") Long dividor
//            , @RequestParam("ip") String ipAddress
//            , @RequestParam("firstResult") Integer firstResult
//            , @RequestParam("maxResults") Integer maxResults) {
//        DDOSGetPacketCountsInTimeIntervalResponse response = new DDOSGetPacketCountsInTimeIntervalResponse();
//        try {
//            List<PacketCountInTimeInterval> list = ddosService.getPacketCountInTimeIntervals(multiplier, dividor, ipAddress, firstResult, maxResults);
//            response.setList(list);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return response;
//    }

    @RequestMapping(value = Urls.GET_PACKET_COUNTS_IN_TIME_INTERVAL, method = RequestMethod.POST)
    @ResponseBody
    public DDOSGetPacketCountsInTimeIntervalResponse getPacketCounts(@RequestBody PacketCountInTimeIntervalsRequest request) {
        DDOSGetPacketCountsInTimeIntervalResponse response = new DDOSGetPacketCountsInTimeIntervalResponse();
        try {
            List<PacketCountInTimeInterval> list = ddosService.findPacketCounts(request.getStart(), request.getEnd(), request.getIncrement(), request.getList());
            response.setList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
