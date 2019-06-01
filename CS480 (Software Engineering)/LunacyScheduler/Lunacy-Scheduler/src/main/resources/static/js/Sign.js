var signUpMod = angular.module('SignUp', ['firebase']);

var config = {
	    apiKey: "AIzaSyC28G2x-Xnel2wtmKnw3xi-a8TmWrnJ7tI",
	    authDomain: "lunacy-scheduler.firebaseapp.com",
	    databaseURL: "https://lunacy-scheduler.firebaseio.com",
	    projectId: "lunacy-scheduler"
	  };
firebase.initializeApp(config);

signUpMod.controller('SignIn', function ($scope, $window, $log) {
    $scope.signin = function() {
        firebase.auth().signInWithEmailAndPassword($scope.signin.email, $scope.signin.password)
            .then(function(firebaseUser) {
                console.log('Logged in');
                window.location = "/schedules.html";
            })
            .catch(function(error) {
                // Handle Errors here.
                var errorCode = error.code;
                var errorMessage = error.message;
                if (errorCode === 'auth/wrong-password') {
                    alert('Wrong password.');
                } else {
                    alert(errorMessage);
                }
                // ...
            });
        //alert("Successfully logged in");
    }
});


signUpMod.controller('SignUp', function ($scope, $log) {
    $scope.signup = function() {
        if($scope.first.password == $scope.confirm.password) {
            if($scope.first.password.length >= 6) {
                firebase.auth().createUserWithEmailAndPassword($scope.signup.email, $scope.confirm.password)
                    .then(function(firebaseUser) {
                        console.log('sign up successful');
                        window.location = "/login.html";
                    })
                    .catch(function(error) {
                        // Handle Errors here.
                        var errorCode = error.code;
                        var errorMessage = error.message;
                        if (errorCode == 'auth/weak-password') {
                            alert('The password is too weak.');
                        } else {
                            alert(errorMessage);
                        }
                        // ...
                    });
                //alert("Account successfully created");
            }
            else {
                alert("Password needs to be atleast 6 characters long");
            }
        } else {
            alert("Passwords do not match!");
        }
    }
});
//
//
// signUpMod.controller('SignOutUser', function ($scope) {
// 	$scope.signout = function() {
//         firebase.auth().signOut();
// 	}
// });
