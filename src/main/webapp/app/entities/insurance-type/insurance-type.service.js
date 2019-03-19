(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('InsuranceType', InsuranceType);

    InsuranceType.$inject = ['$resource'];

    function InsuranceType ($resource) {
        var resourceUrl =  'api/insurance-types/:id';

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
