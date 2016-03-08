forumApp.controller('CategoryCtrl', function($state, $stateParams,
		CategoryService, UserService) {

	var categoryCtrl = this;

	categoryCtrl.categories = CategoryService.getCategories();

	categoryCtrl.curUser = UserService.getUser();

	// console.log(categoryCtrl.curUser);

	categoryCtrl.message = "";

	categoryCtrl.count = 0;

	categoryCtrl.pages = [];

	categoryCtrl.currentPage = 0;

	categoryCtrl.pageSize = 5;

	/*
	 * Promise to get the count of total Forum objects
	 */
	var countPromise = CategoryService.retrieveCategoryCount();

	countPromise.then(function(success) {
		categoryCtrl.count = success.count;
		categoryCtrl.pageCount();
		console.log('Page count: ' + categoryCtrl.pages);
	}, function(error) {
		console.log('count retrieval error');
	});

	categoryCtrl.nextPage = function() {
		categoryCtrl.currentPage++;
		categoryCtrl.retrieveCategoriesByPage(categoryCtrl.currentPage);
	};

	categoryCtrl.prevPage = function() {
		categoryCtrl.currentPage--;
		categoryCtrl.retrieveCategoriesByPage(categoryCtrl.currentPage);
	};

	/*
	 * Count function to create an array to loop over for pagination buttons.
	 */
	categoryCtrl.pageCount = function() {
		var numberOfPages = 0;
		if (categoryCtrl.count % categoryCtrl.pageSize === 0) {
			numberOfPages = categoryCtrl.count / categoryCtrl.pageSize;
		} else {
			numberOfPages = Math.floor(categoryCtrl.count
					/ categoryCtrl.pageSize) + 1;
		}
		categoryCtrl.pages = [];
		for (var i = 0; i < numberOfPages; i++) {
			categoryCtrl.pages.push(i);
		}

	}

	categoryCtrl.retrieveCategories = function() {
		var promise = CategoryService.retrievePaginatedCategories(
				categoryCtrl.currentPage, categoryCtrl.pageSize);

		promise.then(function(success) {
			CategoryService.setCategories(success);
		}, function(error) {
			console.log('Category retrieval error');
		});
	};

	categoryCtrl.retrieveCategories();

	categoryCtrl.retrieveCategoriesByPage = function(page) {
		categoryCtrl.currentPage = page;
		var promise = CategoryService.retrievePaginatedCategories(page,
				categoryCtrl.pageSize);

		promise.then(function(success) {
			CategoryService.setCategories(success);
		}, function(error) {
			console.log('Category retrieval error');
		});
	};

});