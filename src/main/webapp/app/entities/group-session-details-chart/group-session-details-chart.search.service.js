(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('GroupSessionDetailsChartSearch', GroupSessionDetailsChartSearch);

    GroupSessionDetailsChartSearch.$inject = ['$resource'];

    function GroupSessionDetailsChartSearch($resource) {
        var resourceUrl =  'api/_search/group-session-details-charts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
