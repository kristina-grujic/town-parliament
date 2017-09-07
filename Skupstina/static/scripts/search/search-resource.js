/**
 * 
 */

angular.module('xmlClientApp')
	.factory('SearchResource', ['Restangular', '_', function(Restangular, _) {
		'use stict';
		
		
		var retVal = {};
		
		var lista = [];
		
		retVal.search = function(text, act, type) {
			var link = "search/"+act+"/"+type+"/"+text;
			var test = {};
			return Restangular.all(link).post(test).then(function(success) {
				lista = success;
				return lista;
			})
			
		};
		
		
		return retVal;
		
	}]);