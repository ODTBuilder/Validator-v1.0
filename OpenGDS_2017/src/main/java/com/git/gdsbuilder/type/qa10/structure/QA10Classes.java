package com.git.gdsbuilder.type.qa10.structure;

import java.util.HashMap;

public class QA10Classes {
	
	String collectionName;
	HashMap classValues;
	
	public enum EnDXFClasses {

		// default class values
		ACDBDICTIONARYWDFLT("ACDBDICTIONARYWDFLT", "AcDbDictionaryWithDefault", "0", "0", "0"), 
		ACDBPLACEHOLDER("ACDBPLACEHOLDER", "AcDbPlaceHolder", "0", "0", "0"), 
		ARCALIGNEDTEXT("ARCALIGNEDTEXT", "AcDbArcAlignedText", "0", "0", "1"), 
		DICTIONARYVAR("DICTIONARYVAR", "AcDbDictionaryVar", "0", "0", "0"), 
		HATCH("HATCH", "AcDbHatch", "0", "0", "1"), 
		IDBUFFER("IDBUFFER", "AcDbIdBuffer", "0", "0", "0"), 
		IMAGE("IMAGE", "AcDbRasterImage", "127", "0", "1"), 
		IMAGEDEF("IMAGEDEF", "AcDbRasterImageDef", "0", "0", "0"), 
		IMAGEDEF_REACTOR("IMAGEDEF_REACTOR", "AcDbRasterImageDefReactor", "1", "0", "0"), 
		LAYER_INDEX("LAYER_INDEX", "AcDbLayerIndex", "0", "0", "0"), 
		LAYOUT("LAYOUT", "AcDbLayout", "0", "0", "0"), 
		LWPOLYLINE("LWPOLYLINE", "AcDbPolyline", "0", "0", "1"), 
		OBJECT_PTR("OBJECT_PTR", "CAseDLPNTableRecord", "1", "0", "0"), 
		OLE2FRAME("OLE2FRAME", "AcDbOle2Frame", "0", "0", "1"), 
		RASTERVARIABLES("RASTERVARIABLES", "AcDbRasterVariables", "0", "0", "0"), 
		RTEXT("RTEXT", "RText", "0", "0", "1"), 
		SORTENTSTABLE("SORTENTSTABLE", "AcDbSortentsTable", "0", "0", "0"), 
		SPATIAL_INDEX("SPATIAL_INDEX", "AcDbSpatialIndex", "0", "0", "0"), 
		SPATIAL_FILTER("SPATIAL_FILTER", "AcDbSpatialFilter", "0", "0", "0"), 
		WIPEOUT("WIPEOUT", "AcDbWipeout", "127", "0", "1"), 
		WIPEOUTVARIABLES("WIPEOUTVARIABLES", "AcDbWipeoutVariables", "0", "0", "0"); 
		
		String dxfRecordName;
		String cppClassName;
		String code90;
		String code280;
		String code281;
		
		private EnDXFClasses(String dxfRecordName, String cppClassName, String code90, String code280, String code281) {
			this.dxfRecordName = dxfRecordName;
			this.cppClassName = cppClassName;
			this.code90 = code90;
			this.code280 = code280;
			this.code281 = code281;
		}

		public String getDxfRecordName() {
			return dxfRecordName;
		}

		public void setDxfRecordName(String dxfRecordName) {
			this.dxfRecordName = dxfRecordName;
		}

		public String getCppClassName() {
			return cppClassName;
		}

		public void setCppClassName(String cppClassName) {
			this.cppClassName = cppClassName;
		}

		public String getCode90() {
			return code90;
		}

		public void setCode90(String code90) {
			this.code90 = code90;
		}

		public String getCode280() {
			return code280;
		}

		public void setCode280(String code280) {
			this.code280 = code280;
		}

		public String getCode281() {
			return code281;
		}

		public void setCode281(String code281) {
			this.code281 = code281;
		}
	}
	
	public QA10Classes() {
		this.classValues = new HashMap<String, Object>();
	}
	
	public QA10Classes(HashMap<String, Object> classValues) {
		this.classValues =classValues;
	}	
	
	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public void setClassValues(HashMap classValues) {
		this.classValues = classValues;
	}

