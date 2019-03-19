(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('InsuranceCarrier', InsuranceCarrier);

    InsuranceCarrier.$inject = ['$resource'];

    function InsuranceCarrier ($resource) {
        var resourceUrl =  'api/insurance-carriers/:id';

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
