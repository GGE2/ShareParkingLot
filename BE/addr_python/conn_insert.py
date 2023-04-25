import pymysql
import json


def insertsql_from_json():
    conn = pymysql.connect(
        host = "localhost", 	 
        port=3306,
        user = "root", 		 
        password = "sangjun1324",
        database = "jumo",
        charset = 'utf8'
    )

    curs = conn.cursor()
               
    with open('./nnew_parking.json', encoding='utf-8') as json_file:
        json_data = json.load(json_file)
        json_line = json_data['records']
        
        for address in json_line:
            lot_name = address['주차장명']
            lot_part = address['주차장구분']
            lot_type = address['주차장유형']
            road_addr = address['소재지도로명주소']
            old_addr = address['소재지지번주소']
            open_day = address['운영요일']
            week_start = address['평일운영시작시각']
            week_end = address['평일운영종료시각']
            sat_start = address['토요일운영시작시각']
            sat_end = address['토요일운영종료시각']
            holi_start = address['공휴일운영시작시각']
            holi_end = address['공휴일운영종료시각']
            fee_data = address['요금정보']
            fee_basic = address['주차기본시간']
            per_basic = address['주차기본요금']
            plus_time = address['추가단위시간']
            per_plus = address['추가단위요금']
            per_day = address['1일주차권요금']
            per_month = address['월정기권요금']
            pay_type = address['결제방법']
            special_prop = address['특기사항']
            latitude = address['위도']
            longitude = address['경도']

            if not latitude:
                latitude = '0.0'
            if not longitude:
                longitude = '0.0'

            if not week_start:
                week_start ='0'
            if not week_end:
                week_end ='0'
            if not sat_start:
                sat_start ='0'
            if not sat_end:
                sat_end ='0'
            if not holi_start:
                holi_start ='0'
            if not holi_end:
                holi_end ='0'
            if not fee_basic:
                fee_basic ='0'
            if not per_basic:
                per_basic ='0'
            if not plus_time:
                plus_time ='0'
            if not per_plus:
                per_plus ='0'
            if not per_day:
                per_day ='0'
            if not per_month:
                per_month ='0'

            
            

            sql = "INSERT INTO parking_lot(fee_basic, fee_data, holi_end, holi_start, latitude, longitude, lot_name, lot_part, lot_type, old_addr, open_day, pay_type, per_basic, per_day, per_month, per_plus, plus_time, road_addr, sat_end, sat_start, special_prop, week_end, week_start) VALUES (%s, %s,%s, %s,%s, %s,%s, %s,%s, %s,%s, %s,%s, %s,%s, %s,%s, %s,%s, %s,%s, %s,%s)"
            val = (fee_basic, fee_data, holi_end, holi_start, latitude, longitude, lot_name, lot_part, lot_type, old_addr, open_day, pay_type, per_basic, per_day, per_month, per_plus, plus_time, road_addr, sat_end, sat_start, special_prop, week_end, week_start)

            curs.execute(sql, val)
            conn.commit()

    print(curs.rowcount, "record inserted")

insertsql_from_json()