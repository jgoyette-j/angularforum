forumApp.service('CategoryService', function($http,$q){
	
	var categoryService = this;
	
	categoryService.categories = {
			list:null
	};
	
	/*
	 * Added current category to keep track of that.
	 */
	categoryService.currentCategory = {
			category:null
	};
	
	categoryService.getCurrentCategory = function() {
		return categoryService.currentCategory;
	};
	
	categoryService.setCurrentCategory = function(data){
		categoryService.currentCategory.category = data;
	};
	
	categoryService.getCategories = function(){
		return categoryService.categories;
	};
	
	categoryService.setCategories = function(data){
		categoryService.categories.list = data;
	};
	
	categoryService.retrieveCategories = function(){
		var promise = $http.get('category/view').then(
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
	
	categoryService.retrievePaginatedCategories = function(page,size){
		var promise = $http.get('category/viewPaginated',{params:{page:page,size:size}}).then(
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
	
	categoryService.retrieveCategoryCount = function(){
		var promise = $http.get('category/count')
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
	
});