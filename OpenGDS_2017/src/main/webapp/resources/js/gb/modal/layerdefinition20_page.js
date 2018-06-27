/**
 * 레이어 코드를 정의하는 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 04
 * @version 0.01
 */
var gitbuilder;
if (!gitbuilder)
	gitbuilder = {};
if (!gitbuilder.ui)
	gitbuilder.ui = {};
gitbuilder.ui.LayerDefinition20 = $.widget("gitbuilder.layerdefinition20", {
	widnow : undefined,
	layerDef : undefined,
	geom : undefined,
	tbody : undefined,
	tbody2 : undefined,
	message : undefined,
	file : undefined,
	copyNameList : undefined,
	objCopy : undefined,
	addNameList : undefined,
	addObj : undefined,
	pagination : undefined,
	okBtn : undefined,
	options : {
		definition : undefined,
		appendTo : "body"
	},
	_create : function() {
		this.layerDef = $.extend({}, this.options.definition);
		var that = this;
		this._on(false, this.element, {
			click : function(event) {
				if (event.target === that.element[0]) {
					that.open();
				}
			}
		});
		var xSpan = $("<span>").attr({
			"aria-hidden" : true
		}).html("&times;");
		var xButton = $("<button>").attr({
			"type" : "button",
			"data-dismiss" : "modal",
			"aria-label" : "Close"
		}).html(xSpan);
		this._addClass(xButton, "close");

		var htag = $("<h4>");
		htag.text("Layer Definition");
		this._addClass(htag, "modal-title");

		var header = $("<div>").append(xButton).append(htag);
		this._addClass(header, "modal-header");
		/*
		 * 
		 * 
		 * header end
		 * 
		 * 
		 */
		var tdhead1 = $("<td>").text("#");
		var tdhead2 = $("<td>").text("Layer Name");
		var tdhead3 = $("<td>").text("Layer Code");
		var tdhead4 = $("<td>").text("Geometry Type");
		var tdhead5 = $("<td>").text("Delete");
		var tdhead6 = $("<td>").text("QA Area");
		// var tdhead7 = $("<td>").text("Weight");
		var trhead = $("<tr>").append(tdhead1).append(tdhead2).append(tdhead3).append(tdhead4).append(tdhead5).append(tdhead6);
		var thead = $("<thead>").append(trhead);
		that.tbody = $("<tbody>");
		that.tbody2 = $("<tbody>");
		var tb = $("<table>").append(thead).append(that.tbody);
		this._addClass(tb, "table");
		this._addClass(tb, "table-striped");
		this._addClass(tb, "text-center");

		var tdhead12 = $("<td>").text("#");
		var tdhead22 = $("<td>").text("Layer Name");
		var tdhead32 = $("<td>").text("Layer Code");
		var tdhead42 = $("<td>").text("Geometry Type");
		var tdhead52 = $("<td>").text("Delete");
		var tdhead62 = $("<td>").text("QA Area");
		// var tdhead72 = $("<td>").text("Weight");
		var trhead2 = $("<tr>").append(tdhead12).append(tdhead22).append(tdhead32).append(tdhead42).append(tdhead52).append(tdhead62);
		var thead2 = $("<thead>").append(trhead2);
		var tb2 = $("<table>").append(thead2).append(that.tbody2);
		this._addClass(tb2, "table");
		this._addClass(tb2, "table-striped");
		this._addClass(tb2, "text-center");
		this.update();
		$(document).on("click", ".layerdefinition20-del", function(event) {
			var laName;
			if (event.target === this) {
				laName = $(event.target).parent().parent().find("td:eq(1) > input").val();
				$(event.target).parent().parent().remove();
			} else if ($(event.target).parent()[0] === this) {
				laName = $(event.target).parent().parent().parent().find("td:eq(1) > input").val();
				$(event.target).parent().parent().parent().remove();
			}
			// console.log(laName);
			delete that.objCopy[laName];
			// console.log(that.objCopy);
			var page = $(that.pagination).find(".active").text();
			// console.log(page);
			that.update(that.objCopy, page);
		});
		$(document).on("click", ".layerdefinition20-del-temp", function(event) {
			var laName;
			if (event.target === this) {
				laName = $(event.target).parent().parent().find("td:eq(1) > input").val();
				$(event.target).parent().parent().remove();
			} else if ($(event.target).parent()[0] === this) {
				laName = $(event.target).parent().parent().parent().find("td:eq(1) > input").val();
				$(event.target).parent().parent().parent().remove();
			}
			// console.log(laName);
			// console.log(that.objCopy);
			// console.log("del-temp");
		});
		$(document).on("input", ".layerdefinition20-lname-temp", function(event) {
			// console.log($(this).val());
			var idx = $(this).parent().parent().index();
			// console.log(idx);
			that.setTempData(idx);
		});
		$(document).on("input", ".layerdefinition20-lname", function(event) {
			var idx = $(this).parent().parent().index();
			// console.log(idx);
			that.setCopyData(idx);
		});
		$(document).on("input", ".layerdefinition20-lcode-temp", function(event) {
			// console.log($(this).val());
			var idx = $(this).parent().parent().index();
			// console.log(idx);
			that.setTempData(idx);
		});
		$(document).on("input", ".layerdefinition20-lcode", function(event) {
			// console.log($(this).val());
			var idx = $(this).parent().parent().index();
			// console.log(idx);
			that.setCopyData(idx);
		});
		$(document).on("change", ".layerdefinition20-geom-temp", function(event) {
			// console.log($(this).val());
			var idx = $(this).parent().parent().index();
			// console.log(idx);
			that.setTempData(idx);
		});
		$(document).on("change", ".layerdefinition20-geom", function(event) {
			// console.log($(this).val());
			var idx = $(this).parent().parent().index();
			// console.log(idx);
			that.setCopyData(idx);
		});
		$(document).on("change", ".layerdefinition20-area-temp", function(event) {
			// console.log($(this).val());
			var idx = $(this).parent().parent().index();
			// console.log(idx);
			that.setTempData(idx);
		});
		$(document).on("change", ".layerdefinition20-area", function(event) {
			// console.log($(this).val());
			var idx = $(this).parent().parent().index();
			// console.log(idx);
			that.setCopyData(idx);
		});
		var addBtn = $("<button>").attr({
			"type" : "button"
		});
		this._addClass(addBtn, "btn");
		this._addClass(addBtn, "btn-default");
		$(addBtn).text("Add Row");
		this._on(false, addBtn, {
			click : function(event) {
				if (event.target === addBtn[0]) {
					$(that.upper).hide();
					if (!$(that.upper2).is(":visible")) {
						$(that.tbody2).empty();
						$(that.upper2).show();
					}
					if (Object.keys(that.objCopy).length === 0) {
						$(that.backBtn).hide();
					} else {
						$(that.backBtn).show();
					}
					var num = $(that.tbody2).find("tr:last").index() + 2 + Object.keys(that.objCopy).length;
					// console.log(num);
					var no = $("<span>").css({
						"vertical-align" : "-webkit-baseline-middle"
					}).text(num);
					var td1 = $("<td>").append(no);
					var lname = $("<input>").addClass("layerdefinition20-lname-temp").val("");
					this._addClass(lname, "form-control");
					var td2 = $("<td>").append(lname);
					$(td2).attr({
						"type" : "text"
					});
					var lcode = $("<input>").addClass("layerdefinition20-lcode-temp").val("");
					this._addClass(lcode, "form-control");
					var td3 = $("<td>").append(lcode);
					$(td3).attr({
						"type" : "text"
					});
					var ty1 = $("<option>").text("Point").val("point");
					var ty2 = $("<option>").text("LineString").val("linestring");
					var ty3 = $("<option>").text("Polygon").val("polygon");
					var ty4 = $("<option>").text("Text").val("text");
					var gtype = $("<select>").addClass("layerdefinition20-geom-temp").append(ty1).append(ty2).append(ty3).append(ty4);
					this._addClass(gtype, "form-control");
					var td4 = $("<td>").append(gtype);
					var icon = $("<i>").attr("aria-hidden", true);
					this._addClass(icon, "fa");
					this._addClass(icon, "fa-times");
					var delBtn = $("<button>").append(icon);
					this._addClass(delBtn, "btn");
					this._addClass(delBtn, "btn-default");
					this._addClass(delBtn, "layerdefinition20-del-temp");
					var td5 = $("<td>").append(delBtn);
					var radio = $("<input>").attr({
						"type" : "checkbox",
						"name" : "layerdefinition20-area-temp"
					}).css({
						"vertical-align" : "-webkit-baseline-middle"
					}).addClass("layerdefinition20-area-temp");
					var td6 = $("<td>").append(radio);
					// var weight = $("<input>").attr({
					// "type" : "number",
					// "min" : 1,
					// "max" : 100
					// });
					// that._addClass(weight, "form-control");
					// var td7 = $("<td>").append(weight);
					var tr = $("<tr>").append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
					$(that.tbody2).append(tr);
				}
			}
		});

		this.pagination = $("<nav>").addClass("text-center");
		this.upper = $("<div>").css({
			"overflow-y" : "auto",
			"height" : "400px"
		}).append(tb).append(this.pagination);
		var i = $("<i>").attr({
			"aria-hidden" : true
		}).addClass("fa").addClass("fa-reply");
		this.backBtn = $("<button>").addClass("btn").addClass("btn-default").append("Back").click(function() {
			that.objCopy = that.includeAddObject();
			// console.log(that.objCopy);
			$(that.upper).show();
			$(that.upper2).hide();
			that.update(that.objCopy);
		});
		this.upper2 = $("<div>").css({
			"overflow-y" : "auto",
			"height" : "400px"
		}).append(this.backBtn).append(tb2);
		this.message = $("<p>").css({
			"display" : "none"
		});
		this._addClass(this.message, "text-danger");
		var mid = $("<div>").append(this.message).append(addBtn);
		this.file = $("<input>").attr({
			"type" : "file"
		}).css({
			"float" : "left",
			"display" : "inline-block"
		});
		var lower = $("<div>").css({
			"display" : "none",
			"height" : "30px",
			"margin" : "5px 0"
		}).append(this.file);
		this._on(false, this.file, {
			change : function(event) {
				var fileList = that.file[0].files;
				var reader = new FileReader();
				if (fileList.length === 0) {
					return;
				}
				reader.readAsText(fileList[0]);
				that._on(false, reader, {
					load : function(event) {
						var obj = JSON.parse(reader.result.replace(/(\s*)/g, ''));
						that.objCopy = obj;
						that.update(obj);
						$(lower).css("display", "none");
					}
				});
			}
		});
		var body = $("<div>").append(this.upper).append(this.upper2).append(mid).append(lower);
		this._addClass(body, "modal-body");
		/*
		 * 
		 * 
		 * body end
		 * 
		 * 
		 */
		var uploadBtn = $("<button>").attr({
			"type" : "button"
		});
		this._addClass(uploadBtn, "btn");
		this._addClass(uploadBtn, "btn-default");
		$(uploadBtn).text("Upload");
		this._on(false, uploadBtn, {
			click : function(event) {
				if (event.target === uploadBtn[0]) {
					if ($(lower).css("display") === "none") {
						$(lower).css("display", "block");
					} else {
						$(lower).css("display", "none");
					}
				}
			}
		});
		var downloadBtn = $("<button>").attr({
			"type" : "button"
		});
		this._addClass(downloadBtn, "btn");
		this._addClass(downloadBtn, "btn-default");
		$(downloadBtn).text("Download");
		this._on(false, downloadBtn, {
			click : function(event) {
				if (event.target === downloadBtn[0]) {
					that.downloadSetting();
				}
			}
		});

		var pleft = $("<span>").css("float", "left");
		// this._addClass(pleft, "text-left");
		$(pleft).append(uploadBtn).append(downloadBtn);

		var closeBtn = $("<button>").attr({
			"type" : "button",
			"data-dismiss" : "modal"
		});
		this._addClass(closeBtn, "btn");
		this._addClass(closeBtn, "btn-default");
		$(closeBtn).text("Close");

		this.okBtn = $("<button>").attr({
			"type" : "button"
		});
		this._addClass(this.okBtn, "btn");
		this._addClass(this.okBtn, "btn-primary");
		$(this.okBtn).text("Save");

		this._on(false, this.okBtn, {
			click : function(event) {
				if (event.target === that.okBtn[0]) {
					if ($(that.upper).is(":visible")) {
						var obj = that.objCopy;
						if (!!obj) {
							that.setDefinition(obj);
							that.close();
						}
					} else if ($(that.upper2).is(":visible")) {
						// console.log("upper2");
						that.objCopy = that.includeAddObject();
						if (!!that.objCopy) {
							that.setDefinition(that.objCopy);
							that.close();
						}
					}
				}
			}
		});

		var pright = $("<span>").css("float", "right");
		// this._addClass(pright, "text-right");
		$(pright).append(closeBtn).append(this.okBtn);

		var footer = $("<div>").append(pleft).append(pright);
		this._addClass(footer, "modal-footer");
		/*
		 * 
		 * 
		 * footer end
		 * 
		 * 
		 */
		var content = $("<div>").append(header).append(body).append(footer);
		this._addClass(content, "modal-content");

		var dialog = $("<div>").html(content);
		this._addClass(dialog, "modal-dialog");
		this._addClass(dialog, "modal-lg");

		this.window = $("<div>").hide().attr({
			// Setting tabIndex makes the div focusable
			tabIndex : -1,
			role : "dialog",
		}).html(dialog);
		this._addClass(this.window, "modal");
		this._addClass(this.window, "fade");

		this.window.appendTo(this._appendTo());
		this.window.modal({
			backdrop : "static",
			keyboard : true,
			show : false,
		});
	},
	_init : function() {
		this.layerDef = $.extend({}, this.options.definition);
	},
	downloadSetting : function() {
		var def;
		if ($(this.upper).is(":visible")) {
			def = this.objCopy;
			if (!!def) {
				var setting = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(def));
				var anchor = $("<a>").attr({
					"href" : setting,
					"download" : "layer_setting.json"
				});
				$(anchor)[0].click();
			}
		} else if ($(this.upper2).is(":visible")) {
			// def = this.addObj;
			this.setMessage("Please, Save first");
		}

	},
	setDefinition : function(obj) {
		this.layerDef = obj;
	},
	getDefinition : function() {
		return this.layerDef;
	},
	update : function(obj, page) {
		var that = this;
		page = parseInt(page);
		$(this.tbody).empty();
		$(this.pagination).empty();
		$(that.message).css({
			"display" : "none"
		});
		if (!obj) {
			obj = this.layerDef;
		}

		this.copyNameList = Object.keys(obj);
		if (this.copyNameList.length === 0) {
			$(this.upper).hide();
			$(this.upper2).show();
			$(this.backBtn).hide();
			return;
		} else {
			$(this.upper).show();
			$(this.upper2).hide();
		}

		this.copyNameList.sort();
		var itemStartNum, itemEndNum, startPageNum;
		if (!page) {
			page = 1;
		}
		if (this.copyNameList.length >= (page * 5)) {
			itemEndNum = (page * 5);
			itemStartNum = (page * 5) - 5;
		} else {
			itemEndNum = this.copyNameList.length;
			itemStartNum = this.copyNameList.length - (this.copyNameList.length % 5);
		}
		for (itemStartNum; itemStartNum < itemEndNum; itemStartNum++) {
			var no = $("<span>").css({
				"vertical-align" : "-webkit-baseline-middle"
			}).text((itemStartNum + 1));
			var td1 = $("<td>").append(no);
			var lname = $("<input>").attr({
				"type" : "text"
			}).addClass("layerdefinition20-lname").val(this.copyNameList[itemStartNum]);
			this._addClass(lname, "form-control");
			var td2 = $("<td>").append(lname);

			var lcode = $("<input>").attr({
				"type" : "text"
			}).addClass("layerdefinition20-lcode").val(obj[this.copyNameList[itemStartNum]].code.toString());
			this._addClass(lcode, "form-control");
			var td3 = $("<td>").append(lcode);

			var ty1 = $("<option>").text("Point").val("point");
			var ty2 = $("<option>").text("LineString").val("linestring");
			var ty3 = $("<option>").text("Polygon").val("polygon");
			var ty4 = $("<option>").text("Text").val("text");
			var gtype = $("<select>").addClass("layerdefinition20-geom").append(ty1).append(ty2).append(ty3).append(ty4).val(
					obj[this.copyNameList[itemStartNum]].geom);
			this._addClass(gtype, "form-control");
			var td4 = $("<td>").append(gtype);

			var icon = $("<i>").attr("aria-hidden", true);
			this._addClass(icon, "fa");
			this._addClass(icon, "fa-times");
			var delBtn = $("<button>").append(icon);
			this._addClass(delBtn, "btn");
			this._addClass(delBtn, "btn-default");
			this._addClass(delBtn, "layerdefinition20-del");
			var td5 = $("<td>").append(delBtn);
			var radio = $("<input>").attr({
				"type" : "checkbox",
				"name" : "layerdefinition20-area"
			}).css({
				"vertical-align" : "-webkit-baseline-middle"
			}).addClass("layerdefinition20-area");
			if (obj[this.copyNameList[itemStartNum]].area) {
				$(radio).prop("checked", true);
			} else {
				$(radio).prop("checked", false);
			}
			var td6 = $("<td>").append(radio);
			// var weight = $("<input>").attr({
			// "type" : "number",
			// "min" : 1,
			// "max" : 100
			// }).val(obj[keys[i]].weight);
			// that._addClass(weight, "form-control");
			// var td7 = $("<td>").append(weight);
			var tr = $("<tr>").append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
			$(that.tbody).append(tr);
		}
		// 총 페이지 수
		var totalPageNum = Math.ceil(this.copyNameList.length >= 5 ? this.copyNameList.length / 5 : 1);
		// 현재 페이지 위치
		var currentPageNum = (page * 5) >= 5 ? page > totalPageNum ? totalPageNum : page : 1;
		// 보여줄 마지막 페이지
		var maxDisplayNum = 5;
		// 페이지 링크 영역
		var ul = $("<ul>").addClass("pagination");
		// 시작 페이지
		var start = 0;
		while (currentPageNum > maxDisplayNum) {
			if (currentPageNum >= totalPageNum) {
				maxDisplayNum = totalPageNum;
				start = maxDisplayNum - 5;
				break;
			} else {
				maxDisplayNum = maxDisplayNum + 5;
				if (totalPageNum < maxDisplayNum) {
					maxDisplayNum = totalPageNum;
				}
				start = maxDisplayNum - 5;
			}
		}
		if (totalPageNum < 5) {
			start = 0;
			maxDisplayNum = totalPageNum;
		}
		var pre = $("<span>").attr({
			"aria-hidden" : true
		}).html("&laquo;");
		var sa = $("<a>").attr({
			"href" : "#",
			"aria-label" : "Previous"
		}).append(pre).click(function() {
			that.update(that.objCopy, currentPageNum === 1 ? 1 : currentPageNum - 1);
		});
		var sli = $("<li>").append(sa);
		$(ul).append(sli);

		for (var i = start; i < maxDisplayNum; i++) {
			var a = $("<a>").attr({
				"href" : "#"
			}).text(i + 1).click(function() {
				that.update(that.objCopy, $(this).text());
			});
			var li = $("<li>").append(a);
			if (page === (i + 1)) {
				$(li).addClass("active");
			}
			$(ul).append(li);
		}

		var next = $("<span>").attr({
			"aria-hidden" : true
		}).html("&raquo;");
		var ea = $("<a>").attr({
			"href" : "#",
			"aria-label" : "Next"
		}).append(next).click(function() {
			that.update(that.objCopy, currentPageNum >= totalPageNum ? totalPageNum : currentPageNum + 1);
		});

		var eli = $("<li>").append(ea);
		$(ul).append(eli);

		$(this.pagination).empty();
		$(this.pagination).append(ul);
	},
	setWarning : function(tr) {
		if (!$(tr).hasClass("warning")) {
			$(tr).addClass("warning");
		}
	},
	setDefault : function(tr) {
		if ($(tr).hasClass("warning")) {
			$(tr).removeClass("warning");
		}
	},
	setMessage : function(text) {
		if (text !== "") {
			$(this.message).css({
				"display" : "block"
			}).text(text);
		} else {
			$(this.message).css({
				"display" : "none"
			});
		}
	},
	checkArea : function(obj) {
		var keys = Object.keys(obj);
		var area = 0;
		var result = false;
		for (var i = 0; i < keys.length; i++) {
			if (obj[keys[i]].area === true) {
				area++;
			}
		}
		if (area === 1) {
			result = true;
		}
		return result;
	},
	setSaveBtn : function(flag) {
		if (flag) {
			$(this.okBtn).prop({
				"disabled" : false
			});
		} else {
			$(this.okBtn).prop({
				"disabled" : true
			});
		}
	},
	setCopyData : function(index) {
		var that = this;
		$(this.tbody).find("tr").each(function() {
			that.setDefault(this);
		});
		this.setMessage("");
		var tr = $(this.tbody).find("tr:eq(" + index + ")");
		var lname = $(tr).find(".layerdefinition20-lname").val().replace(/(\s*)/g, '');
		if (lname === "") {
			this.setMessage("Layer name must be set.");
			this.setWarning(tr);
			this.setSaveBtn(false);
			return;
		}
		var lcode = $(tr).find(".layerdefinition20-lcode").val().replace(/(\s*)/g, '');
		if (lcode === "") {
			this.setMessage("Layer code must be set.");
			this.setWarning(tr);
			this.setSaveBtn(false);
			return;
		}
		var geom = $(tr).find(".layerdefinition20-geom").val();
		var area = $(tr).find(".layerdefinition20-area").is(":checked");
		if (lname !== "" && lcode !== "" && geom) {
			var idx = parseInt($(tr).find("td:eq(0)").text()) - 1;
			var obj = that.objCopy;

			if (this.copyNameList.indexOf(lname) !== -1 && this.copyNameList[idx] !== lname) {
				this.setMessage("Layer name is duplicated.");
				this.setWarning(tr);
				this.setSaveBtn(false);
				return;
			}
			delete obj[this.copyNameList[idx]];
			this.copyNameList[idx] = lname;

			obj[lname] = {
				"code" : lcode.split(","),
				"geom" : geom,
				"area" : area
			};

			if (!this.checkArea(obj)) {
				this.setMessage("QA area is not set or duplicated.");
				this.setWarning(tr);
				this.setSaveBtn(false);
				return;
			}
			this.setSaveBtn(true);
		}
	},
	setTempData : function(index) {
		var that = this;
		$(this.tbody2).find("tr").each(function() {
			that.setDefault(this);
		});
		this.setMessage("");
		var tr = $(this.tbody2).find("tr:eq(" + index + ")");
		if (typeof this.addObj === "undefined") {
			this.addNameList = [];
			this.addObj = {};
		}
		var lname = $(tr).find(".layerdefinition20-lname-temp").val().replace(/(\s*)/g, '');
		if (lname === "") {
			this.setMessage("Layer name must be set.");
			this.setWarning(tr);
			this.setSaveBtn(false);
			return;
		}
		var lcode = $(tr).find(".layerdefinition20-lcode-temp").val().replace(/(\s*)/g, '');
		if (lcode === "") {
			this.setMessage("Layer code must be set.");
			this.setWarning(tr);
			this.setSaveBtn(false);
			return;
		}
		var geom = $(tr).find(".layerdefinition20-geom-temp").val();
		var area = $(tr).find(".layerdefinition20-area-temp").is(":checked");
		if (lname !== "" && lcode !== "" && geom) {
			var def = this.objCopy;
			var isExistOrigin = def.hasOwnProperty(lname);
			var isExistTemp = this.addObj.hasOwnProperty(lname);
			if (isExistOrigin || (isExistTemp && this.addNameList.indexOf(lname) !== index)) {
				this.setMessage("Same layer names are not allowed.");
				this.setWarning(tr);
				this.setSaveBtn(false);
				return;
			}
			delete this.addObj[this.addNameList[index]];
			this.addNameList[index] = undefined;

			this.addNameList[index] = lname;
			this.addObj[lname] = {
				"code" : lcode.split(","),
				"geom" : geom,
				"area" : area
			};

			var tobj = $.extend({}, this.objCopy, this.addObj);
			if (!this.checkArea(tobj)) {
				this.setMessage("Please check QA area.");
				this.setWarning(tr);
				this.setSaveBtn(false);
				return;
			}
			this.setSaveBtn(true);
		}
	},
	includeAddObject : function() {
		var tobj = $.extend({}, this.objCopy, this.addObj);
		this.addObj = {};
		this.addNameList = [];
		return tobj;
	},
	open : function() {
		this.window.modal('show');
		this.objCopy = JSON.parse(JSON.stringify(this.getDefinition()));
		this.update(this.objCopy);
	},
	close : function() {
		this.window.modal('hide');
	},
	destroy : function() {
		this.element.off("click");
		$(this.window).find("button").off("click");
		$(this.window).find("input").off("change").off("load");
		this.window = undefined;
	},
	_appendTo : function() {
		var element = this.options.appendTo;
		if (element && (element.jquery || element.nodeType)) {
			return $(element);
		}
		return this.document.find(element || "body").eq(0);
	},
	_removeClass : function(element, keys, extra) {
		return this._toggleClass(element, keys, extra, false);
	},

	_addClass : function(element, keys, extra) {
		return this._toggleClass(element, keys, extra, true);
	},

	_toggleClass : function(element, keys, extra, add) {
		add = (typeof add === "boolean") ? add : extra;
		var shift = (typeof element === "string" || element === null), options = {
			extra : shift ? keys : extra,
			keys : shift ? element : keys,
			element : shift ? this.element : element,
			add : add
		};
		options.element.toggleClass(this._classes(options), add);
		return this;
	},

	_on : function(suppressDisabledCheck, element, handlers) {
		var delegateElement;
		var instance = this;

		// No suppressDisabledCheck flag, shuffle arguments
		if (typeof suppressDisabledCheck !== "boolean") {
			handlers = element;
			element = suppressDisabledCheck;
			suppressDisabledCheck = false;
		}

		// No element argument, shuffle and use this.element
		if (!handlers) {
			handlers = element;
			element = this.element;
			delegateElement = this.widget();
		} else {
			element = delegateElement = $(element);
			this.bindings = this.bindings.add(element);
		}

		$.each(handlers, function(event, handler) {
			function handlerProxy() {

				// Allow widgets to customize the disabled
				// handling
				// - disabled as an array instead of boolean
				// - disabled class as method for disabling
				// individual parts
				if (!suppressDisabledCheck && (instance.options.disabled === true || $(this).hasClass("ui-state-disabled"))) {
					return;
				}
				return (typeof handler === "string" ? instance[handler] : handler).apply(instance, arguments);
			}

			// Copy the guid so direct unbinding works
			if (typeof handler !== "string") {
				handlerProxy.guid = handler.guid = handler.guid || handlerProxy.guid || $.guid++;
			}

			var match = event.match(/^([\w:-]*)\s*(.*)$/);
			var eventName = match[1] + instance.eventNamespace;
			var selector = match[2];

			if (selector) {
				delegateElement.on(eventName, selector, handlerProxy);
			} else {
				element.on(eventName, handlerProxy);
			}
		});
	},

	_off : function(element, eventName) {
		eventName = (eventName || "").split(" ").join(this.eventNamespace + " ") + this.eventNamespace;
		element.off(eventName).off(eventName);

		// Clear the stack to avoid memory leaks (#10056)
		this.bindings = $(this.bindings.not(element).get());
		this.focusable = $(this.focusable.not(element).get());
		this.hoverable = $(this.hoverable.not(element).get());
	}
});