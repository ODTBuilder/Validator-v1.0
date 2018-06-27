/**
 * 임베드 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 07.26
 * @version 0.01
 * @class gb.embed.Base
 * @constructor
 * 
 */
var gb;
if (!gb)
	gb = {};
if (!gb.embed)
	gb.embed = {};
gb.embed.OptionDefinition = function(obj) {
	var that = this;
	this.optItem = {
		"BorderLayer" : {
			"title" : "도곽선 설정",
			"alias" : "BorderLayer",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline" ],
			"purpose" : "none",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}

			}
		},
		"Dissolve" : {
			"title" : "인접 속성 병합 오류",
			"alias" : "Dissolve",
			"category" : [ "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "attribute",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : true,
				"key" : true,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"FEntityInHole" : {
			"title" : "홀 존재 오류(임상도)",
			"alias" : "FEntityInHole",
			"category" : [ "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : true,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"TwistedPolygon" : {
			"title" : "꼬인 객체 오류",
			"alias" : "TwistedPolygon",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : true,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"RefAttributeMiss" : {
			"title" : "인접 요소 속성 오류",
			"alias" : "RefAttributeMiss",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "adjacent",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : true,
				"key" : true,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"NodeMiss" : {
			"title" : "노드 오류",
			"alias" : "NodeMiss",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"DRefEntityNone" : {
			"title" : "인접 요소 부재 오류",
			"alias" : "RefEntityNone",
			"category" : [ "numetrical" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "adjacent",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"FRefEntityNone" : {
			"title" : "인접 요소 부재 오류",
			"alias" : "RefEntityNone",
			"category" : [ "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "adjacent",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"EntityDuplicated" : {
			"title" : "요소 중복 오류",
			"alias" : "EntityDuplicated",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : true,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"SelfEntity" : {
			"title" : "단독 존재 오류",
			"alias" : "SelfEntity",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"OutBoundary" : {
			"title" : "경계 초과 오류",
			"alias" : "OutBoundary",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"PointDuplicated" : {
			"title" : "중복점 오류",
			"alias" : "PointDuplicated",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : true,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"SmallLength" : {
			"title" : "허용 범위 이하 길이",
			"alias" : "SmallLength",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"SmallArea" : {
			"title" : "허용 범위 이하 면적",
			"alias" : "SmallArea",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"ConIntersected" : {
			"title" : "등고선 교차 오류",
			"alias" : "ConIntersected",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : true,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"ConOverDegree" : {
			"title" : "등고선 꺾임 오류",
			"alias" : "ConOverDegree",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"ConBreak" : {
			"title" : "등고선 끊김 오류",
			"alias" : "ConBreak",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"ZValueAmbiguous" : {
			"title" : "고도값 오류",
			"alias" : "ZValueAmbiguous",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "attribute",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : true,
				"key" : true,
				"values" : false,
				"number" : true,
				"condition" : true,
				"interval" : true
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"UselessPoint" : {
			"title" : "등고선 직선화 미처리",
			"alias" : "UselessPoint",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : true,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"OverShoot" : {
			"title" : "기준점 초과",
			"alias" : "OverShoot",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"UselessEntity" : {
			"title" : "불확실한 요소 사용 오류",
			"alias" : "UselessEntity",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : true,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"EntityOpenMiss" : {
			"title" : "객체 폐합 오류",
			"alias" : "EntityOpenMiss",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"OneAcre" : {
			"title" : "지류계 오류",
			"alias" : "OneAcre",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"OneStage" : {
			"title" : "경지계 오류",
			"alias" : "OneStage",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"SymbolOut" : {
			"title" : "객체 포함 오류",
			"alias" : "SymbolOut",
			"category" : [ "numetrical" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"BuildingSiteMiss" : {
			"title" : "건물 부지 오류",
			"alias" : "BuildingSiteMiss",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : true,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"BoundaryMiss" : {
			"title" : "경계 누락 오류",
			"alias" : "BoundaryMiss",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"CenterLineMiss" : {
			"title" : "중심선 누락 오류",
			"alias" : "CenterLineMiss",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"EntityInHole" : {
			"title" : "홀 중복 오류",
			"alias" : "EntityInHole",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"HoleMisplacement" : {
			"title" : "홀 존재 오류",
			"alias" : "HoleMisplacement",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : true,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"LinearDisconnection" : {
			"title" : "선형 단락 오류",
			"alias" : "LinearDisconnection",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"MultiPart" : {
			"title" : "멀티 파트 오류",
			"alias" : "MultiPart",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : true,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"BridgeName" : {
			"title" : "교량명 오류",
			"alias" : "BridgeName",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "attribute",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : true,
				"key" : true,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : true,
					"key" : true,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"AdminMiss" : {
			"title" : "행정 경계 오류",
			"alias" : "AdminMiss",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "attribute",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : true,
				"key" : true,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"NumericalValue" : {
			"title" : "수치값 오류",
			"alias" : "NumericalValue",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "attribute",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : true,
				"key" : true,
				"values" : false,
				"number" : true,
				"condition" : true,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"UFIDMiss" : {
			"title" : "UFID 오류",
			"alias" : "UFIDMiss",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "attribute",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : true,
				"key" : true,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"RefZValueMiss" : {
			"title" : "인접 요소 고도값 오류",
			"alias" : "RefZValueMiss",
			"category" : [ "numetrical", "underground", "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "adjacent",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : true,
				"key" : true,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : true,
				"value" : true,
				"condition" : true,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"UAvrgDPH10" : {
			"title" : "평균 심도 오류(정위치)",
			"alias" : "UAvrgDPH10",
			"category" : [ "underground" ],
			"version" : [ "qa1" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : true,
					"value" : true,
					"condition" : true,
					"interval" : true
				}
			}
		},
		"UAvrgDPH20" : {
			"title" : "평균 심도 오류(구조화)",
			"alias" : "UAvrgDPH20",
			"category" : [ "underground" ],
			"version" : [ "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "attribute",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : true,
				"values" : true
			},
			"figure" : {
				"code" : false,
				"key" : true,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : true,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"ULeaderline" : {
			"title" : "지시선 교차 오류",
			"alias" : "ULeaderline",
			"category" : [ "underground" ],
			"version" : [ "qa1" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"UNodeMiss" : {
			"title" : "시설물 선형 노드 오류",
			"alias" : "UNodeMiss",
			"category" : [ "underground" ],
			"version" : [ "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"USymbolDirection" : {
			"title" : "시설물 심볼 방향 오류",
			"alias" : "USymbolDirection",
			"category" : [ "underground" ],
			"version" : [ "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "attribute",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : true,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"USymbolInLine" : {
			"title" : "선형내 심볼 미존재 오류",
			"alias" : "USymbolInLine",
			"category" : [ "underground" ],
			"version" : [ "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : true,
				"values" : true
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"ULineCross" : {
			"title" : "관로 상하월 오류",
			"alias" : "ULineCross",
			"category" : [ "underground" ],
			"version" : [ "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : true,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"USymbolsDistance" : {
			"title" : "심볼 간격 오류",
			"alias" : "USymbolsDistance",
			"category" : [ "underground" ],
			"version" : [ "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : true,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"USymbolOut" : {
			"title" : "심볼 단독 존재 오류",
			"alias" : "USymbolOut",
			"category" : [ "underground" ],
			"version" : [ "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "graphic",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : false,
				"key" : false,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : true,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"FCodeLogicalAttribute" : {
			"title" : "F Code 오류",
			"alias" : "FCodeLogicalAttribute",
			"category" : [ "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "attribute",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : true,
				"key" : true,
				"values" : false,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"name" : false,
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		},
		"FLabelLogicalAttribute" : {
			"title" : "Label 오류",
			"alias" : "FLabelLogicalAttribute",
			"category" : [ "forest" ],
			"version" : [ "qa1", "qa2" ],
			"geometry" : [ "point", "multipoint", "linestring", "multilinestring", "polygon", "multipolygon", "polyline", "lwpolyline",
					"text", "insert" ],
			"purpose" : "attribute",
			"noparam" : false,
			"filter" : {
				"code" : false,
				"key" : false,
				"values" : false
			},
			"figure" : {
				"code" : true,
				"key" : true,
				"values" : true,
				"number" : false,
				"condition" : false,
				"interval" : false
			},
			"tolerance" : {
				"code" : false,
				"value" : false,
				"condition" : false,
				"interval" : false
			},
			"relation" : {
				"filter" : {
					"code" : false,
					"key" : false,
					"values" : false
				},
				"figure" : {
					"code" : false,
					"key" : false,
					"values" : false,
					"number" : false,
					"condition" : false,
					"interval" : false
				},
				"tolerance" : {
					"code" : false,
					"value" : false,
					"condition" : false,
					"interval" : false
				}
			}
		}
	}

	this.structure = {
		"border" : null,
		"definition" : []
	};

	// 수치지도, 지하시설물, 임상도
	// nm, ug, fr
	this.qaCat = undefined;

	// 정위치, 구조화
	// qa1, qa2
	this.qaVer = undefined;

	var options = obj ? obj : {};
	this.layerDef = options.layerDefinition ? options.layerDefinition : undefined;

	this.navi = $("<ol>").addClass("breadcrumb").addClass("gb-optiondefinition-navigation");
	this.message = $("<div>").addClass("alert").addClass("alert-info").attr("role", "alert");
	this.optionArea = $("<div>").addClass("well");
	this.panelBody = $("<div>").addClass("gb-optiondefinition-body").append(this.navi).append(this.message).append(this.optionArea);
	this.panel = $("<div>").append(this.panelBody);
	if (typeof options.append === "string") {
		$(options.append).append(this.panel);
	}
	// 현재 분류 등고선
	this.nowCategory = undefined;
	// 현재 검수 항목 단독 존재 오류
	this.nowOption = undefined;
	// 현재 세부 옵션 종류 필터 피규어 톨러런스 릴레이션
	this.nowDetailCategory = undefined;
	// 세부 옵션이 릴레이션일때 릴레이션 분류
	this.nowRelationCategory = undefined;
	// 세부 옵션이 릴레이션을때 릴레이션 세부 옵션 종류
	this.nowRelationDetailCategory = undefined;

	this.msg = typeof options.msgClass === "string" ? options.msgClass : undefined;
	this.fileParent = undefined;
	this.fileClass = undefined;
	this.file = undefined;
	if (typeof options.fileClass === "string") {
		this.file = $("." + options.fileClass)[0];
		this.fileClass = $(this.file).attr("class");
		var jclass = "." + this.fileClass;
		this.fileParent = $(jclass).parent();
		$(this.fileParent).on("change", jclass, function(event) {
			var fileList = that.file.files;
			var reader = new FileReader();
			if (fileList.length === 0) {
				return;
			}
			reader.readAsText(fileList[0]);
			$(reader).on("load", function(event) {
				var obj = JSON.parse(reader.result.replace(/(\s*)/g, ''));
				that.setStructure(obj);
				that.updateStructure();
			});
			$(that.file).remove();
			that.file = $("<input>").attr({
				"type" : "file"
			}).css("display", "none").addClass(that.fileClass)[0];
			$(that.fileParent).append(that.file);
		});
	}

	// 노 파라미터 모든 분류 체크 박스
	$(this.panelBody).on("change", ".gb-optiondefinition-check-noparamoption-all", function() {
		that.setNoParamOption(this, true);
		console.log(that.getStructure());
	});
	// 모든 레이어 선택
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-relationcategory-all", function() {
		that.printOptionCategory(this, false, true, true);
	});
	// 보더 셀렉트 변경시 도곽선 설정
	$(this.panelBody).on("change", ".gb-optiondefinition-select-border", function() {
		that.setBorderLayer(this);
		console.log(that.getStructure());
	});
	// 필터 추가
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-addfilter", function() {
		that.addFilterRow(this);
	});
	// 피규어 추가
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-addfigure", function() {
		that.addFigureRow(this);
	});
	// 톨러런스 추가
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-addtolerance", function() {
		that.addToleranceRow(this);
	});

	// 네비게이터 분류 클릭시 분류 표시
	$(this.panelBody).on("click", ".gb-optiondefinition-navi-category", function() {
		that.printCategory();
	});
	// 버튼 분류 클릭시 다음 단계 옵션 표시
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-category", function() {
		that.printOption(this, false);
	});

	// 네비게이터 옵션 클릭시 검수 항목 표시
	$(this.panelBody).on("click", ".gb-optiondefinition-navi-option", function() {
		that.printOption(this, true);
	});
	// 버튼 옵션 클릭시 다음 단계 세부 설정 종류 표시
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-option", function() {
		that.printOptionCategory(this, false);
	});

	// 네비게이터 세부 옵션 종류 클릭시 세부 옵션 종류 표시
	$(this.panelBody).on("click", ".gb-optiondefinition-navi-detailcategory", function() {
		that.printOptionCategory(this, true);
	});
	// 버튼 세부 옵션 종류 클릭시 다음 단계 세부 설정 입력 표시
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-detailcategory", function() {
		that.printDetailForm(this, false);
	});

	// 네비게이터 릴레이션 카테고리 클릭시 릴레이션 카테고리 표시
	$(this.panelBody).on("click", ".gb-optiondefinition-navi-relationcategory", function() {
		that.printCategory(true);
	});
	// 버튼 릴레이션 카테고리 클릭시 다음 단계 세부 설정 종류 표시
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-relationcategory", function() {
		// that.printDetailForm(this, false);
		that.printOptionCategory(this, false, true);
	});
	// 네비게이터 릴레이션 카테고리 클릭시 다음 단계 세부 설정 종류 표시
	$(this.panelBody).on("click", ".gb-optiondefinition-navi-relationdetailcategory", function() {
		that.printOptionCategory(this, true, true);
	});
	// 버튼 릴레이션 카테고리 클릭시 다음 단계 세부 설정 종류 표시
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-relationdetailcategory", function() {
		that.printDetailForm(this, false, true);
	});

	// 속성 필터 레이어 코드 추가 버튼 클릭
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-filteraddcode", function() {
		that.addLayerCodeFilter(this);
	});

	// 속성 검수 레이어 코드 추가 버튼 클릭
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-figureaddcode", function() {
		that.addLayerCodeFigure(this);
	});

	// 톨러런스 레이어 코드 추가 버튼 클릭
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-toleranceaddcode", function() {
		that.addLayerCodeTolerance(this);
	});

	// 필터 코드 입력 폼 이벤트
	$(this.panelBody).on("change", ".gb-optiondefinition-select-filtercode", function() {
		that.selectFilterCode(this);
		console.log(that.getStructure());
	});

	// 노 파라미터 체크박스 이벤트
	$(this.panelBody).on("change", ".gb-optiondefinition-check-noparamoption", function() {
		that.setNoParamOption(this);
		console.log(that.getStructure());
	});

	// 필터 속성명 입력 이벤트
	$(this.panelBody).on("input", ".gb-optiondefinition-input-filterkey", function() {
		that.inputFilterKey(this);
		console.log(that.getStructure());
	});

	// 필터 허용값 입력 이벤트
	$(this.panelBody).on("input", ".gb-optiondefinition-input-filtervalues", function() {
		that.inputFilterValues(this);
		console.log(that.getStructure());
	});

	// 필터 레이어 코드 삭제 버튼 클릭
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-deletelayerfilter", function() {
		that.deleteLayerCodeFilter(this);
		console.log(that.getStructure());
	});

	// 필터 로우 삭제 버튼 클릭
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-deletefilterrow", function() {
		that.deleteFilterRow(this);
		console.log(that.getStructure());
	});

	/*
	 * // 예제 $(this.panelBody).on("input",
	 * ".gb-optiondefinition-input-categoryname", function() {
	 * that.inputCategoryName(this); console.log(that.getStructure()); }); // 예제
	 * $(this.panelBody).on("change", ".gb-optiondefinition-select-geometry",
	 * function() { that.selectLayerGeometry(this);
	 * console.log(that.getStructure()); });
	 */

	// 속성 검수 코드 선택 이벤트
	$(this.panelBody).on("change", ".gb-optiondefinition-select-figurecode", function() {
		that.selectFigureCode(this);
		console.log(that.getStructure());
	});

	// 속성 검수 속성명 입력 이벤트
	$(this.panelBody).on("input", ".gb-optiondefinition-input-figurekey", function() {
		that.inputFigureKey(this);
		console.log(that.getStructure());
	});

	// 속성 검수 허용값 입력 이벤트
	$(this.panelBody).on("input", ".gb-optiondefinition-input-figurevalues", function() {
		that.inputFigureValues(this);
		console.log(that.getStructure());
	});

	// 속성 검수 수치값 입력 이벤트
	$(this.panelBody).on("input", ".gb-optiondefinition-input-figurenumber", function() {
		that.inputFigureNumber(this);
		console.log(that.getStructure());
	});

	// 속성 검수 조건 선택 이벤트
	$(this.panelBody).on("change", ".gb-optiondefinition-select-figurecondition", function() {
		that.selectFigureCondition(this);
		console.log(that.getStructure());
	});

	// 속성 검수 수치값 입력 이벤트
	$(this.panelBody).on("input", ".gb-optiondefinition-input-figureinterval", function() {
		that.inputFigureInterval(this);
		console.log(that.getStructure());
	});

	// 수치 조건 코드 선택 이벤트
	$(this.panelBody).on("change", ".gb-optiondefinition-select-tolerancecode", function() {
		that.selectToleranceCode(this);
		console.log(that.getStructure());
	});

	// 수치 조건 수치값 입력 이벤트
	$(this.panelBody).on("input", ".gb-optiondefinition-input-tolerancevalue", function() {
		that.inputToleranceValue(this);
		console.log(that.getStructure());
	});

	// 수치 조건 조건 선택 이벤트
	$(this.panelBody).on("change", ".gb-optiondefinition-select-tolerancecondition", function() {
		that.selectToleranceCondition(this);
		console.log(that.getStructure());
	});

	// 수치 조건 간격 입력 이벤트
	$(this.panelBody).on("input", ".gb-optiondefinition-input-toleranceinterval", function() {
		that.inputToleranceInterval(this);
		console.log(that.getStructure());
	});

	// 피규어 로우 삭제 버튼 클릭
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-deletefigurerow", function() {
		that.deleteFigureRow(this);
		console.log(that.getStructure());
	});

	// 피규어 레이어 코드 삭제 버튼 클릭
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-deletelayerfigure", function() {
		that.deleteLayerCodeFigure(this);
		console.log(that.getStructure());
	});

	// 톨러런스 레이어 코드 삭제 버튼 클릭
	$(this.panelBody).on("click", ".gb-optiondefinition-btn-deletelayertolerance", function() {
		that.deleteLayerCodeTolerance(this);
		console.log(that.getStructure());
	});

	this.init();
};
gb.embed.OptionDefinition.prototype = Object.create(gb.embed.OptionDefinition.prototype);
gb.embed.OptionDefinition.prototype.constructor = gb.embed.OptionDefinition;

gb.embed.OptionDefinition.prototype.init = function() {
	this.printCategory();
};

gb.embed.OptionDefinition.prototype.setMessagePopup = function(type, message) {
	var alert = "alert-";
	switch (type) {
	case "success":
		alert = alert + "success";
		break;
	case "info":
		alert = alert + "info";
		break;
	case "warning":
		alert = alert + "warning";
		break;
	case "danger":
		alert = alert + "danger";
		break;
	default:
		alert = alert + "info";
		break;
	}
	var span = $("<span>").attr("aria-hidden", "true").html("&times;");
	var xbtn = $("<button>").addClass("close").attr("type", "button").attr("data-dismiss", "alert").attr("aria-label", "Close")
			.append(span);
	var head = $("<strong>").text("알림 ");
	var div = $("<div>").addClass("alert").addClass(alert).addClass("alert-dismissible").attr("role", "alert").append(xbtn).append(head)
			.append(message);
	var jclass = "." + this.msg;
	$(jclass).append(div);
};

gb.embed.OptionDefinition.prototype.deleteLayerCodeTolerance = function(btn) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 레이어 엘리먼트
	var layerElem = $(btn).parents().eq(2);
	// 레이어 코드 인덱스
	var layerIdx = $(layerElem).index();

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							if (sec) {
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var j = 0; j < rel.length; j++) {
											if (rel[j]["name"] === this.nowRelationCategory) {
												if (rel[j].hasOwnProperty("tolerance")) {
													if (Array.isArray(rel[j]["tolerance"])) {
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["tolerance"]
																.splice(layerIdx, 1);
														if (strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["tolerance"].length === 0) {
															delete strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["tolerance"];
														}
														var types = Object
																.keys(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]);
														if (types.length === 1) {
															strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"]
																	.splice(j, 1);
															if (strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].length === 0) {
																delete strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
															}
														}
													}
												}
											}
										}
									}
								}
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("tolerance")) {
									var filter = strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"];
									if (Array.isArray(filter)) {
										strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"].splice(layerIdx, 1);
										// $(layerElem).remove();
										if (strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"].length === 0) {
											delete strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"];
										}
									}
								}
							}
							var optionKeys = Object.keys(strc["definition"][i]["options"][type3][this.nowOption.alias]);
							if (optionKeys.length === 0) {
								delete strc["definition"][i]["options"][type3][this.nowOption.alias];
								var typeKeys = Object.keys(strc["definition"][i]["options"][type3]);
								if (typeKeys.length === 0) {
									delete strc["definition"][i]["options"][type3];
									var keys = Object.keys(strc["definition"][i]["options"]);
									if (keys.length === 0) {
										strc["definition"].splice(layerIdx, 1);
									}
								}
							}
						}
					}
				}
			}
		}
		$(layerElem).remove();
	}
};

gb.embed.OptionDefinition.prototype.deleteLayerCodeFigure = function(btn) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 레이어 엘리먼트
	var layerElem = $(btn).parents().eq(2);
	// 레이어 코드 인덱스
	var layerIdx = $(layerElem).index();

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							if (sec) {
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var j = 0; j < rel.length; j++) {
											if (rel[j]["name"] === this.nowRelationCategory) {
												if (rel[j].hasOwnProperty("figure")) {
													if (Array.isArray(rel[j]["figure"])) {
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["figure"]
																.splice(layerIdx, 1);
														if (strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["figure"].length === 0) {
															delete strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["figure"];
														}
														var types = Object
																.keys(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]);
														if (types.length === 1) {
															strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"]
																	.splice(j, 1);
															if (strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].length === 0) {
																delete strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
															}
														}
													}
												}
											}
										}
									}
								}
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("figure")) {
									var filter = strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"];
									if (Array.isArray(filter)) {
										strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"].splice(layerIdx, 1);
										// $(layerElem).remove();
										if (strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"].length === 0) {
											delete strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"];
										}
									}
								}
							}
							var optionKeys = Object.keys(strc["definition"][i]["options"][type3][this.nowOption.alias]);
							if (optionKeys.length === 0) {
								delete strc["definition"][i]["options"][type3][this.nowOption.alias];
								var typeKeys = Object.keys(strc["definition"][i]["options"][type3]);
								if (typeKeys.length === 0) {
									delete strc["definition"][i]["options"][type3];
									var keys = Object.keys(strc["definition"][i]["options"]);
									if (keys.length === 0) {
										strc["definition"].splice(layerIdx, 1);
									}
								}
							}
						}
					}
				}
			}
		}
		$(layerElem).remove();
	}
};

gb.embed.OptionDefinition.prototype.deleteFigureRow = function(btn) {
	// 필터 엘리먼트
	var filterElem = $(btn).parents().eq(2);
	// 필터 인덱스
	var filterIdx = $(filterElem).index();
	// 레이어 인덱스
	var layerIdx = $(btn).parents().eq(5).index();

	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							if (sec) {
								// relation 키가 설정되어 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
									// relation이 배열인지?
									if (Array.isArray(rel)) {
										for (var j = 0; j < rel.length; j++) {
											if (rel[j]["name"] === this.nowRelationCategory) {
												if (rel[j].hasOwnProperty("figure")) {
													if (Array.isArray(rel[j]["figure"])) {
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["figure"][layerIdx]["attribute"]
																.splice(filterIdx, 1);
													}
												}
											}
										}
									}
								}
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("figure")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"])) {
										// filter키가 배열형태임
										var attr = strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"];
										if (Array.isArray(attr)) {
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"]
													.splice(filterIdx, 1);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		$(filterElem).remove();
	}
};

gb.embed.OptionDefinition.prototype.inputToleranceInterval = function(inp) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 필터 인덱스
	var filterIdx = $(inp).parents().eq(1).index();
	// 레이어 인덱스
	var layerIdx = $(inp).parents().eq(4).index();
	// 레이어 코드
	var layerCode = null;
	if (!$(inp).parents().eq(4).find(".gb-optiondefinition-select-tolerancecode").prop("disabled")) {
		layerCode = $(inp).parents().eq(4).find(".gb-optiondefinition-select-tolerancecode option").filter(":selected").attr("geom") === "none" ? null
				: $(inp).parents().eq(4).find(".gb-optiondefinition-select-tolerancecode").val();
	}
	// 수치
	var number = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-input-tolerancevalue").prop("disabled")) {
		var temp = parseFloat($(inp).parents().eq(1).find(".gb-optiondefinition-input-tolerancevalue").val());
		if (!isNaN(temp)) {
			number = parseFloat($(inp).parents().eq(1).find(".gb-optiondefinition-input-tolerancevalue").val());
		}
	}
	// 조건
	var condition = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-select-tolerancecondition").prop("disabled")) {
		condition = $(inp).parents().eq(1).find(".gb-optiondefinition-select-tolerancecondition").val();
	}
	// 간격
	var interval = null;
	if (!$(inp).prop("disabled")) {
		interval = parseFloat($(inp).val());
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							// 현재 입력한 값이 릴레이션 필터 값인지?
							if (sec) {
								// 현재 옵션에 릴레이션 키가 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									// 있다면
									// 배열인지?
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var isExist = false;
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var a = 0; a < rel.length; a++) {
											// 필터키 안에 분류 이름이 현재 릴레이션 분류 이름과 같다면
											if (rel[a]["name"] === this.nowRelationCategory) {
												isExist = true;
												var filterKey;
												if (rel[a].hasOwnProperty("tolerance")) {
													filterKey = rel[a]["tolerance"];
													// 톨러런스 키가 배열인지?
													if (Array.isArray(filterKey)) {
														// 필터 배열 원소에 attribute
														// 키가 있는지?
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["tolerance"][layerIdx] = {
															"code" : layerCode,
															"value" : number !== "" ? number : null,
															"condition" : typeof condition === "string" ? condition : null,
															"interval" : isNaN(interval) ? null : interval
														};
													} else {
														// 톨러런스 키가 배열이 아니라면
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["tolerance"] = [ {
															"code" : layerCode,
															"value" : number !== "" ? number : null,
															"condition" : typeof condition === "string" ? condition : null,
															"interval" : isNaN(interval) ? null : interval
														} ];
													}
												}
											}
										}
										if (!isExist) {
											var codeElem = {
												"code" : layerCode,
												"value" : number !== "" ? number : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											var nameElem = {
												"name" : this.nowRelationCategory,
												"tolerance" : [ codeElem ]
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
										}
									} else {
										// 배열이 아니라면
										var codeElem = {
											"code" : layerCode,
											"value" : number !== "" ? number : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										};
										var nameElem = {
											"name" : this.nowRelationCategory,
											"tolerance" : [ codeElem ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
									}
								} else {
									// 현재 옵션에 릴레이션 키가
									// 없다면
									var codeElem = {
										"code" : layerCode,
										"value" : number !== "" ? number : null,
										"condition" : typeof condition === "string" ? condition : null,
										"interval" : isNaN(interval) ? null : interval
									};
									var nameElem = {
										"name" : this.nowRelationCategory,
										"tolerance" : [ codeElem ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
								}
								// 여기까지 릴레이션
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("tolerance")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"])) {
										// tolerance키가 배열형태임
										strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"][layerIdx] = {
											"code" : layerCode,
											"value" : number !== "" ? number : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										};
									} else {
										// filter키가 배열형태가 아님
										// 허용값이 입력되어있다면 값 변경 / 값은 위에 변수에 할당되어있음
										var obj = {
											"code" : layerCode,
											"value" : number !== "" ? number : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"] = [ obj ];
									}
								} else {
									// filter 키가 설정되어있지 않음
									// 허용값이 입력되어있다면 값 변경
									var obj = {
										"code" : layerCode,
										"value" : number !== "" ? number : null,
										"condition" : typeof condition === "string" ? condition : null,
										"interval" : isNaN(interval) ? null : interval
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"] = [ obj ];
								}
							}
						} else {
							// 해당 검수항목이 설정되어 있지 않음
							// 릴레이션임
							if (sec) {
								// 없다면
								var codeElem = {
									"code" : layerCode,
									"value" : number !== "" ? number : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								};
								var nameElem = {
									"name" : this.nowRelationCategory,
									"tolerance" : [ codeElem ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
								strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
							} else {
								// 해당 검수 항목이 설정되어 있지 않음
								var obj = {
									"code" : layerCode,
									"value" : number !== "" ? number : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								};
								var optionObj = {
									"tolerance" : [ obj ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = optionObj;
							}
						}
					} else {
						// 해당 검수 타입이 설정되어 있지 않음
						if (sec) {
							var codeElem = {
								"code" : layerCode,
								"value" : number !== "" ? number : null,
								"condition" : typeof condition === "string" ? condition : null,
								"interval" : isNaN(interval) ? null : interval
							};
							var nameElem = {
								"name" : this.nowRelationCategory,
								"tolerance" : [ codeElem ]
							};
							strc["definition"][i]["options"][type3] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];

							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
						} else {
							var obj = {
								"code" : layerCode,
								"value" : number !== "" ? number : null,
								"condition" : typeof condition === "string" ? condition : null,
								"interval" : isNaN(interval) ? null : interval
							};
							var optionObj = {
								"tolerance" : [ obj ]
							};
							var typeObj = {};
							typeObj[this.nowOption.alias] = optionObj;
							strc["definition"][i]["options"][type3] = typeObj;
						}
					}
				}
			}
		}
		// 정의에 해당 분류가 없음
		if (!isExist) {
			// 릴레이션 설정임
			if (sec) {
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"value" : number !== "" ? number : null,
					"condition" : typeof condition === "string" ? condition : null,
					"interval" : isNaN(interval) ? null : interval
				};
				var optionsObj = [ {
					"name" : this.nowRelationCategory,
					"tolerance" : [ obj ]
				} ];

				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = {};
				typeObj[type3][this.nowOption.alias]["relation"] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			} else {
				// 릴레이션 설정 아님
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"value" : number !== "" ? number : null,
					"condition" : typeof condition === "string" ? condition : null,
					"interval" : isNaN(interval) ? null : interval
				};
				var optionsObj = {
					"tolerance" : [ obj ]
				};
				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			}
		}
	}
};

