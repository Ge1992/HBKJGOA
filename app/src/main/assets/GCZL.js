/*
�������ϲ��������
         
Լ����
1.���ù������������»���
2.˽�й��������»��ߴ�ͷ:"__"
3.˽�б���һ���»��ߴ�ͷ:"_"
*/


//������汾
var _isFF = false;
var _isIE = false;
var _isOpera = false;
var _isKHTML = false;
var _isMacOS = false;
  if (navigator.userAgent.indexOf('Macintosh') != -1)
    _isMacOS = true;
if ((navigator.userAgent.indexOf('Safari') != -1) || (navigator.userAgent.indexOf('Konqueror') != -1))
    _isKHTML = true;
else if (navigator.userAgent.indexOf('Opera') != -1) {
    _isOpera = true;
    _OperaRv = parseFloat(navigator.userAgent.substr(navigator.userAgent.indexOf('Opera') + 6, 3));
}
else if (navigator.appName.indexOf("Microsoft") != -1)
    _isIE = true;
else {
    _isFF = true;
}

var _currTD = null;
var _inputTA = null;
var objInput = null;
var objTextarea = null;
var totalLine = 0;
var totalCol = 0;
var currTDHeight = null;

$.extend({
    includePath: '',
    include: function(file) {
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++) {
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";
            if ($(tag + "[" + link + "]").length == 0) document.write("<" + tag + attr + link + "></" + tag + ">");
        }
    }
});