	public void putDefaultValues() {
		
		QA10Class ACDBDICTIONARYWDFLT = new QA10Class(EnDXFClasses.ACDBDICTIONARYWDFLT.getDxfRecordName(),
				EnDXFClasses.ACDBDICTIONARYWDFLT.getCppClassName(), EnDXFClasses.ACDBDICTIONARYWDFLT.getCode90(),
				EnDXFClasses.ACDBDICTIONARYWDFLT.getCode280(), EnDXFClasses.ACDBDICTIONARYWDFLT.getCode281());
		classValues.put(EnDXFClasses.ACDBDICTIONARYWDFLT.getDxfRecordName(), ACDBDICTIONARYWDFLT);
		
		QA10Class ACDBPLACEHOLDER = new QA10Class(EnDXFClasses.ACDBPLACEHOLDER.getDxfRecordName(),
				EnDXFClasses.ACDBPLACEHOLDER.getCppClassName(), EnDXFClasses.ACDBPLACEHOLDER.getCode90(),
				EnDXFClasses.ACDBPLACEHOLDER.getCode280(), EnDXFClasses.ACDBPLACEHOLDER.getCode281());
		classValues.put(EnDXFClasses.ACDBPLACEHOLDER.getDxfRecordName(), ACDBPLACEHOLDER);
		
		QA10Class ARCALIGNEDTEXT = new QA10Class(EnDXFClasses.ARCALIGNEDTEXT.getDxfRecordName(),
				EnDXFClasses.ARCALIGNEDTEXT.getCppClassName(), EnDXFClasses.ARCALIGNEDTEXT.getCode90(),
				EnDXFClasses.ARCALIGNEDTEXT.getCode280(), EnDXFClasses.ARCALIGNEDTEXT.getCode281());
		classValues.put(EnDXFClasses.ARCALIGNEDTEXT.getDxfRecordName(), ARCALIGNEDTEXT);
		
		QA10Class DICTIONARYVAR = new QA10Class(EnDXFClasses.DICTIONARYVAR.getDxfRecordName(),
				EnDXFClasses.DICTIONARYVAR.getCppClassName(), EnDXFClasses.DICTIONARYVAR.getCode90(),
				EnDXFClasses.DICTIONARYVAR.getCode280(), EnDXFClasses.DICTIONARYVAR.getCode281());
		classValues.put(EnDXFClasses.DICTIONARYVAR.getDxfRecordName(), DICTIONARYVAR);
		
		QA10Class HATCH = new QA10Class(EnDXFClasses.HATCH.getDxfRecordName(),
				EnDXFClasses.HATCH.getCppClassName(), EnDXFClasses.HATCH.getCode90(),
				EnDXFClasses.HATCH.getCode280(), EnDXFClasses.HATCH.getCode281());
		classValues.put(EnDXFClasses.HATCH.getDxfRecordName(), HATCH);

		QA10Class IDBUFFER = new QA10Class(EnDXFClasses.IDBUFFER.getDxfRecordName(),
				EnDXFClasses.IDBUFFER.getCppClassName(), EnDXFClasses.IDBUFFER.getCode90(),
				EnDXFClasses.IDBUFFER.getCode280(), EnDXFClasses.IDBUFFER.getCode281());
		classValues.put(EnDXFClasses.IDBUFFER.getDxfRecordName(), IDBUFFER);
		
		QA10Class IMAGE = new QA10Class(EnDXFClasses.IMAGE.getDxfRecordName(),
				EnDXFClasses.IMAGE.getCppClassName(), EnDXFClasses.IMAGE.getCode90(),
				EnDXFClasses.IMAGE.getCode280(), EnDXFClasses.IMAGE.getCode281());
		classValues.put(EnDXFClasses.IMAGE.getDxfRecordName(), IMAGE);
		
		QA10Class IMAGEDEF = new QA10Class(EnDXFClasses.IMAGEDEF.getDxfRecordName(),
				EnDXFClasses.IMAGEDEF.getCppClassName(), EnDXFClasses.IMAGEDEF.getCode90(),
				EnDXFClasses.IMAGEDEF.getCode280(), EnDXFClasses.IMAGEDEF.getCode281());
		classValues.put(EnDXFClasses.IMAGEDEF.getDxfRecordName(), IMAGEDEF);
		
		QA10Class IMAGEDEF_REACTOR = new QA10Class(EnDXFClasses.IMAGEDEF_REACTOR.getDxfRecordName(),
				EnDXFClasses.IMAGEDEF_REACTOR.getCppClassName(), EnDXFClasses.IMAGEDEF_REACTOR.getCode90(),
				EnDXFClasses.IMAGEDEF_REACTOR.getCode280(), EnDXFClasses.IMAGEDEF_REACTOR.getCode281());
		classValues.put(EnDXFClasses.IMAGEDEF_REACTOR.getDxfRecordName(), IMAGEDEF_REACTOR);
		
		QA10Class LAYER_INDEX = new QA10Class(EnDXFClasses.LAYER_INDEX.getDxfRecordName(),
				EnDXFClasses.LAYER_INDEX.getCppClassName(), EnDXFClasses.LAYER_INDEX.getCode90(),
				EnDXFClasses.LAYER_INDEX.getCode280(), EnDXFClasses.LAYER_INDEX.getCode281());
		classValues.put(EnDXFClasses.LAYER_INDEX.getDxfRecordName(), LAYER_INDEX);
		
		QA10Class LAYOUT = new QA10Class(EnDXFClasses.LAYOUT.getDxfRecordName(),
				EnDXFClasses.LAYOUT.getCppClassName(), EnDXFClasses.LAYOUT.getCode90(),
				EnDXFClasses.LAYOUT.getCode280(), EnDXFClasses.LAYOUT.getCode281());
		classValues.put(EnDXFClasses.LAYOUT.getDxfRecordName(), LAYOUT);
		
		QA10Class LWPOLYLINE = new QA10Class(EnDXFClasses.LWPOLYLINE.getDxfRecordName(),
				EnDXFClasses.LWPOLYLINE.getCppClassName(), EnDXFClasses.LWPOLYLINE.getCode90(),
				EnDXFClasses.LWPOLYLINE.getCode280(), EnDXFClasses.LWPOLYLINE.getCode281());
		classValues.put(EnDXFClasses.LWPOLYLINE.getDxfRecordName(), LWPOLYLINE);
		
		QA10Class OBJECT_PTR = new QA10Class(EnDXFClasses.OBJECT_PTR.getDxfRecordName(),
				EnDXFClasses.OBJECT_PTR.getCppClassName(), EnDXFClasses.OBJECT_PTR.getCode90(),
				EnDXFClasses.OBJECT_PTR.getCode280(), EnDXFClasses.OBJECT_PTR.getCode281());
		classValues.put(EnDXFClasses.OBJECT_PTR.getDxfRecordName(), OBJECT_PTR);
		
		QA10Class OLE2FRAME = new QA10Class(EnDXFClasses.OLE2FRAME.getDxfRecordName(),
				EnDXFClasses.OLE2FRAME.getCppClassName(), EnDXFClasses.OLE2FRAME.getCode90(),
				EnDXFClasses.OLE2FRAME.getCode280(), EnDXFClasses.OLE2FRAME.getCode281());
		classValues.put(EnDXFClasses.OLE2FRAME.getDxfRecordName(), OLE2FRAME);
		
		QA10Class RASTERVARIABLES = new QA10Class(EnDXFClasses.RASTERVARIABLES.getDxfRecordName(),
				EnDXFClasses.RASTERVARIABLES.getCppClassName(), EnDXFClasses.RASTERVARIABLES.getCode90(),
				EnDXFClasses.RASTERVARIABLES.getCode280(), EnDXFClasses.RASTERVARIABLES.getCode281());
		classValues.put(EnDXFClasses.RASTERVARIABLES.getDxfRecordName(), RASTERVARIABLES);
		
		QA10Class RTEXT = new QA10Class(EnDXFClasses.RTEXT.getDxfRecordName(),
				EnDXFClasses.RTEXT.getCppClassName(), EnDXFClasses.RTEXT.getCode90(),
				EnDXFClasses.RTEXT.getCode280(), EnDXFClasses.RTEXT.getCode281());
		classValues.put(EnDXFClasses.RTEXT.getDxfRecordName(), RTEXT);
		
		QA10Class SORTENTSTABLE = new QA10Class(EnDXFClasses.SORTENTSTABLE.getDxfRecordName(),
				EnDXFClasses.SORTENTSTABLE.getCppClassName(), EnDXFClasses.SORTENTSTABLE.getCode90(),
				EnDXFClasses.SORTENTSTABLE.getCode280(), EnDXFClasses.SORTENTSTABLE.getCode281());
		classValues.put(EnDXFClasses.SORTENTSTABLE.getDxfRecordName(), SORTENTSTABLE);
		
		QA10Class SPATIAL_INDEX = new QA10Class(EnDXFClasses.SPATIAL_INDEX.getDxfRecordName(),
				EnDXFClasses.SPATIAL_INDEX.getCppClassName(), EnDXFClasses.SPATIAL_INDEX.getCode90(),
				EnDXFClasses.SPATIAL_INDEX.getCode280(), EnDXFClasses.SPATIAL_INDEX.getCode281());
		classValues.put(EnDXFClasses.SPATIAL_INDEX.getDxfRecordName(), SPATIAL_INDEX);
		
		QA10Class SPATIAL_FILTER = new QA10Class(EnDXFClasses.SPATIAL_FILTER.getDxfRecordName(),
				EnDXFClasses.SPATIAL_FILTER.getCppClassName(), EnDXFClasses.SPATIAL_FILTER.getCode90(),
				EnDXFClasses.SPATIAL_FILTER.getCode280(), EnDXFClasses.SPATIAL_FILTER.getCode281());
		classValues.put(EnDXFClasses.SPATIAL_FILTER.getDxfRecordName(), SPATIAL_FILTER);
		
		QA10Class WIPEOUT = new QA10Class(EnDXFClasses.WIPEOUT.getDxfRecordName(),
				EnDXFClasses.WIPEOUT.getCppClassName(), EnDXFClasses.WIPEOUT.getCode90(),
				EnDXFClasses.WIPEOUT.getCode280(), EnDXFClasses.WIPEOUT.getCode281());
		classValues.put(EnDXFClasses.WIPEOUT.getDxfRecordName(), WIPEOUT);
		
		QA10Class WIPEOUTVARIABLES = new QA10Class(EnDXFClasses.WIPEOUTVARIABLES.getDxfRecordName(),
				EnDXFClasses.WIPEOUTVARIABLES.getCppClassName(), EnDXFClasses.WIPEOUTVARIABLES.getCode90(),
				EnDXFClasses.WIPEOUTVARIABLES.getCode280(), EnDXFClasses.WIPEOUTVARIABLES.getCode281());
		classValues.put(EnDXFClasses.WIPEOUTVARIABLES.getDxfRecordName(), WIPEOUTVARIABLES);
	}
}
