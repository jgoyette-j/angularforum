forumApp.service('UserService', function($http, $q) {

	var userService = this;

	userService.curUser = {

		id : 0,
		username : '',
		password : '',
		authenticated : false,
		email : ''

	};

	userService.recentUsers = {
		list : []
	};

	userService.message = {
		text : null
	};

	userService.getRecentUsers = function() {
		return userService.recentUsers;
	};

	userService.setRecentUsers = function(users) {
		userService.recentUsers.list = users;
	};

	userService.getMessage = function() {
		return userService.message;
	};

	userService.setMessage = function(text, component) {
		// console.log("component " + component);
		userService.message[component] = {
			text : text
		};
	};

	userService.setUser = function(user) {
		// Bad
		// userService.curUser = user;
		// Good
		userService.curUser.id = user.id;
		userService.curUser.username = user.username;
		userService.curUser.password = user.password;
		userService.curUser.email = user.email;
		userService.curUser.authenticated = user.authenticated;

	};

	userService.getUser = function() {
		return userService.curUser;
	};

	userService.isAuthenticated = function() {
		return userService.curUser.authenticated;
	};
	
	userService.logout = function(){
		
		var promise = $http.post('logout').then(
			function(success){
				console.log('successful logout');
				return success;
			},function(error){
				console.log('logout error');
				return error;
			}	
		);
		
		return promise;
	};

	userService.authenticateUser = function(user) {
		// console.log('auth');

		// console.log(user);

		var headers = user ? {
			authorization : "Basic "
					+ btoa(user.username + ":" + user.password)
		} : {};

		var promise = $http.get('user/login', {
			headers : headers
		}).then(function(success) {
			// console.log('success');
			// console.log(success.data);
			return success.data;
		}, function(error) {
			console.log('error');
			return $q.reject(error);
		});
		return promise;
	};

	userService.registerUser = function(user) {
		// console.log('register');
		// console.log(user);
		var promise = $http.post('user/register', user).then(
				function(response) {
					// console.log('Regi Success');
					return response;
				}, function(response) {
					console.log('Regi Failure');
					console.log(response);
					return $q.reject(response);
				});
		return promise;
	};

	userService.getUserDetails = function(username) {
		var promise = $http.get('user/details', {
			params : {
				username : username
			}
		}).then(function(success) {
			// console.log('success');
			// console.log(success.data);
			return success.data;
		}, function(error) {
			console.log('error');
			return $q.reject(error);
		});
		return promise;
	};

	userService.findTop5Users = function() {
		var promise = $http.get('user/top').then(function(success) {
			return success.data;
		}, function(error) {
			return $q.reject(error);
		});
		return promise;
	};

});