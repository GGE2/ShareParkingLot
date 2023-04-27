import json
import requests

url = "https://dapi.kakao.com/v2/local/search/address.json"

# queryString = {'query' :'전북 삼성동 100'}

# response = requests.get(url, headers=header, params=queryString)
# tokens = response.json()
header = {'Authorization': 'KakaoAK bcc35d3856e97d2a1816183907fb3927'}

# print(response)
# print(tokens)
new_json_str = []

with open('./전국주차장정보표준데이터.json', 'r', encoding='utf-8') as file:
    json_object = json.load(file)

    addresses = json_object.get('records')

    cnt = 0

    for address in addresses:
        new_dic = {}
        new_dic['주차장명'] = address['주차장명']
        new_dic['주차장구분'] = address['주차장구분']
        new_dic['주차장유형'] = address['주차장유형']
        new_dic['주차구획수'] = address['주차구획수']
        new_dic['소재지도로명주소'] = address['소재지도로명주소']
        new_dic['소재지지번주소'] = address['소재지지번주소']
        new_dic['운영요일'] = address['운영요일']
        new_dic['평일운영시작시각'] = address['평일운영시작시각']
        new_dic['평일운영종료시각'] = address['평일운영종료시각']
        new_dic['토요일운영시작시각'] = address['토요일운영시작시각']
        new_dic['토요일운영종료시각'] = address['토요일운영종료시각']
        new_dic['공휴일운영시작시각'] = address['공휴일운영시작시각']
        new_dic['공휴일운영종료시각'] = address['공휴일운영종료시각']
        new_dic['요금정보'] = address['요금정보']
        new_dic['주차기본시간'] = address['주차기본시간']
        new_dic['주차기본요금'] = address['주차기본요금']
        new_dic['추가단위시간'] = address['추가단위시간']
        new_dic['추가단위요금'] = address['추가단위요금']
        new_dic['1일주차권요금'] = address['1일주차권요금']
        new_dic['월정기권요금'] = address['월정기권요금']
        new_dic['결제방법'] = address['결제방법']
        new_dic['특기사항'] = address['특기사항']
        new_dic['위도'] = address['위도']
        new_dic['경도'] = address['경도']


        if not new_dic['소재지도로명주소']:
            queryString = {'query' : new_dic['소재지지번주소']}
            response = requests.get(url, headers=header, params=queryString)
            tokens = response.json()

            if len(tokens['documents']) == 0:
                pass_flag = True
                high_level_addr_lst = new_dic['소재지지번주소'].split(' ')
                while len(high_level_addr_lst) > 1 :
                    high_level_addr = ' '.join(high_level_addr_lst[:-1])
                    queryString = {'query' : high_level_addr}
                    response = requests.get(url, headers=header, params=queryString)
                    tokens = response.json()

                    if len(tokens['documents']):
                        pass_flag = False
                        break
                    else:
                        high_level_addr_lst = high_level_addr.split(' ')

                # print(new_dic['소재지지번주소'])
                # print(response)
                # print('len 0')
                if pass_flag:
                    print('이런 경우 존재?')
                    continue

            
            if not new_dic['위도'] or not new_dic['경도']:
                if tokens['documents'][0]['address'] == None:
                    new_dic['위도'] = tokens['documents'][0]['road_address']['y'] 
                    new_dic['경도'] = tokens['documents'][0]['road_address']['x']
                else:    
                    new_dic['위도'] = tokens['documents'][0]['address']['y'] 
                    new_dic['경도'] = tokens['documents'][0]['address']['x']

            if tokens['documents'][0]['road_address'] == None:
                print('road is none')
                continue

            new_dic['소재지도로명주소'] = tokens['documents'][0]['road_address']['address_name']

            cnt += 1


        elif not new_dic['소재지지번주소']:
            queryString = {'query' : new_dic['소재지도로명주소']}
            response = requests.get(url, headers=header, params=queryString)
            tokens = response.json()

            if len(tokens['documents']) == 0:
                pass_flag = True
                high_level_addr_lst = new_dic['소재지도로명주소'].split(' ')
                while len(high_level_addr_lst) > 1 :
                    high_level_addr = ' '.join(high_level_addr_lst[:-1])
                    queryString = {'query' : high_level_addr}
                    response = requests.get(url, headers=header, params=queryString)
                    tokens = response.json()

                    if len(tokens['documents']):
                        pass_flag = False
                        break
                    else:
                        high_level_addr_lst = high_level_addr.split(' ')

                if pass_flag:
                    print('이런 경우 존재?')
                    continue
                # print(new_dic['소재지도로명주소'])
                # print(response)
                # print('len 0')

            if not new_dic['위도'] or not new_dic['경도']:
                if tokens['documents'][0]['road_address'] == None:
                    new_dic['위도'] = tokens['documents'][0]['address']['y']
                    new_dic['경도'] = tokens['documents'][0]['address']['x']    
                else:
                    new_dic['위도'] = tokens['documents'][0]['road_address']['y']
                    new_dic['경도'] = tokens['documents'][0]['road_address']['x']


            if tokens['documents'][0]['address'] == None:
                print('addr is none')
                continue

            new_dic['소재지지번주소'] = tokens['documents'][0]['address']['address_name']
            cnt += 1
        
        new_json_str.append(new_dic)

    print(cnt)


with open('./new_parking.json', 'w', encoding='utf-8') as new_file:
    write_str = {'records': new_json_str}
    json.dump(write_str, new_file,indent="")