//ҳ�������Ϻ���ø÷������г�ʼ��
function initLib() {
    
    var doee=document.getElementById("PNAME");
  
    var obj = document.createElement("textarea");
    obj.onblur = setData;
    obj.onkeyup = detectEvent;
    objInput = $("input");
    for (var i = 0; i < objInput.length; i++)
        objInput[i].onkeyup = detectEvent;
    objTextarea = $("textarea");
    for (var i = 0; i < objTextarea.length; i++)
        objTextarea[i].onkeyup = detectEvent;
    _inputTA = $(obj);
 
    _inputTA.attr("id", "txtTA");
    _inputTA.css("position", "absolute");
    //_inputTA.css("z-index", "-99"); //����textarea��������Ͳ�
    _inputTA.css("width", "0px");
    _inputTA.css("height", "0px");
    _inputTA.css("margin", "0px");
    _inputTA.css("font-family", "����");
    _inputTA.css("font-size", "12px");
    _inputTA.css("border-style", "none");
    _inputTA.css("border-width", "0px");
    _inputTA.css("overflow", "hidden");
    _inputTA.css("display", "none");
    $("#divTool").append(obj);

    window.onhelp = function() { window.event.returnValue = false; };
}
//ȥ����β�հ�
String.prototype.trim = function() {
    var str = this;
    str = str.replace(/&nbsp;/ig, ""); //ȫ��ƥ��&nbsp�������ִ�Сд;
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
function submitChange() {
    if (_inputTA.css("display") != "none")
        setData();
}

//��˳��Ϊinput,textarea�Լ��ɱ༭td��DirectionKeys����(���㷽�������)
function addClassValue(pStep) {
    $("tr").each(function(i) {
        var j = 0;
        $(this).find(".Content" + pStep).each(function(j) {
            $(this).attr("DirectionKey", i + "DirectionKey" + j);
            if (j > totalCol)
                totalCol = j;
        });
        totalLine = i;
    });
}

//�Ƴ�input,textarea�Լ��ɱ༭td�е�DirectionKey����
function removeClassValue(pStep) {
    $(".Content" + pStep).each(function(i) {
        $(this).removeAttr("DirectionKey")
    });
}

function SetFocus(id) {
    if (typeof ($("input,td,textarea").attr("DirectionKey")) != "undefined" || $("[DirectionKey]").length > 0) {
        $("[DirectionKey='" + id + "']").focus();
        $("[DirectionKey='" + id + "']").focus(function() {
            if (_inputTA.css("display") != "none")
                setData();
            if (!$(this).is("td")) {
                $("td").removeClass("DirectionKey");
                $(this).focusEnd();
            }
            else {
                $("td").removeClass("DirectionKey");
                $(this).addClass("DirectionKey");
                _currTD = $(this);
            }
        });
    }
    return false;
}

function GetLineColMax(Line, Col) {
    var PreLine = NextLine = Line;
    var CurrentLineColMax = Col;
    if (typeof ($("input,td,textarea").attr("DirectionKey")) != "undefined" || $("[DirectionKey]").length > 0) {
        for (var i = Number(Line) + Number(1); i <= totalLine; i++) {
            if ($("[DirectionKey='" + i + "DirectionKey0']").length > 0) {
                NextLine = i;
                break;
            }
        }
        for (var i = Line - 1; i >= 0; i--) {
            if ($("[DirectionKey='" + i + "DirectionKey0']").length > 0) {
                PreLine = i;
                break;
            }
        }
        for (var n = 0; n <= totalCol; n++) {
            if ($("[DirectionKey='" + Line + "DirectionKey" + n + "']").length > 0) {
                continue;
            }
            else {
                CurrentLineColMax = n - 1;
                break;
            }
        }
    }
    return { PreLine: PreLine, NextLine: NextLine, CurrentLineColMax: CurrentLineColMax };
}

function registerEvent(pStep, pCanEdit) {
    //�󶨰����¼�
    $(".Content" + pStep).keypress(function(e) {
        if (!$(this).is("td")) return;
        setTA(true);
        $(this).removeClass("DirectionKey");
        window.event.cancelBubble = true;
    });
    //�󶨻�ȡ�����¼�
    $(".Content" + pStep).focus(function() {
        if (typeof ($("input,td,textarea").attr("DirectionKey")) != "undefined" || $("[DirectionKey]").length > 0) {
            if ($(this).attr("DirectionKey") != "" && $(this).attr("id") == "")
                arr = $(this).attr("DirectionKey").match(/\d+/g);
        }
        if ($(this).is("input") || $(this).is("textarea")) {
            $("input").removeClass("mouseDown");
            $("textarea").removeClass("mouseDown");
            $(this).addClass("mouseDown");
        }
        else
            return;
        window.event.cancelBubble = true;
    });
    $("input[class!=Content" + pStep + "],textarea[class!=Content" + pStep + "],input[id]").focus(function() {
        $("input").removeClass("mouseDown");
        $("textarea").removeClass("mouseDown");
    });
    //����굥���¼�
    $(".Content" + pStep).click(function() {
        if (_inputTA.css("display") != "none") {
            setData();
        }
        if (!$(this).is("td"))
            return;
        _currTD = $(this);
        //��ʼ����ǰtd�߶�
        var n = Number(_currTD.height()) + Number(4);
        if (_inputTA.val() == "")
            n = _currTD.height();
        styleRender(n);

        setTA(true);
        _currTD.addClass("currentTDcss");
        $("td").each(function() {
            $(this).css("background-color", "");
            $(this).removeClass("DirectionKey");
        });
        //$(this).css("background-color", "");
        //$(this).addClass("DirectionKey");
        window.event.cancelBubble = true;
    });

    //�����˫���¼�
    //$(".Content" + pStep).dblclick(function() {
    // _inputTA.removeClass("DirectionKey");
    // if (!$(this).is("td")) return;
    // $(this).css("background-color", "");
    // _currTD = $(this);
    //  setTA(true);
    // window.event.cancelBubble = true;
    //});
    
    //����ǰ�����idΪ�յ�Ԫ������Ϊ��ֻ��
    if (pCanEdit == "Y") {
         $(".Content" + pStep).each(function() {
             //if ($(this).attr("id") == "") {
            $(this).removeAttr("readonly");
            //}
        });

        $("img").click(function() {
            if ($(this).attr("src").indexOf("/Content/signs", 0) >= 0)
                return;
            if ($(this).attr("class") == ("Content" + pStep)) {
                if ($(this).attr("src") == "http://180.97.31.224:8007/Content/images/wx.gif") {
                    $(this).attr("src", "http://180.97.31.224:8007/Content/images/wx1.gif");
                } else {
                    $(this).attr("src", "http://180.97.31.224:8007/Content/images/wx.gif");
                }
            }
        });
    }
}

/* ���õ�Ԫ����������ı� */
function setTA(isIncludeOldValue) {
    if (_currTD == null) return;

    if (_currTD.is("td")) {
        var iPL = parseInt(_currTD.css("padding-left").replace("px", ""));
        var iPR = parseInt(_currTD.css("padding-right").replace("px", ""));
        var iPT = parseInt(_currTD.css("padding-top").replace("px", ""));
        var iPB = parseInt(_currTD.css("padding-bottom").replace("px", ""));
        var h = _currTD.height() + iPT + iPB - 3;
        var w = _currTD.width() + iPL + iPR - 3;
        var l = _currTD.offset().left + 1;
        var t = _currTD.offset().top + 2;

        //�Ƿ���TD�е�ԭʼ����
        if (isIncludeOldValue) {
            if (_currTD.text() == "&nbsp;") {
                _inputTA.html("");
            } else {
                var uk = _currTD.html();
                uk = uk.replace(/<br>/g, "\r\n");
                _inputTA.html(uk.trim());
                _currTD.html("");
            }
        }
        else {
            _inputTA.html("");
        }
        _inputTA.height(h);

        _inputTA.width(w);
        _inputTA.css("left", l);
        _inputTA.css("top", t);
        _inputTA.show();
        _inputTA.focus();
        //��λ��굽�ı����
        var obj = document.getElementById(_inputTA.attr("id"));
       
        var _range = obj.createTextRange();
        var sText = _inputTA.val();
         var iLen = sText.length;
//        _range.move('character', iLen);
        //        _range.select();
       
        obj.setSelectionRange(iLen, iLen+1);
        obj.focus();
        
    }
}
/* ������ʽ�����Ƶ�ǰtd�߶�*/
function styleRender(n) {
    //    var m = document.styleSheets[3].length;
    //    var cssRules = document.styleSheets[3].cssRules || document.styleSheets[3].rules;
    //    if (document.styleSheets[3].insertRule) {
    //        document.styleSheets[3].insertRule(".currentTDcss{height:" + n + "px;}", m);
    //    }
    //    else {
    //        document.styleSheets[3].addRule(".currentTDcss", "height:" + n + "px;", m);
    //    }
}

/* TextAreaʧȥ����������Ŀ��Ԫ���� */
function setData() {
    if (_currTD == null) return;

    var k = _inputTA.val();
    if (k == "") k = " ";
    k = k.replace(/\r\n/g, "<br>");
    _currTD.html(k);
    _inputTA.hide();
}

//����TextArea�İ���������
function detectEvent(e) {
    var evt = e || window.event;
    if (evt.keyCode == 112) {
        //alert("F1");
        //Underline();
        window.showModelessDialog("/PUBLIC/PUBLIC/ShowTSZF", window, "status:no;dialogHeight:470px;dialogWidth:600px;");
    }
    else if (evt.ctrlKey && evt.keyCode == 13) {
        //ctrl+enter����
        //        var selectText = document.selection.createRange();
        //        if (selectText) {
        //            if (selectText.text.length > 0)
        //                selectText.text += "\r\n";
        //            else
        //                selectText.text = "\r\n";
        //            selectText.select();
        //        }
        setData();
    }
    //    else if (evt.keyCode == 13) {
    //        //_inputTA.hide();
    //        //���λس�������
    //        evt.keyCode = "";
    //        //var obj = document.getElementById(_inputTA.attr("id"));
    //        //obj.onblur = null; //������onblur�¼�����������TextArea�ᴥ��onblur���ٴε���setData����
    //        setData();
    //        //obj.onblur = setData;
    //    }
    window.event.cancelBubble = true;
    //return document.defaultAction;
}

/* ���嵥Ԫ���ʽ���� */

//�����
function setAlignLeft() {
    if (_currTD == null) return;
    _currTD.css("text-align", "left");
    _currTD.focus();
}

//ˮƽ����
function setAlignCenter() {
    if (_currTD == null) return;
    _currTD.css("text-align", "center");
    _currTD.focus();
}

//�Ҷ���
function setAlignRight() {
    if (_currTD == null) return;
    _currTD.css("text-align", "right");
    _currTD.focus();
}

//�϶���
function setVAlignTop() {
    if (_currTD == null) return;
    _currTD.attr("valign", "top");
    _currTD.focus();
}

//��ֱ����
function setVAlignMiddle() {
    if (_currTD == null) return;
    _currTD.attr("valign", "middle");
    _currTD.focus();
}

//�¶���
function setVAlignBottom() {
    if (_currTD == null) return;
    _currTD.attr("valign", "bottom");
    _currTD.focus();
}

//������Ԫ��
function setReadonly(isReadonly) {
    if (isReadonly) {
        _currTD.css("background-color", "#E0E0E0");
        _currTD.focus();
    }
    else {
        _currTD.css("background-color", "#FFFFFF");
        _currTD.focus();
    }
}


/* ���ú��� */
function setFocus(id) {
    $("#" + id).focus();
    var obj = document.getElementById(id);
    //        var _range = obj.createTextRange();
    var sText = _inputTA.val();
    
    var iLen = sText.length;
    //        _range.move('character', iLen);
    //        _range.select();
    
    obj.setSelectionRange(iLen, iLen+1);
    obj.focus();
}

function AddText(str) {
    var obj = $(".mouseDown");
    if (_inputTA.css("display") != "none") {
        var ubb = document.getElementById("txtTA");
        var ubbLength = ubb.value.length;
        ubb.focus();
        if (typeof document.selection != "undefined") {
            document.selection.createRange().text = str;
        }
        else {
            ubb.value = ubb.value.substr(0, ubb.selectionStart) + str + ubb.value.substring(ubb.selectionStart, ubbLength);
        }
    }
    else if (obj.length > 0) {
        insertAtCursor(obj, str);
    }
    else
        return;
}

function Underline() {
    var tUnderline = prompt("������Ҫ���õ��»�������\n��ǩ:[u][/u]", "")
    if (tUnderline == "" || tUnderline == null) {
        return;
    }
    tUnderline = "[u]" + tUnderline + "[/u]"
    AddText(tUnderline);
}
function insertAtCursor(obj, str) {
    if (document.selection) {
        obj.focus();
        sel = document.selection.createRange();
        sel.text = str;
        sel.select();
    }
    else if (obj.selectionStart || obj.selectionStart == '0') {
        var startPos = obj.selectionStart;
        var endPos = obj.selectionEnd;
        var restoreTop = obj.scrollTop;
        obj.value = obj.value.substring(0, startPos) + str + obj.value.substring(endPos, obj.value.length);
        if (restoreTop > 0) {
            obj.scrollTop = restoreTop;
        }
        obj.focus();
        obj.selectionStart = startPos + str.length;
        obj.selectionEnd = startPos + str.length;
    } else {
        obj.value += str;
        obj.focus();
    }
}
//��궨λ����λ�����
$.fn.focusEnd = function() {
    this.setCursorPosition(this.val().length);
}
$.fn.setCursorPosition = function(position) {
    if (this.lengh == 0) return this;
    return $(this).setSelection(position, position);
}

$.fn.setSelection = function(selectionStart, selectionEnd) {
    if (this.lengh == 0) return this;
    input = this[0];

    if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    } else if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }
    return this;
}

//���������
function produceRandom() {
    var obj = $("tr>td[class*='Content1']");
    $("tr").each(function() {
        //alert($(this).children("td[class*='Content1']").length);
        //if ($(this).children("td[class*='Content1']").length == 10) {
        //$(this).addClass("trRandom");
        //}
        var standValue = null;
        if ($(this).children("td[class*='Content1']").length == 10) {
            $(this).children("td[class*='Content1']").each(function() {
                if (standValue == null)
                    standValue = $(this).prev().text();
                var patrn1 = /��/;
                var patrn2 = /-/;
                if (patrn1.exec(standValue))
                    $(this).text(Math.round(getRandomValue(-10, 10)));
                else if (patrn2.exec(standValue))
                    $(this).text(Math.round(getRandomValue(0, 10)));
                else
                    $(this).text(Math.round(getRandomValue(-10, 0)));

            });
        }
    });
}
//x����ֵ,y����ֵ
function getRandomValue(x, y) {
    return Math.random() * (y - x + 1) + x;
}

//ɾ����������
function valueDel() {
    if (_currTD == null)
        return;
    _currTD.siblings("td[class*='Content1'][id='']").each(function() {
        $(this).text("");
        _inputTA.val("");
    });
}
