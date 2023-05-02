import json

write_string = {}

with open('./new_parking.json', 'r') as new_file:
    write_string = json.load(new_file)

with open('./nnew_parking.json', 'w', encoding='utf-8') as nnew_file:
    json.dump(write_string, nnew_file, indent='\t', ensure_ascii=False)