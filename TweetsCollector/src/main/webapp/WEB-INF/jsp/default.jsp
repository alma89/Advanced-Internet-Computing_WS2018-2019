<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>
<title>${TITLE} v${VERSION}</title>
<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/css/main.css" />

</head>
<body>

	<div class="container">
		<form action="/submit" method="post">
			<img class="twitter" src="/img/twittericon.png">
			<br/>

			<h4>Please enter keywords. Separate each other with ","</h4>
			<input id="keywords" name="keywords" type="text" placeholder="e.g. Bitcoin">
			<input id="btnSubmit" name="btnSubmit" class="btn btn-sm btn-primary" type="submit" value="Submit">

		</form>
		<h5 class="desc1">TUWien - AIC - G1T4</h5>
		<hr>
		<h5><strong>${TITLE}</strong> ver ${VERSION}</h5>
	</div>

	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>
