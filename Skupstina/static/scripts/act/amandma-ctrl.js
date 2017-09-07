'use strict';

angular.module('xmlClientApp')
	.controller('AddAmandmanCtrl', ['$scope', '$uibModal', '$log', '_', '$routeParams', '$window', '$location', 'ActResource',
	   function($scope, $uibModal, $log, _, $routeParams, $window, $location, ActResource) {

		var id = $routeParams.id;
		var nesto = [];

		ActResource.getAct(id).then(function(items) {
			$scope.dokument = items;

			$scope.amandman = {
					naslovAkta: items.naslov,
					idAkta: items.id,
					linkAkta: id,
					korisnik: items.korisnik,
					odobreno: false,
					dopunaIzmena: false,
					sluzbeniListAmandmana: {
						brojListaAkta: items.sluzbeniList.broj_lista,
						idAkta: '',
						mestoDatum: {
							mesto: items.sluzbeniList.mestoDatum.mesto,
							datum: items.sluzbeniList.mestoDatum.datum
						}
					},
					dopunaZakonaAmandmana: {
						dopunaIzmena: false,
						glava: [{
							id: 1,
							podaciGlave: {
								naslovGlave: '',
								broj_glave: '1'
							},
							podnaslovGlave: '',
							clan: [{
								id: 1,
								podaciClana: {
									naslovClana: '',
									brojClana: '1'
								},
								opis: ''
							}]
						}]
					}
				}


				$scope.addNewGlava = function(glId) {

					//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].length+1;

					$scope.amandman.dopunaZakonaAmandmana.glava.push({'id':glId+1, podnaslovGlave:'',
						podaciGlave:{broj_glave:'1', naslovClana:''} ,clan:[{id:1,
							podaciClana:{brojClana:'1', naslovClana:''}, opis:''}]});

				};

				$scope.addNewClan = function(glId, clId) {
					$scope.amandman.dopunaZakonaAmandmana.glava[glId-1].clan.push({'id':clId+1, podaciClana:{brojClana:'1', naslovClana:''}, opis:''});
				};

				$scope.addAmandman = function () {

					ActResource.saveAmandman($scope.amandman) .then(function (result) {
						if(result.message){
							$location.path('/amandman/proposed');
						} else if(success == 400) {
						}
					})
				}

	    })
	}])

	.controller('AmandmanCrtl', ['$scope', '$uibModal', '$log', '_', '$routeParams', '$window', 'ActResource', 'SearchResource',
	   function($scope, $uibModal, $log, _, $routeParams, $window, ActResource, SearchResource) {

		$scope.list = [];
		var id = $routeParams.accept;
		var tip = 'amandman';

		ActResource.getUsvojeni(id, tip).then(function(items) {
			$scope.name = id;
			$scope.tip = tip;
			$scope.list = items;
	    })


	    $scope.converte = function(id, tip) {


			var res = id.split(".");
			if(tip == 'HTML') {

				var fileName = id+".html";
	            var a = document.createElement("a");
	            document.body.appendChild(a);
	            a.style = "display: none";
	            ActResource.converteAmandman(res[0], tip).then(function (result) {
	                var file = new Blob([result], {type: 'application/html'});
	                var fileURL = window.URL.createObjectURL(file);
	                a.href = fileURL;
	                a.download = fileName;
	                a.click();
	            })
			} else if(tip == 'PDF') {
				window.open('http://localhost:8080/api/amandman/download/pdf/'+res[0]);
			} else {
			}
		}


		$scope.liste = "";
		$scope.nasao = [];
		$scope.search = function() {
			$scope.liste = $scope.keywords;

			if($scope.lista != ""){
				SearchResource.search($scope.keywords, "amandman", id).then(function (result) {
					$scope.nasao = result;
				});
			}
		}

	}])
	.controller('ReadAmandanCrtl', ['$scope', '$uibModal', '$log', '_', '$routeParams', '$window', '$location', 'ActResource',
	   function($scope, $uibModal, $log, _, $routeParams, $window, $location, ActResource) {

		$scope.list = [];
		var id = $routeParams.id;
		var res = id.split(".");


		ActResource.getAmandman(res[0]).then(function(items) {
			$scope.list = items;
			$scope.xml = res[0];
			$scope.name = items.naslovAkta;
	    })

			$scope.statistikaAct = function () {
				// href="#/vote/amandman/{{l.docTitle}}/{{l.docUrl}}"
				$location.path('/vote/amandman/' + $scope.name + '/' + id);
			}

	  $scope.acceptAmandman = function () {
			ActResource.getVote(res[0], 'accept', 'amandman').then(function(items) {
				if(items.error != null) {

					$scope.error = items.error;
				} else if(items.message != null) {
					$scope.vote = items.vote;
					$scope.message = items.message;
				}
			})

		}

		$scope.declineAmandman = function () {
			ActResource.getVote(res[0], 'accept', 'amandman').then(function(items) {
				if(items.error != null) {

					$scope.error = items.error;
				} else if(items.message != null) {
					$scope.message1 = items.message;
				}
			})
		}

		$scope.deleteAmandman = function() {

			ActResource.getDelteAmandman(res[0]).then(function(items) {
				$scope.delete = true;
				window.location = '#/amandman/proposed';
		    });
		}

	}])
