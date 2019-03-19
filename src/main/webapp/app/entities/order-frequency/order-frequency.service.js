(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('OrderFrequency', OrderFrequency);

    OrderFrequency.$inject = ['$resource'];

    function OrderFrequency ($resource) {
        var resourceUrl =  'api/order-frequencies/:id';

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
