(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Orders', Orders);

    Orders.$inject = ['$resource', 'DateUtils'];

    function Orders ($resource, DateUtils) {
        var resourceUrl =  'api/orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.start_date = DateUtils.convertDateTimeFromServer(data.start_date);
                        data.end_date = DateUtils.convertDateTimeFromServer(data.end_date);
                        data.review_date = DateUtils.convertDateTimeFromServer(data.review_date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
