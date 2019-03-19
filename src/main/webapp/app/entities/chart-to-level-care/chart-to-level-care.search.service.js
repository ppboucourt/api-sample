(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ChartToLevelCareSearch', ChartToLevelCareSearch);

    ChartToLevelCareSearch.$inject = ['$resource'];

    function ChartToLevelCareSearch($resource) {
        var resourceUrl =  'api/_search/chart-to-level-cares/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
