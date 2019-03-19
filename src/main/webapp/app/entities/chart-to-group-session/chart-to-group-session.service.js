(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('ChartToGroupSession', ChartToGroupSession);

    ChartToGroupSession.$inject = ['$resource'];

    function ChartToGroupSession ($resource) {
        var resourceUrl =  'api/chart-to-group-sessions/:id';

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
