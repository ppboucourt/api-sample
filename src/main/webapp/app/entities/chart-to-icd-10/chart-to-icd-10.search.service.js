(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ChartToIcd10Search', ChartToIcd10Search);

    ChartToIcd10Search.$inject = ['$resource'];

    function ChartToIcd10Search($resource) {
        var resourceUrl =  'api/_search/chart-to-icd-10-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
