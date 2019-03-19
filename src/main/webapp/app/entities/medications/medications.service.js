(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Medications', Medications);

    Medications.$inject = ['$resource'];

    function Medications ($resource) {
        var resourceUrl =  'api/medications/:id';

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
