'use strict';

angular.module('xmlClientApp')
	.controller('VoteCrtl', ['$scope', '$uibModal', '$log', '_', '$routeParams', '$location', 'VoteResource',
	   function($scope, $uibModal, $log, _, $routeParams, $location, VoteResource) {

		var id = $routeParams.id;
		var tip = $routeParams.tip;
		var res = id.split(".");
		var name = $routeParams.name;

		$scope.name = name;
		$scope.id = id;

		var user = [];
		var vote = [];

		VoteResource.getUser().then(function(items) {
			user = items.length;
			$scope.user = user;
			VoteResource.getVote(res[0], tip).then(function(tems) {

				vote = tems.length;
				$scope.vote = vote;
				$scope.tip = tip;
				$scope.procenat = (vote/user)*100;
				$scope.izglasavanje = 100-$scope.procenat;

				if((user/2) >= vote) {
					$scope.glasanje = false;
				} else {
					$scope.glasanje = true;
				}
			})
		})

		$scope.deleteAct = function() {
			VoteResource.deteAct(res[0]);
			$location.path('/acts/proposed');
		}

		$scope.acceptAct = function() {
			VoteResource.acceptAct(res[0]);
			$scope.message = true;
		}

		$scope.acceptAmandman = function() {
			VoteResource.acceptAmandman(res[0]).then(function(odg) {
				$scope.messages = true;
			})

		}

		$scope.deleteAmandman = function() {
			VoteResource.deleteAmandman(res[0]);
			$scope.messages = true;
		}


	}])
