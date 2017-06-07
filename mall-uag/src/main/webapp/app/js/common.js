/**
 * 公用方法
 * Created by lixiangyang on 2017/5/12.
 */
const Common = {
	// 浅拷贝
    simpleClone: function (p) {
        var c = {};
        for (var i in p) {
            if (p.hasOwnProperty(i)) {
                c[i] = p[i];
            }
        }
        return c;
    },
	deepClone(p, c) {
        var c = c || {};
        for (var i in p) {
            if (typeof p[i] == 'object') {
                c[i] = (p[i].constructor === Array ) ? [] : {};
                deepClone(p[i], c[i]);
            } else {
                if (p.hasOwnProperty(i)) {
                    c[i] = p[i];
                }
            }
        }
        return c;
    },
	/**
	 * date 和 time 合并成 yyyy-MMM-dd HH:mm:ss格式*
	 * */
    formatDateConcat(ymd,hms){
    		var reg = /\d+/;
		if(!reg.test(ymd) || !reg.test(hms)) {
			return '';
		}
		ymd = String(ymd), hms = String(hms);
		var y = ymd.substring(0, 4),
			M = ymd.substring(4, 6),
			d = ymd.substring(6, 8);
		var H = hms.substring(0, 2),
			m = hms.substring(2, 4),
			s = hms.substring(4, 6);
		return y + "-" + M + "-" + d + " " + H + ":" + m + ":" + s;	
    },
    /**
     *  *
     * */
    /**
     * 将日期转换成纯数字格式 *
     * */
    formatDateToNumber(date){
	    	var _date = new Date(date);
	    	if(!date || !_date.getTime()) {
	    		return "";
	    	}
	    	if(typeof date == "string") {
	    		return date.match(/\d/g).join("");
	    	}
	    	var y = _date.getFullYear(),
	    		M = _date.getMonth() + 1,
	    		d = _date.getDate(),
	    		H = _date.getHours(),
	    		m = _date.getMinutes(),
	    		s = _date.getSeconds();
	    	M = M > 9 ? M : ("0" + M);
	    	d = d > 9 ? d : ("0" + d);
	    	H = H > 9 ? H : ("0" + H);
	    	m = m > 9 ? m : ("0" + m);
	    	s = s > 9 ? s : ("0" + s);
	    	return y + M + d + H + m + s;
    },
    /**
     * 获取当前年月日 *
     * */
    getYMDforNowNull(date,type){
    		var _date = new Date(date);
		if(!date || !_date.getTime()){return null;}
		var y = _date.getFullYear(),
			M = _date.getMonth()+1,
			d = _date.getDate(),
			H = _date.getHours(),
			m = _date.getMinutes(),
			s = _date.getSeconds();
			M = M > 9 ? M : ("0"+M);
			d = d > 9 ? d : ("0"+d);
			H = H > 9 ? H : ("0"+H);
			m = m > 9 ? m : ("0"+m);
			s = s > 9 ? s : ("0"+s);
		return type?(H+""+m+""+s):(y+""+M+""+d);
    },
    /**
     * 获取当前年月日 *
     * */
    getYMDforNow(date,type){
    		var _date = new Date(date);
		if(!date || !_date.getTime()){return "";}
		var y = _date.getFullYear(),
			M = _date.getMonth()+1,
			d = _date.getDate(),
			H = _date.getHours(),
			m = _date.getMinutes(),
			s = _date.getSeconds();
			M = M > 9 ? M : ("0"+M);
			d = d > 9 ? d : ("0"+d);
			H = H > 9 ? H : ("0"+H);
			m = m > 9 ? m : ("0"+m);
			s = s > 9 ? s : ("0"+s);
		return type?(H+m+s):(y+M+d);
    },
    /**
     * 获取当前时分秒 *
     * */
    getHMSforNow(){
    		var date = new Date(),
    			h = date.getHours(),
    			m = date.getMinutes(),
    			s = date.getSeconds();
    		h = h > 9 ? h : ("0"+h);
    		m = m > 9 ? m : ("0"+m);
    		s = s > 9 ? s : ("0"+s);
    		return h+":"+m+":"+s;
    },
    /**
     * 13位日期数字转yyyy-MM-dd HH:mm:ss日期格式 *
     * */
    time2DateFormat(t){
    		var date = new Date(t||""),
    			y = date.getFullYear(),
    			M = date.getMonth()+1,
    			d = date.getDate(),
    			h = date.getHours(),
    			m = date.getMinutes(),
    			s = date.getSeconds();
    		M = M > 9 ? M : ("0"+M);
    		d = d > 9 ? d : ("0"+d);
    		h = h > 9 ? h : ("0"+h);
    		m = m > 9 ? m : ("0"+m);
    		s = s > 9 ? s : ("0"+s);
    		return y+"-"+M+"-"+d+" "+h+":"+m+":"+s;
    },
    /**
     * 将ajax参数集合中 空值的字段过滤掉 *
     * */
    filterParamByEmptyValue(param){
    		var obj = {};
        	for(var i in param){
        		if(param[i] || param[i]===false){
        			obj[i] = param[i];
        		}
        	}
        	return obj;
    },
    /**
     * 简化confirm组件 *
     * */
    confirm(title,handleSure,handleCancel){
    		this.$confirm(title, '提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			type: 'warning'
		}).then(() => {
			if(typeof handleSure =="function")
				handleSure();
		}).catch(() => {       
			if(typeof handleCancel =="function")
				handleCancel();
		});
	},
	//压缩图片
	compressImg(img, rate, fileType){
		var cvs = document.createElement('canvas');
		var width=img.width;
		var height = img.height;
		var dic=height/width;
		var width_cvs=width>750?750:width;
		cvs.width = width_cvs;  //图片压缩的标准，这里是按照定款600px计算
		cvs.height = width_cvs * dic;
		var cxt=cvs.getContext("2d");
		cxt.fillStyle="#FFF";
		cxt.fillRect(0,0,width_cvs,width_cvs*dic);   
		cxt.drawImage(img,0,0,width_cvs,width_cvs*dic);
		return cvs.toDataURL(fileType, rate);
	},
    batchHttp(){
        
    }
    
};
export default Common;
