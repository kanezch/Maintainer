package me.kanezheng.app;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@RestController
@RequestMapping(value = "/v1/microservices")
public class MicroServiceController {

    private static final Logger logger = LogManager.getLogger(MicroServiceController.class);

    @Autowired
    IMsService msService;

    @RequestMapping(value="",method = RequestMethod.GET)
    public  ResponseEntity<FindResultResponse> getMicroServices(@RequestParam(value = "pageNum")int pageNum,
                                                                @RequestParam(value = "pageSize")int pagaSize){

        FindResultResponse resultResponse = msService.findPageAble(pageNum,pagaSize);


        logger.info("Get microServices succeed!");
        return new ResponseEntity(resultResponse, HttpStatus.OK);
    }

    @RequestMapping(value="/{msName}", method = RequestMethod.GET)
    public ResponseEntity<MicroService> getMicroServiceByName(@PathVariable("msName") String msName){

        MicroService m = msService.findMicroServiceByName(msName);
        if (null == m){

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else {

            return new ResponseEntity(m, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity addMicroService(@RequestParam(value = "msName")String msName,
                                                  @RequestParam(value = "msTeam")String msTeam,
                                                  @RequestParam(value = "msMaintainer")String msMaintainer,
                                                  @RequestParam(value = "msDesc")String msDesc,
                                                  @RequestParam(value = "codeLang")String codeLang,
                                                  @RequestParam(value = "bIsRestWS")Boolean bIsRestWS,
                                                  @RequestParam(value = "servicePort")int servicePort){
        MicroService ms = new MicroService();
        ms.setMsName(msName);
        ms.setMsTeam(msTeam);
        ms.setMsMaintainer(msMaintainer);
        ms.setMsDesc(msDesc);
        ms.setCodeLang(codeLang);
        ms.setbIsRestWS(bIsRestWS);
        ms.setServicePort(servicePort);

        int c = msService.add(ms);
        if (c == 1){
            logger.info("Add new microService succeed!");
            return new ResponseEntity(HttpStatus.CREATED);
        }else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{msName}", method = RequestMethod.PUT)
    public ResponseEntity updateMicroService(@PathVariable("msName")String msName,
                                             @RequestParam(value = "msTeam")String msTeam,
                                             @RequestParam(value = "msMaintainer")String msMaintainer,
                                             @RequestParam(value = "msDesc")String msDesc,
                                             @RequestParam(value = "codeLang")String codeLang,
                                             @RequestParam(value = "bIsRestWS")Boolean bIsRestWS,
                                             @RequestParam(value = "servicePort")int servicePort){
        MicroService ms = new MicroService();
        ms.setMsName(msName);
        ms.setMsTeam(msTeam);
        ms.setMsMaintainer(msMaintainer);
        ms.setMsDesc(msDesc);
        ms.setCodeLang(codeLang);
        ms.setbIsRestWS(bIsRestWS);
        ms.setServicePort(servicePort);

        int c = msService.update(ms);
        if (c == 1){
            return new ResponseEntity(HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/{msName}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMicroService(@PathVariable("msName")String msName){
       int c = msService.delete(msName);
        if (c == 1){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
