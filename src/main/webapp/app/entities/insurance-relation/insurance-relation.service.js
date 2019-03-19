(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('InsuranceRelation', InsuranceRelation);

    InsuranceRelation.$inject = ['$resource'];

    function InsuranceRelation ($resource) {
        var resourceUrl =  'api/insurance-relations/:id';

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
