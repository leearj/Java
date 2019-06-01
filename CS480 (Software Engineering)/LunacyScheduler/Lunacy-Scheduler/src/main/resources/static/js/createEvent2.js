var cs480App = angular.module('LunacyScheduler', ['ui.bootstrap', 'firebase']);

var config = {
	    apiKey: "AIzaSyC28G2x-Xnel2wtmKnw3xi-a8TmWrnJ7tI",
	    authDomain: "lunacy-scheduler.firebaseapp.com",
	    databaseURL: "https://lunacy-scheduler.firebaseio.com",
	    projectId: "lunacy-scheduler"
	  };
firebase.initializeApp(config);
var database = firebase.database();

cs480App.controller('Datepicker', function ($scope, $firebaseObject, $firebaseArray, $log, $window, $http) {

  var options = [];
  var reference = database.ref("user");
  var users = $firebaseArray(reference);

  users.$loaded()
      .then(function(){
          angular.forEach(users, function(user) {
              var option = "<option>" + user.$id + "</option>"
              options.push(option);
							console.log(user.$id); //This grabs the Object ID

							/*This will only work if we have a user:{name} under object
							console.log(user.user); */
          })

          $('.selectpicker').html(options);
          $('.selectpicker').selectpicker('refresh');
      });

  //DatePicker
  $scope.today = function() {
	    $scope.dt = new Date();
	    $scope.dt.end = new Date();

  };
  $scope.today();

  $scope.clear = function() {
    $scope.dt = null;
    $scope.dt.end = null;
  };


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

  $scope.popup1 = {
    opened: false
  };

  $scope.popup2 = {
    opened: false
  };

	$scope.submit = function() {
	    var stringTemp = $scope.title.replace(/(?!\w|\s)./g, '');
		var targets = [];
		$.each($(".selectpicker option:selected"), function(){
		targets.push($(this).val());
		});

        var pass = {'start': $scope.dt.getTime(),
            'end': $scope.dt.end.getTime(),
            'users': targets};
            var dateTime;
            var response = $http.post("/set", pass);
            response.success(function(data){
                console.log(stringTemp);
                dateTime = data;
                var ref = database.ref("Calendar/" + stringTemp);
                ref.set({
                    'title': $scope.title,
                    'description': $scope.description,
                    'url': "/event_description.html?id=" + stringTemp,
                    'start': dateTime.start,
                    'end': dateTime.end

                });

                angular.forEach(targets, function(tar) {
                    database.ref('user/' + tar).once('value').then(function(snapshot) {
                        var first = snapshot.val().first || 'Anonymous';
                        var last = snapshot.val().last || 'Anonymous';
                        var email = snapshot.val().email || 'Anonymous';
                        var temp = {'first' : first, 'last': last, 'email': email};
                        database.ref("Calendar/" + stringTemp + "/users/").push().set(temp);
                        // ...
                    });
                });
                $window.location.href = '/calendar.html';
            });
            response.error(function(data, status, headers, config) {
                console.log( "Exception details: " + JSON.stringify({data: data}));
            });


	};

});
