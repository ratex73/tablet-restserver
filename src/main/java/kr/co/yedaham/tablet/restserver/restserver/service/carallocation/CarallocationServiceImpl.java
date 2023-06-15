package kr.co.yedaham.tablet.restserver.restserver.service.carallocation;

import kr.co.yedaham.tablet.restserver.restserver.advice.ExceptionAdvice;
import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CCommunicationException;
import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CExistingNewCarallocationException;
import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CMaxCountOverException;
import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CUseridSigninFailedException;
import kr.co.yedaham.tablet.restserver.restserver.config.restslack.SlackSenderManager;
import kr.co.yedaham.tablet.restserver.restserver.entity.*;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.*;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.CarallocationInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.CarallocationReturnInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.FunSmsEmpList;
import kr.co.yedaham.tablet.restserver.restserver.model.slack.SlackTarget;
import kr.co.yedaham.tablet.restserver.restserver.resp.carallocation.*;
import kr.co.yedaham.tablet.restserver.restserver.service.sms.TabletSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarallocationServiceImpl implements CarallocationService {
    private final CarallocationInfoResp carallocationInfoResp;
    private final CarallocationSeqResp carallocationSeqResp;
    private final CarallocationSmsHistoryResp carallocationSmsHistoryResp;
    private final CarallocationRentalResp carallocationRentalResp;
    private final CarallocationMiddleCategoryResp carallocationMiddleCategoryResp;
    private final CarallocationReturnResp carallocationReturnResp;
    private final CarallocationReturnAddListResp carallocationReturnAddListResp;
    private final TabletSmsService tabletSmsService;
    private final ExceptionAdvice exceptionAdvice;
    private final SlackSenderManager slackSenderManager;

    @Override
    @Transactional
    public CarallocationSaveInfo saveCarallcationAndSmsHistory(CarallocationSaveInfo carallocationSaveInfo){

        int financeCount = carallocationInfoResp.getFinanceCount(carallocationSaveInfo.getCarallocationId().getFunCtrlNo());

        if(financeCount == 1){
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowDateString = now.format(date);
        carallocationSaveInfo.setRegisterDay(nowDateString);

        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
        String nowTimeString = now.format(time);
        carallocationSaveInfo.setRegisterTime(nowTimeString);

        ModelMapper modelMapper = new ModelMapper();

        String funCtrlNo = carallocationSaveInfo.getCarallocationId().getFunCtrlNo();
        int carInfoSeq = carallocationInfoResp.getNextValMySequence(carallocationSaveInfo.getCarallocationId().getFunCtrlNo());
        carallocationSaveInfo.getCarallocationId().setLine(carInfoSeq);
        CarallocationInfoEntity save = carallocationInfoResp.save(CarallocationInfoEntity.builder().carallocationSaveInfo(carallocationSaveInfo).build());
        CarallocationSaveInfo result = modelMapper.map(save, CarallocationSaveInfo.class);

        CarallocationSeq seqSave = new CarallocationSeq();
        String seq = carallocationSeqResp.getNextValMySequence();
        Integer parseSeq = Integer.parseInt(seq)+1;
        String nextSeq = String.format("%010d",parseSeq);
        seqSave.setSeq(nextSeq);
        CarallocationSeqEntity entity = new CarallocationSeqEntity();
        entity.setSeq(nextSeq);
        carallocationSeqResp.save(entity);

        List<String> carllocationCaptionList = new ArrayList<>();
        carllocationCaptionList.add("01080627677");
        carllocationCaptionList.add("01035461190");
        for (int i = 0; i< carllocationCaptionList.size();i++) {
             tabletSmsService.sendLmsMessage(carllocationCaptionList.get(i),carallocationSaveInfo.getContents());
        }



        CarallocationSmsHistory carallocationSmsHistory = new CarallocationSmsHistory();
        carallocationSmsHistory.setFunCtrlNo(funCtrlNo);
        carallocationSmsHistory.setItemCkNo(nextSeq);
        carallocationSmsHistory.setHighLine(carInfoSeq);
        carallocationSmsHistoryResp.save(CarallocationSmsHistoryEntity.builder().carallocationSmsHistory(carallocationSmsHistory).build());

        return result;
    }



    @Override
    public ArrayList<CarallocationInfo> getCarallocationList(String functrlno) {
        ArrayList<CarallocationInfo> carallocationList = carallocationSeqResp.getCarallocationList(functrlno);
        return carallocationList;
    }

    @Override
    public ArrayList<CarallocationReturnInfo> getCarallocationReturnList(String functrlno) {
        ArrayList<CarallocationReturnInfo> carallocationReturnInfo = carallocationSeqResp.getCarallocationReturnList(functrlno);
        return carallocationReturnInfo;
    }

    @Override
    public List<CarallocationProductInfo> getCarallocationProductInfo() {
        List<CarallocationProductInfo> carallocationProductInfo = carallocationSeqResp.getProductInfo();
        return carallocationProductInfo;
    }

    @Override
    public List<CarallocationRentalInfo> getCarallocationRentalList(String funno, String itemno) {
        List<CarallocationRentalInfo> carallocationLentalInfo = carallocationSeqResp.getLentalList(funno, itemno);
        return carallocationLentalInfo;
    }

    @Override
    public List<CarallocationRental> saveCarallocationRental(List<CarallocationRental> carallocationRental) {
        if(StringUtils.isEmpty(carallocationRental)){
            return null;
        }

        List<CarallocationRentalEntitiy> addList = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();


        int count = Integer.parseInt(carallocationRentalResp.getNextLine(carallocationRental.get(0).getFunCtrlNo(), carallocationRental.get(0).getItemCkNo()));

        for (CarallocationRental carallocation : carallocationRental) {
            CarallocationSmsHistoryEntity byFunCtrlNoAndItemCkNo = carallocationSmsHistoryResp.findByFunCtrlNoAndItemCkNo(carallocation.getFunCtrlNo(), carallocation.getItemCkNo());
            carallocation.setLine(Integer.toString(count));
            carallocation.setGubun(byFunCtrlNoAndItemCkNo.getGubun());
            carallocation.setHighLine(byFunCtrlNoAndItemCkNo.getHighLine());
            carallocation.setDept(carallocationRentalResp.getDept(carallocation.getFunCtrlNo()));

            addList.add(CarallocationRentalEntitiy.builder().carallocationRental(carallocation).build());
            count++;

        }

        List<CarallocationRentalEntitiy> carallocationRentalEntitiys = new ArrayList<>();
        List<CarallocationRental> result = new ArrayList<>();

        int afterCount = Integer.parseInt(carallocationRentalResp.getNextLine(carallocationRental.get(0).getFunCtrlNo(), carallocationRental.get(0).getItemCkNo()));
        if(count == afterCount){
             new CUseridSigninFailedException();
        }else {
            carallocationRentalEntitiys = carallocationRentalResp.saveAll(addList);
            result = carallocationRentalEntitiys.stream().map(p -> modelMapper.map(p, CarallocationRental.class)).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public long deleteCarallocationRental(long seq) {
        carallocationRentalResp.deleteById(seq);
        return seq;
    }

    @Override
    public int getValueCarRegister(String funno) {
        return carallocationInfoResp.getValueCarRegister(funno);
    }

    @Override
    public CarallocationSaveInfo updateCarallocation(String funno, int line) {

        int maxCarallocationLineCount = carallocationInfoResp.getMaxCarallocationLineCount(funno);

        if(maxCarallocationLineCount != line) {
            throw new CExistingNewCarallocationException();
        }

        ModelMapper mapper = new ModelMapper();
        CarallocationInfoEntity carallocation = carallocationInfoResp.findByCarallocationIdFunCtrlNoAndCarallocationIdLine(funno, line);



        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String day = now.format(dateTimeFormatter);
        String time = now.format(timeFormatter);

        carallocation.setArriveDay(day);
        carallocation.setArriveTime(time);

        CarallocationInfoEntity update = carallocationInfoResp.save(carallocation);
        CarallocationSaveInfo carallocationSaveInfo = mapper.map(update, CarallocationSaveInfo.class);

        return carallocationSaveInfo;
    }

    @Override
    public CarallocationSaveInfo updateCarallocationSignature(String funno, int line, String signature, String arriveDay, String arriveTime) {
        ModelMapper mapper = new ModelMapper();
        CarallocationInfoEntity carallocation = carallocationInfoResp.findByCarallocationIdFunCtrlNoAndCarallocationIdLine(funno, line);
        carallocation.setSignature(signature);
        carallocation.setArriveDay(arriveDay);
        carallocation.setArriveTime(arriveTime);


        CarallocationInfoEntity update = carallocationInfoResp.save(carallocation);
        CarallocationSaveInfo carallocationSaveInfo = mapper.map(update, CarallocationSaveInfo.class);

        return carallocationSaveInfo;
    }

    @Override
    public List<CarallocationMiddleCategory> getMiddleCategory() {
        ModelMapper mapper = new ModelMapper();
        List<CarallocationMiddleCategoryEntity> middleCategory = carallocationMiddleCategoryResp.findAllByOrderByGroupcdAsc();

        return middleCategory.stream().map(p -> mapper.map(p, CarallocationMiddleCategory.class)).collect(Collectors.toList());
    }

    @Override
    public List<CarallocationMiddleProduct> getMiddelProductList(String itemcd) {
        ModelMapper mapper = new ModelMapper();
        List<CarallocationMiddleProduct> middleProductList = carallocationInfoResp.getMiddleProductList(itemcd);
        return middleProductList.stream().map(p -> mapper.map(p, CarallocationMiddleProduct.class)).collect(Collectors.toList());
    }

    @Override
    public List<CarallocationRentalStatus> getRentalStatus(String funno) {
        List<CarallocationRentalStatus> rentalStatusListMapping = new ArrayList<>();
        try {

            rentalStatusListMapping = carallocationRentalResp.findRentalStatusListMapping(funno);
            for(int seq = 0; seq < 6; seq++) {

                CarallocationReturnAdd returnAddInfo = carallocationReturnAddListResp.findReturnAddInfo(funno,String.valueOf(seq +1));
                int returnAddYedahamCount = returnAddInfo.getSceneYedahamCount() + returnAddInfo.getDeliveryYedahamCount() + returnAddInfo.getEtcYedahamCount();
                int returnAddOuterCount = returnAddInfo.getSceneOuterCount() + returnAddInfo.getDeliveryOuterCount() + returnAddInfo.getEtcOuterCount();


                int rentalYedahamCount = carallocationRentalResp.getRentalCount(funno, "2", String.valueOf(seq+1));
                int rentalOuterCount = carallocationRentalResp.getRentalCount(funno, "1", String.valueOf(seq + 1));


                int returnCount = returnAddYedahamCount + returnAddOuterCount;
                int rentalCount = rentalYedahamCount + rentalOuterCount;
                int remainCount =  rentalCount - returnCount;


                rentalStatusListMapping.get(seq).setReaminCount(remainCount);
            }

        }catch (Exception ex){
            System.out.println(ex.getMessage().toString());
        }

        return rentalStatusListMapping;
    }

    @Override
    public List<CarallocationReturnProduct> getReturnProduct(String funno) {
        ModelMapper mapper = new ModelMapper();
        return carallocationReturnResp.findReturnProductList(funno);
    }

    @Override
    @Transactional
    public CarallocationReturnDto saveReturnList(CarallocationReturnDto carallocationReturnDto) {
        ModelMapper mapper = new ModelMapper();
        CarallocationReturnDto result = new CarallocationReturnDto();

        CarallocationReturnEntity save = CarallocationReturnEntity.builder().returnSignature(carallocationReturnDto.getReturnSignature()).build();
        for (CarallocationReturn carallocationReturn : carallocationReturnDto.getCarallocationReturnList()) {

            CarallocationReturnAdd returnAddInfo = carallocationReturnAddListResp.findReturnAddInfo(carallocationReturn.getFunCtrlNo(), carallocationReturn.getType());

            int returnAddYedahamCount = returnAddInfo.getSceneYedahamCount() + returnAddInfo.getDeliveryYedahamCount() + returnAddInfo.getEtcYedahamCount();
            int returnAddOuterCount = returnAddInfo.getSceneOuterCount() + returnAddInfo.getDeliveryOuterCount() + returnAddInfo.getEtcOuterCount();


            int rentalYedahamCount = carallocationRentalResp.getRentalCount(carallocationReturn.getFunCtrlNo(), "2", carallocationReturn.getType());
            int rentalOuterCount = carallocationRentalResp.getRentalCount(carallocationReturn.getFunCtrlNo(), "1", carallocationReturn.getType());

            int returnYedahamCount = carallocationReturn.getSceneYedahamCount() + carallocationReturn.getDeliveryYedahamCount() + carallocationReturn.getEtcYedahamCount();
            int returnOuterCount = carallocationReturn.getSceneOuterCount() + carallocationReturn.getDeliveryOuterCount() + carallocationReturn.getEtcOuterCount();

            int totalReturnYedahmaCount = returnAddYedahamCount  + returnYedahamCount;
            int totalReturnOuterCount = returnAddOuterCount + returnOuterCount;





            if(rentalYedahamCount < totalReturnYedahmaCount) {
                throw  new CMaxCountOverException();
            }
            if(rentalOuterCount < totalReturnOuterCount) {
                throw new CMaxCountOverException();
            }


            save.addReturn(CarallocationReturnAddListEntity.builder().carallocationReturn(carallocationReturn).build());
        }


        CarallocationReturnEntity saveResult = carallocationReturnResp.save(save);
        result.setReturnSignature(mapper.map(saveResult, CarallocationReturnSignature.class));
        result.setCarallocationReturnList(saveResult.getAddCh013ds().stream().map(p -> mapper.map(p, CarallocationReturn.class)).collect(Collectors.toList()));

        return result;

    }

    @Override
    public String getCompare(String funno) {

        String compareResult = "";

        int compareRentalCount = carallocationRentalResp.getCompareRentalCount(funno);
        int compareReturnAddCount = carallocationReturnAddListResp.getCompareReturnAddCount(funno);

        if(compareRentalCount == 0 && compareReturnAddCount == 0){
             compareResult = "N";
        }else {
             if(compareRentalCount - compareReturnAddCount == 0) {
                compareResult = "Y";
            }else {
                compareResult = "N";
            }
        }



        return compareResult;
    }


}