gb.embed.OptionDefinition.prototype.selectToleranceCondition = function(sel) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 필터 인덱스
	var filterIdx = $(sel).parents().eq(1).index();
	// 레이어 인덱스
	var layerIdx = $(sel).parents().eq(4).index();
	// 레이어 코드
	var layerCode = null;
	if (!$(sel).parents().eq(4).find(".gb-optiondefinition-select-tolerancecode").prop("disabled")) {
		layerCode = $(inp).parents().eq(4).find(".gb-optiondefinition-select-tolerancecode option").filter(":selected").attr("geom") === "none" ? null
				: $(inp).parents().eq(4).find(".gb-optiondefinition-select-tolerancecode").val();
	}
	// 수치
	var number = null;
	if (!$(sel).parents().eq(1).find(".gb-optiondefinition-input-tolerancevalue").prop("disabled")) {
		var temp = parseFloat($(sel).parents().eq(1).find(".gb-optiondefinition-input-tolerancevalue").val());
		if (!isNaN(temp)) {
			number = parseFloat($(sel).parents().eq(1).find(".gb-optiondefinition-input-tolerancevalue").val());
		}
	}
	// 조건
	var condition = $(sel).val();
	if (!$(sel).prop("disabled")) {
		condition = $(sel).val();
	}
	// 간격
	var interval = parseFloat($(sel).parents().eq(1).find(".gb-optiondefinition-input-toleranceinterval").val());
	if (!$(sel).parents().eq(1).find(".gb-optiondefinition-input-toleranceinterval").prop("disabled")) {
		interval = parseFloat($(sel).parents().eq(1).find(".gb-optiondefinition-input-toleranceinterval").val());
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							// 현재 입력한 값이 릴레이션 필터 값인지?
							if (sec) {
								// 현재 옵션에 릴레이션 키가 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									// 있다면
									// 배열인지?
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var isExist = false;
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var a = 0; a < rel.length; a++) {
											// 필터키 안에 분류 이름이 현재 릴레이션 분류 이름과 같다면
											if (rel[a]["name"] === this.nowRelationCategory) {
												isExist = true;
												var filterKey;
												if (rel[a].hasOwnProperty("tolerance")) {
													filterKey = rel[a]["tolerance"];
													// 톨러런스 키가 배열인지?
													if (Array.isArray(filterKey)) {
														// 필터 배열 원소에 attribute
														// 키가 있는지?
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["tolerance"][layerIdx] = {
															"code" : layerCode,
															"value" : number !== "" ? number : null,
															"condition" : typeof condition === "string" ? condition : null,
															"interval" : isNaN(interval) ? null : interval
														};
													} else {
														// 톨러런스 키가 배열이 아니라면
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["tolerance"] = [ {
															"code" : layerCode,
															"value" : number !== "" ? number : null,
															"condition" : typeof condition === "string" ? condition : null,
															"interval" : isNaN(interval) ? null : interval
														} ];
													}
												}
											}
										}
										if (!isExist) {
											var codeElem = {
												"code" : layerCode,
												"value" : number !== "" ? number : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											var nameElem = {
												"name" : this.nowRelationCategory,
												"tolerance" : [ codeElem ]
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
										}
									} else {
										// 배열이 아니라면
										var codeElem = {
											"code" : layerCode,
											"value" : number !== "" ? number : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										};
										var nameElem = {
											"name" : this.nowRelationCategory,
											"tolerance" : [ codeElem ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
									}
								} else {
									// 현재 옵션에 릴레이션 키가
									// 없다면
									var codeElem = {
										"code" : layerCode,
										"value" : number !== "" ? number : null,
										"condition" : typeof condition === "string" ? condition : null,
										"interval" : isNaN(interval) ? null : interval
									};
									var nameElem = {
										"name" : this.nowRelationCategory,
										"tolerance" : [ codeElem ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
								}
								// 여기까지 릴레이션
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("tolerance")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"])) {
										// tolerance키가 배열형태임
										strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"][layerIdx] = {
											"code" : layerCode,
											"value" : number !== "" ? number : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										};
									} else {
										// filter키가 배열형태가 아님
										// 허용값이 입력되어있다면 값 변경 / 값은 위에 변수에 할당되어있음
										var obj = {
											"code" : layerCode,
											"value" : number !== "" ? number : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"] = [ obj ];
									}
								} else {
									// filter 키가 설정되어있지 않음
									// 허용값이 입력되어있다면 값 변경
									var obj = {
										"code" : layerCode,
										"value" : number !== "" ? number : null,
										"condition" : typeof condition === "string" ? condition : null,
										"interval" : isNaN(interval) ? null : interval
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"] = [ obj ];
								}
							}
						} else {
							// 해당 검수항목이 설정되어 있지 않음
							// 릴레이션임
							if (sec) {
								// 없다면
								var codeElem = {
									"code" : layerCode,
									"value" : number !== "" ? number : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								};
								var nameElem = {
									"name" : this.nowRelationCategory,
									"tolerance" : [ codeElem ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
								strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
							} else {
								// 해당 검수 항목이 설정되어 있지 않음
								var obj = {
									"code" : layerCode,
									"value" : number !== "" ? number : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								};
								var optionObj = {
									"tolerance" : [ obj ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = optionObj;
							}
						}
					} else {
						// 해당 검수 타입이 설정되어 있지 않음
						if (sec) {
							var codeElem = {
								"code" : layerCode,
								"value" : number !== "" ? number : null,
								"condition" : typeof condition === "string" ? condition : null,
								"interval" : isNaN(interval) ? null : interval
							};
							var nameElem = {
								"name" : this.nowRelationCategory,
								"tolerance" : [ codeElem ]
							};
							strc["definition"][i]["options"][type3] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];

							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
						} else {
							var obj = {
								"code" : layerCode,
								"value" : number !== "" ? number : null,
								"condition" : typeof condition === "string" ? condition : null,
								"interval" : isNaN(interval) ? null : interval
							};
							var optionObj = {
								"tolerance" : [ obj ]
							};
							var typeObj = {};
							typeObj[this.nowOption.alias] = optionObj;
							strc["definition"][i]["options"][type3] = typeObj;
						}
					}
				}
			}
		}
		// 정의에 해당 분류가 없음
		if (!isExist) {
			// 릴레이션 설정임
			if (sec) {
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"value" : number !== "" ? number : null,
					"condition" : typeof condition === "string" ? condition : null,
					"interval" : isNaN(interval) ? null : interval
				};
				var optionsObj = [ {
					"name" : this.nowRelationCategory,
					"tolerance" : [ obj ]
				} ];

				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = {};
				typeObj[type3][this.nowOption.alias]["relation"] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			} else {
				// 릴레이션 설정 아님
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"value" : number !== "" ? number : null,
					"condition" : typeof condition === "string" ? condition : null,
					"interval" : isNaN(interval) ? null : interval
				};
				var optionsObj = {
					"tolerance" : [ obj ]
				};
				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			}
		}
	}
};

gb.embed.OptionDefinition.prototype.inputToleranceValue = function(inp) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 필터 인덱스
	var filterIdx = $(inp).parents().eq(1).index();
	// 레이어 인덱스
	var layerIdx = $(inp).parents().eq(4).index();
	// 레이어 코드
	var layerCode = null;
	if (!$(inp).parents().eq(4).find(".gb-optiondefinition-select-tolerancecode").prop("disabled")) {
		layerCode = $(inp).parents().eq(4).find(".gb-optiondefinition-select-tolerancecode option").filter(":selected").attr("geom") === "none" ? null
				: $(inp).parents().eq(4).find(".gb-optiondefinition-select-tolerancecode").val();
	}
	// 수치
	var number = parseFloat($(inp).val());
	if (!$(inp).prop("disabled")) {
		number = parseFloat($(inp).val());
	}
	// 조건
	var condition = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-select-tolerancecondition").prop("disabled")) {
		condition = $(inp).parents().eq(1).find(".gb-optiondefinition-select-tolerancecondition").val();
	}
	// 간격
	var interval = parseFloat($(inp).parents().eq(1).find(".gb-optiondefinition-input-toleranceinterval").val());
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-input-toleranceinterval").prop("disabled")) {
		interval = parseFloat($(inp).parents().eq(1).find(".gb-optiondefinition-input-toleranceinterval").val());
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							// 현재 입력한 값이 릴레이션 필터 값인지?
							if (sec) {
								// 현재 옵션에 릴레이션 키가 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									// 있다면
									// 배열인지?
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var isExist2 = false;
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var a = 0; a < rel.length; a++) {
											// 필터키 안에 분류 이름이 현재 릴레이션 분류 이름과 같다면
											if (rel[a]["name"] === this.nowRelationCategory) {
												isExist2 = true;
												var filterKey;
												if (rel[a].hasOwnProperty("tolerance")) {
													filterKey = rel[a]["tolerance"];
													// 톨러런스 키가 배열인지?
													if (Array.isArray(filterKey)) {
														// 필터 배열 원소에 attribute
														// 키가 있는지?
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["tolerance"][layerIdx] = {
															"code" : layerCode,
															"value" : number !== "" ? number : null,
															"condition" : typeof condition === "string" ? condition : null,
															"interval" : isNaN(interval) ? null : interval
														};
													} else {
														// 톨러런스 키가 배열이 아니라면
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["tolerance"] = [ {
															"code" : layerCode,
															"value" : number !== "" ? number : null,
															"condition" : typeof condition === "string" ? condition : null,
															"interval" : isNaN(interval) ? null : interval
														} ];
													}
												}
											}
										}
										if (!isExist2) {
											var codeElem = {
												"code" : layerCode,
												"value" : number !== "" ? number : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											var nameElem = {
												"name" : this.nowRelationCategory,
												"tolerance" : [ codeElem ]
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
										}
									} else {
										// 배열이 아니라면
										var codeElem = {
											"code" : layerCode,
											"value" : number !== "" ? number : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										};
										var nameElem = {
											"name" : this.nowRelationCategory,
											"tolerance" : [ codeElem ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
									}
								} else {
									// 현재 옵션에 릴레이션 키가
									// 없다면
									var codeElem = {
										"code" : layerCode,
										"value" : number !== "" ? number : null,
										"condition" : typeof condition === "string" ? condition : null,
										"interval" : isNaN(interval) ? null : interval
									};
									var nameElem = {
										"name" : this.nowRelationCategory,
										"tolerance" : [ codeElem ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
								}
								// 여기까지 릴레이션
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("tolerance")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"])) {
										// tolerance키가 배열형태임
										strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"][layerIdx] = {
											"code" : layerCode,
											"value" : number !== "" ? number : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										};
									} else {
										// filter키가 배열형태가 아님
										// 허용값이 입력되어있다면 값 변경 / 값은 위에 변수에 할당되어있음
										var obj = {
											"code" : layerCode,
											"value" : number !== "" ? number : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"] = [ obj ];
									}
								} else {
									// filter 키가 설정되어있지 않음
									// 허용값이 입력되어있다면 값 변경
									var obj = {
										"code" : layerCode,
										"value" : number !== "" ? number : null,
										"condition" : typeof condition === "string" ? condition : null,
										"interval" : isNaN(interval) ? null : interval
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"] = [ obj ];
								}
							}
						} else {
							// 해당 검수항목이 설정되어 있지 않음
							// 릴레이션임
							if (sec) {
								// 없다면
								var codeElem = {
									"code" : layerCode,
									"value" : number !== "" ? number : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								};
								var nameElem = {
									"name" : this.nowRelationCategory,
									"tolerance" : [ codeElem ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
								strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
							} else {
								// 해당 검수 항목이 설정되어 있지 않음
								var obj = {
									"code" : layerCode,
									"value" : number !== "" ? number : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								};
								var optionObj = {
									"tolerance" : [ obj ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = optionObj;
							}
						}
					} else {
						// 해당 검수 타입이 설정되어 있지 않음
						if (sec) {
							var codeElem = {
								"code" : layerCode,
								"value" : number !== "" ? number : null,
								"condition" : typeof condition === "string" ? condition : null,
								"interval" : isNaN(interval) ? null : interval
							};
							var nameElem = {
								"name" : this.nowRelationCategory,
								"tolerance" : [ codeElem ]
							};
							strc["definition"][i]["options"][type3] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];

							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
						} else {
							var obj = {
								"code" : layerCode,
								"value" : number !== "" ? number : null,
								"condition" : typeof condition === "string" ? condition : null,
								"interval" : isNaN(interval) ? null : interval
							};
							var optionObj = {
								"tolerance" : [ obj ]
							};
							var typeObj = {};
							typeObj[this.nowOption.alias] = optionObj;
							strc["definition"][i]["options"][type3] = typeObj;
						}
					}
				}
			}
		}
		// 정의에 해당 분류가 없음
		if (!isExist) {
			// 릴레이션 설정임
			if (sec) {
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"value" : number !== "" ? number : null,
					"condition" : typeof condition === "string" ? condition : null,
					"interval" : isNaN(interval) ? null : interval
				};
				var optionsObj = [ {
					"name" : this.nowRelationCategory,
					"tolerance" : [ obj ]
				} ];

				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = {};
				typeObj[type3][this.nowOption.alias]["relation"] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			} else {
				// 릴레이션 설정 아님
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"value" : number !== "" ? number : null,
					"condition" : typeof condition === "string" ? condition : null,
					"interval" : isNaN(interval) ? null : interval
				};
				var optionsObj = {
					"tolerance" : [ obj ]
				};
				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			}
		}
	}
};

gb.embed.OptionDefinition.prototype.selectToleranceCode = function(sel) {
	var nowOption = this.optItem[this.nowOption.alias];
	if (nowOption === undefined) {
		return;
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	// 레이어 인덱스
	var layerIdx = $(sel).parents().eq(2).index();
	// 레이어 코드
	var layerCode = $(sel).find("option:selected").attr("geom") === "none" ? null : $(sel).val();
	// 수치
	var number = null;
	if (!$(sel).parents().eq(2).find(".gb-optiondefinition-input-tolerancevalue").prop("disabled")) {
		var temp = parseFloat($(sel).parents().eq(2).find(".gb-optiondefinition-input-tolerancevalue").val());
		if (!isNaN(temp)) {
			number = parseFloat($(sel).parents().eq(2).find(".gb-optiondefinition-input-tolerancevalue").val());
		}
	}
	// 조건
	var condition = null;
	if (!$(sel).parents().eq(2).find(".gb-optiondefinition-select-tolerancecondition").prop("disabled")) {
		condition = $(sel).parents().eq(2).find(".gb-optiondefinition-select-tolerancecondition").val();
	}
	// 간격
	var interval = null;
	if (!$(sel).parents().eq(2).find(".gb-optiondefinition-input-toleranceinterval").prop("disabled")) {
		interval = parseFloat($(sel).parents().eq(2).find(".gb-optiondefinition-input-toleranceinterval").val());
	}
	// 검수 항목 정보
	var optItem = this.optItem[this.nowOption.alias];
	// 검수 타입
	var type3 = optItem["purpose"];
	// 현재 코드에 설정된 필터 갯수
	var figures = $(sel).parents().eq(2).find(".gb-optiondefinition-tolerancearea").children();
	// 현재 레이어코드에 필터가 있는지?
	if (figures.length > 0) {
		var strc = this.getStructure();
		if (Array.isArray(strc["definition"])) {
			var isExist = false;
			for (var i = 0; i < strc["definition"].length; i++) {
				// 현재 검수 옵션에 현재 분류가 있는지?
				if (this.nowCategory === strc["definition"][i].name) {
					isExist = true;
					// 검수 옵션에 현재 타입 키가 있는지 애트리뷰트, 그래픽..
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 현재 검수 항목이 들어있는지?
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							console.log("현재 레이어 코드:" + $(sel).val());
							// 릴레이션 레이어 필터일때
							if (sec) {
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									var relation = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
									if (Array.isArray(relation)) {
										for (var j = 0; j < relation.length; j++) {
											if (relation[j].name === this.nowRelationCategory) {
												// 필터키를 가지고 있는지?
												if (relation[j].hasOwnProperty("tolerance")) {
													if (Array.isArray(relation[j]["tolerance"])) {
														var tolerElem = relation[j]["tolerance"][layerIdx];
														if (tolerElem !== undefined) {
															if (tolerElem.hasOwnProperty("code")) {
																relation[j]["tolerance"][layerIdx]["code"] = layerCode
															}
														} else {
															relation[j]["tolerance"][layerIdx] = {
																"code" : layerCode,
																"value" : number,
																"condition" : condition,
																"interval" : interval
															};
														}
														/*
														 * for (var k = 0; k <
														 * relation[j]["tolerance"].length;
														 * k++) { // 코드 키를 가지고
														 * 있는지? if
														 * (relation[j]["tolerance"][k].hasOwnProperty("code")) {
														 * relation[j]["tolerance"][k]["code"] =
														 * layerCode; } else { //
														 * 코드 키를 가지고 있지 // 않다면 } }
														 */
													}
												} else {
													// 필터키를 가지고 있지 않다면
													relation[j]["tolerance"] = [];
													relation[j]["tolerance"][layerIdx] = {
														"code" : layerCode,
														"value" : number,
														"condition" : condition,
														"interval" : interval
													};
												}
											}
										}
									}
								}
							} else {
								// 그냥 레이어 필터일때
								// 필터 키가 들어있는지
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("tolerance")) {
									var figures = strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"];
									if (Array.isArray(figures)) {
										if (figures[layerIdx] !== undefined) {
											strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"][layerIdx] = {
												"code" : layerCode,
												"value" : number,
												"condition" : condition,
												"interval" : interval
											};
										}
									}
								}
							}
						} else {
							// 검수 항목이 없음
							if (sec) {
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {
									"relation" : []
								};
								var obj = {
									"name" : this.nowRelationCategory,
									"tolerance" : []
								};
								obj["tolerance"][layerIdx] = {
									"code" : layerCode,
									"value" : number,
									"condition" : condition,
									"interval" : interval
								}
								strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(obj);
							} else {
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {
									"tolerance" : []
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"][layerIdx] = {
									"code" : layerCode,
									"value" : number,
									"condition" : condition,
									"interval" : interval
								};
							}
						}
					} else {
						// type3이 없음
						strc["definition"][i]["options"][type3] = {};
						if (sec) {
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {
								"relation" : []
							};
							var obj = {
								"name" : this.nowRelationCategory,
								"tolerance" : []
							};
							obj["tolerance"][layerIdx] = {
								"code" : layerCode,
								"value" : number,
								"condition" : condition,
								"interval" : interval
							}
							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(obj);
						} else {
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {
								"tolerance" : []
							};
							strc["definition"][i]["options"][type3][this.nowOption.alias]["tolerance"][layerIdx] = {
								"code" : layerCode,
								"value" : number,
								"condition" : condition,
								"interval" : interval
							};
						}
					}
				}
			}
			// 검수 옵션에 현재 분류가 없음
			if (!isExist) {
				if (sec) {
					var outerObj = {
						"name" : this.nowCategory,
						"options" : {}
					};
					outerObj["options"][type3] = {};
					outerObj["options"][type3][this.nowOption.alias] = {
						"relation" : [ {
							"name" : this.nowRelationCategory,
							"tolerance" : [ {
								"code" : layerCode,
								"value" : number,
								"condition" : condition,
								"interval" : interval
							} ]
						} ]
					};
					strc["definition"].push(outerObj);
				} else {
					var outerObj = {
						"name" : this.nowCategory,
						"options" : {}
					};
					outerObj["options"][type3] = {};
					outerObj["options"][type3][this.nowOption.alias] = {
						"tolerance" : [ {
							"code" : layerCode,
							"value" : number,
							"condition" : condition,
							"interval" : interval
						} ]
					};
					strc["definition"].push(outerObj);
				}
			}
		}
	}
};

