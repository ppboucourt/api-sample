(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Allergies', Allergies);

    Allergies.$inject = ['$resource'];

    function Allergies ($resource) {
        var resourceUrl =  'api/allergies/:id';

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
