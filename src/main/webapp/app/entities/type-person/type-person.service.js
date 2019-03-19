(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypePerson', TypePerson);

    TypePerson.$inject = ['$resource'];

    function TypePerson ($resource) {
        var resourceUrl =  'api/type-people/:id';

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
