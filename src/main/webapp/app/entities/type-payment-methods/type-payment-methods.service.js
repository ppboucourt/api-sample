(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypePaymentMethods', TypePaymentMethods);

    TypePaymentMethods.$inject = ['$resource'];

    function TypePaymentMethods ($resource) {
        var resourceUrl =  'api/type-payment-methods/:id';

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
