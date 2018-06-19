

Validator-v1.0 (공간자료 검증도구 v1.0) 
=======
이 프로젝트는 국토공간정보연구사업 중 [공간정보 SW 활용을 위한 오픈소스 가공기술 개발]과제의 4차년도 연구성과 입니다.<br>
정식 버전은 차후에 통합된 환경에서 제공될 예정입니다.<br>
이 프로그램들은 완성되지 않았으며, 최종 완료 전 까지 문제가 발생할 수도 있습니다.<br>
발생된 문제는 최종 사용자에게 있으며, 완료가 된다면 제시된 라이선스 및 규약을 적용할 예정입니다.<br>

감사합니다.<br>
공간정보기술(주) 연구소 <link>http://www.git.co.kr/<br>
OpenGeoDT 팀

------

Validator-v1.0 (Validation tool for geospatial data v1.0)

This project is still a work in progress at its 4th year. 
The final version of the project will be integrated in the later years.
The program may cause some problems since it is not complete.
The user is responsible to the caused problem and once it is complete, a new set of legal codes will be applied.

Thank you.
Geospatial Information Technology R&D <link>http://www.git.co.kr/<br>
Team OpenGeoDT


특징
=====
- 공간자료 검증도구 v1.0은 자사 GIS 통합 솔루션인 GeoDT 2.2 기반의 웹 기반 공간데이터 편집/검수 솔루션임.
- 웹 페이지상에서 공간정보의 기하학적/논리적 구조와 속성값에 대한 검수편집 기능을 제공함.
- 다양한 웹 브라우저 지원가능, 플러그인 및 ActiveX 설치 없이 사용 가능함.
- JavaScript, Java 라이브러리 형태로 개발되어 사용자 요구사항에 따라 커스터 마이징 및 확장이 가능함.
- OGC 표준준수, 국내 수치지형도 작성 작업규정을 따르는 20여종의 검수기능을 제공함. 


-------

Characteristics

-Validation tool for geospatial data v1.0 is a GIS integrated solution for inspecting and editing geospatial data based on GeoDT 2.2
-Provides tools for inspecting the geometric/logical structure of geospatial data.
-Supports various types of web browsers without installations of any plugins or ActiveX. 
-Can be customized or extended depending on the needs of the user since the program is written in JavaScript and Java.
-Follows the standards of OGC and provides more than 20 validating functions that follow the Korean law for making maps.

연구기관
=====
- 세부 책임 : 부산대학교 <link>http://www.pusan.ac.kr/<br>
- 연구 책임 : 국토연구원 <link>http://www.krihs.re.kr/

-----

Research Institutes

-Pusan National University <link>http://www.pusan.ac.kr/<br>
-Korea Research Institute for Human Settlements <link>http://www.krihs.re.kr/

Getting Started
=====
### 1. 환경 ###
- Java - OpenGDK 1.8.0.111 64 bit
- Tomcat - Tomcat8.0.43 64bit
- eclipse neon 
- PostgreSQL 9.4 
- Geoserver 2.13.0

### 2. Geoserver 설치 및 설정 ###
- http://geoserver.org/ 접속 후 Geoserver 2.13.0 Windows Installer 다운로드 <br> 
** jdk 1.8 버전 이상 사용 시 Geoserver 2.8 버전 이상 사용
- Windows Installer 실행 후  C:\Program Files (x86) 경로에 설치
- C:\Program Files (x86)\GeoServer 2.13.0\bin 경로의 startup.bat 실행
- Geoserver url 접속
<pre><code> http://[host]:[port]/geoserver </code></pre> 
- 사용자 계정(테스트 계정 : admin)을 작업공간 이름으로 입력 후 작업공간 생성

### 3. PostgreSQL 설치 및 설정 ###
- http://www.postgresql.org/download/ 접속 후 PostgreSQL 다운로드 및 설치
- pgAdmin 실행 후 Databases 생성 후 New Database 클릭 
- 사용자 계정(테스트 계정 : admin)을 Name으로 입력 후 Database 생성 
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
<pre><code> url=http://[host]:[port]/geoserver<br>
 id=[admin]<br>
 pw=[geoserver]<br> </code></pre>
- src\main\webapp\WEB-INF\spring\root-context.xml 파일에 생성한 Database 정보 입력
<pre><code> property name="url" value="jdbc:postgresql://[host]:[port]/admin" <br>
 property name="username" value="[username]" <br>
 property name="password" value="[password]" <br></code></pre>
- 서버 실행 후 메인 페이지 url 접속 
 <pre><code> http://[host]:[port]/opengds/builder.do </code></pre>
- 테스트 계정으로 로그인
 <pre><code> id : admin<br>
 pw : 1234<br> </code></pre>
- 편집도구 초기화면 접속 

