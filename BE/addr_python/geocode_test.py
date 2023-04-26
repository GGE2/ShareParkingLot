from geopy.geocoders import Nominatim

def geocoding(address):
    geolocoder = Nominatim(user_agent = 'South Korea', timeout=None)
    geo = geolocoder.geocode(address)

    crd = {"lat": 0, "lng": 0}

    if geo:
        crd = {"lat": str(geo.latitude), "lng": str(geo.longitude)}
    else:
        addr_list = address.split(" ")
        if len(addr_list) < 2:
            return {"lat": 0, "lng": 0}
        
        print(addr_list)
        addr_list = addr_list[:-1]
        print(addr_list)
        new_addr = ""
        for add_comp in addr_list:
            new_addr += add_comp
            new_addr += " "
        
        new_addr = new_addr.strip()
        print(new_addr)
        return geocoding(new_addr)

    return crd

from geopy.geocoders import Nominatim

def geocoding_reverse(lat_lng_str): 
    geolocoder = Nominatim(user_agent = 'South Korea', timeout=None)
    address = geolocoder.reverse(lat_lng_str)

    return address

import json

with open('전국주차장정보표준데이터.json', 'r', encoding='UTF-8') as json_file:
    parking_json = json.load(json_file)
    parking_list = parking_json["records"]

    # print(parking_list)

    for parking in parking_list:
        # print(parking)
        lat = parking["위도"]
        lng = parking["경도"]
        r_add = parking["소재지도로명주소"]
        o_add = parking["소재지지번주소"]

        if not lat or not lng:
            if o_add:
                crd = geocoding(o_add)
            else:
                crd = geocoding(r_add)
        
        if crd["lat"] == 0:
            print("wasted")
        else:
            print(crd)



import requests

URL ="https://dapi.kakao.com/v2/local/search/address.json"
header =  "Authorization: KakaoAK bcc35d3856e97d2a1816183907fb3927"
query = "query=전북 삼성동 100"

response = requests.get(URL, headers=header, data =query)
print(response)