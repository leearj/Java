var loggedIn = angular.module('Schedule', ['ui.bootstrap', 'firebase']);

var config = {
	    apiKey: "AIzaSyC28G2x-Xnel2wtmKnw3xi-a8TmWrnJ7tI",
	    authDomain: "lunacy-scheduler.firebaseapp.com",
	    databaseURL: "https://lunacy-scheduler.firebaseio.com",
	    projectId: "lunacy-scheduler"
	  };
firebase.initializeApp(config);




var database = firebase.database();


loggedIn.controller('loadSchedules', function($scope, $firebaseArray, $window, $log) {
    var unsubscribe = firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            console.log("UID" + user.uid);
            var ref = database.ref('user/' + user.uid + '/schedules/');
            $scope.schedules = $firebaseArray(ref);


            // User is signed in.
        } else {
            // No user is signed in.
        }
    });
    $scope.submit = function() {
    	unsubscribe();
        $window.location.href = '/createschedule.html';
    }

});

loggedIn.controller('createSchedule', function($scope, $window, $log) {
	var currentUser;
    var unsubscribe = firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            console.log("UID" + user.uid);
			currentUser = user;

            // User is signed in.
        } else {
            // No user is signed in.
        }
    });
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


            $scope.submit = function() {
                var newkey = database.ref('user/' + currentUser.uid + '/schedules/').push().key;
                database.ref('user/' + currentUser.uid + '/schedules/' + newkey).update({
                    name: $scope.schedule.name,
                    start: $scope.dt,
                    end: $scope.dt.end,
                    location: newkey
                });

                database.ref('schedule/' + currentUser.uid + '/available/availableTimes/' + newkey).update ( {

                    start: $scope.dt.getTime(),
                    end: $scope.dt.end.getTime()
                });
                angular.forEach($scope.sunAvails, function(item) {
                    database.ref('schedule/' + currentUser.uid + '/available/availableTimes/' + newkey +'/sunday/').push().update({

                        start: {time: item.start.getTime()},
                        end: {time: item.end.getTime()}

                    });
                });
                angular.forEach($scope.monAvails, function(item) {
                    database.ref('schedule/' + currentUser.uid+'/available/availableTimes/'+ newkey +'/monday/').push().update({
                        start: {time: item.start.getTime()},
                        end: {time: item.end.getTime()}
                    });
                });

                angular.forEach($scope.tuesAvails, function(item) {
                    database.ref('schedule/' + currentUser.uid+'/available/availableTimes/'+ newkey +'/tuesday/').push().update({

                        start: {time: item.start.getTime()},
                        end: {time: item.end.getTime()}
                    });
                });

                angular.forEach($scope.wedAvails, function(item) {
                    database.ref('schedule/' + currentUser.uid+'/available/availableTimes/'+ newkey +'/wednesday/').push().update({

                        start: {time: item.start.getTime()},
                        end: {time: item.end.getTime()}
                    });
                });

                angular.forEach($scope.thursAvails, function(item) {
                    database.ref('schedule/' + currentUser.uid+'/available/availableTimes/'+ newkey +'/thursday/').push().update({

                        start: {time: item.start.getTime()},
                        end: {time: item.end.getTime()}
                    });
                });

                angular.forEach($scope.friAvails, function(item) {
                    database.ref('schedule/' + currentUser.uid+'/available/availableTimes/'+ newkey +'/friday/').push().update({

                        start: {time: item.start.getTime()},
                        end: {time: item.end.getTime()}
                    });
                });
                angular.forEach($scope.satAvails, function(item) {
                    database.ref('schedule/' + currentUser.uid+'/available/availableTimes/'+ newkey +'/saturday/').push().update({

                        start: {time: item.start.getTime()},
                        end: {time: item.end.getTime()}
                    });
                });
                unsubscribe();
                $window.location.href = '/schedules.html';
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
            };

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
            };

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


//
//
// signUpMod.controller('SignOutUser', function ($scope) {
// 	$scope.signout = function() {
//         firebase.auth().signOut();
// 	}
// });
