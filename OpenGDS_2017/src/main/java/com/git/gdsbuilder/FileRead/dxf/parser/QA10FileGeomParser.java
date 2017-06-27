package com.git.gdsbuilder.FileRead.dxf.parser;

import java.util.Iterator;

import org.kabeja.dxf.DXFVertex;
import org.kabeja.dxf.helpers.Point;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.util.GeometricShapeFactory;

public class QA10FileGeomParser {

	// ARC
	public static Geometry parseDTArc(Point pt, double radius, double startAng, double angExtent) {

		// CenterPt
		Coordinate coor = new Coordinate(pt.getX(), pt.getY(), pt.getZ());

		GeometricShapeFactory f = new GeometricShapeFactory();
		f.setCentre(coor);
		f.setSize(radius * 2);
		f.setNumPoints(100);
		f.setRotation(0);
		Geometry arc = f.createArc(Math.toRadians(startAng), Math.toRadians(angExtent));
		return arc;
	}

	// CIRCLE
	public static Geometry parseDTCircle(Point pt, double radius) {

		Coordinate coor = new Coordinate(pt.getX(), pt.getY(), pt.getZ());
		GeometricShapeFactory f = new GeometricShapeFactory();
		f.setCentre(coor);
		f.setSize(radius * 2);
		f.setNumPoints(100);
		f.setRotation(0);
		Geometry circle = f.createCircle();
		return circle.getBoundary();

	}

	// TEXT, INSERT
	public static Geometry parseDTPoint(Point pt) {

		Coordinate coor = new Coordinate(pt.getX(), pt.getY(), pt.getZ());
		GeometryFactory gf = new GeometryFactory();
		return gf.createPoint(coor);

	}

	// LINE
	public static Geometry parseDTLine(Point startPt, Point endPt) {

		Coordinate startCoor = new Coordinate(startPt.getX(), startPt.getY(), startPt.getZ());
		Coordinate endCoor = new Coordinate(endPt.getX(), endPt.getY(), endPt.getZ());
		Coordinate[] lineCoor = new Coordinate[2];
		lineCoor[0] = startCoor;
		lineCoor[1] = endCoor;
		GeometryFactory gf = new GeometryFactory();
		return gf.createLineString(lineCoor);

	}

	// POLYLINE
	public static Geometry parseDTLineString(boolean isClosed, Iterator vertexIterator, int vertexCount) {

		GeometryFactory gf = new GeometryFactory();
		Coordinate[] coors;
		if (isClosed) {
			coors = new Coordinate[vertexCount + 1];
			int i = 0;
			while (vertexIterator.hasNext()) {
				if (i < vertexCount) {
					DXFVertex dxfVertex = (DXFVertex) vertexIterator.next();
					coors[i] = new Coordinate(dxfVertex.getX(), dxfVertex.getY(), dxfVertex.getZ());
					i++;
				} else {
					break;
				}
			}
			coors[vertexCount] = coors[0];
		} else {
			coors = new Coordinate[vertexCount];
			int i = 0;
			while (vertexIterator.hasNext()) {
				if (i < vertexCount) {
					DXFVertex dxfVertex = (DXFVertex) vertexIterator.next();
					coors[i] = new Coordinate(dxfVertex.getX(), dxfVertex.getY(), dxfVertex.getZ());
					i++;
				} else {
					break;
				}
			}
		}
		return gf.createLineString(coors);
	}

	// LWPOLYLINE
	public static Geometry parseDT3DLineString(boolean isClosed, Iterator vertexIterator, int vertexCount,
			double elevation) {

		GeometryFactory gf = new GeometryFactory();
		Coordinate[] coors;
		if (isClosed) {
			coors = new Coordinate[vertexCount + 1];
			int i = 0;
			while (vertexIterator.hasNext()) {
				if (i < vertexCount) {
					DXFVertex dxfVertex = (DXFVertex) vertexIterator.next();
					coors[i] = new Coordinate(dxfVertex.getX(), dxfVertex.getY(), elevation);
					i++;
				} else {
					break;
				}
			}
			coors[vertexCount] = coors[0];
		} else {
			coors = new Coordinate[vertexCount];
			int i = 0;
			while (vertexIterator.hasNext()) {
				if (i < vertexCount) {
					DXFVertex dxfVertex = (DXFVertex) vertexIterator.next();
					coors[i] = new Coordinate(dxfVertex.getX(), dxfVertex.getY(), elevation);
					i++;
				} else {
					break;
				}
			}
		}
		return gf.createLineString(coors);
	}

	// SOLID
	public static Geometry parseDTPolygon(Point point1, Point point2, Point point3, Point point4) {

		GeometryFactory gf = new GeometryFactory();
		Coordinate coor1 = new Coordinate(point1.getX(), point1.getY(), point1.getZ());
		Coordinate coor2 = new Coordinate(point2.getX(), point2.getY(), point2.getZ());
		Coordinate coor3 = new Coordinate(point3.getX(), point3.getY(), point3.getZ());
		Coordinate coor4 = new Coordinate(point4.getX(), point4.getY(), point4.getZ());

		LinearRing lr;
		if (coor3.equals3D(coor4)) {
			coor4 = coor1;
			lr = gf.createLinearRing(new Coordinate[] { coor1, coor2, coor3, coor4 });
			return gf.createPolygon(lr, null);
		} else {
			lr = gf.createLinearRing(new Coordinate[] { coor1, coor2, coor3, coor4 });
			return gf.createPolygon(lr, null);
		}
	}
}
