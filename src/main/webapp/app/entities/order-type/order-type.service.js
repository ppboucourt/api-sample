(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Order_type', Order_type);

    Order_type.$inject = ['$resource'];

    function Order_type ($resource) {
        var resourceUrl =  'api/order-types/:id';

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
