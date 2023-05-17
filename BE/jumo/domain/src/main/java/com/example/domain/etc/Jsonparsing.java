package com.example.domain.etc;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RestController
@ApiIgnore
@RequestMapping("/problem")
@RequiredArgsConstructor
public class Jsonparsing {
    @PostMapping("/parse/save")
    public Boolean saveParse(@RequestBody String jsonStr) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonStr);
        JSONArray parkingList = (JSONArray) jsonObject.get("records");

        int wasteDataCnt = 0;
        int noAddr = 0;
        int noPointData = 0;

        for (int i =0 ; i< parkingList.size() ; i++)
        {
            JSONObject lot_body = (JSONObject) parkingList.get(i);

            String lot_part = lot_body.get("주차장구분").toString();  // 필수
            String lot_type = lot_body.get("주차장유형").toString(); // 필수
            String lot_name = lot_body.get("주차장명").toString(); // 필수
            String road_addr = lot_body.get("소재지도로명주소").toString(); // 필수
            String old_addr = lot_body.get("소재지지번주소").toString(); // 필수
            String lot_field = lot_body.get("주차구획수").toString();
            String open_day = lot_body.get("운영요일").toString();
            String week_start = lot_body.get("평일운영시작시각").toString();
            String week_end = lot_body.get("평일운영종료시각").toString();
            String sat_start = lot_body.get("토요일운영시작시각").toString();
            String sat_end = lot_body.get("토요일운영종료시각").toString();
            String holi_start = lot_body.get("공휴일운영시작시각").toString();
            String holi_end = lot_body.get("공휴일운영종료시각").toString();
            String fee_data  =lot_body.get("요금정보").toString();  // 필수
            float fee_basic  =  Float.parseFloat(lot_body.get("주차기본시간").toString().isEmpty()?"0":lot_body.get("주차기본시간").toString());
            float per_basic  =  Float.parseFloat(lot_body.get("주차기본요금").toString().isEmpty()?"0":lot_body.get("주차기본요금").toString());
            float plus_time  =  Float.parseFloat(lot_body.get("추가단위시간").toString().isEmpty()?"0":lot_body.get("추가단위시간").toString());
            float per_plus  =  Float.parseFloat(lot_body.get("추가단위요금").toString().isEmpty()?"0":lot_body.get("추가단위요금").toString());
            float per_day  =  Float.parseFloat(lot_body.get("1일주차권요금").toString().isEmpty()?"0":lot_body.get("1일주차권요금").toString());
            float per_month  =  Float.parseFloat(lot_body.get("월정기권요금").toString().isEmpty()?"0":lot_body.get("월정기권요금").toString());
            float latitude = Float.parseFloat(lot_body.get("위도").toString().isEmpty()?"0":lot_body.get("위도").toString());
            float longitude = Float.parseFloat(lot_body.get("경도").toString().isEmpty()?"0":lot_body.get("경도").toString());
            String pay_type = lot_body.get("결제방법").toString();
            String special_prop = lot_body.get("특기사항").toString();

            if (road_addr.isEmpty() &&  old_addr.isEmpty() && latitude == 0 && longitude == 0)
            {
                wasteDataCnt ++;
            }

            if (road_addr.isEmpty() &&  old_addr.isEmpty())
            {
                noAddr++;
            }

            if(latitude == 0 && longitude == 0)
            {
                Float[] coords = new Float[2];
                if (road_addr.isEmpty()){
                    coords = findGeoPoint("경기도 성남시 분당구 삼평동");
                }
                else{
                    coords = findGeoPoint("경기도 성남시 분당구 삼평동");
                }

                if (coords != null)
                {
                    System.out.println(old_addr);
                    System.out.println(road_addr);
                    latitude = coords[0];
                    longitude = coords[1];
                    System.out.println(latitude);
                    System.out.println(longitude);
                }



            }




//            System.out.println(road_addr+ Integer.toString(fee_basic));

        }

        System.out.println(wasteDataCnt);
        System.out.println(noAddr);
        System.out.println(noPointData);





        return true;
    }

    public static Float[] findGeoPoint(String location) {

        if (location == null)
            return null;

        // setAddress : 변환하려는 주소 (경기도 성남시 분당구 등)
        // setLanguate : 인코딩 설정
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(location).setLanguage("ko").getGeocoderRequest();

        try {
            Geocoder geocoder = new Geocoder();
            GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);

            if (geocoderResponse.getStatus() == GeocoderStatus.OK & !geocoderResponse.getResults().isEmpty()) {
                GeocoderResult geocoderResult=geocoderResponse.getResults().iterator().next();
                LatLng latitudeLongitude = geocoderResult.getGeometry().getLocation();

                Float[] coords = new Float[2];
                coords[0] = latitudeLongitude.getLat().floatValue();
                coords[1] = latitudeLongitude.getLng().floatValue();
                return coords;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
