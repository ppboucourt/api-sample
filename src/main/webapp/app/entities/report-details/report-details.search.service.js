(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ReportDetailsSearch', ReportDetailsSearch);

    ReportDetailsSearch.$inject = ['$resource'];

    function ReportDetailsSearch($resource) {
        var resourceUrl =  'api/_search/report-details/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
