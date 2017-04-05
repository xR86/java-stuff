app.service('Videos', ['$http', function($http) {

	var serviceObject = {

		videoRetrieve : function(){
			return $http.get('/videos').
				success(function(data) {
					console.log("get successfully");
					//console.log(angular.fromJson(data));

				}).error(function(data) {
					console.error("error in get");
				});
		}

	};

	return serviceObject;
  
}]);
