app.directive('video', function() { 
  return { 
    restrict: 'E',
    replace: true,
    templateUrl: 'js/dashboard/videos/videoPartial.html' 
  }; 
});