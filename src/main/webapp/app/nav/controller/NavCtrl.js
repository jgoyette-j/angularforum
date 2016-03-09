/**
 * 
 */
forumApp.controller('NavCtrl', function(UserService, CategoryService, $state){
	
	var nav = this;
	
	nav.currentUser = UserService.getUser();
	
	console.log("Nav ctrl init");
	
	nav.logout = function(){
		var logoutPromise = UserService.logout();
		
		logoutPromise.then(function(success){
			nav.currentUser.authenticated = false;
			$state.go('home');
		},function(error){
			console.log('Error logging out');
		});
		
	};
	
});