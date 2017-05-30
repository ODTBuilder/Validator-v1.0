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

package com.git.gdsbuilder.validator.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * GeoJSON의 Geometry 검수를 수행한다.
 * 
 * @author dayeon.oh
 * @data 2016.10
 */
public class DTGeometry {

	/**
	 * JSONObject 형태의 Geometry를 타입별로 나누어 검수한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param geometry
	 *            검수를 수행할 Geometry
	 * @return boolean
	 */
	public boolean geometryValidate(JSONObject geometry) {

		// System.out.println(geometry.toString());

		boolean trueGeom = true;

		if (geometry == null) {
			trueGeom = false;
		} else {
			String geometryType = (String) geometry.get("type");
			JSONArray coordinates = (JSONArray) geometry.get("coordinates");
			if (geometryType == null || geometryType.equals("") || coordinates == null || coordinates.size() == 0) {
				trueGeom = false;
			} else {
				// SimpleFeature
				if (geometryType.equals("Point")) {
					trueGeom = pointValidate(coordinates);
				} else if (geometryType.equals("LineString")) {
					trueGeom = lineValidate(coordinates);
				} else if (geometryType.equals("Polygon")) {
					trueGeom = polygonValidate(coordinates);
				} else if (geometryType.equals("MultiPoint")) {
					trueGeom = MultiPointValidate(coordinates);
				} else if (geometryType.equals("MultiLineString")) {
					trueGeom = MultiLineValidate(coordinates);
				} else if (geometryType.equals("MultiPolygon")) {
					trueGeom = MultPolygonValidate(coordinates);
				} else {
					trueGeom = false;
				}
			}
		}
		// System.out.println("trueGeom : " + trueGeom);
		return trueGeom;
	}

	private boolean pointValidate(JSONArray coordinates) {

		boolean trueCoor = true;

//		try {
			int coordinatesSize = coordinates.size();
			// Point는 좌표값이 2개여야 함 (x, y)
			if (coordinatesSize != 2) {
				trueCoor = false;
			} else {
				// x, y는 Double 타입의 값이어야함
				for (int i = 0; i < coordinatesSize; i++) {
					if (!(coordinates.get(i) instanceof Double)) {
						trueCoor = false;
					}
				}
			}
//		} catch (Exception e) {
//			trueCoor = false;
//		}
		return trueCoor;
	}

	private boolean lineValidate(JSONArray coordinates) {

		boolean trueCoor = true;

	//	try {
			// line은 2개 Point 이상이어야함 (x1, y1) (x2, y2), (x3, y3) .........
			int coordinatesSize = coordinates.size();
			if (coordinatesSize < 2) {
				trueCoor = false;
			} else {
				for (int i = 0; i < coordinatesSize; i++) {
					JSONArray coordinate = (JSONArray) coordinates.get(i);
					// x, y는 Double 타입의 값이어야함
					if (coordinate == null) {
						trueCoor = false;
					} else {
						int coordinateSize = coordinate.size();
						if (coordinateSize != 2) {
							trueCoor = false;
						} else {
							for (int j = 0; j < coordinateSize; j++) {
								if (!(coordinate.get(j) instanceof Double)) {
									trueCoor = false;
								}
							}
						}
					}
				}
			}
//		} catch (Exception e) {
//			trueCoor = false;
//		}
		return trueCoor;
	}

	private boolean polygonValidate(JSONArray coordinates) {

		boolean trueCoor = true;
	//	try {
			int coordinatesSize = coordinates.size();
			for (int i = 0; i < coordinatesSize; i++) {
				// i = 1 부터는 hole
				JSONArray outCoor = (JSONArray) coordinates.get(i);
				if (outCoor == null) {
					trueCoor = false;
				} else {
					// 폴리곤은 4개 이상의 점으로 이루어져야함
					int outCoorSize = outCoor.size();
					if (outCoorSize < 4) {
						trueCoor = false;
					} else {
						// 폴리곤의 첫점과 끝점은 같아야함(폐합 조건)
						boolean equals = equalCoordinates((JSONArray) outCoor.get(0), (JSONArray) outCoor.get(outCoor.size() - 1));
						if (equals == false) {
							trueCoor = false;
						} else {
							for (int j = 0; j < outCoorSize; j++) {
								JSONArray inCoor = (JSONArray) outCoor.get(j);
								if (inCoor == null || inCoor.size() != 2) {
									trueCoor = false;
								} else {
									// x, y는 Double 타입의 값이어야함
									int inCoorSize = inCoor.size();
									for (int k = 0; k < inCoorSize; k++) {
										if (!(inCoor.get(k) instanceof Double)) {
											trueCoor = false;
										}
									}
								}
							}
						}
					}
				}
			}
//		} catch (Exception e) {
//			trueCoor = false;
//		}
		return trueCoor;
	}

	private boolean MultiPointValidate(JSONArray coordinates) {

		boolean trueCoor = true;

	//	try {
			int coordinatesSize = coordinates.size();
			for (int i = 0; i < coordinatesSize; i++) {
				JSONArray coordinate = (JSONArray) coordinates.get(i);
				// x, y는 Double 타입의 값이어야함
				if (coordinate == null || coordinate.size() != 2) {
					trueCoor = false;
				} else {
					int coordinateSize = coordinate.size();
					for (int j = 0; j < coordinateSize; j++) {
						if (!(coordinate.get(j) instanceof Double)) {
							trueCoor = false;
						}
					}
				}
			}
//		} catch (Exception e) {
//			trueCoor = false;
//		}
		return trueCoor;
	}

