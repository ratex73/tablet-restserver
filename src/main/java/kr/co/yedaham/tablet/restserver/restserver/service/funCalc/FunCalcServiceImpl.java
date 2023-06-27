package kr.co.yedaham.tablet.restserver.restserver.service.funCalc;

import kr.co.yedaham.tablet.restserver.restserver.entity.CarallocationInfoEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunCalcEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunItemEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunCalcInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunItemCalcDto;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunItemInfo;
import kr.co.yedaham.tablet.restserver.restserver.resp.funCalc.FunCalcResp;
import kr.co.yedaham.tablet.restserver.restserver.resp.funCalc.FunItemResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FunCalcServiceImpl implements FunCalcService {

    private final FunItemResp funItemResp;
    private final FunCalcResp funCalcResp;

    //의전물품 저장
    public boolean saveFunCalc(List<FunItemInfo> funItemInfoList) {
        boolean saveYn = true;
        List<FunItemEntity> funItemEntityList = new ArrayList<>();

        for(int i=0; i<funItemInfoList.size(); i++) {

            funItemEntityList.add(FunItemEntity.builder().funItemInfo(funItemInfoList.get(i)).build());
        }
        System.out.println("saveFunCalc ====funItemInfo=====================" + funItemEntityList.toString());
        funItemResp.saveAll(funItemEntityList);

        return saveYn;
    }

    //의전물품 및 정산저장
    public boolean saveFunItemCalc(FunItemCalcDto funItemCalcDto) {
        boolean saveYn = true;
        List<FunItemEntity> funItemEntityList = new ArrayList<>();

        List<FunItemInfo> funItemInfoList = funItemCalcDto.getFunItemList();
        FunCalcInfo funCalcInfo = funItemCalcDto.getFunCalcInfo();

        for(int i=0; i<funItemInfoList.size(); i++) {

            funItemEntityList.add(FunItemEntity.builder().funItemInfo(funItemInfoList.get(i)).build());
        }
        System.out.println("saveFunItemCalc.funItemEntityList=====================" + funItemEntityList.toString());
        System.out.println("saveFunItemCalc.funCalcInfo=====================" + funCalcInfo.toString());
        
        //의전물품 데이터 전체 삭제
        funItemResp.deleteAll(funItemEntityList);

        //의전물품 데이터 저장
        funItemResp.saveAll(funItemEntityList);
        
        //의전결산 데이터 삭제
        if(funCalcResp.existsById(funCalcInfo.getFunCalcId())) {
            funCalcResp.deleteById(funCalcInfo.getFunCalcId());
        }

        //의전결산 데이터 저장
        funCalcResp.save(FunCalcEntity.builder().funCalcInfo(funCalcInfo).build());

        return saveYn;
    }
}
