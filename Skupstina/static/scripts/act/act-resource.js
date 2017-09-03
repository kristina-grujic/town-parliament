/**
 *
 */

angular.module('xmlClientApp')
	.factory('ActResource', ['Restangular', '_', function(Restangular, _) {
		'use stict';


		var retVal = {};

		var message = [];
		var lista = [];
		var blob = [];

		var akt = [];

		retVal.getUsvojeni = function(id, tip) {

			if(tip == 'acts') {
				var link = 'act/collection/'+id;
				return Restangular.all(link).getList().then(function(entries) {
					lista = entries;
					return lista;
				});
			} else {
				var link = 'amandman/collection/'+id;
				return Restangular.all(link).getList().then(function(entries) {
					lista = entries;
					return lista;
				});
			}

		};

		retVal.getAct = function(id) {
			var link = 'act/find/'+id;
			return Restangular.one(link).get().then(function(entries) {
				akt = entries;
				return akt;
			});
		};

		retVal.getAmandman = function(id) {
			var link = 'amandman/find/'+id;
			return Restangular.one(link).get().then(function(entries) {
				akt = entries;
				return akt;
			});
		};

		retVal.getVote = function(id, vote, act) {
			var link = 'voting/';
			var glasanje = {};
			if(vote == 'accept') {
				glasanje = {
						tip: act,
						name: id,
						yn: true
				};
			}
			if(vote == 'deccident') {
				glasanje = {
						tip: act,
						name: id,
						yn: false
				};
			}

			return Restangular.all(link).post(glasanje).then(function(success) {
				message = success;
				return message;
			})
		};


		retVal.saveAmandman = function(amandman) {
			var link = 'amandman/add';

			return Restangular.all(link).post(amandman).then(function(success) {
				message = success;
				return message;

			})
		};

		retVal.converte = function(id, tip) {
			var link = 'act/convert/'+id+'/'+tip;
			return Restangular.one(link).get().then(function(success) {
				blob = success;
				return blob;
			})
		};

		retVal.converteAmandman = function(id, tip) {
			var link = 'amandman/convert/'+id+'/'+tip;
			return Restangular.one(link).get().then(function(success) {
				blob = success;
				return blob;
			})
		};

		retVal.getDelte = function(id) {
			var link = 'act/delete/'+id;

			return Restangular.all(link).remove().then(function(success) {
				message = success;
				return message;

			})
		};

		retVal.getDelteAmandman = function(id) {
			var link = 'amandman/delete/'+id;

			return Restangular.all(link).remove().then(function(success) {
				message = success;
				return message;

			})
		};

		retVal.rdfConvert = function(id) {
			var link = 'act/download/rdf/'+id;
			return Restangular.one(link).get().then(function(success) {
				blob = success;
				return blob;
			})
		};


		return retVal;

	}]);
