(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('ChartToAction', ChartToAction);

    ChartToAction.$inject = ['$resource', 'DateUtils'];

    function ChartToAction ($resource, DateUtils) {
        var resourceUrl =  'api/chart-to-actions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_prescription = DateUtils.convertDateTimeFromServer(data.date_prescription);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
