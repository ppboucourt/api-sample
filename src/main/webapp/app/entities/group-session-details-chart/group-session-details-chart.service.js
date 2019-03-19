(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('GroupSessionDetailsChart', GroupSessionDetailsChart);

    GroupSessionDetailsChart.$inject = ['$resource'];

    function GroupSessionDetailsChart ($resource) {
        var resourceUrl =  'api/group-session-details-charts/:id';

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
            'byGroupSessionDetails': {url: 'api/search-by-group-session/:gsId', method: 'GET', isArray: true},
            'update': { method:'PUT' },
            'groupSessionDetailsChartByChart': {url: 'api/group-session-details-chart-by-chart/:chartId', method: 'GET', isArray: true},
            'getGroupSessionByChartId': { method: 'GET', url: 'api/group-session-details-charts-chart-id/:id', isArray: true},

        });
    }
})();
