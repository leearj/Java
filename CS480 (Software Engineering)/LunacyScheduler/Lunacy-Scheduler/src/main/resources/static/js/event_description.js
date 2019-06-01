var cs480App = angular.module('LunacyScheduler', ['ui.bootstrap', 'firebase']);

var config = {
	    apiKey: "AIzaSyC28G2x-Xnel2wtmKnw3xi-a8TmWrnJ7tI",
	    authDomain: "lunacy-scheduler.firebaseapp.com",
	    databaseURL: "https://lunacy-scheduler.firebaseio.com",
	    projectId: "lunacy-scheduler"
	  };
firebase.initializeApp(config);
var database = firebase.database();

cs480App.controller('eventDescription', function ($scope, $firebaseArray,$firebaseObject, $log) {

  var sPageURL = window.location.search.substring(1);
  var sURLVariables = sPageURL.split('&');

  var id = GetURLParameter('id');
  var eventID = id.replace(/['"]+/g, '');
  console.log("First: " + eventID);

  function GetURLParameter(sParam){
    for (var i = 0; i < sURLVariables.length; i++){
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam)
        {
            return unescape(sParameterName[1]);
        }
    }
  }

  var reference = database.ref("Calendar/" + eventID);
  var event = $firebaseObject(reference);

  event.$loaded()
      .then(function(){

				$scope.description = event.description;
				$scope.start = moment(event.start).format('MMMM Do YYYY, h:mm:ss a').toString();
				$scope.end = moment(event.end).format('MMMM Do YYYY, h:mm:ss a').toString();
				$scope.title = event.title;
				$scope.users = event.users;

      });

	//Table Filter function
	(function(){
		'use strict';
		var $ = jQuery;
		$.fn.extend({
			filterTable: function(){
				return this.each(function(){
					$(this).on('keyup', function(e){
						var $this = $(this), search = $this.val().toLowerCase(), target = $this.attr('data-filters'), $rows = $(target).find('tbody tr');
						if(search == '') {
							$rows.show();
						} else {
							$rows.each(function(){
								var $this = $(this);
								$this.text().toLowerCase().indexOf(search) === -1 ? $this.hide() : $this.show();
							})
						}
					});
				});
			}
		});
		$('[data-action="filter"]').filterTable();
	})(jQuery);

	$(function(){
		// attach table filter plugin to inputs
		$('[data-action="filter"]').filterTable();
		$('.container').on('click', '.panel-heading span.filter', function(e){
			var $this = $(this),
			$panel = $this.parents('.panel');
			$panel.find('.panel-body').slideToggle();

			if($this.css('display') != 'none') {
				$panel.find('.panel-body input').focus();
			}
		});

		$('[data-toggle="tooltip"]').tooltip();
	})
});