	private boolean MultiLineValidate(JSONArray coordinates) {

		boolean trueCoor = true;

	//	try {
			int coordinatesSize = coordinates.size();
			for (int i = 0; i < coordinatesSize; i++) {
				// i = 1 부터는 hole
				JSONArray outCoor = (JSONArray) coordinates.get(i);
				if (outCoor == null) {
					trueCoor = false;
				} else {
					// 라인은 2개 이상의 점으로 이루어져야함
					int outCoorSize = outCoor.size();
					if (outCoorSize < 2) {
						trueCoor = false;
					} else {
						for (int j = 0; j < outCoorSize; j++) {
							JSONArray inCoor = (JSONArray) outCoor.get(j);
							if (inCoor == null || inCoor.size() != 2) {
								trueCoor = false;
							} else {
								// x, y는 Double 타입의 값이어야함
								int inCoorSize = inCoor.size();
								for (int k = 0; k < inCoorSize; k++) {
									if (!(inCoor.get(k) instanceof Double)) {
										trueCoor = false;
									}
								}
							}
						}
					}
				}
			}
//		} catch (Exception e) {
//			trueCoor = false;
//		}
		return trueCoor;
	}

	private boolean MultPolygonValidate(JSONArray coordinates) {

		boolean trueCoor = true;

	//	try {
			int coordinatesSize = coordinates.size();
			for (int i = 0; i < coordinatesSize; i++) {
				// i = 1 부터는 hole
				JSONArray outCoor = (JSONArray) coordinates.get(i);
				if (outCoor == null) {
					trueCoor = false;
				} else {
					int outCoorSize = outCoor.size();
					for (int j = 0; j < outCoorSize; j++) {
						JSONArray midCoor = (JSONArray) outCoor.get(j);
						if (midCoor == null) {
							trueCoor = false;
						} else {
							// 폴리곤은 4개 이상의 점으로 이루어져야함
							if (midCoor.size() < 4) {
								trueCoor = false;
							} else {
								boolean equals = equalCoordinates((JSONArray) midCoor.get(0), (JSONArray) midCoor.get(midCoor.size() - 1));
								if (equals == false) {
									trueCoor = false;
								} else {
									int midCoorSize = midCoor.size();
									for (int k = 0; k < midCoorSize; k++) {
										JSONArray inCoor = (JSONArray) midCoor.get(k);
										int inCoorSize = inCoor.size();
										for (int n = 0; n < inCoorSize; n++) {
											if (!(inCoor.get(n) instanceof Double)) {
												trueCoor = false;
											}
										}
									}
								}
							}
						}
					}
				}
			}
//		} catch (Exception e) {
//			trueCoor = false;
//		}
		return trueCoor;
	}

	private boolean equalCoordinates(JSONArray point1, JSONArray point2) {

		boolean equalCoor = true;

		int i = point1.size();
		int j = point2.size();

		if (i == j) {
			int size = i;
			for (int k = 0; k < size; k++) {
				if (!(point1.get(k).equals(point2.get(k)))) {
					equalCoor = false;
				}
			}
		} else {
			equalCoor = false;
		}
		return equalCoor;
	}

	public JSONArray getFirstPoint(JSONObject geometry) {

		JSONArray returnJSON;

		if (geometry == null) {
			returnJSON = null;
		} else {
			String geometryType = (String) geometry.get("type");
			JSONArray coordinates = (JSONArray) geometry.get("coordinates");
			if (geometryType == null || geometryType.equals("") || coordinates == null || coordinates.size() == 0) {
				returnJSON = null;
			} else {
				// SimpleFeature
				returnJSON = new JSONArray();
				if (geometryType.equals("Point")) {
					returnJSON = getFirstPointOfPoint(coordinates);
				} else if (geometryType.equals("LineString")) {
					returnJSON = getFirstPointOfLine(coordinates);
				} else if (geometryType.equals("Polygon")) {
					returnJSON = getFirstPointOfPolygon(coordinates);
				} else if (geometryType.equals("MultiPoint")) {
					returnJSON = getFirstPointOfMtPoint(coordinates);
				} else if (geometryType.equals("MultiLineString")) {
					returnJSON = getFirstPointOfMtLine(coordinates);
				} else if (geometryType.equals("MultiPolygon")) {
					returnJSON = getFirstPointOfMtPolygon(coordinates);
				} else {
					returnJSON = null;
				}
			}
		}
		return returnJSON;
	}

	private JSONArray getFirstPointOfPoint(JSONArray coordinates) {
		return coordinates;
	}

	private JSONArray getFirstPointOfLine(JSONArray coordinates) {
		return (JSONArray) coordinates.get(0);
	}

	private JSONArray getFirstPointOfPolygon(JSONArray coordinates) {
		JSONArray outCoor = (JSONArray) coordinates.get(0);
		return (JSONArray) outCoor.get(0);
	}

	private JSONArray getFirstPointOfMtPoint(JSONArray coordinates) {
		return (JSONArray) coordinates.get(0);
	}

	private JSONArray getFirstPointOfMtLine(JSONArray coordinates) {
		JSONArray outCoor = (JSONArray) coordinates.get(0);
		return (JSONArray) outCoor.get(0);
	}

	private JSONArray getFirstPointOfMtPolygon(JSONArray coordinates) {
		JSONArray outCoor = (JSONArray) coordinates.get(0);
		JSONArray midCoor = (JSONArray) outCoor.get(0);
		return (JSONArray) midCoor.get(0);
	}
}
