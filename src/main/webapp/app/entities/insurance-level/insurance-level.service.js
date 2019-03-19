(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('InsuranceLevel', InsuranceLevel);

    InsuranceLevel.$inject = ['$resource'];

    function InsuranceLevel ($resource) {
        var resourceUrl =  'api/insurance-levels/:id';

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
