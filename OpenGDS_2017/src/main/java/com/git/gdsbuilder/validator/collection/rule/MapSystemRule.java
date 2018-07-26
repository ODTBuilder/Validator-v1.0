package com.git.gdsbuilder.validator.collection.rule;

public class MapSystemRule {
	
	private int bottom = 0;
	private int top = 0;
	private int left = 0;
	private int right = 0;
	
	public MapSystemRule(int top, int bottom, int left, int right) {
		// TODO Auto-generated constructor stub
		this.bottom = bottom;
		this.top = top;
		this.left = left;
		this.right = right;
	}
	
	public enum MapSystemRuleType {
		BOTTOM("bottom"),
		LEFT("left"),
		RIGHT("right"),
		TOP("top"),
		UNKNOWN(null);

		private final String typeName;

		private MapSystemRuleType(String typeName) {
			this.typeName = typeName;
		}

		public static MapSystemRuleType get(String typeName) {
			for (MapSystemRuleType type : values()) {
				if(type == UNKNOWN)
					continue;
				if(type.typeName.equals(typeName))
					return type;
			}
			return UNKNOWN;
		}
		public String getTypeName(){
			return this.typeName;
		}
	};
	
	public int getMapSystemlRule(MapSystemRuleType ruleType) {
		int value = 0;
		if (ruleType != null) {
			if (ruleType == MapSystemRuleType.BOTTOM) {
				value = this.bottom;
			} else if (ruleType == MapSystemRuleType.LEFT) {
				value = this.left;
			} else if (ruleType == MapSystemRuleType.RIGHT) {
				value = this.right;
			} else if (ruleType == MapSystemRuleType.TOP) {
				value = this.top;
			}
		}
		return value;
	}
}
