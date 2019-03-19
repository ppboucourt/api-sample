(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Payer', Payer);

    Payer.$inject = ['$resource'];

    function Payer ($resource) {
        var resourceUrl =  'api/payers/:id';

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
