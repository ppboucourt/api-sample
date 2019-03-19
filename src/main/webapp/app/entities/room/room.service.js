(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Room', Room);

    Room.$inject = ['$resource'];

    function Room ($resource) {
        var resourceUrl =  'api/rooms/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'byfacility': {url: 'api/rooms/by-facility/:id', method: 'GET', isArray: true},
            'update': { method:'PUT' }
        });
    }
})();
