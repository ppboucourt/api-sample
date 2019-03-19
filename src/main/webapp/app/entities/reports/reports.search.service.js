(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ReportsSearch', ReportsSearch);

    ReportsSearch.$inject = ['$resource'];

    function ReportsSearch($resource) {
        var resourceUrl =  'api/_search/reports/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
