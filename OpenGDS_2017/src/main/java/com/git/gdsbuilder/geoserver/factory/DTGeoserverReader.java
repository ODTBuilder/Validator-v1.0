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

/*
 *  GeoServer-Manager - Simple Manager Library for GeoServer
 *  
 *  Copyright (C) 2007,2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.git.gdsbuilder.geoserver.factory;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.gdsbuilder.geolayer.data.DTGeoGroupLayer;
import com.git.gdsbuilder.geolayer.data.DTGeoGroupLayerList;
import com.git.gdsbuilder.geolayer.data.DTGeoLayer;
import com.git.gdsbuilder.geolayer.data.DTGeoLayerList;
import com.git.gdsbuilder.geoserver.data.GeoserverLayerCollectionTree;
import com.git.gdsbuilder.geoserver.data.GeoserverLayerCollectionTree.TreeType;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTReader;
import com.git.gdsbuilder.geosolutions.geoserver.rest.HTTPUtils;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTFeatureTypeList;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTLayer;


/** 
* @ClassName: DTGeoserverReader 
* @Description: GeoSolution과 관련 있는 data read 기능
* @author JY.Kim 
* @date 2017. 5. 2. 오후 2:38:58 
*/
public class DTGeoserverReader extends GeoServerRESTReader {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(DTGeoserverReader.class);

    private final String baseurl;
    private String username;
    private String password;
	
	public DTGeoserverReader(String gsUrl, String username, String password) throws MalformedURLException {
		super(gsUrl, username, password);
		this.baseurl = gsUrl;
		this.username = username;
		this.password = password;
    }
	
	public DTGeoLayer getDTGeoLayer(String workspace, String name){
		if (workspace == null || workspace.isEmpty())
            throw new IllegalArgumentException("Workspace may not be null");
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Layername may not be null");
        
        RESTLayer layer = getLayer(workspace, name);
        
        if(layer.getType() != RESTLayer.Type.VECTOR)
            throw new RuntimeException("Bad layer type for layer " + layer.getName());
        
        String response = loadFullURL(layer.getResourceUrl());
        DTGeoLayer dtGeoLayer = null;
        dtGeoLayer = DTGeoLayer.build(response);
        return dtGeoLayer;
	}
	
	@SuppressWarnings("unused")
	public DTGeoGroupLayer getDTGeoGroupLayer(String workspace, String name){
		if (workspace == null || workspace.isEmpty())
            throw new IllegalArgumentException("Workspace may not be null");
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Layername may not be null");
        
        String url;
        if (workspace == null) {
            url = "/rest/layergroups/" + name + ".xml";
        } else {
            url = "/rest/workspaces/" + workspace + "/layergroups/" + name + ".xml";
        }        
        
        return DTGeoGroupLayer.build(load(url));
	}
	
	public DTGeoLayerList getDTGeoLayerList(String workspace, ArrayList<String> layerNames){
		if(workspace ==null || workspace.isEmpty())
			throw new IllegalArgumentException("Workspace may not be null");
		if(layerNames==null)
			throw new IllegalArgumentException("LayerNames may not be null");
		if(layerNames.size()==0)
			throw new IllegalArgumentException("LayerNames may not be null");

		DTGeoLayerList geoLayerList = new DTGeoLayerList();
		
		for(String layerName:layerNames){
			DTGeoLayer dtGeoLayer = getDTGeoLayer(workspace, layerName);
			RESTLayer layer = getLayer(username, layerName);
//			dtGeoLayer.setStyle(layer.getDefaultStyle());
			geoLayerList.add(dtGeoLayer);
		}
		return geoLayerList;
	}
	
	public DTGeoGroupLayerList getDTGeoGroupLayerList(String workspace, ArrayList<String> groupNames){
		if(workspace ==null || workspace.isEmpty())
			throw new IllegalArgumentException("Workspace may not be null");
		if(groupNames==null)
			throw new IllegalArgumentException("GroupNames may not be null");
		if(groupNames.size()==0)
			throw new IllegalArgumentException("GroupNames may not be null");
		
		DTGeoGroupLayerList groupLayerList = new DTGeoGroupLayerList();
		
		for(String groupName : groupNames){
			DTGeoGroupLayer geoGroupLayer = getDTGeoGroupLayer(workspace, groupName);
			groupLayerList.add(geoGroupLayer);
		}
		return groupLayerList;
	};
	
	public GeoserverLayerCollectionTree getGeoserverLayerCollectionTree(String workspace, TreeType treeType){
		if(workspace ==null || workspace.isEmpty()){
			throw new IllegalArgumentException("Workspace may not be null");
		}
		RESTFeatureTypeList typeList = super.getFeatureTypes(workspace);
		GeoserverLayerCollectionTree collectionTree = null;
		collectionTree = new GeoserverLayerCollectionTree(typeList,treeType);
		return collectionTree;
	}
	
	public List<String> getGeoserverContainNames(String workspace,String name){
		List<String> containNames = new ArrayList<String>();
		RESTFeatureTypeList featureTypeList = getFeatureTypes(workspace);
		List<String> layerNames =new ArrayList<String>();
		
		layerNames = featureTypeList.getNames();
		
		
		for(String layerName : layerNames){
			if(layerName.contains(name)){
				containNames.add(layerName);
			}
		}
		return containNames;
	}
	
    private String load(String url) {
        LOGGER.info("Loading from REST path " + url);
        String response = HTTPUtils.get(baseurl + url, username, password);
        return response;
    }

    private String loadFullURL(String url) {
        LOGGER.info("Loading from REST path " + url);
        String response = HTTPUtils.get(url, username, password);
        return response;
    }
    
    
}
