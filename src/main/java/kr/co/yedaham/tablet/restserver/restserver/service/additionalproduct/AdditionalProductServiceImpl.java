package kr.co.yedaham.tablet.restserver.restserver.service.additionalproduct;

import kr.co.yedaham.tablet.restserver.restserver.model.product.AdditionalPostRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.product.AdditionalProductList;
import kr.co.yedaham.tablet.restserver.restserver.resp.additionalproduct.AdditionalProductResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdditionalProductServiceImpl implements AdditionalProductService {
    @Autowired
    private AdditionalProductResp productResp;

    @Override
    public ArrayList<AdditionalProductList> getAdditionalProductYedahamOneTwoList(AdditionalPostRequest request) {
        return productResp.findAdditionalProductYedahamOneTwoList(request.getCertno(), request.getGrpcode(), request.getAmt(),request.getPlinm());
    }

    @Override
    public ArrayList<AdditionalProductList> getAdditionalProductYedahamOneTwoAllList(String certno) {
        return productResp.findAdditionalProductYedahamOneTwoAllList(certno);
    }

    @Override
    public ArrayList<AdditionalProductList> getAdditionalDusanProductList(AdditionalPostRequest request) {
        return productResp.findAdditionalDusanProductList(request.getCertno(), request.getGrpcode(), request.getAmt(), request.getCdnm(),request.getPlinm());
    }

    @Override
    public ArrayList<AdditionalProductList> getAdditionalDusanProductAllList(String certno) {
        return productResp.findAdditionalDusanProductAllList(certno);
    }

    @Override
    public ArrayList<AdditionalProductList> getAdditionalNewProductList(AdditionalPostRequest request) {
        return productResp.findAdditionalNewProductList(request.getCertno(), request.getGrpcode(), request.getAmt(), request.getCdnm(),request.getPlicd(),
                                                        request.getPlinm1(),request.getPlinm2(),request.getPlinm3());
    }

    @Override
    public ArrayList<AdditionalProductList> getAdditionalNewProductAllList(String certno) {
        return productResp.findAdditionalNewProductAllList(certno);
    }
    @Override
    public ArrayList<AdditionalProductList> getInitAdditionalNewProductList(String functrlno, String certno) {
        return productResp.findInitAdditionalNewProductList(functrlno, certno);
    }

    @Override
    public ArrayList<AdditionalProductList> getInitAdditionalDusanProductList(String functrlno, String certno) {
        return productResp.findInitAdditionalDusanProductList(functrlno, certno);
    }

    @Override
    public ArrayList<AdditionalProductList> getInitAdditionaOneTwoProductList(String functrlno, String certno) {
        return productResp.findInitAdditionalYedahamOneTwoProductList(functrlno, certno);
    }
}
