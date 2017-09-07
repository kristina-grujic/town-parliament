'use strict';

angular.module('xmlClientApp')
	.controller('ActCrtl', ['$scope', '$uibModal', '$log', '_', '$routeParams', '$window', 'ActResource', 'SearchResource',
	   function($scope, $uibModal, $log, _, $routeParams, $window, ActResource, SearchResource) {

		$scope.list = [];
		var id = $routeParams.accept;
		var tip = 'acts';

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
	            ActResource.converte(res[0], tip).then(function (result) {
	                var file = new Blob([result], {type: 'application/html'});
	                var fileURL = window.URL.createObjectURL(file);
	                a.href = fileURL;
	                a.download = fileName;
	                a.click();
	            });
			} else if(tip == 'PDF') {

				window.open('http://localhost:8080/api/act/download/pdf/'+res[0]);

			} else if(tip == 'RDF') {

				window.open('http://localhost:8080/api/act/download/rdf/'+res[0]);
				//
				// window.alert("RDF");
				// var fileName = id+".rdf";
	      //       var a = document.createElement("a");
	      //       document.body.appendChild(a);
	      //       a.style = "display: none";
	      //       ActResource.rdfConvert(res[0]).then(function (result) {
	      //           var file = new Blob([result], {type: 'application/rdf'});
	      //           var fileURL = window.URL.createObjectURL(file);
	      //           a.href = fileURL;
	      //           a.download = fileName;
	      //           a.click();
	      //       });

			}
		}

		$scope.nasao = [];
		$scope.lista = "";
		$scope.pretraga = " ";
		$scope.search = function() {
			$scope.lista = $scope.keywords;

			if($scope.lista != ""){
				SearchResource.search($scope.keywords, "acts", id).then(function (result) {
					$scope.nasao = result;
				});
			}


		}
	}])

	.controller('AddActCtrl', ['$scope', '$uibModal', '$log', '_', '$rootScope', '$routeParams', 'Act', '$location',
	   function($scope, $uibModal, $log, _, $routeParams, $rootScope, Act, $location) {

		$scope.dokument = {
				id:'1',
				korisnik:'',
				odobreno:false,
				naslov:'',
				sluzbeniList:{
					broj_lista:'',
					cena:'',
					mestoDatum:{
						mesto:'',
						datum:''
					}
				},
				propisi:[{
					id:'1',
					korisnik:'',
					naziv_propisa:'',
					preambula:'',
					uvodniDeo:[{
						glava:[{
							id:1,
							podnaslovGlave:'',
							podaciGlave:{
								broj_glave:'1',
								naslovGlave:''
							},
							clan:[{
								id:1,
								podaciClana:{
									brojClana:'1',
									naslovClana:''
								},
								opis:''
							}]
						}]
					}],
					glavniDeo:[{
						glava:[{
							id:1,
							podnaslovGlave:'',
							podaciGlave:{
								broj_glave:'1',
								naslovGlave:''
							},
							clan:[{
								id:1,
								podaciClana:{
									brojClana:'1',
									naslovClana:''
								},
								opis:''
							}]
						}]
					}],
					zavrsniDeo:[{
						glava:[{
							id:1,
							podnaslovGlave:'',
							podaciGlave:{
								broj_glave:'1',
								naslovGlave:''
							},
							clan:[{
								id:1,
								podaciClana:{
									brojClana:'1',
									naslovClana:''
								},
								opis:''
							}]
						}],
						potpis_presednika:''
					}],
					dopunaZakona:{
						glava:[{
							id:1,
							podnaslovGlave:'',
							podaciGlave:{
								broj_glave:'1',
								naslovGlave:''
							},
							clan:[{
								id:1,
								podaciClana:{
									brojClana:'1',
									naslovClana:''
								},
								opis:''
							}]
						}]
					}
			}]
		};



		/**
		 * Funkcija pomocu koje dodajemo nove propise u nas dokument
		 */
		$scope.addNewPropis = function(id) {

			var newItemNo = $scope.dokument.propisi.length+1;
			$scope.dokument.propisi.push({'id':newItemNo, korisnik:'', naziv_propisa:'', preambula:'',
				uvodniDeo:[{glava:[{id:1, podnaslovGlave:'', podaciGlave:{broj_glave:'', naslovGlave:''},
					clan:[{id:1, podaciClana:{brojClana:'1', naslovClana:''}, opis:''}]}]}]});
		};

		/**
		 * Funkcija pomocu koje dodajemo zaglavnje za uvodni deo dokumenta
		 */
		$scope.addNewGlava = function(proId, glId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].length+1;
			$scope.dokument.propisi[proId-1].uvodniDeo[0].glava.push({'id':glId+1, podnaslovGlave:'',
				podaciGlave:{broj_glave:'1', naslovClana:''} ,clan:[{id:1,
					podaciClana:{brojClana:'1', naslovClana:''}, opis:''}]});
		};

		/**
		 * Funkcija pomocu koje dodajemo novo zaglavlje za glavni deo
		 * dokumenta
		 */
		$scope.addNewGlavaGlavniDeo = function(proId, glId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].length+1;
			$scope.dokument.propisi[proId-1].glavniDeo[0].glava.push({'id':glId+1, podnaslovGlave:'',
				podaciGlave:{broj_glave:'1', naslovClana:''} ,clan:[{id:1,
					podaciClana:{brojClana:'1', naslovClana:''}, opis:''}]});

		};


		/**
		 * Funkcija pomocu koje dodajemo zaglavnje za zavrsni deo naseg
		 * dokumenta
		 */
		$scope.addNewGlavaZavrsniDeo = function(proId, glId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].length+1;
			$scope.dokument.propisi[proId-1].zavrsniDeo[0].glava.push({'id':glId+1, podnaslovGlave:'',
				podaciGlave:{broj_glave:'1', naslovClana:''} ,clan:[{id:1,
					podaciClana:{brojClana:'1', naslovClana:''}, opis:''}], potpis_presednika:'Novica Nikolic'});

		};


		/**
		 * Funkcija pomocu koje dodajemo novi clan date glave u uvodnom delu
		 * dokumenta
		 */
		$scope.addNewClan = function(proId, glId, clId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].clan[clId-1].length+1;
			$scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].clan.push({'id':clId+1, podaciClana:{brojClana:'1', naslovClana:''}, opis:''});
		};

		/**
		 * Funkcija pomocu koje dodajemo novi clan date glave za glavni deo
		 * dokumenta
		 */
		$scope.addNewClanGlavniDeo = function(proId, glId, clId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].clan[clId-1].length+1;
			$scope.dokument.propisi[proId-1].glavniDeo[0].glava[glId-1].clan.push({'id':clId+1, podaciClana:{brojClana:'1', naslovClana:''}, opis:''});
		};

		/**
		 * Funkcija pomocu koje dodajemo novi clan date glave za zavrsni deo
		 * dokumenta
		 */
		$scope.addNewClanZavrsniDeo = function(proId, glId, clId) {

			//var newItemNo = $scope.dokument.propisi[proId-1].uvodniDeo[0].glava[glId-1].clan[clId-1].length+1;
			$scope.dokument.propisi[proId-1].zavrsniDeo[0].glava[glId-1].clan.push({'id':clId+1, podaciClana:{brojClana:'1', naslovClana:''}, opis:''});
		};

		/**
		 * Funkcija kada se pozove treba da preko rest posalje na server kreirani
		 * JSON fajl sa svim podacima
		 */
		$scope.addAct = function() {
			//$scope.dokument.propisi = $scope.propisa;

			Act.saveAct($scope.dokument, callBack);
		}

		function callBack(success) {
			if(success == 200){
				$location.path('/acts/proposed');
			} else if(success == 400) {
			}
		}
	}])
	.controller('ReadCrtl', ['$scope', '$uibModal', '$log', '_', '$routeParams', '$location', 'ActResource',
	   function($scope, $uibModal, $log, _, $routeParams, $location, ActResource) {

		$scope.list = [];
		var id = $routeParams.id;
		var res = id.split(".");


		ActResource.getAct(res[0]).then(function(items) {
			$scope.list = items;
			$scope.name = items.naslovAkta;
			$scope.xml = res[0];

		})


			$scope.openVotes = function() {
				$location.path('/vote/acts/' + $scope.name + '/' + id);
			}

	    $scope.acceptAct = function() {
			ActResource.getVote(res[0], 'accept', 'act').then(function(items) {
				if(items.error != null) {

					$scope.error = items.error;

				} else if(items.message != null) {
					$scope.vote = items.vote;
					$scope.message = items.message;
				}
		    });
		}

		$scope.deccidentAct = function() {
			ActResource.getVote(res[0], 'deccident', 'act').then(function(items) {
				if(items.error != null) {

					$scope.error1 = items.error;
				} else if(items.message != null) {
					$scope.vote = items.vote;
					$scope.message1 = items.message;
				}
		    });
		}

		$scope.againstAct = function() {
			ActResource.getVote(res[0], 'deccident', 'act').then(function(items) {
		    });
		}

		$scope.dopuna = function(id) {
			window.location = '#/complement/amandman/'+id;
		}

		$scope.deleteAct = function() {

			ActResource.getDelte(res[0]).then(function(items) {
				$location.path('/acts/accepted');
	    });
		}


	}])