### 5. 검수 Test 파일 및 설정 파일 다운로드 ###
- test_data.zip 다운로드 후 압축해체
- 검수 Test 파일 (37712012.zip, 37712013.zip) 확인
<pre><code> ** 검수 Test zip 파일 구조 **
   압축파일.zip
     ㄴ폴더 (1/5000 수치지형도 인덱스명)
          ㄴ test1.shp
          ㄴ test2.shp
          ㄴ test3.shp
          ㄴ area.shp (LineString 타입의 검수 영역 레이어)
   * 모든 폴더 및 파일에 특수문자 입력 불가</code></pre>
- 레이어 설정 파일 (layer_setting.json) 확인<br>
<pre><code> ** 레이어 설정 파일 json 구조 **
    { 
      레이어대분류명 : {
        "code" : [
          레이어명
         ],
         "geom" : geometry 타입,
         "area" : true/false
      }
    }
</code></pre>
- 검수 설정 파일 (validation_setting.json) 확인<br>
<pre><code> ** 검수 설정 파일 json 구조 **
    { 
      레이어대분류명 : {
        검수항목1 : true/false,
        검수항목2 : {
          "figure" : 수치값
        }
    }
</code></pre>
** 레이어 설정 파일 및 검수 설정 파일은 편집화면에 업로드 후 편집 가능

### 6. 검수 실행 및 오류 네비게이터 실행 ###
- 메인 페이지 url 접속 
<pre><code> http://[host]:[port]/opengds/builder.do </code></pre>
- 검수 Test 파일 업로드 
<pre><code> 화면 상단 메뉴 New -> File -> SHP -> CRS (ex. EPSG:4326 -> 4326으로 입력) 입력 및 Search -> Test 파일 (37712012.zip, 37712013.zip) add -> Start upload </code></pre>
- 레이어 설정 파일 업로드 
<pre><code> 화면 상단 메뉴 QA 2.0 -> Layer Definition -> layer_setting.json upload -> Save </code></pre>
- 검수 설정 파일 업로드 
<pre><code> 화면 상단 메뉴 QA 2.0 -> Layer Definition -> layer_setting.json upload -> Save </code></pre>
- 검수 수행
<pre><code> 화면 상단 메뉴 QA 2.0 -> Validation -> 좌측 레이어 트리에서 37712012, 37712013 그룹 레이어 클릭 -> Start </code></pre>
- 검수 결과 확인
<pre><code> 화면 좌측 Geoserver Layers 트리 새로고침 후 ValidatorLayers Layers 하단에 37712012_1, 37712013_1 각각의 오류 레이어 생성 확인 </code></pre>
- 오류 네비게이터 실행
<pre><code> 화면 상단 메뉴 QA Edit -> 하단 Geoserver Layers 트리에서 검수 대상 레이어 우클릭 후 Center 클릭 -> 하단 ValidatorLayers 트리에서 검수 대상 레이어의 오류 레이어 우클릭 후 Error 클릭 -> OK </code></pre>
- 화면 우측에 오류 네비게이터의 화살표 버튼을 클릭하며 오류 사항 확인


사용 라이브러리
=====
1. jQuery 2.2.2 (MIT License, CC0) http://jquery.com/
2. jQuery UI 1.11.4 (MIT License & GPL License, this case MIT License), start theme. http://jqueryui.com/
3. GeoTools 16.5 (LGPL) http://www.geotools.org/
4. ApachePOI 3.14 (Apache License 2.0) http://poi.apache.org
5. ApacheCommons 1.3.3 (Apache License 2.0) commons.apache.org/proper/commons-logging/
6. JACKSON 1.9.7 (Apache License (AL) 2.0, LGPL 2.1)
7. JSON 20160212 (MIT License)
8. Openlayers3 3.13.0 (FreeBSD) www.openlayers.org
9. Spectrum 1.8.0 (MIT) http://numeraljs.com/
10. Bootstrap v3.3.2 (MIT) http://getbootstrap.com
11. JSTS (EPL) http://bjornharrtell.github.io/jsts/


Mail
=====
Developer : SG.LEE
ghre55@git.co.kr



------------



Getting Started
=====
### 1. Background ###
- Java - OpenGDK 1.8.0.111 64 bit
- Tomcat - Tomcat8.0.43 64bit
- eclipse neon 
- PostgreSQL 9.4 
- Geoserver 2.13.0

### 2. Geoserver installation and settings ###
- Visit http://geoserver.org/ and download Geoserver 2.13.0 Windows Installer <br> 
** if jdk version 1.8 or higher is used, use Geoserver version 2.8 or higher
- Run Windows Installer and download to C:\Program Files (x86)
- Run startup.bat located at C:\Program Files (x86)\GeoServer 2.13.0\bin
- Connect to Geoserver url
<pre><code> http://[host]:[port]/geoserver </code></pre> 
- Enter the workspace name with the user account(test account: admin) and create workspace

### 3. PostgreSQL installation and settings ###
- Visit http://www.postgresql.org/download/ and download PostgreSQL
- Run pgAdmin and create Databases then click New Database 
- Enter the workspace name with the user account(test account: admin) and create workspace
- Run Query Tool and run query as listed below
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

### 4. Source Code installation and Project Execution ###
- Visit https://github.com/ODTBuilder/Validator-v1.0 and download source code
- Run eclipse and import Project as a zip file
- Create project server after syncing eclipse and tomcat
- change Context path of server.xml to "/opengds"
- Enter Geoserver information to src\main\resources\geoserver.properties
<pre><code> url=http://[host]:[port]/geoserver<br>
 id=[admin]<br>
 pw=[geoserver]<br> </code></pre>
- Enter Database information in the src\main\webapp\WEB-INF\spring\root-context.xml file that has been created
<pre><code> property name="url" value="jdbc:postgresql://[host]:[port]/admin" <br>
 property name="username" value="[username]" <br>
 property name="password" value="[password]" <br></code></pre>
- Start server and connect to main page url 
 <pre><code> http://[host]:[port]/opengds/builder.do </code></pre>
- Log into test account
 <pre><code> id : admin<br>
 pw : 1234<br> </code></pre>
- Enter the initial screen for editing tools 

### 5. Download inspection test file and settings file ###
- Download and unzip test_data.zip
- Check Inspection test files (37712012.zip, 37712013.zip)
<pre><code> ** Inspection Test zip file structure **
   CompressedFile.zip
     ㄴFolder (1/5000 map index name)
          ㄴ test1.shp
          ㄴ test2.shp
          ㄴ test3.shp
          ㄴ area.shp (LineString layr to be inspected)
   * Impossible to use special characters as part of file or folder names </code></pre>
- Check Layer setting file (layer_setting.json) <br>
<pre><code> ** Layer setting file json structure **
    { 
      Layer Category Name: {
        "code" : [
          Layer Name
         ],
         "geom" : geometry Type,
         "area" : true/false
      }
    }
</code></pre>
- Check Inspection setting file (validation_setting.json) <br>
<pre><code> ** Inspection setting file json Structure **
    { 
      Layer Category Name: {
        Inspection Category 1 : true/false,
        Inspection Category 2 : {
          "figure" : Value
        }
    }
</code></pre>
** Layer setting file and inspection setting file can only be edited after uploading them to the editing screen.

### 6. Troubleshooting ###
- Visit main page url 
<pre><code> http://[host]:[port]/opengds/builder.do </code></pre>
- Upload inspection Test file 
<pre><code> Click on top of screen where it says New -> File -> SHP -> CRS (ex. Change EPSG:4326 -> 4326) Enter then Search -> Test file (37712012.zip, 37712013.zip) add -> Start upload </code></pre>
- Upload Layer Setting file  
<pre><code> Click on top of screen where it says QA 2.0 -> Layer Definition -> layer_setting.json upload -> Save </code></pre>
- Upload Inspection setting file 
<pre><code> Click on top of screen where it says QA 2.0 -> Layer Definition -> layer_setting.json upload -> Save </code></pre>
- Run Inspection
<pre><code> Click on top of screen where it says QA 2.0 -> Validation -> Click from the left layer tree the group layers- 37712012, 37712013 -> Start </code></pre>
- Check Inspection results
<pre><code> Click on left of screenwhere it says Geoserver Layers Tree,then refresh. Verify that error layers are created at the bottom named  37712012_1, 37712013_1  </code></pre>
- Run error navigator
<pre><code> Click on top of screen where it says QA Edit -> then right click at bottom of the screen where it says "Target to be inspected" under  Geoserver Layers Tree Center, then click at the center -> Right click error layer at the ValidatorLayers at the bottom then click on "Error" -> OK </code></pre>
- Check the errors after clicking the error navigator arror at the right of the screen


Used Libraries
=====
1. jQuery 2.2.2 (MIT License, CC0) http://jquery.com/
2. jQuery UI 1.11.4 (MIT License & GPL License, this case MIT License), start theme. http://jqueryui.com/
3. GeoTools 16.5 (LGPL) http://www.geotools.org/
4. ApachePOI 3.14 (Apache License 2.0) http://poi.apache.org
5. ApacheCommons 1.3.3 (Apache License 2.0) commons.apache.org/proper/commons-logging/
6. JACKSON 1.9.7 (Apache License (AL) 2.0, LGPL 2.1)
7. JSON 20160212 (MIT License)
8. Openlayers3 3.13.0 (FreeBSD) www.openlayers.org
9. Spectrum 1.8.0 (MIT) http://numeraljs.com/
10. Bootstrap v3.3.2 (MIT) http://getbootstrap.com
11. JSTS (EPL) http://bjornharrtell.github.io/jsts/


Mail
=====
Developer : SG.LEE
ghre55@git.co.kr
