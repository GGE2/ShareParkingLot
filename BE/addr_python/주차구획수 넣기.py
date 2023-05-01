import json

new_json_str = []

with open('./field_new_parking.json', 'r', encoding='utf-8') as file:
    with open('./new_parking.json', 'r', encoding='utf-8') as field_file:
        json_object = json.load(file)
        field_object = json.load(field_file)

        field_include_address = field_object.get('records')
        addresses = json_object.get('records')

        cnt = 0

        for address in addresses:

            new_dic = {}
            
            for i in range(cnt, len(field_include_address)):
                if address['주차장명'] == field_include_address[i]['주차장명']:
                    cnt = i
                    new_dic['주차구획수'] = field_include_address[i]['주차구획수']
                    break

            new_dic['주차장명'] = address['주차장명']
            new_dic['주차장구분'] = address['주차장구분']
            new_dic['주차장유형'] = address['주차장유형']
            # new_dic['주차구획수'] = address['주차구획수']
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
            
            new_json_str.append(new_dic)

with open('./final_new_parking.json', 'w', encoding='utf-8') as new_file:
    write_str = {'records': new_json_str}
    json.dump(write_str, new_file,indent='\t', ensure_ascii=False)