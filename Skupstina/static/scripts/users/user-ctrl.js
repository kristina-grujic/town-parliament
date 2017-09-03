
'use strict';

angular.module('xmlClientApp')
	.controller('LoginCtrl', ['$scope', '$localStorage', '$http', '$window', '$log', '_', '$rootScope', 'LoginResources',
	    function($scope, $localStorage, $http, $window, $log, _, $rootScope, LoginResources) {
			
		$scope.user = {};
		
		$scope.login = function() {
			LoginResources.login($scope.user, callback);
		};
		
		function callback(success) {
			if(success == 'invalid'){
				$scope.message = "invalid";
			} else {
				window.location = '#/';
			}
			
		}
		
		$scope.logout = function () {
			LoginResources.logout();
		}
		
	}])