gb.embed.OptionDefinition.prototype.selectFigureCode = function(sel) {
	var nowOption = this.optItem[this.nowOption.alias];
	if (nowOption === undefined) {
		return;
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	// 레이어 인덱스
	var layerIdx = $(sel).parents().eq(2).index();
	// 레이어 코드
	var layerCode = $(sel).find("option:selected").attr("geom") === "none" ? null : $(sel).val();
	// 검수 항목 정보
	var optItem = this.optItem[this.nowOption.alias];
	// 검수 타입
	var type3 = optItem["purpose"];
	// 현재 코드에 설정된 필터 갯수
	var figures = $(sel).parents().eq(2).find(".gb-optiondefinition-figurearea").children();
	// 현재 레이어코드에 필터가 있는지?
	if (figures.length > 0) {
		var strc = this.getStructure();
		if (Array.isArray(strc["definition"])) {
			var isExist = false;
			for (var i = 0; i < strc["definition"].length; i++) {
				// 현재 검수 옵션에 현재 분류가 있는지?
				if (this.nowCategory === strc["definition"][i].name) {
					isExist = true;
					// 검수 옵션에 현재 타입 키가 있는지 애트리뷰트, 그래픽..
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 현재 검수 항목이 들어있는지?
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							console.log("현재 레이어 코드:" + $(sel).val());
							// 릴레이션 레이어 필터일때
							if (sec) {
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									var relation = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
									if (Array.isArray(relation)) {
										for (var j = 0; j < relation.length; j++) {
											if (relation[j].name === this.nowRelationCategory) {
												// 필터키를 가지고 있는지?
												if (relation[j].hasOwnProperty("figure")) {
													if (Array.isArray(relation[j]["figure"])) {
														for (var k = 0; k < relation[j]["figure"].length; k++) {
															// 코드 키를 가지고 있는지?
															if (relation[j]["figure"][k].hasOwnProperty("code")) {
																relation[j]["figure"][k]["code"] = layerCode;
															} else {
																// 코드 키를 가지고 있지
																// 않다면
															}
														}
													}
												} else {
													// 필터키를 가지고 있지 않다면
												}
											}
										}
									}
								}
							} else {
								// 그냥 레이어 필터일때
								// 필터 키가 들어있는지
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("figure")) {
									var figures = strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"];
									if (Array.isArray(figures)) {
										if (figures[layerIdx] !== undefined) {
											if (figures[layerIdx].hasOwnProperty("code")) {
												strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["code"] = layerCode;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
};

gb.embed.OptionDefinition.prototype.inputFigureInterval = function(inp) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 필터 인덱스
	var filterIdx = $(inp).parents().eq(2).index();
	// 레이어 인덱스
	var layerIdx = $(inp).parents().eq(5).index();
	// 레이어 코드
	var layerCode = null;
	if (!$(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode").prop("disabled")) {
		layerCode = $(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode option").filter(":selected").attr("geom") === "none" ? null
				: $(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode").val();
	}
	// 속성명
	var attrKey = null;
	if (!$(inp).parents().eq(2).find(".gb-optiondefinition-input-figurekey").prop("disabled")) {
		attrKey = $(inp).parents().eq(2).find(".gb-optiondefinition-input-figurekey").val().replace(/(\s*)/g, '');
	}
	// 허용값
	var attrValues = null;
	if (!$(inp).parents().eq(2).find(".gb-optiondefinition-input-figurevalues").prop("disabled")) {
		attrValues = $(inp).parents().eq(2).find(".gb-optiondefinition-input-figurevalues").val().replace(/(\s*)/g, '').split(",");
	}
	// 수치
	var number = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-input-figurenumber").prop("disabled")) {
		var temp = parseFloat($(inp).parents().eq(1).find(".gb-optiondefinition-input-figurenumber").val());
		if (!isNaN(temp)) {
			number = parseFloat($(inp).parents().eq(1).find(".gb-optiondefinition-input-figurenumber").val());
		}
	}
	// 조건
	var condition = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-select-figurecondition").prop("disabled")) {
		condition = $(inp).parents().eq(1).find(".gb-optiondefinition-select-figurecondition").val();
	}
	// 간격
	var interval = null;
	if (!$(inp).prop("disabled")) {
		interval = parseFloat($(inp).val());
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}
	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							// 현재 입력한 값이 릴레이션 필터 값인지?
							if (sec) {
								// 현재 옵션에 릴레이션 키가 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									// 있다면
									// 배열인지?
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var isExist = false;
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var a = 0; a < rel.length; a++) {
											// 필터키 안에 분류 이름이 현재 릴레이션 분류 이름과 같다면
											if (rel[a]["name"] === this.nowRelationCategory) {
												isExist = true;
												var filterKey;
												if (rel[a].hasOwnProperty("figure")) {
													filterKey = rel[a]["figure"];
													// 필터가 배열인지?
													if (Array.isArray(filterKey)) {
														var filterElem = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx];
														// 필터 배열 원소에 attribute
														// 키가 있는지?
														if (filterElem.hasOwnProperty("attribute")) {
															// attribute 키가
															// 배열인지?
															if (Array.isArray(filterElem["attribute"])) {
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																};
															} else {
																// 배열이 아닐때
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"] = [];
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																};
															}
														} else {
															// 필터 배열 원소에
															// attribute
															// 키가 없다면
															strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx] = {
																"code" : layerCode,
																"attribute" : [ {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																} ]
															};
														}
													} else {
														// 필터가 배열이 아니라면
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"] = [ {
															"code" : layerCode,
															"attribute" : [ {
																"key" : attrKey,
																"values" : Array.isArray(attrValues) ? attrValues : null,
																"number" : number !== null && number !== undefined ? number !== "" ? number
																		: null : null,
																"condition" : typeof condition === "string" ? condition : null,
																"interval" : isNaN(interval) ? null : interval
															} ]
														} ];
													}
												}
											}
										}
										if (!isExist) {
											var attrElem = [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											} ];
											var codeElem = {
												"code" : layerCode,
												"attribute" : attrElem
											};
											var nameElem = {
												"name" : this.nowRelationCategory,
												"figure" : [ codeElem ]
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
										}
									} else {
										// 배열이 아니라면
										var attrElem = [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ];
										var codeElem = {
											"code" : layerCode,
											"attribute" : attrElem
										};
										var nameElem = {
											"name" : this.nowRelationCategory,
											"figure" : [ codeElem ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
									}
								} else {
									// 현재 옵션에 릴레이션 키가
									// 없다면
									var attrElem = [ {
										"key" : attrKey,
										"values" : Array.isArray(attrValues) ? attrValues : null,
										"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
										"condition" : typeof condition === "string" ? condition : null,
										"interval" : isNaN(interval) ? null : interval
									} ];
									var codeElem = {
										"code" : layerCode,
										"attribute" : attrElem
									};
									var nameElem = {
										"name" : this.nowRelationCategory,
										"figure" : [ codeElem ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
								}
								// 여기까지 릴레이션
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("figure")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"])) {
										// filter키가 배열형태임
										if (strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx] === undefined) {
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx] = {};
										}
										strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["code"] = layerCode;
										// attribute 키가 배열 형태임
										if (Array
												.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"])) {
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"][filterIdx] = obj;
										} else {
											// attribute 키가 배열 형태가 아님
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"] = [ obj ];
										}

									} else {
										// filter키가 배열형태가 아님
										// 허용값이 입력되어있다면 값 변경 / 값은 위에 변수에 할당되어있음
										var obj = {
											"code" : layerCode,
											"attribute" : [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											} ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"] = [ obj ];
									}
								} else {
									// filter 키가 설정되어있지 않음
									// 허용값이 입력되어있다면 값 변경
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"] = [ obj ];
								}
							}
						} else {
							// 해당 검수항목이 설정되어 있지 않음
							// 릴레이션임
							if (sec) {
								// 없다면
								var attrElem = [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
									"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								} ];
								var codeElem = {
									"code" : layerCode,
									"attribute" : attrElem
								};
								var nameElem = {
									"name" : this.nowRelationCategory,
									"figure" : [ codeElem ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
								strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
							} else {
								// 해당 검수 항목이 설정되어 있지 않음
								// 허용값이 입력되어있다면 값 변경
								if (attrValues !== undefined && attrValues.length === 1 && attrValues[0] === "" ? false : true) {
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ]
									};
									var filterObj = [ obj ];
									var optionObj = {
										"figure" : filterObj
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias] = optionObj;
								}
							}
						}
					} else {
						// 해당 검수 타입이 설정되어 있지 않음
						if (sec) {
							var attrElem = [ {
								"key" : attrKey,
								"values" : Array.isArray(attrValues) ? attrValues : null,
								"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
								"condition" : typeof condition === "string" ? condition : null,
								"interval" : isNaN(interval) ? null : interval
							} ];
							var codeElem = {
								"code" : layerCode,
								"attribute" : attrElem
							};
							var nameElem = {
								"name" : this.nowRelationCategory,
								"figure" : [ codeElem ]
							};
							strc["definition"][i]["options"][type3] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];

							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
						} else {
							var obj = {
								"code" : layerCode,
								"attribute" : [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
									"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								} ]
							};
							var filterObj = [ obj ];
							var optionObj = {
								"figure" : filterObj
							};
							var typeObj = {};
							typeObj[this.nowOption.alias] = optionObj;
							strc["definition"][i]["options"][type3] = typeObj;
						}
					}
				}
			}
		}
		// 정의에 해당 분류가 없음
		if (!isExist) {
			// 릴레이션 설정임
			if (sec) {
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
						"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
						"condition" : typeof condition === "string" ? condition : null,
						"interval" : isNaN(interval) ? null : interval
					} ]
				};
				var optionsObj = [ {
					"name" : this.nowRelationCategory,
					"figure" : [ obj ]
				} ];

				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = {};
				typeObj[type3][this.nowOption.alias]["relation"] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			} else {
				// 릴레이션 설정 아님
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
						"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
						"condition" : typeof condition === "string" ? condition : null,
						"interval" : isNaN(interval) ? null : interval
					} ]
				};
				var optionsObj = {
					"figure" : [ obj ]
				};
				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			}
		}
	}
}

gb.embed.OptionDefinition.prototype.selectFigureCondition = function(sel) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 필터 인덱스
	var filterIdx = $(sel).parents().eq(2).index();
	// 레이어 인덱스
	var layerIdx = $(sel).parents().eq(5).index();
	// 레이어 코드
	var layerCode = null;
	if (!$(sel).parents().eq(5).find(".gb-optiondefinition-select-figurecode").prop("disabled")) {
		layerCode = $(sel).parents().eq(5).find(".gb-optiondefinition-select-figurecode option").filter(":selected").attr("geom") === "none" ? null
				: $(sel).parents().eq(5).find(".gb-optiondefinition-select-figurecode").val();
	}
	// 속성명
	var attrKey = null;
	if (!$(sel).parents().eq(2).find(".gb-optiondefinition-input-figurekey").prop("disabled")) {
		attrKey = $(sel).parents().eq(2).find(".gb-optiondefinition-input-figurekey").val().replace(/(\s*)/g, '');
	}
	// 허용값
	var attrValues = null;
	if (!$(sel).parents().eq(2).find(".gb-optiondefinition-input-figurevalues").prop("disabled")) {
		attrValues = $(sel).parents().eq(2).find(".gb-optiondefinition-input-figurevalues").val().replace(/(\s*)/g, '').split(",");
	}
	// 수치
	var number = null;
	if (!$(sel).parents().eq(1).find(".gb-optiondefinition-input-figurenumber").prop("disabled")) {
		var temp = parseFloat($(sel).parents().eq(1).find(".gb-optiondefinition-input-figurenumber").val());
		if (!isNaN(temp)) {
			number = parseFloat($(sel).parents().eq(1).find(".gb-optiondefinition-input-figurenumber").val());
		}
	}
	// 조건
	var condition = $(sel).val();
	if (!$(sel).prop("disabled")) {
		condition = $(sel).val();
	}
	// 간격
	var interval = parseFloat($(sel).parents().eq(1).find(".gb-optiondefinition-input-figureinterval").val());
	if (!$(sel).parents().eq(1).find(".gb-optiondefinition-input-figureinterval").prop("disabled")) {
		interval = parseFloat($(sel).parents().eq(1).find(".gb-optiondefinition-input-figureinterval").val());
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}
	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							// 현재 입력한 값이 릴레이션 필터 값인지?
							if (sec) {
								// 현재 옵션에 릴레이션 키가 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									// 있다면
									// 배열인지?
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var isExist = false;
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var a = 0; a < rel.length; a++) {
											// 필터키 안에 분류 이름이 현재 릴레이션 분류 이름과 같다면
											if (rel[a]["name"] === this.nowRelationCategory) {
												isExist = true;
												var filterKey;
												if (rel[a].hasOwnProperty("figure")) {
													filterKey = rel[a]["figure"];
													// 필터가 배열인지?
													if (Array.isArray(filterKey)) {
														var filterElem = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx];
														// 필터 배열 원소에 attribute
														// 키가 있는지?
														if (filterElem.hasOwnProperty("attribute")) {
															// attribute 키가
															// 배열인지?
															if (Array.isArray(filterElem["attribute"])) {
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																};
															} else {
																// 배열이 아닐때
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"] = [];
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																};
															}
														} else {
															// 필터 배열 원소에
															// attribute
															// 키가 없다면
															strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx] = {
																"code" : layerCode,
																"attribute" : [ {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																} ]
															};
														}
													} else {
														// 필터가 배열이 아니라면
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"] = [ {
															"code" : layerCode,
															"attribute" : [ {
																"key" : attrKey,
																"values" : Array.isArray(attrValues) ? attrValues : null,
																"number" : number !== null && number !== undefined ? number !== "" ? number
																		: null : null,
																"condition" : typeof condition === "string" ? condition : null,
																"interval" : isNaN(interval) ? null : interval
															} ]
														} ];
													}
												}
											}
										}
										if (!isExist) {
											var attrElem = [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											} ];
											var codeElem = {
												"code" : layerCode,
												"attribute" : attrElem
											};
											var nameElem = {
												"name" : this.nowRelationCategory,
												"figure" : [ codeElem ]
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
										}
									} else {
										// 배열이 아니라면
										var attrElem = [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ];
										var codeElem = {
											"code" : layerCode,
											"attribute" : attrElem
										};
										var nameElem = {
											"name" : this.nowRelationCategory,
											"figure" : [ codeElem ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
									}
								} else {
									// 현재 옵션에 릴레이션 키가
									// 없다면
									var attrElem = [ {
										"key" : attrKey,
										"values" : Array.isArray(attrValues) ? attrValues : null,
										"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
										"condition" : typeof condition === "string" ? condition : null,
										"interval" : isNaN(interval) ? null : interval
									} ];
									var codeElem = {
										"code" : layerCode,
										"attribute" : attrElem
									};
									var nameElem = {
										"name" : this.nowRelationCategory,
										"figure" : [ codeElem ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
								}
								// 여기까지 릴레이션
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("figure")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"])) {
										// filter키가 배열형태임
										if (strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx] === undefined) {
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx] = {};
										}
										strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["code"] = layerCode;
										// attribute 키가 배열 형태임
										if (Array
												.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"])) {
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"][filterIdx] = obj;
										} else {
											// attribute 키가 배열 형태가 아님
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"] = [ obj ];
										}

									} else {
										// filter키가 배열형태가 아님
										// 허용값이 입력되어있다면 값 변경 / 값은 위에 변수에 할당되어있음
										var obj = {
											"code" : layerCode,
											"attribute" : [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											} ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"] = [ obj ];
									}
								} else {
									// filter 키가 설정되어있지 않음
									// 허용값이 입력되어있다면 값 변경
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"] = [ obj ];
								}
							}
						} else {
							// 해당 검수항목이 설정되어 있지 않음
							// 릴레이션임
							if (sec) {
								// 없다면
								var attrElem = [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
									"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								} ];
								var codeElem = {
									"code" : layerCode,
									"attribute" : attrElem
								};
								var nameElem = {
									"name" : this.nowRelationCategory,
									"figure" : [ codeElem ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
								strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
							} else {
								// 해당 검수 항목이 설정되어 있지 않음
								// 허용값이 입력되어있다면 값 변경
								if (attrValues !== undefined && attrValues.length === 1 && attrValues[0] === "" ? false : true) {
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ]
									};
									var filterObj = [ obj ];
									var optionObj = {
										"figure" : filterObj
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias] = optionObj;
								}
							}
						}
					} else {
						// 해당 검수 타입이 설정되어 있지 않음
						if (sec) {
							var attrElem = [ {
								"key" : attrKey,
								"values" : Array.isArray(attrValues) ? attrValues : null,
								"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
								"condition" : typeof condition === "string" ? condition : null,
								"interval" : isNaN(interval) ? null : interval
							} ];
							var codeElem = {
								"code" : layerCode,
								"attribute" : attrElem
							};
							var nameElem = {
								"name" : this.nowRelationCategory,
								"figure" : [ codeElem ]
							};
							strc["definition"][i]["options"][type3] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];

							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
						} else {
							var obj = {
								"code" : layerCode,
								"attribute" : [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
									"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								} ]
							};
							var filterObj = [ obj ];
							var optionObj = {
								"figure" : filterObj
							};
							var typeObj = {};
							typeObj[this.nowOption.alias] = optionObj;
							strc["definition"][i]["options"][type3] = typeObj;
						}
					}
				}
			}
		}
		// 정의에 해당 분류가 없음
		if (!isExist) {
			// 릴레이션 설정임
			if (sec) {
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
						"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
						"condition" : typeof condition === "string" ? condition : null,
						"interval" : isNaN(interval) ? null : interval
					} ]
				};
				var optionsObj = [ {
					"name" : this.nowRelationCategory,
					"figure" : [ obj ]
				} ];

				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = {};
				typeObj[type3][this.nowOption.alias]["relation"] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			} else {
				// 릴레이션 설정 아님
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
						"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
						"condition" : typeof condition === "string" ? condition : null,
						"interval" : isNaN(interval) ? null : interval
					} ]
				};
				var optionsObj = {
					"figure" : [ obj ]
				};
				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			}
		}
	}
};

gb.embed.OptionDefinition.prototype.inputFigureNumber = function(inp) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 필터 인덱스
	var filterIdx = $(inp).parents().eq(2).index();
	// 레이어 인덱스
	var layerIdx = $(inp).parents().eq(5).index();
	// 레이어 코드
	var layerCode = null;
	if (!$(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode").prop("disabled")) {
		layerCode = $(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode option").filter(":selected").attr("geom") === "none" ? null
				: $(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode").val();
	}
	// 속성명
	var attrKey = null;
	if (!$(inp).parents().eq(2).find(".gb-optiondefinition-input-figurekey").prop("diabled")) {
		attrKey = $(inp).parents().eq(2).find(".gb-optiondefinition-input-figurekey").val().replace(/(\s*)/g, '');
	}
	// 허용값
	var attrValues = null;
	if (!$(inp).parents().eq(2).find(".gb-optiondefinition-input-figurevalues").prop("disabled")) {
		attrValues = $(inp).parents().eq(2).find(".gb-optiondefinition-input-figurevalues").val().replace(/(\s*)/g, '').split(",");
	}
	// 수치
	var number = null;
	if (!$(inp).prop("disabled")) {
		var temp = parseFloat($(inp).val());
		if (!isNaN(temp)) {
			number = parseFloat($(inp).val());
		}
	}
	// 조건
	var condition = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-select-figurecondition").prop("disabled")) {
		condition = $(inp).parents().eq(1).find(".gb-optiondefinition-select-figurecondition").val();
	}
	// 간격
	var interval = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-input-figureinterval").prop("disabled")) {
		interval = parseFloat($(inp).parents().eq(1).find(".gb-optiondefinition-input-figureinterval").val());
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}
	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							// 현재 입력한 값이 릴레이션 필터 값인지?
							if (sec) {
								// 현재 옵션에 릴레이션 키가 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									// 있다면
									// 배열인지?
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var isExist = false;
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var a = 0; a < rel.length; a++) {
											// 필터키 안에 분류 이름이 현재 릴레이션 분류 이름과 같다면
											if (rel[a]["name"] === this.nowRelationCategory) {
												isExist = true;
												var filterKey;
												if (rel[a].hasOwnProperty("figure")) {
													filterKey = rel[a]["figure"];
													// 필터가 배열인지?
													if (Array.isArray(filterKey)) {
														var filterElem = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx];
														// 필터 배열 원소에 attribute
														// 키가 있는지?
														if (filterElem.hasOwnProperty("attribute")) {
															// attribute 키가
															// 배열인지?
															if (Array.isArray(filterElem["attribute"])) {
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																};
															} else {
																// 배열이 아닐때
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"] = [];
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																};
															}
														} else {
															// 필터 배열 원소에
															// attribute
															// 키가 없다면
															strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx] = {
																"code" : layerCode,
																"attribute" : [ {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																} ]
															};
														}
													} else {
														// 필터가 배열이 아니라면
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"] = [ {
															"code" : layerCode,
															"attribute" : [ {
																"key" : attrKey,
																"values" : Array.isArray(attrValues) ? attrValues : null,
																"number" : number !== null && number !== undefined ? number !== "" ? number
																		: null : null,
																"condition" : typeof condition === "string" ? condition : null,
																"interval" : isNaN(interval) ? null : interval
															} ]
														} ];
													}
												}
											}
										}
										if (!isExist) {
											var attrElem = [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											} ];
											var codeElem = {
												"code" : layerCode,
												"attribute" : attrElem
											};
											var nameElem = {
												"name" : this.nowRelationCategory,
												"figure" : [ codeElem ]
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
										}
									} else {
										// 배열이 아니라면
										var attrElem = [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ];
										var codeElem = {
											"code" : layerCode,
											"attribute" : attrElem
										};
										var nameElem = {
											"name" : this.nowRelationCategory,
											"figure" : [ codeElem ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
									}
								} else {
									// 현재 옵션에 릴레이션 키가
									// 없다면
									var attrElem = [ {
										"key" : attrKey,
										"values" : Array.isArray(attrValues) ? attrValues : null,
										"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
										"condition" : typeof condition === "string" ? condition : null,
										"interval" : isNaN(interval) ? null : interval
									} ];
									var codeElem = {
										"code" : layerCode,
										"attribute" : attrElem
									};
									var nameElem = {
										"name" : this.nowRelationCategory,
										"figure" : [ codeElem ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
								}
								// 여기까지 릴레이션
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("figure")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"])) {
										// filter키가 배열형태임
										if (strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx] === undefined) {
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx] = {};
										}
										strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["code"] = layerCode;
										// attribute 키가 배열 형태임
										if (Array
												.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"])) {
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"][filterIdx] = obj;
										} else {
											// attribute 키가 배열 형태가 아님
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"] = [ obj ];
										}

									} else {
										// filter키가 배열형태가 아님
										// 허용값이 입력되어있다면 값 변경 / 값은 위에 변수에 할당되어있음
										var obj = {
											"code" : layerCode,
											"attribute" : [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											} ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"] = [ obj ];
									}
								} else {
									// filter 키가 설정되어있지 않음
									// 허용값이 입력되어있다면 값 변경
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"] = [ obj ];
								}
							}
						} else {
							// 해당 검수항목이 설정되어 있지 않음
							// 릴레이션임
							if (sec) {
								// 없다면
								var attrElem = [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
									"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								} ];
								var codeElem = {
									"code" : layerCode,
									"attribute" : attrElem
								};
								var nameElem = {
									"name" : this.nowRelationCategory,
									"figure" : [ codeElem ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
								strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
							} else {
								// 해당 검수 항목이 설정되어 있지 않음
								// 허용값이 입력되어있다면 값 변경
								if (attrValues !== undefined && attrValues.length === 1 && attrValues[0] === "" ? false : true) {
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ]
									};
									var filterObj = [ obj ];
									var optionObj = {
										"figure" : filterObj
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias] = optionObj;
								}
							}
						}
					} else {
						// 해당 검수 타입이 설정되어 있지 않음
						if (sec) {
							var attrElem = [ {
								"key" : attrKey,
								"values" : Array.isArray(attrValues) ? attrValues : null,
								"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
								"condition" : typeof condition === "string" ? condition : null,
								"interval" : isNaN(interval) ? null : interval
							} ];
							var codeElem = {
								"code" : layerCode,
								"attribute" : attrElem
							};
							var nameElem = {
								"name" : this.nowRelationCategory,
								"figure" : [ codeElem ]
							};
							strc["definition"][i]["options"][type3] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];

							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
						} else {
							var obj = {
								"code" : layerCode,
								"attribute" : [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
									"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								} ]
							};
							var filterObj = [ obj ];
							var optionObj = {
								"figure" : filterObj
							};
							var typeObj = {};
							typeObj[this.nowOption.alias] = optionObj;
							strc["definition"][i]["options"][type3] = typeObj;
						}
					}
				}
			}
		}
		// 정의에 해당 분류가 없음
		if (!isExist) {
			// 릴레이션 설정임
			if (sec) {
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
						"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
						"condition" : typeof condition === "string" ? condition : null,
						"interval" : isNaN(interval) ? null : interval
					} ]
				};
				var optionsObj = [ {
					"name" : this.nowRelationCategory,
					"figure" : [ obj ]
				} ];

				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = {};
				typeObj[type3][this.nowOption.alias]["relation"] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			} else {
				// 릴레이션 설정 아님
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
						"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
						"condition" : typeof condition === "string" ? condition : null,
						"interval" : isNaN(interval) ? null : interval
					} ]
				};
				var optionsObj = {
					"figure" : [ obj ]
				};
				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			}
		}
	}
};

gb.embed.OptionDefinition.prototype.inputFigureKey = function(inp) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 필터 인덱스
	var filterIdx = $(inp).parents().eq(2).index();
	// 레이어 인덱스
	var layerIdx = $(inp).parents().eq(5).index();
	// 레이어 코드
	var layerCode = null;
	if (!$(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode").prop("disabled")) {
		layerCode = $(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode option").filter(":selected").attr("geom") === "none" ? null
				: $(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode").val();
	}
	// 속성명
	var attrKey = null;
	if (!$(inp).prop("disabled")) {
		attrKey = $(inp).val().replace(/(\s*)/g, '');
	}
	// 허용값
	var attrValues = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-input-figurevalues").prop("disabled")) {
		attrValues = $(inp).parents().eq(1).find(".gb-optiondefinition-input-figurevalues").val().replace(/(\s*)/g, '').split(",");
	}
	// 수치
	var number = null;
	if (!$(inp).parents().eq(2).find(".gb-optiondefinition-input-figurenumber").prop("disabled")) {
		var temp = parseFloat($(inp).parents().eq(2).find(".gb-optiondefinition-input-figurenumber").val());
		if (!isNaN(temp)) {
			number = parseFloat($(inp).parents().eq(2).find(".gb-optiondefinition-input-figurenumber").val());
		}
	}
	// 조건
	var condition = null;
	if (!$(inp).parents().eq(2).find(".gb-optiondefinition-select-figurecondition").prop("disabled")) {
		condition = $(inp).parents().eq(2).find(".gb-optiondefinition-select-figurecondition").val();
	}

	// 간격
	var interval = null;
	if (!$(inp).parents().eq(2).find(".gb-optiondefinition-input-figureinterval").prop("disabled")) {
		interval = parseFloat($(inp).parents().eq(2).find(".gb-optiondefinition-input-figureinterval").val());
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}
	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							// 현재 입력한 값이 릴레이션 필터 값인지?
							if (sec) {
								// 현재 옵션에 릴레이션 키가 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									// 있다면
									// 배열인지?
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var isExist = false;
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var a = 0; a < rel.length; a++) {
											// 필터키 안에 분류 이름이 현재 릴레이션 분류 이름과 같다면
											if (rel[a]["name"] === this.nowRelationCategory) {
												isExist = true;
												var filterKey;
												if (rel[a].hasOwnProperty("figure")) {
													filterKey = rel[a]["figure"];
													// 필터가 배열인지?
													if (Array.isArray(filterKey)) {
														var filterElem = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx];
														// 필터 배열 원소에 attribute
														// 키가 있는지?
														if (filterElem.hasOwnProperty("attribute")) {
															// attribute 키가
															// 배열인지?
															if (Array.isArray(filterElem["attribute"])) {
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																};
															} else {
																// 배열이 아닐때
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"] = [];
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																};
															}
														} else {
															// 필터 배열 원소에
															// attribute
															// 키가 없다면
															strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx] = {
																"code" : layerCode,
																"attribute" : [ {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																} ]
															};
														}
													} else {
														// 필터가 배열이 아니라면
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"] = [ {
															"code" : layerCode,
															"attribute" : [ {
																"key" : attrKey,
																"values" : Array.isArray(attrValues) ? attrValues : null,
																"number" : number !== null && number !== undefined ? number !== "" ? number
																		: null : null,
																"condition" : typeof condition === "string" ? condition : null,
																"interval" : isNaN(interval) ? null : interval
															} ]
														} ];
													}
												}
											}
										}
										if (!isExist) {
											var attrElem = [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											} ];
											var codeElem = {
												"code" : layerCode,
												"attribute" : attrElem
											};
											var nameElem = {
												"name" : this.nowRelationCategory,
												"figure" : [ codeElem ]
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
										}
									} else {
										// 배열이 아니라면
										var attrElem = [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ];
										var codeElem = {
											"code" : layerCode,
											"attribute" : attrElem
										};
										var nameElem = {
											"name" : this.nowRelationCategory,
											"figure" : [ codeElem ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
									}
								} else {
									// 현재 옵션에 릴레이션 키가
									// 없다면
									var attrElem = [ {
										"key" : attrKey,
										"values" : Array.isArray(attrValues) ? attrValues : null,
										"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
										"condition" : typeof condition === "string" ? condition : null,
										"interval" : isNaN(interval) ? null : interval
									} ];
									var codeElem = {
										"code" : layerCode,
										"attribute" : attrElem
									};
									var nameElem = {
										"name" : this.nowRelationCategory,
										"figure" : [ codeElem ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
								}
								// 여기까지 릴레이션
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("figure")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"])) {
										// filter키가 배열형태임
										if (strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx] === undefined) {
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx] = {};
										}
										strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["code"] = layerCode;
										// attribute 키가 배열 형태임
										if (Array
												.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"])) {
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"][filterIdx] = obj;
										} else {
											// attribute 키가 배열 형태가 아님
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"] = [ obj ];
										}

									} else {
										// filter키가 배열형태가 아님
										// 허용값이 입력되어있다면 값 변경 / 값은 위에 변수에 할당되어있음
										var obj = {
											"code" : layerCode,
											"attribute" : [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											} ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"] = [ obj ];
									}
								} else {
									// filter 키가 설정되어있지 않음
									// 허용값이 입력되어있다면 값 변경
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"] = [ obj ];
								}
							}
						} else {
							// 해당 검수항목이 설정되어 있지 않음
							// 릴레이션임
							if (sec) {
								// 없다면
								var attrElem = [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
									"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								} ];
								var codeElem = {
									"code" : layerCode,
									"attribute" : attrElem
								};
								var nameElem = {
									"name" : this.nowRelationCategory,
									"figure" : [ codeElem ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
								strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
							} else {
								// 해당 검수 항목이 설정되어 있지 않음
								// 허용값이 입력되어있다면 값 변경
								if (attrValues !== undefined && attrValues.length === 1 && attrValues[0] === "" ? false : true) {
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ]
									};
									var filterObj = [ obj ];
									var optionObj = {
										"figure" : filterObj
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias] = optionObj;
								}
							}
						}
					} else {
						// 해당 검수 타입이 설정되어 있지 않음
						if (sec) {
							var attrElem = [ {
								"key" : attrKey,
								"values" : Array.isArray(attrValues) ? attrValues : null,
								"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
								"condition" : typeof condition === "string" ? condition : null,
								"interval" : isNaN(interval) ? null : interval
							} ];
							var codeElem = {
								"code" : layerCode,
								"attribute" : attrElem
							};
							var nameElem = {
								"name" : this.nowRelationCategory,
								"figure" : [ codeElem ]
							};
							strc["definition"][i]["options"][type3] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];

							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
						} else {
							var obj = {
								"code" : layerCode,
								"attribute" : [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
									"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								} ]
							};
							var filterObj = [ obj ];
							var optionObj = {
								"figure" : filterObj
							};
							var typeObj = {};
							typeObj[this.nowOption.alias] = optionObj;
							strc["definition"][i]["options"][type3] = typeObj;
						}
					}
				}
			}
		}
		// 정의에 해당 분류가 없음
		if (!isExist) {
			// 릴레이션 설정임
			if (sec) {
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
						"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
						"condition" : typeof condition === "string" ? condition : null,
						"interval" : isNaN(interval) ? null : interval
					} ]
				};
				var optionsObj = [ {
					"name" : this.nowRelationCategory,
					"figure" : [ obj ]
				} ];

				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = {};
				typeObj[type3][this.nowOption.alias]["relation"] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			} else {
				// 릴레이션 설정 아님
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
						"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
						"condition" : typeof condition === "string" ? condition : null,
						"interval" : isNaN(interval) ? null : interval
					} ]
				};
				var optionsObj = {
					"figure" : [ obj ]
				};
				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			}
		}
	}
};

