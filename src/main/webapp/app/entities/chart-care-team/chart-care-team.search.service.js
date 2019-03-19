(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ChartCareTeamSearch', ChartCareTeamSearch);

    ChartCareTeamSearch.$inject = ['$resource'];

    function ChartCareTeamSearch($resource) {
        var resourceUrl =  'api/_search/chart-care-team/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
