forumApp.controller('ForumCtrl',
		function(UserService, ForumService, $state, $stateParams, $timeout, $scope) {

			var forumCtrl = this;
			
			forumCtrl.forums = ForumService.getForums();

			forumCtrl.forum = ForumService.getForum();
			forumCtrl.curUser = UserService.getUser();

			// console.log(forumCtrl.curUser);

			forumCtrl.message = "";

			forumCtrl.count = 0;

			forumCtrl.pages = [];

			forumCtrl.currentPage = 0;

			forumCtrl.pageSize = 5;
			
			forumCtrl.curCategoryId = $stateParams.categoryId;
			
			/*
			 * Promise to get the count of total Forum objects
			 */
			var countPromise = ForumService.retrieveForumCountByCategory(forumCtrl.curCategoryId);

			countPromise.then(function(success) {
				forumCtrl.count = success.count;
				forumCtrl.pageCount();
				console.log('Page count: ' + forumCtrl.pages);
			}, function(error) {
				console.log('count retrieval error');
			});
			
			forumCtrl.nextPage = function(){
				forumCtrl.currentPage++;
				forumCtrl.retrieveForumsByPage(forumCtrl.currentPage);
			};
			
			forumCtrl.prevPage = function(){
				forumCtrl.currentPage--;
				forumCtrl.retrieveForumsByPage(forumCtrl.currentPage);
			};

			/*
			 * Count function to create an array to loop over for pagination
			 * buttons.
			 */
			forumCtrl.pageCount = function() {
				var numberOfPages = 0;
				if (forumCtrl.count % forumCtrl.pageSize === 0) {
					numberOfPages = forumCtrl.count / forumCtrl.pageSize;
				} else {
					numberOfPages = Math.floor(forumCtrl.count
							/ forumCtrl.pageSize) + 1;
				}
				forumCtrl.pages = [];
				for (var i = 0; i < numberOfPages; i++) {
					forumCtrl.pages.push(i);
				}

			}

			forumCtrl.retrieveForums = function() {
//				var promise = ForumService.retrievePaginatedForums(forumCtrl.currentPage, forumCtrl.pageSize);
				var promise = ForumService.retrievePaginatedForumsByCategory(forumCtrl.curCategoryId, forumCtrl.currentPage,forumCtrl.pageSize);

				promise.then(function(success) {
					ForumService.setForums(success);
					console.log(success);
				}, function(error) {
					console.log('Forum retrieval error');
				});
			};

			forumCtrl.retrieveForums();

			forumCtrl.retrieveForumsByPage = function(page) {
				forumCtrl.currentPage = page;
				var promise = ForumService.retrievePaginatedForumsByCategory(forumCtrl.curCategoryId, page, forumCtrl.pageSize);
				//var promise = ForumService.retrievePaginatedForums(page, forumCtrl.pageSize);

				promise.then(function(success) {
					ForumService.setForums(success);
				}, function(error) {
					console.log('Forum retrieval error');
				});
			};

			/*
			 * Add a new forum to the database. After getting the forum,
			 * pass it to the ForumService for submission.
			 */
			forumCtrl.doAdd = function() {

				forumCtrl.forum.author = forumCtrl.curUser.username;
				forumCtrl.forum.posts[0].author = forumCtrl.curUser.username;
				/*
				 * Add current category ID to the forum for submission.
				 */
				forumCtrl.forum.category.id = forumCtrl.curCategoryId;
				
				var addPromise = ForumService.addForum(forumCtrl.forum);

				addPromise.then(function(success) {
					// console.log(success);
					forumCtrl.message = "Successfully added new forum!";
					ForumService.getForums().list.unshift(success);
				}, function(error) {
					console.log(error);
					forumCtrl.message = "There was an issue adding the forum.";
				});

				forumCtrl.forum = {
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
				;
			};

			console.log("ForumCtrl init");
			
			/*
			 * Fixed duplicate poller based on multiple controllers being created.
			 */
			$scope.$on("$destroy", function() {
				console.log("Destroyering.....")
				
		        if (timer) {
		        	console.log("cancelling");
		            $timeout.cancel(timer);
		        }
		    });

			/*
			 * Commented out poller until refreshing model issue is fixed.
			 */
			var poll = function() {
//				$timeout(function() {
					// service call to update forums
					var promise = ForumService.retrievePaginatedForumsByCategory(forumCtrl.curCategoryId,
							forumCtrl.currentPage, forumCtrl.pageSize);

					promise.then(function(success) {

						var pollCountPromise = ForumService
								.retrieveForumCountByCategory(forumCtrl.curCategoryId);

						pollCountPromise.then(function(success) {
							forumCtrl.count = success.count;
							forumCtrl.pageCount();
						}, function(error) {
							console.log('count retrieval error');
						});

						ForumService.setForums(success);
					}, function(error) {
						console.log('Forum retrieval error in poller');
					})
					timer = $timeout(poll, 1000)
//				}, 1000);
			};
			var timer = $timeout(poll, 1000);
		});