gb.embed.OptionDefinition.prototype.deleteFilterRow = function(btn) {
	// 필터 엘리먼트
	var filterElem = $(btn).parents().eq(1);
	// 필터 인덱스
	var filterIdx = $(filterElem).index();
	// 레이어 인덱스
	var layerIdx = $(btn).parents().eq(4).index();

	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							if (sec) {
								// relation 키가 설정되어 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
									// relation이 배열인지?
									if (Array.isArray(rel)) {
										for (var j = 0; j < rel.length; j++) {
											if (rel[j]["name"] === this.nowRelationCategory) {
												if (rel[j].hasOwnProperty("filter")) {
													if (Array.isArray(rel[j]["filter"])) {
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["filter"][layerIdx]["attribute"]
																.splice(filterIdx, 1);
														/*
														 * if
														 * (rel[j]["filter"].length
														 * === 0) { delete
														 * strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["filter"]; }
														 */
													}
												}
											}
										}
									}
								}
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("filter")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"])) {
										// filter키가 배열형태임
										var attr = strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["attribute"];
										if (Array.isArray(attr)) {
											strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["attribute"]
													.splice(filterIdx, 1);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		$(filterElem).remove();
	}
};

gb.embed.OptionDefinition.prototype.deleteLayerCodeFilter = function(btn) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 레이어 엘리먼트
	var layerElem = $(btn).parents().eq(2);
	// 레이어 코드 인덱스
	var layerIdx = $(layerElem).index();

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							if (sec) {
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var j = 0; j < rel.length; j++) {
											if (rel[j]["name"] === this.nowRelationCategory) {
												if (rel[j].hasOwnProperty("filter")) {
													if (Array.isArray(rel[j]["filter"])) {
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["filter"]
																.splice(layerIdx, 1);
														if (strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["filter"].length === 0) {
															delete strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]["filter"];
														}
														var types = Object
																.keys(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j]);
														if (types.length === 1) {
															strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"]
																	.splice(j, 1);
															if (strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].length === 0) {
																delete strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
															}
														}
													}
												}
											}
										}
									}
								}
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("filter")) {
									var filter = strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"];
									if (Array.isArray(filter)) {
										strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"].splice(layerIdx, 1);
										// $(layerElem).remove();
										if (strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"].length === 0) {
											delete strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"];
										}
									}
								}
							}
							var optionKeys = Object.keys(strc["definition"][i]["options"][type3][this.nowOption.alias]);
							if (optionKeys.length === 0) {
								delete strc["definition"][i]["options"][type3][this.nowOption.alias];
								var typeKeys = Object.keys(strc["definition"][i]["options"][type3]);
								if (typeKeys.length === 0) {
									delete strc["definition"][i]["options"][type3];
									var keys = Object.keys(strc["definition"][i]["options"]);
									if (keys.length === 0) {
										strc["definition"].splice(layerIdx, 1);
									}
								}
							}
						}
					}
				}
			}
		}
		$(layerElem).remove();
	}
};

gb.embed.OptionDefinition.prototype.inputFigureValues = function(inp) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 필터 인덱스
	var filterIdx = $(inp).parents().eq(2).index();
	// 레이어 인덱스
	var layerIdx = $(inp).parents().eq(5).index();
	// 레이어 코드
	var layerCode = null;
	if (!$(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode").prop("disabled")) {
		layerCode = $(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode option").filter(":selected").attr("geom") === "none" ? null
				: $(inp).parents().eq(5).find(".gb-optiondefinition-select-figurecode").val();
	}
	// 속성명
	var attrKey = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-input-figurekey").prop("disabled")) {
		attrKey = $(inp).parents().eq(1).find(".gb-optiondefinition-input-figurekey").val().replace(/(\s*)/g, '');
	}
	// 허용값
	var attrValues = null;
	if (!$(inp).prop("disabled")) {
		attrValues = $(inp).val().replace(/(\s*)/g, '').split(",");
	}
	// 수치
	var number = null;
	if (!$(inp).parents().eq(2).find(".gb-optiondefinition-input-figurenumber").prop("disabled")) {
		var temp = parseFloat($(inp).parents().eq(2).find(".gb-optiondefinition-input-figurenumber").val());
		if (!isNaN(temp)) {
			number = parseFloat($(inp).parents().eq(2).find(".gb-optiondefinition-input-figurenumber").val());
		}
	}
	// 조건
	var condition = null;
	if (!$(inp).parents().eq(2).find(".gb-optiondefinition-select-figurecondition").prop("disabled")) {
		condition = $(inp).parents().eq(2).find(".gb-optiondefinition-select-figurecondition").val();
	}
	// 간격
	var interval = null;
	if (!$(inp).parents().eq(2).find(".gb-optiondefinition-input-figureinterval").prop("disabled")) {
		interval = parseFloat($(inp).parents().eq(2).find(".gb-optiondefinition-input-figureinterval").val());
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}
	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							// 현재 입력한 값이 릴레이션 필터 값인지?
							if (sec) {
								// 현재 옵션에 릴레이션 키가 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									// 있다면
									// 배열인지?
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var isExist = false;
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var a = 0; a < rel.length; a++) {
											// 필터키 안에 분류 이름이 현재 릴레이션 분류 이름과 같다면
											if (rel[a]["name"] === this.nowRelationCategory) {
												isExist = true;
												var filterKey;
												if (rel[a].hasOwnProperty("figure")) {
													filterKey = rel[a]["figure"];
													// 필터가 배열인지?
													if (Array.isArray(filterKey)) {
														var filterElem = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx];
														// 필터 배열 원소에 attribute
														// 키가 있는지?
														if (filterElem.hasOwnProperty("attribute")) {
															// attribute 키가
															// 배열인지?
															if (Array.isArray(filterElem["attribute"])) {
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																};
															} else {
																// 배열이 아닐때
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"] = [];
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																};
															}
														} else {
															// 필터 배열 원소에
															// attribute
															// 키가 없다면
															strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"][layerIdx] = {
																"code" : layerCode,
																"attribute" : [ {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																	"number" : number !== null && number !== undefined ? number !== "" ? number
																			: null
																			: null,
																	"condition" : typeof condition === "string" ? condition : null,
																	"interval" : isNaN(interval) ? null : interval
																} ]
															};
														}
													} else {
														// 필터가 배열이 아니라면
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["figure"] = [ {
															"code" : layerCode,
															"attribute" : [ {
																"key" : attrKey,
																"values" : Array.isArray(attrValues) ? attrValues : null,
																"number" : number !== null && number !== undefined ? number !== "" ? number
																		: null : null,
																"condition" : typeof condition === "string" ? condition : null,
																"interval" : isNaN(interval) ? null : interval
															} ]
														} ];
													}
												}
											}
										}
										if (!isExist) {
											var attrElem = [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											} ];
											var codeElem = {
												"code" : layerCode,
												"attribute" : attrElem
											};
											var nameElem = {
												"name" : this.nowRelationCategory,
												"figure" : [ codeElem ]
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
										}
									} else {
										// 배열이 아니라면
										var attrElem = [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ];
										var codeElem = {
											"code" : layerCode,
											"attribute" : attrElem
										};
										var nameElem = {
											"name" : this.nowRelationCategory,
											"figure" : [ codeElem ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
									}
								} else {
									// 현재 옵션에 릴레이션 키가
									// 없다면
									var attrElem = [ {
										"key" : attrKey,
										"values" : Array.isArray(attrValues) ? attrValues : null,
										"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
										"condition" : typeof condition === "string" ? condition : null,
										"interval" : isNaN(interval) ? null : interval
									} ];
									var codeElem = {
										"code" : layerCode,
										"attribute" : attrElem
									};
									var nameElem = {
										"name" : this.nowRelationCategory,
										"figure" : [ codeElem ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
								}
								// 여기까지 릴레이션
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("figure")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"])) {
										// filter키가 배열형태임
										if (strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx] === undefined) {
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx] = {};
										}
										strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["code"] = layerCode;
										// attribute 키가 배열 형태임
										if (Array
												.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"])) {
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"][filterIdx] = obj;
										} else {
											// attribute 키가 배열 형태가 아님
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"][layerIdx]["attribute"] = [ obj ];
										}

									} else {
										// filter키가 배열형태가 아님
										// 허용값이 입력되어있다면 값 변경 / 값은 위에 변수에 할당되어있음
										var obj = {
											"code" : layerCode,
											"attribute" : [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
												"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
												"condition" : typeof condition === "string" ? condition : null,
												"interval" : isNaN(interval) ? null : interval
											} ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"] = [ obj ];
									}
								} else {
									// filter 키가 설정되어있지 않음
									// 허용값이 입력되어있다면 값 변경
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["figure"] = [ obj ];
								}
							}
						} else {
							// 해당 검수항목이 설정되어 있지 않음
							// 릴레이션임
							if (sec) {
								// 없다면
								var attrElem = [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
									"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								} ];
								var codeElem = {
									"code" : layerCode,
									"attribute" : attrElem
								};
								var nameElem = {
									"name" : this.nowRelationCategory,
									"figure" : [ codeElem ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
								strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
							} else {
								// 해당 검수 항목이 설정되어 있지 않음
								// 허용값이 입력되어있다면 값 변경
								if (attrValues !== undefined && attrValues.length === 1 && attrValues[0] === "" ? false : true) {
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
											"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
											"condition" : typeof condition === "string" ? condition : null,
											"interval" : isNaN(interval) ? null : interval
										} ]
									};
									var filterObj = [ obj ];
									var optionObj = {
										"figure" : filterObj
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias] = optionObj;
								}
							}
						}
					} else {
						// 해당 검수 타입이 설정되어 있지 않음
						if (sec) {
							var attrElem = [ {
								"key" : attrKey,
								"values" : Array.isArray(attrValues) ? attrValues : null,
								"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
								"condition" : typeof condition === "string" ? condition : null,
								"interval" : isNaN(interval) ? null : interval
							} ];
							var codeElem = {
								"code" : layerCode,
								"attribute" : attrElem
							};
							var nameElem = {
								"name" : this.nowRelationCategory,
								"figure" : [ codeElem ]
							};
							strc["definition"][i]["options"][type3] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];

							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
						} else {
							var obj = {
								"code" : layerCode,
								"attribute" : [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
									"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
									"condition" : typeof condition === "string" ? condition : null,
									"interval" : isNaN(interval) ? null : interval
								} ]
							};
							var filterObj = [ obj ];
							var optionObj = {
								"figure" : filterObj
							};
							var typeObj = {};
							typeObj[this.nowOption.alias] = optionObj;
							strc["definition"][i]["options"][type3] = typeObj;
						}
					}
				}
			}
		}
		// 정의에 해당 분류가 없음
		if (!isExist) {
			// 릴레이션 설정임
			if (sec) {
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
						"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
						"condition" : typeof condition === "string" ? condition : null,
						"interval" : isNaN(interval) ? null : interval
					} ]
				};
				var optionsObj = [ {
					"name" : this.nowRelationCategory,
					"figure" : [ obj ]
				} ];

				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = {};
				typeObj[type3][this.nowOption.alias]["relation"] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			} else {
				// 릴레이션 설정 아님
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
						"number" : number !== null && number !== undefined ? number !== "" ? number : null : null,
						"condition" : typeof condition === "string" ? condition : null,
						"interval" : isNaN(interval) ? null : interval
					} ]
				};
				var optionsObj = {
					"figure" : [ obj ]
				};
				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			}
		}
	}
}

gb.embed.OptionDefinition.prototype.inputFilterValues = function(inp) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 필터 인덱스
	var filterIdx = $(inp).parents().eq(1).index();
	// 레이어 인덱스
	var layerIdx = $(inp).parents().eq(4).index();
	// 레이어 코드
	var layerCode = null;
	if (!$(inp).parents().eq(4).find(".gb-optiondefinition-select-filtercode").prop("disabled")) {
		layerCode = $(inp).parents().eq(4).find(".gb-optiondefinition-select-filtercode option").filter(":selected").attr("geom") === "none" ? null
				: $(inp).parents().eq(4).find(".gb-optiondefinition-select-filtercode").val();
	}
	// 속성명
	var attrKey = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-input-filterkey").prop("disabled")) {
		attrKey = $(inp).parents().eq(1).find(".gb-optiondefinition-input-filterkey").val().replace(/(\s*)/g, '');
	}
	// 허용값
	var attrValues = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-input-filtervalues").prop("disabled")) {
		attrValues = $(inp).parents().eq(1).find(".gb-optiondefinition-input-filtervalues").val().replace(/(\s*)/g, '').split(",");
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}
	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							// 현재 입력한 값이 릴레이션 필터 값인지?
							if (sec) {
								// 현재 옵션에 릴레이션 키가 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									// 있다면
									// 배열인지?
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var isExist = false;
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var a = 0; a < rel.length; a++) {
											// 필터키 안에 분류 이름이 현재 릴레이션 분류 이름과 같다면
											if (rel[a]["name"] === this.nowRelationCategory) {
												isExist = true;
												var filterKey;
												if (rel[a].hasOwnProperty("filter")) {
													filterKey = rel[a]["filter"];
													// 필터가 배열인지?
													if (Array.isArray(filterKey)) {
														var filterElem = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx];
														// 필터 배열 원소에 attribute
														// 키가 있는지?
														if (filterElem.hasOwnProperty("attribute")) {
															// attribute 키가
															// 배열인지?
															if (Array.isArray(filterElem["attribute"])) {
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																};
															} else {
																// 배열이 아닐때
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx]["attribute"] = [];
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																};
															}
														} else {
															// 필터 배열 원소에
															// attribute
															// 키가 없다면
															strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx] = {
																"code" : layerCode,
																"attribute" : [ {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																} ]
															};
														}
													} else {
														// 필터가 배열이 아니라면
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"] = [ {
															"code" : layerCode,
															"attribute" : [ {
																"key" : attrKey,
																"values" : Array.isArray(attrValues) ? attrValues : null,
															} ]
														} ];
													}
												}
											}
										}
										if (!isExist) {
											var attrElem = [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
											} ];
											var codeElem = {
												"code" : layerCode,
												"attribute" : attrElem
											};
											var nameElem = {
												"name" : this.nowRelationCategory,
												"filter" : [ codeElem ]
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
										}
									} else {
										// 배열이 아니라면
										var attrElem = [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
										} ];
										var codeElem = {
											"code" : layerCode,
											"attribute" : attrElem
										};
										var nameElem = {
											"name" : this.nowRelationCategory,
											"filter" : [ codeElem ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
									}
								} else {
									// 현재 옵션에 릴레이션 키가
									// 없다면
									var attrElem = [ {
										"key" : attrKey,
										"values" : Array.isArray(attrValues) ? attrValues : null,
									} ];
									var codeElem = {
										"code" : layerCode,
										"attribute" : attrElem
									};
									var nameElem = {
										"name" : this.nowRelationCategory,
										"filter" : [ codeElem ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
								}
								// 여기까지 릴레이션
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("filter")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"])) {
										// filter키가 배열형태임
										// 속성명이 입력되어있다면 값 변경
										var key = $(inp).parents().eq(1).find(".gb-optiondefinition-input-filterkey").val();
										if (key !== undefined && key !== "") {
											var attr = strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["attribute"];
											// 애트리뷰트 키가 배열일때
											if (Array.isArray(attr)) {
												// 레이어 코드 입력
												strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["code"] = layerCode;
												// 속성명 입력
												strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["attribute"][filterIdx]["key"] = attrKey;
												// 허용값 입력
												strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["attribute"][filterIdx]["values"] = $(
														inp).val().replace(/(\s*)/g, '').split(",");
											} else {
												// 레이어 코드 입력
												strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["code"] = layerCode;
												// 애트리뷰트 키가 배열이 아닐때
												var obj = {
													"key" : attrKey,
													"values" : $(inp).val().replace(/(\s*)/g, '').split(",")
												};
												strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["attribute"] = [ obj ];
											}
										}
									} else {
										// filter키가 배열형태가 아님
										// 속성명이 입력되어있다면 값 변경
										var key = $(inp).parents().eq(1).find(".gb-optiondefinition-input-filterkey").val();
										if (key !== undefined && key !== "") {
											var obj = {
												"code" : layerCode,
												"attribute" : [ {
													"key" : attrKey,
													"values" : $(inp).val().replace(/(\s*)/g, '').split(",")
												} ]
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"] = [ obj ];
										}
									}
								} else {
									// filter 키가 설정되어있지 않음
									// 속성명이 입력되어있다면 값 변경
									var key = $(inp).parents().eq(1).find(".gb-optiondefinition-input-filterkey").val();
									if (key !== undefined && key !== "") {
										var obj = {
											"code" : layerCode,
											"attribute" : [ {
												"key" : attrKey,
												"values" : $(inp).val().replace(/(\s*)/g, '').split(",")
											} ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"] = [ obj ];
									}
								}
							}
						} else {
							if (sec) {
								// 없다면
								var attrElem = [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
								} ];
								var codeElem = {
									"code" : layerCode,
									"attribute" : attrElem
								};
								var nameElem = {
									"name" : this.nowRelationCategory,
									"filter" : [ codeElem ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
								strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
							} else {
								// 해당 검수 항목이 설정되어 있지 않음
								// 속성명이 입력되어있다면 값 변경
								var key = $(inp).parents().eq(1).find(".gb-optiondefinition-input-filterkey").val();
								if (key !== undefined && key !== "") {
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : $(inp).val().replace(/(\s*)/g, '').split(",")
										} ]
									};
									var filterObj = [ obj ];
									var optionObj = {
										"filter" : filterObj
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias] = optionObj;
								}
							}
						}
					} else {
						// 해당 검수 타입이 설정되어 있지 않음
						// 속성명이 입력되어있다면 값 변경
						if (sec) {
							var attrElem = [ {
								"key" : attrKey,
								"values" : Array.isArray(attrValues) ? attrValues : null,
							} ];
							var codeElem = {
								"code" : layerCode,
								"attribute" : attrElem
							};
							var nameElem = {
								"name" : this.nowRelationCategory,
								"filter" : [ codeElem ]
							};
							strc["definition"][i]["options"][type3] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];

							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
						} else {
							var key = $(inp).parents().eq(1).find(".gb-optiondefinition-input-filterkey").val();
							if (key !== undefined && key !== "") {
								var obj = {
									"code" : layerCode,
									"attribute" : [ {
										"key" : attrKey,
										"values" : $(inp).val().replace(/(\s*)/g, '').split(",")
									} ]
								};
								var filterObj = [ obj ];
								var optionObj = {
									"filter" : filterObj
								};
								var typeObj = {};
								typeObj[this.nowOption.alias] = optionObj;
								strc["definition"][i]["options"][type3] = typeObj;
							}
						}
					}
				}
			}
		}
	}
}

gb.embed.OptionDefinition.prototype.inputFilterKey = function(inp) {
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];
	// 필터 인덱스
	var filterIdx = $(inp).parents().eq(1).index();
	// 레이어 인덱스
	var layerIdx = $(inp).parents().eq(4).index();
	// 레이어 코드
	var layerCode = null;
	if (!$(inp).parents().eq(4).find(".gb-optiondefinition-select-filtercode").prop("disabled")) {
		layerCode = $(inp).parents().eq(4).find(".gb-optiondefinition-select-filtercode option").filter(":selected").attr("geom") === "none" ? null
				: $(inp).parents().eq(4).find(".gb-optiondefinition-select-filtercode").val();
	}
	// 속성명
	var attrKey = null;
	if (!$(inp).prop("disabled")) {
		attrKey = $(inp).val().replace(/(\s*)/g, '');
	}
	// 허용값
	var attrValues = null;
	if (!$(inp).parents().eq(1).find(".gb-optiondefinition-input-filtervalues").prop("disabled")) {
		attrValues = $(inp).parents().eq(1).find(".gb-optiondefinition-input-filtervalues").val().replace(/(\s*)/g, '').split(",");
	}
	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}
	var strc = this.getStructure();
	if (Array.isArray(strc["definition"])) {
		var isExist = false;
		// definition에서 현재 분류를 찾는다
		for (var i = 0; i < strc["definition"].length; i++) {
			if (strc["definition"][i]["name"] === this.nowCategory) {
				isExist = true;
				// options 키를 가지고 있는지?
				if (strc["definition"][i].hasOwnProperty("options")) {
					// 검수 타입이 설정 되어있는지
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 해당 검수 항목이 설정되어 있는지
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							// 현재 입력한 값이 릴레이션 필터 값인지?
							if (sec) {
								// 현재 옵션에 릴레이션 키가 있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									// 있다면
									// 배열인지?
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
										var isExist = false;
										var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
										for (var a = 0; a < rel.length; a++) {
											// 필터키 안에 분류 이름이 현재 릴레이션 분류 이름과 같다면
											if (rel[a]["name"] === this.nowRelationCategory) {
												isExist = true;
												var filterKey;
												if (rel[a].hasOwnProperty("filter")) {
													filterKey = rel[a]["filter"];
													// 필터가 배열인지?
													if (Array.isArray(filterKey)) {
														var filterElem = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx];
														// 필터 배열 원소에 attribute
														// 키가 있는지?
														if (filterElem.hasOwnProperty("attribute")) {
															// attribute 키가
															// 배열인지?
															if (Array.isArray(filterElem["attribute"])) {
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																};
															} else {
																// 배열이 아닐때
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx]["code"] = layerCode;
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx]["attribute"] = [];
																strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx]["attribute"][filterIdx] = {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																};
															}
														} else {
															// 필터 배열 원소에
															// attribute
															// 키가 없다면
															strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"][layerIdx] = {
																"code" : layerCode,
																"attribute" : [ {
																	"key" : attrKey,
																	"values" : Array.isArray(attrValues) ? attrValues : null,
																} ]
															};
														}
													} else {
														// 필터가 배열이 아니라면
														strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][a]["filter"] = [ {
															"code" : layerCode,
															"attribute" : [ {
																"key" : attrKey,
																"values" : Array.isArray(attrValues) ? attrValues : null,
															} ]
														} ];
													}
												}
											}
										}
										if (!isExist) {
											var attrElem = [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
											} ];
											var codeElem = {
												"code" : layerCode,
												"attribute" : attrElem
											};
											var nameElem = {
												"name" : this.nowRelationCategory,
												"filter" : [ codeElem ]
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
										}
									} else {
										// 배열이 아니라면
										var attrElem = [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
										} ];
										var codeElem = {
											"code" : layerCode,
											"attribute" : attrElem
										};
										var nameElem = {
											"name" : this.nowRelationCategory,
											"filter" : [ codeElem ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"].push(nameElem);
									}
								} else {
									// 현재 옵션에 릴레이션 키가
									// 없다면
									var attrElem = [ {
										"key" : attrKey,
										"values" : Array.isArray(attrValues) ? attrValues : null,
									} ];
									var codeElem = {
										"code" : layerCode,
										"attribute" : attrElem
									};
									var nameElem = {
										"name" : this.nowRelationCategory,
										"filter" : [ codeElem ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
								}
								// 여기까지 릴레이션
							} else {
								// filter 키가 설정되어있는지?
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("filter")) {
									if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"])) {
										// filter키가 배열형태임
										if (strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx] === undefined) {
											strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx] = {
												"code" : undefined
											};
										}
										strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["code"] = layerCode;
										// attribute 키가 배열 형태임
										if (Array
												.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["attribute"])) {
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["attribute"][filterIdx] = obj;
										} else {
											// attribute 키가 배열 형태가 아님
											var obj = {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
											};
											strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["attribute"] = [ obj ];
										}

									} else {
										// filter키가 배열형태가 아님
										// 허용값이 입력되어있다면 값 변경 / 값은 위에 변수에 할당되어있음
										var obj = {
											"code" : layerCode,
											"attribute" : [ {
												"key" : attrKey,
												"values" : Array.isArray(attrValues) ? attrValues : null,
											} ]
										};
										strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"] = [ obj ];
									}
								} else {
									// filter 키가 설정되어있지 않음
									// 허용값이 입력되어있다면 값 변경
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
										} ]
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"] = [ obj ];
								}
							}
						} else {
							// 해당 검수항목이 설정되어 있지 않음
							// 릴레이션임
							if (sec) {
								// 없다면
								var attrElem = [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
								} ];
								var codeElem = {
									"code" : layerCode,
									"attribute" : attrElem
								};
								var nameElem = {
									"name" : this.nowRelationCategory,
									"filter" : [ codeElem ]
								};
								strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
								strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];
							} else {
								// 해당 검수 항목이 설정되어 있지 않음
								// 허용값이 입력되어있다면 값 변경
								var values = $(inp).parents().eq(1).find(".gb-optiondefinition-input-filtervalues").val();
								if (values !== undefined && values !== "") {
									var obj = {
										"code" : layerCode,
										"attribute" : [ {
											"key" : attrKey,
											"values" : Array.isArray(attrValues) ? attrValues : null,
										} ]
									};
									var filterObj = [ obj ];
									var optionObj = {
										"filter" : filterObj
									};
									strc["definition"][i]["options"][type3][this.nowOption.alias] = optionObj;
								}
							}
						}
					} else {
						// 해당 검수 타입이 설정되어 있지 않음
						if (sec) {
							var attrElem = [ {
								"key" : attrKey,
								"values" : Array.isArray(attrValues) ? attrValues : null,
							} ];
							var codeElem = {
								"code" : layerCode,
								"attribute" : attrElem
							};
							var nameElem = {
								"name" : this.nowRelationCategory,
								"filter" : [ codeElem ]
							};
							strc["definition"][i]["options"][type3] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias] = {};
							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ nameElem ];

							strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
						} else {
							var obj = {
								"code" : layerCode,
								"attribute" : [ {
									"key" : attrKey,
									"values" : Array.isArray(attrValues) ? attrValues : null,
								} ]
							};
							var filterObj = [ obj ];
							var optionObj = {
								"filter" : filterObj
							};
							var typeObj = {};
							typeObj[this.nowOption.alias] = optionObj;
							strc["definition"][i]["options"][type3] = typeObj;
						}
					}
				}
			}
		}
		// 정의에 해당 분류가 없음
		if (!isExist) {
			// 릴레이션 설정임
			if (sec) {
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
					} ]
				};
				var optionsObj = [ {
					"name" : this.nowRelationCategory,
					"filter" : [ obj ]
				} ];

				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = {};
				typeObj[type3][this.nowOption.alias]["relation"] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			} else {
				// 릴레이션 설정 아님
				// 해당 검수 타입이 설정되어 있지 않음
				// 허용값이 입력되어있다면 값 변경
				var obj = {
					"code" : layerCode,
					"attribute" : [ {
						"key" : attrKey,
						"values" : Array.isArray(attrValues) ? attrValues : null,
					} ]
				};
				var optionsObj = {
					"filter" : [ obj ]
				};
				var typeObj = {};
				typeObj[type3] = {};
				typeObj[type3][this.nowOption.alias] = optionsObj;

				var definitionObj = {
					"name" : this.nowCategory,
					"options" : typeObj
				};
				this.getStructure()["definition"].push(definitionObj);
			}
		}
	}
}

