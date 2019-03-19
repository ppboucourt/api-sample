(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Corporation', Corporation);

    Corporation.$inject = ['$resource'];

    function Corporation ($resource) {
        var resourceUrl =  'api/corporations/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
