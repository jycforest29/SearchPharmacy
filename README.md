# SearchPharmacy
# 모델별 사용한 데이터 및 데이터 전처리 기준
## 전체적인 설명
이 프로젝트의 핵심 기능은 동 검색시 해당 동에 속하는 여러개의 도로명 주소가 나오는 것.<br>
따라서 모든 모델들이 같은 범위의 동, 도로명 value들을 가져야 하고, 동-도로명 사이의 관계가 고정되어 있어야 함.(이하 '기준')<br>
이 프로젝트에선 우체국의 우편번호 DB(칼럼으로 우편번호, 도로명주소, 법정동명 등 포함하기에 위의 조건 만족 가능)를 기준으로 모든 테이블을 생성함(이하 '기준DB')<br>
## 모델별 설명
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
    <td>String name(pk)<br>String color</td>
    <td>노션의 우이경전철 -> 우이신설경전철, 김포골드 -> 김포도시철도</td>
    <td>1-9호선, 경춘선, 공항철도, 김포도시철도, 수인분당선, 신분당선, 우이신설경전철만 사용</td>
    <td>서울교통공사 노선별 지하철역 정보(서울 열린데이터 광장)<hr>서울교통공사 지하철역 주소 및 전화번호 정보(서울 열린데이터 광장)<hr>기준DB</td>
  </tr>
  <tr>
    <td>Station</td>
    <td>int index(pk)<br>String name<br>String line<br>String dong</td>
    <td>서울 전체의 지하철역 590개 중 339개만 역과 동 연결. 나머지는 안드로이드 내에서 api로 처리</td>
    <td>동일한 역 이름에 대해 각각 다른 호선과 동 명을 갖는 경우 모두 다른 객체로 취급</td>
    <td>동일</td>
  </tr>
</table>
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
    <td>종별코드명 칼럼의 값이 의원, 치과의원, 병원인 경우만 추출<hr>도로명 기준으로 검색하기에 도로명 제외 모든 약국 정보가 같아도 도로명이 다르면 다른 객체로 생성</td>
    <td>전국 병의원 및 약국 현황(보건의료빅데이터개방시스템)<hr>기준DB</td>
  </tr>
  <tr>
    <td>Pharmacy</td>
    <td>int index(pk)<br>String name<br>String address<br>String loadaddress<br>String startdate</td>
    <td></td>
    <td>도로명 기준으로 검색하기에 도로명 제외 모든 약국 정보가 같아도 도로명이 다르면 다른 객체로 생성</td>
    <td>동일</td>
  </tr>
  <tr>
    <td>Convenience</td>
    <td>int index(pk)<br>String name<br>String address<br>String loadaddress<br>String startdate</td>
    <td></td>
    <td>영업상태 칼럼의 값이 영업/정상, 휴업인 값들 추출<hr>도로명 기준으로 검색하기에 도로명 제외 모든 약국 정보가 같아도 도로명이 다르면 다른 객체로 생성</td>
    <td>서울시 안전상비의약품 판매업소 인허가 정보(서울 열린데이터 광장)<hr>기준DB</td>
  </tr>
  <tr>
    <td>PharmacyLocation</td>
    <td>int index(pk)<br>String dong<br>String loadaddress<br>int hospitalcount<br>int pharmacycount<br>int conveniencecount<br>int doctorcount<br>float hospitalperpharmacy<br>float doctorperpharmacy<br>float convenienceperpharmacy<br>int viewcount</td>
    <td></td>
    <td>a 대비 b의 비율에서 a == 0 and b != 0인 경우 0, a != 0 and b == 0인 경우 -1로 replace</td>
    <td>전국 병의원 및 약국 현황(보건의료빅데이터개방시스템)<hr>서울시 안전상비의약품 판매업소 인허가 정보(서울 열린데이터 광장)<hr>기준DB</td>
  </tr>
</table><br>

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
