(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeEmployee', TypeEmployee);

    TypeEmployee.$inject = ['$resource'];

    function TypeEmployee ($resource) {
        var resourceUrl =  'api/type-employees/:id';

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
