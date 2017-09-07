angular.module('xmlClientApp')
	.factory('VoteResource', ['Restangular', '_', function(Restangular, _) {
		'use stict';

		var retVal = {};

		var user = [];
		var vote = [];
		var message = [];

		retVal.getUser = function() {
			var link = 'user/';
			return Restangular.all(link).getList().then(function(entries) {
				user = entries;
				return user;
			});
		}

		retVal.getVote = function(id, tip) {
			var link = link = 'act/adopt/proposal/'+id;


			return Restangular.all(link).getList().then(function(entries) {
				vote = entries;
				return vote;
			});

		}

		retVal.acceptAct = function(id) {
			var link = 'act/accept/'+id;
			return Restangular.one(link).get();

		}

		retVal.deteAct = function(id) {
			var link = 'act/delete/'+id;
			return Restangular.all(link).remove();
		}

		retVal.acceptAmandman = function(id) {
			var link = 'amandman/accept/'+id;
			return Restangular.one(link).get().then(function(resoult) {
				message = resoult;
				return message;
			})

		}

		retVal.deleteAmandman = function(id) {
			var link = 'amandman/delete/'+id;
			return Restangular.all(link).remove();
		}

		return retVal;


	}]);
