(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ChartToActionSearch', ChartToActionSearch);

    ChartToActionSearch.$inject = ['$resource'];

    function ChartToActionSearch($resource) {
        var resourceUrl =  'api/_search/chart-to-actions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
