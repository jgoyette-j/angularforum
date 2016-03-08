<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link
	href="${ pageContext.request.contextPath }/app/resources/bootstrap.css"
	rel="stylesheet">
</head>
<body ng-app="ForumApp">

	<nav class="navbar navbar-default" ng-controller="NavCtrl as nav">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Brand</a>
			</div>

			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li ui-sref-active="active" ng-cloak
						ng-show="!nav.currentUser.authenticated"><a ui-sref="login">Login</a></li>
					<li ui-sref-active="active" ng-cloak
						ng-show="!nav.currentUser.authenticated"><a
						ui-sref="register">Register</a></li>
					<li ui-sref-active="active" ng-cloak
						ng-show="nav.currentUser.authenticated"><a ui-sref="forum">Forums</a></li>
					<li ng-cloak ng-show="nav.currentUser.authenticated"><a
						href="#" ng-click="nav.logout()">Logout</a></li>
				</ul>
				<!-- 				<ul class="nav navbar-nav navbar-right"> -->
				<!-- 					<li><a href="#">Link</a></li> -->
				<!-- 				</ul> -->
			</div>
		</div>
	</nav>

	<div class="container-fluid" ui-view autoscroll="true"></div>
	<!-- Angular -->
	<script
		src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.js"></script>
	<!-- UI-Router -->
	<script
		src="//angular-ui.github.io/ui-router/release/angular-ui-router.js"></script>
	<script type="text/javascript"
		src="${ pageContext.request.contextPath }/app/app.js"></script>
	<script type="text/javascript"
		src="${ pageContext.request.contextPath }/app/user/service/UserService.js"></script>
	<script
		src="${ pageContext.request.contextPath }/app/forum/service/ForumService.js"></script>
	<script
		src="${ pageContext.request.contextPath }/app/post/service/PostService.js"></script>
	<script type="text/javascript"
		src="${ pageContext.request.contextPath }/app/category/service/CategoryService.js"></script>
	<script type="text/javascript"
		src="${ pageContext.request.contextPath }/app/user/controller/LoginCtrl.js"></script>
	<script type="text/javascript"
		src="${ pageContext.request.contextPath }/app/user/controller/RegisterCtrl.js"></script>
	<script type="text/javascript"
		src="${ pageContext.request.contextPath }/app/home/controller/HomeCtrl.js"></script>
	<script type="text/javascript"
		src="${ pageContext.request.contextPath }/app/forum/controller/ForumCtrl.js"></script>
	<script type="text/javascript"
		src="${ pageContext.request.contextPath }/app/nav/controller/NavCtrl.js"></script>
	<script type="text/javascript"
		src="${ pageContext.request.contextPath }/app/forum/controller/ForumDetailCtrl.js"></script>
	<script type="text/javascript"
		src="${ pageContext.request.contextPath }/app/category/controller/CategoryCtrl.js"></script>

</body>
</html>