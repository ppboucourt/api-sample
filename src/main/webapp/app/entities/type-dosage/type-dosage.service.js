(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeDosage', TypeDosage);

    TypeDosage.$inject = ['$resource'];

    function TypeDosage ($resource) {
        var resourceUrl =  'api/type-dosages/:id';

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
