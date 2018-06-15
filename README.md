
Validator-v1.0 (공간자료 검증도구 v1.0)
=======

(내부 : Validator-v1.0)
이 프로젝트는 국토공간정보연구사업 중, [공간정보 SW 활용을 위한 오픈소스 가공기술 개발]과제중 4차년도 연구성과 입니다.
정식 버전은 차후에 통합된 환경에서 제공될 예정입니다.
이 프로그램들은 완성되지 않았으며, 최종 완료 전 까지 문제가 발생할 수도 있습니다.
발생된 문제는 최종 사용자에게 있으며, 완료가 된다면 제시된 라이선스 및 규약을 적용할 예정입니다.

감사합니다.
공간정보기술(주) 연구소 <link>http://www.git.co.kr/
OpenGeoDT 팀

연구기관
---
세부 책임 : 부산대학교 <link>http://www.pusan.ac.kr/

연구 책임 : 국토연구원 <link>http://www.krihs.re.kr/


Getting Started
----
### 1. 개발환경 ###
- Java - OpenGDK 1.8.0.111 64 bit
- Tomcat - Tomcat8.0.43 64bit
- eclipse neon 
- PostgreSQL 9.4 
- Geoserver 2.13.0

### 2. Geoserver 설치 및 설정 ###
- http://geoserver.org/ 접속 후 Geoserver 2.13.0 Windows Installer 다운로드 
** jdk 1.8 버전 이상 사용 시 Geoserver 2.8 버전 이상 사용
- Windows Installer 실행 후  C:\Program Files (x86) 경로에 설치
- C:\Program Files (x86)\GeoServer 2.13.0\bin 경로의 startup.bat 실행
- Geoserver url 접속 ex) http://localhost:9999/geoserver
- 사용자 id를 작업공간 이름으로 입력 후 작업공간 생성

### 3. PostgreSQL 설치 및 설정 ###
- http://www.postgresql.org/download/ 접속 후 PostgreSQL 다운로드 및 설치
- pgAdmin 실행 후 Databases 생성 후 New Database 클릭 
- 사용자 id를 Name으로 입력 후 Database 생성 ex) Name:admin
- Query Tool 실행 후 아래의 query를 차례로 실행
<pre><code> 1) create extension postgis;<br>
 2) create extension postgis_topology;<br> 
 3) create table shp_layercollection (<br>
    c_idx serial primary key,<br>
    c_name character varying(200)<br>
    );<br>
 4) create table shp_layer_metadata (<br>
    lm_idx serial primary key,<br>
    layer_name varchar(200),<br>
    layer_t_name varchar(200),<br>
    current_layer_name character varying(200),<br>
    c_idx int references shp_layercollection(c_idx)<br> 
    );<br>
 5) create table shp_layercollection_qa_progress (<br>
    p_idx serial primary key,<br>
    collection_name character varying(100),<br>
    file_type character varying(50),<br>
    state int,<br>
    request_time timestamp,<br> 
    response_time timestamp,<br> 
    err_layer_name character varying(100),<br>
    c_idx int<br>
    );</code></pre>

### 4. 소스코드 설치 및 프로젝트 실행 ###
- https://github.com/ODTBuilder/Validator-v1.0 접속 후 소스코드 다운로드
- eclipse 실행 후 zip 파일 형태로 Project Import
- eclipse와 톰캣 연동 후 해당 프로젝트 서버 생성
- server.xml 파일의 Context path를 "/opengds"로 변경
- src\main\resources\geoserver.properties 파일에 Geoserver 정보 입력
<pre><code> ex) url=http://localhost:9999/geoserver<br>
                id=admin<br>
                pw=geoserver<br> </code></pre>


사용 라이브러리
=====

1. jQuery 2.2.2 (MIT License, CC0) http://jquery.com/
2. jQuery UI 1.11.4 (MIT License & GPL License, this case MIT License), start theme. http://jqueryui.com/
3. GeoTools 13.1 (LGPL) http://www.geotools.org/
4. ApachePOI 3.14 (Apache License 2.0) http://poi.apache.org
5. ApacheCommons 1.3.3 (Apache License 2.0) commons.apache.org/proper/commons-logging/
6. JACKSON 1.9.7 (Apache License (AL) 2.0, LGPL 2.1)
7. JSON 20160212 (MIT License)
8. Openlayers3 3.13.0 (FreeBSD) www.openlayers.org
9. Spectrum 1.8.0 (MIT) http://numeraljs.com/
10. Bootstrap v3.3.2 (MIT) http://getbootstrap.com
11. JSTS (EPL) http://bjornharrtell.github.io/jsts/

Mail
====
Developer : SG.LEE
ghre55@git.co.kr
