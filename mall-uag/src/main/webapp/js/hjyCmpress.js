// 压缩 ，src base64 rate  fileType 
function compress(src, rate, fileType) {
	var img = new Image();
	img.src = src;
	// naturalWidth真实图片的宽度
	var cvs = document.createElement('canvas');
	var width=img.width;
	var height = img.height;
	var dic=height/width;
	var width_cvs=width>600?600:width;
	cvs.width = width_cvs;  //图片压缩的标准，这里是按照定款600px计算
	cvs.height = width_cvs * dic;
	var cxt=cvs.getContext("2d");
	cxt.fillStyle="#FFF";
    cxt.fillRect(0,0,width_cvs,width_cvs*dic);   
	cxt.drawImage(img,0,0,width_cvs,width_cvs*dic);
	newImageData = cvs.toDataURL(fileType, rate);
	return newImageData;
}

//压缩 ，src base64 rate  fileType 
function compressImg(img, rate, fileType) {
	var cvs = document.createElement('canvas');
	var width=img.width;
	var height = img.height;
	var dic=height/width;
	var width_cvs=width>600?600:width;
	cvs.width = width_cvs;  //图片压缩的标准，这里是按照定款600px计算
	cvs.height = width_cvs * dic;
	var cxt=cvs.getContext("2d");
	cxt.fillStyle="#FFF";
    cxt.fillRect(0,0,width_cvs,width_cvs*dic);   
	cxt.drawImage(img,0,0,width_cvs,width_cvs*dic);
	newImageData = cvs.toDataURL(fileType, rate);
	return newImageData;
}


function compressAppIndex(img, rate, fileType) {
	// naturalWidth真实图片的宽度
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
	newImageData = cvs.toDataURL(fileType, rate);
	return newImageData;
}
