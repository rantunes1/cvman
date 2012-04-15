<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="icon" href="${pageContext.request.contextPath}/theme/brisa.ico">

<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/theme/jqui-redmond/jquery-ui-1.8.16.custom.css">
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/theme/jqgrid/ui.jqgrid-4.3.1.css">
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/theme/application.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jqgrid-4.3.1/jquery.tablednd-0.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jqgrid-4.3.1/grid.locale-pt.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jqgrid-4.3.1/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/json2.js"></script>


<script type="text/javascript">
	var rootPath = '${pageContext.request.contextPath}/';

	$(document).ready(function() {
		try{
			$('<div id="preloader" style="display:none; position:fixed; top:3px; right:3px"><img src="${pageContext.request.contextPath}/theme/images/preloader.png"></img></div>')	
				.ajaxSend(function() { $(this).show(); })
				.ajaxComplete(function() { $(this).hide(); })
				.appendTo('body');
			}catch(ignored){}
	});
</script>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/application.js"></script>

