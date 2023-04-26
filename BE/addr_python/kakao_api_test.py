import requests

URL ="https://dapi.kakao.com/v2/local/search/address.json"
header =  {"Authorization": "KakaoAK bcc35d3856e97d2a1816183907fb3927"}
query = "query=전북 삼성동 100"

response = requests.get(url=URL, headers=header, params=query)
print(response)