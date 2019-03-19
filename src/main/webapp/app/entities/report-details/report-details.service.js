(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('ReportDetails', ReportDetails);

    ReportDetails.$inject = ['$resource'];

    function ReportDetails ($resource) {
        var resourceUrl =  'api/report-details/:id';

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
