var forumApp = angular.module('ForumApp', [ 'ui.router' ]);

forumApp.config(function($stateProvider, $urlRouterProvider) {
	
	$urlRouterProvider.otherwise('/');
	
	$stateProvider
	.state('login',{
		url: '/login',
		templateUrl : 'app/user/templates/login.html',
		controller: 'LoginCtrl as loginCtrl'
	})
	.state('register',{
		url: '/register',
		templateUrl : 'app/user/templates/register.html',
		controller: 'RegisterCtrl as registerCtrl'
	})
	.state('home',{
		url: '/',
		templateUrl : 'app/home/templates/home.html',
		controller: 'HomeCtrl as homeCtrl',
		resolve: {authenticate: authenticate}
	})
	.state('forum', {
		url: '/forum',
		templateUrl : 'app/forum/templates/forumsMain.html',
		controller: 'ForumCtrl as forumCtrl',
		resolve: {authenticate: authenticate}
	})
//	.state('forum.add', {
//		url: '/forumAdd',
//		templateUrl : 'app/forum/templates/forumAdd.html'
//	})
	.state('forum.detail',{
		url: '/detail/:forumId',
		templateUrl: 'app/forum/templates/forumDetail.html',
		controller: 'ForumDetailCtrl as forumDetailCtrl',
		resolve: {authenticate: authenticate}
	});
	
    function authenticate($q, UserService, $state, $timeout) {
        if (UserService.isAuthenticated()) {
          // Resolve the promise successfully
          return $q.when()
        } else {
          // The next bit of code is asynchronously tricky.

          $timeout(function() {
            // This code runs after the authentication promise has been rejected.
            // Go to the log-in page
        	  UserService.setMessage('Please login before accessing the application...')
            $state.go('login');
          })

          // Reject the authentication promise to prevent the state from loading
          return $q.reject()
        }
      }
	
});