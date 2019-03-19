(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ChartSearch', ChartSearch);

    ChartSearch.$inject = ['$resource'];

    function ChartSearch($resource) {
        var resourceUrl = 'api/_search/charts/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'allCharts': {
                method: 'GET',
                url: 'api/_search/all-charts',
                isArray: true
            },
        });
    }
})();
