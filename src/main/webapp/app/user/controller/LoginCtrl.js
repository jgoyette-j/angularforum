forumApp.controller('LoginCtrl', function(UserService, $state, $rootScope){
	
	var loginCtrl = this;
	
	loginCtrl.curUser = UserService.getUser();
	
	loginCtrl.message = UserService.getMessage();
	
	if($rootScope.from != "registration" || $rootScope.from != "secure"){
		UserService.setMessage(null,"login");
	}
	
	console.log("Login init");
	
	loginCtrl.doLogin = function(isValid){
		
		var promise = UserService.authenticateUser(loginCtrl.curUser);
		
		var detailPromise;
		
		promise.then(
				function(data){
					detailPromise = UserService.getUserDetails(data.name);
					detailPromise.then(function(data){
						data.authenticated = true;
						UserService.setUser(data);
						$state.go('forum');
						/*$state.go('category');*/
					},function(error){
						console.log(error);
					});
				},function(error){
					console.log(error);
					UserService.setMessage(error.data.message, "login");
					UserService.setMessage(null,"registration");
					UserService.setMessage(null,"secure");
				}
		);
		
		
		
	};
	
});