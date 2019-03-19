(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ChartToGroupSessionSearch', ChartToGroupSessionSearch);

    ChartToGroupSessionSearch.$inject = ['$resource'];

    function ChartToGroupSessionSearch($resource) {
        var resourceUrl =  'api/_search/chart-to-group-sessions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
