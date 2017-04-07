app.controller('videoCtrl', ['$scope', '$http', 'Videos', function($scope,  $http, Videos) {
	$scope.videosList = [{title:'No video here'}];
	

	//Read
	$scope.videosRetrieve = function() {
		var promise = Videos.videoRetrieve().then(function(dat){

			if(dat.data){
				$scope.videosList = angular.fromJson(dat.data);
			}
		});

	};
	$scope.videosRetrieve(); //should run on window.onload

}]);
