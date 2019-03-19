(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeEthnicity', TypeEthnicity);

    TypeEthnicity.$inject = ['$resource'];

    function TypeEthnicity ($resource) {
        var resourceUrl =  'api/type-ethnicities/:id';

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
