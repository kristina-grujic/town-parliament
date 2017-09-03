
'use strict';

angular.module('xmlClientApp')
	.controller('UserAddCtrl', ['$scope', '$localStorage', '$http', '$window', '$log', '_', '$rootScope', '$location', 'LoginResources',
	    function($scope, $localStorage, $http, $window, $log, _, $rootScope, $location, LoginResources) {
			
		$scope.user = {};
		
		$scope.registration = function() {
			LoginResources.registration($scope.user, callback);
		};
		
		function callback(success) {
			if(success == 'invalid'){
				$scope.message = "invalid";
			} else {
				$scope.message = "success";
				$scope.user = {};
				$location.path('/');
			}
			
		}
		
		$scope.logout = function () {
			LoginResources.logout();
		}
		
	}])
		
		
