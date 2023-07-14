package kr.co.yedaham.tablet.restserver.restserver.service.additionalproduct;

import kr.co.yedaham.tablet.restserver.restserver.model.product.AdditionalPostRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.product.AdditionalProductList;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ProductList;

import java.util.ArrayList;

public interface AdditionalProductService {
    public ArrayList<AdditionalProductList> getAdditionalProductYedahamOneTwoList(AdditionalPostRequest request);
    public ArrayList<AdditionalProductList> getAdditionalProductYedahamOneTwoAllList(String certno);
    public ArrayList<AdditionalProductList> getAdditionalDusanProductList(AdditionalPostRequest request);
    public ArrayList<AdditionalProductList> getAdditionalDusanProductAllList(String certno);

    public ArrayList<AdditionalProductList> getAdditionalNewProductList(AdditionalPostRequest request);

    public ArrayList<AdditionalProductList> getAdditionalNewProductAllList(String certno);
    public ArrayList<AdditionalProductList> getInitAdditionalNewProductList(String functrlno, String certno);

    public ArrayList<AdditionalProductList> getInitAdditionalDusanProductList(String functrlno, String certno);
}