gb.embed.OptionDefinition.prototype.setNoParamOption = function(check, all) {
	var strc = this.getStructure();
	var def = strc["definition"];
	var optItem = this.optItem[this.nowOption.alias];
	var type3 = optItem["purpose"];

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}
	// 모든 분류 선택인지
	if (sec && all) {
		var layerDef = this.getLayerDefinition().getStructure();
		// 검수 옵션 객체의 definition이 배열인지?
		if (Array.isArray(def)) {
			// 베열안에 값이 있는지?
			var isExist = false;
			if (def.length > 0) {
				for (var i = 0; i < def.length; i++) {
					if (this.nowCategory === def[i].name) {
						isExist = true;
						// 분류안에 options 키가 있는지
						if (def[i].hasOwnProperty("options")) {
							// 있음
							// 분류 안에 있는 options 키
							// def[i]["options"];
							// options 안에 그래픽, 애트리, 인접 키가 있는지?
							if (def[i]["options"].hasOwnProperty(type3)) {
								// 있을때
								// 키 안에 현재 검수 항목이 있는지?
								if (def[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
									// 있을때
									// 체크박스가 체크되어 있는지?
									if ($(check).is(":checked")) {

										def[i]["options"][type3][this.nowOption.alias]["relation"] = [];
										if (Array.isArray(layerDef)) {
											for (var a = 0; a < layerDef.length; a++) {
												var obj = {
													"filter" : null
												};
												if (type3 === "attribute") {
													obj["figure"] = null;
													obj["relation"] = null;
												} else if (type3 === "graphic") {
													obj["tolerance"] = null;
													obj["relation"] = null;
												} else if (type3 === "adjacent") {
													obj["figure"] = null;
													obj["tolerance"] = null;
													obj["relation"] = null;
												}
												obj["name"] = layerDef[a].name;
												delete obj["relation"];
												def[i]["options"][type3][this.nowOption.alias]["relation"].push(obj);
											}
										}
									} else {
										// 체크 안됨
										// 검수 항목 키 삭제
										if (def[i].hasOwnProperty("options")) {
											if (def[i]["options"].hasOwnProperty(type3)) {
												// 검수 옵션 항목이 있다면
												if (def[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
													// 릴레이션 키가 있다면
													if (def[i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
														if (Array.isArray(def[i]["options"][type3][this.nowOption.alias]["relation"])) {
															def[i]["options"][type3][this.nowOption.alias]["relation"] = [];
															var rel = def[i]["options"][type3][this.nowOption.alias]["relation"];
															if (rel.length === 0) {
																delete def[i]["options"][type3][this.nowOption.alias]["relation"];
															}
															var itemNames = Object.keys(def[i]["options"][type3][this.nowOption.alias]);
															if (itemNames.length === 0) {
																delete def[i]["options"][type3][this.nowOption.alias];
															}
															var typeNames = Object.keys(def[i]["options"][type3]);
															if (typeNames.length === 0) {
																delete def[i]["options"][type3];
															}
															var names = Object.keys(def[i]["options"]);
															if (names.length === 0) {
																def.splice(i, 1);
															}
														}
													} else {
														// 릴레이션 키가 없다면
													}
												}
											}
										}
									}
								} else {
									// 타입 키안에 현재 검수 옵션이
									// 없을때
									// 쳌박스 쳌됨
									if ($(check).is(":checked")) {
										// 쳌박스 쳌됨
										if (def[i]["options"][type3] === undefined) {
											def[i]["options"][type3] = {};
										}
										def[i]["options"][type3][this.nowOption.alias] = {
											"relation" : []
										};

										for (var a = 0; a < layerDef.length; a++) {
											var obj = {
												"filter" : null
											};
											if (type3 === "attribute") {
												obj["figure"] = null;
												obj["relation"] = null;
											} else if (type3 === "graphic") {
												obj["tolerance"] = null;
												obj["relation"] = null;
											} else if (type3 === "adjacent") {
												obj["figure"] = null;
												obj["tolerance"] = null;
												obj["relation"] = null;
											}
											obj["name"] = layerDef[a].name;
											delete obj["relation"];
											def[i]["options"][type3][this.nowOption.alias]["relation"].push(obj);
										}
									} else {
										// 체크 안됨
										// 검수 항목 키 삭제
										def[i]["options"][type3][this.nowOption.alias]["relation"] = [];

									}
								}
							} else {
								// 옵션안에 애트리뷰트 그래픽 등의 키가
								// 없을때
								if ($(check).is(":checked")) {
									if (!def[i].hasOwnProperty("options")) {
										def[i]["options"] = {};
									}
									if (!def[i]["options"].hasOwnProperty(type3)) {
										def[i]["options"][type3] = {};
									}
									if (!def[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
										def[i]["options"][type3][this.nowOption.alias] = {};
									}
									def[i]["options"][type3][this.nowOption.alias]["relation"] = [];

									for (var a = 0; a < layerDef.length; a++) {
										var obj = {
											"filter" : null
										};
										if (type3 === "attribute") {
											obj["figure"] = null;
											obj["relation"] = null;
										} else if (type3 === "graphic") {
											obj["tolerance"] = null;
											obj["relation"] = null;
										} else if (type3 === "adjacent") {
											obj["figure"] = null;
											obj["tolerance"] = null;
											obj["relation"] = null;
										}

										obj["name"] = layerDef[a].name;
										delete obj["relation"];

									}
								} else {
									// 체크 안됨
									// 검수 항목 키 삭제
									if (def[i].hasOwnProperty("options")) {
										if (def[i]["options"].hasOwnProperty(type3)) {
											if (def[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
												if (def[i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
													delete def[i]["options"][type3][this.nowOption.alias]["relation"];
												}
											}
										}
									}
									var itemNames = Object.keys(def[i]["options"][type3][this.nowOption.alias]);
									if (itemNames.length === 0) {
										delete def[i]["options"][type3][this.nowOption.alias];
									}
									var optionKeys = Object.keys(def[i]["options"][type3]);
									if (optionKeys.length === 0) {
										delete def[i]["options"][type3];
									}
									var typeKeys = Object.keys(def[i]["options"]);
									if (typeKeys.length === 0) {
										delete def[i]["options"];
										def.splice(i, 1);
									}
								}
							}
						}
					}
				}
				if (isExist === false) {
					// definition을 다돌았지만 없었음 새로 입력
					if ($(check).is(":checked")) {
						// 체크함
						var outerObj = {
							"name" : this.nowCategory,
							"options" : {}
						};
						outerObj["options"][type3] = {};
						outerObj["options"][type3][this.nowOption.alias] = {
							"relation" : []
						};

						for (var a = 0; a < layerDef.length; a++) {
							var obj = {
								"filter" : null
							};
							if (type3 === "attribute") {
								obj["figure"] = null;
								obj["relation"] = null;
							} else if (type3 === "graphic") {
								obj["tolerance"] = null;
								obj["relation"] = null;
							} else if (type3 === "adjacent") {
								obj["figure"] = null;
								obj["tolerance"] = null;
								obj["relation"] = null;
							}

							obj["name"] = layerDef[a].name;
							delete obj["relation"];
							outerObj["options"][type3][this.nowOption.alias]["relation"].push(obj);
						}
						this.getStructure()["definition"].push(outerObj);
					}
				}
			} else {
				// definition 키가
				// 배열은 맞는데 안에 값이 없음
				// 현재 체크박스를 체크했는지?
				if ($(check).is(":checked")) {
					// 체크함
					var outerObj = {
						"name" : this.nowCategory,
						"options" : {}
					};
					outerObj["options"][type3] = {};
					outerObj["options"][type3][this.nowOption.alias] = {
						"relation" : []
					};

					for (var a = 0; a < layerDef.length; a++) {
						var obj = {
							"filter" : null
						};
						if (type3 === "attribute") {
							obj["figure"] = null;
							obj["relation"] = null;
						} else if (type3 === "graphic") {
							obj["tolerance"] = null;
							obj["relation"] = null;
						} else if (type3 === "adjacent") {
							obj["figure"] = null;
							obj["tolerance"] = null;
							obj["relation"] = null;
						}

						obj["name"] = layerDef[a].name;
						delete obj["relation"];
						outerObj["options"][type3][this.nowOption.alias]["relation"].push(obj);
					}
					this.getStructure()["definition"].push(outerObj);
				}
			}
		}
	} else {
		// 모든 분류 체크가 아님
		// 검수 옵션 객체의 definition이 배열인지?
		if (Array.isArray(def)) {
			// 베열안에 값이 있는지?
			var isExist = false;
			if (def.length > 0) {
				for (var i = 0; i < def.length; i++) {
					if (this.nowCategory === def[i].name) {
						isExist = true;
						// 분류안에 options 키가 있는지
						if (def[i].hasOwnProperty("options")) {
							// 있음
							// 분류 안에 있는 options 키
							// def[i]["options"];
							// options 안에 그래픽, 애트리, 인접 키가 있는지?
							if (def[i]["options"].hasOwnProperty(type3)) {
								// 있을때
								// 키 안에 현재 검수 항목이 있는지?
								if (def[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
									// 있을때
									// 체크박스가 체크되어 있는지?
									if ($(check).is(":checked")) {
										var obj = {
											"filter" : null
										};
										if (type3 === "attribute") {
											obj["figure"] = null;
											obj["relation"] = null;
										} else if (type3 === "graphic") {
											obj["tolerance"] = null;
											obj["relation"] = null;
										} else if (type3 === "adjacent") {
											obj["figure"] = null;
											obj["tolerance"] = null;
											obj["relation"] = null;
										}
										if (sec) {
											obj["name"] = this.nowRelationCategory;
											delete obj["relation"];
											if (def[i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
												if (Array.isArray(def[i]["options"][type3][this.nowOption.alias]["relation"])) {
													def[i]["options"][type3][this.nowOption.alias]["relation"].push(obj);
												} else {
													def[i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
												}
											} else {
												def[i]["options"][type3][this.nowOption.alias] = {
													"relation" : [ obj ]
												};
											}
										} else {
											def[i]["options"][type3][this.nowOption.alias] = obj;
										}
									} else {
										// 체크 안됨
										// 검수 항목 키 삭제
										if (sec) {
											if (def[i].hasOwnProperty("options")) {
												if (def[i]["options"].hasOwnProperty(type3)) {
													// 검수 옵션 항목이 있다면
													if (def[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
														// 릴레이션 키가 있다면
														if (def[i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
															if (Array.isArray(def[i]["options"][type3][this.nowOption.alias]["relation"])) {
																var rel = def[i]["options"][type3][this.nowOption.alias]["relation"];
																for (var j = 0; j < rel.length; j++) {
																	if (rel[j]["name"] === this.nowRelationCategory) {
																		def[i]["options"][type3][this.nowOption.alias]["relation"].splice(
																				j, 1);
																	}
																}
																if (rel.length === 0) {
																	delete def[i]["options"][type3][this.nowOption.alias]["relation"];
																}
																var itemNames = Object.keys(def[i]["options"][type3][this.nowOption.alias]);
																if (itemNames.length === 0) {
																	delete def[i]["options"][type3][this.nowOption.alias];
																}
																var typeNames = Object.keys(def[i]["options"][type3]);
																if (typeNames.length === 0) {
																	delete def[i]["options"][type3];
																}
																var names = Object.keys(def[i]["options"]);
																if (names.length === 0) {
																	def.splice(i, 1);
																}
															}
														} else {
															// 릴레이션 키가 없다면
														}
													}
												}
											}
										} else {
											if (def[i].hasOwnProperty("options")) {
												if (def[i]["options"].hasOwnProperty(type3)) {
													if (def[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
														delete def[i]["options"][type3][this.nowOption.alias];
													}
												}
											}
											var optionKeys = Object.keys(def[i]["options"][type3]);
											if (optionKeys.length === 0) {
												delete def[i]["options"][type3];
											}
											var typeKeys = Object.keys(def[i]["options"]);
											if (typeKeys.length === 0) {
												delete def[i]["options"];
												def.splice(i, 1);
											}
										}
									}
								} else {
									// 타입 키안에 현재 검수 옵션이
									// 없을때
									// 쳌박스 쳌됨
									if ($(check).is(":checked")) {
										// 쳌박스 쳌됨
										var obj = {
											"filter" : null
										};
										if (type3 === "attribute") {
											obj["figure"] = null;
											obj["relation"] = null;
										} else if (type3 === "graphic") {
											obj["tolerance"] = null;
											obj["relation"] = null;
										} else if (type3 === "adjacent") {
											obj["figure"] = null;
											obj["tolerance"] = null;
											obj["relation"] = null;
										}
										if (sec) {
											obj["name"] = this.nowRelationCategory;
											delete obj["relation"];
											if (def[i]["options"][type3] === undefined) {
												def[i]["options"][type3] = {};
											}
											def[i]["options"][type3][this.nowOption.alias] = {
												"relation" : [ obj ]
											};
										} else {
											def[i]["options"][type3][this.nowOption.alias] = obj;
										}
									} else {
										// 체크 안됨
										// 검수 항목 키 삭제
										if (sec) {

										} else {
											if (def[i].hasOwnProperty("options")) {
												if (def[i]["options"].hasOwnProperty(type3)) {
													if (def[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
														delete def[i]["options"][type3][this.nowOption.alias];
													}
												}
											}
											var optionKeys = Object.keys(def[i]["options"][type3]);
											if (optionKeys.length === 0) {
												delete def[i]["options"][type3];
											}
											var typeKeys = Object.keys(def[i]["options"]);
											if (typeKeys.length === 0) {
												delete def[i]["options"];
												def.splice(i, 1);
											}
										}
									}
								}
							} else {
								// 옵션안에 애트리뷰트 그래픽 등의 키가
								// 없을때
								if ($(check).is(":checked")) {
									var obj = {
										"filter" : null
									};
									if (type3 === "attribute") {
										obj["figure"] = null;
										obj["relation"] = null;
									} else if (type3 === "graphic") {
										obj["tolerance"] = null;
										obj["relation"] = null;
									} else if (type3 === "adjacent") {
										obj["figure"] = null;
										obj["tolerance"] = null;
										obj["relation"] = null;
									}

									if (sec) {
										obj["name"] = this.nowRelationCategory;
										delete obj["relation"];
										if (!def[i].hasOwnProperty("options")) {
											def[i]["options"] = {};
										}
										if (!def[i]["options"].hasOwnProperty(type3)) {
											def[i]["options"][type3] = {};
										}
										if (!def[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
											def[i]["options"][type3][this.nowOption.alias] = {};
										}
										if (def[i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
											if (Array.isArray(def[i]["options"][type3][this.nowOption.alias]["relation"])) {
												def[i]["options"][type3][this.nowOption.alias]["relation"].push(obj);
											} else {
												def[i]["options"][type3][this.nowOption.alias]["relation"] = [ obj ];
											}
										} else {
											def[i]["options"][type3][this.nowOption.alias] = {
												"relation" : [ obj ]
											};
										}
									} else {
										if (def[i]["options"].hasOwnProperty(type3)) {
											if (def[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
												def[i]["options"][type3][this.nowOption.alias] = obj;
											}
										} else {
											if (def[i]["options"][type3] === undefined) {
												def[i]["options"][type3] = {};
											}
											def[i]["options"][type3][this.nowOption.alias] = obj;
										}
									}
								} else {
									// 체크 안됨
									// 검수 항목 키 삭제
									if (def[i].hasOwnProperty("options")) {
										if (def[i]["options"].hasOwnProperty(type3)) {
											if (def[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
												delete def[i]["options"][type3][this.nowOption.alias];
											}
										}
									}
									var optionKeys = Object.keys(def[i]["options"][type3]);
									if (optionKeys.length === 0) {
										delete def[i]["options"][type3];
									}
									var typeKeys = Object.keys(def[i]["options"]);
									if (typeKeys.length === 0) {
										delete def[i]["options"];
										def.splice(i, 1);
									}
								}
							}
						}
					}
				}
				if (isExist === false) {
					// definition을 다돌았지만 없었음 새로 입력
					if ($(check).is(":checked")) {
						// 체크함
						var obj = {
							"filter" : null
						};
						if (type3 === "attribute") {
							obj["figure"] = null;
							obj["relation"] = null;
						} else if (type3 === "graphic") {
							obj["tolerance"] = null;
							obj["relation"] = null;
						} else if (type3 === "adjacent") {
							obj["figure"] = null;
							obj["tolerance"] = null;
							obj["relation"] = null;
						}

						if (sec) {
							obj["name"] = this.nowRelationCategory;
							delete obj["relation"];
							var outerObj = {
								"name" : this.nowCategory,
								"options" : {}
							};
							outerObj["options"][type3] = {};
							outerObj["options"][type3][this.nowOption.alias] = {
								"relation" : [ obj ]
							}
						} else {
							var type3Obj = {};
							type3Obj[this.nowOption.alias] = obj;
							var options = {};
							options[type3] = type3Obj;
							var outerObj = {
								"name" : this.nowCategory
							};
							outerObj["options"] = options;
						}
						this.getStructure()["definition"].push(outerObj);
					}
				}
			} else {
				// definition 키가
				// 배열은 맞는데 안에 값이 없음
				// 현재 체크박스를 체크했는지?
				if ($(check).is(":checked")) {
					// 체크함
					var obj = {
						"filter" : null
					};
					if (type3 === "attribute") {
						obj["figure"] = null;
						obj["relation"] = null;
					} else if (type3 === "graphic") {
						obj["tolerance"] = null;
						obj["relation"] = null;
					} else if (type3 === "adjacent") {
						obj["figure"] = null;
						obj["tolerance"] = null;
						obj["relation"] = null;
					}

					if (sec) {
						obj["name"] = this.nowRelationCategory;
						delete obj["relation"];
						var outerObj = {
							"name" : this.nowCategory,
							"options" : {}
						};
						outerObj["options"][type3] = {};
						outerObj["options"][type3][this.nowOption.alias] = {
							"relation" : [ obj ]
						}
					} else {
						var type3Obj = {};
						type3Obj[this.nowOption.alias] = obj;
						var options = {};
						options[type3] = type3Obj;
						var outerObj = {
							"name" : this.nowCategory
						};
						outerObj["options"] = options;
					}
					this.getStructure()["definition"].push(outerObj);
				}
			}
		}
	}
};

gb.embed.OptionDefinition.prototype.addLayerCodeFilter = function(btn) {
	var codeCol1 = $("<div>").addClass("col-md-1").text("코드:");
	var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-filtercode");
	var cat = this.getLayerDefinition().getStructure();

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	var category;
	if (sec) {
		category = this.nowRelationCategory;
	} else {
		category = this.nowCategory;
	}

	if (Array.isArray(cat)) {
		for (var i = 0; i < cat.length; i++) {
			if (cat[i].name === category) {
				var layers = cat[i].layers;
				var allCode = $("<option>").text("모두 적용").attr("geom", "none");
				$(codeSelect).append(allCode);
				for (var j = 0; j < layers.length; j++) {
					var option = $("<option>").text(layers[j].code).attr("geom", layers[j].geometry);
					$(codeSelect).append(option);
				}
			}
		}
	}
	var codeCol2 = $("<div>").addClass("col-md-7").append(codeSelect);

	var delBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-deletelayerfilter").text(
			"레이어 코드 삭제").css("width", "100%");
	var delBtnCol = $("<div>").addClass("col-md-2").append(delBtn);

	var addBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-addfilter").css({
		"width" : "100%"
	}).text("필터 추가");
	var addBtnCol = $("<div>").addClass("col-md-2").append(addBtn);

	var addFilterRow = $("<div>").addClass("row").append(codeCol1).append(codeCol2).append(delBtnCol).append(addBtnCol);
	var filterArea = $("<div>").addClass("col-md-12").addClass("gb-optiondefinition-filterarea");
	var filterAreaRow = $("<div>").addClass("row").append(filterArea);
	var totalArea = $("<div>").addClass("well").append(addFilterRow).append(filterAreaRow);
	$(btn).parents().eq(2).find(".gb-optiondefinition-tuplearea").append(totalArea);
	$(".gb-optiondefinition-select-filtercode").trigger("change");
};

gb.embed.OptionDefinition.prototype.addLayerCodeFigure = function(btn) {
	var codeCol1 = $("<div>").addClass("col-md-1").text("코드:");
	var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-figurecode");
	var cat = this.getLayerDefinition().getStructure();

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	var category;
	if (sec) {
		category = this.nowRelationCategory;
	} else {
		category = this.nowCategory;
	}

	if (Array.isArray(cat)) {
		for (var i = 0; i < cat.length; i++) {
			if (cat[i].name === category) {
				var layers = cat[i].layers;
				var allCode = $("<option>").text("모두 적용").attr("geom", "none");
				$(codeSelect).append(allCode);
				for (var j = 0; j < layers.length; j++) {
					var option = $("<option>").text(layers[j].code).attr("geom", layers[j].geometry);
					$(codeSelect).append(option);
				}
			}
		}
	}
	var codeCol2 = $("<div>").addClass("col-md-7").append(codeSelect);

	var delBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-deletelayerfigure").text(
			"레이어 코드 삭제").css("width", "100%");
	var delBtnCol = $("<div>").addClass("col-md-2").append(delBtn)

	var addBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-addfigure").text("속성 추가").css(
			"width", "100%");
	var addBtnCol = $("<div>").addClass("col-md-2").append(addBtn);

	var addFigureRow = $("<div>").addClass("row").append(codeCol1).append(codeCol2).append(delBtnCol).append(addBtnCol);
	var figureArea = $("<div>").addClass("col-md-12").addClass("gb-optiondefinition-figurearea");
	var figureAreaRow = $("<div>").addClass("row").append(figureArea);
	var totalArea = $("<div>").addClass("well").append(addFigureRow).append(figureAreaRow);
	$(btn).parents().eq(2).find(".gb-optiondefinition-tuplearea").append(totalArea);
	$(".gb-optiondefinition-select-figurecode").trigger("change");
};

gb.embed.OptionDefinition.prototype.addLayerCodeTolerance = function(btn) {
	var codeCol1 = $("<div>").addClass("col-md-1").text("코드:");
	var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-tolerancecode");
	var cat = this.getLayerDefinition().getStructure();

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	var category;
	if (sec) {
		category = this.nowRelationCategory;
	} else {
		category = this.nowCategory;
	}

	if (Array.isArray(cat)) {
		for (var i = 0; i < cat.length; i++) {
			if (cat[i].name === category) {
				var layers = cat[i].layers;
				var allCode = $("<option>").text("모두 적용").attr("geom", "none");
				$(codeSelect).append(allCode);
				for (var j = 0; j < layers.length; j++) {
					var option = $("<option>").text(layers[j].code).attr("geom", layers[j].geometry);
					$(codeSelect).append(option);
				}
			}
		}
	}
	var codeCol2 = $("<div>").addClass("col-md-9").append(codeSelect);

	var delBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-deletelayertolerance").text(
			"레이어 코드 삭제").css("width", "100%");
	var delBtnCol = $("<div>").addClass("col-md-2").append(delBtn);

	/*
	 * var addBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-addtolerance").text("조건
	 * 추가").css( "width", "100%"); var addBtnCol = $("<div>").addClass("col-md-2").append(addBtn);
	 */

	var addFigureRow = $("<div>").addClass("row").append(codeCol1).append(codeCol2).append(delBtnCol);
	var figureArea = $("<div>").addClass("col-md-12").addClass("gb-optiondefinition-tolerancearea");
	// ============================
	var optItem = this.optItem[this.nowOption.alias];
	var row = $("<div>").addClass("row");

	if (sec) {

		var numCol1 = $("<div>").addClass("col-md-1").text("수치:");
		var inputNum = $("<input>").attr({
			"type" : "number",
			"placeholder" : "숫자형 기준값"
		}).addClass("form-control").addClass("gb-optiondefinition-input-tolerancevalue");
		if (!optItem.relation.tolerance.value) {
			$(inputNum).prop("disabled", true);
		}
		var numCol2 = $("<div>").addClass("col-md-3").append(inputNum);
		$(row).append(numCol1).append(numCol2);

		var codeCol1 = $("<div>").addClass("col-md-1").text("조건:");
		var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-tolerancecondition");
		var optionEqual = $("<option>").text("같음").attr("value", "equal");
		var optionOver = $("<option>").text("초과").attr("value", "over");
		var optionUnder = $("<option>").text("미만").attr("value", "under");
		$(codeSelect).append(optionEqual).append(optionOver).append(optionUnder);
		if (!optItem.relation.tolerance.condition) {
			$(codeSelect).prop("disabled", true);
		}
		var codeCol2 = $("<div>").addClass("col-md-3").append(codeSelect);
		$(row).append(codeCol1).append(codeCol2);

		var numCol1 = $("<div>").addClass("col-md-1").text("간격:");
		var inputNum = $("<input>").attr({
			"type" : "number",
			"placeholder" : "숫자형 기준값"
		}).addClass("form-control").addClass("gb-optiondefinition-input-toleranceinterval");
		if (!optItem.relation.tolerance.interval) {
			$(inputNum).prop("disabled", true);
		}
		var numCol2 = $("<div>").addClass("col-md-3").append(inputNum);

		$(row).append(numCol1).append(numCol2);

		$(figureArea).append(row);
	} else {

		var numCol1 = $("<div>").addClass("col-md-1").text("수치:");
		var inputNum = $("<input>").attr({
			"type" : "number",
			"placeholder" : "숫자형 기준값"
		}).addClass("form-control").addClass("gb-optiondefinition-input-tolerancevalue");
		if (!optItem.tolerance.value) {
			$(inputNum).prop("disabled", true);
		}
		var numCol2 = $("<div>").addClass("col-md-3").append(inputNum);

		$(row).append(numCol1).append(numCol2);

		var codeCol1 = $("<div>").addClass("col-md-1").text("조건:");
		var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-tolerancecondition");
		var optionEqual = $("<option>").text("같음").attr("value", "equal");
		var optionOver = $("<option>").text("초과").attr("value", "over");
		var optionUnder = $("<option>").text("미만").attr("value", "under");
		$(codeSelect).append(optionEqual).append(optionOver).append(optionUnder);
		if (!optItem.tolerance.condition) {
			$(codeSelect).prop("disabled", true);
		}
		var codeCol2 = $("<div>").addClass("col-md-3").append(codeSelect);
		$(row).append(codeCol1).append(codeCol2);

		var numCol1 = $("<div>").addClass("col-md-1").text("간격:");
		var inputNum = $("<input>").attr({
			"type" : "number",
			"placeholder" : "숫자형 기준값"
		}).addClass("form-control").addClass("gb-optiondefinition-input-toleranceinterval");
		if (!optItem.tolerance.interval) {
			$(inputNum).prop("disabled", true);
		}
		var numCol2 = $("<div>").addClass("col-md-3").append(inputNum);

		$(row).append(numCol1).append(numCol2);

		$(figureArea).append(row);
	}

	/*
	 * var btnDel = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-delete-row").text("조건
	 * 삭제").css( "width", "100%"); var delCol1 = $("<div>").addClass("col-md-2").append(btnDel);
	 * $(row).append(delCol1);
	 */

	// ============================
	var figureAreaRow = $("<div>").addClass("row").append(figureArea);
	var totalArea = $("<div>").addClass("well").append(addFigureRow).append(figureAreaRow);
	$(btn).parents().eq(2).find(".gb-optiondefinition-tuplearea").append(totalArea);
	$(".gb-optiondefinition-select-tolerancecode").trigger("change");
};

gb.embed.OptionDefinition.prototype.selectFilterCode = function(sel) {

	var nowOption = this.optItem[this.nowOption.alias];
	if (nowOption === undefined) {
		return;
	}

	var sec = false;
	if (this.nowDetailCategory !== undefined) {
		if (this.nowDetailCategory.alias === "relation" && this.nowRelationCategory !== undefined) {
			sec = true;
		}
	}

	// 레이어 인덱스
	var layerIdx = $(sel).parents().eq(2).index();
	// 레이어 코드
	var layerCode = null;
	if (!$(sel).prop("disabled")) {
		layerCode = $(sel).find("option:selected").attr("geom") === "none" ? null : $(sel).val();
	}
	// 검수 항목 정보
	var optItem = this.optItem[this.nowOption.alias];
	// 검수 타입
	var type3 = optItem["purpose"];
	// 현재 코드에 설정된 필터 갯수
	var filters = $(sel).parents().eq(2).find(".gb-optiondefinition-filterarea").children();
	// 현재 레이어코드에 필터가 있는지?
	if (filters.length > 0) {
		var strc = this.getStructure();
		if (Array.isArray(strc["definition"])) {
			var isExist = false;
			for (var i = 0; i < strc["definition"].length; i++) {
				// 현재 검수 옵션에 현재 분류가 있는지?
				if (this.nowCategory === strc["definition"][i].name) {
					isExist = true;
					// 검수 옵션에 현재 타입 키가 있는지 애트리뷰트, 그래픽..
					if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
						// 현재 검수 항목이 들어있는지?
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							console.log("현재 레이어 코드:" + $(sel).val());
							// 릴레이션 레이어 필터일때
							if (sec) {
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
									var relation = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
									if (Array.isArray(relation)) {
										for (var j = 0; j < relation.length; j++) {
											if (relation[j].name === this.nowRelationCategory) {
												// 필터키를 가지고 있는지?
												if (relation[j].hasOwnProperty("filter")) {
													if (Array.isArray(relation[j]["filter"])) {
														for (var k = 0; k < relation[j]["filter"].length; k++) {
															// 코드 키를 가지고 있는지?
															if (relation[j]["filter"][k].hasOwnProperty("code")) {
																relation[j]["filter"][k]["code"] = layerCode;
															} else {
																// 코드 키를 가지고 있지
																// 않다면
															}
														}
													}
												} else {
													// 필터키를 가지고 있지 않다면
												}
											}
										}
									}
								}
							} else {
								// 그냥 레이어 필터일때
								// 필터 키가 들어있는지
								if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("filter")) {
									var filters = strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"];
									if (Array.isArray(filters)) {
										if (filters[layerIdx].hasOwnProperty("code")) {
											strc["definition"][i]["options"][type3][this.nowOption.alias]["filter"][layerIdx]["code"] = layerCode;
										}
									}
								}
							}
						}
					}
				}
			}
			/*
			 * if (!isExist) { var filterObj = { "code" : layerCode, "attribute" : [] };
			 * var optionsObj = { "filter" : [ filterObj ] }; var typeObj = {};
			 * typeObj[type3] = {}; typeObj[type3][this.nowOption.alias] =
			 * optionsObj;
			 * 
			 * var definitionObj = { "name" : this.nowCategory, "options" :
			 * typeObj }; this.getStructure()["definition"].push(definitionObj); }
			 */
		}
	}
};

gb.embed.OptionDefinition.prototype.setQACategory = function(qa) {
	this.qaCat = qa;
	console.log(this.getQACategory());
};

gb.embed.OptionDefinition.prototype.getQACategory = function() {
	return this.qaCat;
};

gb.embed.OptionDefinition.prototype.setQAVersion = function(qa) {
	this.qaVer = qa;
	console.log(this.getQAVersion());
};

gb.embed.OptionDefinition.prototype.getQAVersion = function() {
	return this.qaVer;
};

gb.embed.OptionDefinition.prototype.addFilterRow = function(btn) {
	if (this.nowOption === undefined) {
		return;
	}
	var optItem = this.optItem[this.nowOption.alias];
	var row = $("<div>").addClass("row");
	if (optItem.filter.key) {
		var attrCol1 = $("<div>").addClass("col-md-1").text("속성명:");
		var inputAttr = $("<input>").attr({
			"type" : "text",
			"placeholder" : "속성명 EX) 재질"
		}).addClass("form-control").addClass("gb-optiondefinition-input-filterkey");
		var attrCol2 = $("<div>").addClass("col-md-2").append(inputAttr);

		$(row).append(attrCol1).append(attrCol2);
	}
	if (optItem.filter.values) {
		var filterCol1 = $("<div>").addClass("col-md-1").text("허용값:");
		var inputValues = $("<input>").attr({
			"type" : "text",
			"placeholder" : "허용값들을 콤마(,)로 구분하여 입력 EX) 1,2,3"
		}).addClass("form-control").addClass("gb-optiondefinition-input-filtervalues");
		var filterCol2 = $("<div>").addClass("col-md-6").append(inputValues);
		$(row).append(filterCol1).append(filterCol2);
	}
	var btnDel = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-deletefilterrow").text("필터 삭제")
			.css("width", "100%");
	var delCol1 = $("<div>").addClass("col-md-2").append(btnDel);
	$(row).append(delCol1);

	$(btn).parents().eq(2).find(".gb-optiondefinition-filterarea").append(row);

};

gb.embed.OptionDefinition.prototype.addFigureRow = function(btn) {
	if (this.nowOption === undefined) {
		return;
	}
	var optItem = this.optItem[this.nowOption.alias];
	var row = $("<div>").addClass("row");

	var attrCol1 = $("<div>").addClass("col-md-1").text("속성명:");
	var inputAttr = $("<input>").attr({
		"type" : "text",
		"placeholder" : "속성명 EX) 재질"
	}).addClass("form-control").addClass("gb-optiondefinition-input-figurekey");
	if (!optItem.figure.key) {
		$(inputAttr).prop("disabled", true);
	}
	var attrCol2 = $("<div>").addClass("col-md-2").append(inputAttr);

	$(row).append(attrCol1).append(attrCol2);

	var filterCol1 = $("<div>").addClass("col-md-1").text("허용값:");
	var inputValues = $("<input>").attr({
		"type" : "text",
		"placeholder" : "허용값들을 콤마(,)로 구분하여 입력 EX) 1,2,3"
	}).addClass("form-control").addClass("gb-optiondefinition-input-figurevalues");
	if (!optItem.figure.values) {
		$(inputValues).prop("disabled", true);
	}
	var filterCol2 = $("<div>").addClass("col-md-8").append(inputValues);
	$(row).append(filterCol1).append(filterCol2);

	var row2 = $("<div>").addClass("row");

	var numCol1 = $("<div>").addClass("col-md-1").text("수치:");
	var inputNum = $("<input>").attr({
		"type" : "number",
		"placeholder" : "숫자형 기준값"
	}).addClass("form-control").addClass("gb-optiondefinition-input-figurenumber");
	if (!optItem.figure.number) {
		$(inputNum).prop("disabled", true);
	}
	var numCol2 = $("<div>").addClass("col-md-3").append(inputNum);

	$(row2).append(numCol1).append(numCol2);

	var codeCol1 = $("<div>").addClass("col-md-1").text("조건:");
	var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-figurecondition");
	var optionEqual = $("<option>").text("같음").attr("value", "equal");
	var optionOver = $("<option>").text("초과").attr("value", "over");
	var optionUnder = $("<option>").text("미만").attr("value", "under");
	$(codeSelect).append(optionEqual).append(optionOver).append(optionUnder);
	if (!optItem.figure.condition) {
		$(codeSelect).prop("disabled", true);
	}
	var codeCol2 = $("<div>").addClass("col-md-2").append(codeSelect);
	$(row2).append(codeCol1).append(codeCol2);

	var numCol1 = $("<div>").addClass("col-md-1").text("간격:");
	var inputNum = $("<input>").attr({
		"type" : "number",
		"placeholder" : "숫자형 기준값"
	}).addClass("form-control").addClass("gb-optiondefinition-input-figureinterval");
	if (!optItem.figure.interval) {
		$(inputNum).prop("disabled", true);
	}
	var numCol2 = $("<div>").addClass("col-md-2").append(inputNum);

	$(row2).append(numCol1).append(numCol2);

	var btnDel = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-deletefigurerow").text("속성 삭제")
			.css("width", "100%");
	var delCol1 = $("<div>").addClass("col-md-2").append(btnDel);
	$(row2).append(delCol1);

	var outerRow = $("<div>").append(row).append(row2);
	$(btn).parents().eq(2).find(".gb-optiondefinition-figurearea").append(outerRow);
};

gb.embed.OptionDefinition.prototype.addToleranceRow = function(btn) {
	if (this.nowOption === undefined) {
		return;
	}
	var optItem = this.optItem[this.nowOption.alias];
	var row = $("<div>").addClass("row");

	var numCol1 = $("<div>").addClass("col-md-1").text("수치:");
	var inputNum = $("<input>").attr({
		"type" : "number",
		"placeholder" : "숫자형 기준값"
	}).addClass("form-control").addClass("gb-optiondefinition-input-tolerancevalue");
	if (!optItem.tolerance.value) {
		$(inputNum).prop("disabled", true);
	}
	var numCol2 = $("<div>").addClass("col-md-3").append(inputNum);

	$(row).append(numCol1).append(numCol2);

	var codeCol1 = $("<div>").addClass("col-md-1").text("조건:");
	var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-tolerancecondition");
	var optionEqual = $("<option>").text("같음").attr("value", "equal");
	var optionOver = $("<option>").text("초과").attr("value", "over");
	var optionUnder = $("<option>").text("미만").attr("value", "under");
	$(codeSelect).append(optionEqual).append(optionOver).append(optionUnder);
	if (!optItem.tolerance.condition) {
		$(codeSelect).prop("disabled", true);
	}
	var codeCol2 = $("<div>").addClass("col-md-2").append(codeSelect);
	$(row).append(codeCol1).append(codeCol2);

	var numCol1 = $("<div>").addClass("col-md-1").text("간격:");
	var inputNum = $("<input>").attr({
		"type" : "number",
		"placeholder" : "숫자형 기준값"
	}).addClass("form-control").addClass("gb-optiondefinition-input-toleranceinterval");
	if (!optItem.tolerance.interval) {
		$(inputNum).prop("disabled", true);
	}
	var numCol2 = $("<div>").addClass("col-md-2").append(inputNum);

	$(row).append(numCol1).append(numCol2);

	var btnDel = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-deletetolerancerow").text("조건 삭제")
			.css("width", "100%");
	var delCol1 = $("<div>").addClass("col-md-2").append(btnDel);
	$(row).append(delCol1);

	$(btn).parents().eq(2).find(".gb-optiondefinition-tolerancearea").append(row);
};

gb.embed.OptionDefinition.prototype.setBorderLayer = function(sel) {
	var opt = $(sel).find("option:selected");
	var strc = this.getStructure();
	if ($(opt).attr("geom") !== "none") {
		strc["border"] = {
			"code" : $(opt).text(),
			"geometry" : $(opt).attr("geom")
		};
	} else if ($(opt).attr("geom") === "none") {
		strc["border"] = null
	}
	console.log(this.getStructure());
};

gb.embed.OptionDefinition.prototype.updateNavigation = function(idx, rel) {
	// 인덱스와 레이블을 받아서 해당 인덱스에 레이블 텍스트와 애트리뷰트에 밸류를 넣고 이후의 엘리먼트를 삭제한다.
	// 제일 마지막 엘리먼트에 링크를 지운다
	$(this.navi).empty();
	for (var i = 0; i < (idx + 1); i++) {
		if (i === 0) {
			if (i === idx) {
				var li = $("<li>").addClass("active").text("분류 설정");
				$(this.navi).append(li);
			} else {
				var btn = $("<button>").addClass("btn").addClass("btn-link").addClass("gb-optiondefinition-navi-category");
				if (this.nowCategory !== undefined) {
					$(btn).text(this.nowCategory);
				} else {
					$(btn).text("분류 설정");
				}
				var li = $("<li>").append(btn);
				$(this.navi).append(li);
			}
		} else if (i === 1) {
			if (i === idx) {
				var li = $("<li>").addClass("active").text("검수 항목 설정");
				$(this.navi).append(li);
			} else {
				var btn = $("<button>").addClass("btn").addClass("btn-link").addClass("gb-optiondefinition-navi-option");
				if (this.nowOption !== undefined) {
					$(btn).text(this.nowOption.title);
					$(btn).attr("value", this.nowOption.alias);
				} else {
					$(btn).text("검수 항목 설정");
				}
				var li = $("<li>").append(btn);
				$(this.navi).append(li);
			}
		} else if (i === 2) {
			if (i === idx) {
				var li = $("<li>").addClass("active").text("검수 세부 설정 선택");
				$(this.navi).append(li);
			} else {
				var btn = $("<button>").addClass("btn").addClass("btn-link").addClass("gb-optiondefinition-navi-detailcategory");
				if (this.nowDetailCategory !== undefined) {
					$(btn).text(this.nowDetailCategory.title);
				} else {
					$(btn).text("검수 세부 설정 선택");
				}
				var li = $("<li>").append(btn);
				$(this.navi).append(li);
			}
		} else if (i === 3) {
			if (i === idx) {
				if (rel) {
					var li = $("<li>").addClass("active").text("관계 레이어 선택");
					$(this.navi).append(li);
				} else {
					var li = $("<li>").addClass("active").text("검수 세부 설정 입력");
					$(this.navi).append(li);
				}
			} else {
				var btn = $("<button>").addClass("btn").addClass("btn-link").addClass("gb-optiondefinition-navi-relationcategory");
				if (this.nowRelationCategory !== undefined) {
					$(btn).text(this.nowRelationCategory);
				} else {
					$(btn).text("관계 레이어 세부 설정 선택");
				}
				var li = $("<li>").append(btn);
				$(this.navi).append(li);
			}
		} else if (i === 4) {
			if (i === idx) {
				var li = $("<li>").addClass("active").text("관계 레이어 세부 설정 선택");
				$(this.navi).append(li);
			} else {
				var btn = $("<button>").addClass("btn").addClass("btn-link").addClass("gb-optiondefinition-navi-relationdetailcategory");
				if (this.nowRelationDetailCategory !== undefined) {
					$(btn).text(this.nowRelationDetailCategory.title);
				} else {
					$(btn).text("관계 레이어 세부 설정 선택");
				}
				var li = $("<li>").append(btn);
				$(this.navi).append(li);
			}
		} else if (i === 5) {
			if (i === idx) {
				var li = $("<li>").addClass("active").text("관계 레이어 세부 설정 입력");
				$(this.navi).append(li);
			}
		}
	}
	console.log(this.nowCategory);
	console.log(this.nowOption);
	console.log(this.nowDetailCategory);
	console.log(this.nowRelationCategory);
	console.log(this.nowRelationDetailCategory);
};

gb.embed.OptionDefinition.prototype.setMessage = function(msg) {
	$(this.message).empty();
	$(this.message).text(msg);
};

gb.embed.OptionDefinition.prototype.printCategory = function(rel) {
	var className = "gb-optiondefinition-btn-category";
	var alias;
	var optItem;
	var type3;
	if (!rel) {
		this.nowCategory = undefined;
		this.nowOption = undefined;
		this.nowDetailCategory = undefined;
		this.nowRelationCategory = undefined;
		this.nowRelationDetailCategory = undefined;
		this.updateNavigation(0);
	} else {
		var alias = this.nowOption.alias;
		var optItem = this.optItem[alias];
		var type3 = optItem["purpose"];
		this.nowRelationCategory = undefined;
		this.nowRelationDetailCategory = undefined;
		this.updateNavigation(3, true);
		className = "gb-optiondefinition-btn-relationcategory";
	}
	this.setMessage("검수할 분류를 선택하세요.");
	$(this.optionArea).empty();
	if (rel) {
		var allBtn = $("<button>").text("모든 분류").addClass("btn").addClass("btn-default").addClass(
				"gb-optiondefinition-btn-relationcategory-all").css("width", "100%");
		var col = $("<div>").addClass("col-md-12").css({
			"margin-top" : "5px",
			"margin-bottom" : "5px",
			"padding-left" : "5px",
			"padding-right" : "5px"
		}).addClass("text-right").append(allBtn);
		var row = $("<div>").addClass("row").append(col);
		$(this.optionArea).append(row);
	}
	var layers = this.getLayerDefinition().getStructure();
	var def = this.getStructure().definition;
	if (rel) {
		for (var i = 0; i < this.getStructure().definition.length; i++) {
			if (this.getStructure().definition[i].name === this.nowCategory) {
				if (this.getStructure().definition[i].hasOwnProperty("options")) {
					if (this.getStructure().definition[i]["options"].hasOwnProperty(type3)) {
						if (this.getStructure().definition[i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							if (this.getStructure().definition[i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
								if (Array.isArray(this.getStructure().definition[i]["options"][type3][this.nowOption.alias]["relation"])) {
									def = this.getStructure().definition[i]["options"][type3][this.nowOption.alias]["relation"];
									break;
								} else {
									def = [];
								}
							} else {
								def = [];
							}
						} else {
							def = [];
						}
					} else {
						def = [];
					}
				} else {
					def = [];
				}
			} else {
				def = [];
			}
		}
	}

	if (Array.isArray(layers)) {
		var catArea = $("<div>").addClass("row");
		for (var i = 0; i < layers.length; i++) {
			var btn = $("<button>").addClass("btn").addClass("btn-default").addClass(className).css("width", "100%").text(layers[i].name);
			for (var j = 0; j < def.length; j++) {
				if (layers[i].name === def[j].name) {
					$(btn).removeClass("btn-default");
					$(btn).addClass("btn-primary");
				}
			}
			var colArea = $("<div>").addClass("col-md-3").css({
				"margin-top" : "5px",
				"margin-bottom" : "5px",
				"padding-left" : "5px",
				"padding-right" : "5px"
			}).append(btn);
			$(catArea).append(colArea);
		}
		$(this.optionArea).append(catArea);
	}
};

gb.embed.OptionDefinition.prototype.printOption = function(cat, navi) {
	if (!navi) {
		this.nowCategory = $(cat).text();
	}
	this.nowOption = undefined;
	this.nowDetailCategory = undefined;
	this.nowRelationCategory = undefined;
	this.nowRelationDetailCategory = undefined;

	this.updateNavigation(1);
	this.setMessage("검수 항목을 선택하세요.");
	$(this.optionArea).empty();
	var opArea = $("<div>").addClass("row");
	var keys = Object.keys(this.optItem);
	for (var i = 0; i < keys.length; i++) {
		var flag = false;
		if (this.optItem[keys[i]].category.indexOf(this.getQACategory()) !== -1) {
			if (this.getQACategory() === "numetrical") {
				if (this.optItem[keys[i]].version.indexOf(this.getQAVersion()) !== -1) {
					flag = true;
				}
			} else {
				flag = true;
			}
		}
		if (flag) {
			var optBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-option").css("width",
					"100%").text(this.optItem[keys[i]].title).attr("value", keys[i]);
			var strc = this.getStructure();
			// 현재 버튼이 도곽선 설정인지
			if (this.optItem[keys[i]].alias === "BorderLayer") {
				// 도곽선이 설정 되어있는지?
				if (strc["border"] !== null && strc["border"] !== undefined) {
					if ($(optBtn).hasClass("btn-default")) {
						$(optBtn).removeClass("btn-default");
					}
					$(optBtn).addClass("btn-primary");
				}
			} else {
				for (var j = 0; j < strc["definition"].length; j++) {
					// 옵션중에 분류가 현재 선택한 분류랑 같은지?
					if (strc["definition"][j].name === this.nowCategory) {
						if (strc["definition"][j]["options"] !== null && strc["definition"][j]["options"] !== undefined) {
							// 그중에 현재 옵션의 목적이 3 타입중 어떤건지?
							if (strc["definition"][j]["options"].hasOwnProperty("attribute")) {
								if (strc["definition"][j]["options"]["attribute"] !== undefined
										&& strc["definition"][j]["options"]["attribute"] !== null) {
									var type = strc["definition"][j]["options"]["attribute"];
									var keysSecond = Object.keys(type);
									if (keysSecond.indexOf(this.optItem[keys[i]].alias) !== -1) {
										// var icon =
										// $("<i>").addClass("fas").addClass("fa-check");
										// $(optBtn).prepend(icon);
										if ($(optBtn).hasClass("btn-default")) {
											$(optBtn).removeClass("btn-default");
										}
										$(optBtn).addClass("btn-primary");
										continue;
									}
								}
							}
							if (strc["definition"][j]["options"].hasOwnProperty("graphic")) {
								if (strc["definition"][j]["options"]["graphic"] !== undefined
										&& strc["definition"][j]["options"]["graphic"] !== null) {
									var type = strc["definition"][j]["options"]["graphic"];
									var keysSecond = Object.keys(type);
									if (keysSecond.indexOf(this.optItem[keys[i]].alias) !== -1) {
										// var icon =
										// $("<i>").addClass("fas").addClass("fa-check");
										// $(optBtn).prepend(icon);
										if ($(optBtn).hasClass("btn-default")) {
											$(optBtn).removeClass("btn-default");
										}
										$(optBtn).addClass("btn-primary");
										continue;
									}
								}
							}
							if (strc["definition"][j]["options"].hasOwnProperty("adjacent")) {
								if (strc["definition"][j]["options"]["adjacent"] !== undefined
										&& strc["definition"][j]["options"]["adjacent"] !== null) {
									var type = strc["definition"][j]["options"]["adjacent"];
									var keysSecond = Object.keys(type);
									if (keysSecond.indexOf(this.optItem[keys[i]].alias) !== -1) {
										// var icon =
										// $("<i>").addClass("fas").addClass("fa-check");
										// $(optBtn).prepend(icon);
										if ($(optBtn).hasClass("btn-default")) {
											$(optBtn).removeClass("btn-default");
										}
										$(optBtn).addClass("btn-primary");
										continue;
									}
								}
							}
						}
					}
				}
			}
			var colArea = $("<div>").addClass("col-md-3").css({
				"margin-top" : "5px",
				"margin-bottom" : "5px",
				"padding-left" : "5px",
				"padding-right" : "5px"
			}).append(optBtn);
			$(opArea).append(colArea);
		}
	}
	$(this.optionArea).append(opArea);

};

gb.embed.OptionDefinition.prototype.printOptionCategory = function(opt, navi, sec, all) {
	if (!navi && !sec) {
		this.nowOption = {
			"title" : $(opt).text(),
			"alias" : $(opt).attr("value")
		};
		this.nowDetailCategory = undefined;
		this.nowRelationCategory = undefined;
		this.nowRelationDetailCategory = undefined;
		this.updateNavigation(2);
	} else if (!navi && sec) {
		this.nowRelationCategory = $(opt).text();
		this.nowRelationDetailCategory = undefined;
		this.updateNavigation(4, true);
	} else if (navi && !sec) {
		this.nowDetailCategory = undefined;
		this.nowRelationCategory = undefined;
		this.nowRelationDetailCategory = undefined;
		this.updateNavigation(2);
	} else if (navi && sec) {
		this.nowRelationDetailCategory = undefined;
		this.updateNavigation(4, true);
	}
	$(this.optionArea).empty();

	if (this.nowOption.alias === "BorderLayer" && !sec) {
		this.setMessage("도곽선으로 사용할 레이어 코드를 선택하세요.");
		var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-border");
		var option = $("<option>").attr("geom", "none").text("미설정");
		$(codeSelect).append(option);
		var cat = this.getLayerDefinition().getStructure();
		var borderGeom = this.optItem.BorderLayer.geometry;
		var nowBorder = this.getStructure().border;
		var nowBorderCode = undefined;
		if (nowBorder !== undefined && nowBorder !== null) {
			nowBorderCode = nowBorder.code;
		}
		if (Array.isArray(cat)) {
			for (var i = 0; i < cat.length; i++) {
				if (cat[i].name === this.nowCategory) {
					var layers = cat[i].layers;
					for (var j = 0; j < layers.length; j++) {
						if (Array.isArray(borderGeom)) {
							if (borderGeom.indexOf(layers[j].geometry) !== -1) {
								var option = $("<option>").text(layers[j].code).attr("geom", layers[j].geometry);
								if (layers[j].code === nowBorderCode) {
									$(option).attr("selected", "selected");
								}
								$(codeSelect).append(option);
							}
						}
					}
				}
			}
		}
		var borderCol1 = $("<div>").addClass("col-md-2").addClass("text-center").text("도곽선");
		var borderCol2 = $("<div>").addClass("col-md-10").append(codeSelect);
		var row = $("<div>").addClass("row").append(borderCol1).append(borderCol2);
		$(this.optionArea).append(row);
	} else {
		this.setMessage("세부 설정 항목을 선택하세요.");
		var alias = this.nowOption.alias;
		var optItem = this.optItem[alias];
		var type3 = optItem["purpose"];
		var row = $("<div>").addClass("row");
		var className = "gb-optiondefinition-btn-detailcategory";
		if (sec) {
			if (all) {
				className = "gb-optiondefinition-btn-relationdetailcategory";
				if (optItem.relation.filter.code || optItem.relation.filter.key || optItem.relation.filter.values) {
					var filterBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(className).css("width", "100%").text(
							"속성 필터").attr("value", "filter");
					var col = $("<div>").addClass("col-md-3").append(filterBtn);
					$(row).append(col);
				}
				if (optItem.relation.figure.code || optItem.relation.figure.key || optItem.relation.figure.values
						|| optItem.relation.figure.number || optItem.relation.figure.condition || optItem.relation.figure.interval) {
					var figBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(className).css("width", "100%").text(
							"속성 검수").attr("value", "figure");
					var col = $("<div>").addClass("col-md-3").append(figBtn);
					$(row).append(col);
				}
				if (optItem.relation.tolerance.code || optItem.relation.tolerance.value || optItem.relation.tolerance.condition
						|| optItem.relation.tolerance.interval) {
					var tolBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(className).css("width", "100%").text(
							"수치 조건").attr("value", "tolerance");
					var col = $("<div>").addClass("col-md-3").append(tolBtn);
					$(row).append(col);
				}
				if (optItem.relation.name
						&& (!optItem.relation.filter.code && !optItem.relation.filter.key && !optItem.relation.filter.values
								&& !optItem.relation.figure.code && !optItem.relation.figure.key && !optItem.relation.figure.values
								&& !optItem.relation.figure.number && !optItem.relation.figure.condition
								&& !optItem.relation.figure.interval && !optItem.relation.tolerance.code
								&& !optItem.relation.tolerance.value && !optItem.relation.tolerance.condition && !optItem.relation.tolerance.interval)) {
					var strc = this.getStructure();
					var layerDef = this.getLayerDefinition().getStructure();
					var names = [];
					for (var a = 0; a < layerDef.length; a++) {
						names.push(layerDef[a].name);
					}
					var flag = false;
					var count = 0;
					var layerMatch = true;
					var relNames = [];
					if (strc["definition"].length > 0) {
						for (var i = 0; i < strc["definition"].length; i++) {
							if (strc["definition"][i]["name"] === this.nowCategory) {
								if (strc["definition"][i].hasOwnProperty("options")) {
									if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
										if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
											if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
												if (Array
														.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
													var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
													for (var j = 0; j < rel.length; j++) {
														relNames.push(rel[j]["name"]);
													}
													for (var b = 0; b < names.length; b++) {
														if (relNames.indexOf(names[b]) === -1) {
															layerMatch = false;
														}
													}
													if (relNames.length === names.length && layerMatch) {
														flag = true;
													}
												}
											}
										}
									}
								}
							}
						}
					}

					var check = $("<input>").attr({
						"type" : "checkbox"
					}).addClass("gb-optiondefinition-check-noparamoption-all").prop("checked", flag);
					var label = $("<label>").append(check).append("검수 수행(세부 설정 불필요)");
					var col = $("<div>").addClass("col-md-12").append(label);
					$(row).append(col);
				}
			} else {
				className = "gb-optiondefinition-btn-relationdetailcategory";
				if (optItem.relation.filter.code || optItem.relation.filter.key || optItem.relation.filter.values) {
					var filterBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(className).css("width", "100%").text(
							"속성 필터").attr("value", "filter");
					var col = $("<div>").addClass("col-md-3").append(filterBtn);
					$(row).append(col);
				}
				if (optItem.relation.figure.code || optItem.relation.figure.key || optItem.relation.figure.values
						|| optItem.relation.figure.number || optItem.relation.figure.condition || optItem.relation.figure.interval) {
					var figBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(className).css("width", "100%").text(
							"속성 검수").attr("value", "figure");
					var col = $("<div>").addClass("col-md-3").append(figBtn);
					$(row).append(col);
				}
				if (optItem.relation.tolerance.code || optItem.relation.tolerance.value || optItem.relation.tolerance.condition
						|| optItem.relation.tolerance.interval) {
					var tolBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(className).css("width", "100%").text(
							"수치 조건").attr("value", "tolerance");
					var col = $("<div>").addClass("col-md-3").append(tolBtn);
					$(row).append(col);
				}
				if (optItem.relation.name
						&& (!optItem.relation.filter.code && !optItem.relation.filter.key && !optItem.relation.filter.values
								&& !optItem.relation.figure.code && !optItem.relation.figure.key && !optItem.relation.figure.values
								&& !optItem.relation.figure.number && !optItem.relation.figure.condition
								&& !optItem.relation.figure.interval && !optItem.relation.tolerance.code
								&& !optItem.relation.tolerance.value && !optItem.relation.tolerance.condition && !optItem.relation.tolerance.interval)) {
					var strc = this.getStructure();
					var flag = false;
					if (strc["definition"].length > 0) {
						for (var i = 0; i < strc["definition"].length; i++) {
							if (strc["definition"][i]["name"] === this.nowCategory) {
								if (strc["definition"][i].hasOwnProperty("options")) {
									if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
										if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
											if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
												if (Array
														.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"])) {
													var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
													for (var j = 0; j < rel.length; j++) {
														if (rel[j]["name"] === this.nowRelationCategory) {
															flag = true;
															break;
														}
													}
													if (flag) {
														break;
													}
												}
											}
										}
									}
								}
							}
						}
					}
					var check = $("<input>").attr({
						"type" : "checkbox"
					}).addClass("gb-optiondefinition-check-noparamoption").prop("checked", flag);
					var label = $("<label>").append(check).append("검수 수행(세부 설정 불필요)");
					var col = $("<div>").addClass("col-md-12").append(label);
					$(row).append(col);
				}
			}
		} else {
			if (optItem.noparam) {

				var strc = this.getStructure();
				var def = strc["definition"];
				var flag = false;
				if (Array.isArray(def)) {
					if (def.length > 0) {
						for (var i = 0; i < def.length; i++) {
							if (this.nowCategory === def[i].name) {
								var options = def[i].options;
								if (options.hasOwnProperty(type3)) {
									var type3Option = options[type3];
									if (type3Option.hasOwnProperty(this.nowOption.alias)) {
										var option = type3Option[this.nowOption.alias];
										if (option !== undefined) {
											flag = true;
										}
									}
								}
							}
						}
					}
				}

				var check = $("<input>").attr({
					"type" : "checkbox"
				}).addClass("gb-optiondefinition-check-noparamoption").prop("checked", flag);
				var label = $("<label>").append(check).append("검수 수행(세부 설정 불필요)");
				var col = $("<div>").addClass("col-md-12").append(label);
				$(row).append(col);
			}

			if (optItem.filter.code || optItem.filter.key || optItem.filter.values) {
				var filterBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(className).css("width", "100%")
						.text("속성 필터").attr("value", "filter");
				var col = $("<div>").addClass("col-md-3").append(filterBtn);
				$(row).append(col);
			}
			if (optItem.figure.code || optItem.figure.key || optItem.figure.values || optItem.figure.number || optItem.figure.condition
					|| optItem.figure.interval) {
				var figBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(className).css("width", "100%").text("속성 검수")
						.attr("value", "figure");
				var col = $("<div>").addClass("col-md-3").append(figBtn);
				$(row).append(col);
			}
			if (optItem.tolerance.code || optItem.tolerance.value || optItem.tolerance.condition || optItem.tolerance.interval) {
				var tolBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(className).css("width", "100%").text("수치 조건")
						.attr("value", "tolerance");
				var col = $("<div>").addClass("col-md-3").append(tolBtn);
				$(row).append(col);
			}
			if (!optItem.noparam
					&& (optItem.relation.name || ((optItem.relation.filter.code || optItem.relation.filter.key
							|| optItem.relation.filter.values || optItem.relation.figure.code || optItem.relation.figure.key
							|| optItem.relation.figure.values || optItem.relation.figure.number || optItem.relation.figure.condition
							|| optItem.relation.figure.interval || optItem.relation.tolerance.code || optItem.relation.tolerance.value
							|| optItem.relation.tolerance.condition || optItem.relation.tolerance.interval) && !sec))) {
				var relBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-detailcategory").css(
						"width", "100%").text("레이어 관계").attr("value", "relation");
				var col = $("<div>").addClass("col-md-3").append(relBtn);
				$(row).append(col);
			}
		}
		$(this.optionArea).append(row);
	}
};

