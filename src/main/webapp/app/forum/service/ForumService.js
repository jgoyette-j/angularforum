forumApp.service('ForumService', function($http, $q) {

	var forumService = this;

	forumService.forum = {
		id : 0,
		author : '',
		title : '',
		created : '',
		category : {},
		posts : [ {
			id : 0,
			author : '',
			text : '',
			created : ''
		} ]
	};
	
	forumService.forums = {
			list:[]
	};
	
	forumService.recentForums = {
			list:[]
	};

	forumService.getForum = function() {
		return forumService.forum;
	};
	forumService.setForum = function(data) {
		forumService.forum = data;
	};
	forumService.getForums = function() {
		return forumService.forums;
	};
	forumService.setForums = function(data) {
		forumService.forums.list = data;
	};
	
	forumService.getRecentForums = function(){
		return forumService.recentForums;
	};
	
	forumService.setRecentForums = function(recentForums){
		forumService.recentForums.list = recentForums;
	};
	
	forumService.findTop5Forums = function(){
		var promise = $http.get('forum/top').then(
				function(success){
					return success.data;
				},function(error){
					return $q.reject(error);
				}
		);
		return promise;
	};
	
	forumService.retrieveForums = function(){
		var promise = $http.get('forum/view').then(
				function(success){
					//console.log('forum retrieval success');
					return success.data;
				},
				function(error){
					console.log('forum retrieval error');
					return $q.reject(error);
		});
		return promise;
	};
	
	// Old way that returns ALL paginated forums regardless of their category.
	forumService.retrievePaginatedForums = function(page,size){
		var promise = $http.get('forum/viewPaginated',{params:{page:page,size:size}}).then(
				function(success){
					//console.log('forum retrieval success');
					//console.log(success.data.content);
					return success.data.content;
				},
				function(error){
					console.log('forum retrieval error');
					return $q.reject(error);
		});
		return promise;
	};
	
	// Old way the returns a simple count of EVERY forum.
	forumService.retrieveForumCount = function(){
		var promise = $http.get('forum/count')
				.then(
						function(success){
							return success.data;
						},function(error){
							console.log("Error retrieving count.");
							return $q.reject(error);
						}
				);
		return promise;
	}
	
	// New way that returns paginated forums by category.
	forumService.retrievePaginatedForumsByCategory = function(categoryId,page,size){
		var promise = $http.get('forum/viewPaginatedForumsByCategory',{params:{categoryId:categoryId,page:page,size:size}}).then(
				function(success){
					//console.log('forum retrieval success');
					//console.log(success.data.content);
					return success.data.content;
				},
				function(error){
					console.log('forum retrieval error');
					return $q.reject(error);
		});
		return promise;
	};
	
	// New way the returns a count of forum by category.
	forumService.retrieveForumCountByCategory = function(categoryId){
		var promise = $http.get('forum/countByCategoryId',{params:{categoryId:categoryId}})
				.then(
						function(success){
							return success.data;
						},function(error){
							console.log("Error retrieving count.");
							return $q.reject(error);
						}
				);
		return promise;
	};
	
	forumService.addForum = function(data) {
		//console.log(data);
		var promise = $http.post('forum/add', data).then(
				function(success){
					return success.data;
				},
				function(error){
					return $q.reject(error);
				}
		);
		return promise;
		//forumService.forums.push(data);
	};
	
	forumService.addPostToForum = function(data){
		//console.log("Adding post of: " + data);
		var promise = $http.post('post/add', data).then(
				function(success){
					return success.data;
				},
				function(error){
					return $q.reject(error);
				}
		);
		return promise;
	};

});