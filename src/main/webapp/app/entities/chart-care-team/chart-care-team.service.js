(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('ChartCareTeam', ChartCareTeam);

    ChartCareTeam.$inject = ['$resource'];

    function ChartCareTeam ($resource) {
        var resourceUrl =  'api/chart-care-team/:id';

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
            'update': { method:'PUT' },
            'getAllByChartId': { 'url': 'api/chart-care-team-by-chart/:id', method: 'GET', isArray: true},
            'getAllByEmployeeId': { 'url': 'api/chart-care-team-by-employee/:id', method: 'GET', isArray: true},
            'getAllVOByChart': {'url': 'api/chart-care-team/vo-chart/:id', method: 'GET', isArray: true}
        });
    }
})();