gb.embed.OptionDefinition.prototype.printDetailForm = function(optcat, navi, sec) {
	if (!sec) {
		if (!navi) {
			this.nowDetailCategory = {
				"title" : $(optcat).text(),
				"alias" : $(optcat).attr("value")
			};
		}
		this.nowRelationCategory = undefined;
		this.nowRelationDetailCategory = undefined;
		this.updateNavigation(3);
	} else {
		if (!navi) {
			this.nowRelationDetailCategory = {
				"title" : $(optcat).text(),
				"alias" : $(optcat).attr("value")
			};
			this.updateNavigation(5);
		}
	}

	this.setMessage("레이어 코드를 추가한 후, 세부 설정을 입력하세요.");
	$(this.optionArea).empty();

	var type = $(optcat).attr("value");
	if (type === "filter") {
		var addCodeBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-filteraddcode").text(
				"레이어 코드 추가").css("width", "100%");
		var addCodeBtnCol1 = $("<div>").addClass("col-md-12").append(addCodeBtn);
		var addCodeBtnRow = $("<div>").addClass("row").append(addCodeBtnCol1);
		var tupleArea = $("<div>").addClass("gb-optiondefinition-tuplearea");
		$(this.optionArea).append(addCodeBtnRow);
		$(this.optionArea).append(tupleArea);
		console.log(this.getStructure());
		// 현재 분류 등고선
		// this.nowCategory;
		// 현재 검수 항목 단독 존재 오류
		// this.nowOption;
		// 현재 세부 옵션 종류 필터 피규어 톨러런스 릴레이션
		// this.nowDetailCategory;
		// 세부 옵션이 릴레이션일때 릴레이션 분류
		// this.nowRelationCategory;
		// 세부 옵션이 릴레이션을때 릴레이션 세부 옵션 종류
		// this.nowRelationDetailCategory;
		var strc = this.getStructure();
		for (var i = 0; i < strc["definition"].length; i++) {
			// 옵션중에 분류가 현재 선택한 분류랑 같은지?
			if (strc["definition"][i].name === this.nowCategory) {
				var optItem = this.optItem[this.nowOption.alias];
				var type3 = optItem["purpose"];
				// 그중에 현재 옵션의 목적이 3 타입중 어떤건지?
				if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
					// 릴레이션인지 확인
					var nowFilter = [];
					if (sec) {
						var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
						if (Array.isArray(rel)) {
							for (var j = 0; j < rel.length; j++) {
								if (rel[j]["name"] === this.nowRelationCategory) {
									if (rel[j].hasOwnProperty(type)) {
										nowFilter = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j][type];
									}
								}
							}
						}
					} else {
						if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias][type])) {
							nowFilter = strc["definition"][i]["options"][type3][this.nowOption.alias][type];
						}
					}
					for (var a = 0; a < nowFilter.length; a++) {
						// ============레이어 코드=============
						var codeCol1 = $("<div>").addClass("col-md-1").text("코드:");
						var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-filtercode");
						var cat = this.getLayerDefinition().getStructure();

						if (Array.isArray(cat)) {
							for (var j = 0; j < cat.length; j++) {
								var category;
								// 릴레이션인지 확인
								if (sec) {
									category = this.nowRelationCategory;
								} else {
									category = this.nowCategory;
								}
								// 선언된 분류와 비교중 현재 분류와 같은지?
								if (cat[j].name === category) {
									var layers = cat[j].layers;
									var allCode = $("<option>").text("모두 적용").attr("geom", "none");
									$(codeSelect).append(allCode);
									for (var k = 0; k < layers.length; k++) {
										var option = $("<option>").text(layers[k].code).attr("geom", layers[k].geometry);
										// 분류안의 코드와 현재 선언된 분류가 같은지?
										if (layers[k].code === nowFilter[a].code) {
											$(option).attr("selected", "selected");
										}
										$(codeSelect).append(option);
									}
									if (nowFilter[a].code === null) {
										$(allCode).attr("selected", "selected");
									}
								}
							}
						}
						var codeCol2 = $("<div>").addClass("col-md-7").append(codeSelect);

						var delBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(
								"gb-optiondefinition-btn-deletelayerfilter").text("레이어 코드 삭제").css("width", "100%");
						var delBtnCol = $("<div>").addClass("col-md-2").append(delBtn);

						var addBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-addfilter")
								.css({
									"width" : "100%"
								}).text("필터 추가");
						var addBtnCol = $("<div>").addClass("col-md-2").append(addBtn);

						var addFilterRow = $("<div>").addClass("row").append(codeCol1).append(codeCol2).append(delBtnCol).append(addBtnCol);
						var filterArea = $("<div>").addClass("col-md-12").addClass("gb-optiondefinition-filterarea");
						// =============필터=================
						// nowFilter[a].code
						for (var b = 0; b < nowFilter[a].attribute.length; b++) {
							var optItem = this.optItem[this.nowOption.alias];
							var row = $("<div>").addClass("row");
							if (optItem.filter.key) {
								var attrCol1 = $("<div>").addClass("col-md-1").text("속성명:");
								var inputAttr = $("<input>").attr({
									"type" : "text",
									"placeholder" : "속성명 EX) 재질"
								}).addClass("form-control").addClass("gb-optiondefinition-input-filterkey");
								if (nowFilter[a].attribute[b].key !== undefined && nowFilter[a].attribute[b].key !== null) {
									$(inputAttr).val(nowFilter[a].attribute[b].key);
								}
								var attrCol2 = $("<div>").addClass("col-md-2").append(inputAttr);

								$(row).append(attrCol1).append(attrCol2);
							}
							if (optItem.filter.values) {
								var filterCol1 = $("<div>").addClass("col-md-1").text("허용값:");
								var inputValues = $("<input>").attr({
									"type" : "text",
									"placeholder" : "허용값들을 콤마(,)로 구분하여 입력 EX) 1,2,3"
								}).addClass("form-control").addClass("gb-optiondefinition-input-filtervalues");
								if (nowFilter[a].attribute[b].values !== undefined && nowFilter[a].attribute[b].values !== null) {
									$(inputValues).val(nowFilter[a].attribute[b].values.toString());
								}
								var filterCol2 = $("<div>").addClass("col-md-6").append(inputValues);
								$(row).append(filterCol1).append(filterCol2);
							}
							var btnDel = $("<button>").addClass("btn").addClass("btn-default").addClass(
									"gb-optiondefinition-btn-deletefilterrow").text("필터 삭제").css("width", "100%");
							var delCol1 = $("<div>").addClass("col-md-2").append(btnDel);
							$(row).append(delCol1);

							$(filterArea).append(row);
						}
						// =============필터=================
						var filterAreaRow = $("<div>").addClass("row").append(filterArea);
						var totalArea = $("<div>").addClass("well").append(addFilterRow).append(filterAreaRow);
						$(tupleArea).append(totalArea);
						// ============레이어 코드=============
					}

				}
			}
		}
		/*
		 * var codeCol1 = $("<div>").addClass("col-md-1").text("코드:"); var
		 * codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-filtercode");
		 * var cat = this.getLayerDefinition().getStructure(); var strc =
		 * this.getStructure(); if (Array.isArray(cat)) { for (var i = 0; i <
		 * cat.length; i++) { if (cat[i].name === this.nowCategory) { var layers =
		 * cat[i].layers; for (var j = 0; j < layers.length; j++) { var option =
		 * $("<option>").text(layers[j].code).attr("geom", layers[j].geometry);
		 * 
		 * $(codeSelect).append(option); } } } } var codeCol2 = $("<div>").addClass("col-md-7").append(codeSelect);
		 * 
		 * var delBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-deletelayerfilter").text(
		 * "레이어 코드 삭제"); var delBtnCol = $("<div>").addClass("col-md-2").append(delBtn);
		 * 
		 * var addBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-addfilter").css({
		 * "width" : "100%" }).text("필터 추가"); var addBtnCol = $("<div>").addClass("col-md-2").append(addBtn);
		 * 
		 * var addFilterRow = $("<div>").addClass("row").append(codeCol1).append(codeCol2).append(delBtnCol).append(addBtnCol);
		 * var filterArea = $("<div>").addClass("col-md-12").addClass("gb-optiondefinition-filterarea");
		 * var filterAreaRow = $("<div>").addClass("row").append(filterArea);
		 * var totalArea = $("<div>").addClass("well").append(addFilterRow).append(filterAreaRow);
		 * $(btn).parents().eq(2).find(".gb-optiondefinition-tuplearea").append(totalArea);
		 */

	} else if (type === "figure") {
		var addCodeBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-figureaddcode").text(
				"레이어 코드 추가").css("width", "100%");
		var addCodeBtnCol1 = $("<div>").addClass("col-md-12").append(addCodeBtn);
		var addCodeBtnRow = $("<div>").addClass("row").append(addCodeBtnCol1);
		var tupleArea = $("<div>").addClass("gb-optiondefinition-tuplearea");
		$(this.optionArea).append(addCodeBtnRow);
		$(this.optionArea).append(tupleArea);

		var strc = this.getStructure();
		for (var i = 0; i < strc["definition"].length; i++) {
			// 옵션중에 분류가 현재 선택한 분류랑 같은지?
			if (strc["definition"][i].name === this.nowCategory) {
				var optItem = this.optItem[this.nowOption.alias];
				var type3 = optItem["purpose"];
				// 그중에 현재 옵션의 목적이 3 타입중 어떤건지?
				if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
					// 릴레이션인지 확인
					var nowFilter = [];
					if (sec) {
						var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
						if (Array.isArray(rel)) {
							for (var j = 0; j < rel.length; j++) {
								if (rel[j]["name"] === this.nowRelationCategory) {
									if (rel[j].hasOwnProperty(type)) {
										nowFilter = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j][type];
									}
								}
							}
						}
					} else {
						if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
							if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty(type)) {
								if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias][type])) {
									nowFilter = strc["definition"][i]["options"][type3][this.nowOption.alias][type];
								}
							}
						}
					}
					for (var a = 0; a < nowFilter.length; a++) {
						// ============레이어 코드=============
						var codeCol1 = $("<div>").addClass("col-md-1").text("코드:");
						var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-figurecode");
						var cat = this.getLayerDefinition().getStructure();

						if (Array.isArray(cat)) {
							for (var j = 0; j < cat.length; j++) {
								var category;
								// 릴레이션인지 확인
								if (sec) {
									category = this.nowRelationCategory;
								} else {
									category = this.nowCategory;
								}
								// 선언된 분류와 비교중 현재 분류와 같은지?
								if (cat[j].name === category) {
									var layers = cat[j].layers;
									var allCode = $("<option>").text("모두 적용").attr("geom", "none");
									$(codeSelect).append(allCode);
									for (var k = 0; k < layers.length; k++) {
										var option = $("<option>").text(layers[k].code).attr("geom", layers[k].geometry);
										// 분류안의 코드와 현재 선언된 분류가 같은지?
										if (layers[k].code === nowFilter[a].code) {
											$(option).attr("selected", "selected");
										}
										$(codeSelect).append(option);
									}
									if (nowFilter[a].code === null) {
										$(allCode).attr("selected", "selected");
									}
								}
							}
						}

						var codeCol2 = $("<div>").addClass("col-md-7").append(codeSelect);

						var delBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(
								"gb-optiondefinition-btn-deletelayerfigure").text("레이어 코드 삭제").css("width", "100%");
						var delBtnCol = $("<div>").addClass("col-md-2").append(delBtn)

						var addBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-addfigure")
								.text("속성 추가").css("width", "100%");
						var addBtnCol = $("<div>").addClass("col-md-2").append(addBtn);

						var addFigureRow = $("<div>").addClass("row").append(codeCol1).append(codeCol2).append(delBtnCol).append(addBtnCol);
						var figureArea = $("<div>").addClass("col-md-12").addClass("gb-optiondefinition-figurearea");
						// =============피규어=============
						for (var b = 0; b < nowFilter[a].attribute.length; b++) {
							var optItem = this.optItem[this.nowOption.alias];
							var row = $("<div>").addClass("row");

							var attrCol1 = $("<div>").addClass("col-md-1").text("속성명:");
							var inputAttr = $("<input>").attr({
								"type" : "text",
								"placeholder" : "속성명 EX) 재질"
							}).addClass("form-control").addClass("gb-optiondefinition-input-figurekey");
							if (nowFilter[a].attribute[b].key !== undefined && nowFilter[a].attribute[b].key !== null) {
								$(inputAttr).val(nowFilter[a].attribute[b].key);
							}
							if (!optItem.figure.key) {
								$(inputAttr).prop("disabled", true);
							}
							var attrCol2 = $("<div>").addClass("col-md-2").append(inputAttr);

							$(row).append(attrCol1).append(attrCol2);

							var filterCol1 = $("<div>").addClass("col-md-1").text("허용값:");
							var inputValues = $("<input>").attr({
								"type" : "text",
								"placeholder" : "허용값들을 콤마(,)로 구분하여 입력 EX) 1,2,3"
							}).addClass("form-control").addClass("gb-optiondefinition-input-figurevalues");
							if (nowFilter[a].attribute[b].values !== undefined && nowFilter[a].attribute[b].values !== null) {
								$(inputValues).val(nowFilter[a].attribute[b].values.toString());
							}
							if (!optItem.figure.values) {
								$(inputValues).prop("disabled", true);
							}
							var filterCol2 = $("<div>").addClass("col-md-8").append(inputValues);
							$(row).append(filterCol1).append(filterCol2);

							var row2 = $("<div>").addClass("row");

							var numCol1 = $("<div>").addClass("col-md-1").text("수치:");
							var inputNum = $("<input>").attr({
								"type" : "number",
								"placeholder" : "숫자형 기준값"
							}).addClass("form-control").addClass("gb-optiondefinition-input-figurenumber");
							if (nowFilter[a].attribute[b].number !== undefined && nowFilter[a].attribute[b].number !== null) {
								$(inputNum).val(nowFilter[a].attribute[b].number);
							}
							if (!optItem.figure.number) {
								$(inputNum).prop("disabled", true);
							}
							var numCol2 = $("<div>").addClass("col-md-3").append(inputNum);
							$(row2).append(numCol1).append(numCol2);

							var codeCol1 = $("<div>").addClass("col-md-1").text("조건:");
							var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-figurecondition");
							var optionEqual = $("<option>").text("같음").attr("value", "equal");
							var optionOver = $("<option>").text("초과").attr("value", "over");
							var optionUnder = $("<option>").text("미만").attr("value", "under");
							$(codeSelect).append(optionEqual).append(optionOver).append(optionUnder);
							if (nowFilter[a].attribute[b].condition !== undefined && nowFilter[a].attribute[b].condition !== null) {
								$(codeSelect).val(nowFilter[a].attribute[b].condition);
							}
							if (!optItem.figure.condition) {
								$(codeSelect).prop("disabled", true);
							}
							var codeCol2 = $("<div>").addClass("col-md-2").append(codeSelect);
							$(row2).append(codeCol1).append(codeCol2);

							var numCol1 = $("<div>").addClass("col-md-1").text("간격:");
							var inputNum = $("<input>").attr({
								"type" : "number",
								"placeholder" : "숫자형 기준값"
							}).addClass("form-control").addClass("gb-optiondefinition-input-figureinterval");
							if (nowFilter[a].attribute[b].interval !== undefined && nowFilter[a].attribute[b].interval !== null) {
								$(inputNum).val(nowFilter[a].attribute[b].interval);
							}
							if (!optItem.figure.interval) {
								$(inputNum).prop("disabled", true);
							}
							var numCol2 = $("<div>").addClass("col-md-2").append(inputNum);
							$(row2).append(numCol1).append(numCol2);

							var btnDel = $("<button>").addClass("btn").addClass("btn-default").addClass(
									"gb-optiondefinition-btn-deletefigurerow").text("속성 삭제").css("width", "100%");
							var delCol1 = $("<div>").addClass("col-md-2").append(btnDel);
							$(row2).append(delCol1);

							var outerRow = $("<div>").append(row).append(row2);
							$(figureArea).append(outerRow);
						}
						// =============피규어=============
						var figureAreaRow = $("<div>").addClass("row").append(figureArea);
						var totalArea = $("<div>").addClass("well").append(addFigureRow).append(figureAreaRow);
						$(tupleArea).append(totalArea);
						// ============레이어 코드=============
					}
				}
			}
		}
	} else if (type === "tolerance") {
		var addCodeBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-toleranceaddcode").text(
				"레이어 코드 추가").css("width", "100%");
		var addCodeBtnCol1 = $("<div>").addClass("col-md-12").append(addCodeBtn);
		var addCodeBtnRow = $("<div>").addClass("row").append(addCodeBtnCol1);
		var tupleArea = $("<div>").addClass("gb-optiondefinition-tuplearea");
		$(this.optionArea).append(addCodeBtnRow);
		$(this.optionArea).append(tupleArea);

		var strc = this.getStructure();
		for (var i = 0; i < strc["definition"].length; i++) {
			// 옵션중에 분류가 현재 선택한 분류랑 같은지?
			if (strc["definition"][i].name === this.nowCategory) {
				var optItem = this.optItem[this.nowOption.alias];
				var type3 = optItem["purpose"];
				// 그중에 현재 옵션의 목적이 3 타입중 어떤건지?
				if (strc["definition"][i]["options"].hasOwnProperty(type3)) {
					// 릴레이션인지 확인
					var nowFilter = [];
					if (strc["definition"][i]["options"][type3].hasOwnProperty(this.nowOption.alias)) {
						if (sec) {
							if (strc["definition"][i]["options"][type3][this.nowOption.alias].hasOwnProperty("relation")) {
								var rel = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"];
								if (Array.isArray(rel)) {
									for (var j = 0; j < rel.length; j++) {
										if (rel[j]["name"] === this.nowRelationCategory) {
											if (rel[j].hasOwnProperty(type)) {
												nowFilter = strc["definition"][i]["options"][type3][this.nowOption.alias]["relation"][j][type];
											}
										}
									}
								}
							}
						} else {
							if (Array.isArray(strc["definition"][i]["options"][type3][this.nowOption.alias][type])) {
								nowFilter = strc["definition"][i]["options"][type3][this.nowOption.alias][type];
							}
						}
					}
					for (var a = 0; a < nowFilter.length; a++) {
						// ============레이어 코드=============
						var codeCol1 = $("<div>").addClass("col-md-1").text("코드:");
						var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-tolerancecode");
						var cat = this.getLayerDefinition().getStructure();

						if (Array.isArray(cat)) {
							for (var j = 0; j < cat.length; j++) {
								var category;
								// 릴레이션인지 확인
								if (sec) {
									category = this.nowRelationCategory;
								} else {
									category = this.nowCategory;
								}
								// 선언된 분류와 비교중 현재 분류와 같은지?
								if (cat[j].name === category) {
									var layers = cat[j].layers;
									var allCode = $("<option>").text("모두 적용").attr("geom", "none");
									$(codeSelect).append(allCode);
									for (var k = 0; k < layers.length; k++) {
										var option = $("<option>").text(layers[k].code).attr("geom", layers[k].geometry);
										// 분류안의 코드와 현재 선언된 분류가 같은지?
										if (layers[k].code === nowFilter[a].code) {
											$(option).attr("selected", "selected");
										}
										$(codeSelect).append(option);
									}
									if (nowFilter[a].code === null) {
										$(allCode).attr("selected", "selected");
									}
								}
							}
						}
						var codeCol2 = $("<div>").addClass("col-md-9").append(codeSelect);

						var delBtn = $("<button>").addClass("btn").addClass("btn-default").addClass(
								"gb-optiondefinition-btn-deletelayertolerance").text("레이어 코드 삭제").css("width", "100%");
						var delBtnCol = $("<div>").addClass("col-md-2").append(delBtn);

						/*
						 * var addBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-btn-addtolerance").text("조건
						 * 추가").css( "width", "100%"); var addBtnCol = $("<div>").addClass("col-md-2").append(addBtn);
						 */

						var addFigureRow = $("<div>").addClass("row").append(codeCol1).append(codeCol2).append(delBtnCol);
						var figureArea = $("<div>").addClass("col-md-12").addClass("gb-optiondefinition-tolerancearea");
						// ============================
						var optItem = this.optItem[this.nowOption.alias];
						var row = $("<div>").addClass("row");

						var numCol1 = $("<div>").addClass("col-md-1").text("수치:");
						var inputNum = $("<input>").attr({
							"type" : "number",
							"placeholder" : "숫자형 기준값"
						}).addClass("form-control").addClass("gb-optiondefinition-input-tolerancevalue");
						if (nowFilter[a].value !== undefined && nowFilter[a].value !== null) {
							$(inputNum).val(nowFilter[a].value);
						}
						if (!optItem.tolerance.value) {
							$(inputNum).prop("disabled", true);
						}
						var numCol2 = $("<div>").addClass("col-md-3").append(inputNum);
						$(row).append(numCol1).append(numCol2);

						var codeCol1 = $("<div>").addClass("col-md-1").text("조건:");
						var codeSelect = $("<select>").addClass("form-control").addClass("gb-optiondefinition-select-tolerancecondition");
						var optionEqual = $("<option>").text("같음").attr("value", "equal");
						var optionOver = $("<option>").text("초과").attr("value", "over");
						var optionUnder = $("<option>").text("미만").attr("value", "under");
						$(codeSelect).append(optionEqual).append(optionOver).append(optionUnder);
						if (nowFilter[a].condition !== undefined && nowFilter[a].condition !== null) {
							$(codeSelect).val(nowFilter[a].condition);
						}
						if (!optItem.tolerance.condition) {
							$(codeSelect).prop("disabled", true);
						}
						var codeCol2 = $("<div>").addClass("col-md-3").append(codeSelect);
						$(row).append(codeCol1).append(codeCol2);

						var numCol1 = $("<div>").addClass("col-md-1").text("간격:");
						var inputNum = $("<input>").attr({
							"type" : "number",
							"placeholder" : "숫자형 기준값"
						}).addClass("form-control").addClass("gb-optiondefinition-input-toleranceinterval");
						if (nowFilter[a].interval !== undefined && nowFilter[a].interval !== null) {
							$(inputNum).val(nowFilter[a].interval);
						}
						if (!optItem.tolerance.interval) {
							$(inputNum).prop("disabled", true);
						}
						var numCol2 = $("<div>").addClass("col-md-3").append(inputNum);
						$(row).append(numCol1).append(numCol2);

						/*
						 * var btnDel = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-optiondefinition-delete-row").text("조건
						 * 삭제").css( "width", "100%"); var delCol1 = $("<div>").addClass("col-md-2").append(btnDel);
						 * $(row).append(delCol1);
						 */

						$(figureArea).append(row);
						// ============================
						var figureAreaRow = $("<div>").addClass("row").append(figureArea);
						var totalArea = $("<div>").addClass("well").append(addFigureRow).append(figureAreaRow);
						$(tupleArea).append(totalArea);
						// ============레이어 코드=============
					}
				}
			}
		}
	} else if (type === "relation") {
		this.printCategory(true);
	}
};

