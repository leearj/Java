// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var cs480App = angular.module('LunacyScheduler', []);
var config = {
	    apiKey: "AIzaSyC28G2x-Xnel2wtmKnw3xi-a8TmWrnJ7tI",
	    authDomain: "lunacy-scheduler.firebaseapp.com",
	    databaseURL: "https://lunacy-scheduler.firebaseio.com",
	    projectId: "lunacy-scheduler"
	  };
firebase.initializeApp(config);
var database = firebase.database();


cs480App.controller('Schedule', function ($scope, $http) {
	$scope.addTime = function() {
		  firebase.database().ref('tester/').set({
			name: $scope.name,
			group: $scope.group,
		    start: $scope.start.toString(),
		    end: $scope.end.toString()
		  });
	}
	
	$scope.addDate = function() {
		  firebase.database().ref('ubaldo/').set({
		    start: $scope.start.toString()
		  });
	}
	
	$scope.addDateTest = function() {
		  firebase.database().ref('ubaldo2/').set({
		    start: $scope.start.toString(),
		    end: $scope.end.toString()
		  });
	}
	$scope.open1 = function() {
	    $scope.popup1.opened = true;
	  }
  $scope.loadUsers = function() {
	  $http.get("cs480/users/list")
	  	.success(function(data){
	  		$scope.users = data;
	  	});
  }

  $scope.getUser = function() {
	  $http.get("cs480/user/" + $scope.userIdToSearch)
	  	.success(function(data){
	  		$scope.founduser = data;
	  	});
  }

  $scope.addUser = function() {
	  $http.post("cs480/user/" + $scope.new_id + "?name=" + $scope.new_name + "&major=" + $scope.new_major)
	  	.success(function(data){
	  		$scope.loadUsers();
	  	});
  }

  $scope.deleteUser = function(userId) {
	  $http.delete("cs480/user/" + userId)
	  	.success(function(data){
	  		$scope.loadUsers();
	  	});
  }

  $scope.loadUsers();

});