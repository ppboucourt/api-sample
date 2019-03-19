(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ChartToFormSearch', ChartToFormSearch);

    ChartToFormSearch.$inject = ['$resource'];

    function ChartToFormSearch($resource) {
        var resourceUrl =  'api/_search/chart-to-forms/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
