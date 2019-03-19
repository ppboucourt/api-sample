(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeRace', TypeRace);

    TypeRace.$inject = ['$resource'];

    function TypeRace ($resource) {
        var resourceUrl =  'api/type-races/:id';

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
