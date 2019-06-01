var cs480App = angular.module('LunacyScheduler', ['ui.bootstrap', 'firebase']);

var config = {
	    apiKey: "AIzaSyC28G2x-Xnel2wtmKnw3xi-a8TmWrnJ7tI",
	    authDomain: "lunacy-scheduler.firebaseapp.com",
	    databaseURL: "https://lunacy-scheduler.firebaseio.com",
	    projectId: "lunacy-scheduler"
	  };
firebase.initializeApp(config);
var database = firebase.database();



cs480App.controller('DatepickerPopupDemoCtrl', function ($scope, $log, $firebaseArray, $http, $window) {


  //DatePicker
  $scope.today = function() {
	    $scope.dt = new Date();
	    $scope.dt.end = new Date();

  };
  $scope.today();



  $scope.dateOptions = {
    minDate: new Date(),
    showWeeks: false
  };

  $scope.dateOptions1 = {
	minDate: $scope.dt,
	showWeeks: false
  };
  $scope.open1 = function() {
    $scope.popup1.opened = true;
  };

  $scope.open2 = function() {
	$scope.dateOptions1.minDate = $scope.dt;
    $scope.popup2.opened = true;
  };

  $scope.setDate = function(year, month, day) {
    $scope.dt = new Date(year, month, day);
  };

  $scope.popup1 = {
    opened: false
  };

  $scope.popup2 = {
    opened: false
  };

	//Timepicker

		  $scope.submit = function() {
			  database.ref('user/' + $scope.first.name).set({
				  	first: $scope.first.name,
				  	last: $scope.last.name,
				  	email: $scope.emailname
				  });
			  var pushkey = database.ref('user/'+$scope.first.name+'/available/').push().key;
			  database.ref('user/'+$scope.first.name+'/available/availableTimes/'+ pushkey).set ( {

                  start: $scope.dt.getTime(),
                  end: $scope.dt.end.getTime()
			  });
					angular.forEach($scope.sunAvails, function(item) {
						database.ref('user/'+$scope.first.name+'/available/availableTimes/'+ pushkey +'/sunday/').push().set({
								
											start: {time: item.start.getTime()},
											end: {time: item.end.getTime()}
									
							});
					});
					angular.forEach($scope.monAvails, function(item) {
						database.ref('user/'+$scope.first.name+'/available/availableTimes/'+ pushkey +'/monday/').push().set({
                            start: {time: item.start.getTime()},
                            end: {time: item.end.getTime()}
							});
					});

					angular.forEach($scope.tuesAvails, function(item) {
						database.ref('user/'+$scope.first.name+'/available/availableTimes/'+ pushkey +'/tuesday/').push().set({

                            start: {time: item.start.getTime()},
                            end: {time: item.end.getTime()}
							});
					});

					angular.forEach($scope.wedAvails, function(item) {
						database.ref('user/'+$scope.first.name+'/available/availableTimes/'+ pushkey +'/wednesday/').push().set({

                            start: {time: item.start.getTime()},
                            end: {time: item.end.getTime()}
							});
					});

					angular.forEach($scope.thursAvails, function(item) {
						database.ref('user/'+$scope.first.name+'/available/availableTimes/'+ pushkey +'/thursday/').push().set({

                            start: {time: item.start.getTime()},
                            end: {time: item.end.getTime()}
							});
					});

					angular.forEach($scope.friAvails, function(item) {
						database.ref('user/'+$scope.first.name+'/available/availableTimes/'+ pushkey +'/friday/').push().set({

                            start: {time: item.start.getTime()},
                            end: {time: item.end.getTime()}
							});
					});
					angular.forEach($scope.satAvails, function(item) {
						database.ref('user/'+$scope.first.name+'/available/availableTimes/'+ pushkey +'/saturday/').push().set({

                            start: {time: item.start.getTime()},
                            end: {time: item.end.getTime()}
							});
					});

              		$window.location.href = '/CreateEvent2.html';
		  };

			$scope.sunStart = {
				date : ""
			};
			$scope.sunEnd = {
				date : ""
			};
			$scope.monStart = {
				date : ""
			};
			$scope.monEnd = {
				date : ""
			};
			$scope.tuesStart = {
				date : ""
			};
			$scope.tuesEnd = {
				date : ""
			};
			$scope.wedStart = {
				date : ""
			};
			$scope.wedEnd = {
				date : ""
			};
			$scope.thursStart = {
				date : ""
			};
			$scope.thursEnd = {
				date : ""
			};
			$scope.friStart = {
				date : ""
			};
			$scope.friEnd = {
				date : ""
			};
			$scope.satStart = {
				date : ""
			};
			$scope.satEnd = {
				date : ""
			};

			  $scope.hstep = 1;
			  $scope.mstep = 1;

			  $scope.ismeridian = true;

			  $scope.toggleMode = function() {
			    $scope.ismeridian = ! $scope.ismeridian;
			  };


						  $scope.startSunday = function () {
						    $log.log('Time changed to: ' + $scope.sunStart.date);
						  };
						  $scope.startMonday = function () {
							$log.log('Time changed to: ' + $scope.monStart.date);
						  };
						  $scope.startTuesday = function () {
							$log.log('Time changed to: ' + $scope.tuesStart.date);
						  };
						  $scope.startWednesday = function () {
						    $log.log('Time changed to: ' + $scope.wedStart.date);
						  };
						  $scope.startThursday = function () {
						    $log.log('Time changed to: ' + $scope.thursStart.date);
						  };
						  $scope.startFriday = function () {
							$log.log('Time changed to: ' + $scope.friStart.date);
						  };
						  $scope.startSaturday = function () {
							$log.log('Time changed to: ' + $scope.satStart.date);
						  };
						  $scope.endSunday = function () {
							$log.log('Time changed to: ' + $scope.sunEnd.date);
						  };
						  $scope.endMonday = function () {
							$log.log('Time changed to: ' + $scope.monEnd.date);
						  };
						  $scope.endTuesday = function () {
							$log.log('Time changed to: ' + $scope.tuesEnd.date);
						  };
						  $scope.endWednesday = function () {
							$log.log('Time changed to: ' + $scope.wedEnd.date);
						  };
						  $scope.endThursday = function () {
							$log.log('Time changed to: ' + $scope.thursEnd.date);
						  };
						  $scope.endFriday = function () {
							$log.log('Time changed to: ' + $scope.friEnd.date);
						  };
						  $scope.endSaturday = function () {
						    $log.log('Time changed to: ' + $scope.satEnd.date);
						};

			$scope.sunAvails = [];
			$scope.monAvails = [];
			$scope.tuesAvails = [];
			$scope.wedAvails = [];
			$scope.thursAvails = [];
			$scope.friAvails = [];
			$scope.satAvails = [];


			$scope.sunSubmit = function() {
				$scope.sunAvails.push({ 'start':$scope.sunStart.date, 'end': $scope.sunEnd.date});
				$scope.sunStart.date = null;
				$scope.sunEnd.date = null;
			};

			$scope.removeSun = function(start) {
				var counter = -1;
				var comArr = eval( $scope.sunAvails);
				for( var i = 0; i < comArr.length; i++ ) {
					if( comArr[i].start === start ) {
						counter = i;
						break;
					}
				}
				if( counter === -1 ) {
					alert( "Something gone wrong" );
				}
				$scope.sunAvails.splice( counter, 1 );
			}

			$scope.monSubmit = function() {
				$scope.monAvails.push({ 'start':$scope.monStart.date, 'end': $scope.monEnd.date});
				$scope.monStart.date=null;
				$scope.monEnd.date=null;
			};

			$scope.removeMon = function(start) {
				var counter = -1;
				var comArr = eval( $scope.monAvails);
				for( var i = 0; i < comArr.length; i++ ) {
					if( comArr[i].start === start ) {
						counter = i;
						break;
					}
				}
				if( counter === -1 ) {
					alert( "Something gone wrong" );
				}
				$scope.monAvails.splice( counter, 1 );
			}

			$scope.tuesSubmit = function() {
				$scope.tuesAvails.push({ 'start':$scope.tuesStart.date, 'end': $scope.tuesEnd.date});
				$scope.tuesStart.date=null;
				$scope.tuesEnd.date=null;
			};

			$scope.removeTues = function(start) {
				var counter = -1;
				var comArr = eval( $scope.tuesAvails);
				for( var i = 0; i < comArr.length; i++ ) {
					if( comArr[i].start === start ) {
						counter = i;
						break;
					}
				}
				if( counter === -1 ) {
					alert( "Something gone wrong" );
				}
				$scope.tuesAvails.splice( counter, 1 );
			};

			$scope.wedSubmit = function() {
				$scope.wedAvails.push({ 'start':$scope.wedStart.date, 'end': $scope.wedEnd.date});
				$scope.wedStart.date=null;
				$scope.wedEnd.date=null;
			};

			$scope.removeWed = function(start) {
				var counter = -1;
				var comArr = eval( $scope.wedAvails);
				for( var i = 0; i < comArr.length; i++ ) {
					if( comArr[i].start === start ) {
						counter = i;
						break;
					}
				}
				if( counter === -1 ) {
					alert( "Something gone wrong" );
				}
				$scope.wedAvails.splice( counter, 1 );
			};

			$scope.thursSubmit = function() {
				$scope.thursAvails.push({ 'start':$scope.thursStart.date, 'end': $scope.thursEnd.date});
				$scope.thursStart.date=null;
				$scope.thursEnd.date=null;
			};

			$scope.removeThurs = function(start) {
				var counter = -1;
				var comArr = eval( $scope.thursAvails);
				for( var i = 0; i < comArr.length; i++ ) {
					if( comArr[i].start === start ) {
						counter = i;
						break;
					}
				}
				if( counter === -1 ) {
					alert( "Something gone wrong" );
				}
				$scope.thursAvails.splice( counter, 1 );
			};

			$scope.friSubmit = function() {
				$scope.friAvails.push({ 'start':$scope.friStart.date, 'end': $scope.friEnd.date});
				$scope.friStart.date=null;
				$scope.friEnd.date=null;
			};

			$scope.removeFri = function(start) {
				var counter = -1;
				var comArr = eval( $scope.friAvails);
				for( var i = 0; i < comArr.length; i++ ) {
					if( comArr[i].start === start ) {
						counter = i;
						break;
					}
				}
				if( counter === -1 ) {
					alert( "Something gone wrong" );
				}
				$scope.friAvails.splice( counter, 1 );
			};

			$scope.satSubmit = function() {
				$scope.satAvails.push({ 'start':$scope.satStart.date, 'end': $scope.satEnd.date});
				$scope.satStart.date=null;
				$scope.satEnd.date=null;
			};

			$scope.removeSat = function(start) {
				var counter = -1;
				var comArr = eval( $scope.satAvails);
				for( var i = 0; i < comArr.length; i++ ) {
					if( comArr[i].start === start ) {
						counter = i;
						break;
					}
				}
				if( counter === -1 ) {
					alert( "Something gone wrong" );
				}
				$scope.satAvails.splice( counter, 1 );
			}
			


});
