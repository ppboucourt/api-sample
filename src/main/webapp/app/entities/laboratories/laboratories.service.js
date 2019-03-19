(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Laboratories', Laboratories);

    Laboratories.$inject = ['$resource'];

    function Laboratories ($resource) {
        var resourceUrl =  'api/laboratories/:id';

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
