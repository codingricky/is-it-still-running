"use strict";

angular.module('app.controllers', ['app.services'])
    .controller('mainController', ['$scope', '$location', '$route', '$http', function ($scope, $location, $route, $http) {
        $scope.url = "http://www.google.com";
        $scope.check = function() {
            var payload = {"url" : $scope.url};
            $http.post('/check', payload).success(function(data, status, headers, config) {
                alert(data["status"]);
            });
        }
    }]);
