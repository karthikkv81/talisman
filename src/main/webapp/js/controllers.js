'use strict';

/* Controllers */

var userControllers = angular.module('userControllers', []);

addressbookControllers.controller('userCtrl', ['$rootScope', '$scope', '$routeParams', '$http',
	function($rootScope, $scope, $routeParams, $http) {
		$scope.createUser = function() {
			console.log('BEGIN createUser');
			
			$http.post('api/user', {
					"userName": $scope.userName
					,"firstName": $scope.firstName
					,"lastName": $scope.lastName
			})
			.success(function(data, status, headers, config) {
				console.log('data = ' , data);
				$scope.userName = '';
				$scope.firstName = '';
				$scope.lastName = '';
				$scope.newUserId = data;
			})
			.error(function(data, status, headers, config) {
				console.log('error: data = ' , data);
			});
		};
		
		$scope.searchUser = function() {
			$http.get('api/user/' + $scope.searchUserId)
			.success(function(data, status, headers, config) {
				console.log('data = ' , data);
				$scope.user = data;
			})
			.error(function(data, status, headers, config) {
				console.log('error: data = ' , data);
			});
		};
	}
]);
