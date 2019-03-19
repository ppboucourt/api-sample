(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Building', Building);

    Building.$inject = ['$resource'];

    function Building ($resource) {
        var resourceUrl =  'api/buildings/:id';

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
            'byfacility': {url: 'api/buildings/by-facility/:id', method: 'GET', isArray: true},
            'update': { method:'PUT' }
        });
    }
})();
