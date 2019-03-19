(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('OrderComponent', OrderComponent);

    OrderComponent.$inject = ['$resource', 'DateUtils'];

    function OrderComponent ($resource, DateUtils) {
        var resourceUrl =  'api/order-components/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.administration_time = DateUtils.convertDateTimeFromServer(data.administration_time);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
