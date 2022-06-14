# SearchPharmacy
# 모델별 사용한 데이터 및 데이터 전처리 기준
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
    <td>동 검색결과로 나온 PL 클릭시 디테일 화면에 PL 정보 제공</td>
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
