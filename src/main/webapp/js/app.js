'use strict';

/* App Module */

var talismanApp = angular.module('talismanApp', [
	'ngRoute',
	'talismanControllers'
]);

talismanApp.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider.
			when('/user', {
				templateUrl: 'partials/user.html',
				controller: 'userCtrl'
			}).
			otherwise({
				redirectTo: '/user'
			});
	}
]);
