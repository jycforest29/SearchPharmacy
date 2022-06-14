# SearchPharmacy
# 모델별 사용한 데이터 및 데이터 전처리 기준
우편번호 DB를 모든 테이블에 공통적으로 사용함으로써 동일한 동, 도로명 주소 범위를 가짐<br>
<table>
  <tr>
    <th>모델명</th>
    <th>필드</th>
    <th>임의처리</th>
    <th>특이사항</th>
    <th>사용 DB 및 출처</th>
  </tr>
  <tr>
    <td>Line</td>
    <td>String name(pk)</td>
    <td>없음</td>
    <td>1-9호선, 경춘선, 공항철도, 김포도시철도, 수인분당선, 신분당선, 우이신설경전철만 사용</td>
    <td>서울교통공사 노선별 지하철역 정보(서울 열린데이터 광장)<br> 우편번호 DB(우체국)</td>
  </tr>
  <tr>
    <td>Station</td>
    <td>int index(pk)<br>String name<br>String line<br>String dong</td>
    <td>기준 데이터(서울 열린데이터-)와 1개의 DB만 합침</td>
    <td>동일한 역 이름에 대해 각각 다른 호선과 동 명을 갖는 경우 존재. 이 경우 모두 다른 객체로 취급</td>
    <td>동일</td>
  </tr>
</table>
우편번호 기준으로 병합하여 주소-도로명 연결
<table>
  <tr>
    <th>모델명</th>
    <th>필드</th>
    <th>임의처리</th>
    <th>특이사항</th>
    <th>사용 DB 및 출처</th>
  </tr>
  <tr>
    <td>Hospital</td>
    <td>int index(pk)<br>String name<br>String type<br>String address<br>String loadaddress<br>String startdate<br>int totalDoctor</td>
    <td></td>
    <td>종별코드명 칼럼의 값이 의원, 치과의원, 병원인 경우만 추출<br>도로명 주소가 없는 객체도 존재<br>loadAddress제외 모든 병원 정보가 같아도 loadAddress가 다르면 다른 객체로 생성</td>
    <td>전국 병의원 및 약국 현황(보건의료빅데이터개방시스템)<br> 우편번호 DB(우체국)<br>서울시 안전상비의약품 판매업소 인허가 정보(서울 열린데이터 광장)</td>
  </tr>
  <tr>
    <td>Pharmacy</td>
    <td>int index(pk)<br>String name<br>String address<br>String loadaddress<br>String startdate</td>
    <td></td>
    <td>도로명 주소가 없는 객체도 존재<br>loadAddress제외 모든 약국 정보가 같아도 loadAddress가 다르면 다른 객체로 생성</td>
    <td>동일</td>
  </tr>
  <tr>
    <td>Convenience</td>
    <td>int index(pk)<br>String name<br>String address<br>String loadaddress<br>String startdate</td>
    <td></td>
    <td>영업상태 칼럼의 값이 영업/정상, 휴업인 값들 추출<br>도로명주소나 도로명우편번호 칼럼의 값이 nan인 경우는 열 제거</td>
    <td>동일</td>
  </tr>
  <tr>
    <td>PharmacyLocation</td>
    <td>int index(pk)<br>String dong<br>String loadaddress<br>int hospitalcount<br>int pharmacycount<br>int conveniencecount<br>int doctorcount<br>float hospitalperpharmacy<br>float doctorperpharmacy<br>float convenienceperpharmacy<br>int viewcount</td>
    <td>기준 데이터(서울 열린데이터-)와 1개의 DB만 합침</td>
    <td>동일한 역 이름에 대해 각각 다른 호선과 동 명을 갖는 경우 존재. 이 경우 모두 다른 객체로 취급</td>
    <td>동일</td>
  </tr>
</table><br>
![image](https://user-images.githubusercontent.com/103106183/173526482-8ecc0622-fa11-4997-9b4d-e84dee758d51.png)<br>
![image](https://user-images.githubusercontent.com/103106183/173537426-fe62cb37-2eba-4aa9-b6eb-0c1d912b2a0c.png)


# api 명세서
<table>
  <tr>
    <th>uri</th>
    <th>method</th>
    <th>description</th>
    <th>사용 모델</th>
    <th>제공하는 정보</th>
  </tr>
  <tr>
    <td>getLines/</td>
    <td>get</td>
    <td>모든 호선의 정보 제공</td>
    <td>Line</td>
    <td>name</td>
  </tr>
  <tr>
    <td>getStationsByLine/{str:line}/</td>
    <td>get</td>
    <td>호선 선택시 해당 호선에 속하는 역들을 name을 기준으로 중복 제거해서 정보 제공</td>
    <td>Station</td>
    <td>name</td>
  </tr>
  <tr>
    <td>getDongBySearch/{str:name}/</td>
    <td>get</td>
    <td>역 검색시 해당 역이 속하는 동들을 dong을 기준으로 중복 제거해서 정보 제공</td>
    <td>Station</td>
    <td>dong</td>
  </tr>
  <tr>
    <td>getDongBySelect/{str:name}/</td>
    <td>get</td>
    <td>역 선택시 해당 역이 속하는 동들을 dong을 기준으로 중복 제거해서 정보 제공</td>
    <td>Station</td>
    <td>dong</td>
  </tr>
  <tr>
    <td>search/{str:dong}</td>
    <td>get</td>
    <td>동 검색시 해당 동에 속하는 PL 정보 제공</td>
    <td>PharmacyLocation</td>
    <td>all fields</td>
  </tr>
  <tr>
    <td>detail/{int:index}/</td>
    <td>get</td>
    <td>동 검색결과로 나온 PL 클릭시 해당 PL의 pk에 따라 디테일 화면에 PL 정보 제공</td>
    <td>PharmacyLocation</ttdh>
    <td>all fields</td>
  </tr>
  <tr>
    <td>detail/{int:index}/hospital/</td>
    <td>get</td>
    <td>PL의 디테일 화면에서 병원 탭 클릭시 PL의 도로명주소에 속하는 모든 병원 정보 제공 </td>
    <td>Hospital</td>
    <td>loadaddress<br>name<br>type<br>address<br>startdate<br>totaldoctor</td>
  </tr>
  <tr>
    <td>detail/{int:index}/pharmacy/</td>
    <td>get</td>
    <td>PL의 디테일 화면에서 약국 탭 클릭시 PL의 도로명주소에 속하는 모든 약국 정보 제공 </td>
    <td>Pharmacy</td>
    <td>loadaddress<br>name<br>address<br>startdate</td>
  </tr>
   <tr>
    <td>detail/{int:index}/convenience/</td>
    <td>get</td>
    <td>PL의 디테일 화면에서 편의점 탭 클릭시 PL의 도로명주소에 속하는 모든 편의점 정보 제공 </td>
    <td>Convenience</td>
    <td>loadaddress<br>name<br>address<br>startdate</td>
  </tr>
  <tr>
    <td>getTop5/</td>
    <td>get</td>
    <td>PL의 viewcount값이 5개 이상인 객체 중 높은순으로 5개 뽑아 정보 </td>
    <td>PharmacyLocation</td>
    <td>all fields</td>
  </tr>
</table>
