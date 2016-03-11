/**
 * 
 */
forumApp.controller('NavCtrl', function(UserService, CategoryService, $state){
	
	var nav = this;
	
	nav.currentUser = UserService.getUser();
	
	console.log("Nav ctrl init");
	
	/*
	 *  Made call to get current category.
	 *  This should fix being able to go straight to forums by button without it.
	 *  I need to fix doing it by url.
	 */ 
	nav.currentCategory = CategoryService.getCurrentCategory();
	
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