gb.embed.OptionDefinition.prototype.getInputFile = function() {
	return this.file;
};

gb.embed.OptionDefinition.prototype.updateStructure = function() {
	console.log(this.getStructure());
	$(this.optionArea).empty();
	var strc = this.getStructure();
	this.init();
};

gb.embed.OptionDefinition.prototype.setLayerDefinition = function(strc) {
	this.layerDef = strc;
};

gb.embed.OptionDefinition.prototype.getLayerDefinition = function() {
	return this.layerDef;
};

gb.embed.OptionDefinition.prototype.clearStructure = function() {
	this.structure = {
		"border" : null,
		"definition" : []
	};
};

gb.embed.OptionDefinition.prototype.setStructure = function(strc) {
	var isOK = true;
	var borderElem = [ "code", "geometry" ];
	var defElem = [ "name", "options" ];
	var optionElem = [ "attribute", "graphic", "adjacent" ];
	var optionItem = Object.keys(this.optItem);
	var optionNameElem = [ "filter", "figure", "tolerance", "relation" ];
	var filterElem = [ "code", "attribute" ];
	var filterAttributeElem = [ "key", "values" ];
	var figureElem = [ "code", "attribute" ];
	var figureAttributeElem = [ "key", "values", "number", "condition", "interval" ];
	var toleranceElem = [ "code", "value", "condition", "interval" ];
	var relationElem = [ "name", "filter", "figure", "tolerance" ];
	var layerStructure = this.getLayerDefinition().getStructure();
	if (strc.hasOwnProperty("border")) {
		var border = strc["border"];
		if (border !== null) {
			if (!border.hasOwnProperty("code")) {
				isOK = false;
				this.setMessagePopup("danger", " 도곽선 Code가 입력되지 않았습니다.");
			}
			if (!border.hasOwnProperty("geometry")) {
				isOK = false;
				this.setMessagePopup("danger", " 도곽선 Geometry가 입력되지 않았습니다.");
			}
		}
	}
	if (strc.hasOwnProperty("definition")) {
		if (Array.isArray(strc["definition"])) {
			var definitions = strc["definition"];
			for (var i = 0; i < definitions.length; i++) {
				var definition = definitions[i];
				var defKeys = Object.keys(definition);
				for (var j = 0; j < defKeys.length; j++) {
					if (defElem.indexOf(defKeys[j]) === -1) {
						isOK = false;
						this.setMessagePopup("danger", defKeys[j] + " 키 네임은 유효한 키 네임이 아닙니다.");
					}
					if (!definitions[i].hasOwnProperty("name")) {
						isOK = false;
						this.setMessagePopup("danger", (i + 1) + "번째 분류의 분류명을 입력해야 합니다.");
					}
					if (defKeys[j] === "options") {
						var options = definitions[i]["options"];
						if (options !== undefined && options !== null) {
							var optionKeys = Object.keys(options);
							for (var k = 0; k < optionKeys.length; k++) {
								if (optionElem.indexOf(optionKeys[k]) === -1) {
									isOK = false;
									this.setMessagePopup("danger", optionKeys[k] + " 키 네임은 유효한 키 네임이 아닙니다.");
								} else {
									if (options.hasOwnProperty(optionKeys[k])) {
										var type3Obj = options[optionKeys[k]];
										if (type3Obj !== undefined && type3Obj !== null) {
											var optionItemKeys = Object.keys(type3Obj);
											for (var l = 0; l < optionItemKeys.length; l++) {
												if (optionItem.indexOf(optionItemKeys[l]) === -1) {
													isOK = false;
													this.setMessagePopup("danger", optionItemKeys[l] + " 검수 항목은 유효한 이름이 아닙니다.");
													console.error("");
												} else {
													// 검수 항목 설정 객체 filter,
													// figure 등이
													// 들어있음
													var type4Obj = type3Obj[optionItemKeys[l]];
													type4Keys = Object.keys(type4Obj);
													for (var m = 0; m < type4Keys.length; m++) {
														if (optionNameElem.indexOf(type4Keys[m]) === -1) {
															isOK = false;
															this.setMessagePopup("danger", type4Keys[m] + "키 네임은 유효한 키 네임이 아닙니다.");
															console.error("");
														} else {
															if (type4Keys[m] === "filter") {
																var filterArr = type4Obj["filter"];
																if (Array.isArray(filterArr)) {
																	for (var n = 0; n < filterArr.length; n++) {
																		var filterKeys = Object.keys(filterArr[n]);
																		for (var o = 0; o < filterKeys.length; o++) {
																			if (filterElem.indexOf(filterKeys[o]) === -1) {
																				isOK = false;
																				this.setMessagePopup("danger", filterKeys[o]
																						+ "키 네임은 유효한 키 네임이 아닙니다.");
																			} else {
																				if (filterKeys[o] === "code") {
																					if (filterArr[n]["code"] !== null
																							&& filterArr[n]["code"] !== undefined) {
																						var nowName = definitions[i]["name"];
																						var nowCode = filterArr[n]["code"];
																						if (Array.isArray(layerStructure)) {
																							var nameExist = false;
																							var codeExist = false;
																							for (var e = 0; e < layerStructure.length; e++) {
																								if (layerStructure[e]
																										.hasOwnProperty("name")) {
																									if (layerStructure[e]["name"] === nowName) {
																										nameExist = true;
																										if (layerStructure[e]
																												.hasOwnProperty("layers")) {
																											if (Array
																													.isArray(layerStructure[e]["layers"])) {
																												var layers = layerStructure[e]["layers"];
																												for (var f = 0; f < layers.length; f++) {
																													if (layers[f]
																															.hasOwnProperty("code")) {
																														if (layers[f]["code"] === nowCode) {
																															codeExist = true;
																														}
																													}
																												}
																											}
																										} else {
																											isOK = false;
																											this
																													.setMessagePopup(
																															"danger",
																															nowCode
																																	+ "레이어 코드는 레이어 정의에 입력되어 있지 않습니다.");
																										}
																									}
																								}
																							}
																							if (nameExist === false && codeExist === false) {
																								isOK = false;
																								this.setMessagePopup("danger", "분류명 "
																										+ nowName + "의 레이어코드 " + nowCode
																										+ "는 레이어 정의에 입력되지 않았습니다.");
																							}
																						}
																					}
																				} else if (filterKeys[o] === "attribute") {
																					if (Array.isArray(filterArr[n]["attribute"])) {
																						var attributes = filterArr[n]["attribute"];
																						for (var p = 0; p < attributes.length; p++) {
																							var attrKeys = Object.keys(attributes[p]);
																							for (var q = 0; q < attrKeys.length; q++) {
																								if (filterAttributeElem
																										.indexOf(attrKeys[q]) === -1) {
																									isOK = false;
																									this
																											.setMessagePopup(
																													"danger",
																													attrKeys[q]
																															+ "키 네임은 유효한 키 네임이 아닙니다.");
																									console.error(attrKeys[q]
																											+ "키 네임은 유효한 키 네임이 아닙니다.");
																								} else {
																									if (attrKeys[q] === "values") {
																										if (!Array
																												.isArray(attributes[p]["values"])
																												&& attributes[p]["values"] !== null) {
																											isOK = false;
																											this
																													.setMessagePopup(
																															"danger",
																															"values는 null 또는 배열 형태여야 합니다.");
																											console.error("");
																										}
																									}
																								}
																							}
																						}
																					} else {
																						isOK = false;
																						this.setMessagePopup("danger",
																								"attribute는 null 또는 배열 형태여야 합니다.");
																						console.error("");
																					}
																				}
																			}
																		}
																	}
																} else if (filterArr !== null && !Array.isArray(filterArr)) {
																	isOK = false;
																	this.setMessagePopup("danger", "filter는 null 또는 배열의 형태여야 합니다.");
																	console.error("");
																}
															} else if (type4Keys[m] === "figure") {
																var figureArr = type4Obj["figure"];
																if (Array.isArray(figureArr)) {
																	for (var n = 0; n < figureArr.length; n++) {
																		var figureKeys = Object.keys(figureArr[n]);
																		for (var o = 0; o < figureKeys.length; o++) {
																			if (figureElem.indexOf(figureKeys[o]) === -1) {
																				isOK = false;
																				this.setMessagePopup("danger", figureKeys[o]
																						+ "키 네임은 유효한 키 네임이 아닙니다.");
																				console.error("");
																			} else {
																				if (figureKeys[o] === "code") {
																					if (figureArr[n]["code"] !== null
																							&& figureArr[n]["code"] !== undefined) {
																						var nowName = definitions[i]["name"];
																						var nowCode = figureArr[n]["code"];
																						if (Array.isArray(layerStructure)) {
																							var nameExist = false;
																							var codeExist = false;
																							for (var e = 0; e < layerStructure.length; e++) {
																								if (layerStructure[e]
																										.hasOwnProperty("name")) {
																									if (layerStructure[e]["name"] === nowName) {
																										nameExist = true;
																										if (layerStructure[e]
																												.hasOwnProperty("layers")) {
																											if (Array
																													.isArray(layerStructure[e]["layers"])) {
																												var layers = layerStructure[e]["layers"];
																												for (var f = 0; f < layers.length; f++) {
																													if (layers[f]
																															.hasOwnProperty("code")) {
																														if (layers[f]["code"] === nowCode) {
																															codeExist = true;
																														}
																													}
																												}
																											}
																										} else {
																											isOK = false;
																											this
																													.setMessagePopup(
																															"danger",
																															nowCode
																																	+ "레이어 코드는 레이어 정의에 입력되어 있지 않습니다.");
																										}
																									}
																								}
																							}
																							if (nameExist === false && codeExist === false) {
																								isOK = false;
																								this.setMessagePopup("danger", "분류명 "
																										+ nowName + "의 레이어코드 " + nowCode
																										+ "는 레이어 정의에 입력되지 않았습니다.");
																							}
																						}
																					}
																				} else if (figureKeys[o] === "attribute") {
																					if (Array.isArray(figureArr[n]["attribute"])) {
																						var attributes = figureArr[n]["attribute"];
																						for (var p = 0; p < attributes.length; p++) {
																							var attrKeys = Object.keys(attributes[p]);
																							for (var q = 0; q < attrKeys.length; q++) {
																								if (figureAttributeElem
																										.indexOf(attrKeys[q]) === -1) {
																									isOK = false;
																									this
																											.setMessagePopup(
																													"danger",
																													attrKeys[q]
																															+ "키 네임은 유효한 키 네임이 아닙니다.");
																									console.error("");
																								} else {
																									if (attrKeys[q] === "values") {
																										if (!Array
																												.isArray(attributes[p]["values"])
																												&& attributes[p]["values"] !== null) {
																											isOK = false;
																											this
																													.setMessagePopup(
																															"danger",
																															"values는 null 또는 배열 형태여야 합니다.");
																											console.error("");
																										}
																									}
																								}
																							}
																						}
																					} else {
																						isOK = false;
																						this.setMessagePopup("danger",
																								"attribute는 null 또는 배열 형태여야 합니다.");
																						console.error("");
																					}
																				}
																			}
																		}
																	}
																} else if (figureArr !== null && !Array.isArray(figureArr)) {
																	isOK = false;
																	this.setMessagePopup("danger", "figure는 null 또는 배열의 형태여야 합니다.");
																	console.error("");
																}
															} else if (type4Keys[m] === "tolerance") {
																var toleranceArr = type4Obj["tolerance"];
																if (Array.isArray(toleranceArr)) {
																	for (var n = 0; n < toleranceArr.length; n++) {
																		var toleranceKeys = Object.keys(toleranceArr[n]);
																		for (var o = 0; o < toleranceKeys.length; o++) {
																			if (toleranceElem.indexOf(toleranceKeys[o]) === -1) {
																				isOK = false;
																				this.setMessagePopup("danger", toleranceKeys[o]
																						+ "키 네임은 유효한 키 네임이 아닙니다.");
																			}
																			if (toleranceKeys[o] === "code") {
																				if (toleranceArr[n]["code"] !== null
																						&& toleranceArr[n]["code"] !== undefined) {
																					var nowName = definitions[i]["name"];
																					var nowCode = toleranceArr[n]["code"];
																					if (Array.isArray(layerStructure)) {
																						var nameExist = false;
																						var codeExist = false;
																						for (var e = 0; e < layerStructure.length; e++) {
																							if (layerStructure[e].hasOwnProperty("name")) {
																								if (layerStructure[e]["name"] === nowName) {
																									nameExist = true;
																									if (layerStructure[e]
																											.hasOwnProperty("layers")) {
																										if (Array
																												.isArray(layerStructure[e]["layers"])) {
																											var layers = layerStructure[e]["layers"];
																											for (var f = 0; f < layers.length; f++) {
																												if (layers[f]
																														.hasOwnProperty("code")) {
																													if (layers[f]["code"] === nowCode) {
																														codeExist = true;
																													}
																												}
																											}
																										}
																									} else {
																										isOK = false;
																										this
																												.setMessagePopup(
																														"danger",
																														nowCode
																																+ "레이어 코드는 레이어 정의에 입력되어 있지 않습니다.");
																									}
																								}
																							}
																						}
																						if (nameExist === false && codeExist === false) {
																							isOK = false;
																							this.setMessagePopup("danger", "분류명 " + nowName
																									+ "의 레이어코드 " + nowCode
																									+ "는 레이어 정의에 입력되지 않았습니다.");
																						}
																					}
																				}
																			}
																		}
																	}
																} else if (toleranceArr !== null && !Array.isArray(toleranceArr)) {
																	isOK = false;
																	this.setMessagePopup("danger", "tolerance는 null 또는 배열의 형태여야 합니다.");
																	console.error("");
																}
															} else if (type4Keys[m] === "relation") {
																var relationArr = type4Obj["relation"];
																if (Array.isArray(relationArr)) {
																	for (var a = 0; a < relationArr.length; a++) {
																		var rel = relationArr[a];
																		var relKeys = Object.keys(rel);
																		for (var b = 0; b < relKeys.length; b++) {
																			if (relationElem.indexOf(relKeys[b]) === -1) {
																				isOK = false;
																				this.setMessagePopup("danger", relKeys[b]
																						+ "키 네임은 유효한 키 네임이 아닙니다.");
																			} else {
																				if (relKeys[b] === "filter") {
																					var filterArr = rel["filter"];
																					if (Array.isArray(filterArr)) {
																						for (var n = 0; n < filterArr.length; n++) {
																							var filterKeys = Object.keys(filterArr[n]);
																							for (var o = 0; o < filterKeys.length; o++) {
																								if (filterElem.indexOf(filterKeys[o]) === -1) {
																									isOK = false;
																									this
																											.setMessagePopup(
																													"danger",
																													filterKeys[o]
																															+ "키 네임은 유효한 키 네임이 아닙니다.");
																								} else {
																									if (filterKeys[o] === "code") {
																										if (filterArr[n]["code"] !== null
																												&& filterArr[n]["code"] !== undefined) {
																											var nowName = definitions[i]["name"];
																											var nowCode = filterArr[n]["code"];
																											if (Array
																													.isArray(layerStructure)) {
																												var nameExist = false;
																												var codeExist = false;
																												for (var e = 0; e < layerStructure.length; e++) {
																													if (layerStructure[e]
																															.hasOwnProperty("name")) {
																														if (layerStructure[e]["name"] === nowName) {
																															nameExist = true;
																															if (layerStructure[e]
																																	.hasOwnProperty("layers")) {
																																if (Array
																																		.isArray(layerStructure[e]["layers"])) {
																																	var layers = layerStructure[e]["layers"];
																																	for (var f = 0; f < layers.length; f++) {
																																		if (layers[f]
																																				.hasOwnProperty("code")) {
																																			if (layers[f]["code"] === nowCode) {
																																				codeExist = true;
																																			}
																																		}
																																	}
																																}
																															} else {
																																isOK = false;
																																this
																																		.setMessagePopup(
																																				"danger",
																																				nowCode
																																						+ "레이어 코드는 레이어 정의에 입력되어 있지 않습니다.");
																															}
																														}
																													}
																												}
																												if (nameExist === false
																														&& codeExist === false) {
																													isOK = false;
																													this
																															.setMessagePopup(
																																	"danger",
																																	"분류명 "
																																			+ nowName
																																			+ "의 레이어코드 "
																																			+ nowCode
																																			+ "는 레이어 정의에 입력되지 않았습니다.");
																												}
																											}
																										}
																									} else if (filterKeys[o] === "attribute") {
																										if (Array
																												.isArray(filterArr[n]["attribute"])) {
																											var attributes = filterArr[n]["attribute"];
																											for (var p = 0; p < attributes.length; p++) {
																												var attrKeys = Object
																														.keys(attributes[p]);
																												for (var q = 0; q < attrKeys.length; q++) {
																													if (filterAttributeElem
																															.indexOf(attrKeys[q]) === -1) {
																														isOK = false;
																														this
																																.setMessagePopup(
																																		"danger",
																																		attrKeys[q]
																																				+ "키 네임은 유효한 키 네임이 아닙니다.");
																														console
																																.error(attrKeys[q]
																																		+ "키 네임은 유효한 키 네임이 아닙니다.");
																													} else {
																														if (attrKeys[q] === "values") {
																															if (!Array
																																	.isArray(attributes[p]["values"])
																																	&& attributes[p]["values"] !== null) {
																																isOK = false;
																																this
																																		.setMessagePopup(
																																				"danger",
																																				"values는 null 또는 배열 형태여야 합니다.");
																																console
																																		.error("");
																															}
																														}
																													}
																												}
																											}
																										} else {
																											isOK = false;
																											this
																													.setMessagePopup(
																															"danger",
																															"attribute는 null 또는 배열 형태여야 합니다.");
																											console.error("");
																										}
																									}
																								}
																							}
																						}
																					} else if (filterArr !== null
																							&& !Array.isArray(filterArr)) {
																						isOK = false;
																						this.setMessagePopup("danger",
																								"filter는 null 또는 배열의 형태여야 합니다.");
																						console.error("");
																					}
																				} else if (relKeys[b] === "figure") {
																					var figureArr = rel["figure"];
																					if (Array.isArray(figureArr)) {
																						for (var n = 0; n < figureArr.length; n++) {
																							var figureKeys = Object.keys(figureArr[n]);
																							for (var o = 0; o < figureKeys.length; o++) {
																								if (figureElem.indexOf(figureKeys[o]) === -1) {
																									isOK = false;
																									this
																											.setMessagePopup(
																													"danger",
																													figureKeys[o]
																															+ "키 네임은 유효한 키 네임이 아닙니다.");
																									console.error("");
																								} else {
																									if (figureKeys[o] === "code") {
																										if (figureArr[n]["code"] !== null
																												&& figureArr[n]["code"] !== undefined) {
																											var nowName = definitions[i]["name"];
																											var nowCode = figureArr[n]["code"];
																											if (Array
																													.isArray(layerStructure)) {
																												var nameExist = false;
																												var codeExist = false;
																												for (var e = 0; e < layerStructure.length; e++) {
																													if (layerStructure[e]
																															.hasOwnProperty("name")) {
																														if (layerStructure[e]["name"] === nowName) {
																															nameExist = true;
																															if (layerStructure[e]
																																	.hasOwnProperty("layers")) {
																																if (Array
																																		.isArray(layerStructure[e]["layers"])) {
																																	var layers = layerStructure[e]["layers"];
																																	for (var f = 0; f < layers.length; f++) {
																																		if (layers[f]
																																				.hasOwnProperty("code")) {
																																			if (layers[f]["code"] === nowCode) {
																																				codeExist = true;
																																			}
																																		}
																																	}
																																}
																															} else {
																																isOK = false;
																																this
																																		.setMessagePopup(
																																				"danger",
																																				nowCode
																																						+ "레이어 코드는 레이어 정의에 입력되어 있지 않습니다.");
																															}
																														}
																													}
																												}
																												if (nameExist === false
																														&& codeExist === false) {
																													isOK = false;
																													this
																															.setMessagePopup(
																																	"danger",
																																	"분류명 "
																																			+ nowName
																																			+ "의 레이어코드 "
																																			+ nowCode
																																			+ "는 레이어 정의에 입력되지 않았습니다.");
																												}
																											}
																										}
																									} else if (figureKeys[o] === "attribute") {
																										if (Array
																												.isArray(figureArr[n]["attribute"])) {
																											var attributes = figureArr[n]["attribute"];
																											for (var p = 0; p < attributes.length; p++) {
																												var attrKeys = Object
																														.keys(attributes[p]);
																												for (var q = 0; q < attrKeys.length; q++) {
																													if (figureAttributeElem
																															.indexOf(attrKeys[q]) === -1) {
																														isOK = false;
																														this
																																.setMessagePopup(
																																		"danger",
																																		attrKeys[q]
																																				+ "키 네임은 유효한 키 네임이 아닙니다.");
																														console.error("");
																													} else {
																														if (attrKeys[q] === "values") {
																															if (!Array
																																	.isArray(attributes[p]["values"])
																																	&& attributes[p]["values"] !== null) {
																																isOK = false;
																																this
																																		.setMessagePopup(
																																				"danger",
																																				"values는 null 또는 배열 형태여야 합니다.");
																																console
																																		.error("");
																															}
																														}
																													}
																												}
																											}
																										} else {
																											isOK = false;
																											this
																													.setMessagePopup(
																															"danger",
																															"attribute는 null 또는 배열 형태여야 합니다.");
																											console.error("");
																										}
																									}
																								}
																							}
																						}
																					} else if (figureArr !== null
																							&& !Array.isArray(figureArr)) {
																						isOK = false;
																						this.setMessagePopup("danger",
																								"figure는 null 또는 배열의 형태여야 합니다.");
																						console.error("");
																					}
																				} else if (relKeys[b] === "tolerance") {
																					var toleranceArr = rel["tolerance"];
																					if (Array.isArray(toleranceArr)) {
																						for (var n = 0; n < toleranceArr.length; n++) {
																							var toleranceKeys = Object
																									.keys(toleranceArr[n]);
																							for (var o = 0; o < toleranceKeys.length; o++) {
																								if (toleranceElem.indexOf(toleranceKeys[o]) === -1) {
																									isOK = false;
																									this
																											.setMessagePopup(
																													"danger",
																													toleranceKeys[o]
																															+ "키 네임은 유효한 키 네임이 아닙니다.");
																								}
																								if (toleranceKeys[o] === "code") {
																									if (toleranceArr[n]["code"] !== null
																											&& toleranceArr[n]["code"] !== undefined) {
																										var nowName = definitions[i]["name"];
																										var nowCode = toleranceArr[n]["code"];
																										if (Array.isArray(layerStructure)) {
																											var nameExist = false;
																											var codeExist = false;
																											for (var e = 0; e < layerStructure.length; e++) {
																												if (layerStructure[e]
																														.hasOwnProperty("name")) {
																													if (layerStructure[e]["name"] === nowName) {
																														nameExist = true;
																														if (layerStructure[e]
																																.hasOwnProperty("layers")) {
																															if (Array
																																	.isArray(layerStructure[e]["layers"])) {
																																var layers = layerStructure[e]["layers"];
																																for (var f = 0; f < layers.length; f++) {
																																	if (layers[f]
																																			.hasOwnProperty("code")) {
																																		if (layers[f]["code"] === nowCode) {
																																			codeExist = true;
																																		}
																																	}
																																}
																															}
																														} else {
																															isOK = false;
																															this
																																	.setMessagePopup(
																																			"danger",
																																			nowCode
																																					+ "레이어 코드는 레이어 정의에 입력되어 있지 않습니다.");
																														}
																													}
																												}
																											}
																											if (nameExist === false
																													&& codeExist === false) {
																												isOK = false;
																												this
																														.setMessagePopup(
																																"danger",
																																"분류명 "
																																		+ nowName
																																		+ "의 레이어코드 "
																																		+ nowCode
																																		+ "는 레이어 정의에 입력되지 않았습니다.");
																											}
																										}
																									}
																								}
																							}
																						}
																					} else if (toleranceArr !== null
																							&& !Array.isArray(toleranceArr)) {
																						isOK = false;
																						this.setMessagePopup("danger",
																								"tolerance는 null 또는 배열의 형태여야 합니다.");
																						console.error("");
																					}
																				}
																			}
																		}
																	}
																} else if (relationArr !== null && !Array.isArray(relationArr)) {
																	isOK = false;
																	this.setMessagePopup("danger", "relation은 null 또는 배열의 형태여야 합니다.");
																	console.error("");
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			isOK = false;
			this.setMessagePopup("danger", " definition키는 배열 형태여야 합니다.");
		}
	} else {
		isOK = false;
		this.setMessagePopup("danger", " 옵션 정의가 입력되지 않았습니다.");
	}
	if (isOK) {
		this.structure = strc;
		this.setMessagePopup("success", " [검수 항목 정의]가 변경 되었습니다.");
	}
};

gb.embed.OptionDefinition.prototype.getStructure = function() {
	return this.structure;
};

gb.embed.OptionDefinition.prototype.setJSONFile = function() {

};

gb.embed.OptionDefinition.prototype.getJSONFile = function() {
	// Opera 8.0+
	var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;

	// Firefox 1.0+
	var isFirefox = typeof InstallTrigger !== 'undefined';

	// Safari 3.0+ "[object HTMLElementConstructor]"
	var isSafari = /constructor/i.test(window.HTMLElement) || (function(p) {
		return p.toString() === "[object SafariRemoteNotification]";
	})(!window['safari'] || (typeof safari !== 'undefined' && safari.pushNotification));

	// Internet Explorer 6-11
	var isIE = /* @cc_on!@ */false || !!document.documentMode;

	// Edge 20+
	var isEdge = !isIE && !!window.StyleMedia;

	// Chrome 1+
	var isChrome = !!window.chrome && !!window.chrome.webstore;

	// Blink engine detection
	var isBlink = (isChrome || isOpera) && !!window.CSS;

	if (isIE) {
		download(JSON.stringify(this.getStructure()), "option_setting.json", "text/plain");
	} else {
		var setting = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(this.getStructure()));
		var anchor = $("<a>").attr({
			"href" : setting,
			"download" : "option_setting.json"
		});
		$(anchor)[0].click();
	}
};