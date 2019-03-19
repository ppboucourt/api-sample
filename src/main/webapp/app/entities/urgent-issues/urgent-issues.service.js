(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('UrgentIssues', UrgentIssues);

    UrgentIssues.$inject = ['$resource'];

    function UrgentIssues ($resource) {
        var resourceUrl =  'api/urgent-issues/:id';

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
            'issuesByChart': {url: 'api/urgent-issues/chart/:id', method: 'GET', isArray: true},
            'issuesByChartAndEmployee': {url: 'api/urgent-issues/chart/:chartId/:empId', method: 'GET', isArray: true},
            'update': { method:'PUT' }
        });
    }
})();
