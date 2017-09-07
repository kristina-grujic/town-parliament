'use strict';

/**
 * @ngdoc overview
 * @name studentsClientApp
 * @description
 * # studentsClientApp
 *
 * Main module of the application.
 */

angular
    .module('xmlClientApp', [
        'ngResource',
        'ngRoute',
        'ngCookies',
        'ngStorage',
        'restangular',
        'ui.bootstrap',
        'lodash'
    ])
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider
	        .when('/', {
	            templateUrl: 'views/home.html'
	        })
	        .when('/login', {
	            templateUrl: 'views/login.html',
	            controller: 'LoginCtrl',
	            controllerAs: 'registrovan'
	        })
	        .when('/acts/:accept', {
	            templateUrl: 'views/list.html',
	            controller: 'ActCrtl',
	            controllerAs: 'acceptAct'
	        })
	        .when('/add/new/act', {
	            templateUrl: 'views/add.html',
	            controller: 'AddActCtrl',
	            controllerAs: 'addAct'
	        })
	        .when('/acts/read/proposed/:id', {
	            templateUrl: 'views/readAct.html',
	            controller: 'ReadCrtl',
	            controllerAs: 'readCtrl'
	        })
	        .when('/acts/read/accepted/:id', {
	            templateUrl: 'views/readAcceptAct.html',
	            controller: 'ReadCrtl',
	            controllerAs: 'readCtrl'
	        })
	        .when('/vote/:tip/:name/:id', {
	            templateUrl: 'views/vote.html',
	            controller: 'VoteCrtl',
	            controllerAs: 'readCtrl'
	        })
	        .when('/add/new/minister', {
	            templateUrl: 'views/addAlderman.html',
	            controller: 'UserAddCtrl',
	            controllerAs: 'userAddCtrl'
	        })

	        .otherwise({
                redirectTo: '/'
            });

    }])

    .run(['Restangular', '$log', '$rootScope', '$http', '$location', '$localStorage', 'LoginResources', function(Restangular, $log, $rootScope, $http, $location, $localStorage, LoginResources) {
        Restangular.setBaseUrl("api");
        Restangular.setErrorInterceptor(function(response) {
            if (response.status === 500) {
                $log.info("internal server error");
                return true; // greska je obradjena
            }
            return true; // greska nije obradjena
        });
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = $localStorage.currentUser.token;
        }
        $rootScope.logout = function () {
        	LoginResources.logout();
        }
        $rootScope.getCurrentUserRole = function () {
            if (!LoginResources.getCurrentUser()){
              return undefined;
            }
            else{

              return LoginResources.getCurrentUser().rola;
            }
        }
        $rootScope.getCurrentUserUser = function () {
            if (!LoginResources.getCurrentUser()){
              return undefined;
            }
            else{
              return LoginResources.getCurrentUser().username;
            }
        }
        $rootScope.isLoggedIn = function () {
            if (LoginResources.getCurrentUser()){
              return true;
            }
            else{
              return false;
            }
        }


    }]);
