"use strict";

angular.module('app.controllers', ['app.services'])
    .controller('mainController', ['$scope', '$http', '$modal', function ($scope, $http, $modal) {
        $scope.url = "http://www.google.com";

        $scope.check = function () {
            var payload = {"url": $scope.url};
            var modalInstance = $modal.open({templateUrl: 'spinner.html'});
            $http.post('/check', payload).success(function (data, status, headers, config) {
                modalInstance.dismiss();
                if (data['status'] == 200) {
                    $scope.message = '200 returned, site is up.';
                }
            }).error(function (data, status, headers, config) {;
                modalInstance.dismiss();
            });
        }
    }]);
