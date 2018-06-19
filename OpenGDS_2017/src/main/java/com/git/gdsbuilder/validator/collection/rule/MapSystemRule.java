package com.git.gdsbuilder.validator.collection.rule;

/**
 * 인접 검수 영역 Index Rule
 * 
 * @author DY.Oh
 *
 */
public class MapSystemRule {

	/**
	 * 인접 검수 영역 중 하위 Index
	 */
	private int bottom = 0;
	/**
	 * 인접 검수 영역 중 상위 Index
	 */
	private int top = 0;
	/**
	 * 인접 검수 영역 중 좌측 Index
	 */
	private int left = 0;
	/**
	 * 인접 검수 영역 중 우측 Index
	 */
	private int right = 0;

	/**
	 * MapSystemRule 생성자
	 * 
	 * @param top
	 *            인접 검수 영역 중 상위 Index
	 * @param bottom
	 *            인접 검수 영역 중 하위 Index
	 * @param left
	 *            인접 검수 영역 중 좌측 Index
	 * @param right
	 *            인접 검수 영역 중 우측 Index
	 */
	public MapSystemRule(int top, int bottom, int left, int right) {
		// TODO Auto-generated constructor stub
		this.bottom = bottom;
		this.top = top;
		this.left = left;
		this.right = right;
	}

	public enum MapSystemRuleType {
		BOTTOM("bottom"), LEFT("left"), RIGHT("right"), TOP("top"), UNKNOWN(null);

		private final String typeName;

		private MapSystemRuleType(String typeName) {
			this.typeName = typeName;
		}

		public static MapSystemRuleType get(String typeName) {
			for (MapSystemRuleType type : values()) {
				if (type == UNKNOWN)
					continue;
				if (type.typeName.equals(typeName))
					return type;
			}
			return UNKNOWN;
		}

		public String getTypeName() {
			return this.typeName;
		}
	};

	/**
	 * 인접 검수 영역 Index Rule 타입에 해당하는 Index 값 반환
	 * 
	 * @param ruleType
	 *            인접 검수 영역 Index Rule
	 * @return int
	 */
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
