/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.opengds.geoserver.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.geolayer.data.DTGeoGroupLayer;
import com.git.gdsbuilder.geolayer.data.DTGeoGroupLayerList;
import com.git.gdsbuilder.geolayer.data.DTGeoLayerList;
import com.git.gdsbuilder.geoserver.data.GeoserverLayerCollectionTree;
import com.git.gdsbuilder.geoserver.factory.DTGeoserverPublisher;
import com.git.gdsbuilder.geoserver.factory.DTGeoserverReader;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTFeatureType;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTLayer;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTLayerList;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTPublishedList;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSLayerGroupEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.utils.NestedElementEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.utils.XmlElement;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfoList;
import com.git.opengds.geoserver.data.style.GeoserverSldTextType;
import com.git.opengds.upload.domain.FileMeta;
import com.git.opengds.user.domain.UserVO;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

/**
 * Geoserver와 관련된 요청을 처리하는 클래스
 * 
 * @author SG.Lee
 * @Date 2017. 5. 12. 오전 2:22:14
 */
@Service
public class GeoserverServiceImpl implements GeoserverService {
	private static final String URL;
	private static final String ID;
	private static final String PW;
	private static DTGeoserverReader dtReader;
	private static DTGeoserverPublisher dtPublisher;

	// private final String workspace;

	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classLoader.getResourceAsStream("geoserver.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		URL = properties.getProperty("url");
		ID = properties.getProperty("id");
		PW = properties.getProperty("pw");
		try {
			dtReader = new DTGeoserverReader(URL, ID, PW);
			dtPublisher = new DTGeoserverPublisher(URL, ID, PW);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * public GeoserverServiceImpl(UserVO userVO) { // TODO Auto-generated
	 * constructor stub workspace = userVO.getId(); }
	 */

	/**
	 * @since 2017. 5. 12.
	 * @author SG.Lee
	 * @param layerInfo
	 * @return
	 * @throws IllegalArgumentException
	 * @throws MalformedURLException
	 * @see com.git.opengds.geoserver.service.GeoserverService#dbLayerPublishGeoserver(com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo)
	 */
	@SuppressWarnings("unused")
	public FileMeta dbLayerPublishGeoserver(UserVO userVO, GeoLayerInfo layerInfo) {
		String wsName = userVO.getId();
		String dsName = userVO.getId();

		String fileName = layerInfo.getFileName();
		List<String> layerNameList = layerInfo.getLayerNames();
		String originSrc = "EPSG:" + layerInfo.getOriginSrc();
		List<String> successLayerList = new ArrayList<String>();
		String fileType = layerInfo.getFileType();
		// boolean flag = false;

		// Collection<Geometry> geometryCollection = new ArrayList<Geometry>();
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

		GeoserverSldTextType sldType = new GeoserverSldTextType(); // Geoserver
																	// TEXT 타입

		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		System.out.println("작업 처리 요청 !");

		Result result = new Result();

		class Task implements Runnable {
			Result result;
			String layerName;

			boolean flag = false;

			Task(Result result, String layerName) {
				this.result = result;
				this.layerName = layerName;
			}

			@Override
			public void run() {
				/*
				 * int sum = 0;
				 * 
				 * for (int i = 1; i <= 10; i++) { sum += i; }
				 * 
				 * result.addValue(sum);
				 */
				
				System.out.println(layerName+" 레이어 발행 시작");
				
				GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
				GSLayerEncoder layerEncoder = new GSLayerEncoder();
				String upperLayerName = layerName.toUpperCase();

				int dash = layerName.indexOf("_");
				String cutLayerName = layerName.substring(0, dash);
				String layerType = layerName.substring(dash + 1);
				String layerFullName = "geo_" + fileType + "_" + fileName + "_" + layerName;

				fte.setProjectionPolicy(ProjectionPolicy.REPROJECT_TO_DECLARED);
				fte.setTitle(layerFullName); // 제목
				fte.setName(layerFullName); // 이름
				fte.setSRS(originSrc); // 좌표
				fte.setNativeCRS(originSrc);
				fte.setNativeName(layerFullName); // nativeName

				// 성능향상
				fte.addMetadata("cacheAgeMax", "604800");
				fte.addMetadata("cachingEnabled", ("true"));

				// Style 적용
				String styleName = upperLayerName;

				if (layerType.equals("TEXT")) {
					List<String> smallTextList = sldType.getSmallTextList();
					List<String> mediumTextList = sldType.getMediumTextList();
					List<String> largeTextList = sldType.getLargeTextList();
					List<String> exceptTextList = sldType.getExceptTextList();

					boolean isTextStyle = false;

					for (String stext : smallTextList) {
						if (cutLayerName.equals(stext)) {
							styleName = "SMALL_TEXT";
							isTextStyle = true;
						}
					}

					if (!isTextStyle) {
						for (String mtext : mediumTextList) {
							if (cutLayerName.equals(mtext)) {
								styleName = "MEDIUM_TEXT";
								isTextStyle = true;
								break;
							}
						}
						if (!isTextStyle) {
							for (String ltext : largeTextList) {
								if (cutLayerName.equals(ltext)) {
									styleName = "LARGE_TEXT";
									isTextStyle = true;
								}
							}
						}
						if (!isTextStyle) {
							if (cutLayerName.toUpperCase().equals("H0059153")) {
								if (fileType.equals("dxf")) {
									styleName = "DXF_" + cutLayerName + "+_TEXT";
									isTextStyle = true;
								} else if (fileType.equals("ngi")) {
									styleName = "NGI_" + cutLayerName + "+_TEXT";
									isTextStyle = true;
								}
							} else if (cutLayerName.equals("H0040000")) {
								styleName = cutLayerName + "+_TEXT";
								isTextStyle = true;
							}
						}
					}
				}

				if (layerType.equals("LWPOLYLINE") || layerType.equals("POLYLINE") || layerType.equals("LINE")) {
					styleName = cutLayerName.toUpperCase() + "_LWPOLYLINE";
				}
				if (layerType.equals("MULTILINESTRING")) {
					styleName = cutLayerName.toUpperCase() + "_LINESTRING";
				}
				if (layerType.equals("MULTIPOLYGON")) {
					styleName = cutLayerName.toUpperCase() + "_POLYGON";
				}
				if (layerType.equals("MULTIPOINT")) {
					styleName = cutLayerName.toUpperCase() + "_POINT";
				}

				boolean styleFlag = dtReader.existsStyle(styleName);
				if (styleFlag) {
					layerEncoder.setDefaultStyle(styleName);
				} else {
					layerEncoder.setDefaultStyle("defaultStyle");
				}

				flag = dtPublisher.publishDBLayer(wsName, dsName, fte, layerEncoder);

				if (flag == true) {
					RESTLayer layer = dtReader.getLayer(userVO.getId(), layerFullName);
					RESTFeatureType featureType = dtReader.getFeatureType(layer);

					double minx = featureType.getNativeBoundingBox().getMinX();
					double miny = featureType.getNativeBoundingBox().getMinY();
					double maxx = featureType.getNativeBoundingBox().getMaxX();
					double maxy = featureType.getNativeBoundingBox().getMaxY();

					if (minx != 0 && minx != -1 && miny != 0 && miny != -1 && maxx != 0 && maxx != -1 && maxy != 0
							&& maxy != -1) {
						Coordinate[] coords = new Coordinate[] { new Coordinate(minx, miny), new Coordinate(maxx, miny),
								new Coordinate(maxx, maxy), new Coordinate(minx, maxy), new Coordinate(minx, miny) };

						LinearRing ring = geometryFactory.createLinearRing(coords);
						LinearRing holes[] = null; // use LinearRing[] to
													// represent
													// holes
						Polygon polygon = geometryFactory.createPolygon(ring, holes);
						Geometry geometry = polygon;
						result.addGeoCollection(geometry);
					}
					result.addLayerName(userVO.getId() + ":" + layerFullName);
				} else if (flag == false) {
					result.addFailCount();

					/*
					 * for (String sucLayerName : successLayerList) {
					 * dtPublisher.removeLayer(wsName, sucLayerName); }
					 * dtPublisher.removeLayer(wsName, layerName);
					 * layerInfo.setServerPublishFlag(flag); return layerInfo;
					 */
				}

			}
		};

		List<Future<Result>> futures = new ArrayList<Future<Result>>();
		
		for (int i = 0; i < layerNameList.size(); i++) {
			Runnable task = new Task(result, layerNameList.get(i));
			Future<Result> future = executorService.submit(task, result);

			futures.add(future);
		}
		

		
		for(Future<Result> future : futures){
			try {
				System.out.println("레이어 발행완료");
				result = future.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		executorService.shutdown();

		if (result.failCount > 0) {
			for (String sucLayerName : result.successLayerList) {
				dtPublisher.removeLayer(wsName, sucLayerName);
			}
			layerInfo.setServerPublishFlag(false);
		} else {
			if (result.successLayerList.size() != 0) {
				GeometryCollection collection = (GeometryCollection) geometryFactory.buildGeometry(result.geometryCollection);
				Geometry geometry = collection.union();
				GSLayerGroupEncoder group = new GSLayerGroupEncoder();
				for (int i = 0; i < result.successLayerList.size(); i++) {
					String layer = (String) result.successLayerList.get(i);
					group.addLayer(layer);
				}

				Coordinate[] coordinateArray = geometry.getEnvelope().getCoordinates();
				Coordinate minCoordinate = new Coordinate();
				Coordinate maxCoordinate = new Coordinate();

				minCoordinate = coordinateArray[0];
				maxCoordinate = coordinateArray[2];

				double minx = minCoordinate.x;
				double miny = minCoordinate.y;
				double maxx = maxCoordinate.x;
				double maxy = maxCoordinate.y;

				group.setBounds(originSrc, minx, maxx, miny, maxy);

				dtPublisher.createLayerGroup(wsName, "gro_" + fileType + "_" + fileName, group);
			}
			layerInfo.setServerPublishFlag(true);
		}
		System.out.println("그룹레이어 발행");
		return layerInfo;

		/*
		 * class GeoCollectionResult{ Collection<Geometry> geometryCollection =
		 * new ArrayList<Geometry>(); synchronized void add(Geometry geometry) {
		 * geometryCollection.add(geometry); } }
		 */
/*
		for (int i = 0; i < layerNameList.size(); i++) {

			GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
			GSLayerEncoder layerEncoder = new GSLayerEncoder();
			String layerName = layerNameList.get(i);

			String upperLayerName = layerName.toUpperCase();

			int dash = layerName.indexOf("_");
			String cutLayerName = layerName.substring(0, dash);
			String layerType = layerName.substring(dash + 1);
			String layerFullName = "geo_" + fileType + "_" + fileName + "_" + layerName;

			fte.setProjectionPolicy(ProjectionPolicy.REPROJECT_TO_DECLARED);
			fte.setTitle(layerFullName); // 제목
			fte.setName(layerFullName); // 이름
			fte.setSRS(originSrc); // 좌표
			fte.setNativeCRS(originSrc);
			fte.setNativeName(layerFullName); // nativeName
			// fte.setLatLonBoundingBox(minx, miny, maxx, maxy, originSrc);

			// 성능향상
			fte.addMetadata("cacheAgeMax", "604800");
			fte.addMetadata("cachingEnabled", ("true"));

			// Style 적용
			String styleName = upperLayerName;

			if (layerType.equals("TEXT")) {
				List<String> smallTextList = sldType.getSmallTextList();
				List<String> mediumTextList = sldType.getMediumTextList();
				List<String> largeTextList = sldType.getLargeTextList();
				List<String> exceptTextList = sldType.getExceptTextList();

				boolean isTextStyle = false;

				for (String stext : smallTextList) {
					if (cutLayerName.equals(stext)) {
						styleName = "SMALL_TEXT";
						isTextStyle = true;
					}
				}

				if (!isTextStyle) {
					for (String mtext : mediumTextList) {
						if (cutLayerName.equals(mtext)) {
							styleName = "MEDIUM_TEXT";
							isTextStyle = true;
							break;
						}
					}
					if (!isTextStyle) {
						for (String ltext : largeTextList) {
							if (cutLayerName.equals(ltext)) {
								styleName = "LARGE_TEXT";
								isTextStyle = true;
							}
						}
					}
					if (!isTextStyle) {
						if (cutLayerName.toUpperCase().equals("H0059153")) {
							if (fileType.equals("dxf")) {
								styleName = "DXF_" + cutLayerName + "+_TEXT";
								isTextStyle = true;
							} else if (fileType.equals("ngi")) {
								styleName = "NGI_" + cutLayerName + "+_TEXT";
								isTextStyle = true;
							}
						} else if (cutLayerName.equals("H0040000")) {
							styleName = cutLayerName + "+_TEXT";
							isTextStyle = true;
						}
					}
				}
			}

			if (layerType.equals("LWPOLYLINE") || layerType.equals("POLYLINE") || layerType.equals("LINE")) {
				styleName = cutLayerName.toUpperCase() + "_LWPOLYLINE";
			}
			if (layerType.equals("MULTILINESTRING")) {
				styleName = cutLayerName.toUpperCase() + "_LINESTRING";
			}
			if (layerType.equals("MULTIPOLYGON")) {
				styleName = cutLayerName.toUpperCase() + "_POLYGON";
			}
			if (layerType.equals("MULTIPOINT")) {
				styleName = cutLayerName.toUpperCase() + "_POINT";
			}

			boolean styleFlag = dtReader.existsStyle(styleName);
			if (styleFlag) {
				layerEncoder.setDefaultStyle(styleName);
			} else {
				layerEncoder.setDefaultStyle("defaultStyle");
			}

			flag = dtPublisher.publishDBLayer(wsName, dsName, fte, layerEncoder);

			if (flag == true) {
				RESTLayer layer = dtReader.getLayer(userVO.getId(), layerFullName);
				RESTFeatureType featureType = dtReader.getFeatureType(layer);

				double minx = featureType.getNativeBoundingBox().getMinX();
				double miny = featureType.getNativeBoundingBox().getMinY();
				double maxx = featureType.getNativeBoundingBox().getMaxX();
				double maxy = featureType.getNativeBoundingBox().getMaxY();

				if (minx != 0 && minx != -1 && miny != 0 && miny != -1 && maxx != 0 && maxx != -1 && maxy != 0
						&& maxy != -1) {
					Coordinate[] coords = new Coordinate[] { new Coordinate(minx, miny), new Coordinate(maxx, miny),
							new Coordinate(maxx, maxy), new Coordinate(minx, maxy), new Coordinate(minx, miny) };

					LinearRing ring = geometryFactory.createLinearRing(coords);
					LinearRing holes[] = null; // use LinearRing[] to represent
												// holes
					Polygon polygon = geometryFactory.createPolygon(ring, holes);
					Geometry geometry = polygon;
					geometryCollection.add(geometry);
				}
			} else if (flag == false) {
				for (String sucLayerName : successLayerList) {
					dtPublisher.removeLayer(wsName, sucLayerName);
				}
				dtPublisher.removeLayer(wsName, layerName);
				layerInfo.setServerPublishFlag(flag);
				return layerInfo;
			}
			successLayerList.add(userVO.getId() + ":" + layerFullName);
		}

		if (layerNameList.size() != 0) {
			GeometryCollection collection = (GeometryCollection) geometryFactory.buildGeometry(geometryCollection);
			Geometry geometry = collection.union();
			GSLayerGroupEncoder group = new GSLayerGroupEncoder();
			for (int i = 0; i < successLayerList.size(); i++) {
				String layer = (String) successLayerList.get(i);
				group.addLayer(layer);
			}

			Coordinate[] coordinateArray = geometry.getEnvelope().getCoordinates();
			Coordinate minCoordinate = new Coordinate();
			Coordinate maxCoordinate = new Coordinate();

			minCoordinate = coordinateArray[0];
			maxCoordinate = coordinateArray[2];

			double minx = minCoordinate.x;
			double miny = minCoordinate.y;
			double maxx = maxCoordinate.x;
			double maxy = maxCoordinate.y;

			group.setBounds(originSrc, minx, maxx, miny, maxy);

			dtPublisher.createLayerGroup(wsName, "gro_" + fileType + "_" + fileName, group);
		}
		layerInfo.setServerPublishFlag(flag);

		return layerInfo;*/
	}

	

	/**
	 * @since 2017. 5. 12.
	 * @author SG.Lee
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#getGeoserverLayerCollectionTree()
	 */
	@Override
	public JSONArray getGeoserverLayerCollectionTree(UserVO userVO) {
		GeoserverLayerCollectionTree collectionTree = dtReader.getGeoserverLayerCollectionTree(userVO.getId());
		return collectionTree;
	}

	/**
	 * @since 2017. 6. 23.
	 * @author SG.Lee
	 * @param layerList
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#duplicateCheck(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject duplicateCheck(UserVO userVO, ArrayList<String> layerList) {
		JSONObject object = new JSONObject();
		for (String layerName : layerList) {
			object.put(layerName, dtReader.existsLayer(userVO.getId(), layerName));
		}
		return object;
	}

	/**
	 * @since 2017. 4
	 * @author SG.Lee
	 * @param layerList
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#getGeoLayerList(java.util.ArrayList)
	 */
	@Override
	public DTGeoLayerList getGeoLayerList(UserVO userVO, ArrayList<String> layerList) {
		if (layerList == null)
			throw new IllegalArgumentException("LayerNames may not be null");
		if (layerList.size() == 0)
			throw new IllegalArgumentException("LayerNames may not be null");
		return dtReader.getDTGeoLayerList(userVO.getId(), layerList);
	}

	/**
	 * @since 2017. 4
	 * @author SG.Lee
	 * @param groupList
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#getGeoGroupLayerList(java.util.ArrayList)
	 */
	@Override
	public DTGeoGroupLayerList getGeoGroupLayerList(UserVO userVO, ArrayList<String> groupList) {
		if (groupList == null)
			throw new IllegalArgumentException("GroupNames may not be null");
		if (groupList.size() == 0)
			throw new IllegalArgumentException("GroupNames may not be null");
		return dtReader.getDTGeoGroupLayerList(userVO.getId(), groupList);
	}

	/**
	 * @since 2017. 6. 5.
	 * @author SG.Lee
	 * @param layerName
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#removeGeoserverLayer(java.lang.String)
	 */
	@Override
	public boolean removeDTGeoserverLayer(UserVO userVO, String groupLayerName, String layerName) {
		boolean isConfigureGroup = false;
		boolean isRemoveFeatureType = false;
		DTGeoGroupLayer dtGeoGroupLayer = dtReader.getDTGeoGroupLayer(userVO.getId(), groupLayerName);

		if (dtGeoGroupLayer != null) {
			List<String> layerList = dtGeoGroupLayer.getPublishedList().getNames();
			layerList.remove(layerName);

			GSLayerGroupEncoder groupEncoder = new GSLayerGroupEncoder();
			groupEncoder.setName(dtGeoGroupLayer.getName());
			groupEncoder.setWorkspace(dtGeoGroupLayer.getWorkspace());
			groupEncoder.setBounds(dtGeoGroupLayer.getCRS(), dtGeoGroupLayer.getMinX(), dtGeoGroupLayer.getMaxY(),
					dtGeoGroupLayer.getMinY(), dtGeoGroupLayer.getMaxY());
			for (String name : layerList) {
				groupEncoder.addLayer(userVO.getId() + ":" + name);
			}
			isConfigureGroup = dtPublisher.configureLayerGroup(userVO.getId(), groupLayerName, groupEncoder);
			isRemoveFeatureType = dtPublisher.unpublishFeatureType(userVO.getId(), userVO.getId(), layerName);
			// isRemoveLayer = dtPublisher.removeLayer(userVO.getId(),
			// layerName);
		} else {
			isRemoveFeatureType = dtPublisher.unpublishFeatureType(userVO.getId(), userVO.getId(), layerName);
			if (!isRemoveFeatureType) {
				return false;
			}
			return true;
		}
		if (!isConfigureGroup && !isRemoveFeatureType) {
			return false;
		}
		return true;
	}

	/**
	 * @since 2017. 6. 5.
	 * @author SG.Lee
	 * @param layerNameList
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#removeGeoserverLayers(java.util.List)
	 */
	@Override
	public boolean removeDTGeoserverLayers(UserVO userVO, List<String> layerNameList) {
		return dtPublisher.removeLayers(userVO.getId(), layerNameList);
	}

	/**
	 * 
	 * @since 2017. 6. 5.
	 * @author SG.Lee
	 * @param groupLayerName
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#removeGeoserverGroupLayer(java.lang.String)
	 */
	@Override
	public boolean removeDTGeoserverAllLayer(UserVO userVO, String groupLayerName) {
		boolean isRemoveFlag = false;
		DTGeoGroupLayer dtGeoGroupLayer = dtReader.getDTGeoGroupLayer(userVO.getId(), groupLayerName);

		int flagVal = 0;

		if (dtGeoGroupLayer != null) {
			List<String> layerList = dtGeoGroupLayer.getPublishedList().getNames();

			dtPublisher.removeLayerGroup(userVO.getId(), groupLayerName);

			for (String layerName : layerList) {
				boolean isRemoveFeatureType = dtPublisher.unpublishFeatureType(userVO.getId(), userVO.getId(),
						layerName);
				if (isRemoveFeatureType) {
					flagVal++;
				}
			}

			if (layerList.size() == flagVal) {
				isRemoveFlag = true;
			} else
				isRemoveFlag = false;
		} else
			isRemoveFlag = false;
		return isRemoveFlag;
	}

	/**
	 *
	 * @author SG.Lee
	 * @Date 2017. 6. 19. 오후 9:15:07
	 * @return boolean
	 */
	@Override
	public List<String> getGeoserverStyleList() {
		return dtReader.getStyles().getNames();
	}

	/**
	 * 
	 * @since 2017. 6. 7.
	 * @author SG.Lee
	 * @param sldBody
	 * @param name
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#publishStyle(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean publishStyle(final String sldBody, final String name) {
		return dtPublisher.publishStyle(sldBody, name);
	};

	/**
	 * 
	 * @since 2017. 6. 7.
	 * @author SG.Lee
	 * @param sldBody
	 * @param name
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#updateStyle(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean updateStyle(final String sldBody, final String name) {
		return dtPublisher.updateStyle(sldBody, name);
	};

	/**
	 * 
	 * @since 2017. 6. 7.
	 * @author SG.Lee
	 * @param styleName
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#removeStyle(java.lang.String)
	 */
	@Override
	public boolean removeStyle(String styleName) {
		return dtPublisher.removeStyle(styleName);
	};

	@Override
	public boolean updateFeatureType(UserVO userVO, String orginalName, String name, String title,
			String abstractContent, String style, boolean attChangeFlag) {
		boolean updateFlag = false;
		GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
		GSLayerEncoder layerEncoder = null;

		if (orginalName == null) {
			// throw new IllegalArgumentException("OriginalName may not be
			// null!");
			return false;
		}
		if (orginalName.isEmpty()) {
			// throw new IllegalArgumentException("OriginalName may not be
			// empty!");
			return false;
		}
		if (name != null && !name.isEmpty()) {
			fte.setName(name);
		}
		if (title != null && !title.isEmpty()) {
			fte.setTitle(title);
		}
		if (abstractContent != null && !abstractContent.isEmpty()) {
			fte.setAbstract(abstractContent);
		}
		if (style != null && !style.isEmpty()) {
			layerEncoder = new GSLayerEncoder();
			layerEncoder.setDefaultStyle(style);
		}

		// boolean flag = dtPublisher.recalculate(workspace, storename,
		// layerFullName, testFte, testLayerEncoder);
		updateFlag = dtPublisher.updateFeatureType(userVO.getId(), userVO.getId(), orginalName, fte, layerEncoder,
				attChangeFlag);

		return updateFlag;
	}

	@Override
	public boolean errLayerListPublishGeoserver(GeoLayerInfoList geoLayerInfoList) {

		// boolean isAllSuccessed = true;
		// for (int i = 0; i < geoLayerInfoList.size(); i++) {
		// GeoLayerInfo geoLayerInfo = geoLayerInfoList.get(i);
		// isAllSuccessed = dtPublisher.publishErrLayer(ID, ID, geoLayerInfo);
		// }
		return false;
	}

	@Override
	public boolean errLayerPublishGeoserver(UserVO userVO, GeoLayerInfo geoLayerInfo) {
		// TODO Auto-generated method stub
		return dtPublisher.publishErrLayer(userVO.getId(), userVO.getId(), geoLayerInfo);
	}
}

/**
 * 쓰레드 Result 클래스
 * @author SG.Lee
 * @Date 2017. 9. 6. 오후 3:09:38
 * */
class Result {
	List<String> successLayerList = new ArrayList<String>();
	Collection<Geometry> geometryCollection = new ArrayList<Geometry>();
	int failCount = 0;

	synchronized void addLayerName(String layerName) {
		successLayerList.add(layerName);
	}

	synchronized void addGeoCollection(Geometry geometry) {
		geometryCollection.add(geometry);
	}

	synchronized void addFailCount() {
		failCount++;
	}
}
