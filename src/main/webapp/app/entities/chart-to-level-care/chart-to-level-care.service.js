(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('ChartToLevelCare', ChartToLevelCare);

    ChartToLevelCare.$inject = ['$resource', 'DateUtils'];

    function ChartToLevelCare ($resource, DateUtils) {
        var resourceUrl =  'api/chart-to-level-cares/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'byChart': {url: 'api/chart-to-level-cares/chart/:id', method: 'GET', isArray: true},
            'lastByChart': {url: 'api/chart-to-level-cares/last-by-chart/:id', method: 'GET'},
            'update': {method:'PUT'}
        });
    }
})();
