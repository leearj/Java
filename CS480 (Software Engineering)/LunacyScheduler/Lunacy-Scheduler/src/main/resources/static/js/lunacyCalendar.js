var calendarApp = angular.module('FullCalendar', ['firebase']);

loadCalendar();

var config = {
	    apiKey: "AIzaSyC28G2x-Xnel2wtmKnw3xi-a8TmWrnJ7tI",
	    authDomain: "lunacy-scheduler.firebaseapp.com",
	    databaseURL: "https://lunacy-scheduler.firebaseio.com",
	    projectId: "lunacy-scheduler"
	  };
firebase.initializeApp(config);
var database = firebase.database();

calendarApp.controller('displayCalendar', function ($scope, $firebaseObject,$firebaseArray) {

	var reference = database.ref("Calendar");
	$scope.events = new $firebaseArray(reference);
	$scope.events.$loaded().then(function() {
		$('#calendar').fullCalendar('renderEvents', $scope.events, true);
	});
});

//Initialized Calendar
function loadCalendar(){

	$(document).ready(function() {

		flag = true;

		$('#calendar').fullCalendar({
			defaultDate: new Date(),
			editable: true,
			navLinks: true, // can click day/week names to navigate views
			eventLimit: true, // allow "more" link when too many events
			header: {
		        left: 'prev,next today',
		        center: 'title',
		        right: 'switchView month,agendaWeek,agendaDay,listMonth,listWeek,listDay '
		    },
		    customButtons: {
		        switchView: {
		            text: 'View: Agenda',
		            click: function() {

		            	var view = $('#calendar').fullCalendar('getView');

		            	if(view.name == "month"){
		            		$('#calendar').fullCalendar('changeView', 'listMonth');
		            	}

		            	else if(view.name == "agendaWeek"){
		            		$('#calendar').fullCalendar('changeView', 'listWeek');
		            	}

		            	else if(view.name == "agendaDay"){
		            		$('#calendar').fullCalendar('changeView', 'listDay');
		            	}
		            	else if(view.name == "listMonth"){
		            		$('#calendar').fullCalendar('changeView', 'month');
		            	}

		            	else if(view.name == "listWeek"){
		            		$('#calendar').fullCalendar('changeView', 'agendaWeek');
		            	}

		            	else if(view.name == "listDay"){
		            		$('#calendar').fullCalendar('changeView', 'agendaDay');
		            	}

		            	changeViews();
		            }
		        }
		    },
		    buttonText: {
		    	month: "Month",
		    	agendaWeek: "Week",
		    	agendaDay: "Day",
		    	listWeek: "Week",
		    	listDay: "Day"
		    },
		});

		changeViews();
	});

	function changeViews(){

		if(flag){

		$(".fc-listMonth-button").hide();
		$(".fc-listWeek-button").hide();
		$(".fc-listDay-button").hide();
		$(".fc-month-button").show();
		$(".fc-agendaWeek-button").show();
		$(".fc-agendaDay-button").show();

		$(".fc-switchView-button").text('View: Agenda');

		}else{

		$(".fc-listMonth-button").show();
		$(".fc-listWeek-button").show();
		$(".fc-listDay-button").show();
		$(".fc-month-button").hide();
		$(".fc-agendaWeek-button").hide();
		$(".fc-agendaDay-button").hide();

		$(".fc-switchView-button").text('View: List');
		}

		flag = !flag;
		}
}
