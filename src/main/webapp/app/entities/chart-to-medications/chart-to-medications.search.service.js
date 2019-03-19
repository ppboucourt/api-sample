(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ChartToMedicationsSearch', ChartToMedicationsSearch);

    ChartToMedicationsSearch.$inject = ['$resource'];

    function ChartToMedicationsSearch($resource) {
        var resourceUrl =  'api/_search/chart-to-medications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
