
var app = angular.module('spring-fii-practic', ['ui.router']);

app.config(function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/');
    
    $stateProvider
        .state('home', {
            url: '/',
            templateUrl: 'js/root/homePartial.html'
        })

        .state('dashboard', {
            url: '/dashboard',
            templateUrl: 'js/dashboard/dashboardPartial.html'
        })
        
});

