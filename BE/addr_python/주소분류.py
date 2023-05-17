import json

with open('./final_new_parking.json', 'r', encoding='utf-8') as file:
    json_object = json.load(file)
    addresses = json_object['records']

    dong_address = {}

    for address in addresses:
        split_addr = address['소재지지번주소'].split(' ')

        if len(split_addr)> 2:
            if dong_address.get(split_addr[-3]):
                dong_address[split_addr[-3]] += 1
            else:
                dong_address[split_addr[-3]] = 1
        elif len(split_addr) == 2:
            if dong_address.get(split_addr[-1]):
                dong_address[split_addr[-1]] += 1
            else:
                dong_address[split_addr[-1]] = 1
        elif len(split_addr) == 1:
            if dong_address.get(split_addr[-1]):
                dong_address[split_addr[-1]] += 1
            else:
                dong_address[split_addr[-1]] = 1
    
    for key, item in dong_address.items():
        if key:
            if key[-1] != '동' and key[-1] != '가' and key[-1] != '리' :
                print(f'{key} {item}')