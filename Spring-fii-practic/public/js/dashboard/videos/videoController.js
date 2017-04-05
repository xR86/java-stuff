app.controller('videoCtrl', ['$scope', '$http', 'Videos', function($scope,  $http, Videos) {
	$scope.videosList = [{title:'No video here'}];
	

	//Read
	$scope.videosRetrieve = function() {
		

		var data;
		var promise = Videos.videoRetrieve().then(function(dat){

			console.log(dat);
			data = dat.data;
			console.log(dat.data);

			if(data){
				$scope.videosList = angular.fromJson(data);

				// angular.forEach($scope.msgMongoList, function(value, key) {
				// 	//console.log(key + ': ' + value);
				// 	var i = 0;

				// 	var id, content, timestamp;
				// 	$scope.msgList.push(value);

				// 	$scope.loading = false;
				// 	//console.dir(value);
				// 	var container = document.getElementById("msgContainer");
				// 	container.scrollTop = container.scrollHeight;
				// });
			}
		});

	};
	$scope.videosRetrieve(); //should run on window.onload

	
}]);

