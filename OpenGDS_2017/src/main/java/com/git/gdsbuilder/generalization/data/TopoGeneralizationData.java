package com.git.gdsbuilder.generalization.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.generalization.data.topo.Topology;
import com.git.gdsbuilder.generalization.data.topo.TopologyTable;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class TopoGeneralizationData { 
	private SimpleFeatureCollection collection;
	private ConnectObject connectObject;
	private DisConnectObject disConnectObject;
	private TopologyTable topologyTable;
	
	
	public TopoGeneralizationData(){
		
	}
	
	class ConnectObject{
		private DefaultFeatureCollection connectCollection = new DefaultFeatureCollection();
		
		public ConnectObject() {
			// TODO Auto-generated constructor stub
		}
		
		public ConnectObject(SimpleFeatureCollection collection) {
			SimpleFeatureIterator iterator = collection.features();
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				connectCollection.add(feature);
			}
		}
		
		public void add(SimpleFeature feature){
			connectCollection.add(feature);
		}
		
		public SimpleFeatureCollection getConnectCollection(){
			return connectCollection;
		}
	}
	
	class DisConnectObject{
		private DefaultFeatureCollection disConnectCollection = new DefaultFeatureCollection();

		
		public DisConnectObject(){
			// TODO Auto-generated constructor stub
		}
		
		public DisConnectObject(SimpleFeatureCollection collection) {
			SimpleFeatureIterator iterator = collection.features();
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				disConnectCollection.add(feature); 
			}
		}
		
		public void add(SimpleFeature feature){
			disConnectCollection.add(feature);
		}
		
		public SimpleFeatureCollection getDisConnectCollection(){
			return disConnectCollection;
		}
	}
	
	public TopoGeneralizationData(SimpleFeatureCollection collection) {
		this.collection = collection;
	}
	
	public SimpleFeatureCollection getCollection() {
		return collection;
	}

	public void setCollection(SimpleFeatureCollection collection) {
		this.collection = collection;  
	}
	
	public SimpleFeatureCollection getConnectCollection() {
		return this.connectObject.getConnectCollection();
	}

	public SimpleFeatureCollection getDisConnectCollection() {
		return this.disConnectObject.getDisConnectCollection();
	}

	public TopologyTable getTopologyTable() {
		return topologyTable;
	}

	
	public void topologyBuilder(){
		if(this.collection != null){
			Vector<SimpleFeature> simpleFeatures = new Vector<SimpleFeature>();
			SimpleFeatureIterator iterator = this.collection.features();
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				String id = feature.getID();
				simpleFeatures.add(feature);
			}
			
			//Topology 관련 객체 초기화
			connectObject = new ConnectObject();
			disConnectObject = new DisConnectObject();
			topologyTable = new TopologyTable();
			
			GeometryFactory factory = new GeometryFactory();
			
			
			
			List<String> firstObjs = new ArrayList<String>();
			List<String> lastObjs = new ArrayList<String>();
			
			if (simpleFeatures.size() != 0) {
				for (int i = 0; i < simpleFeatures.size(); i++) {
					//Feature간 Topology 생성
					Topology topology = new Topology();
					firstObjs.clear();
					lastObjs.clear();
					
					topology.setObjID(simpleFeatures.get(i).getID());
					System.out.println(simpleFeatures.get(i).getID() + "빌드중");
					
					///////////////////////////////////////////////////////////////////////////////////
					boolean startFlag = false;
					boolean endFlag = false;
					Geometry mainGeom = (Geometry) simpleFeatures.get(i).getDefaultGeometry();
					
					//길이, 영역
					///////////////////////////
					Double length = mainGeom.getLength()/1000;
					topology.setAlValue(length.valueOf(Math.round(length*100d)));
					
					
					
					Coordinate[] mainCoord = mainGeom.getCoordinates();
					for (int j = 0; j < simpleFeatures.size(); j++) {
						if (i != j) {
							Geometry subGeom = (Geometry) simpleFeatures.get(j).getDefaultGeometry();
							/*if (mainCoord[0].equa ls2D(mainCoord[mainCoord.length - 1])) {
								allCollection.add(simpleFeatures.get(i));}*/  
//							else {
								Geometry startGeom = factory.createPoint(mainCoord[0]);
								Geometry endGeom = factory.createPoint(mainCoord[mainCoord.length - 1]);
								if (endGeom.isWithinDistance(subGeom, 2000)) {
									endFlag = true;
									lastObjs.add(simpleFeatures.get(j).getID());
								} 
								if (startGeom.isWithinDistance(subGeom, 2000)) {
									startFlag = true;
									firstObjs.add(simpleFeatures.get(j).getID());
								}
//							}
						}
					}
					if(topology!=null){
						topology.setFirstObjs(firstObjs);
						topology.setLastObjs(lastObjs);
						topologyTable.add(topology);
					}
					if (startFlag == true && endFlag == true) {
						connectObject.add(simpleFeatures.get(i));
					} else
						disConnectObject.add(simpleFeatures.get(i));
				}
			}
		}
	}
}
