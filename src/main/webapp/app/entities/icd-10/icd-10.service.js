(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Icd10', Icd10);

    Icd10.$inject = ['$resource'];

    function Icd10 ($resource) {
        var resourceUrl =  'api/icd-10-s/:id';

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
