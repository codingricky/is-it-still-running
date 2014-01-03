"use strict";

angular.module('app.services', [])
    .factory('Race', function ($resource) {
        return $resource('/api/race/:id', {}, {
            query: {method: 'GET', isArray: true},
            get: {method: 'GET'},
            update: {method: 'PUT'}
        })
    });

